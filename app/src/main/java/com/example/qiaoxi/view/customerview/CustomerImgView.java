package com.example.qiaoxi.view.customerview;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.example.qiaoxi.helper.viewhelper.DisplayHelper;


public class CustomerImgView extends AppCompatImageView {

    private float width,height;
    private Paint paint;
    private Paint shadowPaint ;
    private Paint borderPaint;
    private Bitmap mask;
    private PorterDuffXfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private RectF rectF;
    private RectF borderRectF;
    private float radius;
    private float borderWidth;


    public CustomerImgView(Context context) {
        super(context);
        initView(context,null,0);
    }
    public CustomerImgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context,attributeSet,0);
    }
    public CustomerImgView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context,attributeSet,defStyle);
        initView(context,attributeSet,defStyle);
    }



    private void initView(Context context, AttributeSet attrs, int defStyle) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        int color =getResources().getColor(R.color.danhui_0);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomerImgView);
            int r = typedArray.getInteger(R.styleable.CustomerImgView_qxRadius,0);
            radius = DisplayHelper.dip2Px(context,r);
            int border = typedArray.getInteger(R.styleable.CustomerImgView_qxBorder, 0);
            borderWidth = DisplayHelper.dip2Px(context,border);
            color = typedArray.getColor(R.styleable.CustomerImgView_qxBorderColor,getResources().getColor(R.color.danhui_0));
            typedArray.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        shadowPaint.setAntiAlias(true);
        shadowPaint.setShadowLayer(10 , 5, 10, getResources().getColor(R.color.C1C1C1_gray));

        borderPaint.setColor(color);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setAntiAlias(true);



    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        if (rectF == null) {
            rectF = new RectF(0,0,width,height);
            borderRectF = new RectF( borderWidth / 2 , borderWidth / 2 , width - borderWidth / 2 ,height - borderWidth / 2 );
        }
        if (mask == null) {
            createMaskBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        paint.setXfermode(xFermode);
        canvas.drawBitmap(mask,null,rectF,paint);
        paint.setXfermode(null);
        if (borderWidth != 0) {
            canvas.drawRoundRect(borderRectF, radius - borderWidth / 2, radius - borderWidth / 2, borderPaint);
        }
        canvas.restoreToCount(saveCount);

    }

    private void createMaskBitmap() {
        mask = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);
        canvas.drawRoundRect(rectF,radius,radius,paint);
    }
}