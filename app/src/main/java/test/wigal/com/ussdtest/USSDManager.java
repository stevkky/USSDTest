package test.wigal.com.ussdtest;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

/**
 * Created by stevkky on 01/31/2018.
 */

public class USSDManager  extends AccessibilityService {

    public static String TAG = "USSDManager";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.d(TAG, "onAccessibilityEvent");

      /*  Log.v(TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                getEventType(event), event.getClassName(), event.getPackageName(),
                event.getEventTime(), getEventText(event)));
                */

        String text = event.getText().toString();

        if (event.getClassName().equals("android.app.AlertDialog")) {
            performGlobalAction(GLOBAL_ACTION_BACK);
            Log.d(TAG, "My test here: "+text);

            Toast.makeText(this,text,Toast.LENGTH_LONG);

            Intent intent = new Intent("com.times.ussd.action.REFRESH");
            intent.putExtra("message", text);

            sendBroadcast(intent); //broadcast the message received so that any listeners will be notified

        }
    }


    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = new String[]{"com.android.phone"};
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}
