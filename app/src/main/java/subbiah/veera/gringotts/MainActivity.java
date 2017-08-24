package subbiah.veera.gringotts;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listview);

        AppLocker.setData(getPackages());
        final ListViewAdapter adapter = new ListViewAdapter(this, AppLocker.getData());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

        Intent i = new Intent(this, AppLocker.class);
        stopService(i);
        startService(i);
    }

    private List<ListModel> getPackages() {
        List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

        ArrayList<ListModel> res = new ArrayList<>();
        for(int i=0; i< apps.size(); i++) {
            ApplicationInfo p = apps.get(i);
            if(isSystemPackage(p) && !isWhiteListed(p.packageName)) continue;
            ListModel newInfo = new ListModel()
                    .setAppName(p.loadLabel(getPackageManager()).toString())
                    .setPackageName(p.packageName)
                    .setIcon(p.loadIcon(getPackageManager()));

            res.add(newInfo);
        }

        Collections.sort(res, new Comparator<ListModel>() {
            @Override
            public int compare(ListModel o1, ListModel o2) {
                return o1.getAppName().toLowerCase().compareTo(o2.getAppName().toLowerCase());
            }
        });

        return res;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListViewAdapter.ViewHolder holder = (ListViewAdapter.ViewHolder) view.getTag();
        ((ListModel) holder.checkBox.getTag()).setSelected(!holder.checkBox.isChecked());
        holder.checkBox.setChecked(!holder.checkBox.isChecked());
    }

    private boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private boolean isWhiteListed(String appName) {
        String whiteListed[] = new String[]{
                "Drive", "Call", "Camera", "Chrome", "Clock", "Contact", "Download",
                "File", "mail", "play", "map", "photo", "gallery", "setting"
        };

        for(String value: whiteListed) {
            if(appName.toLowerCase().contains(value.toLowerCase())) return true;
        }
        return false;
    }
}
