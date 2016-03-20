package com.blstream.kaczynska.longopsbackgroundapp;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.blstream.kaczynska.longopsbackgroundapp.FirstScreenFragment.OnGetDetailsButtonPressedListener;
import com.blstream.kaczynska.longopsbackgroundapp.FirstScreenFragment.OnOperationStartButtonPressedListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnGetDetailsButtonPressedListener, OnOperationStartButtonPressedListener {


    public final static String RECEIVEMSG = "onReceive";
    public OperationService operationService;
    public Bundle secondFragmentBundle;
    String currentFragment;
    Fragment fragment;
    private boolean serviceStatus;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the retained fragment on activity restarts
//        currentFragment = "FirstScreenFragment";
        if (savedInstanceState == null) {
            fragment = new FirstScreenFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.framelayoutId, fragment, "FirstScreenFragment");
            fragmentTransaction.commit();
        }
//        } else {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragment = fragmentManager.findFragmentByTag(currentFragment);
//        }

    }

    @Override
    protected void onDestroy() {
//         Unbind from the service
        if (serviceStatus) {
            unbindService(workingOperation);
            serviceStatus = false;
        }
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        // Bind to LocalService
        Intent intent = new Intent(this, OperationService.class);
        bindService(intent, workingOperation, Context.BIND_AUTO_CREATE);
        serviceStatus = true;

        super.onStart();
    }

    @Override
    public void OnGetDetailsButtonPressed(ArrayList<Operation> startedOperationList) {

        SecondScreenFragment secondScreenFragment = new SecondScreenFragment();

        secondFragmentBundle = new Bundle();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("startedOperationList", startedOperationList);
        secondFragmentBundle.putBundle("startedOperationList", bundle);

        secondScreenFragment.setArguments(secondFragmentBundle);

        for (Operation operation : startedOperationList) {
            operationService.countTime(operation);
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutId, secondScreenFragment, "SecondScreenFragment");
        fragmentTransaction.addToBackStack(null).commit();
    }

//    @Override
//    public void onBackPressed() {
//
//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getFragmentManager().popBackStack();
//        }
//
//    }

    @Override
    public void OnOperationStartButtonPressed(Operation operation) {
        startService(new Intent(this, OperationService.class));
//        operationService.countTime(operation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        if (fragment instanceof FirstScreenFragment) {
            getSupportFragmentManager().putFragment(outState, "FirstScreenFragment", fragment);
//        }
//        else {
//            getSupportFragmentManager().putFragment(outState, "SecondScreenFragment", fragment);
//        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        fragment = getSupportFragmentManager().getFragment(outState, "FirstScreenFragment");
        super.onSaveInstanceState(outState);
    }

}

