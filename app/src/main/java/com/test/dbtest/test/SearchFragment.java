package com.test.dbtest.test;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.test.dbtest.R;

import java.util.ArrayList;

/**
 * Created by zhengmj on 19-4-8.
 */

public class SearchFragment extends DialogFragment implements View.OnClickListener{
    private EditText et_search;
    private TextView tv_search;
    private RecyclerView rv_list;
    private HomeListAdapter adapter;
    private Spinner spinner;
    private LinearLayout ll_result;
    private String[] keys = new String[]{Database.Id,Database.Name,Database.Age,Database.Phone};
    private String key;
    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        spinner = view.findViewById(R.id.spinner);
        rv_list = view.findViewById(R.id.rv_list);
        adapter = new HomeListAdapter();
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list.setAdapter(adapter);
        ll_result = view.findViewById(R.id.ll_result);
        et_search = view.findViewById(R.id.et_search);
        tv_search = view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.item_spinner,new String[]{"ID","姓名","年龄","电话号码"});

        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                key = keys[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                if (TextUtils.isEmpty(et_search.getText())){
                    return;
                }
                Database database = new Database(getActivity());
                ArrayList<InfoEntity> list = database.queryData(null,key+" = ?",new String[]{et_search.getText().toString()},null,null,null);
                if (list!=null&&list.size()>0){
                    ll_result.setVisibility(View.VISIBLE);
                    adapter.setList(list);
                }else {
                    ll_result.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"搜索结果不存在",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
