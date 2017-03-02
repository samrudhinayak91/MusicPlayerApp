package com.example.samrudhinayak.playerclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import CommonPackage.CommonInterface;


public class MainActivity extends Activity {
    protected static final String TAG = "ServiceUser";
    //object of the common interface
    private CommonInterface mGeneratorService;
    //variable used to check if the client was bound to the server
    private boolean mIsBound = false;
    //used to get the track which needs to be played
    int tracknum=0;
    Button pausebutton;
    Button playbutton;
    Button stopbutton;
    Button resumebutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set each of the buttons
        pausebutton=(Button) findViewById(R.id.pause_button);
        playbutton=(Button) findViewById(R.id.play_button);
        stopbutton=(Button) findViewById(R.id.stop_button);
        resumebutton=(Button) findViewById(R.id.button);
        //initially disable these buttons since they cannot be pressed unless the song is played
        pausebutton.setEnabled(false);
        stopbutton.setEnabled(false);
        resumebutton.setEnabled(false);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mIsBound) {

            boolean binder = false;
            Intent intent = new Intent("CommonPackage");
            // Must make intent explicit or lower target API level to 19.
            ResolveInfo info = getPackageManager().resolveService(intent, Context.BIND_AUTO_CREATE);
            //used to explicitly set the component to handle the intent
            intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            //used to bind the client to the server
            binder = bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);
            if (binder) {
                Log.i(TAG, "Samrudhi says bindService() succeeded!");
            } else {
                Log.i(TAG, "Samrudhi says bindService() failed!");
            }

        }
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {
            //used to access the stub in the server
            mGeneratorService = CommonInterface.Stub.asInterface(iservice);
            //flag used to indicate whether client is bound to the server or not
            mIsBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {
            //used to unbind from the server
            mGeneratorService = null;

            mIsBound = false;

        }
    };



    public void playmethod(View view) {
        if (mIsBound) {
            try {
                //used to get the value of the edittext
                EditText mEdit = (EditText) findViewById(R.id.editText);
                //used to check if there is no value input in the edittext
                if(mEdit.getText().length()==0)
                    tracknum=0;
                else
                //used to get the value of the edittext field
                tracknum = Integer.valueOf(mEdit.getText().toString());
                //check if the value is within range
                    if (tracknum > 0 && tracknum < 4) {
                        //disable other buttons that cannot be used
                        pausebutton.setEnabled(true);
                        stopbutton.setEnabled(true);
                        resumebutton.setEnabled(true);
                        //call the method on the server
                        mGeneratorService.playservice(tracknum);
                    }

                }catch(RemoteException e){
                    e.printStackTrace();
                }
            }
        }


    public void stopmethod(View view) {
        if (mIsBound) {
            try {
                //call method on server
                mGeneratorService.stopservice(tracknum);
                //disable buttons that are not used
                stopbutton.setEnabled(false);
                pausebutton.setEnabled(false);
                resumebutton.setEnabled(false);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    public void pausemethod(View view) {
        if (mIsBound) {
            try {
                //call method on server
                mGeneratorService.pauseservice(tracknum);
                //disable and enable buttons accordingly
                pausebutton.setEnabled(false);
                resumebutton.setEnabled(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void resumemethod(View view) {
        if (mIsBound) {
            try {
                //call methods on the server
                mGeneratorService.resumeservice(tracknum);
                //disable and enable buttons accordingly
                resumebutton.setEnabled(false);
                pausebutton.setEnabled(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void getlogs(View view) {
        if(mIsBound){
            try {
                //get list of string transaction from the server database
                List<String> logs=mGeneratorService.getlogs();
                Log.d("Samrudhi says",logs.toString());
                //use explicit intent to pass these values to the activity that displays the values
                Intent intent = new Intent(this,ListDisplayActivity.class);
                //add the string to the intent
                intent.putExtra("logs",logs.toArray(new String[logs.size()]));
                startActivity(intent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
}
