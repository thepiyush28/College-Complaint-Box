package com.example.complaintbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ComplaintDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "college_complaints.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_COMPLAINTS = "complaints";

    public static final String COL_ID = "id";
    public static final String COL_COMPLAINT_ID = "complaint_id";
    public static final String COL_STUDENT_NAME = "student_name";
    public static final String COL_CATEGORY = "category";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DATE = "date";
    public static final String COL_STATUS = "status";

    public ComplaintDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_COMPLAINTS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_COMPLAINT_ID + " TEXT, "
                + COL_STUDENT_NAME + " TEXT, "
                + COL_CATEGORY + " TEXT, "
                + COL_TITLE + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_DATE + " TEXT, "
                + COL_STATUS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINTS);
        onCreate(db);
    }

    public long insertComplaint(String complaintId, String studentName, String category, String title,
                                String description, String date, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COMPLAINT_ID, complaintId);
        values.put(COL_STUDENT_NAME, studentName);
        values.put(COL_CATEGORY, category);
        values.put(COL_TITLE, title);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_DATE, date);
        values.put(COL_STATUS, status);
        return db.insert(TABLE_COMPLAINTS, null, values);
    }

    public ArrayList<Complaint> getAllComplaints() {
        ArrayList<Complaint> complaints = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COMPLAINTS + " ORDER BY " + COL_ID + " DESC", null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    complaints.add(new Complaint(
                            cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPLAINT_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS))
                    ));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return complaints;
    }

    public Complaint getComplaintByComplaintId(String complaintId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_COMPLAINTS,
                null,
                COL_COMPLAINT_ID + "=?",
                new String[]{complaintId},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                return new Complaint(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPLAINT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS))
                );
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    public boolean deleteComplaint(String complaintId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_COMPLAINTS, COL_COMPLAINT_ID + "=?", new String[]{complaintId}) > 0;
    }
}
