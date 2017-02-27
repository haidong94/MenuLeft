package com.example.dong.menuleft;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dong.menuleft.model.Types;

import java.util.ArrayList;

/**
 * Created by DONG on 08-Feb-17.
 */
public class SpinAdapter extends ArrayAdapter<Types> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Types> types;

    public SpinAdapter(Context context, int textViewResourceId,
                       ArrayList<Types> types) {
        super(context, textViewResourceId, types);
        this.context = context;
        this.types = types;
    }

    public int getCount() {
        return types.size();
    }

    public Types getItem(int position) {
        return types.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setTextColor(Color.parseColor("#000000"));
        txt.setText(types.get(position).getName()+" ");
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);

        return txt;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(types.get(position).getName());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
}