package com.example.martijn.myapplication.src;

/**
 * Created by Martijn on 14-12-16.
 * Hiermee worden alle knopjes netjes weergeven op het hoofdscherm.
 * De code is grotendeels overgenomen van internet.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.martijn.myapplication.R;
import com.example.martijn.myapplication.threads.getCocktailThread;

import java.util.ArrayList;

import API.api.MixDrankResponse;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final int _position = position;
        final DataObjectHolder _holder = holder;
        if(mDataset.get(_position).getIdentifier().equals("+")) {
            _holder.label.setText("+");
        }else {
            OnTaskCompleted<MixDrankResponse> listener = new OnTaskCompleted<MixDrankResponse>() {
                @Override
                public void onTaskCompleted(MixDrankResponse _response) {
                    MixDrankResponse response = _response;
                    mDataset.get(_position).setMixName(response.getMixDrankNaam());
                    _holder.label.setText(mDataset.get(_position).getMixName());
                }
            };
            getCocktailThread request = new getCocktailThread(listener);
            request.execute(mDataset.get(position).getIdentifier());
        }


    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}