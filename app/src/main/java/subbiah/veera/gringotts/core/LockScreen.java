package subbiah.veera.gringotts.core;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Veera.Subbiah on 27/08/17.
 */

public class LockScreen extends Activity {
    @SuppressWarnings("unused")
    private static final String TAG = "LockScreen";
    private final int LOCK = 8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openLockScreen();
    }

    private void openLockScreen() {
        KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        Intent i = km.createConfirmDeviceCredentialIntent("Is this Veera?", "Please confirm yourself, Veera");
        startActivityForResult(i, LOCK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        AppLocker.setLocked(false);
        if(requestCode == LOCK && resultCode != RESULT_OK)
            closeApp();
    }

    private void closeApp() {
        Intent homeScreen = new Intent(Intent.ACTION_MAIN);
        homeScreen.addCategory(Intent.CATEGORY_HOME);
        homeScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeScreen);
    }
}
