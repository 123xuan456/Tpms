package com.reeching.tpms.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by 齐绍轩1 on 2017/7/19.
 */

public class ProviderUtils {
    public static void deleteData(Context paramContext, Uri paramUri, String paramString, String[] paramArrayOfString)
    {
        paramContext.getContentResolver().delete(paramUri, paramString, paramArrayOfString);
    }
    public static boolean existData(Context paramContext, Uri paramUri, ContentValues paramContentValues, String[] paramArrayOfString)
    {
        ContentResolver localContentResolver = paramContext.getContentResolver();
        int i = paramArrayOfString.length;
        String str = null;
        String[] arrayOfString = null;
        if (i > 0)
        {
            str = new String();
            arrayOfString = new String[paramArrayOfString.length];
        }
        int j = 0;
        Cursor localCursor;
        if (j >= paramArrayOfString.length)
        {
            localCursor = localContentResolver.query(paramUri, null, str, arrayOfString, null);
            localCursor.moveToFirst();
            if (localCursor.getCount() > 0)
            {
                localCursor.close();
                return true;
            }
        }
        else
        {
            if (paramArrayOfString.length - j > 1);
            for (str = str + paramArrayOfString[j] + "=? and "; ; str = str + paramArrayOfString[j] + "=?")
            {
                arrayOfString[j] = paramContentValues.getAsString(paramArrayOfString[j]);
                j++;
            }
        }
        localCursor.close();
        return false;
    }

    public static Uri insertData(Context paramContext, Uri paramUri, ContentValues paramContentValues)
    {
        return paramContext.getContentResolver().insert(paramUri, paramContentValues);
    }


    public static boolean undateData(Context paramContext, Uri paramUri, ContentValues paramContentValues, String[] paramArrayOfString)
    {
        ContentResolver localContentResolver = paramContext.getContentResolver();
        int i = paramArrayOfString.length;
        String str = null;
        String[] arrayOfString = null;
        if (i > 0)
        {
            str = new String();
            arrayOfString = new String[paramArrayOfString.length];
        }
        int j = 0;
        if (j >= paramArrayOfString.length)
        {
            localContentResolver.update(paramUri, paramContentValues, str, arrayOfString);
            return true;
        }
        if (paramArrayOfString.length - j > 1);
        for (str = str + paramArrayOfString[j] + "=? and "; ; str = str + paramArrayOfString[j] + "=?")
        {
            arrayOfString[j] = paramContentValues.getAsString(paramArrayOfString[j]);
            j++;
            break;
        }
        return false;
    }


}
