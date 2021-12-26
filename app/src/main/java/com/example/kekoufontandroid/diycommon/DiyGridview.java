package com.example.kekoufontandroid.diycommon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class DiyGridview extends GridView {
    public DiyGridview(Context context) {
        super(context);
    }

    public DiyGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiyGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //修改子控件需要多大高度就扩展多大高度
        int exp = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, exp);

    }
}
