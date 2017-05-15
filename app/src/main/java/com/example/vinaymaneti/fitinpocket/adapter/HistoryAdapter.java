package com.example.vinaymaneti.fitinpocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vinaymaneti.fitinpocket.R;
import com.example.vinaymaneti.fitinpocket.model.HistoryModel;
import com.example.vinaymaneti.fitinpocket.model.ProfileModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    // Store a member variable for the contacts
    private List<ProfileModel> mProfileModelsList;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public HistoryAdapter(Context context, List<ProfileModel> profileModels) {
        mProfileModelsList = profileModels;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        mContext = parent.getContext();
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        ProfileModel profileModel = mProfileModelsList.get(position);
        if (mProfileModelsList.size() <= 0) {
            holder.noHistory.setVisibility(View.VISIBLE);
            holder.actual_date.setVisibility(View.INVISIBLE);
            holder.actual_weight.setVisibility(View.INVISIBLE);
        } else {
            holder.actual_date.setText(profileModel.getDate_time_created());
            holder.actual_weight.setText(String.valueOf(profileModel.getCurrent_weight()));
        }
    }

    @Override
    public int getItemCount() {
        return mProfileModelsList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView actual_date, actual_weight, noHistory;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            actual_date = (TextView) itemView.findViewById(R.id.actual_date);
            actual_weight = (TextView) itemView.findViewById(R.id.actual_weight);
            noHistory = (TextView) itemView.findViewById(R.id.noHistory);

        }
    }
}
