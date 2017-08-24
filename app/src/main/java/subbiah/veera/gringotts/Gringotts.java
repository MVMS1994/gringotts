package subbiah.veera.gringotts;

import android.app.Application;

import java.util.List;

/**
 * Created by Veera.Subbiah on 24/08/17.
 */

public class Gringotts extends Application {
    private List<ListModel> list;

    public void setData(List<ListModel> data) {
        list = data;
    }

    public List<ListModel> getData() {
        return list;
    }
}
