package com.hass.ali.doctorsapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import bussines.CustomItemClickListener;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {

    private List<appointmentBean> appointmentBeanList;
   // private Context ctx;
    CustomItemClickListener listener;
    public AppointmentListAdapter(List<appointmentBean> activityBeanList, Context ctx) {
        this.appointmentBeanList = activityBeanList;
     //   this.ctx=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,token,number;
        //ImageButton addAppointmentBtn;
       // public ImageView profilePic;

        public MyViewHolder(View view) {
            super(view);

            this.name = (TextView) view.findViewById(R.id.name);
            this.token = (TextView) view.findViewById(R.id.token);
            this.number = (TextView) view.findViewById(R.id.number);

        }
    }

    @Override
    public AppointmentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_layout, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);



        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(AppointmentListAdapter.MyViewHolder holder, final int position) {

        final appointmentBean scheBean = appointmentBeanList.get(position);
        final PatientBean patientbean = scheBean.getPatientBean();

        holder.name.setText(patientbean.patientName+"");
        holder.token.setText(scheBean.getToken_no());
        holder.number.setText(patientbean.contactNo+"");

    }

    @Override
    public int getItemCount() {
        return appointmentBeanList.size();
    }
    public void remove(int position) {
        if (position < 0 || position >= appointmentBeanList.size()) {
            return;
        }
        appointmentBeanList.remove(position);
        notifyItemRemoved(position);
    }



    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
     //   final Photo mPhoto = photos.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, "PHOTO REMOVED", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int mAdapterPosition = viewHolder.getAdapterPosition();

                    }
                });
        snackbar.show();
        appointmentBeanList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);

    }



}
