package cn.lunodio.commonview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import cn.lunodio.commonview.core.LHelper;
import cn.lunodio.commonview.core.LHelperImpl;
import cn.lunodio.commonview.core.LMethodInterface;

/**
 */
public class LView extends View implements LMethodInterface {

    private final LHelper mHelper = new LHelperImpl();

    public LView(Context context) {
        this(context, null);
    }

    public LView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper.init(context, attrs, this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mHelper.preDraw(canvas);
        super.draw(canvas);
        mHelper.drawPath(canvas, getDrawableState());
    }

    @Override
    public void setRadius(float radiusDp) {
        mHelper.setRadius(radiusDp);
    }

    @Override
    public void setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) {
        mHelper.setRadius(radiusTopLeftDp, radiusTopRightDp, radiusBottomLeftDp, radiusBottomRightDp);
    }

    @Override
    public void setRadiusLeft(float radiusDp) {
        mHelper.setRadiusLeft(radiusDp);
    }

    @Override
    public void setRadiusRight(float radiusDp) {
        mHelper.setRadiusRight(radiusDp);
    }

    @Override
    public void setRadiusTop(float radiusDp) {
        mHelper.setRadiusTop(radiusDp);
    }

    @Override
    public void setRadiusBottom(float radiusDp) {
        mHelper.setRadiusBottom(radiusDp);
    }

    @Override
    public void setRadiusTopLeft(float radiusDp) {
        mHelper.setRadiusTopLeft(radiusDp);
    }

    @Override
    public void setRadiusTopRight(float radiusDp) {
        mHelper.setRadiusTopRight(radiusDp);
    }

    @Override
    public void setRadiusBottomLeft(float radiusDp) {
        mHelper.setRadiusBottomLeft(radiusDp);
    }

    @Override
    public void setRadiusBottomRight(float radiusDp) {
        mHelper.setRadiusBottomRight(radiusDp);
    }

    @Override
    public void setStrokeWidth(float widthDp) {
        mHelper.setStrokeWidth(widthDp);
    }

    @Override
    public void setStrokeColor(int color) {
        mHelper.setStrokeColor(color);
    }

    @Override
    public void setStrokeWidthColor(float widthDp, int color) {
        mHelper.setStrokeWidthColor(widthDp, color);
    }
}
