package com.example.kekoufontandroid.diycommon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.SearchResultAdapter;

public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;

    public SimplePaddingDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.line_color,null));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.line_height);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
        int position = parent.getChildAdapterPosition(view);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        SearchResultAdapter adapter = (SearchResultAdapter)parent.getAdapter();
        int subjectLen = adapter.getSubjectLen();

        View view = parent.getChildAt(subjectLen-1);

//        long childItemId = parent.getChildItemId(view);
//        Log.d("test","childItemId"+childItemId);
        if (view!=null) {
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
//        for (int i = 0; i < childCount - 1; i++) {
//            View view = parent.getChildAt(i);
//            float top = view.getBottom();
//            float bottom = view.getBottom() + dividerHeight;
//            c.drawRect(left, top, right, bottom, dividerPaint);
//        }
    }
}