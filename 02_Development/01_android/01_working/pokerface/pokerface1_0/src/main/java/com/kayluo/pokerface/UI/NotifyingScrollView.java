package com.kayluo.pokerface.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by cxm170 on 2015/5/31.
 */
public class NotifyingScrollView extends ScrollView {

    private static final float HIDE_THRESHOLD = 10;
    private static final float SHOW_THRESHOLD = 70;

    private int mToolbarOffset = 0;
    private boolean mControlsVisible = true;
    public int mToolbarHeight;


    public interface OnScrollChangedListener {
//        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
        void onMoved(int distance);
        void onShow();
        void onHide();
    }

    private OnScrollChangedListener mOnScrollChangedListener;



    public NotifyingScrollView(Context context) {
        super(context);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        if (mOnScrollChangedListener != null) {
//            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
//        }
        int dy = t - oldt;
        Log.i("scrollview dy :",dy+"");
        clipToolbarOffset();
        mOnScrollChangedListener.onMoved(mToolbarOffset);

        if((mToolbarOffset <mToolbarHeight && dy>0) || (mToolbarOffset >0 && dy<0)) {
            mToolbarOffset += dy;
        }
        Log.i("mToolbarOffset:",mToolbarOffset + "");

        if (mControlsVisible) {
            if (mToolbarOffset > HIDE_THRESHOLD) {
                setInvisible();
            } else {
                setVisible();
            }
        } else {
            if ((mToolbarHeight - mToolbarOffset) > SHOW_THRESHOLD) {
                setVisible();
            } else {
                setInvisible();
            }
        }


    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    private void clipToolbarOffset() {
        if(mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        } else if(mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }

    private void setVisible() {
        if(mToolbarOffset > 0) {
            mOnScrollChangedListener.onShow();
            mToolbarOffset = 0;
        }
        mControlsVisible = true;
    }

    private void setInvisible() {
        if(mToolbarOffset < mToolbarHeight) {
            mOnScrollChangedListener.onHide();
            mToolbarOffset = mToolbarHeight;
        }
        mControlsVisible = false;
    }


}
