package cn.lunodio.commonview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import cn.lunodio.commonview.core.LHelper;
import cn.lunodio.commonview.core.LHelperImpl;
import cn.lunodio.commonview.core.LMethodInterface;

/**
 * @author test123 on 2019/12/10
 */
public class LCircleImageView extends AppCompatImageView implements LMethodInterface {

    private final LHelper lHelper = new LHelperImpl();

    public LCircleImageView(Context context) {
        this(context, null);
    }

    public LCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lHelper.init(context, attrs, this);
        lHelper.setCircle(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lHelper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        lHelper.preDraw(canvas);
        super.draw(canvas);
        lHelper.drawPath(canvas, getDrawableState());
    }

    @Override
    public void setRadius(float radiusDp) {
        lHelper.setRadius(radiusDp);
    }

    @Override
    public void setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) {
        lHelper.setRadius(radiusTopLeftDp, radiusTopRightDp, radiusBottomLeftDp, radiusBottomRightDp);
    }

    @Override
    public void setRadiusLeft(float radiusDp) {
        lHelper.setRadiusLeft(radiusDp);
    }

    @Override
    public void setRadiusRight(float radiusDp) {
        lHelper.setRadiusRight(radiusDp);
    }

    @Override
    public void setRadiusTop(float radiusDp) {
        lHelper.setRadiusTop(radiusDp);
    }

    @Override
    public void setRadiusBottom(float radiusDp) {
        lHelper.setRadiusBottom(radiusDp);
    }

    @Override
    public void setRadiusTopLeft(float radiusDp) {
        lHelper.setRadiusTopLeft(radiusDp);
    }

    @Override
    public void setRadiusTopRight(float radiusDp) {
        lHelper.setRadiusTopRight(radiusDp);
    }

    @Override
    public void setRadiusBottomLeft(float radiusDp) {
        lHelper.setRadiusBottomLeft(radiusDp);
    }

    @Override
    public void setRadiusBottomRight(float radiusDp) {
        lHelper.setRadiusBottomRight(radiusDp);
    }

    @Override
    public void setStrokeWidth(float widthDp) {
        lHelper.setStrokeWidth(widthDp);
    }

    @Override
    public void setStrokeColor(int color) {
        lHelper.setStrokeColor(color);
    }

    @Override
    public void setStrokeWidthColor(float widthDp, int color) {
        lHelper.setStrokeWidthColor(widthDp, color);
    }
}
