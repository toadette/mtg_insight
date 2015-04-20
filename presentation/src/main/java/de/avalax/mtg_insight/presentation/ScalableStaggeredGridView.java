package de.avalax.mtg_insight.presentation;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;

import com.etsy.android.grid.StaggeredGridView;

public class ScalableStaggeredGridView extends StaggeredGridView {

    private float scaleFactor;
    private ScaleGestureDetector scaleDetector;

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

    private void init(Context context) {
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        scaleFactor = 1.f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_MOVE) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (scaleFactor == 1.f) {
                setLayoutParams(new LinearLayout.LayoutParams(getWidth(), getHeight()));
            }
            scaleFactor *= detector.getScaleFactor();

            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            setPivotX(0);
            setPivotY(0);
            setScaleX(scaleFactor);
            setScaleY(scaleFactor);
            invalidate();
            return true;
        }
    }
}
