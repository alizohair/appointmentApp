package com.hass.ali.doctorsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ScheduledateListAdapter extends RecyclerView.Adapter<ScheduledateListAdapter.MyViewHolder>{

    private List<ScheduleBean> scheduleBeanList;
    private Context ctx;
    public ScheduledateListAdapter(List<ScheduleBean> activityBeanList, Context ctx) {
        this.scheduleBeanList = activityBeanList;
        this.ctx=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,capacity,appointment;
       // public ImageView profilePic;

        public MyViewHolder(View view) {
            super(view);

            this.name = (TextView) view.findViewById(R.id.schedule_name);
            this.capacity = (TextView) view.findViewById(R.id.capacity_Ed);
            this.appointment = (TextView) view.findViewById(R.id.appointment_Ed);


        }
    }

    @Override
    public ScheduledateListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduledateListAdapter.MyViewHolder holder, final int position) {

        ScheduleBean scheBean = scheduleBeanList.get(position);

        holder.name.setText(scheBean.getScheduleName());
        holder.capacity.setText("Capacity:"+scheBean.getCapacity());
        holder.appointment.setText("Appointments:"+scheBean.getAppointmentCount());



    }

    @Override
    public int getItemCount() {
        return scheduleBeanList.size();
    }


}
