package com.hackathon.sequoia.sequoiahackathon.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.global.SharedInfo;
import com.hackathon.sequoia.sequoiahackathon.global.Utilities;

/**
 * Created by sudarshanbhat on 22/01/15.
 */
public class FontedEditText extends EditText {

    public FontedEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray arr = context.obtainStyledAttributes(attributeSet,
                R.styleable.FontStyle);
        int fontEnum = arr
                .getInt(R.styleable.FontStyle_font, 0);

        Typeface typeface;
        String fontName = Utilities.getFontName(fontEnum);

        typeface = SharedInfo.getCustomFontTypeFace(context, fontName.toString());
        this.setTypeface(typeface);

        arr.recycle();
    }
}
