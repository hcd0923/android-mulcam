package com.example.androidstudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      화면출력이 끝난 상태, view 객체 생성 완료
        setContentView(R.layout.activity_main);
//      필요한 view 객체를 획득
        button = findViewById(R.id.btn);
        editText = findViewById(R.id.edit);
//      이벤트 등록
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String data = editText.getText().toString();
        Log.d("test", data);
    }
}
