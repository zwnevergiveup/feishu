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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.qiaoxi.R;


public class CustomerImgView extends AppCompatImageView {
    private int img_resourceID = 0;
    private Path mPath = new Path();
    private float width,height;
    Paint paint;
    Path path = new Path();
    Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap mask;
    BlurMaskFilter bf;
    PorterDuffXfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    RectF rectF;
    float radius = 50;


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

//        paint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setShadowLayer(10 , 5, 10, getResources().getColor(R.color.C1C1C1_gray));

//        shadowPaint.setAlpha(0);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        if (rectF == null) {
            rectF = new RectF(0,0,width,height);
        }
        if (mask == null) {
            createMaskBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);


//        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,null,rectF,paint);

//        path.addCircle(width/2, height/2 , height /2, Path.Direction.CCW);
        paint.setXfermode(xFermode);
//        canvas.drawPath(path,paint);
        canvas.drawBitmap(mask,null,rectF,paint);


        paint.setXfermode(null);

        canvas.restoreToCount(saveCount);

//        if (bitmap != null) {
//            canvas.drawRoundRect(rectF,rectF.height() /2,rectF.width() / 2,shadowPaint);
//
//            canvas.drawBitmap(bitmap,null,rectF,paint);
//        }

//        canvas.drawRoundRect(rectF, 5, 5, shadowPaint);



//        path.addCircle(width / 2, height / 2 , height /2 - 10, Path.Direction.CW);
//        canvas.clipPath(path);
//        canvas.drawPath(path,shadowPaint);
//        super.onDraw(canvas);

//        canvas.drawPath(path1,paint);

    }

    private void createMaskBitmap() {
        mask = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);
        canvas.drawRoundRect(rectF,radius,radius,paint);
    }

    public void setImgResourceID(int resId) {
        this.img_resourceID = resId;
        bitmap = BitmapFactory.decodeResource(getResources(),resId);
        bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.chunfen);
//         bitmap=Bitmap.createBitmap(60,60, Bitmap.Config.ARGB_8888);
    }
}