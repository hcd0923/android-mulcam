package com.example.pjt_student;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "studentdb", null, 1);
    }

//  AppInstall 후  어디선가 최초로 이용되는 순간 호출
    @Override
    public void onCreate(SQLiteDatabase db) {
        String studentSql = "create table tb_student (" +
                "_id integer primary key autoincrement," +
                "name not null," +
                "email," +
                "phone," +
                "photo," +
                "memo)";

        String scoreSql = "create table tb_score (" +
                "_id integer primary key autoincrement," +
                "student_id not null," +
                "date," +
                "score)";

        db.execSQL(studentSql);
        db.execSQL(scoreSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tb_student");
        db.execSQL("drop table tb_score");
        onCreate(db);
    }
}
