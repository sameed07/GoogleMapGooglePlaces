package com.example.sameedshah.googlemapgoogleplaces;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 999;

    Button btnMap;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap = findViewById(R.id.btnMap);

        if(isServiceOK()){
            init();
        }

    }

    private void init(){

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,MapActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean isServiceOK(){
        Log.d(TAG,"isServiceOK : Checking google version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and user can make map request

            Log.d(TAG,"Google play service is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //if the error occur but its resolvable
            Log.d(TAG,"An error occur but we can fix it!");

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else{
            Toast.makeText(this, "you cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
