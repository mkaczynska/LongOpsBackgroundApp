package com.blstream.kaczynska.longopsbackgroundapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.blstream.kaczynska.longopsbackgroundapp.FirstScreenFragment.OnButtonStartClickListener} interface
 * to handle interaction events.
 * Use the {@link FirstScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnButtonStartClickListener mListener;

    public FirstScreenFragment() {
        // Required empty public constructor
    }

    public interface OnButtonStartClickListener {
        // TODO: Update argument type and name
        void onButtonStartClick(Operation operation);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstScreenFragment newInstance(String param1, String param2) {
        FirstScreenFragment fragment = new FirstScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_screen, container, false);
        final Context context = view.getContext();

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.operationTimeArray, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

//        spinner.setPrompt("@string/timePrompt");
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (initializedAdapter != parentView.getAdapter()) {
                    initializedAdapter = parentView.getAdapter();
                    return;
                }
                String selected = parentView.getItemAtPosition(position).toString();
                Toast.makeText(parentView.getContext(), "Item is " + selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Button startButton = (Button) view.findViewById(R.id.buttonStartId);
        startButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Operation selectedOperation = new Operation(1, (String) spinner.getSelectedItem());
                String spinnerString = selectedOperation.toString();
                int nPos = spinner.getSelectedItemPosition();
                Toast.makeText(context, "getSelectedItem=" + spinnerString,
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "getSelectedItemPosition=" + nPos,
                        Toast.LENGTH_SHORT).show();
                if (null != mListener) {
                    mListener.onButtonStartClick(selectedOperation);
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Operation operation) {
        if (mListener != null) {
            mListener.onButtonStartClick(operation);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonStartClickListener) {
            mListener = (OnButtonStartClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
