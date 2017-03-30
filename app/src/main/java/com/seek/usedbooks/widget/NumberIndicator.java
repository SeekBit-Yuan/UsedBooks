/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.seek.usedbooks.R;
import com.seek.usedbooks.utils.UnitUtil;

public class NumberIndicator extends View {

    private Context mContext;
    private Rect mBounds;
    private Paint mPaint;
    private RectF mRectF;

    private int mItemCount;
    private int mCurrentIndex;

    private float mTextSize;
    private int mTextColor;
    private int mBgColor;
    private float mRadius;

    public NumberIndicator(Context context) {
        this(context, null);
    }

    public NumberIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        final TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.NumberIndicator, 0, 0);

        mTextSize = array.getDimension(R.styleable.NumberIndicator_textSize, 30);
        mTextColor = array.getColor(R.styleable.NumberIndicator_textColor, Color.WHITE);
        mBgColor = array.getColor(R.styleable.NumberIndicator_bgColor, Color.GRAY);
        mRadius = array.getDimension(R.styleable.NumberIndicator_radius, 4);

        array.recycle();
        init();
    }

    public void setItemCount(int itemCount) {
        this.mItemCount = itemCount;
        invalidate();
    }

    public void setIndex(int index) {
        this.mCurrentIndex = index;
        invalidate();
    }

    private void init() {
        mBounds = new Rect();
        mRectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // draw background
        mPaint.setColor(mBgColor);
        mRectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
            canvas.drawRoundRect(mRectF, UnitUtil.dpToPx(mContext, (int) mRadius),
                    UnitUtil.dpToPx(mContext, (int) mRadius), mPaint);

        // draw text
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        String text = mCurrentIndex + "/" + mItemCount;
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2
                + textHeight / 2, mPaint);
    }

}