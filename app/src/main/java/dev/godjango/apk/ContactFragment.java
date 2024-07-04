package dev.godjango.apk;

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

/** @noinspection deprecation*/
public class ContactFragment extends Fragment implements onDeleteMessagesListener {
    private static final String FILE_NAME = "messages.json";
    private ActionLayoutListener actionLayoutListener;
    private ImageButton cancel_reply;
    private EditText editTextMessage;
    private Handler handler;
    private boolean isSwipe = false;
    private Message messageToReply;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
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
        this.messageList = new ArrayList<>();
        loadMessages();
        this.messageAdapter = new MessageAdapter(this.messageList);
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
        Message newMessage;
        String messageText = this.editTextMessage.getText().toString();
        if (!messageText.trim().isEmpty()) {
            this.cancel_reply.setVisibility(View.GONE);
            if (!messageText.isEmpty()) {
                if (this.messageToReply != null) {
                    RecyclerView.ViewHolder viewHolder = this.recyclerView.findViewHolderForAdapterPosition(this.messageList.indexOf(this.messageToReply));
                    if (viewHolder != null) {
                        TextView senderNameTextView = viewHolder.itemView.findViewById(R.id.sender_name);
                        String senderName = senderNameTextView.getText().toString();
                        newMessage = new Message(messageText, true, this.messageToReply, senderName);
                    } else {
                        Log.w("ContactFragment", "Not found viewHolder for adapter position");
                        newMessage = new Message(messageText, true, this.messageToReply, null);
                    }
                } else {
                    newMessage = new Message(messageText, true);
                }
                if (this.messageList.isEmpty() || this.messageAdapter.sameDay(this.messageList.get(this.messageList.size() - 1).getTimestamp(), newMessage.getTimestamp())) {
                    String dateText = this.messageAdapter.calculateDateText(new Date(newMessage.getTimestamp()));
                    Message fechaMessage = new Message(dateText, false, newMessage.getTimestamp());
                    fechaMessage.setDate(true);
                    this.messageList.add(fechaMessage);
                }
                this.messageList.add(newMessage);
                this.messageAdapter.notifyDataSetChanged();
                this.editTextMessage.setText("");
                this.recyclerView.scrollToPosition(this.messageList.size() - 1);
                new Handler(Looper.getMainLooper()).postDelayed(ContactFragment.this::autoResponse, 1000L);
                ViewGroup.LayoutParams params = messageLayout.getLayoutParams();
                params.height = (int) getResources().getDimension(R.dimen.altura_sin_respuesta);
                messageLayout.setLayoutParams(params);
                this.messageToReply = null;
            }
            this.replyingToText.setVisibility(View.GONE);
        }
        this.isSwipe = false;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void autoResponse() {
        Message reply = new Message("Automatic response to check.", false);
        this.messageList.add(reply);
        this.messageAdapter.notifyDataSetChanged();
        this.recyclerView.scrollToPosition(this.messageList.size() - 1);
    }

    public void cancelReply(RelativeLayout messageLayout) {
        this.cancel_reply.setVisibility(View.GONE);
        this.replyingToText.setVisibility(View.GONE);
        this.messageToReply = null;
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
                ContactFragment.this.messageToReply =  ContactFragment.this.messageList.get(position);
                ContactFragment.this.messageAdapter.notifyItemChanged(position);
                ContactFragment.this.replyingToText.setVisibility(View.VISIBLE);
                ContactFragment.this.replyingToText.setText(ContactFragment.this.messageToReply.getMessage());
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
            String json = gson.toJson(this.messageList);
            FileOutputStream outputStream = requireContext().openFileOutput(FILE_NAME, 0);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            Log.d("Error saving messages", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void loadMessages() {
        this.messageList = new ArrayList<>();
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
            Type typeMessageList = new TypeToken<List<Message>>() {
            }.getType();
            this.messageList = gson.fromJson(json, typeMessageList);
            if (this.messageList == null) {
                this.messageList = new ArrayList<>();
            }
        } catch (IOException e) {
            Log.d("Error loading messages", e.getMessage() != null ? e.getMessage() : "Error");
            this.messageList.clear();
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
}