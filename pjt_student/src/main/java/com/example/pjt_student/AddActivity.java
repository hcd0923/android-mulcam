package com.example.pjt_student;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    EditText nameView;
    EditText phoneView;
    EditText emailView;
    EditText memoView;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameView = findViewById(R.id.add_name);
        phoneView = findViewById(R.id.add_Phone);
        emailView = findViewById(R.id.add_email);
        memoView = findViewById(R.id.add_Memo);
        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = nameView.getText().toString();
        String phone = phoneView.getText().toString();
        String email = emailView.getText().toString();
        String memo = memoView.getText().toString();

        if(name == null || name.equals("")){
            Toast toast = Toast.makeText(this, R.string.add_name_null, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into tb_student (name, email, phone, memo) values (?,?,?,?)", new String[]{name, email, phone, memo});
            db.close();
        }

//        Intent intent = new Intent(this, AddActivity.class);
//        startActivity(intent);
//        위처럼 개발해도 되나 하지 않는다. 이유는 18장에서
//        Intent에 의해 화면전환 시키지 않는다. AddActivity를 종료 시켜서 시스템에 의해 자동으로 이전화면으로 전환되게 처리

        finish();
    }
}
