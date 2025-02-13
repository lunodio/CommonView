package cn.lunodio.commonview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import cn.lunodio.commonview.core.LHelper;
import cn.lunodio.commonview.core.LHelperImpl;
import cn.lunodio.commonview.core.LMethodInterface;

/**
 *
 */
public class LEditText extends AppCompatEditText implements LMethodInterface {
    private final LHelper helper = new LHelperImpl();

    public LEditText(@NonNull Context context) {
        this(context, null);

    }

    public LEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public LEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper.init(context, attrs, this);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        helper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        helper.preDraw(canvas);
        super.draw(canvas);
        helper.drawPath(canvas, getDrawableState());
    }

    @Override
    public void setRadius(float radiusDp) {
        helper.setRadius(radiusDp);
    }

    @Override
    public void setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) {
        helper.setRadius(radiusTopLeftDp, radiusTopRightDp, radiusBottomLeftDp, radiusBottomRightDp);
    }

    @Override
    public void setRadiusLeft(float radiusDp) {
        helper.setRadiusLeft(radiusDp);
    }

    @Override
    public void setRadiusRight(float radiusDp) {
        helper.setRadiusRight(radiusDp);
    }

    @Override
    public void setRadiusTop(float radiusDp) {
        helper.setRadiusTop(radiusDp);
    }

    @Override
    public void setRadiusBottom(float radiusDp) {
        helper.setRadiusBottom(radiusDp);
    }

    @Override
    public void setRadiusTopLeft(float radiusDp) {
        helper.setRadiusTopLeft(radiusDp);
    }

    @Override
    public void setRadiusTopRight(float radiusDp) {
        helper.setRadiusTopRight(radiusDp);
    }

    @Override
    public void setRadiusBottomLeft(float radiusDp) {
        helper.setRadiusBottomLeft(radiusDp);
    }

    @Override
    public void setRadiusBottomRight(float radiusDp) {
        helper.setRadiusBottomRight(radiusDp);
    }

    @Override
    public void setStrokeWidth(float widthDp) {
        helper.setStrokeWidth(widthDp);
    }

    @Override
    public void setStrokeColor(int color) {
        helper.setStrokeColor(color);
    }

    @Override
    public void setStrokeWidthColor(float widthDp, int color) {
        helper.setStrokeWidthColor(widthDp, color);
    }
}
