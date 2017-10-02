package com.hass.ali.doctorsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hp on 9/29/2017.
 */
public class DepartmentArrayAdapter extends ArrayAdapter<PatientBean> {
    private final Context mContext;
    private final ArrayList<PatientBean> mDepartments;
    private final ArrayList<PatientBean> mDepartments_All;
    private final ArrayList<PatientBean> mDepartments_Suggestion;
    private final int mLayoutResourceId;

    public DepartmentArrayAdapter(Context context, int resource, ArrayList<PatientBean> departments) {
        super(context, resource, departments);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mDepartments = new ArrayList<>(departments);
        this.mDepartments_All = new ArrayList<>(departments);
        this.mDepartments_Suggestion = new ArrayList<>();
    }

    public int getCount() {
        return mDepartments.size();
    }

    public PatientBean getItem(int position) {
        return mDepartments.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            PatientBean department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textView);
            TextView number = (TextView) convertView.findViewById(R.id.textView1);
            name.setText(department.patientName);
            number.setText(department.contactNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((PatientBean) resultValue).patientName;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    mDepartments_Suggestion.clear();
                    for (PatientBean department : mDepartments_All) {
                        if (department.patientName.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            mDepartments_Suggestion.add(department);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mDepartments_Suggestion;
                    filterResults.count = mDepartments_Suggestion.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDepartments.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    ArrayList<?> result = (ArrayList<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof PatientBean) {
                            mDepartments.add((PatientBean) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mDepartments.addAll(mDepartments_All);
                }
                notifyDataSetChanged();
            }
        };
    }
}