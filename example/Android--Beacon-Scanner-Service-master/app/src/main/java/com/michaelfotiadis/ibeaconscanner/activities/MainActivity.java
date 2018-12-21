package com.michaelfotiadis.ibeaconscanner.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.michaelfotiadis.ibeaconscanner.R;
import com.michaelfotiadis.ibeaconscanner.adapter.ExpandableListAdapter;
import com.michaelfotiadis.ibeaconscanner.beetrootActivity;
import com.michaelfotiadis.ibeaconscanner.containers.CustomConstants;
import com.michaelfotiadis.ibeaconscanner.datastore.Singleton;
import com.michaelfotiadis.ibeaconscanner.processes.ScanHelper;
import com.michaelfotiadis.ibeaconscanner.tasks.MonitorTask;
import com.michaelfotiadis.ibeaconscanner.tasks.MonitorTask.OnBeaconDataChangedListener;
import com.michaelfotiadis.ibeaconscanner.utils.BluetoothHelper;
import com.michaelfotiadis.ibeaconscanner.utils.Logger;
import com.michaelfotiadis.ibeaconscanner.utils.ToastUtils;
import com.michaelfotiadis.ibeaconscanner.utils.pinkActivity;
import com.michaelfotiadis.ibeaconscanner.yellowActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;

public class MainActivity extends BaseActivity implements OnChildClickListener {

    private static final int RESULT_SETTINGS = 1;
    private final String TAG = MainActivity.class.getSimpleName();
    ExpandableListAdapter mListAdapter;
    List<String> mListDataHeader;
    HashMap<String, List<String>> mListDataChild;
    private BluetoothHelper mBluetoothHelper;
    private ExpandableListView mExpandableListView;
    private CharSequence mTextViewContents;
    private MonitorTask mMonitorTask;
    // Receivers
    private ResponseReceiver mScanReceiver;
    private SharedPreferences mSharedPrefs;
    private boolean mIsScanRunning = true;
    private boolean mIsToastScanningNowShown;
    private boolean mIsToastStoppingScanShown;

    public void onCheckedChanged() {



            if (mIsScanRunning) {
                serviceToggle();

        } else {

            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionsResultAction() {

                        @Override
                        public void onGranted() {

                            if (!mBluetoothHelper.isBluetoothLeSupported()) {
                                ToastUtils.makeWarningToast(MainActivity.this, getString(R.string.toast_no_le));
                             //   buttonView.setChecked(false);
                                return;
                            } else {
                                if (!mBluetoothHelper.isBluetoothOn()) {
                                    ScanHelper.cancelService(MainActivity.this);
                                    mBluetoothHelper.askUserToEnableBluetoothIfNeeded();
                                  //  buttonView.setChecked(false);
                                    return;
                                }
                            }

                            serviceToggle();
                        }

                        @Override
                        public void onDenied(final String permission) {
                            ToastUtils.makeWarningToast(MainActivity.this, getString(R.string.toast_warning_permission_not_granted));
                            //buttonView.setChecked(false);
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public boolean onChildClick(final ExpandableListView parent, final View v,
                                final int groupPosition, final int childPosition, final long id) {

        if (mListDataChild != null) {


            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();


                final String address = mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();

                if(address.equals("F6:DD:97:92:D0:31")){
                    AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater layoutInflater
                            = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    alertDialog1.setTitle("Estimote Beacon");

                    alertDialog1.setMessage("You are now in Sports Isle");
                    alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                    Log.v("msg",mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition));
//                    final String address = mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
                            final Intent intent = new Intent(getApplicationContext(), beetrootActivity.class);

//                    intent.putExtra(
//                            CustomConstants.Payloads.PAYLOAD_1.toString(),
//                            Singleton.getInstance().getBluetoothLeDeviceForAddress(address));
                            startActivity(intent);
                          //  Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();

                        }

                    });
                    alertDialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
// Write your code here to invoke NO event
//Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    alertDialog1.show(); }
   else if(address.equals("E3:91:4E:EE:06:BC")){
                AlertDialog.Builder alertDialog4 = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                alertDialog4.setTitle("Estimote Beacon");

                alertDialog4.setMessage("You are now in Mens Clothing Isle");
                alertDialog4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                    Log.v("msg",mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition));
//                    final String address = mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
                        final Intent intent = new Intent(getApplicationContext(), pinkActivity.class);

//                    intent.putExtra(
//                            CustomConstants.Payloads.PAYLOAD_1.toString(),
//                            Singleton.getInstance().getBluetoothLeDeviceForAddress(address));
                        startActivity(intent);
                      //  Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();

                    }

                });
                    alertDialog4.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
// Write your code here to invoke NO event
//Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                alertDialog4.show();
            }
      else if(address.equals("D9:61:DB:7B:3B:72")){
                AlertDialog.Builder alertDialog5 = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                alertDialog5.setTitle("Estimote Beacon");

                alertDialog5.setMessage("You are now in Electronics Isle");
                alertDialog5 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                    Log.v("msg",mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition));
//                    final String address = mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
                        final Intent intent = new Intent(getApplicationContext(), yellowActivity.class);

//                    intent.putExtra(
//                            CustomConstants.Payloads.PAYLOAD_1.toString(),
//                            Singleton.getInstance().getBluetoothLeDeviceForAddress(address));
                        startActivity(intent);
                     //   Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();

                    }

                });
                    alertDialog5.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
// Write your code here to invoke NO event
//Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                alertDialog5.show();
            }
             else{   //   ToastUtils.makeProgressToast(this, device.getAddress());
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater layoutInflater
                    = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            alertDialog.setTitle("Estimote Beacon");

            alertDialog.setMessage("UnAuthorised");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    Log.v("msg",mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition));
//                    final String address = mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
                    dialog.cancel();

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// Write your code here to invoke NO event
//Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            alertDialog.show();
                }
            if(childPosition ==10||childPosition==13){
            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater layoutInflater1
                    = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            alertDialog1.setTitle("Estimote Beacon");

            alertDialog1.setMessage("Unauthorized ");
            alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();


                }
            });
            alertDialog1.show();}
            return true;

        } else {

            return false;
        }

    }

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
       // In();
    // ScanHelper.scanForIBeacons(MainActivity.this, getScanTime(), getPauseTime());;
        mExpandableListView = (ExpandableListView) findViewById(R.id.listViewResults);
        mExpandableListView.setOnChildClickListener(this);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (savedInstanceState != null) {
            mTextViewContents = savedInstanceState.getCharSequence(CustomConstants.Payloads.PAYLOAD_1.toString());
            mIsScanRunning = savedInstanceState.getBoolean(CustomConstants.Payloads.PAYLOAD_2.toString(), true);
            mIsToastScanningNowShown = savedInstanceState.getBoolean(CustomConstants.Payloads.PAYLOAD_4.toString(), false);
            mIsToastStoppingScanShown = savedInstanceState.getBoolean(CustomConstants.Payloads.PAYLOAD_5.toString(), false);
        }

        // initialise Bluetooth utilities
        mBluetoothHelper = new BluetoothHelper(this);

        // monitor the singleton
        registerMonitorTask();

        // Wait for broadcasts from the scanning process
        registerResponseReceiver();
        SuperActivityToast.cancelAllSuperToasts();
      //  handleResume();
//In();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final View layout = menu.findItem(R.id.action_toggle).getActionView();
        final SwitchCompat toggle = (SwitchCompat) layout.findViewById(R.id.switchForActionBar);
       // toggle.setChecked(true);

       toggle.setChecked(mIsScanRunning);

     //   toggle.setOnCheckedChangeListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // same as using a normal menu
        switch (item.getItemId()) {
            case R.id.action_settings:
                startPreferencesActivity();
                break;
            default:
                break;
        }
        return true;
    }
    public void serviceToggle() {
        SuperActivityToast.cancelAllSuperToasts();

        if (mIsScanRunning) {
            // Cancels the alarms if the scan is already running
            ScanHelper.scanForIBeacons(MainActivity.this, getScanTime(), getPauseTime());

           // ScanHelper.cancelService(this);
            mIsToastScanningNowShown = false;
            ToastUtils.makeInfoToast(this, getString(R.string.toast_interrupted));
            mIsScanRunning = false;
        } else {
            // This ScanHelper will also cancel all alarms on continuation
            ScanHelper.scanForIBeacons(MainActivity.this, getScanTime(), getPauseTime());
        }
    }
    @Override
    protected void onDestroy() {
        removeReceivers();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putCharSequence(CustomConstants.Payloads.PAYLOAD_1.toString(), mTextViewContents);
        outState.putBoolean(CustomConstants.Payloads.PAYLOAD_2.toString(), mIsScanRunning);
        outState.putBoolean(CustomConstants.Payloads.PAYLOAD_4.toString(), mIsToastScanningNowShown);
        outState.putBoolean(CustomConstants.Payloads.PAYLOAD_5.toString(), mIsToastStoppingScanShown);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        // Cancel the alarm
        SuperActivityToast.cancelAllSuperToasts();
        ScanHelper.cancelService(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SuperActivityToast.cancelAllSuperToasts();
      //  In();
        onCheckedChanged();
        handleResume();

    }

    private void handleResume() {
        onCheckedChanged();
            if (mBluetoothHelper.isBluetoothOn()
                    && mBluetoothHelper.isBluetoothLeSupported()) {
                Logger.i(TAG, "Bluetooth has been activated");
                if (mIsScanRunning) {
                    Logger.d(TAG, "Restarting Scan Service");
                    ScanHelper.scanForIBeacons(MainActivity.this, getScanTime(), getPauseTime());
                }

                if (mIsToastScanningNowShown) {
                    ToastUtils.makeProgressToast(this, getString(R.string.toast_scanning));
                }
            } else {
                SuperActivityToast.cancelAllSuperToasts();
                ToastUtils.makeProgressToast(this, getString(R.string.toast_waiting));
            }
        updateListData();

    }

    protected void removeReceivers() {
        try {
            this.unregisterReceiver(mScanReceiver);
            Logger.d(TAG, "Scan Receiver Unregistered Successfully");
        } catch (final Exception e) {
            Logger.d(
                    TAG,
                    "Scan Receiver Already Unregistered. Exception : "
                            + e.getLocalizedMessage());
        }
    }

    protected void stopMonitorTask() {
        if (mMonitorTask != null) {
            Logger.d(TAG, "Monitor Task paused");
            mMonitorTask.stop();
        }
    }

    private int getPauseTime() {
        final String result = mSharedPrefs.getString(
                getString(R.string.pref_pausetime),
                String.valueOf(getResources().getInteger(R.integer.default_pausetime)));
        return Integer.parseInt(result);
    }

    private int getScanTime() {
        final String result = mSharedPrefs.getString(
                getString(R.string.pref_scantime),
                String.valueOf(getResources().getInteger(R.integer.default_scantime)));
        return Integer.parseInt(result);

    }

    private void notifyDataChanged() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Singleton.getInstance().getAvailableDevicesList() != null) {
                    updateListData();
                }
            }
        });
    }

    private void registerMonitorTask() {
        Logger.d(TAG, "Starting Monitor Task");
        mMonitorTask = new MonitorTask(new OnBeaconDataChangedListener() {
            @Override
            public void onDataChanged() {
                Logger.d(TAG, "Singleton Data Changed");
                notifyDataChanged();
            }
        });
        mMonitorTask.start();
    }

    private void registerResponseReceiver() {
        Logger.d(TAG, "Registering Response Receiver");
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CustomConstants.Broadcasts.BROADCAST_1.getString());
        intentFilter.addAction(CustomConstants.Broadcasts.BROADCAST_2.getString());

        mScanReceiver = new ResponseReceiver();
        this.registerReceiver(mScanReceiver, intentFilter);
    }

    private void startPreferencesActivity() {
        Logger.d(TAG, "Starting Settings Activity");
      //  mIsScanRunning = true;
        final Intent intent = new Intent(this, ScanPreferencesActivity.class);
        startActivityForResult(intent, RESULT_SETTINGS);
    }

    private void updateListData() {
      //  mIsScanRunning = true;

        Logger.d(TAG, "Updating List Data");
        mListDataHeader = new ArrayList<>();
       mListDataHeader.add("Available Devices (" + Singleton.getInstance().getAvailableDeviceListSize() + ")");
//        mListDataHeader.add("New old (" + Singleton.getInstance().getNewDeviceListSize() + ")");
//        mListDataHeader.add("Updated Devices (" + Singleton.getInstance().getUpdatedDeviceListSize() + ")");
      //  mListDataHeader.add("Moving Closer Devices (" + Singleton.getInstance().getMovingCloserDeviceListSize() + ")");
       // mListDataHeader.add("Moving Farther Device (" + Singleton.getInstance().getMovingFartherDeviceListSize() + ")");
//        mListDataHeader.add("Disappearing Devices (" + Singleton.getInstance().getDisappearingDeviceListSize() + ")");

        mListDataChild = new HashMap<>();
        mListDataChild.put(mListDataHeader.get(0), Singleton.getInstance().getDevicesAvailableAsStringList());
       //mListDataChild.put(mListDataHeader.get(0), Singleton.getInstance().getDevicesMovingCloserAsStringList());
//        mListDataChild.put(mListDataHeader.get(2), Singleton.getInstance().getDevicesUpdatedAsStringList());
//        mListDataChild.put(mListDataHeader.get(3), Singleton.getInstance().getDevicesMovingCloserAsStringList());
      //mListDataChild.put(mListDataHeader.get(1), Singleton.getInstance().getDevicesMovingFartherAsStringList());
//        mListDataChild.put(mListDataHeader.get(5), Singleton.getInstance().getDevicesDisappearingAsStringList());

        mListAdapter = new ExpandableListAdapter(this, mListDataHeader, mListDataChild);
        Logger.d(TAG, "Setting Adapter");
        mExpandableListView.setAdapter(mListAdapter);
    }

    public class ResponseReceiver extends BroadcastReceiver {
        private final String TAG = ResponseReceiver.class.getSimpleName();

        @Override
        public void onReceive(final Context context, final Intent intent) {
            Logger.d(TAG, "On Receiver Result");
            if (intent.getAction().equalsIgnoreCase(
                    CustomConstants.Broadcasts.BROADCAST_1.getString())) {
                Logger.i(TAG, "Scan Running");
                SuperActivityToast.cancelAllSuperToasts();
               mIsScanRunning = true;
                mIsToastScanningNowShown = true;
                ToastUtils.makeProgressToast(MainActivity.this, getString(R.string.toast_scanning));

            } else if (intent.getAction().equalsIgnoreCase(
                    CustomConstants.Broadcasts.BROADCAST_2.getString())) {
                Logger.i(TAG, "Service Finished");
                SuperActivityToast.cancelAllSuperToasts();
                mIsToastScanningNowShown = false;
                //				isToastStoppingScanShown = false;
                ToastUtils.makeInfoToast(MainActivity.this, getString(R.string.toast_completed));
            }
        }
    }

}
