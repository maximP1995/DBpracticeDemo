package com.test.dbtest.test;

import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.dbtest.R;

/**
 * Created by zhengmj on 19-4-8.
 */

public class SelectFragment extends DialogFragment implements View.OnClickListener{
    public final static String ID_KEY = "idkey";
    private long id;
    private TextView tv_delete;
    private TextView tv_update;
    private TextView tv_confirm;
//    private EditText et_id;
    private EditText et_name;
    private EditText et_age;
//    private EditText et_job;
    private EditText et_phone;
    private LinearLayout ll_update;
    public static SelectFragment newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong(ID_KEY,id);
        SelectFragment fragment = new SelectFragment();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_select,container,false);
        Bundle bundle = getArguments();
        if (bundle!=null){
            id = bundle.getLong(ID_KEY);
        }
//        et_id = view.findViewById(R.id.et_id);
        et_name = view.findViewById(R.id.et_name);
        et_age = view.findViewById(R.id.et_age);
//        et_job = view.findViewById(R.id.et_job);
        et_phone = view.findViewById(R.id.et_phone);
        tv_delete = view.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);
        tv_update = view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        ll_update = view.findViewById(R.id.ll_update);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_delete:
                Database database = new Database(getActivity());
                database.delete(id);
                HomeActivity activity = (HomeActivity) getActivity();
                activity.refresh();
                dismiss();
                break;
            case R.id.tv_update:
                ll_update.setVisibility(View.VISIBLE);
                Database ss = new Database(getActivity());
                Cursor cursor = ss.getDatabase().query(ss.getTableName(),null,"id = ?",new String[]{String.valueOf(id)},null,null,null);
                if (cursor.moveToNext()){
//                    et_id.setText(String.valueOf(cursor.getLong(cursor.getColumnIndex(Database.Id))));
                    et_age.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Database.Age))));
                    et_name.setText(cursor.getString(cursor.getColumnIndex(Database.Name)));
                    et_phone.setText(cursor.getString(cursor.getColumnIndex(Database.Phone)));
                }
                break;
            case R.id.tv_confirm:
                Database database1 = new Database(getActivity());
                InfoEntity entity = new InfoEntity();
                if (TextUtils.isEmpty(et_phone.getText())||TextUtils.isEmpty(et_name.getText())||TextUtils.isEmpty(et_age.getText())){
                    return;
                }
                entity.phone = et_phone.getText().toString();
//                entity.job = et_job.getText().toString();
                entity.name = et_name.getText().toString();
//                entity.id = Long.parseLong(et_id.getText().toString());
                entity.age = Integer.parseInt(et_age.getText().toString());
                database1.update(id,entity);
                HomeActivity activity1 = (HomeActivity) getActivity();
                activity1.refresh();
                dismiss();
                break;
        }
    }
}
