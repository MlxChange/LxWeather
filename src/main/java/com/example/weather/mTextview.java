package com.example.weather;

import android.content.Context;
import android.service.voice.AlwaysOnHotwordDetector;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/1/24.
 */

public class mTextview extends TextView {
    public mTextview(Context context) {
        super(context);
    }

    public mTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
