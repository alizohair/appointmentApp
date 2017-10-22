package com.hass.ali.doctorsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import bussines.CustomItemClickListener;

public class ScheduledateListAdapter extends RecyclerView.Adapter<ScheduledateListAdapter.MyViewHolder> {

    private List<ScheduleBean> scheduleBeanList;
    private Context ctx;
    CustomItemClickListener listener;
    public ScheduledateListAdapter(List<ScheduleBean> activityBeanList, Context ctx) {
        this.scheduleBeanList = activityBeanList;
        this.ctx=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,capacity,appointment;
        ImageButton addAppointmentBtn;
       // public ImageView profilePic;

        public MyViewHolder(View view) {
            super(view);

            this.name = (TextView) view.findViewById(R.id.name);
            this.capacity = (TextView) view.findViewById(R.id.capacity_Ed);
            this.appointment = (TextView) view.findViewById(R.id.appointment_Ed);
            this.addAppointmentBtn = (ImageButton) view.findViewById(R.id.addAppointment);


        }
    }

    @Override
    public ScheduledateListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_layout, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, AppointmentList.class);
                intent.putExtra("ScheduleBean", scheduleBeanList.get(mViewHolder.getAdapterPosition()));

                ctx.startActivity(intent);
            }
        });


        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduledateListAdapter.MyViewHolder holder, final int position) {

        final ScheduleBean scheBean = scheduleBeanList.get(position);

        holder.name.setText(scheBean.getScheduleName());
        holder.capacity.setText("Capacity:"+scheBean.getCapacity());
        holder.appointment.setText("Appointments:"+scheBean.getAppointmentCount() == null ? "0":scheBean.getAppointmentCount());
        holder.addAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,NewAppoint.class);
                intent.putExtra("ScheduleBean",scheBean);
                ctx.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return scheduleBeanList.size();
    }


}
