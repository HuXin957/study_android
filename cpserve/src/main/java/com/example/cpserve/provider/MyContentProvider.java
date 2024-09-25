package com.example.cpserve.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.cpserve.database.UserDBHelper;

public class MyContentProvider extends ContentProvider {
    private UserDBHelper dbHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USERS = 1;
    private static final int USER = 2;

    static {
        // 往Uri匹配器中添加指定数据路径
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user", USERS);
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user/#", USER);
    }

    @Override
    public boolean onCreate() {
        dbHelper = UserDBHelper.getInstance(getContext());
        Log.d("MyContentProvider", "onCREATE");
        return false;
    }

    // content://com.example.cpserve.provider.MyContentProvider/user
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(UserDBHelper.TABLE_NAME, null, values);
        }
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
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            // content://com.example.cpserve.provider.MyContentProvider/user
            // 删除多行
            case USERS:
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                count = db1.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
                db1.close();
                break;

            // content://com.example.cpserve.provider.MyContentProvider/user/2
            // 删除单行
            case USER:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                count = db2.delete(UserDBHelper.TABLE_NAME, "id=?", new String[]{id});
                db2.close();
                break;

        }

        return count;
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