package subbiah.veera.gringotts;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Veera.Subbiah on 23/08/17.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
class ListModel {
    private static final String TAG = "ListModel";
    private String appName = "";
    private String packageName = "";
    private boolean selected = false;


    public String getPackageName() {
        return packageName;
    }

    public ListModel setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public ListModel setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public ListModel setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("packageName", getPackageName());
            jsonObject.put("appName", getAppName());
            jsonObject.put("selected", isSelected());
        } catch (JSONException e) {
            Logger.e(TAG, "This Happened: ", e);
        }

        return jsonObject.toString();
    }

    public static ListModel fromString(String payload) {
        try {
            JSONObject jsonObject = new JSONObject(payload);
            return new ListModel()
                    .setSelected(jsonObject.getBoolean("selected"))
                    .setAppName(jsonObject.getString("appName"))
                    .setPackageName(jsonObject.getString("packageName"));
        } catch (JSONException e) {
            Logger.e(TAG, "This Happened: ", e);
        }
        return new ListModel();
    }
}
