package com.hass.ali.doctorsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import bussines.HomeHandler;

/**
 * Created by Hp on 10/28/2017.
 */

public class PendingFragmentList extends Fragment {

    public static PendingFragmentList newInstance() {
        return new PendingFragmentList();
    }
    public PendingFragmentList(){}
    View view;
    ArrayList<appointmentBean> list;
    AppointmentListAdapter appointmentListAdapter;
    RecyclerView appointmentList;
    private static Paint p = new Paint();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_appointment_list, container, false);
        String status = "pending";

        appointmentList = (RecyclerView) view.findViewById(R.id.appointment_list);

        HomeHandler homeHandler = new HomeHandler();
        try {

            final ScheduleBean scheBean =   (ScheduleBean)  getActivity().getIntent().getSerializableExtra("ScheduleBean");
            list = homeHandler.getAppointmentList(scheBean.getScheduleID(),scheBean.getCurrentDate(),status);

            appointmentListAdapter = new AppointmentListAdapter(list,getActivity());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            appointmentList.setLayoutManager(mLayoutManager);
            appointmentList.setItemAnimator(new DefaultItemAnimator());
            appointmentList.setAdapter(appointmentListAdapter);
            setUpItemTouchHelper(appointmentList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private static void setUpItemTouchHelper(final RecyclerView mRecyclerView) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            AppointmentListAdapter adapter = (AppointmentListAdapter)mRecyclerView.getAdapter();
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return true;
            }


            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

//16 == swap left  ,   32 swap right
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();


                if(swipeDir == 16){

                    try {
                        adapter.onItemRemove(viewHolder, mRecyclerView,"pending");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(swipeDir == 32){
                    try {
                        adapter.onChangeStatus(viewHolder, mRecyclerView,"pending");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


             //   adapter.remove(swipedPosition);
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#9E9E9E"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(ApplicationClass.getContext().getResources(), R.drawable.btn_check_on_holo_light);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#CFD8DC"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(ApplicationClass.getContext().getResources(), R.drawable.btn_check_on_holo_light);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
























        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
