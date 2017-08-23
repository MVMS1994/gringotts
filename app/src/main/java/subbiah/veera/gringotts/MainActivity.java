package subbiah.veera.gringotts;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

        List<ListModel> list = getPackages();
        final ListViewAdapter adapter = new ListViewAdapter(this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    private List<ListModel> getPackages() {
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);

        ArrayList<ListModel> res = new ArrayList<>();
        for(int i=0; i< apps.size(); i++) {
            PackageInfo p = apps.get(i);
            ListModel newInfo = new ListModel()
                    .setAppName(p.applicationInfo.loadLabel(getPackageManager()).toString())
                    .setPackageName(p.packageName)
                    .setVersionName(p.versionName)
                    .setVersionCode(p.versionCode)
                    .setIcon(p.applicationInfo.loadIcon(getPackageManager()));

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
}
