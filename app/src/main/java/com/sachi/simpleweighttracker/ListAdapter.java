package com.sachi.simpleweighttracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewTreeViewModelKt;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {

    Activity mContext;
    List<WeightClass> weightList;
    public ListAdapter(@NonNull Activity mContext, List<WeightClass> weightList) {
        super(mContext, R.layout.listlayout,weightList);

        this.mContext = mContext;
        this.weightList = weightList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View view =inflater.inflate(R.layout.listlayout,null,true);

        TextView weight,time;

        weight = view.findViewById(R.id.weightData);
        time = view.findViewById(R.id.timeData);

        WeightClass listWeight = weightList.get(position);

        weight.setText(listWeight.weight);
        time.setText(listWeight.time);

        return view;
    }
}
