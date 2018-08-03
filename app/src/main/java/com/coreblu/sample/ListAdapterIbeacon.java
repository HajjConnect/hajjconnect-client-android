package com.coreblu.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.coreblu.sample.R;
import coreblu.SDK.Beacons.Beacon;
import coreblu.SDK.Beacons.iBeacon;




public class ListAdapterIbeacon extends ArrayAdapter<iBeacon> {

	private Activity context;
	private List<iBeacon> Items;
	private ViewHolder holder;
	private int LayoutID;
	private Map<String, iBeacon>  it = new HashMap<String, iBeacon>();
	private final int LIST_REFRESH_TIME=1000;//time in ms
	private final int SCAN_TIMEOUT_TIME=5000;//time in ms
	private long previousTime=0;

	class ViewHolder{
		protected TextView text;
	}


	public ListAdapterIbeacon(Activity context, List<iBeacon> Items,int LayoutID) {
		super(context, LayoutID,Items);
		this.context = context;
		this.Items = Items;
		this.LayoutID = LayoutID;
	}

	public void add(iBeacon miBeacon)
	{
		it.put(miBeacon.getMacAddress(), miBeacon);
		long currentTime = System.currentTimeMillis();
		if(currentTime > previousTime + LIST_REFRESH_TIME ){
		this.Items =   new ArrayList<iBeacon>(it.values());
		notifyDataSetChanged();
		previousTime = currentTime;
		}
	}

	public void add(Collection<iBeacon> beacons)
	{
		//this.Items =   new ArrayList<iBeacon>(beacons);
		for(iBeacon ib : beacons)
		{
			it.put(ib.getMacAddress(), ib);
		}
		long currentTime = System.currentTimeMillis();
		if(currentTime > previousTime + LIST_REFRESH_TIME ){
		this.Items =   new ArrayList<iBeacon>(it.values());
		notifyDataSetChanged();
		previousTime = currentTime;
		}
		
	}
	
	public void clear()
	{
		it.clear();
		Items.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return Items.size();
	}

	public iBeacon getobj(int pos) {
		return new ArrayList<iBeacon>(it.values()).get(pos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(LayoutID, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) convertView.findViewById(R.id.text);

			//TextView tv;
			//----------------------
			//tv=findViewById(R.id.text);   // you take TextView id (R.id.textview1)
			//tv = (TextView)findViewById(R.id.text);

			//TextView tv = (TextView) findViewById (R.id.text);


			LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(2000,200);
			//viewHolder.setLayoutParams(Params1);   //you take linearlayout and relativelayout.
			//viewHolder.text.setLayoutParams(Params1);
			viewHolder.text.setTextSize(20);
			convertView.setTag(viewHolder);
		} else {
		}

		holder = (ViewHolder) convertView.getTag();

		try{
			String txt="";
			long currentTime = System.currentTimeMillis();
			String name="";


			//Log.i("ListLoop" ,"Tick ="+MyVar.myInt);

			int minorVal=Items.get(position).getMinor();
			int rangeVal = Items.get(position).getRssi()+100;
			long TimeInMil = Items.get(position).getTimeMillis();
			if(minorVal== 1){name = "Ali Ahmed";}
			else if(minorVal== 2){name = "Haji 3";}
			else if(minorVal== 3){name = "Maryam Alkhereji";}
			else if(minorVal== 4){name = "Haji 4";}
			else if(minorVal== 5){name = "Haji 5";}
			//else if (minorVal== 3){	txt = txt+ "Pilgrim = Marium Jalil";}
			//else if (minorVal== 4){txt = txt+ "Pilgrim = Muhammad Yusuf";}
			/*String txt = "MAC = "+Items.get(position).getMacAddress()+""
					+ "\nRssi ="+Items.get(position).getRssi()
					+ "\nBeacon Type ="+Items.get(position).getType()
					+ "\nUUID ="+Items.get(position).getUUID()
					+ "\nMajor="+Items.get(position).getMajor()+"Minor="+Items.get(position).getMinor();*/
			//txt = "TagID = "+Items.get(position).getMinor()+" ("+name+")";
			//txt = name+ " (TagID = "+Items.get(position).getMinor()+")";
			txt = "\n"+name+"\n";

					//+ "\nTimeInMsec = "+TimeInMil;
			if(currentTime > TimeInMil + SCAN_TIMEOUT_TIME ) {
				//Log.i("Timeout", "Tag "+minorVal+" Timeout");
				txt = txt+ "\nProximity = Out of Range";
			}
			else
			{
				if(rangeVal>20){txt = txt+ "\nProximity = Near (Strength= "+rangeVal+")\n";}
				else{txt = txt+ "\nProximity = Far (Strength= "+rangeVal+")\n";}
			}


			//if(minorVal== 2)
			//	txt = txt+ "\nPilgrim = Ahmed Osama";

			/*if(Items.get(position).getType().equals(Beacon.BEACON_TYPE_COREBLU_IBEACON))
				txt = txt+ "\nBattery Voltage ="+Items.get(position).getbatteryVoltage();*/
			//Log.i("Timeout", "Current Time = "+currentTime+" LastSeen = "+TimeInMil);

			if(currentTime > TimeInMil + SCAN_TIMEOUT_TIME ) {
				Log.i("Timeout", "Tag "+minorVal+" Timeout");
			}

			holder.text.setText(txt);
		}
		catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return convertView;
	}
}


