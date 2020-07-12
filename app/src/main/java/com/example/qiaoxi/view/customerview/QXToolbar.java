package com.example.qiaoxi.view.customerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.qiaoxi.R;

public class QXToolbar extends ConstraintLayout {

    public ImageView mLeftIcon;
    public TextView mTitle;
    public ImageView mRightIcon;
    private Context mContext;

    public QXToolbar(Context context) {
        super(context);
        this.mContext = context;
        initView(context,null,0);
    }
    public QXToolbar(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.mContext = context;
        initView(context,attrs,0);
    }
    public QXToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mContext = context;
        initView(context,attrs,defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_qx, this);
        mLeftIcon = findViewById(R.id.qxtoolbar_leftiv);
        mTitle = findViewById(R.id.qxtoolbar_title);
        mRightIcon = findViewById(R.id.qxtoolbar_rightiv);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QXToolbar);
            int leftIconResourceID = typedArray.getResourceId(R.styleable.QXToolbar_leftButton_src,0);
//            String titleText = typedArray.getString(R.styleable.QXToolbar_title);
            int rightIconResourceID = typedArray.getResourceId(R.styleable.QXToolbar_rightButton_src, 0);
            int backgroundColor = typedArray.getColor(R.styleable.QXToolbar_background_color,0);
//            int titleTextColor = typedArray.getColor(R.styleable.QXToolbar_titleTextColor, 0);

            if (leftIconResourceID != 0) {
                mLeftIcon.setImageResource(leftIconResourceID);
                mLeftIcon.setVisibility(VISIBLE);
            }

            if (rightIconResourceID != 0) {
                mRightIcon.setImageResource(rightIconResourceID);
                mRightIcon.setVisibility(VISIBLE);
            }

//            if (titleText != null ) {
//                mTitle.setText(titleText);
//                if (titleTextColor != 0) {
//                    mTitle.setTextColor(titleTextColor);
//                }
//                mTitle.setVisibility(VISIBLE);
//            }

            if (backgroundColor != 0) {
                setBackgroundColor(backgroundColor);
            }

            typedArray.recycle();
        }
    }

    public void setTitleText(String title, int color) {
        this.mTitle.setTextColor(color);
        this.mTitle.setText(title);
        this.mTitle.setVisibility(VISIBLE);
    }
}
