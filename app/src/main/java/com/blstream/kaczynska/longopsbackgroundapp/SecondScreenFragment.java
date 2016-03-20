package com.blstream.kaczynska.longopsbackgroundapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blstream.kaczynska.longopsbackgroundapp.MyOperationRecyclerViewAdapter.ViewHolder;

import java.util.ArrayList;

/**
 * A fragment representing a list of running operations.
 */
public class SecondScreenFragment extends Fragment {

    public final static String RECEIVEMSG = "onReceive";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static int progressStatus = 0;
    MyOperationRecyclerViewAdapter myOperationRecyclerViewAdapter;
    RecyclerView recyclerView;
    Context context;
    private int mColumnCount = 1;
    private ArrayList<Operation> startedOperationList;
    private RemainingTimeUpdateReceiver timeUpdateReceiver;
    private ProgressBar operationProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain the instance between configuration changes
        setRetainInstance(true);

        initReceiver();
        Bundle bundle;

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            bundle = getArguments().getBundle("startedOperationList");
            if (bundle != null) {
                startedOperationList = bundle.getParcelableArrayList("startedOperationList");
            }
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation_list, container, false);
        context = view.getContext();

        if (savedInstanceState != null) {
            startedOperationList = savedInstanceState.getParcelableArrayList("startedOperationList");
//            layoutManagerState = savedInstanceState.getParcelable("layoutManagerState");
//            recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerState);
        }// Set the adapter
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myOperationRecyclerViewAdapter = new MyOperationRecyclerViewAdapter(startedOperationList);
            recyclerView.setAdapter(myOperationRecyclerViewAdapter);
        }
        return view;
    }


    private void initReceiver() {
        timeUpdateReceiver = new RemainingTimeUpdateReceiver();
        IntentFilter filter = new IntentFilter(RECEIVEMSG);
        getActivity().registerReceiver(timeUpdateReceiver, filter);
    }

    private void finishReceiver() {
        getActivity().unregisterReceiver(timeUpdateReceiver);
    }

    @Override
    public void onDestroy() {
        finishReceiver();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("startedOperationList", startedOperationList);
//        layoutManager = recyclerView.getLayoutManager();
//        outState.putParcelable("layoutManagerState", layoutManager.onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
//            layoutManagerState = savedInstanceState.getParcelable("layoutManagerState");
            startedOperationList = savedInstanceState.getParcelableArrayList("startedOperationList");
        }
        super.onViewStateRestored(savedInstanceState);
    }

    private class RemainingTimeUpdateReceiver extends BroadcastReceiver {
        @Override
        public synchronized void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RECEIVEMSG)) {
                try {
                    int operationId = Integer.parseInt(intent.getStringExtra("operationId"));
                    double secsUntilFinished = Integer.parseInt(intent.getStringExtra("remainingTime"));

                    Operation currentOperation = startedOperationList.get(operationId - 1);
                    currentOperation.setRemainingTime(secsUntilFinished);

                    ViewHolder operationView = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(operationId - 1);
                    operationView.progressInfo.setText(currentOperation.toString());
                    progressStatus = (int) (currentOperation.getRemainingTime() * 100 / currentOperation.getDurationTime());
                    operationProgressBar = operationView.progressBar;
                    operationProgressBar.setProgress(progressStatus);

                } catch (NullPointerException exception) {
                    throw new NullPointerException("NullPointerException.");
                }
            }
        }
    }
}
