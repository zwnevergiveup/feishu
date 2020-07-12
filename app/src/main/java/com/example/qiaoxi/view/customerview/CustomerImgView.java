package com.example.qiaoxi.view.customerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.qiaoxi.R;

public class CustomerImgView extends AppCompatImageView {
    private int img_resourceID = 0;
    private Path mPath = new Path();
    private float width,height;


    public CustomerImgView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context,attributeSet,defStyle);
    }
    public CustomerImgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        img_resourceID = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android","src",0);
    }
    public CustomerImgView(Context context) {
        super(context);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap rect = getRectBitmap();
        Bitmap resBitmap = getResourceBitmap();

        setLayerType(LAYER_TYPE_HARDWARE,null);
        Paint paint = new Paint();
        canvas.drawARGB(0,0,0,0);
        canvas.drawBitmap(rect, 0, 0, paint);


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(resBitmap,10,10,paint);

    }
    private Bitmap getResourceBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled= true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),img_resourceID,options).copy(Bitmap.Config.ARGB_8888,true);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (int)this.width - 20;
        int newHeight = (int)this.height- 20;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    private Bitmap getCircleBitmap() {
        Bitmap circle = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(circle);
        c.drawARGB(0, 0, 0, 0);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.main_green));
        c.drawCircle(width / 2 , height / 2, width / 2, p);
        return circle;
    }
    private Bitmap getRectBitmap() {
        Bitmap rect = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(rect);
        c.drawARGB(0, 0, 0, 0);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.main_green));
        c.drawRect(0,0,width,height,p);
        return rect;
    }
}