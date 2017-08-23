package subbiah.veera.gringotts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.listview);
        final String[] values = new String[] {
                "Android", "iPhone", "WindowsMobile", "Blackberry",
                "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Android", "iPhone", "WindowsMobile"
        };

        List<ListModel> list = new ArrayList<>(values.length);
        for (String value : values) {
            ListModel listModel = new ListModel()
                    .setName(value)
                    .setSelected(false);
            list.add(listModel);
        }

        final ListViewAdapter adapter = new ListViewAdapter(this, list);
        listview.setAdapter(adapter);
    }
}
