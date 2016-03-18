package com.blstream.kaczynska.longopsbackgroundapp;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements SecondScreenFragment.OnListFragmentInteractionListener {

    Fragment firstScreenFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            firstScreenFragment = new FirstScreenFragment();
            fragmentTransaction.add(R.id.framelayoutId, firstScreenFragment);
            fragmentTransaction.commit();
        }
    }

//    @Override
//    public void onButtonStartClick(Operation selectedOperation) {
//        SecondScreenFragment fragment = new SecondScreenFragment();
//
////        Bundle bundle = new Bundle();
////        Operation operation = values.get(uri);
////        bundle.putParcelable("selected_item", operation);
////        fragment.setArguments(bundle);
//
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.framelayoutId, fragment);
//            fragmentTransaction.addToBackStack(null).commit();
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "firstScreenFragment", firstScreenFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        firstScreenFragment = getSupportFragmentManager().getFragment(outState, "firstScreenFragment");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListFragmentInteraction(Operation operationItem) {

    }
}
