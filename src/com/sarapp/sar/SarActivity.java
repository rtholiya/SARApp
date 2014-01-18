package com.sarapp.sar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.sarapp.common.Utils;
import com.sarapp.tasks.SarTask;

public class SarActivity extends Activity {
	
	AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sar);
		
		//Fetch and display the table
		if(Utils.isOnline(this))
		{
			new SarTask(this).execute("nothing");
		}
		else
		{
			LinearLayout pbLayout =  (LinearLayout)findViewById(R.id.pbLayout);
			pbLayout.setVisibility(View.VISIBLE);
			//Show alert to the user
			AlertDialog alertDialog = new AlertDialog.Builder(this).create(); 
		    alertDialog.setTitle("FlickTorrents");
		    alertDialog.setMessage("Application requires active network connection.");
		    alertDialog.setButton( Dialog.BUTTON_POSITIVE, "Settings", new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int which) {
		    	   Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
		    	   startActivity(settings);
		    	   }
		       }
		       );
		    alertDialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sar, menu);
		return true;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    View v = inflater.inflate(R.layout.activity_sar, container, false);
	    mAdView = (AdView) v.findViewById(R.id.ad);
	    mAdView.loadAd(new AdRequest());
	    return v;
	}


}
