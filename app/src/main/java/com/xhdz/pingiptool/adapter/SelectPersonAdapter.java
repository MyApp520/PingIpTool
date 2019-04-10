package com.xhdz.pingiptool.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xhdz.pingiptool.R;
import com.xhdz.pingiptool.bean.PersonBean;

import java.util.List;

/**
 * Created by smile on 2019/4/8.
 */

public class SelectPersonAdapter extends MultiSelectAdapter<PersonBean> {
    private List<PersonBean> data;

    public SelectPersonAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setTag(R.id.tv_item_people_name, item.getName());
        if (item.getIsSelectItem()) {
            helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_selected);
        } else {
            helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_normal);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder helper, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(helper, position, payloads);
        if (data == null || payloads.isEmpty()) {
            return;
        }
        if (data.get(position).getIsSelectItem()) {
            helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_selected);
        } else {
            helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_normal);
        }
    }

    public void setAdapterNewData(List<PersonBean> data) {
        if (data == null) {
            return;
        }
        this.data = data;
        setNewData(data);
    }
}
