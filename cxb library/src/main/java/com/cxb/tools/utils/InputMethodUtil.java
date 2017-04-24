package com.cxb.tools.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public final class InputMethodUtil {

    //开关输入法
    public final static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
