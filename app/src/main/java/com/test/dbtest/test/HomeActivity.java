package com.test.dbtest.test;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.test.dbtest.R;

/**
 * Created by zhengmj on 19-4-8.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private Database database;
    private RecyclerView rv_list;
    private HomeListAdapter adapter;
    private TextView tv_add;
    private TextView tv_search;
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_job;
    private TextView tv_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_age.setOnClickListener(this);
        tv_job = (TextView) findViewById(R.id.tv_job);
        tv_job.setOnClickListener(this);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_id.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setOnClickListener(this);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeListAdapter();
        rv_list.setAdapter(adapter);
        database = new Database(this);
        adapter.setList(database.queryAll());
        adapter.setClickCallback(new HomeListAdapter.ClickCallback() {
            @Override
            public void onClick(int position) {
                InfoEntity entity = adapter.getItemOn(position);
                SelectFragment fragment = SelectFragment.newInstance(entity.id);
                fragment.show(HomeActivity.this.getSupportFragmentManager(),"");
            }
        });
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
    }

    public void refresh(){
        adapter.setList(database.queryAll());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add:
                AddDialog dialog = AddDialog.newInstance();
                dialog.show(getSupportFragmentManager(),"");
                break;
            case R.id.tv_id:
                adapter.setList(database.queryData(null,null,null,null,null,Database.Id));
                break;
            case R.id.tv_name:
                adapter.setList(database.queryData(null,null,null,null,null,Database.Name));
                break;
            case R.id.tv_age:
                adapter.setList(database.queryData(null,null,null,null,null,Database.Age));
                break;
            case R.id.tv_job:
                adapter.setList(database.queryData(null,null,null,null,null,Database.Job));
                break;
            case R.id.tv_phone:
                adapter.setList(database.queryData(null,null,null,null,null,Database.Phone));
                break;
            case R.id.tv_search:
                SearchFragment fragment = SearchFragment.newInstance();
                fragment.show(getSupportFragmentManager(),"");
                break;
        }
    }
}
