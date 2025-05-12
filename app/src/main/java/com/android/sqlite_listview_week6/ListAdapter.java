package com.android.sqlite_listview_week6;

import android.os.Bundle;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
public class ListAdapter extends BaseAdapter {
    Context context;
    private final String [] names;
    private final String [] id;
    private final int [] images;
    public ListAdapter(Context context, String [] names, String [] id, int [] images){
//super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.names = names;
        this.id = id;
        this.images = images;
    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.student_name);
            viewHolder.txtID = (TextView) convertView.findViewById(R.id.student_id);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtName.setText(names[position]);
        viewHolder.txtID.setText("MSSV: " + id[position]);
        viewHolder.icon.setImageResource(images[position]);
        return convertView;
    }
    private static class ViewHolder {
        TextView txtName;
        TextView txtID;
        ImageView icon;
    }
}
