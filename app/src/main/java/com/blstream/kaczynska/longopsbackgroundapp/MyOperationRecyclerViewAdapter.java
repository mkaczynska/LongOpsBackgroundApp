package com.blstream.kaczynska.longopsbackgroundapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MyOperationRecyclerViewAdapter extends RecyclerView.Adapter<MyOperationRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Operation> startedOperationList;
    ViewHolder holder;

    public MyOperationRecyclerViewAdapter(ArrayList<Operation> operations) {
        startedOperationList = operations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.running_operation, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Operation operation = startedOperationList.get(position);
        holder.operation = operation;
        String progressInfo = operation.toString();
        holder.progressInfo.setText(progressInfo);
        holder.progressBar.setProgress(0);
    }

    @Override
    public int getItemCount() {
        return startedOperationList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView progressInfo;
        public final ProgressBar progressBar;
        public Operation operation;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            progressInfo = (TextView) view.findViewById(R.id.progressTextViewID);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBarID);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);

        }

    }
}
