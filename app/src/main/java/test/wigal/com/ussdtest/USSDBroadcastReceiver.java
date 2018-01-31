package test.wigal.com.ussdtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by stevkky on 01/31/2018.
 */

public class USSDBroadcastReceiver extends BroadcastReceiver {


    public interface USSDResponse
    {
        void OnUSSDResponseReceived(String response, String time);
    }

    private static USSDResponse mListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("com.times.ussd.action.REFRESH")){

            Bundle bundle = intent.getExtras();
            String data = bundle.getString("message");

            if(!data.isEmpty())
            {
                if( mListener != null)
                {
                    mListener.OnUSSDResponseReceived(data, Calendar.getInstance().getTime().toString());
                }

            }

        }

    }

    public static void bindListener(USSDResponse listener) {
        mListener = listener;
    }
}
