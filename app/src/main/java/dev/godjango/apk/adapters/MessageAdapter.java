package dev.godjango.apk.adapters;

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

import dev.godjango.apk.models.MessageData;
import dev.godjango.apk.R;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<MessageData> messageDataList;

    public MessageAdapter(List<MessageData> messageDataList) {
        this.messageDataList = messageDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fecha, parent, false);
            return new DateViewHolder(view);
        }
        int layout = viewType == 0 ? R.layout.item_message_send : R.layout.item_message_receive;
        View view2 = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MessageViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageData messageData = this.messageDataList.get(position);
        if (position == 0 || sameDay(this.messageDataList.get(position - 1).getTimestamp(), messageData.getTimestamp())) {
            showDate(holder, messageData.getTimestamp());
        }
        if (holder instanceof MessageViewHolder) {
            MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
            messageViewHolder.messageTextView.setText(messageData.getMessage());
            if (messageData.isReply()) {
                SpannableString spannableString = getSpannableString(messageData);
                messageViewHolder.replyTextView.setText(spannableString);
                messageViewHolder.replyTextView.setVisibility(View.VISIBLE);
            } else {
                messageViewHolder.replyTextView.setVisibility(View.GONE);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            messageViewHolder.textViewTime.setText(dateFormat.format(new Date(messageData.getTimestamp())));
            if (messageData.isSelected()) {
                holder.itemView.setBackgroundColor(-3355444);
            } else {
                holder.itemView.setBackgroundColor(-1);
            }
            return;
        }
        if (holder instanceof DateViewHolder) {
            DateViewHolder dateViewHolder = (DateViewHolder) holder;
            dateViewHolder.dateTextView.setText(messageData.getMessage());
        }
    }

    public void toggleSelection(int position) {
        MessageData messageData = this.messageDataList.get(position);
        if (!messageData.isDate()) {
            messageData.setSelected(!messageData.isSelected());
            notifyItemChanged(position);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteSelectedMessages() {
        List<MessageData> messagesToDelete = new ArrayList<>();
        for (int i = this.messageDataList.size() - 1; i >= 0; i--) {
            if (this.messageDataList.get(i).isSelected() && !this.messageDataList.get(i).isDate()) {
                messagesToDelete.add(this.messageDataList.get(i));
            }
        }
        this.messageDataList.removeAll(messagesToDelete);
        deleteEmptyDates();
        notifyDataSetChanged();
    }

    private void deleteEmptyDates() {
        List<MessageData> datesToDelete = new ArrayList<>();
        for (int i = this.messageDataList.size() - 1; i >= 0; i--) {
            MessageData messageData = this.messageDataList.get(i);
            if (messageData.isDate() && !messagesForDate(messageData.getTimestamp())) {
                datesToDelete.add(messageData);
            }
        }
        this.messageDataList.removeAll(datesToDelete);
    }

    private boolean messagesForDate(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int day = cal.get(6);
        int year = cal.get(1);
        for (MessageData messageData : this.messageDataList) {
            if (!messageData.isDate()) {
                Calendar msgCal = Calendar.getInstance();
                msgCal.setTimeInMillis(messageData.getTimestamp());
                if (msgCal.get(6) == day && msgCal.get(1) == year) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasSelectedMessages() {
        for (MessageData messageData : this.messageDataList) {
            if (messageData.isSelected()) {
                return true;
            }
        }
        return false;
    }

    public int getSelectedItemCount() {
        int count = 0;
        for (MessageData messageData : this.messageDataList) {
            if (messageData.isSelected() && !messageData.isDate()) {
                count++;
            }
        }
        return count;
    }

    private static SpannableString getSpannableString(MessageData messageData) {
        String senderName = messageData.getSenderName();
        SpannableString spannableString = new SpannableString(senderName + "\n" + messageData.getRepliedMessage().getMessage());
        StyleSpan boldSpan = new StyleSpan(1);
        spannableString.setSpan(boldSpan, 0, senderName.length(), 33);
        return spannableString;
    }

    public boolean sameDay(long timestamp1, long timestamp2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date1 = dateFormat.format(new Date(timestamp1));
        String date2 = dateFormat.format(new Date(timestamp2));
        return !date1.equals(date2);
    }

    private void showDate(RecyclerView.ViewHolder holder, long timestamp) {
        if (holder instanceof DateViewHolder) {
            DateViewHolder dateViewHolder = (DateViewHolder) holder;
            String dateText = calculateDateText(new Date(timestamp));
            dateViewHolder.dateTextView.setText(dateText);
        }
    }

    public String calculateDateText(Date messageDate) {
        Calendar calendarToday = Calendar.getInstance();
        Calendar calendarYesterday = Calendar.getInstance();
        calendarToday.add(6, -1);
        Calendar calendarMessage = Calendar.getInstance();
        calendarMessage.setTime(messageDate);
        if (calendarMessage.get(1) != calendarToday.get(1) || calendarMessage.get(6) != calendarToday.get(6)) {
            if (calendarMessage.get(1) == calendarYesterday.get(1) && calendarMessage.get(6) == calendarYesterday.get(6)) {
                return "Today";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
            return dateFormat.format(messageDate);
        }
        updateTextViewDate();
        return "Yesterday";
    }

    public void updateTextViewDate() {
        new Handler(Looper.getMainLooper()).post(MessageAdapter.this::m114x370b1101);
    }

    public void m114x370b1101() {
        for (int i = 0; i < this.messageDataList.size(); i++) {
            MessageData messageData = this.messageDataList.get(i);
            if (messageData.isDate()) {
                String newDateText = calculateDateText(new Date(messageData.getTimestamp()));
                if (!messageData.getMessage().equals(newDateText)) {
                    this.messageDataList.set(i, new MessageData(newDateText, false, messageData.getTimestamp()));
                    notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.messageDataList.size();
    }

    @Override
    public int getItemViewType(int i) {
        MessageData messageData = this.messageDataList.get(i);
        if (messageData.isDate()) {
            return 2;
        }
        return !messageData.isOutgoing() ? 1 : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void unselectedAll() {
        for (MessageData messageData : this.messageDataList) {
            if (messageData.isSelected()) {
                messageData.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectedAll() {
        for (MessageData messageData : this.messageDataList) {
            if (!messageData.isDate()) {
                messageData.setSelected(true);
            }
        }
        notifyDataSetChanged();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;

        public DateViewHolder(View itemView) {
            super(itemView);
            this.dateTextView = itemView.findViewById(R.id.text_view_fecha);
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView replyTextView;
        TextView textViewTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            this.messageTextView =  itemView.findViewById(R.id.text_view_message);
            this.replyTextView =  itemView.findViewById(R.id.reply_message);
            this.textViewTime =  itemView.findViewById(R.id.date_time);
        }
    }
}
