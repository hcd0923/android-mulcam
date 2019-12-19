package com.example.pjt_student;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

//    Button testBtn;
    ListView listView;
    ArrayList<StudentVO> datas;
    ImageView addBtn;

    double intiTime;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testBtn =findViewById(R.id.main_test_btn);
        listView =findViewById(R.id.main_list);
        addBtn = findViewById(R.id.main_btn);

//        testBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        addBtn.setOnClickListener(this);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_student order by name" , null);
        datas = new ArrayList<>();
        while (cursor.moveToNext()){
            StudentVO studentVO = new StudentVO();
            studentVO.id = cursor.getInt(0);
            studentVO.name = cursor.getString(1);
            studentVO.email = cursor.getColumnName(2);
            studentVO.phone = cursor.getString(3);
            studentVO.photo = cursor.getString(4);
            studentVO.memo = cursor.getString(5);
            datas.add(studentVO);
        }

        db.close();
        MainListAdapter adapter=new MainListAdapter(this, R.layout.main_list_item,
                datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v==addBtn){
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
//        else if(v==listView) {
//            Intent intent = new Intent(this, DetailActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(System.currentTimeMillis() - intiTime > 30000) {
                Toast.makeText(this, R.string.main_back_end, Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            intiTime = System.currentTimeMillis();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //SearchView를 포함한 MenuItem 획득하고.. 그 객체에서 SearchView 획득..
        MenuItem menuItem=menu.findItem(R.id.menu_main_search);
        searchView=(SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint(getResources().getString(R.string.main_search_hint));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(queryListener);

        return super.onCreateOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener queryListener=new SearchView.OnQueryTextListener() {
        //검색때문에 키보드가 올라오면 우측하단 키가.. 검색키가 된다..
        //유저가 검색키를 누른순간...
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            Log.d("kkang", query);
            return false;
        }
        //한자한자 입력시마다..
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
}
