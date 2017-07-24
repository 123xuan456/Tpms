package com.reeching.tpms.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reeching.tpms.R;
import com.reeching.tpms.utils.BleDevice;

import java.util.List;

/**
 * Created by 齐绍轩1 on 2017/7/19.
 */

public class DeviceAdapter extends BaseAdapter
{
    private Context context;
    private List<BleDevice> devices;
    private LayoutInflater inflater;

    public DeviceAdapter(Context paramContext, List<BleDevice> paramList)
    {
        this.devices = paramList;
        this.context = paramContext;
        this.inflater = (LayoutInflater)paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return this.devices.size();
    }

    public Object getItem(int paramInt)
    {
        return this.devices.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        ViewHolder localViewHolder;
        BleDevice localBleDevice;
        if (paramView == null)
        {
            paramView = this.inflater.inflate(R.layout.layout_item, null);
            localViewHolder = new ViewHolder();
            localViewHolder.title = ((TextView)paramView.findViewById(R.id.textView1));
            localViewHolder.content = ((TextView)paramView.findViewById(R.id.textView2));
            localViewHolder.image = ((ImageView)paramView.findViewById(R.id.imageView1));
            paramView.setTag(localViewHolder);
        }
        {
            localViewHolder = (ViewHolder)paramView.getTag();
            localBleDevice = (BleDevice)this.devices.get(paramInt);
            localViewHolder.title.setText(localBleDevice.getName());//蓝牙名称
            localViewHolder.content.setText(localBleDevice.getMac());//蓝牙地址
            Log.i("蓝牙","getName="+localBleDevice.getName());
            Log.i("蓝牙1","getUuid="+localBleDevice.getMac());
            localViewHolder.image.setVisibility(View.GONE);//未连接的设备
            return paramView;
        }
    }

    class ViewHolder
    {
        TextView content;
        ImageView image;
        TextView title;
    }
}