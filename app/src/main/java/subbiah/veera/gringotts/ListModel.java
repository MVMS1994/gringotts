package subbiah.veera.gringotts;

import android.graphics.drawable.Drawable;

/**
 * Created by Veera.Subbiah on 23/08/17.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
class ListModel {
    private String appName = "";
    private String packageName = "";
    private String versionName = "";
    private int versionCode = 0;
    private boolean selected = false;
    private Drawable icon;


    public Drawable getIcon() {
        return icon;
    }

    public ListModel setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public ListModel setVersionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public String getVersionName() {
        return versionName;
    }

    public ListModel setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
