package com.example.ashrafulhassan.calculatorremind.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ashrafulhassan.calculatorremind.model.History;

import java.util.ArrayList;

/**
 * Created by Hassan M Ashraful on 4/22/2018.
 * Jr Software Developer
 * Innovizz Technology
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    // Database reference
    private SQLiteDatabase mDatabase;

    private static final String CALCULATOR_DATABASE_NAME = "CALCULATOR.DB";
    private static final int CALCULATOR_DATABASE_VERSION = 3;

    private static final String CALCULATOR_HISTORY_TABLE = "history_table";
    private static final String EXPRESSION = "EXPRESSION";
    private static final String RESULT = "RESULT";
    private static final String REMARKS = "REMARKS";
    private static final String TIME = "TIME";

    private static final String CALCULATOR_CREATE_QUERY = "CREATE TABLE "+ CALCULATOR_HISTORY_TABLE +"("+ EXPRESSION +" TEXT,"+ RESULT +" TEXT,"+ REMARKS +" TEXT,"+ TIME +" TEXT);";

    //private static final String CALCULATOR_CREATE_QUERY = "CREATE TABLE "+ StudentReport.StudentReportData.STUDENT_REPORT_TABEL_NAME +"("+ StudentReport.StudentReportData.STUDENT_NAME+" TEXT,"+ StudentReport.StudentReportData.STUDENT_IDS+" TEXT,"+ StudentReport.StudentReportData.CURRENT_DATE +" TEXT,"+ StudentReport.StudentReportData.STUDENT_VERDICT +" TEXT);";



    public DataBaseHelper(Context context){
        super(context, CALCULATOR_DATABASE_NAME, null, CALCULATOR_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CALCULATOR_CREATE_QUERY);
        Log.e("DATABASE OPERATION","Table created");

    }


    public void addHistory(String expression, String result, String remarks, String time){
        mDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPRESSION, expression);
        contentValues.put(RESULT, result);
        contentValues.put(REMARKS, remarks);
        contentValues.put(TIME, time);
        mDatabase.insert(CALCULATOR_HISTORY_TABLE, null, contentValues);
        mDatabase.close();
        Log.e("DATABASE OPERATION","one row inserted ");

    }


    public ArrayList<History> getHistory(){
        ArrayList<History> histories = new ArrayList<>();
        mDatabase = this.getReadableDatabase();
        Cursor cursor;
        String[] projection = {EXPRESSION, RESULT, REMARKS, TIME};
        cursor = mDatabase.query(CALCULATOR_HISTORY_TABLE, projection, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                String expression, results, remarks, time;
                expression = cursor.getString(0);
                results = cursor.getString(1);
                remarks = cursor.getString(2);
                time = cursor.getString(3);
                History history = new History(expression, results, remarks, time);
                histories.add(history);
            } while (cursor.moveToNext());
        }

        cursor.close(); mDatabase.close();

        return histories;

    }




    public void deleteStudentReport(){
        //String[] projection = {StudentReport.StudentReportData.STUDENT_NAME, StudentReport.StudentReportData.STUDENT_IDS, StudentReport.StudentReportData.CURRENT_DATE, StudentReport.StudentReportData.STUDENT_VERDICT };

        mDatabase = this.getWritableDatabase();
        mDatabase.delete(CALCULATOR_HISTORY_TABLE, null, null);

    }

    public void updateRemarks(String remarks, String time) {
        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REMARKS, remarks);

        mDatabase.update(CALCULATOR_HISTORY_TABLE, values, TIME + "=?", new String[]{time});
        mDatabase.close();
    }


   /* public Cursor getContact(String roll, SQLiteDatabase sqLiteDatabase){
        String[] projection = {StudentInfo.NewUserInfo.STUDENT_NAME, StudentInfo.NewUserInfo.STUDENT_MOB};
        String selection = StudentInfo.NewUserInfo.STUDENT_ROLL +" LIKE ?";
        String[] selection_args = {roll};
        Cursor cursor = sqLiteDatabase.query(StudentInfo.NewUserInfo.STUDENT_INFO_TABEL_NAME, projection, selection, selection_args,null,null,null);
        return cursor;
    }*/


  /*  public void deleteInformation(String roll, SQLiteDatabase sqLiteDatabase){
        String selection = StudentInfo.NewUserInfo.STUDENT_ROLL +" LIKE ?";
        String[] selection_args = {roll};
        sqLiteDatabase.delete(StudentInfo.NewUserInfo.STUDENT_INFO_TABEL_NAME, selection, selection_args);

    }*/


   /* public  int updateStudent(String old_roll, String new_roll, String new_name, String new_phn, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentInfo.NewUserInfo.STUDENT_NAME, new_name);
        contentValues.put(StudentInfo.NewUserInfo.STUDENT_ROLL, new_roll);
        contentValues.put(StudentInfo.NewUserInfo.STUDENT_MOB, new_phn);
        String selection = StudentInfo.NewUserInfo.STUDENT_ROLL + " LIKE ?";
        String[] selection_args = {old_roll};
        int count = sqLiteDatabase.update(StudentInfo.NewUserInfo.STUDENT_INFO_TABEL_NAME, contentValues, selection, selection_args);

        return count;
    }*/


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ CALCULATOR_HISTORY_TABLE);
        onCreate(db);
    }



}
