package com.blstream.kaczynska.longopsbackgroundapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class OperationTimeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OperationOption> operationOptionList;
    OperationOption selectedOption;
    ArrayList<Operation> listOfStartedOperations;

    public OperationTimeAdapter(Context context, ArrayList<OperationOption> options) {
        operationOptionList = options;
        this.context = context;
        listOfStartedOperations = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {
        ViewHolderOperation viewHolder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.operation_option, parent, false);
            viewHolder = new ViewHolderOperation(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolderOperation) convertView.getTag();
        }
        OperationOption operationOption = operationOptionList.get(position);

        if (operationOption != null) {

            viewHolder.operationOptionTextView.setText(operationOption.toString());
            viewHolder.operationOptionTextView.setTag(operationOption.getId());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return operationOptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return operationOptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return operationOptionList.get(position).getId();
    }

    static class ViewHolderOperation {
        public final TextView operationOptionTextView;
        public final View mView;

        public ViewHolderOperation(View view) {
            mView = view;
            this.operationOptionTextView = (TextView) view.findViewById(R.id.operationOptionId);
        }

        @Override
        public String toString() {
            return super.toString() + " " + operationOptionTextView.getText() + " ";
        }
    }
}


