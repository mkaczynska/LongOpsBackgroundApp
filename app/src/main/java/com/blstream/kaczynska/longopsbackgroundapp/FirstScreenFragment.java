package com.blstream.kaczynska.longopsbackgroundapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    private ArrayList<Operation> listOfStartedOperations;
    private ArrayList<OperationOption> operationOptionList;
    public OperationService operationService;
    private boolean serviceStatus;
    private Context context;
    final static int[] operationTimeArray = new int[]{10, 15, 20, 25};

    public FirstScreenFragment() {
        operationOptionList = new ArrayList<>();
        listOfStartedOperations = new ArrayList<>();
        for(int i=0; i<operationTimeArray.length; i++) {
            operationOptionList.add(new OperationOption(operationTimeArray[i]));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bind to LocalService
//        if (!serviceStatus) {
            Intent intent = new Intent(context, OperationService.class);
            context.bindService(intent, workingOperation, Context.BIND_AUTO_CREATE);
            serviceStatus = true;
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unbind from the service
        if (serviceStatus) {
            context.unbindService(workingOperation);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                Operation startedOperation = new Operation(selectedOption);
                listOfStartedOperations.add(startedOperation);

//                Toast.makeText(parentView.getContext(), "Selected operation is  " + selectedOption.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner.setAdapter(operationTimeAdapter);

        Button startButton = (Button) view.findViewById(R.id.buttonStartId);
        startButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationOption selectedOperation = (OperationOption) spinner.getSelectedItem();
//                String spinnerString = selectedOperation.toString();
//                int nPos = spinner.getSelectedItemPosition();
//                Toast.makeText(context, "getSelectedItem=" + spinnerString,
//                        Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "getSelectedItemPosition=" + nPos,
//                        Toast.LENGTH_SHORT).show();
                operationService.countTime(selectedOption.getDurationTime());
            }
        });
        return view;
    }
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    ServiceConnection workingOperation = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            OperationService.LocalBinder binder = (OperationService.LocalBinder) service;
            operationService = binder.getService();
            serviceStatus = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceStatus = false;
        }
    };
}
