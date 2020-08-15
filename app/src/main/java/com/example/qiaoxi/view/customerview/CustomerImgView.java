package com.example.qiaoxi.view.customerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.qiaoxi.R;


public class CustomerImgView extends AppCompatImageView {
    private int img_resourceID = 0;
    private Path mPath = new Path();
    private float width,height;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();
    Paint shadowPaint = new Paint();
    Bitmap bitmap;
    BlurMaskFilter bf;

    public CustomerImgView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context,attributeSet,defStyle);
        initView();
    }
    public CustomerImgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        img_resourceID = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android","src",0);
        initView();
    }
    public CustomerImgView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        bf = new BlurMaskFilter(10,BlurMaskFilter.Blur.INNER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setMaskFilter(bf);

        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);
//        shadowPaint.setAlpha(0);

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
        float raduis = height / 12 > 40 ? 40: height / 12;
        float shadowColor = height / 16 > 28? 28 : height / 16;
//        paint.setShadowLayer(200 , 10, 10, getDarkerColor(Color.RED));

        RectF rectF = new RectF(0 , 0, width-10 , height-10 );

        if (bitmap != null) {
            canvas.drawBitmap(bitmap.extractAlpha(paint, null), 20,20, paint);
            canvas.drawBitmap(bitmap,null,rectF,shadowPaint);
        }
        canvas.save();

//        canvas.drawRoundRect(rectF, 5, 5, shadowPaint);



//        path.addCircle(width / 2, height / 2 , height /2 - 10, Path.Direction.CW);
//        canvas.clipPath(path);
//        canvas.drawPath(path,shadowPaint);
//        super.onDraw(canvas);

//        canvas.drawPath(path1,paint);

    }

    private int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        return Color.HSVToColor(hsv);
    }

    private Bitmap getResourceBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled= true;
        Bitmap bitmap = Bitmap.createBitmap(64,64, Bitmap.Config.ARGB_8888);//BitmapFactory.decodeResource(getResources(),img_resourceID,options).copy(Bitmap.Config.ARGB_8888,true);
        bitmap.eraseColor(Color.parseColor("#000000"));
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

    public void setImgResourceID(int resId) {
        this.img_resourceID = resId;
        bitmap = BitmapFactory.decodeResource(getResources(),resId);
    }
}