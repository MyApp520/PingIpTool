package com.xhdz.pingiptool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by smile on 2019/4/8.
 */

public abstract class MultiSelectAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public MultiSelectAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }
}
