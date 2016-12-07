package com.cxb.tools.utils;

import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

/**
 * 列表固定高度的高度计算工具类（只用于Item高度固定时）
 */

public class ListFixedHeightUtils {

    public static void getListViewHeight(ListView listView, int heightPx, int itemCount) {
        int totalHeight = heightPx * itemCount;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    public static void getGridViewHeight(GridView gridView, int numColumns, int heightPx, int itemCount) {
        int totalHeight = heightPx * (itemCount / numColumns + (itemCount % numColumns == 0 ? 0 : 1));
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

}
