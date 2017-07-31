package com.reeching.tpms.act;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.reeching.tpms.R;
import com.reeching.tpms.service.BluetoothLeService;
import com.reeching.tpms.utils.Contants;
import com.reeching.tpms.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by 绍轩 on 2017/7/21.
 * 设备信息
 */

public class DeviceControlActivity extends Activity

{
    private final static String TAG = DeviceControlActivity.class
            .getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    /**
     * ServiceConnection代表与服务的连接，它只有两个方法，
     * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
     * 通过这个IBinder对象，实现宿主和Service的交互。
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        /**
         * 系统会调用该方法以传递服务的　onBind() 方法返回的 IBinder。
         * 其中service便是服务端返回的IBinder实现类对象，通过该对象我们便可以调用获取LocalService实例对象，进而调用服务端的公共方法。
         * 而ComponentName是一个封装了组件(Activity, Service, BroadcastReceiver, or ContentProvider)信息的类，如包名，组件描述等信息，较少使用该参数。
         * @param componentName
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                    .getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up
            // initialization.
            //在成功启动时自动连接到设备
            mBluetoothLeService.connect(mDeviceAddress);
        }
        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         * 注意:当客户端取消绑定时，系统“绝对不会”调用该方法
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // 通过服务控制不同的事件
    // ACTION_GATT_CONNECTED: 连接到GATT服务端
    // ACTION_GATT_DISCONNECTED: 未连接GATT服务端.
    // ACTION_GATT_SERVICES_DISCOVERED: 未发现GATT服务.
    // ACTION_DATA_AVAILABLE: 接受来自设备的数据，可以通过读或通知操作获得。
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            System.out.println("action = " + action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.dialog8);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
                    .equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
                    .equals(action)) {
                // 显示所有受支持的服务和特性
                // user interface.
                displayGattServices(mBluetoothLeService
                        .getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                displayData(intent
                        .getStringExtra(BluetoothLeService.EXTRA_DATA));
            }



        }
    };


    // If a given GATT characteristic is selected, check for supported features.
    // This sample
    // demonstrates 'Read' and 'Notify' features. See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for
    // the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            if (mGattCharacteristics != null) {
                final BluetoothGattCharacteristic characteristic = mGattCharacteristics
                        .get(groupPosition).get(childPosition);
                final int charaProp = characteristic.getProperties();
                System.out.println("charaProp = " + charaProp + ",UUID = "
                        + characteristic.getUuid().toString());
                Random r = new Random();

                if (characteristic.getUuid().toString()
                        .equals("0000fff2-0000-1000-8000-00805f9b34fb")) {
                    int time= 0;
                    while((time=r.nextInt(9))<=0){

                    }

                    String data = time+","+"1,,,,,";
                    characteristic.setValue(data.getBytes());
                    mBluetoothLeService.wirteCharacteristic(characteristic);
                }
                if (characteristic.getUuid().toString()
                        .equals("0000fff1-0000-1000-8000-00805f9b34fb")) {
                    int R = r.nextInt(255);
                    int G = r.nextInt(255);
                    int B = r.nextInt(255);
                    int BB = r.nextInt(100);
                    String data = R + "," + G + "," + B + "," + BB;
                    while (data.length() < 18) {
                        data += ",";
                    }
                    System.out.println(data);
                    characteristic.setValue(data.getBytes());
                    mBluetoothLeService.wirteCharacteristic(characteristic);
                }
                if (characteristic.getUuid().toString()
                        .equals("0000fff3-0000-1000-8000-00805f9b34fb")) {
                    int R = r.nextInt(255);
                    int G = r.nextInt(255);
                    int B = r.nextInt(255);
                    int BB = r.nextInt(100);
                    String data = R + "," + G + "," + B + "," + BB;
                    while (data.length() < 18) {
                        data += ",";
                    }
                    System.out.println("RT");
                    characteristic.setValue("RT".getBytes());
                    mBluetoothLeService.wirteCharacteristic(characteristic);
                }
                if (characteristic.getUuid().toString()
                        .equals("0000fff5-0000-1000-8000-00805f9b34fb")) {
                    characteristic.setValue("S".getBytes());
                    mBluetoothLeService.wirteCharacteristic(characteristic);
                    System.out.println("send S");
                } else {

                    if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a
                        // characteristic, clear
                        // it first so it doesn't update the data field on the
                        // user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        mBluetoothLeService.readCharacteristic(characteristic);

                    }
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {

                    if (characteristic.getUuid().toString().equals("0000fff6-0000-1000-8000-00805f9b34fb")||characteristic.getUuid().toString().equals("0000fff4-0000-1000-8000-00805f9b34fb")) {
                        System.out.println("enable notification");
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(
                                characteristic, true);

                    }
                }

                return true;
            }
            return false;
        }
    };

    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        // Sets up UI references.
        ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
        mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
        mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);

        getActionBar().setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        boolean bll = bindService(gattServiceIntent, mServiceConnection,
                BIND_AUTO_CREATE);
        if (bll) {
            System.out.println("---------------");
        } else {
            System.out.println("===============");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "连接结果=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    /**
     *
     * @param data utf-8
     */
    private void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    // Demonstrates how to iterate through the supported GATT
    // Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the
    // ExpandableListView
    // on the UI.
    //处理服务器数据
    // 获取到服务列表后自然就是要对服务进行解析。解析出有哪些服务，服务里有哪些Characteristic，哪些Characteristic可读可写可发通知等等
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null)
            return;
        String uuid = null;
        String unknownServiceString = getResources().getString(
                R.string.unknown_service);
        String unknownCharaString = getResources().getString(
                R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {//遍历出gattServices里面的所有服务
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            LogUtils.i(uuid);
            currentServiceData.put(LIST_NAME,
                    Contants.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService
                    .getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {// 遍历每条服务里的所有Characteristic
                // 有哪些UUID，每个UUID有什么属性及作用，一般硬件工程师都会给相应的文档。我们程序也可以读取其属性判断其属性。
                // 此处可以可根据UUID的类型对设备进行读操作，写操作，设置notification等操作
                // BluetoothGattCharacteristic gattNoticCharacteristic 假设是可设置通知的Characteristic
                // BluetoothGattCharacteristic gattWriteCharacteristic 假设是可读的Characteristic
                // BluetoothGattCharacteristic gattReadCharacteristic  假设是可写的Characteristic
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(LIST_NAME,
                        Contants.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this, gattServiceData,
                android.R.layout.simple_expandable_list_item_2, new String[] {
                LIST_NAME, LIST_UUID }, new int[] { android.R.id.text1,
                android.R.id.text2 }, gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2, new String[] {
                LIST_NAME, LIST_UUID }, new int[] { android.R.id.text1,
                android.R.id.text2 });
        mGattServicesList.setAdapter(gattServiceAdapter);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter
                .addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}