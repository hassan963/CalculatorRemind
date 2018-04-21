package com.example.ashrafulhassan.calculatorremind.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ashraful Hassan on 4/21/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String database_Name="CALCULATOR_DB";
    private static final int database_Version=2;
    private static final String TAG="DATABASE OPERATIONS";
    private static final String table_Name="history";
    private static final String column1="calculator_name";
    private static final String column2="expression";
    private static final String create_Table="CREATE TABLE "+table_Name+"("+column1+" TEXT,"+column2+" TEXT);";

    private SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, database_Name,null,database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db.execSQL(create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '" + table_Name + "'");
        onCreate(db);
    }

    public void insert(String calcName,String expression)
    {
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(column1,calcName);
        contentValues.put(column2, expression);
        db.insert(table_Name, null, contentValues);
        db.close();
    }

    public ArrayList<String> showHistory(String calcName)
    {
        db=getReadableDatabase();
        Cursor cursor;
        ArrayList<String> list=new ArrayList<String>();
        String []selectionArgs={calcName};
        //cursor=db.query(table_Name,columns,column1+" LIKE ?",selectionArgs,null,null,null);
        cursor=db.rawQuery("select * from "+table_Name+" where "+column1+" = ?",selectionArgs);
        if(cursor.moveToFirst())
        {
            do
            {
                String expression=cursor.getString(1);
                list.add(expression);
            }while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public void deleteRecords(String calcName)
    {
        db=getWritableDatabase();
        String value[]={calcName};
        int i=db.delete(table_Name, column1+"=?", value);
        db.close();
    }
}
