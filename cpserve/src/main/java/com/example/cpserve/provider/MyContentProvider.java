package com.example.cpserve.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.cpserve.database.UserDBHelper;

public class MyContentProvider extends ContentProvider {
    private UserDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = UserDBHelper.getInstance(getContext());
        Log.d("MyContentProvider", "onCREATE");
        return false;
    }

    // content://com.example.cpserve.provider.MyContentProvider/user
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(UserDBHelper.TABLE_NAME, null, values);
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(UserDBHelper.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null, null);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}