package com.test.dbtest.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.dbtest.R;

/**
 * Created by zhengmj on 19-4-8.
 */

public class AddDialog extends DialogFragment implements View.OnClickListener{
    private EditText et_id;
    private EditText et_name;
    private EditText et_age;
    private EditText et_job;
    private EditText et_phone;
    private TextView tv_confirm;
    private Database database;
    public static AddDialog newInstance(){
        return new AddDialog();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.dialog);
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad,container,false);
        et_id = view.findViewById(R.id.et_id);
        et_name = view.findViewById(R.id.et_name);
        et_age = view.findViewById(R.id.et_age);
        et_job = view.findViewById(R.id.et_job);
        et_phone = view.findViewById(R.id.et_phone);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
        database = new Database(getActivity());
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(et_id.getText())||TextUtils.isEmpty(et_phone.getText())||TextUtils.isEmpty(et_job.getText())||TextUtils.isEmpty(et_name.getText())||TextUtils.isEmpty(et_age.getText())){
                    return;
                }
                InfoEntity entity = new InfoEntity();
                entity.phone = et_phone.getText().toString();
                entity.job = et_job.getText().toString();
                entity.name = et_name.getText().toString();
                entity.id = Long.parseLong(et_id.getText().toString());
                entity.age = Integer.parseInt(et_age.getText().toString());
                if (database.insertData(entity)){
                    HomeActivity activity = (HomeActivity) getActivity();
                    activity.refresh();
                    dismiss();
                }
                break;
        }
    }
}
