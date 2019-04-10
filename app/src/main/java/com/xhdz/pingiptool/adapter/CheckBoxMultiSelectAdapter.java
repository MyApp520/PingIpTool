package com.xhdz.pingiptool.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xhdz.pingiptool.R;
import com.xhdz.pingiptool.bean.StudentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smile on 2019/4/9.
 */

public class CheckBoxMultiSelectAdapter extends BaseQuickAdapter<StudentBean, BaseViewHolder> {
    private List<StudentBean> studentBeanList;
    private boolean isHasCloseMultiSelect;
    /**
     * 当前选中条目的position
     */
    private int currentCheckedPosition = -1;

    public CheckBoxMultiSelectAdapter(int layoutResId, @Nullable List<StudentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, StudentBean item) {
        final int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_item_student_name, item.getName());
        CheckBox checkBox = helper.getView(R.id.checkbox_item_student);

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(studentBeanList.get(position).getIsChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG, "onCheckedChanged: isChecked = " + isChecked + ", currentCheckedPosition = " + currentCheckedPosition);
                if (isChecked) {
                    if (isHasCloseMultiSelect) {
                        if (-1 != currentCheckedPosition) {
                            studentBeanList.get(currentCheckedPosition).setIsChecked(false);
                            //注意：如果currentCheckedPosition指定的条目是不可见的，调用notifyItemChanged是无效的，无法刷新
                            notifyItemChanged(currentCheckedPosition, "unchecked_item");
                        }
                        currentCheckedPosition = position;
                    }
                } else if (currentCheckedPosition == position) {
                    // 点击的是取消当前选中的条目
                    currentCheckedPosition = -1;
                }
                studentBeanList.get(position).setIsChecked(isChecked);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder helper, int position, @NonNull List<Object> payloads) {
        Log.e(TAG, "刷新一个条目: payloads = " + payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(helper, position);
            return;
        }
        if ("unchecked_item".equals(payloads.get(0))) {
            Log.e(TAG, "刷新未选中的条目: position = " + position);
            CheckBox checkBox = helper.getView(R.id.checkbox_item_student);
            checkBox.setChecked(false);
        }
    }

    public void setAdapterData(List<StudentBean> data) {
        if (data == null) {
            return;
        }
        studentBeanList = data;
        setNewData(data);
    }

    public List<StudentBean> getStudentBeanList() {
        if (studentBeanList == null) {
            return new ArrayList<>();
        }
        return studentBeanList;
    }

    /**
     * 当前选中条目的position
     */
    public int getCurrentCheckedPosition() {
        return currentCheckedPosition;
    }

    /**
     * 设置是否关闭多选功能
     *
     * @param isCloseMultiSelect
     */
    public void setCloseMultiSelect(boolean isCloseMultiSelect) {
        this.isHasCloseMultiSelect = isCloseMultiSelect;
    }

    public boolean isHasCloseMultiSelect() {
        return isHasCloseMultiSelect;
    }
}
