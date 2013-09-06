package com.parama.android.siapkontraktor;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private EditText mHostname;
	private EditText mDatabase;
	private EditText mUsername;
	private EditText mPassword;
	private Button mLogin;
	
	public static DefaultHttpClient httpClient;
	private UserFunctions uf;
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		i = new Intent(this, MainActivity.class);
		
		initializeView();
		LoginActivity.httpClient = new DefaultHttpClient();
		mLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Login().execute();
			}
		});
	}

	private void initializeView() {
		// TODO Auto-generated method stub
		mHostname = (EditText) findViewById(R.id.hostname);
		mDatabase = (EditText) findViewById(R.id.database);
		mUsername = (EditText) findViewById(R.id.username);
		mPassword = (EditText) findViewById(R.id.password);
		mLogin = (Button) findViewById(R.id.button1);
		uf = new UserFunctions();
	}
	
	public void clearFields() {
		mHostname.setText("");
		mDatabase.setText("");
		mUsername.setText("");
		mPassword.setText("");
	}
	
	class Login extends AsyncTask<String, String, String> {
		ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
		JSONObject json;
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setMessage("Logging In");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String hostname = mHostname.getText().toString();
			String database = mDatabase.getText().toString();
			String username = mUsername.getText().toString();
			String password = mPassword.getText().toString();
			
			json = uf.loginUser(hostname, database, username, password, httpClient);
			return null;
		}
		
		protected void onPostExecute(String arg0) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						if (json.getString("success") != null) {
							if (Integer.parseInt(json.getString("success")) == 1) {
								pDialog.dismiss();
								Intent i = new Intent(getApplicationContext(), MainActivity.class);
								startActivity(i);
							}
							else {
								pDialog.dismiss();
								Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
							}
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					clearFields();
				}
			});
		}
	}
}
