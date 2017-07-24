package com.reeching.tpms.act;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.reeching.tpms.R;
import com.reeching.tpms.adapter.DeviceAdapter;
import com.reeching.tpms.base.OtherBaseActivity;
import com.reeching.tpms.utils.BleDevice;
import com.reeching.tpms.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 齐绍轩1 on 2017/7/11.
 * 搜索蓝牙设备
 *
 */

public class DeviceListActivity extends OtherBaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    private BluetoothAdapter adapter = null;
    private List<BleDevice> devices1;
    private List<BleDevice> devices2;
    private List<String> devices_addrs;
    private Handler handler = new Handler();
    private DeviceAdapter homeDevice1;
    private DeviceAdapter homeDevice2adapter;
    private ListView homelist;
    private ListView listView1;
    int theSameCount=0;
    private Runnable stopSPPScanRunnable;

    @Override
    protected void onSettingChange() {
    }
    //添加蓝牙设备到集合
    private void addDevice(final BluetoothDevice device) {
        if (devices2.size() > 0) {
            if (devices_addrs.contains(device.getAddress())) {
                theSameCount++;
                if (theSameCount > 30) {
                    theSameCount = 0;
                    final int index = devices_addrs.indexOf(device
                            .getAddress());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            devices2.get(index).setDetail(device.getName(),device.getAddress());
                            homeDevice2adapter.notifyDataSetChanged();
                        }
                    });
                }
                return;
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BleDevice deviceDetail = new BleDevice(device.getName(),device.getAddress());
                devices2.add(deviceDetail);
                devices_addrs.add(deviceDetail.getMac());
                homeDevice2adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_device_list);
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.listView1 = ((ListView)findViewById(R.id.listView1));
        this.homelist = ((ListView)findViewById(R.id.listView2));
        this.listView1.setOnItemClickListener(this);
        this.homelist.setOnItemClickListener(this);

        this.devices2 = new ArrayList();
        this.devices_addrs = new ArrayList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(receiver, intentFilter);
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去

        setupRunnable();
        handler.postDelayed(stopSPPScanRunnable, 30000);// 搜索30s
        adapter.startDiscovery();
        this.homeDevice2adapter = new DeviceAdapter(this, this.devices2);
        this.homelist.setAdapter(this.homeDevice2adapter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE,10);
                System.out.println("蓝牙"+device.getName());
                addDevice(device);
            }
        }
    };
    //设置Runnable
    private void setupRunnable() {
        if (stopSPPScanRunnable == null) {
            stopSPPScanRunnable = new Runnable() {
                @Override
                public void run() {
                    //关闭搜索
                    adapter.cancelDiscovery();
                }
            };
        }
    }
    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        //停止搜索蓝牙
        adapter.cancelDiscovery();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String mac = devices2.get(position).getMac();
        LogUtils.i(mac);
        BleDevice device = devices2.get(position);
        Intent intent=new Intent(this,DeviceControlActivity.class);
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getMac());
        startActivity(intent);
        adapter.cancelDiscovery();

        if (view.getId()==R.id.listView1){
           // MessageManager.getIntance(this).connectDevice(localBleDevice);
            return;
        }
//        MessageManager.getIntance(this).connectDevice(localBleDevice);

    }
}
