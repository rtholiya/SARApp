package com.sarapp.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sarapp.adaptor.DataAdaptor;
import com.sarapp.common.SARRating;
import com.sarapp.common.Utils;
import com.sarapp.sar.R;
import com.sarapp.ui.SideBar;

public class SarTask extends AsyncTask<String, Void, DataAdaptor> {

	private Activity activity;
	private LinearLayout pbLayout;
	private SideBar sideBar;

	public static final String TAG = "SarTask";

	public SarTask(Activity activity) {
		this.activity = activity;
		this.pbLayout = (LinearLayout) activity.findViewById(R.id.pbLayout);
		this.sideBar = (SideBar) activity.findViewById(R.id.sideBar);
	}

	@Override
	protected void onPreExecute() {
		pbLayout.setVisibility(View.VISIBLE);
		sideBar.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onPostExecute(DataAdaptor result) {
		pbLayout.setVisibility(View.GONE);
		sideBar.setVisibility(View.VISIBLE);
	}

	@Override
	protected DataAdaptor doInBackground(String... params) {
		try {
			List<SARRating> ratings = new ArrayList<SARRating>();
			Document doc = Jsoup.connect(
					"http://sarshield.com/radiation-chart/").get();
			Elements elements = doc.select("tr");
			for (int i = 1; i < elements.size(); i++) {
				Elements cols = elements.get(i).select("td");
				if (null != cols && null != cols.get(0)
						&& !("".equalsIgnoreCase(cols.get(0).text())))
					Log.i(TAG, "Modal:" + cols.get(0).text() + " US Rating:"
							+ cols.get(1).text() + " EU Rating:"
							+ cols.get(2).text());
				String mName = cols.get(0).text();
				String usRating = cols.get(1).text();
				String euRating = cols.get(2).text();
				if(!Utils.isBlankString(mName))
				{
					ratings.add(new SARRating(mName,!Utils.isBlankString(usRating) ? usRating : "N/A" , !Utils.isBlankString(euRating) ? usRating : "N/A"));
				}
				
			}
			
			Collections.sort(ratings, new Comparator<SARRating>() {
				public int compare(SARRating l,SARRating r)
				{
					return l.getModelName().compareTo(r.getModelName());
				}
			});
			
			final DataAdaptor mAdaptor = new DataAdaptor(activity, ratings);
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					ListView list = (ListView) activity
							.findViewById(R.id.listView);
					list.setAdapter(mAdaptor);
					sideBar.setListView(list);
				}
			});
		} catch (Exception e) {
			Log.e(TAG, "Msg:" + e.getMessage());
		}

		return null;
	}

}
