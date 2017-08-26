package subbiah.veera.gringotts.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import subbiah.veera.gringotts.R;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

public class KeyStore {

    @SuppressWarnings("SameParameterValue")
    @SuppressLint("ApplySharedPref")
    public static void write(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @SuppressWarnings("SameParameterValue")
    public static String read(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
}
