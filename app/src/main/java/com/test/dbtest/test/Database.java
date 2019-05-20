package com.test.dbtest.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by zhengmj on 19-4-8.
 */

public class Database {
    public static final String Id = "id";
    public static final String Name = "name";
    public static final String Phone = "tel_no";
    public static final String Age = "age";
    private SQLiteDbHelper dbHelper;
    public Database(Context context){
        dbHelper = new SQLiteDbHelper(context);
    }
    public SQLiteDatabase getDatabase(){
        return dbHelper.getWritableDatabase();
    }
    public String getTableName(){
        return SQLiteDbHelper.TABLE_NAME;
    }
    public boolean insertData(InfoEntity entity){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name,entity.name);
        contentValues.put(Age,entity.age);
        contentValues.put(Phone,entity.phone);
        try {
            getDatabase().insert(getTableName(),null,contentValues);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public ArrayList<InfoEntity> queryAll(){
        return queryData(null,null,null,null,null,null);
    }
    public ArrayList<InfoEntity> queryData(String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
        ArrayList<InfoEntity> result = new ArrayList<>();
        Cursor cursor = getDatabase().query(getTableName(),columns,selection,selectionArgs,groupBy,having,orderBy);
        while (cursor.moveToNext()){
            InfoEntity entity = new InfoEntity();
            entity.id = cursor.getLong(cursor.getColumnIndex(Id));
            entity.name = cursor.getString(cursor.getColumnIndex(Name));
            entity.age = cursor.getInt(cursor.getColumnIndex(Age));
            entity.phone = cursor.getString(cursor.getColumnIndex(Phone));
            result.add(entity);
        }
        cursor.close();
        return result;
    }
    public void update(long id,@NonNull InfoEntity entity){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name,entity.name);
        contentValues.put(Age,entity.age);
        contentValues.put(Phone,entity.phone);
        getDatabase().update(getTableName(),contentValues,"id = ?",new String[]{String.valueOf(id)});
    }
    public void delete(long id){
        getDatabase().delete(getTableName(),"id = ?",new String[]{String.valueOf(id)});
    }
    private class SQLiteDbHelper extends SQLiteOpenHelper{

        public static final String DB_NAME = "testdb.db";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "testTable";
        public static final String CREATE_TABLE = "create table "+TABLE_NAME+" ( id integer primary key autoincrement," +
                "name varchar(20) not null," +
                "tel_no varchar(11) not null," +
                "age interger default 0" +
                ");";
        public SQLiteDbHelper(Context context){
            this(context,DB_NAME,null,DB_VERSION);
        }
        public SQLiteDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public SQLiteDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d("120","SQLiteOpenHelper onCreate");
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            Log.d("120","SQLiteOpenHelper onUpgrade");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            Log.d("120","SQLiteOpenHelper onOpen");

        }
    }
}
