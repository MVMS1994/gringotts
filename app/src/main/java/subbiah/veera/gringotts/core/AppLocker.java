package subbiah.veera.gringotts.core;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import subbiah.veera.gringotts.data.KeyStore;
import subbiah.veera.gringotts.data.ListModel;
import subbiah.veera.gringotts.data.Logger;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

public class AppLocker extends AccessibilityService {

    private static final String TAG = "AppLocker";
    private String lastApp = "";
    private static List<ListModel> list;

    @Override
    public void onCreate() {
        super.onCreate();
        getData(this);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        decideAndOpenLockScreen(filterSelected(list), event.getPackageName().toString(), event.getClassName().toString());
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        Logger.d(TAG, "onServiceConnected");

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    private void decideAndOpenLockScreen(List<ListModel> list, String packageName, String className) {
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        if(!keyguardManager.isDeviceSecure()) return;

        for(ListModel item: list) {
            if(item.getPackageName().equalsIgnoreCase(packageName) && !packageName.equalsIgnoreCase(lastApp)) {
                Logger.d(TAG, "Open Lock Screen: " + packageName);

                openLockScreen();
                break;
            }
        }
        if(!isBlackListed(className))
            lastApp = packageName;
    }

    private void openLockScreen() {
//        Intent intent = new Intent(this, LockScreen.class);
//        startActivity(intent);
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
        saveList(context, list);
    }

    public static List<ListModel> getData(Context context) {
        if(list == null) {
            list = new ArrayList<>();
            String rawData =  KeyStore.read(context, "data", "");
            if(!rawData.equals("")) {
                String[] data = rawData.split("_/\\\\_");
                for(String datum : data) {
                    list.add(ListModel.fromString(datum));
                }
            }
        }
        return list;
    }

    public static void saveList(final Context context, final List<ListModel> list) {
        new Thread("List Saver") {
            @Override
            public void run() {
                if (list.size() >= 1) {
                    StringBuilder data = new StringBuilder();
                    data.append(list.get(0).toString());
                    for (int i = 1; i < list.size(); i++) {
                        data.append("_/\\_");
                        data.append(list.get(i).toString());
                    }
                    KeyStore.write(context, "data", data.toString());
                }
            }
        }.start();
    }

    private boolean isBlackListed(String packageName) {
        String[] blackListed = {
                "com.android.systemui.recents.RecentsActivity",
                "com.android.settings.ConfirmLockPattern"
        };

        List<String> apps = Arrays.asList(blackListed);
        return apps.contains(packageName);
    }
}
