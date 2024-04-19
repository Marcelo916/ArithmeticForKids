package com.example.arithmeticforkids.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arithmeticforkids.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public ViewHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.recycler_view);
    }
}

