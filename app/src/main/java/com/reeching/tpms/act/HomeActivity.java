package com.reeching.tpms.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.reeching.tpms.R;
import com.reeching.tpms.base.OtherBaseActivity;
import com.reeching.tpms.view.BatteryView;

import java.util.HashMap;

public class HomeActivity extends OtherBaseActivity implements View.OnClickListener{
    private TextView barvalue1;
    private TextView barvalue2;
    private TextView barvalue3;
    private TextView barvalue4;
    private BatteryView battery_topmenu;
    private ImageView carico;
    private AlertDialog dialog;
    private ImageView exitbtn;
    private RelativeLayout home_leftfont_rl;
    private RelativeLayout home_leftrear_rl;
    private RelativeLayout home_rightfont_rl;
    private RelativeLayout home_rightrear_rl;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private RelativeLayout rel1;
    private RelativeLayout rel2;
    private RelativeLayout rel3;
    private RelativeLayout rel4;
    private ImageView settingbtn;
    private CheckBox settingvoicebtn;
    private TextView tempra1;
    private TextView tempra2;
    private TextView tempra3;
    private TextView tempra4;
    private EditText title;
    HashMap<String, String> titlecon = new HashMap();
    private TextView unittaiya1;
    private TextView unittaiya2;
    private TextView unittaiya3;
    private TextView unittaiya4;
    private TextView unittemp1;
    private TextView unittemp2;
    private TextView unittemp3;
    private TextView unittemp4;
    private boolean init;

    //    private ServiceConnection HomeConnection = new ServiceConnection()
//    {
//        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
//        {
//            HomeActivity.this.homeBinder = ((BluetoothMultiService.HomeBinder)paramAnonymousIBinder).getService();
//            HomeActivity.this.homeBinder.connectDevices(false);
//        }
//
//        public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
//        {
//            HomeActivity.this.homeBinder = null;
//        }
//    };
//    private BluetoothMultiService homeBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //判断是否首次打开
        this.init = getSharedPreferences("config", 0).getBoolean("value", false);
        if (!init){
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);
//        bindService(new Intent(this, BluetoothMultiService.class), this.HomeConnection, Context.BIND_AUTO_CREATE);
        initView();
    }

    private void initView() {
        this.rel1 = ((RelativeLayout)findViewById(R.id.rel1));
        this.rel2 = ((RelativeLayout)findViewById(R.id.rel2));
        this.rel3 = ((RelativeLayout)findViewById(R.id.rel3));
        this.rel4 = ((RelativeLayout)findViewById(R.id.rel4));
        this.battery_topmenu = ((BatteryView)findViewById(R.id.battery_topmenu));
        this.barvalue1 = ((TextView)findViewById(R.id.barvalue1));
        this.tempra1 = ((TextView)findViewById(R.id.tempra1));
        this.barvalue2 = ((TextView)findViewById(R.id.barvalue2));
        this.tempra2 = ((TextView)findViewById(R.id.tempra2));
        this.barvalue3 = ((TextView)findViewById(R.id.barvalue3));
        this.tempra3 = ((TextView)findViewById(R.id.tempra3));
        this.barvalue4 = ((TextView)findViewById(R.id.barvalue4));
        this.tempra4 = ((TextView)findViewById(R.id.tempra4));
        this.unittaiya1 = ((TextView)findViewById(R.id.unittaiya1));
        this.unittemp1 = ((TextView)findViewById(R.id.unittemp1));
        this.unittaiya2 = ((TextView)findViewById(R.id.unittaiya2));
        this.unittemp2 = ((TextView)findViewById(R.id.unittemp2));
        this.unittaiya3 = ((TextView)findViewById(R.id.unittaiya3));
        this.unittemp3 = ((TextView)findViewById(R.id.unittemp3));
        this.unittaiya4 = ((TextView)findViewById(R.id.unittaiya4));
        this.unittemp4 = ((TextView)findViewById(R.id.unittemp4));
        this.title = ((EditText)findViewById(R.id.title));
        this.title.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.title.setSelection(this.title.getText().length(), this.title.getText().length());
        this.carico = ((ImageView)findViewById(R.id.carico));
        this.imageView1 = ((ImageView)findViewById(R.id.imageView1));
        this.imageView2 = ((ImageView)findViewById(R.id.imageView2));
        this.imageView3 = ((ImageView)findViewById(R.id.imageView3));
        this.imageView4 = ((ImageView)findViewById(R.id.imageView4));
        this.exitbtn = ((ImageView)findViewById(R.id.exitbtn));
        this.settingvoicebtn = ((CheckBox)findViewById(R.id.settingvoicebtn));
        this.settingbtn = ((ImageView)findViewById(R.id.settingbtn));
        this.home_leftfont_rl = ((RelativeLayout)findViewById(R.id.home_leftfont_rl));
        this.home_leftrear_rl = ((RelativeLayout)findViewById(R.id.home_leftrear_rl));
        this.home_rightfont_rl = ((RelativeLayout)findViewById(R.id.home_rightfont_rl));
        this.home_rightrear_rl = ((RelativeLayout)findViewById(R.id.home_rightrear_rl));
        this.rel1.setOnClickListener(this);
        this.rel2.setOnClickListener(this);
        this.rel3.setOnClickListener(this);
        this.rel4.setOnClickListener(this);
        this.settingbtn.setOnClickListener(this);
        this.exitbtn.setOnClickListener(this);
        this.settingvoicebtn.setOnClickListener(this);


    }

    @Override
    protected void onSettingChange() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (this.homeBinder != null)
//            this.homeBinder.connectDevices(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (this.homeBinder != null)
//        {
//            this.homeBinder.stopFindDevices();
//            unbindService(this.HomeConnection);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exitbtn:
                AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
                int i;
                int j;
                int k;
                AlertDialog.Builder localBuilder3;
                int m;
                i = R.string.singlechoice1;
                AlertDialog.Builder localBuilder2 = localBuilder1.setTitle(i);
                String[] arrayOfString = new String[2];
                Resources localResources1 = getResources();
                j = R.string.singlechoice2;
                arrayOfString[0] = localResources1.getString(j);
                Resources localResources2 = getResources();
                k = R.string.singlechoice3;
                arrayOfString[1] = localResources2.getString(k);
                localBuilder3 = localBuilder2.setItems(arrayOfString, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        paramAnonymousDialogInterface.dismiss();
                        if (paramAnonymousInt == 0)
                        {
                            Intent localIntent1 = new Intent(HomeActivity.this, IdSettingActivity.class);
                            HomeActivity.this.startActivity(localIntent1);
                            return;
                        }
                        Intent localIntent2 = new Intent(HomeActivity.this, ChangeActivity.class);
                        HomeActivity.this.startActivity(localIntent2);
                    }
                });
                m = R.string.singlechoice4;
                localBuilder3.setNegativeButton(m, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        paramAnonymousDialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.settingbtn:
                Intent i1=new Intent(this,SettingActivity.class);
                startActivity(i1);
                break;
            case R.id.rel1:
                Intent localIntent4 = new Intent(this, LuntaiActivity.class);
                localIntent4.putExtra("id", 1);
                startActivity(localIntent4);
                break;
            case R.id.rel2:
                Intent localIntent3 = new Intent(this, LuntaiActivity.class);
                localIntent3.putExtra("id", 2);
                startActivity(localIntent3);
                break;
            case R.id.rel3:
                Intent localIntent2 = new Intent(this, LuntaiActivity.class);
                 localIntent2.putExtra("id", 3);
                startActivity(localIntent2);
                break;
            case R.id.rel4:
                Intent localIntent1 = new Intent(this, LuntaiActivity.class);
                localIntent1.putExtra("id", 4);
                startActivity(localIntent1);
                break;


        }
    }
}
