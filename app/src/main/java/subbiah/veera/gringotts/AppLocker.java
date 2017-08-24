package subbiah.veera.gringotts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

public class AppLocker extends Service {

    private static final String TAG = "AppLocker";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        List<ListModel> list = ((Gringotts)getApplicationContext()).getData();
        Logger.e(TAG, String.valueOf(list.size()));
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
