package subbiah.veera.gringotts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

public class AppLocker extends Service {

    private static final String TAG = "AppLocker";
    private volatile boolean shouldContinue;
    private static List<ListModel> list;
    private Thread worker;

    @Override
    public void onCreate() {
        super.onCreate();

        shouldContinue = true;
        getData(this);

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

    @Override
    public void onDestroy() {
        shouldContinue = false;
        worker = null;
        super.onDestroy();
    }

    private Thread getNewThread() {
        return new Thread("AppLocker Worker") {
            @Override
            public void run() {
                super.run();
                List<ListModel> list = getData(AppLocker.this);

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

    public static void setData(Context context, List<ListModel> data) {
        list = data;
        saveList(context);
    }

    public static List<ListModel> getData(Context context) {
        if(list == null) {
            list = new ArrayList<>();
            String rawData[] =  KeyStore.read(context, "data", new JSONObject().toString()).split("_/\\\\_");
            for(String datum: rawData) {
                list.add(ListModel.fromString(datum));
            }
        }
        return list;
    }

    public static void saveList(Context context) {
        if(list.size() >= 1) {
            StringBuilder data = new StringBuilder();
            data.append(list.get(0).toString());
            for (int i = 1; i < list.size(); i++) {
                data.append("_/\\_");
                data.append(list.get(i).toString());
            }
            KeyStore.write(context, "data", data.toString());
        }
    }

}
