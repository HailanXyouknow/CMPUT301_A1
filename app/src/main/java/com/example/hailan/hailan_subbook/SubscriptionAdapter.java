package com.example.hailan.hailan_subbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hailan on 2018-02-03.
 */

public class SubscriptionAdapter extends ArrayAdapter{
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Subscription> subscriptions;

    public SubscriptionAdapter(Context context, int resource, List<Subscription> subscriptions) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.subscriptions = subscriptions;
    }

    /* The following is learnt from Tim Buchalka's android course, Copyright © 2018 Udemy, Inc.*/
    @Override
    public int getCount() {
        return subscriptions.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
        }
        TextView subName = (TextView) convertView.findViewById(R.id.subName); //note view. - find these in the views in the constraint layout
        TextView subDate = (TextView) convertView.findViewById(R.id.subDate);
        TextView subCost = (TextView) convertView.findViewById(R.id.subCost);
        TextView subComment = (TextView) convertView.findViewById(R.id.subComment);

        Subscription currentSub = subscriptions.get(position);

        subName.setText(currentSub.getName());
        subDate.setText("Date: " +currentSub.getDate());
        subCost.setText("$" + currentSub.getMonthlyCharge());
        subComment.setText("Comment: " + currentSub.getComment());

        return convertView;
    }
}
