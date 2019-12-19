package com.example.pjt_student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainListAdapter extends ArrayAdapter<StudentVO> {
    Context context;
    ArrayList<StudentVO> datas;
    int resid;

    public MainListAdapter(Context context, int resid, ArrayList<StudentVO> datas) {
        super(context, resid);
        this.context = context;
        this.datas = datas;
        this.resid = resid;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override



    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resid, null);

            MainListViewWrapper wrapper = new MainListViewWrapper(convertView);
            convertView.setTag(wrapper);
        }

        MainListViewWrapper wrapper = (MainListViewWrapper) convertView.getTag();
        ImageView studentImageView = wrapper.studentImageView;
        ImageView photoImageView = wrapper.photoImageView;
        TextView nameView = wrapper.nameView;

        final StudentVO vo = datas.get(position);
        nameView.setText(vo.name);


        return convertView;
    }
}
