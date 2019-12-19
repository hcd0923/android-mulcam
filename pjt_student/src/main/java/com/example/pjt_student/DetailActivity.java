package com.example.pjt_student;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    ListView listView;

    ArrayList<HashMap<String, String>> scoreList;

    SimpleAdapter simpleAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initData();
        initTab();
        initAddScore();
        initSpannable();
        initList();
    }

    private void initList() {
        listView = findViewById(R.id.detail_score_list);
        scoreList = new ArrayList<>();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select score, date from tb_score where student_id = ? order by date desc", new String[]{String.valueOf(studentId)});
        while (cursor.moveToNext()){
            HashMap<String, String> map = new HashMap<>();
            map.put("score", cursor.getString(0));
            Date d = new Date(Long.parseLong(cursor.getString(1)));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            map.put("date", simpleDateFormat.format(d));
            scoreList.add(map);
        }
        db.close();

        simpleAdapter= new SimpleAdapter(this, scoreList, R.layout.read_list_item, new String[]{"score", "date"}, new int[]{R.id.read_list_score, R.id.read_list_date});
        listView.setAdapter(simpleAdapter);
    }

    private void initSpannable(){
        TextView spanView=findViewById(R.id.spanView);
        TextView htmlView=findViewById(R.id.htmlView);

        //ForegroundColorSpan 등은 이  span 이 적용되어 문자열이 그  UI 로 나오면 끝....
        //URLSpan 은 span 적용 문자열이 링크 모양으로 나온다가 끝이 아니라.. 유저가 링크 클릭시
        //이벤트 처리는????? 이벤트 로직은 개발자 로직...
        //이벤트 로직을 포함한  URLSpan 상속 서브 클래스로 적용...
        URLSpan urlSpan=new URLSpan(""){
            @Override
            public void onClick(View widget) {
                Toast toast=Toast.makeText(DetailActivity.this, "more click",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        String data=spanView.getText().toString();
        Spannable spannable=(Spannable)spanView.getText();

        int pos=data.indexOf("EXID");
        while(pos > -1){
            spannable.setSpan(new ForegroundColorSpan(Color.RED), pos, pos+4,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            pos=data.indexOf("EXID", pos+1);
        }

        pos=data.indexOf("more");
        spannable.setSpan(urlSpan, pos, pos+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //아래 설정하지 않으면 이벤트 발생 안한다..
        spanView.setMovementMethod(LinkMovementMethod.getInstance());


        String html="<font color='blue'>HANI</font><br/><img src='myImage'/>";

        htmlView.setText(
                //fromHtml 함수가 문자열의 태그에 해당되는 span 적용...
                //만약 <img> 태그만 없다면.. 매개변수 하나짜리...
                Html.fromHtml(
                        html,
                        //브라우저 아니다.. 이미지는 개발자가 획득해야 한다... 획득한 이미지를
                        //<img>위치에 찍는거만 해준다..
                        new MyImageGetter(),
                        //만약 특정 태그를 개발자 알고리즘으로 돌릴려면 TagHandler 상속 적용..
                        null));
    }

    class MyImageGetter implements Html.ImageGetter{
        //fromHtml 함수에 의해 이미지 획득목적으로 자동 호출..
        //<img> 태그 10개다.. 10번 호출된다..
        //매개변수는 <img> 태그의 src 값...
        //최종 리턴되는 Drawable 을 <img> 에 찍어준다..
        @Override
        public Drawable getDrawable(String source) {
            if(source.equals("myImage")){
                //이미지는 리소스, 파일, 네트웍.. 직접 획득...
                Drawable drawable= ResourcesCompat.getDrawable(getResources(),
                        R.drawable.hani_1, null);
                //아래의 정보 설정이 안되면 이미지 나오지 않는다..
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
            //null리턴되면 에러나지 않는다.. 이미지 없다는 이야기다..
            return null;
        }
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
        makeTabs("tab3", "memo", R.id.detail_memo);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
