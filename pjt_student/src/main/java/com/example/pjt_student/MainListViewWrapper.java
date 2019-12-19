package com.example.pjt_student;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListViewWrapper {
    ImageView studentImageView;
    TextView nameView;
    ImageView photoImageView;

    public MainListViewWrapper(View root) {
        studentImageView = root.findViewById(R.id.main_item_student_image);
        nameView = root.findViewById(R.id.main_item_name);
        photoImageView = root.findViewById(R.id.main_item_contact);
    }
}