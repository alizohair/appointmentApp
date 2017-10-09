package adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hass.ali.doctorsapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.hass.ali.doctorsapp.LoginActivity.context;

/**
 * Created by ali.zohair on 9/12/2017.
 */

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    public int selected;
    public int currentdate;
    Context ctx;
  //  private List<EventObjects> allEvents;
    public GridAdapter(Context ctx, List<Date> monthlyDates, Calendar currentDate,int selected) {
        super(ctx, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.selected = selected;
        this.currentdate = selected;
        this.ctx = ctx;
       // this.allEvents = allEvents;
        mInflater = LayoutInflater.from(ctx);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        TextView dateView = (TextView)view.findViewById(R.id.calendar_date_id);
        if(displayMonth == currentMonth && displayYear == currentYear){
            //view.setBackgroundColor(Color.parseColor("#FF5733"));
        }else{
            dateView.setEnabled(false);
            dateView.setClickable(false);
            //view.setBackgroundColor(Color.parseColor("#cccccc"));
        }


        if(dateView.isEnabled() && dayValue == selected){
            view.setBackgroundColor(Color.parseColor("#BDBDBD"));

        } else if(currentdate!=dayValue){
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }else
        {

            view.setBackgroundColor(Color.parseColor("#ededed"));
        }

        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        //Add events to the calendar
        TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);
        Calendar eventCalendar = Calendar.getInstance();
       /* for(int i = 0; i < allEvents.size(); i++){
            eventCalendar.setTime(allEvents.get(i).getDate());
            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                eventIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
            }
        }*/
        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    public void setSelected(int position){
        this.selected = position;
        notifyDataSetChanged();


    }
}