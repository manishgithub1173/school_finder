package com.hackathon.sequoia.sequoiahackathon.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.global.SharedInfo;
import com.hackathon.sequoia.sequoiahackathon.global.Utilities;

public class FontedTextView extends TextView {
	
	public FontedTextView(Context context, String fontName) {
		super(context);
		
		Typeface typeface;
		typeface = SharedInfo.getCustomFontTypeFace(context, fontName.toString());
		this.setTypeface(typeface);
		
	}

	public FontedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray arr = context.obtainStyledAttributes(attrs,
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
