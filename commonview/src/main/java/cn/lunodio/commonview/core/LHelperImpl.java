package cn.lunodio.commonview.core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.lunodio.commonview.R;
import cn.lunodio.commonview.util.DensityUtil;

/**
 * @author test123 on 2019/12/10
 */
public class LHelperImpl implements LHelper {
    protected Context context;
    protected View v;

    protected Paint paint;
    protected RectF mRectF;
    protected RectF mStrokeRectF;
    protected RectF mOriginRectF;

    protected Path mPath;
    protected Path mTempPath;

    protected Xfermode mXfermode;

    protected boolean isCircle;

    private float[] mRadii;
    private float[] mStrokeRadii;

    protected int mWidth;
    protected int mHeight;
    protected int mStrokeColor;
    protected float mStrokeWidth;
    protected ColorStateList mStrokeColorStateList;

    protected float rTopLeft;
    protected float rTopRight;
    protected float rBottomLeft;
    protected float rBottomRight;

    protected boolean isNewLayer;

    @Override
    public void init(Context context, AttributeSet attrs, View view) {
        if (view instanceof ViewGroup && view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }

        this.context = context;
        v = view;
        mRadii = new float[8];
        mStrokeRadii = new float[8];
        paint = new Paint();

        mRectF = new RectF();
        mStrokeRectF = new RectF();
        mOriginRectF = new RectF();
        mPath = new Path();
        mTempPath = new Path();
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mStrokeColor = Color.WHITE;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LStyleable);
        if (array == null) {
            return;
        }
        float radius = array.getDimension(R.styleable.LStyleable_l_radius, 0);
        float radiusLeft = array.getDimension(R.styleable.LStyleable_l_radius_start, radius);
        float radiusRight = array.getDimension(R.styleable.LStyleable_l_radius_end, radius);
        float radiusTop = array.getDimension(R.styleable.LStyleable_l_radius_top, radius);
        float radiusBottom = array.getDimension(R.styleable.LStyleable_l_radius_bottom, radius);

        rTopLeft = array.getDimension(R.styleable.LStyleable_l_radius_top_start, radiusTop > 0 ? radiusTop : radiusLeft);
        rTopRight = array.getDimension(R.styleable.LStyleable_l_radius_top_end, radiusTop > 0 ? radiusTop : radiusRight);
        rBottomLeft = array.getDimension(R.styleable.LStyleable_l_radius_bottom_start, radiusBottom > 0 ? radiusBottom : radiusLeft);
        rBottomRight = array.getDimension(R.styleable.LStyleable_l_radius_bottom_end, radiusBottom > 0 ? radiusBottom : radiusRight);

        mStrokeWidth = array.getDimension(R.styleable.LStyleable_l_stroke_width, 0);
        mStrokeColor = array.getColor(R.styleable.LStyleable_l_stroke_color, mStrokeColor);
        mStrokeColorStateList = array.getColorStateList(R.styleable.LStyleable_l_stroke_color);

        isNewLayer = array.getBoolean(R.styleable.LStyleable_l_new_layer, false);
        if (isNewLayer && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && view instanceof ViewGroup) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        array.recycle();
    }

    @Override
    public void onSizeChanged(int width, int height) {
        mWidth = width;
        mHeight = height;

        if (isCircle) {
            float radius = Math.min(height, width) * 1f / 2;
            rTopLeft = radius;
            rTopRight = radius;
            rBottomRight = radius;
            rBottomLeft = radius;
        }
        setRadius();

        if (mRectF != null) {
            mRectF.set(mStrokeWidth, mStrokeWidth, width - mStrokeWidth, height - mStrokeWidth);
        }
        if (mStrokeRectF != null) {
            mStrokeRectF.set((mStrokeWidth / 2), (mStrokeWidth / 2), width - mStrokeWidth / 2, height - mStrokeWidth / 2);
        }
        if (mOriginRectF != null) {
            mOriginRectF.set(0, 0, width, height);
        }
    }

    @Override
    public void preDraw(Canvas canvas) {
        canvas.saveLayer(isNewLayer && Build.VERSION.SDK_INT > Build.VERSION_CODES.P ? mOriginRectF : mRectF, null, Canvas.ALL_SAVE_FLAG);
    }

    @Override
    public void drawPath(Canvas canvas, int[] drawableState) {
        paint.reset();
        mPath.reset();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(mXfermode);

        mPath.addRoundRect(mRectF, mRadii, Path.Direction.CCW);
        mTempPath.reset();
        mTempPath.addRect(mOriginRectF, Path.Direction.CCW);
        mTempPath.op(mPath, Path.Op.DIFFERENCE);
        canvas.drawPath(mTempPath, paint);
        paint.setXfermode(null);
        canvas.restore();

        // draw stroke
        if (mStrokeWidth > 0) {
            if (mStrokeColorStateList != null && mStrokeColorStateList.isStateful()) {
                mStrokeColor = mStrokeColorStateList.getColorForState(drawableState, mStrokeColorStateList.getDefaultColor());
            }

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(mStrokeWidth);
            paint.setColor(mStrokeColor);

            mPath.reset();
            mPath.addRoundRect(mStrokeRectF, mStrokeRadii, Path.Direction.CCW);
            canvas.drawPath(mPath, paint);
        }
    }

    @Override
    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    @Override
    public void setRadius(float radiusDp) {
        if (context == null) {
            return;
        }
        float radiusPx = DensityUtil.dip2px(context, radiusDp);
        rTopLeft = radiusPx;
        rTopRight = radiusPx;
        rBottomLeft = radiusPx;
        rBottomRight = radiusPx;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) {
        if (context == null) {
            return;
        }
        rTopLeft = DensityUtil.dip2px(context, radiusTopLeftDp);
        rTopRight = DensityUtil.dip2px(context, radiusTopRightDp);
        rBottomLeft = DensityUtil.dip2px(context, radiusBottomLeftDp);
        rBottomRight = DensityUtil.dip2px(context, radiusBottomRightDp);
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusLeft(float radiusDp) {
        if (context == null) {
            return;
        }
        float radiusPx = DensityUtil.dip2px(context, radiusDp);
        rTopLeft = radiusPx;
        rBottomLeft = radiusPx;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusRight(float radiusDp) {
        if (context == null) {
            return;
        }
        float radiusPx = DensityUtil.dip2px(context, radiusDp);
        rTopRight = radiusPx;
        rBottomRight = radiusPx;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusTop(float radiusDp) {
        if (context == null) {
            return;
        }
        float radiusPx = DensityUtil.dip2px(context, radiusDp);
        rTopLeft = radiusPx;
        rTopRight = radiusPx;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusBottom(float radiusDp) {
        if (context == null) {
            return;
        }
        float radiusPx = DensityUtil.dip2px(context, radiusDp);
        rBottomLeft = radiusPx;
        rBottomRight = radiusPx;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusTopLeft(float radiusDp) {
        if (context == null) {
            return;
        }
        rTopLeft = DensityUtil.dip2px(context, radiusDp);
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusTopRight(float radiusDp) {
        if (context == null) {
            return;
        }
        rTopRight = DensityUtil.dip2px(context, radiusDp);
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusBottomLeft(float radiusDp) {
        if (context == null) {
            return;
        }
        rBottomLeft = DensityUtil.dip2px(context, radiusDp);
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setRadiusBottomRight(float radiusDp) {
        if (context == null) {
            return;
        }
        rBottomRight = DensityUtil.dip2px(context, radiusDp);
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setStrokeWidth(float widthDp) {
        if (context == null) {
            return;
        }
        mStrokeWidth = DensityUtil.dip2px(context, widthDp);
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setStrokeColor(int color) {
        mStrokeColor = color;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    @Override
    public void setStrokeWidthColor(float widthDp, int color) {
        if (context == null) {
            return;
        }
        mStrokeWidth = DensityUtil.dip2px(context, widthDp);
        mStrokeColor = color;
        if (v != null) {
            onSizeChanged(mWidth, mHeight);
            v.invalidate();
        }
    }

    private void setRadius() {
        mRadii[0] = mRadii[1] = rTopLeft - mStrokeWidth;
        mRadii[2] = mRadii[3] = rTopRight - mStrokeWidth;
        mRadii[4] = mRadii[5] = rBottomRight - mStrokeWidth;
        mRadii[6] = mRadii[7] = rBottomLeft - mStrokeWidth;

        mStrokeRadii[0] = mStrokeRadii[1] = rTopLeft - mStrokeWidth / 2;
        mStrokeRadii[2] = mStrokeRadii[3] = rTopRight - mStrokeWidth / 2;
        mStrokeRadii[4] = mStrokeRadii[5] = rBottomRight - mStrokeWidth / 2;
        mStrokeRadii[6] = mStrokeRadii[7] = rBottomLeft - mStrokeWidth / 2;
    }
}
