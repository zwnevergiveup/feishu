package com.example.qiaoxi.view.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class LetterNavigationView extends View {
    private TextPaint mTextPaint;
    private String[] mNavigationContent = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private float width,heigh;

    public LetterNavigationView(Context context) {
        super(context);
        initView();
    }
    public LetterNavigationView(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView();
    }
    public LetterNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initView();
    }

    private void initView(){
        //绘制文字画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        heigh = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float oneHeight = heigh / mNavigationContent.length * 9 / 10;
        float top = heigh / 20;
        for (int i = 0 ;i < mNavigationContent.length; i++) {
            mTextPaint.setTextSize(oneHeight * 3 / 4);
            canvas.drawText(mNavigationContent[i],width / 2,top + oneHeight * i,mTextPaint);
        }

    }
}
