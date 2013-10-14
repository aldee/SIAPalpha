package com.parama.android.siapkontraktor;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parama.android.siapkontraktor.dummy.DaftarProyek;
import com.parama.android.siapkontraktor.dummy.DaftarProyek.Proyek;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.text.NumberFormat;
import java.util.*;

/**
 * An activity representing a list of Projects. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ProjectDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ProjectListFragment} and the item details (if present) is a
 * {@link ProjectDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ProjectListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ProjectListActivity extends FragmentActivity implements
		ProjectListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private JSONParser jParser = new JSONParser();
	public static DefaultHttpClient httpClient;
	public JSONObject json;
	public static DaftarProyek dp;
	private ProgressDialog pDialog;
	public static JSONArray datas;
	
	public static String id = "";
	public static String namaProyek = "";
	public static String kodeProyek = "";
	public static String nilaiKontrak = "";
	private static final String url_all_proyek = "http://siap-kontraktor.com/android/api/get_all_proyek.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		
		httpClient = LoginActivity.httpClient;
		dp = new DaftarProyek();

		if (findViewById(R.id.project_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ProjectListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.project_list))
					.setActivateOnItemClick(true);
		}

		new GetAllProyek().execute();
	}
	
	class GetAllProyek extends AsyncTask<String, String, String> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			ProjectListFragment.adapter.clear();
			DaftarProyek.removeItems();
			pDialog = new ProgressDialog(ProjectListActivity.this);
			pDialog.setMessage("Please Wait");
			pDialog.setCancelable(false);
			pDialog.setIndeterminate(false);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			json = jParser.makeHttpRequest(url_all_proyek, "GET", params1, LoginActivity.httpClient);
			try {
				int success = json.getInt("success");
				if (success == 1) {
					datas = json.getJSONArray("data");
					for (int i = 0; i < datas.length() ; i++) {
						JSONObject c = datas.getJSONObject(i);
						id = Integer.toString(i);
						namaProyek = c.getString("nama");
						kodeProyek = c.getString("kode");
						nilaiKontrak = c.getString("nilai_kon");
						Log.v("JSON", datas.toString());
					}
				}

				else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			

			DaftarProyek.addItem(new Proyek(id, namaProyek, kodeProyek, nilaiKontrak));
			return null;
		}
		
		protected void onPostExecute(String arg) {
			ListFragment frag = (ListFragment) getFragmentManager().findFragmentById(R.id.project_list);
			ProjectListActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ProjectListFragment.adapter.notifyDataSetChanged();
					pDialog.dismiss();
				}

			});
		}
		
	}

	/**
	 * Callback method from {@link ProjectListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ProjectDetailFragment.ARG_ITEM_ID, id);
			ProjectDetailFragment fragment = new ProjectDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.project_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ProjectDetailActivity.class);
			detailIntent.putExtra(ProjectDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
