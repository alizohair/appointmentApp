package com.hass.ali.doctorsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class PatinetListAdapter extends RecyclerView.Adapter<PatinetListAdapter.MyViewHolder>{

    private List<PatientBean> patienBeanList;
    private Context ctx;
    public PatinetListAdapter(List<PatientBean> patienBeanList, Context ctx) {
        this.patienBeanList = patienBeanList;
        this.ctx=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,number;

       // public ImageView profilePic;

        public MyViewHolder(View view) {
            super(view);

            this.name = (TextView) view.findViewById(R.id.patient_name);
            this.number = (TextView) view.findViewById(R.id.number);

        }
    }

    @Override
    public PatinetListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_screen_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatinetListAdapter.MyViewHolder holder, final int position) {

        final PatientBean scheBean = patienBeanList.get(position);

        holder.name.setText(scheBean.getPatientName());
        holder.number.setText(scheBean.getContactNo());

    }

    @Override
    public int getItemCount() {
        return patienBeanList.size();
    }


    public void updateList(List<PatientBean> patienBeanList) {
        this.patienBeanList = patienBeanList;
        notifyDataSetChanged();
    }

}
