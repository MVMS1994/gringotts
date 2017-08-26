package subbiah.veera.gringotts;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import subbiah.veera.gringotts.core.AppLocker;
import subbiah.veera.gringotts.data.ListModel;
import subbiah.veera.gringotts.ui.ListViewAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, AppLocker.class);
        stopService(i);
        startService(i);
    }

    @Override
    protected void onPause() {
        AppLocker.saveList(this, AppLocker.getData(this));
        super.onPause();
    }

    @Override
    protected void onResume() {
        AppLocker.setData(this, getPackages());
        renderListView();

        super.onResume();
    }

    private List<ListModel> getPackages() {
        List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

        List<ListModel> oldList = AppLocker.getData(this);
        List<ListModel> newList = new ArrayList<>();
        for(ApplicationInfo app: apps) {
            if(isSystemPackage(app) && !isWhiteListed(app.packageName)) continue;
            ListModel newInfo = new ListModel()
                    .setAppName(app.loadLabel(getPackageManager()).toString())
                    .setPackageName(app.packageName);

            newList.add(newInfo);
        }
        oldList.retainAll(newList);
        newList.removeAll(oldList);

        for(ListModel item: newList) {
            oldList.add(item);
        }

        Collections.sort(oldList, new Comparator<ListModel>() {
            @Override
            public int compare(ListModel o1, ListModel o2) {
                return o1.getAppName().toLowerCase().compareTo(o2.getAppName().toLowerCase());
            }
        });

        return oldList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListViewAdapter.ViewHolder holder = (ListViewAdapter.ViewHolder) view.getTag();

        ((ListModel) holder.checkBox.getTag()).setSelected(!holder.checkBox.isChecked());
        holder.checkBox.setChecked(!holder.checkBox.isChecked());

        AppLocker.saveList(this, AppLocker.getData(this));
    }

    private boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private boolean isWhiteListed(String appName) {
        String whiteListed[] = new String[]{
            "Drive", "Call", "Camera", "Chrome", "Clock", "Contact", "Download",
            "File", "mail", "play", "map", "photo", "gallery", "setting", "gallery"
        };

        for(String value: whiteListed) {
            if(appName.toLowerCase().contains(value.toLowerCase())) return true;
        }
        return false;
    }

    private void renderListView() {
        ListView listview = (ListView) findViewById(R.id.listview);

        final ListViewAdapter adapter = new ListViewAdapter(this, AppLocker.getData(this));
        listview.setOnItemClickListener(this);
        listview.setAdapter(adapter);
    }
}
