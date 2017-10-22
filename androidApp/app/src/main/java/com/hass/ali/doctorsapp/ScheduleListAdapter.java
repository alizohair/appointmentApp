package com.hass.ali.doctorsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.MyViewHolder>{

    private List<ScheduleBean> scheduleBeanList;
    private Context ctx;
    public ScheduleListAdapter(List<ScheduleBean> activityBeanList, Context ctx) {
        this.scheduleBeanList = activityBeanList;
        this.ctx=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,capacity,endTime,startTime;
        ImageButton addAppointmentBtn;
       // public ImageView profilePic;

        public MyViewHolder(View view) {
            super(view);

            this.name = (TextView) view.findViewById(R.id.name);
            this.capacity = (TextView) view.findViewById(R.id.capacity_Ed);
            this.endTime = (TextView) view.findViewById(R.id.end_time);
            this.startTime = (TextView) view.findViewById(R.id.startTime);
            this.addAppointmentBtn = (ImageButton) view.findViewById(R.id.addAppointment);

        }
    }

    @Override
    public ScheduleListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_screen_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleListAdapter.MyViewHolder holder, final int position) {

        final ScheduleBean scheBean = scheduleBeanList.get(position);

        holder.name.setText(scheBean.getScheduleName());
        holder.capacity.setText("Capacity: "+scheBean.getCapacity());
        holder.endTime.setText("End Time: "+scheBean.getScheduleTimeTo());
        holder.startTime.setText("Start Time: "+scheBean.getScheduleTimeFrom());



    }

    @Override
    public int getItemCount() {
        return scheduleBeanList.size();
    }


}
