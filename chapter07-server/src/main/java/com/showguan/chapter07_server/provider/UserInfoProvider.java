package com.showguan.chapter07_server.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.showguan.chapter07_server.database.UserDBHelper;

public class UserInfoProvider extends ContentProvider {
    private static String TAG = "Kennem";
    private UserDBHelper dbHelper;
    private static final int USER = 1;
    private static final int USERS = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user/#", USER);
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user", USERS);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Log.d(TAG, "ContentProvider onCreate: ");
        dbHelper = UserDBHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert(UserDBHelper.TABLE_NAME, null, values);
//            if(rowId > 0){
//                Uri newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, rowId);
//                getContext().getContentResolver().notifyChange(newUri, null);
//            }
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (URI_MATCHER.match(uri) == USERS) {
            Log.d(TAG, "UserInfoProvider query: ");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(UserDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            return cursor;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case USER:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                count = db1.delete(UserDBHelper.TABLE_NAME, "_id = ?", new String[]{id});
                db1.close();
                break;
            case USERS:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                count = db2.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
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