package subbiah.veera.gringotts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

public class AppLocker extends Service {

    private static final String TAG = "AppLocker";
    private Thread worker;
    private volatile boolean shouldContinue;
    private static List<ListModel> list;

    @Override
    public void onCreate() {
        super.onCreate();

        shouldContinue = true;
        if(worker == null || !worker.isAlive()) {
            worker = getNewThread();
            worker.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public boolean stopService(Intent name) {
        shouldContinue = false;
        worker = null;
        return super.stopService(name);
    }

    public Thread getNewThread() {
        return new Thread("AppLocker Worker") {
            @Override
            public void run() {
                super.run();
                List<ListModel> list = getData();

                while (shouldContinue) {
                    try {
                        Thread.sleep(1000);
                        Logger.e(TAG, String.valueOf(filterSelected(list).size()));
                    } catch (InterruptedException e) {
                        Logger.e(TAG, "Interrupted from Sleep", e);
                    }
                }
            }
        };
    }

    private List<ListModel> filterSelected(List<ListModel> list) {
        List<ListModel> newList = new ArrayList<>();
        for(ListModel listModel: list) {
            if(listModel.isSelected())
                newList.add(listModel);
        }
        return newList;
    }

    public static void setData(List<ListModel> data) {
        list = data;
    }

    public static List<ListModel> getData() {
        return list;
    }

}
