package com.xhdz.pingiptool.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xhdz.pingiptool.R;
import com.xhdz.pingiptool.adapter.CheckBoxMultiSelectAdapter;
import com.xhdz.pingiptool.adapter.MultiSelectAdapter;
import com.xhdz.pingiptool.adapter.OneSelectAdapter;
import com.xhdz.pingiptool.bean.PersonBean;
import com.xhdz.pingiptool.bean.StudentBean;

import java.util.ArrayList;
import java.util.List;

public class PeopleListActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView_people;
    private MultiSelectAdapter<PersonBean> multiSelectAdapter;
    private CheckBoxMultiSelectAdapter checkBoxMultiSelectAdapter;
    private OneSelectAdapter oneSelectAdapter;
    private List<PersonBean> personBeanData;
    private List<StudentBean> studentBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        recyclerView_people = findViewById(R.id.recyclerView_people);
        recyclerView_people.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_people.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        initCheckBoxSelectAdapter();

        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxMultiSelectAdapter == null) {
                    return;
                }
                for (StudentBean studentBean : checkBoxMultiSelectAdapter.getStudentBeanList()) {
                    studentBean.setIsChecked(false);
                }
                checkBoxMultiSelectAdapter.notifyDataSetChanged();

                if (checkBoxMultiSelectAdapter.isHasCloseMultiSelect()) {
                    checkBoxMultiSelectAdapter.setCloseMultiSelect(false);
                    tvTitle.setText("联系人列表---多选");
                } else {
                    checkBoxMultiSelectAdapter.setCloseMultiSelect(true);
                    tvTitle.setText("联系人列表---单选");
                }
            }
        });
    }

    private void initOneSelectAdapter() {
        oneSelectAdapter = new OneSelectAdapter(R.layout.item_student_list, null);
        oneSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (StudentBean studentBean : oneSelectAdapter.getStudentBeanList()) {
                    if (studentBean.getIsChecked()) {
                        Log.e(TAG, "onItemClick: 选择了哪几个 = " + studentBean.getName());
                    }
                }
            }
        });
        recyclerView_people.setAdapter(oneSelectAdapter);
    }

    private void initCheckBoxSelectAdapter() {
        checkBoxMultiSelectAdapter = new CheckBoxMultiSelectAdapter(R.layout.item_student_list, null);
        checkBoxMultiSelectAdapter.setCloseMultiSelect(false);
        checkBoxMultiSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (StudentBean studentBean : checkBoxMultiSelectAdapter.getStudentBeanList()) {
                    if (studentBean.getIsChecked()) {
                        Log.e(TAG, "onItemClick: 选择了哪几个 = " + studentBean.getName());
                    }
                }
            }
        });
        recyclerView_people.setAdapter(checkBoxMultiSelectAdapter);
    }

    private void initMultiSelectAdapter() {
        multiSelectAdapter = new MultiSelectAdapter<PersonBean>(R.layout.item_people_list, null) {
            @Override
            protected void convert(BaseViewHolder helper, PersonBean item) {
                helper.setText(R.id.tv_item_people_name, item.getName());
                if (item.getIsSelectItem()) {
                    helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_selected);
                } else {
                    helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_normal);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder helper, int position, @NonNull List<Object> payloads) {
                if (personBeanData == null || payloads.isEmpty()) {
                    onBindViewHolder(helper, position);
                    return;
                }
                if (personBeanData.get(position).getIsSelectItem()) {
                    helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_selected);
                } else {
                    helper.setImageResource(R.id.image_item_people_select, R.drawable.blue_check_normal);
                }
            }
        };
        multiSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (personBeanData == null || position == -1 || position >= personBeanData.size()) {
                    return;
                }
                if (personBeanData.get(position).getIsSelectItem()) {
                    personBeanData.get(position).setIsSelectItem(false);
                } else {
                    personBeanData.get(position).setIsSelectItem(true);
                }
                multiSelectAdapter.notifyItemChanged(position, "select_item");
            }
        });
        recyclerView_people.setAdapter(multiSelectAdapter);

        personBeanData = new ArrayList<>();
        PersonBean personBean;
        for (int i = 0; i < 20000; i++) {
            personBean = new PersonBean();
            personBean.setName("我是 " + i + " 号选手");
            personBean.setIsSelectItem(false);
            personBeanData.add(personBean);
        }
        multiSelectAdapter.setNewData(personBeanData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentBeanList = new ArrayList<>();
        StudentBean studentBean;
        for (int i = 0; i < 200000; i++) {
            studentBean = new StudentBean();
            studentBean.setName("我是 " + i + " 号学员");
            studentBeanList.add(studentBean);
        }
        checkBoxMultiSelectAdapter.setAdapterData(studentBeanList);
    }
}
