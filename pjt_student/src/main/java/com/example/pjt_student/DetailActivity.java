package com.example.pjt_student;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    ImageView studentImageView;
    TextView nameView;
    TextView phoneView;
    TextView emailView;
    TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initData();
        initTab();
    }

    private void initTab() {
        host = findViewById(R.id.host);
        host.setup();


        makeTabs("tab1", "score", R.id.detail_score_list);
        makeTabs("tab2", "chart", R.id.detail_score_chart);
        makeTabs("tab3", "add", R.id.detail_score_add);
        makeTabs("tab3", "memo", R.id.detail_score_memo);

    }

    private void makeTabs(String tabSpecName, String indecator, int viewsId) {
        TabHost.TabSpec spec = host.newTabSpec(tabSpecName);
        spec.setIndicator(indecator);
        spec.setContent(viewsId);
        host.addTab(spec);
    }

    private void initData() {
        studentImageView = findViewById(R.id.detail_student_imange);
        nameView = findViewById(R.id.detail_name);
        phoneView = findViewById(R.id.detail_phone);
        emailView = findViewById(R.id.detail_email);
    }


}
