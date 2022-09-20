package com.example.demotest.recycler;

/**
 * @author 345 QQ:1831712732
 * @name NestedScrollableHost
 * @package com.example.demotest.recycler
 * @time 2022/06/19 21:48
 * @description
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 此类用于解决 ViewPager2  嵌套 ViewPager2 或者 RecyclerView 等相互嵌套的冲突问题，
 */
public class NestedScrollableHost extends FrameLayout {

    private int touchSlop;
    private float initialX;
    private float initialY;
    private HashMap _$_findViewCache;

    private final ViewPager2 getParentViewPager() {
        ViewParent var10000 = this.getParent();
        if (!(var10000 instanceof View)) {
            var10000 = null;
        }

        View v;
        for (v = (View) var10000; v != null && !(v instanceof ViewPager2); v = (View) var10000) {
            var10000 = v.getParent();
            if (!(var10000 instanceof View)) {
                var10000 = null;
            }
        }

        View var2 = v;
        if (!(v instanceof ViewPager2)) {
            var2 = null;
        }

        return (ViewPager2) var2;
    }

    private final View getChild() {
        return this.getChildCount() > 0 ? this.getChildAt(0) : null;
    }

    private final boolean canChildScroll(int orientation, float delta) {
        boolean var5 = false;
        int direction = -((int) Math.signum(delta));
        View var10000;
        boolean var6 = false;
        switch (orientation) {
            case 0:
                var10000 = this.getChild();
                var6 = var10000 != null ? var10000.canScrollHorizontally(direction) : false;
                break;
            case 1:
                var10000 = this.getChild();
                var6 = var10000 != null ? var10000.canScrollVertically(direction) : false;
                break;
            default:
                // throw (Throwable)(new IllegalArgumentException());
        }

        return var6;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        this.handleInterceptTouchEvent(e);
        return super.onInterceptTouchEvent(e);
    }

    private final void handleInterceptTouchEvent(MotionEvent e) {
        ViewPager2 var10000 = this.getParentViewPager();
        if (var10000 != null) {
            int orientation = var10000.getOrientation();
            if (this.canChildScroll(orientation, -1.0F) || this.canChildScroll(orientation, 1.0F)) {
                if (e.getAction() == 0) {
                    this.initialX = e.getX();
                    this.initialY = e.getY();
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                } else if (e.getAction() == 2) {
                    float dx = e.getX() - this.initialX;
                    float dy = e.getY() - this.initialY;
                    boolean isVpHorizontal = orientation == 0;
                    boolean var8 = false;
                    float scaledDx = Math.abs(dx) * (isVpHorizontal ? 0.5F : 1.0F);
                    boolean var9 = false;
                    float scaledDy = Math.abs(dy) * (isVpHorizontal ? 1.0F : 0.5F);
                    if (scaledDx > (float) this.touchSlop || scaledDy > (float) this.touchSlop) {
                        if (isVpHorizontal == scaledDy > scaledDx) {
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        } else if (this.canChildScroll(orientation, isVpHorizontal ? dx : dy)) {
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }

            }
        }
    }

    public NestedScrollableHost(Context context) {
        super(context);
        ViewConfiguration var10001 = ViewConfiguration.get(this.getContext());
        this.touchSlop = var10001.getScaledTouchSlop();
    }

    public NestedScrollableHost(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration var10001 = ViewConfiguration.get(this.getContext());
        this.touchSlop = var10001.getScaledTouchSlop();
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View) this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}
