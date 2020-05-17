package com.example.hvroundimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatImageView;

public class RoundedCornerImageView extends AppCompatImageView {
    private Path hexagonPath;
    private Path hexagonBorderPath;
    private Path hexagonCornerPath;
    private Paint mBorderPaint;

    @StyleableRes
    int index0 = 0;
    @StyleableRes
    int index1 = 1;
    @StyleableRes
    int index2 = 2;
    @StyleableRes
    int index3 = 3;
    @StyleableRes
    int index4 = 4;
    @StyleableRes
    int index5 = 5;


    float cornerRadius;
    Boolean topLeftCorner;
    Boolean topRightCorner;
    Boolean bottomLeftCorner;
    Boolean bottomRightCorner;


    public RoundedCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] sets = {R.attr.cornerRadius,R.attr.topLeftCorner,R.attr.topRightCorner,R.attr.bottomLeftCorner,R.attr.bottomRightCorner};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        CharSequence cornerR = typedArray.getText(index0);
        Boolean topLeft = typedArray.getBoolean(index1,true);
        Boolean topRight = typedArray.getBoolean(index2,true);
        Boolean bottomLeft = typedArray.getBoolean(index3,true);
        Boolean bottomRight = typedArray.getBoolean(index4,true);

        if (cornerR == null)
        {
            cornerR = "0";
        }

        cornerRadius = Float.parseFloat((String) cornerR);
        topLeftCorner = topLeft;
        topRightCorner = topRight;
        bottomLeftCorner = bottomLeft;
        bottomRightCorner = bottomRight;


        typedArray.recycle();

        setCornerRadius(cornerRadius);
        setTopLeftCorner(topLeftCorner);
        setTopRightCorner(topRightCorner);
        setBottomLeftCorner(bottomLeftCorner);
        setBottomRightCorner(bottomRightCorner);
        init();
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] sets = {R.attr.cornerRadius};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        CharSequence cornerR = typedArray.getText(index0);
        Boolean topLeft = typedArray.getBoolean(index1,false);
        Boolean topRight = typedArray.getBoolean(index2,false);
        Boolean bottomLeft = typedArray.getBoolean(index3,false);
        Boolean bottomRight = typedArray.getBoolean(index4,false);

        cornerRadius = Float.parseFloat((String) cornerR);
        topLeftCorner = topLeft;
        topRightCorner = topRight;
        bottomLeftCorner = bottomLeft;
        bottomRightCorner = bottomRight;


        typedArray.recycle();

        setCornerRadius(cornerRadius);
        setTopLeftCorner(topLeftCorner);
        setTopRightCorner(topRightCorner);
        setBottomLeftCorner(bottomLeftCorner);
        setBottomRightCorner(bottomRightCorner);

        init();
    }

    private void init() {
        this.hexagonPath = new Path();
        this.hexagonBorderPath = new Path();

        this.mBorderPaint = new Paint();
        this.mBorderPaint.setColor(Color.TRANSPARENT);
        this.mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBorderPaint.setStrokeWidth(5f);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setBottomLeftCorner(Boolean bottomLeftCorner) {
        this.bottomLeftCorner = bottomLeftCorner;
    }

    public void setBottomRightCorner(Boolean bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    public void setTopLeftCorner(Boolean topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public void setTopRightCorner(Boolean topRightCorner) {
        this.topRightCorner = topRightCorner;
    }

    public void setRadius(float radius) {
        calculatePath(radius);
    }

    public void setBorderColor(int color) {
        this.mBorderPaint.setColor(color);
        invalidate();
    }

    private void calculatePath(float radius) {
        float halfRadius = radius / 2f;
        float triangleHeight = (float) (Math.sqrt(3.0) * halfRadius);
        float centerX = getMeasuredWidth() / 2f;
        float centerY = getMeasuredHeight() / 2f;

        this.hexagonPath.reset();
        this.hexagonPath.moveTo(centerX, centerY + radius);
        this.hexagonPath.lineTo(centerX - triangleHeight, centerY + halfRadius);
        this.hexagonPath.lineTo(centerX - triangleHeight, centerY - halfRadius);
        this.hexagonPath.lineTo(centerX, centerY - radius);
        this.hexagonPath.lineTo(centerX + triangleHeight, centerY - halfRadius);
        this.hexagonPath.lineTo(centerX + triangleHeight, centerY + halfRadius);
        this.hexagonPath.close();

        float radiusBorder = radius - 5f;
        float halfRadiusBorder = radiusBorder / 2f;
        float triangleBorderHeight = (float) (Math.sqrt(3.0) * halfRadiusBorder);

        this.hexagonBorderPath.reset();
        this.hexagonBorderPath.moveTo(centerX, centerY + radiusBorder);
        this.hexagonBorderPath.lineTo(centerX - triangleBorderHeight, centerY + halfRadiusBorder);
        this.hexagonBorderPath.lineTo(centerX - triangleBorderHeight, centerY - halfRadiusBorder);
        this.hexagonBorderPath.lineTo(centerX, centerY - radiusBorder);
        this.hexagonBorderPath.lineTo(centerX + triangleBorderHeight, centerY - halfRadiusBorder);
        this.hexagonBorderPath.lineTo(centerX + triangleBorderHeight, centerY + halfRadiusBorder);
        this.hexagonBorderPath.close();

        invalidate();
    }


    private void RoundedRect1(float left, float top, float right, float bottom, float rx, float ry, boolean topLeftCorner, boolean topRightCorner,boolean bottomLeftCorner, boolean bottomRightCorner) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width/2) rx = width/2;
        if (ry > height/2) ry = height/2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));


        path.moveTo(right, top + ry);

        if(topRightCorner) {
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
            path.rLineTo(-widthMinusCorners, 0);
        }
        else{
            path.rLineTo(0,-ry);
            path.rLineTo(-width + rx,0);
        }


        if (topLeftCorner)
        {
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
            path.rLineTo(0, heightMinusCorners);
        }
        else
        {
            path.rLineTo(-rx, 0);
            path.rLineTo(left, width - ry);
        }


        if (bottomLeftCorner)
        {
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
            path.rLineTo(widthMinusCorners, 0);
        }
        else
        {
            path.rLineTo(0,ry);
            path.rLineTo(width - ry ,0);
        }

        if(bottomRightCorner)
        {
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        }
        else
        {
            path.rLineTo(rx,0);
        }
        path.close();//Given close, last lineto can be removed.
        this.hexagonPath = path;
        this.hexagonBorderPath = path;
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawPath(hexagonBorderPath, mBorderPaint);
        c.clipPath(hexagonPath, Region.Op.INTERSECT);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        super.onDraw(c);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        //calculatePath(Math.min(width / 2f, height / 2f) - 10f);

        cornerRadius = convertDpToPixel(cornerRadius,getContext());

        RoundedRect1(0,0,width,height,cornerRadius,cornerRadius,topLeftCorner,topRightCorner,bottomLeftCorner,bottomRightCorner);
    }
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

     private void RoundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width/2) rx = width/2;
        if (ry > height/2) ry = height/2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        path.rLineTo(-widthMinusCorners, 0);
        path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        path.rLineTo(0, heightMinusCorners);

        if (conformToOriginalPost) {
            path.rLineTo(0, ry);
            path.rLineTo(width, 0);
            path.rLineTo(0, -ry);
        }
        else {
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
            path.rLineTo(widthMinusCorners, 0);
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

         this.hexagonPath = path;
         this.hexagonBorderPath = path;
    }
}