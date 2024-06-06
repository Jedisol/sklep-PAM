package com.example.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] mOptions;
    private int[] mImageResources;

    public CustomSpinnerAdapter(Context context, String[] options, int[] imageResources) {
        super(context, R.layout.spinner_item, options);
        mContext = context;
        mOptions = options;
        mImageResources = imageResources;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(R.layout.spinner_item, parent, false);

        TextView textViewDescription = row.findViewById(R.id.description);
        ImageView imageView = row.findViewById(R.id.imageView);

        textViewDescription.setText(mOptions[position]);
        imageView.setImageResource(mImageResources[position]);

        return row;
    }
}
