package com.example.prueba;

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


public class DashboardFragment extends Fragment implements onDeleteMessagesListener {
    private static final String FILE_NAME = "messages.json";
    private ActionLayoutListener actionLayoutListener;
    private ImageButton cancel_reply;
    private EditText editTextMessage;
    private Handler handler;
    private boolean isSwipe = false;
    private Message mensajeAResponder;
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        limpiarArchivoMensajes();
        this.cancel_reply = view.findViewById(R.id.cancel_reply);
        this.recyclerView = view.findViewById(R.id.recycler_view);
        this.editTextMessage = view.findViewById(R.id.edit_text_message);
        ImageButton buttonSend =  view.findViewById(R.id.button_send);
        this.messageList = new ArrayList<>();
        cargarMensajes();
        this.messageAdapter = new MessageAdapter(this.messageList);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(this.messageAdapter);
        this.replyingToText = view.findViewById(R.id.reply_message);
        this.replyingToText.setVisibility(View.GONE);
        final RelativeLayout messageLayout = view.findViewById(R.id.message_layout);

        buttonSend.setOnClickListener(view2 -> DashboardFragment.this.m103xe81eb2e0(messageLayout, view2));
        ItemTouchHelper itemTouchHelper = getItemTouchHelper(messageLayout);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);

        this.cancel_reply.setOnClickListener(view2 -> DashboardFragment.this.m104x5d98d921(messageLayout, view2));
        this.handler = new Handler(Looper.getMainLooper());

        programarActualizacionDiaria();
        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view2, int position) {
                Log.d("DashboardFragment", "onItemClick llamado");
                if (DashboardFragment.this.messageAdapter.hayMensajesSeleccionados()) {
                    DashboardFragment.this.messageAdapter.toggleSelection(position);
                    if (DashboardFragment.this.messageAdapter.getSelectedItemCount() > 0) {
                        DashboardFragment.this.actionLayoutListener.onMessageSelected();
                    } else {
                        DashboardFragment.this.actionLayoutListener.onActionLayoutDismissed();
                    }
                }
            }

            @Override
            public void onItemLongClick(View view2, int position) {
                if (!DashboardFragment.this.isSwipe) {
                    DashboardFragment.this.messageAdapter.toggleSelection(position);
                    if (DashboardFragment.this.messageAdapter.getSelectedItemCount() > 0) {
                        DashboardFragment.this.actionLayoutListener.onMessageSelected();
                    } else {
                        DashboardFragment.this.actionLayoutListener.onActionLayoutDismissed();
                    }
                }
            }
        }));
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void m103xe81eb2e0(RelativeLayout messageLayout, View v) {
        Message nuevoMensaje;
        String messageText = this.editTextMessage.getText().toString();
        if (!messageText.trim().isEmpty()) {
            this.cancel_reply.setVisibility(View.GONE);
            if (!messageText.isEmpty()) {
                if (this.mensajeAResponder != null) {
                    RecyclerView.ViewHolder viewHolder = this.recyclerView.findViewHolderForAdapterPosition(this.messageList.indexOf(this.mensajeAResponder));
                    if (viewHolder != null) {
                        TextView senderNameTextView = viewHolder.itemView.findViewById(R.id.sender_name);
                        String senderName = senderNameTextView.getText().toString();
                        nuevoMensaje = new Message(messageText, true, this.mensajeAResponder, senderName);
                    } else {
                        Log.w("DashboardFragment", "No se pudo encontrar el ViewHolder del mensaje a responder");
                        nuevoMensaje = new Message(messageText, true, this.mensajeAResponder, null);
                    }
                } else {
                    nuevoMensaje = new Message(messageText, true);
                }
                if (this.messageList.isEmpty() || this.messageAdapter.esMismoDia(this.messageList.get(this.messageList.size() - 1).getTimestamp(), nuevoMensaje.getTimestamp())) {
                    String textoFecha = this.messageAdapter.calcularTextoFecha(new Date(nuevoMensaje.getTimestamp()));
                    Message fechaMessage = new Message(textoFecha, false, nuevoMensaje.getTimestamp());
                    fechaMessage.setFecha(true);
                    this.messageList.add(fechaMessage);
                }
                this.messageList.add(nuevoMensaje);
                this.messageAdapter.notifyDataSetChanged();
                this.editTextMessage.setText("");
                this.recyclerView.scrollToPosition(this.messageList.size() - 1);
                new Handler(Looper.getMainLooper()).postDelayed(DashboardFragment.this::m102x72a48c9f, 1000L);
                ViewGroup.LayoutParams params = messageLayout.getLayoutParams();
                params.height = (int) getResources().getDimension(R.dimen.altura_sin_respuesta);
                messageLayout.setLayoutParams(params);
                this.mensajeAResponder = null;
            }
            this.replyingToText.setVisibility(View.GONE);
        }
        this.isSwipe = false;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void m102x72a48c9f() {
        Message mensajeRespuesta = new Message("Esta es una respuesta automática para comprobar diseño.", false);
        this.messageList.add(mensajeRespuesta);
        this.messageAdapter.notifyDataSetChanged();
        this.recyclerView.scrollToPosition(this.messageList.size() - 1);
    }

    public void m104x5d98d921(RelativeLayout messageLayout, View v) {
        this.cancel_reply.setVisibility(View.GONE);
        this.replyingToText.setVisibility(View.GONE);
        this.mensajeAResponder = null;
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
                DashboardFragment.this.isSwipe = true;
                int position = viewHolder.getAdapterPosition();
                DashboardFragment.this.mensajeAResponder =  DashboardFragment.this.messageList.get(position);
                DashboardFragment.this.messageAdapter.notifyItemChanged(position);
                DashboardFragment.this.replyingToText.setVisibility(View.VISIBLE);
                DashboardFragment.this.replyingToText.setText(DashboardFragment.this.mensajeAResponder.getMessage());
                ViewGroup.LayoutParams params = messageLayout.getLayoutParams();
                params.height = (int) DashboardFragment.this.getResources().getDimension(R.dimen.altura_con_respuesta);
                messageLayout.setLayoutParams(params);
                DashboardFragment.this.cancel_reply.setVisibility(View.VISIBLE);
            }
        };
        return new ItemTouchHelper(itemTouchHelperCallback);
    }

    private void programarActualizacionDiaria() {
        Calendar ahora = Calendar.getInstance();
        Calendar medianoche = Calendar.getInstance();
        medianoche.set(11, 0);
        medianoche.set(12, 0);
        medianoche.set(13, 0);
        medianoche.add(6, 1);
        long tiempoHastaMedianoche = medianoche.getTimeInMillis() - ahora.getTimeInMillis();
        this.handler.postDelayed(DashboardFragment.this::m105x822ce722, tiempoHastaMedianoche);
    }

    public void m105x822ce722() {
        this.messageAdapter.actualizarTextViewsFecha();
        programarActualizacionDiaria();
    }

    @Override
    public void onPause() {
        super.onPause();
        guardarMensajes();
    }

    private void guardarMensajes() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this.messageList);
            FileOutputStream outputStream = requireContext().openFileOutput(FILE_NAME, 0);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            Log.d("Error al guardar mensajes", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void cargarMensajes() {
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
            Type tipoListaMensajes = new TypeToken<List<Message>>() {
            }.getType();
            this.messageList = gson.fromJson(json, tipoListaMensajes);
            if (this.messageList == null) {
                this.messageList = new ArrayList<>();
            }
        } catch (IOException e) {
            Log.d("Error al cargar mensajes", e.getMessage() != null ? e.getMessage() : "Error desconocido");
            this.messageList.clear();
        }
    }

    private void limpiarArchivoMensajes() {
        try {
            FileOutputStream outputStream = requireContext().openFileOutput(FILE_NAME, 0);
            outputStream.write("".getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            Log.d("Mensajes", "Archivo messages.json limpiado exitosamente.");
        } catch (IOException e) {
            Log.e("Mensajes", "Error al limpiar el archivo messages.json", e);
        }
    }

    @Override
    public void onDeleteMessages() {
        if (this.messageAdapter != null) {
            Log.d("DashboardFragment", "onDeleteMessages llamado");
            this.messageAdapter.eliminarMensajesSeleccionados();
        }
    }
}
