package de.avalax.mtg_insight.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.etsy.android.grid.StaggeredGridView;

public class ScalableStaggeredGridView extends StaggeredGridView {

    public ScalableStaggeredGridView(Context context) {
        super(context);
        init(context);
    }

    public ScalableStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScalableStaggeredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        setOnTouchListener(new View.OnTouchListener() {

            private ScaleGestureDetector mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

            private float mScaleFactor = 1.f;

            public boolean onTouch(View v, MotionEvent event) {
                if (!mScaleDetector.onTouchEvent(event)) {
                    onTouchEvent(event);
                }
                return true;
            }

            class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    mScaleFactor *= detector.getScaleFactor();

                    // Don't let the object get too small or too large.
                    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

                    setScaleX(mScaleFactor);
                    setScaleY(mScaleFactor);
                    invalidate();
                    return true;
                }
            }
        });
    }
}
