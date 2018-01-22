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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import bussines.CustomItemClickListener;
import bussines.HomeHandler;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {

    private List<appointmentBean> appointmentBeanList;
    private Context ctx;
    CustomItemClickListener listener;
    public AppointmentListAdapter(List<appointmentBean> activityBeanList, Context ctx) {
        this.appointmentBeanList = activityBeanList;
       this.ctx=ctx;
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


    /**
     * calls on left swap
     * @param viewHolder
     * @param recyclerView
     * @param currentStatus
     * @throws Exception
     */
    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView, final String currentStatus) throws Exception {
        final int adapterPosition = viewHolder.getAdapterPosition();


        final HomeHandler homeHandler = new HomeHandler();
        String changeStatus = null;

        String TokenNo = null;
        String TokenDate = null;
        String AvailedDate = null;
        String reorderDate = null;
        String DateTime;

        DateFormat dateFormat = new SimpleDateFormat("dd--MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        DateTime = dateFormat.format(cal.getTime());
        //System.out.println(dateFormat.format(cal.getTime()));


        if(currentStatus.equalsIgnoreCase("availed")){

            changeStatus = "tokened";


        }else if(currentStatus.equalsIgnoreCase("tokened")){

            changeStatus = "pending";
        }
        else if(currentStatus.equalsIgnoreCase("tokened")){

            changeStatus = "deleted";
        }



        final appointmentBean bean = appointmentBeanList.get(adapterPosition);
        homeHandler.changeStatus(bean.appointment_date,String.valueOf(bean.appointment_id),String.valueOf(bean.schedule_id),changeStatus,null,null,null,null);
        // appointmentBeanList.remove(adapterPosition);



        Snackbar snackbar = Snackbar
                .make(recyclerView, "STATUS CHANGED", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {
                            homeHandler.changeStatus(bean.appointment_date,String.valueOf(bean.appointment_id),String.valueOf(bean.schedule_id),currentStatus,null,null,null,null);

                            refreshList(String.valueOf(bean.schedule_id),bean.appointment_date,currentStatus);


                        } catch (Exception e) {


                            e.printStackTrace();
                        }

                    }
                });
        snackbar.show();

        refreshList(String.valueOf(bean.schedule_id),bean.appointment_date,currentStatus);





    }


    /**
     * method call on right swap
     * @param viewHolder
     * @param recyclerView
     * @param currentStatus
     * @throws Exception
     */
 public void onChangeStatus(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView, final String currentStatus) throws Exception
 {
        final int adapterPosition = viewHolder.getAdapterPosition();
     //   final Photo mPhoto = photos.get(adapterPosition);

     final HomeHandler homeHandler = new HomeHandler();
     String changeStatus = null;

     String TokenNo = null;
     String TokenDate = null;
     String AvailedDate = null;
     String reorderDate = null;
     String DateTime;

     DateFormat dateFormat = new SimpleDateFormat("dd--MM-yyyy HH:mm:ss");
     Calendar cal = Calendar.getInstance();

     DateTime = dateFormat.format(cal.getTime());

     if(currentStatus.equalsIgnoreCase("pending")){
         TokenNo = String.valueOf(homeHandler.getNewToken());
         changeStatus = "tokened";
         TokenDate = DateTime;


     }else if(currentStatus.equalsIgnoreCase("tokened")){
         AvailedDate = DateTime;
         changeStatus = "availed";
     }

    // final HomeHandler homeHandler = new HomeHandler();


     final appointmentBean bean = appointmentBeanList.get(adapterPosition);
     homeHandler.changeStatus(bean.appointment_date,String.valueOf(bean.appointment_id),String.valueOf(bean.schedule_id),changeStatus,TokenNo,TokenDate,AvailedDate,reorderDate);
    // appointmentBeanList.remove(adapterPosition);



     Snackbar snackbar = Snackbar
             .make(recyclerView, "STATUS CHANGED", Snackbar.LENGTH_LONG)
             .setAction("UNDO", new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {


                     try {
                         homeHandler.changeStatus(bean.appointment_date,String.valueOf(bean.appointment_id),String.valueOf(bean.schedule_id),currentStatus,null,null,null,null);

                         refreshList(String.valueOf(bean.schedule_id),bean.appointment_date,currentStatus);


                     } catch (Exception e) {


                         e.printStackTrace();
                     }

                 }
             });
     snackbar.show();

     refreshList(String.valueOf(bean.schedule_id),bean.appointment_date,currentStatus);





 }


    public void refreshList(String scheduleID,String date,String status) throws Exception {




       // ((AppointmentTab)ctx).refreshTabs();

        HomeHandler homeHandler = new HomeHandler();

        appointmentBeanList = homeHandler.getAppointmentList(scheduleID,date,status);


        notifyDataSetChanged();





    }





}
