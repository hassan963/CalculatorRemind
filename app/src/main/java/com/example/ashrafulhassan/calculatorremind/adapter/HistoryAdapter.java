package com.example.ashrafulhassan.calculatorremind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashrafulhassan.calculatorremind.R;
import com.example.ashrafulhassan.calculatorremind.model.History;

import java.util.ArrayList;

/**
 * Created by Hassan M Ashraful on 4/24/2018.
 * Jr Software Developer
 * Innovizz Technology
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<History> arrayList;
    private Context context;

    public HistoryAdapter(ArrayList<History> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

        Log.v("adapter:::   ","adapter called");

    }

    public void updateReceiptsList(ArrayList<History> newlist) {
        arrayList.clear();
        arrayList.addAll(newlist);
        this.notifyDataSetChanged();
    }


    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_each_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {

        holder.expressionTextView.setText(arrayList.get(position).getExpression());
        holder.resultTextView.setText(arrayList.get(position).getResult());
        holder.remarkTextView.setText(arrayList.get(position).getRemarks());
        holder.timeTextView.setText(arrayList.get(position).getTime());

        Log.v("adapter",arrayList.get(position).getRemarks());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expressionTextView;
        private TextView resultTextView;
        private TextView remarkTextView, timeTextView;

        public ViewHolder(View view) {
            super(view);

            expressionTextView = view.findViewById(R.id.expressionTextView);
            resultTextView = view.findViewById(R.id.resultTextView);
            remarkTextView = view.findViewById(R.id.remarkTextView);
            timeTextView = view.findViewById(R.id.timeTextView);

        }
    }
}


