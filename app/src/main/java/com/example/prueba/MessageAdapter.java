package com.example.prueba;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fecha, parent, false);
            return new FechaViewHolder(view);
        }
        int layout = viewType == 0 ? R.layout.item_message_send : R.layout.item_message_receive;
        View view2 = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MessageViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = this.messageList.get(position);
        if (position == 0 || esMismoDia(this.messageList.get(position - 1).getTimestamp(), message.getTimestamp())) {
            mostrarFecha(holder, message.getTimestamp());
        }
        if (holder instanceof MessageViewHolder) {
            MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
            messageViewHolder.messageTextView.setText(message.getMessage());
            if (message.isReply()) {
                SpannableString spannableString = getSpannableString(message);
                messageViewHolder.replyTextView.setText(spannableString);
                messageViewHolder.replyTextView.setVisibility(View.VISIBLE);
            } else {
                messageViewHolder.replyTextView.setVisibility(View.GONE);
            }
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            messageViewHolder.textViewHora.setText(formatoHora.format(new Date(message.getTimestamp())));
            if (message.isSelected()) {
                holder.itemView.setBackgroundColor(-3355444);
            } else {
                holder.itemView.setBackgroundColor(-1);
            }
            return;
        }
        if (holder instanceof FechaViewHolder) {
            FechaViewHolder fechaViewHolder = (FechaViewHolder) holder;
            fechaViewHolder.textViewFecha.setText(message.getMessage());
        }
    }

    public void toggleSelection(int position) {
        Message mensaje = this.messageList.get(position);
        if (!mensaje.isFecha()) {
            mensaje.setSelected(!mensaje.isSelected());
            notifyItemChanged(position);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void eliminarMensajesSeleccionados() {
        List<Message> mensajesAEliminar = new ArrayList<>();
        for (int i = this.messageList.size() - 1; i >= 0; i--) {
            if (this.messageList.get(i).isSelected() && !this.messageList.get(i).isFecha()) {
                mensajesAEliminar.add(this.messageList.get(i));
            }
        }
        this.messageList.removeAll(mensajesAEliminar);
        eliminarMensajesFechaVacios();
        notifyDataSetChanged();
    }

    private void eliminarMensajesFechaVacios() {
        List<Message> mensajesFechaAEliminar = new ArrayList<>();
        for (int i = this.messageList.size() - 1; i >= 0; i--) {
            Message message = this.messageList.get(i);
            if (message.isFecha() && !hayMensajesParaFecha(message.getTimestamp())) {
                mensajesFechaAEliminar.add(message);
            }
        }
        this.messageList.removeAll(mensajesFechaAEliminar);
    }

    private boolean hayMensajesParaFecha(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int day = cal.get(6);
        int year = cal.get(1);
        for (Message message : this.messageList) {
            if (!message.isFecha()) {
                Calendar msgCal = Calendar.getInstance();
                msgCal.setTimeInMillis(message.getTimestamp());
                if (msgCal.get(6) == day && msgCal.get(1) == year) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hayMensajesSeleccionados() {
        for (Message message : this.messageList) {
            if (message.isSelected()) {
                return true;
            }
        }
        return false;
    }

    public int getSelectedItemCount() {
        int count = 0;
        for (Message message : this.messageList) {
            if (message.isSelected() && !message.isFecha()) {
                count++;
            }
        }
        return count;
    }

    private static SpannableString getSpannableString(Message message) {
        String senderName = message.getSenderName();
        SpannableString spannableString = new SpannableString(senderName + "\n" + message.getRepliedMessage().getMessage());
        StyleSpan boldSpan = new StyleSpan(1);
        spannableString.setSpan(boldSpan, 0, senderName.length(), 33);
        return spannableString;
    }

    public boolean esMismoDia(long timestamp1, long timestamp2) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fecha1 = formatoFecha.format(new Date(timestamp1));
        String fecha2 = formatoFecha.format(new Date(timestamp2));
        return !fecha1.equals(fecha2);
    }

    private void mostrarFecha(RecyclerView.ViewHolder holder, long timestamp) {
        if (holder instanceof FechaViewHolder) {
            FechaViewHolder fechaViewHolder = (FechaViewHolder) holder;
            String textoFecha = calcularTextoFecha(new Date(timestamp));
            fechaViewHolder.textViewFecha.setText(textoFecha);
        }
    }

    public String calcularTextoFecha(Date fechaMensaje) {
        Calendar calendarioHoy = Calendar.getInstance();
        Calendar calendarioAyer = Calendar.getInstance();
        calendarioAyer.add(6, -1);
        Calendar calendarioMensaje = Calendar.getInstance();
        calendarioMensaje.setTime(fechaMensaje);
        if (calendarioMensaje.get(1) != calendarioHoy.get(1) || calendarioMensaje.get(6) != calendarioHoy.get(6)) {
            if (calendarioMensaje.get(1) == calendarioAyer.get(1) && calendarioMensaje.get(6) == calendarioAyer.get(6)) {
                return "Yesterday";
            }
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM", Locale.getDefault());
            return formatoFecha.format(fechaMensaje);
        }
        actualizarTextViewsFecha();
        return "Today";
    }

    public void actualizarTextViewsFecha() {
        new Handler(Looper.getMainLooper()).post(MessageAdapter.this::m114x370b1101);
    }

    public void m114x370b1101() {
        for (int i = 0; i < this.messageList.size(); i++) {
            Message mensaje = this.messageList.get(i);
            if (mensaje.isFecha()) {
                String nuevoTextoFecha = calcularTextoFecha(new Date(mensaje.getTimestamp()));
                if (!mensaje.getMessage().equals(nuevoTextoFecha)) {
                    this.messageList.set(i, new Message(nuevoTextoFecha, false, mensaje.getTimestamp()));
                    notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.messageList.size();
    }

    @Override
    public int getItemViewType(int i) {
        Message message = this.messageList.get(i);
        if (message.isFecha()) {
            return 2;
        }
        return !message.isOutgoing() ? 1 : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deseleccionarTodos() {
        for (Message mensaje : this.messageList) {
            if (mensaje.isSelected()) {
                mensaje.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void seleccionarTodos() {
        for (Message mensaje : this.messageList) {
            if (!mensaje.isFecha()) {
                mensaje.setSelected(true);
            }
        }
        notifyDataSetChanged();
    }

    public static class FechaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha;

        public FechaViewHolder(View itemView) {
            super(itemView);
            this.textViewFecha = itemView.findViewById(R.id.text_view_fecha);
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView replyTextView;
        TextView textViewHora;

        public MessageViewHolder(View itemView) {
            super(itemView);
            this.messageTextView =  itemView.findViewById(R.id.text_view_message);
            this.replyTextView =  itemView.findViewById(R.id.reply_message);
            this.textViewHora =  itemView.findViewById(R.id.date_time);
        }
    }
}
