package com.blstream.kaczynska.longopsbackgroundapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class FirstScreenFragment extends Fragment {

    private OperationOption selectedOption;
    private ArrayList<Operation> startedOperationList;
    private ArrayList<OperationOption> operationOptionList;
    private Context context;
    private OnGetDetailsButtonPressedListener onGetDetailsCallback;
    private OnOperationStartButtonPressedListener OnOperationStartCallback;
    final static int[] operationTimeArray = new int[]{10, 15, 20, 25, 1000};


    public interface OnGetDetailsButtonPressedListener {
        void OnGetDetailsButtonPressed(ArrayList<Operation> startedOperationList);
    }
    public interface OnOperationStartButtonPressedListener {
        void OnOperationStartButtonPressed(Operation operation);
    }


    public FirstScreenFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("startedOperationList", startedOperationList);
//        layoutManager = recyclerView.getLayoutManager();
//        outState.putParcelable("layoutManagerState", layoutManager.onSaveInstanceState());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState != null) {
            startedOperationList = savedInstanceState.getParcelableArrayList("startedOperationList");
        }
        else {
            operationOptionList = new ArrayList<>();
            startedOperationList = new ArrayList<>();
            for (int i = 0; i < operationTimeArray.length; i++) {
                operationOptionList.add(new OperationOption(operationTimeArray[i]));
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_screen, container, false);
        context = view.getContext();

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerId);

        OperationTimeAdapter operationTimeAdapter =
                new OperationTimeAdapter(context, operationOptionList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedOption = (OperationOption) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner.setAdapter(operationTimeAdapter);


        Button startOperationButton = (Button) view.findViewById(R.id.buttonStartOperationId);
        startOperationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOption != null) {
                    Operation startedOperation = new Operation(selectedOption);
                    startedOperationList.add(startedOperation);
                    OnOperationStartCallback.OnOperationStartButtonPressed(startedOperation);
                }
            }
        });


        Button operationDetailsButton = (Button) view.findViewById(R.id.buttonGetDetailsId);
        operationDetailsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetDetailsCallback.OnGetDetailsButtonPressed(startedOperationList);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onGetDetailsCallback = (OnGetDetailsButtonPressedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnGetDetailsButtonPressedListener");
        }
        try {
            OnOperationStartCallback = (OnOperationStartButtonPressedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnOperationStartButtonPressedListener");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onGetDetailsCallback = null;
        OnOperationStartCallback = null;
    }

}
