package subbiah.veera.gringotts;

import android.os.Build;
import android.util.Log;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class Logger {
    private static boolean isAppDebuggable = BuildConfig.DEBUG;
    private Logger() {}

    public static void d(String tag, String msg) {
        if(isAppDebuggable) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if(isAppDebuggable) Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
    }
}
