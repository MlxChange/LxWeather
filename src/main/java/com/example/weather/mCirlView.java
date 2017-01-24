package com.example.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/1/20.
 */

public class mCirlView extends View {

    private String Title="";
    private int Number;
    private String NumTxt="";

    private float mwidth,mheight;

    public mCirlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.mCirlView);
        Title=array.getString(R.styleable.mCirlView_title);
        Number=array.getInteger(R.styleable.mCirlView_Number,0);
        NumTxt=array.getString(R.styleable.mCirlView_Numtxt);
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNumTxt() {
        return NumTxt;
    }

    public void setNumTxt(String numTxt) {
        NumTxt = numTxt;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mwidth/2,mheight/2);
        canvas.scale(-1,1);
        //画灰色表盘
        RectF rectF=new RectF(-150,-150,150,150);
        Path path=new Path();
        path.addArc(rectF,120,300);
        Paint paint=new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(70);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        canvas.drawPath(path,paint);

        //画中间数字
        Paint paint5=new Paint();
        paint5.setTextSize(120);
        paint5.setColor(Color.parseColor("#008000"));
        canvas.scale(-1,1);
        paint5.setStyle(Paint.Style.FILL);
        if(Number>=100){
            canvas.drawText(Number+"",-100,30,paint5);
        }else{
            canvas.drawText(Number+"",-70,30,paint5);
        }

        //画中间数字下文字
        Paint paint1=new Paint();
        paint1.setColor(Color.parseColor("#cccccc"));
        paint1.setTextSize(30);
        paint1.setStyle(Paint.Style.FILL);
        canvas.drawText(NumTxt,-20,70,paint1);


        //画覆盖表盘的弧
        Paint paint2=new Paint();
        paint2.setColor(Color.parseColor("#008000"));
        paint2.setStrokeWidth(7);
        paint2.setStyle(Paint.Style.STROKE);
        Path path1=new Path();
        if(Number>=300){
            Number=300;
            paint2.setColor(Color.RED);
        }
        path1.addArc(rectF,120,Number);
        canvas.drawPath(path1,paint2);

        //画最底下文字
        Paint paint3=new Paint();
        paint3.setColor(Color.parseColor("#696969"));
        paint3.setTextSize(37);
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(Title,-90,180,paint3);




    }

    public void notifyDatachange(){
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mwidth=w;
        mheight=h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public mCirlView(Context context) {
        super(context);
    }
}
