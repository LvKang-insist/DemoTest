package com.example.demotest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 345 QQ:1831712732
 * @name GridDivider
 * @package com.example.demotest
 * @time 2022/02/22 16:06
 * @description
 */


public class GridDivider extends RecyclerView.ItemDecoration {

    private int spacing;
    private boolean isIncludeEdge;
    private int vertivalSpacing;
    private Drawable mDivider;

    public GridDivider(int spacing) {
        this.spacing = spacing;
        this.isIncludeEdge = false;
        this.vertivalSpacing = spacing;
    }

    public GridDivider(int spacing, int color) {
        this.spacing = spacing;
        this.isIncludeEdge = false;
        this.vertivalSpacing = spacing;
        this.mDivider = new ColorDrawable(color);
    }

    public GridDivider(int spacing, boolean isIncludeEdge) {
        this.spacing = spacing;
        this.isIncludeEdge = isIncludeEdge;
        this.vertivalSpacing = spacing;
    }

    public GridDivider(int spacing, int vertivalSpacing, boolean isIncludeEdge) {
        this.spacing = spacing;
        this.isIncludeEdge = isIncludeEdge;
        this.vertivalSpacing = vertivalSpacing;
    }

    public GridDivider(int spacing, int vertivalSpacing, int color, boolean isIncludeEdge) {
        this.spacing = spacing;
        this.isIncludeEdge = isIncludeEdge;
        this.vertivalSpacing = vertivalSpacing;
        this.mDivider = new ColorDrawable(color);
    }

    public void setDivider(Drawable divider) {
        this.mDivider = divider;
    }

    public void setColorDivider(int color) {
        this.mDivider = new ColorDrawable(color);
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setVertivalSpacing(int vertivalSpacing) {
        this.vertivalSpacing = vertivalSpacing;
    }

    public void setIncludeEdge(boolean isIncludeEdge) {
        this.isIncludeEdge = isIncludeEdge;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider != null) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        final int spanCount = getSpanCount(parent);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right;
            if (!isIncludeEdge && i != 0 && (i + 1) % spanCount == 0) {
                right = child.getRight() + params.rightMargin;
            } else {
                right = child.getRight() + params.rightMargin + spacing;
            }
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + vertivalSpacing;

            final int lastRaw = childCount % spanCount == 0 ? childCount - spanCount : childCount - childCount % spanCount;
            if (isIncludeEdge || i < lastRaw) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }

            if (isIncludeEdge && i < spanCount) {
                final int edgeTop = child.getTop() - params.topMargin - vertivalSpacing;
                final int edgeBottom = child.getTop() - params.topMargin;
                final int edgeLeft = i == 0 ? left - spacing : left;
                mDivider.setBounds(edgeLeft, edgeTop, right, edgeBottom);
                mDivider.draw(c);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        final int spanCount = getSpanCount(parent);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + spacing;

            if (isIncludeEdge || i == 0 || (i + 1) % spanCount != 0) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }

            if (isIncludeEdge && i % spanCount == 0) {
                final int edgeBottom = child.getBottom() + params.bottomMargin + vertivalSpacing;
                final int edgeLeft = child.getLeft() - params.leftMargin - spacing;
                final int edgeRight = child.getLeft() - params.leftMargin;
                mDivider.setBounds(edgeLeft, top, edgeRight, edgeBottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int spanCount = getSpanCount(parent);
        int column = position % spanCount; // item column

        if (isIncludeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = vertivalSpacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f / spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = vertivalSpacing; // item top
            }
        }
    }
}