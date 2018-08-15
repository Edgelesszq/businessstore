package com.businessstore.view.roundImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.businessstore.util.DpConversion;


public class RoundImageView2 extends android.support.v7.widget.AppCompatImageView {

        float width,height;
        Context mContext;


        public RoundImageView2(Context context) {
            this(context, null);
            mContext = context;
        }

        public RoundImageView2(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
            mContext = context;
        }

        public RoundImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mContext = context;
            if (Build.VERSION.SDK_INT < 18) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            width = getWidth();
            height = getHeight();
        }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
        protected void onDraw(Canvas canvas) {
        int round = DpConversion.dp2px(mContext,5);

            /*if (width > round && height > round) {
                Path path = new Path();
                path.moveTo(round, 0);
                path.lineTo(width - round, 0);
                path.quadTo(width, 0, width, round);
                path.lineTo(width, height - round);
                path.quadTo(width, height, width - round, height);
                path.lineTo(round, height);
                path.quadTo(0, height, 0, height - round);
                path.lineTo(0, round);
                path.quadTo(0, 0, round, 0);
                canvas.clipPath(path);
            }*/
            if (width > round && height > round) {
                Path path = new Path();
                path.moveTo(round, 0);
                path.lineTo(width - round, 0);
                path.quadTo(width, 0, width, round);
                path.lineTo(width, height);
//                path.quadTo(width, height, width - round, height);
                path.lineTo(0, height);
//                path.quadTo(0, height, 0, height - round);
                path.lineTo(0, round);
                path.quadTo(0, 0, round, 0);
                canvas.clipPath(path);
            }

            super.onDraw(canvas);
        }
    }

