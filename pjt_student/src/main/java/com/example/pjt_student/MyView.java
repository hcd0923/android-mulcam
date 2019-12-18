package com.example.pjt_student;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    Context context;

    int score;
    int color;

//    view를 activity 개발자가 java에서 직접 생성해서 이동한다면 생성자 하나만 가능하다.
//    하지만 layout.xml에 등록하여 사용한다면 상황에 따라 생성되는 생성자를 만들해야 한다.

    public MyView (Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MyView (Context context, AttributeSet  attributeSet){
        super(context, attributeSet);
        this.context = context;
        init(attributeSet);
    }

    public MyView(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
        this.context =  context;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if(attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.AAA);
            color = array.getColor(R.styleable.AAA_scoreColor, Color.RED);
        }
    }

    public void setScore(int score) {
        this.score = score;
//        새로운 스코어가 전달 되었으니, 다시 그린다.

//        onDraw를 호출해준다.
         invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.alpha(Color.CYAN));
        //사각형을 그리기 위한 정보..
        RectF rectf = new RectF(15, 15, 70, 70);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        canvas.drawArc(rectf, 0, 360, false, paint);

        float endAngle = (360*score) /100;

        paint.setColor(color);

        canvas.drawArc(rectf, -90, endAngle, false, paint);

        paint.setColor(Color.RED);

        canvas.drawArc(rectf, -90, 10, false, paint);

        super.onDraw(canvas);
    }
}
