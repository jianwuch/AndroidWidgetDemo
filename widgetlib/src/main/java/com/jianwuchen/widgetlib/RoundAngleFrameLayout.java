package com.jianwuchen.widgetlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.jianwuchen.widgetlib.util.Density;

/**
 * 圆角FrameLayout
 * 类似CardView
 *
 * @author jove.chen
 */
public class RoundAngleFrameLayout extends FrameLayout {

    private static final float RADIUS = Density.dp2px(3);
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;

    private boolean isNeedTopLeftRound = true;
    private boolean isNeedTopRightRound = true;
    private boolean isNeedBottomLeftRound = true;
    private boolean isNeedBottomRightRound = true;
    private Paint roundPaint;
    private Paint imagePaint;

    public RoundAngleFrameLayout(Context context) {
        this(context, null);
    }

    public RoundAngleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundAngleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //拿圆角尺寸
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleFrameLayout);
        float radius = ta.getDimension(R.styleable.RoundAngleFrameLayout_radius, RADIUS);

        //拿是哪个角需要圆角,默认全圆角
        int type = ta.getInt(R.styleable.RoundAngleFrameLayout_type, 0x00001111);
        if (type > 0) {
            isNeedTopLeftRound = (type & 0x00000001) > 0;
            isNeedTopRightRound = (type & 0x00000010) > 0;
            isNeedBottomLeftRound = (type & 0x00000100) > 0;
            isNeedBottomRightRound = (type & 0x00001000) > 0;
        }
        ta.recycle();

        topLeftRadius = radius;
        topRightRadius = radius;
        bottomLeftRadius = radius;
        bottomRightRadius = radius;

        roundPaint = new Paint();
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        imagePaint = new Paint();
        imagePaint.setXfermode(null);
    }


    //重写这个方法会导致整个布局都是显示圆角的，设置背景会被子覆盖
    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint,
                Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        if (isNeedTopLeftRound) drawTopLeft(canvas);
        if (isNeedTopRightRound) drawTopRight(canvas);
        if (isNeedBottomLeftRound) drawBottomLeft(canvas);
        if (isNeedBottomRightRound) drawBottomRight(canvas);
        canvas.restore();
    }

    //通过以下方法是可以显示直角的背景
/*    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint,
                Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        if (isNeedTopLeftRound) drawTopLeft(canvas);
        if (isNeedTopRightRound) drawTopRight(canvas);
        if (isNeedBottomLeftRound) drawBottomLeft(canvas);
        if (isNeedBottomRightRound) drawBottomRight(canvas);
        canvas.restore();
    }*/

    private void drawTopLeft(Canvas canvas) {
        if (topLeftRadius > 0) {
            Path path = new Path();
            path.moveTo(0, topLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(topLeftRadius, 0);
            path.arcTo(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2), -90, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (topRightRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - topRightRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, topRightRadius);
            path.arcTo(new RectF(width - 2 * topRightRadius, 0, width, topRightRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (bottomLeftRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - bottomLeftRadius);
            path.lineTo(0, height);
            path.lineTo(bottomLeftRadius, height);
            path.arcTo(new RectF(0, height - 2 * bottomLeftRadius, bottomLeftRadius * 2, height),
                    90, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (bottomRightRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - bottomRightRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - bottomRightRadius);
            path.arcTo(
                    new RectF(width - 2 * bottomRightRadius, height - 2 * bottomRightRadius, width,
                            height), 0, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    public void setRoundType(boolean topleft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
        this.isNeedTopLeftRound = topleft;
        this.isNeedTopRightRound = topRight;
        this.isNeedBottomLeftRound = bottomLeft;
        this.isNeedBottomRightRound = bottomRight;
        invalidate();
    }

    public void setRadius(float radius) {
        topLeftRadius = radius;
        topRightRadius = radius;
        bottomLeftRadius = radius;
        bottomRightRadius = radius;
        invalidate();
    }

}
