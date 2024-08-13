package dev.godjango.apk.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import dev.godjango.apk.R;

public class CountryAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> countries;

    public CountryAdapter(Context context, List<String> countries) {
        super(context, R.layout.spinner_item, countries);
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createView(position, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createView(position, parent);
    }

    @SuppressLint("SetTextI18n")
    private View createView(int position, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.spinner_item, parent, false);

        String country = countries.get(position);
        TextView textView = view.findViewById(R.id.text1);
        textView.setText(country);

        return view;
    }
}
