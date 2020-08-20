package com.example.qiaoxi.view.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.qiaoxi.widget.QXApplication;

public class LetterNavigationView extends View {
    private TextPaint mTextPaint;
    private Paint mViewPaint;
    private String[] mNavigationContent = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private float width,height, aPosition, oneHeight;
    private OnNavigationScrollerListener mListener;
    private int mCurrentIndex;
    private boolean scrolling = false;
    private float mCurrentY;

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


        //绘制选中文字背景
        mViewPaint = new Paint();
        mViewPaint.setAntiAlias(true);
        mViewPaint.setColor(Color.GRAY);
        mViewPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        oneHeight = height / mNavigationContent.length * 14 / 15 ;
        aPosition = height / 30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0 ;i < mNavigationContent.length; i++) {
            mTextPaint.setTextSize(oneHeight * 3 / 4);
            canvas.drawText(mNavigationContent[i],width * 4/ 5,(aPosition + oneHeight * i) + oneHeight / 2 ,mTextPaint);
        }
        if (scrolling) {
            Log.e("qiaoxi","mCurrentY: "+ mCurrentY);
            canvas.drawCircle(width * 3 /10,mCurrentY, width * 3 / 10,mViewPaint);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mEventY = event.getY();
        mCurrentY = mEventY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                scrolling = true;
                if (mListener != null) {
                    mListener.onDown(showShouldShow(mEventY),mCurrentIndex );
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onScroll(showShouldShow(mEventY),mCurrentIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
                scrolling = false;
                if (mListener != null) {
                    mListener.onUp();
                }
                break;
        }
        invalidate();
        return true;
    }

    private String showShouldShow(float y) {
        float inFactValue = y - aPosition ;
        int choose = Math.round(inFactValue /  oneHeight);
        if (choose < 0  ) {
            mCurrentIndex = 0;
            return mNavigationContent[0];
        }else if (choose >= mNavigationContent.length) {
            mCurrentIndex = mNavigationContent.length - 1;
            return mNavigationContent[mNavigationContent.length - 1];
        }else {
            mCurrentIndex = choose;
            return mNavigationContent[choose];
        }
    }

    public interface OnNavigationScrollerListener {
        void onDown(String letter, int currentIndex);

        void onScroll(String letter,int currentIndex);

        void onUp();
    }

    public void setScrollerListener(OnNavigationScrollerListener listener) {
        this.mListener = listener;
    }
}
