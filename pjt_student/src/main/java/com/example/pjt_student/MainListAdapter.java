package com.example.pjt_student;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

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



    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resid, null);

            MainListViewWrapper wrapper = new MainListViewWrapper(convertView);
            convertView.setTag(wrapper);
        }

        MainListViewWrapper wrapper = (MainListViewWrapper) convertView.getTag();
        ImageView studentImageView = wrapper.studentImageView;
        ImageView phoneImageView = wrapper.phoneImageView;
        TextView nameView = wrapper.nameView;


        final StudentVO vo = datas.get(position);
        nameView.setText(vo.name);

        if(vo.photo != null && !vo.photo.equals("")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize= 10;
            Bitmap bitmap = BitmapFactory.decodeFile(vo.photo, options);
            if(bitmap != null){
                studentImageView.setImageBitmap(bitmap);
            }
        } else {
            studentImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_student_small, null));

        }

        studentImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View dialogRoot=inflater.inflate(R.layout.dialog_student_image, null);
                ImageView dialogImageView=dialogRoot.findViewById(R.id.dialog_image);

                if(vo.photo != null && !vo.photo.equals("")){
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=10;//10분의 1로 줄여서 로딩한다..
                    Bitmap bitmap= BitmapFactory.decodeFile(vo.photo, options);
                    if(bitmap != null){
                        dialogImageView.setImageBitmap(bitmap);
                    }
                }else {
                    dialogImageView.setImageDrawable(ResourcesCompat.getDrawable(
                            context.getResources(), R.drawable.ic_student_large, null));
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setView(dialogRoot);
                AlertDialog dialog=builder.create();


                dialog.show();
            }
        });

        phoneImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(vo.phone != null && !vo.phone.equals("")){
                    //CallApp 의 actiity 를 intent로.. 전화번호 넘기면서..
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+vo.phone));
                    context.startActivity(intent);
                }else {
                    Toast toast=Toast.makeText(context, R.string.main_list_phone_error,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return convertView;
    }


}
