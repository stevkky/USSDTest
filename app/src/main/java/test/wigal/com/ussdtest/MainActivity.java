package test.wigal.com.ussdtest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final int RC_CALL_APP_PERM = 4;

    private EditText code;
    private TextView response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        code = (EditText) findViewById(R.id.code);
        response = (TextView) findViewById(R.id.response);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                response.setText("");
             if(!code.getText().toString().isEmpty())
             {
                 dailNumber(code.getText().toString().substring(0,code.getText().toString().length() -1));
             }
             else
             {
                 code.setError("USSD Code required");
             }


            }
        });


        startService(new Intent(MainActivity.this, USSDManager.class));
        requestPermissions();


        USSDBroadcastReceiver.bindListener(new USSDBroadcastReceiver.USSDResponse() {
            @Override
            public void OnUSSDResponseReceived(String responsedata, String time) {

                response.setText(responsedata);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dailNumber(String code) {
        String ussdCode = code + Uri.encode("#");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }


    @AfterPermissionGranted(RC_CALL_APP_PERM)
    private void requestPermissions() {
        String[] perms = {  Manifest.permission.CALL_PHONE, Manifest.permission.BIND_ACCESSIBILITY_SERVICE };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // do something after permission granted


        } else {
            //if permission is denied
            EasyPermissions.requestPermissions(this, "This app needs access to your Call Services", RC_CALL_APP_PERM, perms);
        }
    }

}
