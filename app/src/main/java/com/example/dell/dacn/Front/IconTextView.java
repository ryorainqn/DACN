package com.example.dell.dacn.Front;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by DELL on 18/10/17.
 */

public class IconTextView extends AppCompatTextView {
    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setType(context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public IconTextView(Context context) {
        super(context);
        setType(context);
    }

    private void setType(Context context) {
        Typeface typeFaceFont = FrontManager.getTypeface(context, FrontManager.FONTAWESOME);
        FrontManager.markAsIconContainer(this, typeFaceFont);
    }

}
