package com.example.pjt_student;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    ImageView studentImageView;
    TextView nameView;
    TextView phoneView;
    TextView emailView;
    TabHost host;

    MyView scoreView;



    int studentId=1;

    TextView addScoreView;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnAdd, btnBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initData();
        initTab();
        initAddScore();
    }

    private void initAddScore() {
        btn0 = (Button) findViewById(R.id.key_0);
        btn1 = (Button) findViewById(R.id.key_1);
        btn2 = (Button) findViewById(R.id.key_2);
        btn3 = (Button) findViewById(R.id.key_3);
        btn4 = (Button) findViewById(R.id.key_4);
        btn5 = (Button) findViewById(R.id.key_5);
        btn6 = (Button) findViewById(R.id.key_6);
        btn7 = (Button) findViewById(R.id.key_7);
        btn8 = (Button) findViewById(R.id.key_8);
        btn9 = (Button) findViewById(R.id.key_9);
        btnBack = (Button) findViewById(R.id.key_back);
        btnAdd = (Button) findViewById(R.id.key_add);

        addScoreView = (TextView) findViewById(R.id.key_edit);

        btn0.setOnClickListener(addScoreListener);
        btn1.setOnClickListener(addScoreListener);
        btn2.setOnClickListener(addScoreListener);
        btn3.setOnClickListener(addScoreListener);
        btn4.setOnClickListener(addScoreListener);
        btn5.setOnClickListener(addScoreListener);
        btn6.setOnClickListener(addScoreListener);
        btn7.setOnClickListener(addScoreListener);
        btn8.setOnClickListener(addScoreListener);
        btn9.setOnClickListener(addScoreListener);
        btnBack.setOnClickListener(addScoreListener);
        btnAdd.setOnClickListener(addScoreListener);

    }

    View.OnClickListener addScoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==btnAdd){
                Log.d("addScoreListener", "btnAdd");
                String score=addScoreView.getText().toString();
                long date=System.currentTimeMillis();

                DBHelper helper=new DBHelper(DetailActivity.this);
                SQLiteDatabase db=helper.getWritableDatabase();
                db.execSQL("insert into tb_score (student_id, date, score) values (?,?,?)",
                        new String[]{String.valueOf(studentId), String.valueOf(date), score});
                db.close();

                //화면은???
                //목록 탭 으로 자동 화면 전환...
                addScoreView.setText("0");
                host.setCurrentTab(0);
                scoreView.setScore(Integer.parseInt(score));
            }else if(v==btnBack){
                String score=addScoreView.getText().toString();
                if(score.length()==1){
                    addScoreView.setText("0");
                }else {
                    String newScore=score.substring(0, score.length()-1);
                    addScoreView.setText(newScore);
                }
            }else {
                //현재 이벤트 발생 버튼의 문자열 추출..
                Button btn=(Button)v;
                String txt=btn.getText().toString();

                String score=addScoreView.getText().toString();
                if(score.equals("0")){
                    addScoreView.setText(txt);
                }else {
                    String newScore=score+txt;
                    int intScore=Integer.parseInt(newScore);
                    if(intScore>100){
                        Toast toast=Toast.makeText(DetailActivity.this,
                                R.string.read_add_score_over_score, Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        addScoreView.setText(newScore);
                    }
                }
            }
        }
    };

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
        scoreView = findViewById(R.id.detail_score);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_student where _id = ?", new String[]{String.valueOf(studentId)});

        String photo = null;

        if(cursor.moveToFirst()){
            nameView.setText(cursor.getString(1));
            emailView.setText(cursor.getString(2));
            phoneView.setText(cursor.getString(3));
            photo = cursor.getString(4);

            int score = 0;

            Cursor scoreCursor = db.rawQuery("select score from tb_score where student_id = ? order by date desc limit 1",
                    new String[]{String.valueOf(studentId)});

            if(scoreCursor.moveToFirst()){
                score=scoreCursor.getInt(0);

            }
            Log.d("score", String.valueOf(score));
            scoreView.setScore(score);

        }

        db.close();

    }


}
