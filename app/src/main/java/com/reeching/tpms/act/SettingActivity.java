package com.reeching.tpms.act;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.reeching.tpms.R;
import com.reeching.tpms.base.OtherBaseActivity;

public class SettingActivity extends OtherBaseActivity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{

    private static final long DIALOG_DISPLAY_TIME = 500L;
    protected static final int OPENLISTACTIVITY = 103;
    private TextView bar;
    private ImageView bindDevice;
    BroadcastReceiver blueReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
            String str = paramAnonymousIntent.getAction();
            System.out.println(str);
            str.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        }
    };
    private TextView devicename;
    Handler handler = new Handler();
    private CheckBox ir2cheBox;
    private CheckBox ir3cheBox;
    private CheckBox irCheckBox;
    private TextView kg;
    private TextView kpa;
    private TextView lanAuto;
    private TextView lanCh;
    private TextView lanEn;
    private TextView lanZh;
    private Dialog mAutoCloseDialog;
    private View mView;
    private TextView pis;
    private SeekBar proBar1;
    private SeekBar proBar2;
    private SeekBar proBar3;
    private Dialog processDialog;
    private TextView temph;
    private TextView temps;
    private TextView value1;
    private TextView value2;
    private TextView value3;
    private BluetoothAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);
        initBluetooth();
        initView();
        setResult(0);

    }

    private void initBluetooth() {
        // 检查设备是否支持蓝牙
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null)
        {
            // 设备不支持蓝牙
        }
        // 打开蓝牙
        if (!adapter.isEnabled())
        {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // 设置蓝牙可见性，最多300秒
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(intent,1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 103){
            DelayDialog(false);
        }
        if (requestCode == 1) {
            if (resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(),

                        getResources().getText(R.string.dialog6),
                        Toast.LENGTH_SHORT).show();
            }else if (resultCode==RESULT_CANCELED){
                finish();
            }
        }


    }

    protected void DelayDialog(final boolean paramBoolean)
    {
        this.mView = LayoutInflater.from(this).inflate(R.layout.dialog_delay, null);
        this.processDialog = new Dialog(this, R.style.processDialog);
        this.processDialog.setCancelable(false);
        this.processDialog.setContentView(this.mView);
        if (paramBoolean)
        {
            Intent localIntent = new Intent(SettingActivity.this, DeviceListActivity.class);
            SettingActivity.this.startActivityForResult(localIntent, 103);
        }
        SettingActivity.this.sendBroadcast(new Intent("com.changhewulian.ble.taiya.actionstartsearch"));
    }
    private void initView() {
        this.lanCh = ((TextView)findViewById(R.id.lanuagesimple));
        this.lanZh = ((TextView)findViewById(R.id.lanuagemulti));
        this.lanEn = ((TextView)findViewById(R.id.lanuageenglish));
        this.lanAuto = ((TextView)findViewById(R.id.lanAuto));
        this.bar = ((TextView)findViewById(R.id.yalibar));
        this.pis = ((TextView)findViewById(R.id.yalipis));
        this.kpa = ((TextView)findViewById(R.id.yalikpa));
        this.kg = ((TextView)findViewById(R.id.yalikg));
        this.value1 = ((TextView)findViewById(R.id.yalitopvalue));
        this.value2 = ((TextView)findViewById(R.id.yalibottomvalue));
        this.value3 = ((TextView)findViewById(R.id.tempratopvalue));
        this.temps = ((TextView)findViewById(R.id.temrac));
        this.temph = ((TextView)findViewById(R.id.temraf));
        this.devicename = ((TextView)findViewById(R.id.devicename));
        this.bindDevice = ((ImageView)findViewById(R.id.imageView1));
        this.irCheckBox = ((CheckBox)findViewById(R.id.setpower1));
        this.ir2cheBox = ((CheckBox)findViewById(R.id.setpower2));
        this.ir3cheBox = ((CheckBox)findViewById(R.id.setpower3));
        this.proBar1 = ((SeekBar)findViewById(R.id.song_progress));
        this.proBar2 = ((SeekBar)findViewById(R.id.song_progress2));
        this.proBar3 = ((SeekBar)findViewById(R.id.song_progress3));
        this.lanCh.setOnClickListener(this);
        this.lanZh.setOnClickListener(this);
        this.lanEn.setOnClickListener(this);
        this.lanAuto.setOnClickListener(this);
        this.bar.setOnClickListener(this);
        this.pis.setOnClickListener(this);
        this.kpa.setOnClickListener(this);
        this.kg.setOnClickListener(this);
        this.temps.setOnClickListener(this);
        this.temph.setOnClickListener(this);
        this.bindDevice.setOnClickListener(this);
        this.irCheckBox.setOnClickListener(this);
        this.ir2cheBox.setOnClickListener(this);
        this.ir3cheBox.setOnClickListener(this);
        this.proBar1.setOnSeekBarChangeListener(this);
        this.proBar2.setOnSeekBarChangeListener(this);
        this.proBar3.setOnSeekBarChangeListener(this);


    }

    @Override
    protected void onSettingChange() {

    }

    public void viewexitClick(View view){
        finish();
    }
    public void btn(View view){
        DelayDialog(true);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView1:
                DelayDialog(true);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        registerReceiver(this.blueReceiver, localIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(this.blueReceiver);
    }
}
