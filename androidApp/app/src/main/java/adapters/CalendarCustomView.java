package adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hass.ali.doctorsapp.HomeScreen;
import com.hass.ali.doctorsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import bussines.DateCapacity;
import bussines.HomeHandler;

/**
 * Created by ali.zohair on 9/12/2017.
 */

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
   // private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    public Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    public GridAdapter mAdapter;
    private OnLoadingButtonClickListener<Date> mONOnLoadingButtonClickListener;
  //  private DatabaseQuery mQuery;
    public CalendarCustomView(Context context) {
        super(context);
    }
    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
    }
    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView)view.findViewById(R.id.previous_month);
        nextButton = (ImageView)view.findViewById(R.id.next_month);
        currentDate = (TextView)view.findViewById(R.id.display_current_date);
       // addEventButton = (Button)view.findViewById(R.id.add_calendar_event);
        calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);
    }
    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setGridCellClickEvents(){



        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mONOnLoadingButtonClickListener != null){
                    mONOnLoadingButtonClickListener.onLoadingButtonClickListener((Date) calendarGridView.getAdapter().getItem(position));
                    //Toast.makeText(context, "Clicked " + calendarGridView.getAdapter().getItem(position), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void setUpCalendarAdapter(){
        List<Date> dayValueInCells = new ArrayList<Date>();
     //   mQuery = new DatabaseQuery(context);
      //  List<EventObjects> mEvents = mQuery.getAllFutureEvents();
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);

        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);


        String dateFrom = currentYear+"-"+currentMonth+"-01";
        String dateTo = currentYear+"-"+currentMonth+"-31";

        HomeHandler homeHandler = new HomeHandler();
        HashMap<String, Boolean> dateScapacity = null;
        try {
            dateScapacity = homeHandler.getMonthCapacity(dateFrom,dateTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter = new GridAdapter(context, dayValueInCells, cal,cal.get(Calendar.DATE),dateScapacity);
        calendarGridView.setAdapter(mAdapter);
    }

    public void mONOnLoadingButtonClickListener(OnLoadingButtonClickListener<Date> mONOnLoadingButtonClickListener) {
        this.mONOnLoadingButtonClickListener = mONOnLoadingButtonClickListener;
    }

    public interface OnLoadingButtonClickListener<data>{
        void onLoadingButtonClickListener(Date data);
    }

}