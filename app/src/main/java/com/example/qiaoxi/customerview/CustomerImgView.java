package com.example.qiaoxi.customerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.jar.Attributes;

public class CustomerImgView extends AppCompatImageView {
    private Path circlePath = new Path();
    private Paint paint = new Paint();
    private int img_resourceID = 0;
    private Bitmap mBitmap;


    public CustomerImgView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context,attributeSet,defStyle);
        init();
    }
    public CustomerImgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        img_resourceID = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android","src",0);
        init();
    }
    public CustomerImgView(Context context) {
        super(context);
        init();
    }

    void init(){
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),img_resourceID);
        mBitmap = Bitmap.createScaledBitmap(bmp,450,450,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,0,0,paint);
        circlePath.addCircle(150f, 150f, 150f, Path.Direction.CCW);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mBitmap,200,200,paint);
//        paint.setXfermode(null);
//        canvas.restore();
    }
}