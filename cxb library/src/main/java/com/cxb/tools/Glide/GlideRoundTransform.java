package com.cxb.tools.Glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Glide控件 画圆角和边框
 */
public class GlideRoundTransform extends BitmapTransformation {

    private float radius = 0f;
    private float borderThickness = 0;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int alpha = 0;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int radius) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
    }

    public GlideRoundTransform setColor(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        return this;
    }

    public GlideRoundTransform setBorderThickness(int px) {
        this.borderThickness = px;
        return this;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        if (alpha != 0) {
            Paint bgPaint = new Paint();
            bgPaint.setColor(Color.rgb(red, green, blue));
            bgPaint.setAntiAlias(true);
            RectF bgRectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(bgRectF, radius, radius, bgPaint);
        }

        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(borderThickness, borderThickness, source.getWidth() - borderThickness, source.getHeight() - borderThickness);
        canvas.drawRoundRect(rectF, radius - borderThickness / 2, radius - borderThickness / 2, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
