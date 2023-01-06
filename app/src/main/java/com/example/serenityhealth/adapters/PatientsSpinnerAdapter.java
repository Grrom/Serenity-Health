package com.example.serenityhealth.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serenityhealth.R;
import com.example.serenityhealth.models.PatientModel;

import java.util.List;

public class PatientsSpinnerAdapter extends ArrayAdapter<PatientModel> {

    LayoutInflater flater;

    public PatientsSpinnerAdapter(Activity context, int resouceId, List<PatientModel> list) {

        super(context, resouceId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        PatientModel rowItem = getItem(position);

        viewHolder holder;
        View rowview = convertView;

        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.patient_item_spinner, null, false);

            holder.name = (TextView) rowview.findViewById(R.id.patient_name);
            holder.image = (ImageView) rowview.findViewById(R.id.patient_image);
            holder.section = (TextView) rowview.findViewById(R.id.patient_section);
            rowview.setTag(holder);

        } else {
            holder = (viewHolder) rowview.getTag();
        }
        holder.image.setImageResource(rowItem.getImage());
        holder.name.setText(rowItem.getFullName());
        holder.section.setText(rowItem.getSection());

        return rowview;
    }

    private class viewHolder {
        TextView name;
        ImageView image;
        TextView section;
    }
}
