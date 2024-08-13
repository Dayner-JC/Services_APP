package dev.godjango.apk.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dev.godjango.apk.R;
import dev.godjango.apk.adapters.MessageAdapter;
import dev.godjango.apk.listeners.ActionLayoutListener;
import dev.godjango.apk.listeners.RecyclerItemClickListener;
import dev.godjango.apk.listeners.onDeleteMessagesListener;
import dev.godjango.apk.models.MessageData;

/** @noinspection deprecation*/
public class ContactFragment extends Fragment implements onDeleteMessagesListener {
    private static final String FILE_NAME = "messages.json";
    private ActionLayoutListener actionLayoutListener;
    private ImageButton cancel_reply;
    private EditText editTextMessage;
    private Handler handler;
    private boolean isSwipe = false;
    private MessageData messageDataToReply;
    private MessageAdapter messageAdapter;
    private List<MessageData> messageDataList;
    private RecyclerView recyclerView;
    private TextView replyingToText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.actionLayoutListener = (ActionLayoutListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement ActionLayoutListener");
        }
    }

    public MessageAdapter getMessageAdapter() {
        return this.messageAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact, container, false);
        cleanFileMessages();
        this.cancel_reply = view.findViewById(R.id.cancel_reply);
        this.recyclerView = view.findViewById(R.id.recycler_view);
        this.editTextMessage = view.findViewById(R.id.edit_text_message);
        ImageButton buttonSend =  view.findViewById(R.id.button_send);
        this.messageDataList = new ArrayList<>();
        loadMessages();
        this.messageAdapter = new MessageAdapter(this.messageDataList);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(this.messageAdapter);
        this.replyingToText = view.findViewById(R.id.reply_message);
        this.replyingToText.setVisibility(View.GONE);
        final RelativeLayout messageLayout = view.findViewById(R.id.message_layout);

        buttonSend.setOnClickListener(view2 -> ContactFragment.this.sendMessage(messageLayout));
        ItemTouchHelper itemTouchHelper = getItemTouchHelper(messageLayout);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);

        this.cancel_reply.setOnClickListener(view2 -> ContactFragment.this.cancelReply(messageLayout));
        this.handler = new Handler(Looper.getMainLooper());

        dailyUpdate();
        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view2, int position) {
                Log.d("ContactFragment", "onItemClick called");
                if (ContactFragment.this.messageAdapter.hasSelectedMessages()) {
                    ContactFragment.this.messageAdapter.toggleSelection(position);
                    if (ContactFragment.this.messageAdapter.getSelectedItemCount() > 0) {
                        ContactFragment.this.actionLayoutListener.onMessageSelected();

                    } else {
                        ContactFragment.this.actionLayoutListener.onActionLayoutDismissed();
                    }
                }
            }

            @Override
            public void onItemLongClick(View view2, int position) {
                if (!ContactFragment.this.isSwipe) {
                    ContactFragment.this.messageAdapter.toggleSelection(position);
                    if (ContactFragment.this.messageAdapter.getSelectedItemCount() > 0) {
                        ContactFragment.this.actionLayoutListener.onMessageSelected();
                    } else {
                        ContactFragment.this.actionLayoutListener.onActionLayoutDismissed();
                    }
                }
            }
        }));

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sendMessage(RelativeLayout messageLayout) {
        MessageData newMessageData;
        String messageText = this.editTextMessage.getText().toString();
        if (!messageText.trim().isEmpty()) {
            this.cancel_reply.setVisibility(View.GONE);
            if (!messageText.isEmpty()) {
                if (this.messageDataToReply != null) {
                    RecyclerView.ViewHolder viewHolder = this.recyclerView.findViewHolderForAdapterPosition(this.messageDataList.indexOf(this.messageDataToReply));
                    if (viewHolder != null) {
                        TextView senderNameTextView = viewHolder.itemView.findViewById(R.id.sender_name);
                        String senderName = senderNameTextView.getText().toString();
                        newMessageData = new MessageData(messageText, true, this.messageDataToReply, senderName);
                    } else {
                        Log.w("ContactFragment", "Not found viewHolder for adapter position");
                        newMessageData = new MessageData(messageText, true, this.messageDataToReply, null);
                    }
                } else {
                    newMessageData = new MessageData(messageText, true);
                }
                if (this.messageDataList.isEmpty() || this.messageAdapter.sameDay(this.messageDataList.get(this.messageDataList.size() - 1).getTimestamp(), newMessageData.getTimestamp())) {
                    String dateText = this.messageAdapter.calculateDateText(new Date(newMessageData.getTimestamp()));
                    MessageData fechaMessageData = new MessageData(dateText, false, newMessageData.getTimestamp());
                    fechaMessageData.setDate(true);
                    this.messageDataList.add(fechaMessageData);
                }
                this.messageDataList.add(newMessageData);
                this.messageAdapter.notifyDataSetChanged();
                this.editTextMessage.setText("");
                this.recyclerView.scrollToPosition(this.messageDataList.size() - 1);
                ViewGroup.LayoutParams params = messageLayout.getLayoutParams();
                params.height = (int) getResources().getDimension(R.dimen.altura_sin_respuesta);
                messageLayout.setLayoutParams(params);
                this.messageDataToReply = null;
            }
            this.replyingToText.setVisibility(View.GONE);
        }
        this.isSwipe = false;
    }

    public void cancelReply(RelativeLayout messageLayout) {
        this.cancel_reply.setVisibility(View.GONE);
        this.replyingToText.setVisibility(View.GONE);
        this.messageDataToReply = null;
        ViewGroup.LayoutParams params = messageLayout.getLayoutParams();
        params.height = (int) getResources().getDimension(R.dimen.altura_sin_respuesta);
        messageLayout.setLayoutParams(params);
        this.isSwipe = false;
    }

    private ItemTouchHelper getItemTouchHelper(final RelativeLayout messageLayout) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, 8) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ContactFragment.this.isSwipe = true;
                int position = viewHolder.getAdapterPosition();
                ContactFragment.this.messageDataToReply =  ContactFragment.this.messageDataList.get(position);
                ContactFragment.this.messageAdapter.notifyItemChanged(position);
                ContactFragment.this.replyingToText.setVisibility(View.VISIBLE);
                ContactFragment.this.replyingToText.setText(ContactFragment.this.messageDataToReply.getMessage());
                ViewGroup.LayoutParams params = messageLayout.getLayoutParams();
                params.height = (int) ContactFragment.this.getResources().getDimension(R.dimen.altura_con_respuesta);
                messageLayout.setLayoutParams(params);
                ContactFragment.this.cancel_reply.setVisibility(View.VISIBLE);
            }
        };
        return new ItemTouchHelper(itemTouchHelperCallback);
    }

    private void dailyUpdate() {
        Calendar now = Calendar.getInstance();
        Calendar midnight = Calendar.getInstance();
        midnight.set(11, 0);
        midnight.set(12, 0);
        midnight.set(13, 0);
        midnight.add(6, 1);
        long timeUntilMidnight = midnight.getTimeInMillis() - now.getTimeInMillis();
        this.handler.postDelayed(ContactFragment.this::update, timeUntilMidnight);
    }

    public void update() {
        this.messageAdapter.updateTextViewDate();
        dailyUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveMessages();
    }

    private void saveMessages() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this.messageDataList);
            FileOutputStream outputStream = requireContext().openFileOutput(FILE_NAME, 0);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            Log.d("Error saving messages", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void loadMessages() {
        this.messageDataList = new ArrayList<>();
        try {
            FileInputStream inputStream = requireContext().openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                } else {
                    stringBuilder.append(line);
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            String json = stringBuilder.toString();
            Gson gson = new Gson();
            Type typeMessageList = new TypeToken<List<MessageData>>() {
            }.getType();
            this.messageDataList = gson.fromJson(json, typeMessageList);
            if (this.messageDataList == null) {
                this.messageDataList = new ArrayList<>();
            }
        } catch (IOException e) {
            Log.d("Error loading messages", e.getMessage() != null ? e.getMessage() : "Error");
            this.messageDataList.clear();
        }
    }

    private void cleanFileMessages() {
        try {
            FileOutputStream outputStream = requireContext().openFileOutput(FILE_NAME, 0);
            outputStream.write("".getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            Log.d("Messages", "Clean file success.");
        } catch (IOException e) {
            Log.e("Messages", "Could not clean file", e);
        }
    }

    @Override
    public void onDeleteMessages() {
        if (this.messageAdapter != null) {
            Log.d("ContactFragment", "onDeleteMessages called");
            this.messageAdapter.deleteSelectedMessages();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sendAutoMessage() {
        MessageData newMessageData = new MessageData("Hello, I have requested a service with a price to be quoted.", true);

        if (messageDataList.isEmpty() || messageAdapter.sameDay(messageDataList.get(messageDataList.size() - 1).getTimestamp(), newMessageData.getTimestamp())) {
            String dateText = messageAdapter.calculateDateText(new Date(newMessageData.getTimestamp()));
            MessageData fechaMessageData = new MessageData(dateText, false, newMessageData.getTimestamp());
            fechaMessageData.setDate(true);
            messageDataList.add(fechaMessageData);
        }

        messageDataList.add(newMessageData);
        messageAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messageDataList.size() - 1);
    }
}
