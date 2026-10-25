package com.example.complaintbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ComplaintAdapter extends BaseAdapter {

    private final ArrayList<Complaint> complaints;
    private final LayoutInflater inflater;

    public ComplaintAdapter(Context context, ArrayList<Complaint> complaints) {
        this.complaints = complaints;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return complaints.size();
    }

    @Override
    public Object getItem(int position) {
        return complaints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return complaints.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_complaint, parent, false);
            holder = new ViewHolder();
            holder.complaintIdText = convertView.findViewById(R.id.complaintIdText);
            holder.titleText = convertView.findViewById(R.id.titleText);
            holder.categoryText = convertView.findViewById(R.id.categoryText);
            holder.statusText = convertView.findViewById(R.id.statusText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Complaint complaint = complaints.get(position);
        holder.complaintIdText.setText(complaint.getComplaintId());
        holder.titleText.setText(complaint.getTitle());
        holder.categoryText.setText(complaint.getCategory());
        holder.statusText.setText(complaint.getStatus());

        return convertView;
    }

    private static class ViewHolder {
        TextView complaintIdText;
        TextView titleText;
        TextView categoryText;
        TextView statusText;
    }
}
