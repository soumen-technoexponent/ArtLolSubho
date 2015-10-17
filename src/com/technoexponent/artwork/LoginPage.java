package com.technoexponent.artwork;

import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.technoexponent.artwork.data.JSONfunctions;

import com.technoexponent.artwork.staticclass.StaticMethods;
import com.technoexponent.artwork.url.Url;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends Activity{
	
	RelativeLayout back_rltv;
	EditText email_edit,password_edit;
	TextView login_txt,forgotPass_txt;
	private ProgressDialog pDialog;
	ImageView fbBtn_imgvw,googleBtn_imgvw;
	
	JSONObject jobj;
	String msg,data,result;
	String emailAddress="",userPassword="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
//		Progress dialog initialization
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		
		email_edit = (EditText)findViewById(R.id.email_edit);
		password_edit = (EditText)findViewById(R.id.password_edit);
		login_txt = (TextView)findViewById(R.id.login_txt);
		forgotPass_txt = (TextView)findViewById(R.id.forgotPass_txt);
		back_rltv = (RelativeLayout)findViewById(R.id.back_rltv);
		fbBtn_imgvw = (ImageView)findViewById(R.id.fbBtn_imgvw);
		googleBtn_imgvw = (ImageView)findViewById(R.id.googleBtn_imgvw);
		
		fbBtn_imgvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				msg = "under Constraction!";
				new StaticMethods().popUp(LoginPage.this, msg);
			}
		});
		
		googleBtn_imgvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				msg = "under Constraction!";
				new StaticMethods().popUp(LoginPage.this, msg);
			}
		});
		
		forgotPass_txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				msg = "under Constraction!";
				new StaticMethods().popUp(LoginPage.this, msg);
			}
		});
		
		login_txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				emailAddress = email_edit.getText().toString();
				userPassword = password_edit.getText().toString();
				
				if(!emailAddress.equals("") && !userPassword.equals(""))
				{
					new JsonForLogin().execute();
				}
				else
				{
					msg = "Please fill up all the field!";
					new StaticMethods().popUp(LoginPage.this, msg);
				}
			}
		});
		
		back_rltv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent act2 = new Intent(LoginPage.this, MainActivity.class);
				
				startActivity(act2);
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		Intent act2 = new Intent(LoginPage.this, MainActivity.class);
		
		startActivity(act2);
	}
	
//	For displaying the
	private class JsonForLogin extends AsyncTask<Void, Void, Void> {

		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			Check
			
			pDialog.show();
			
		}
		@Override
		protected Void doInBackground(Void... params) {

			
				try{
					String flag = null;
					SharedPreferences sharedLoc=getSharedPreferences("FLAG", Context.MODE_PRIVATE);
					flag = sharedLoc.getString("flag", "");
					
					data = URLEncoder.encode("emailAddress", "UTF-8") 
							+ "=" + URLEncoder.encode(emailAddress, "UTF-8"); 

					data += "&" + URLEncoder.encode("userPassword", "UTF-8") + "="
							+ URLEncoder.encode(userPassword, "UTF-8"); 

					
				}
				catch(Exception e)
				{
					e.printStackTrace();

				}
				
				String url = Url.URL_LOGIN;
				JSONfunctions j = new JSONfunctions();

				try {
					Log.d("--------Data Value----", data);
					result = j.getJSONfromURL(url,data);
					if(result!= null && !result.equals("FileNotFoundException") && !result.equals("OtherException"))
					{
						jobj = new JSONObject(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void  resultaaa) {

			
			try{
				if(result!= null && !result.equals("FileNotFoundException") && !result.equals("OtherException"))
				{
					if(jobj.getString("has_error").equals("0"))
					{
//						Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
						SharedPreferences sharedPreferences1=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor1=sharedPreferences1.edit();
						editor1.putString("emailAddress",emailAddress);
						editor1.putString("userPassword", userPassword);
						editor1.commit();
						
						SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_RESPONSE", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor=sharedPreferences.edit();
						String firstName = jobj.getJSONObject("profile_info").getString("firstName");
						String lastName = jobj.getJSONObject("profile_info").getString("lastName");
						String emailAddress = jobj.getJSONObject("profile_info").getString("emailAddress");
						String userName = jobj.getJSONObject("profile_info").getString("userName");
						String userID = jobj.getJSONObject("profile_info").getString("id");
						
						String address = jobj.getJSONObject("profile_info").getString("address");
						String web_link = jobj.getJSONObject("profile_info").getString("web_link");
						String profile_desc = jobj.getJSONObject("profile_info").getString("profile_desc");
						String paypal_email = jobj.getJSONObject("profile_info").getString("paypal_email");
						String skrill_email = jobj.getJSONObject("profile_info").getString("skrill_email");
						
						editor.putString("firstName", firstName);
						editor.putString("lastName",lastName);
						editor.putString("emailAddress",emailAddress);
						editor.putString("userName", userName);			
						editor.putString("userID", userID);	
						
						editor.putString("address", address);	
						editor.putString("web_link", web_link);	
						editor.putString("profile_desc", profile_desc);	
						editor.putString("paypal_email", paypal_email);	
						editor.putString("skrill_email", skrill_email);	
						
						Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
						editor.commit();	
//						Toast.makeText(getApplicationContext(), firstName, Toast.LENGTH_SHORT).show();
//						Toast.makeText(getApplicationContext(), lastName, Toast.LENGTH_SHORT).show();
//						Toast.makeText(getApplicationContext(), emailAddress, Toast.LENGTH_SHORT).show();
//						Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();
						finish();
						Intent in = new Intent(LoginPage.this,HomePage.class);
						startActivity(in);
					}
					else if(jobj.getString("has_error").equals("1")) 
					{
//						
						msg = jobj.getString("login_error");
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
					}
				}
				else if(result.equals("FileNotFoundException"))
				{
					msg = "Sorry for the inconvenience caused to you!! We'll back shortly." ;
					new StaticMethods().popUp(LoginPage.this, msg);
				}
				else if(result.equals("OtherException"))
				{
					msg = "Please Check Your Internet Connection!!!" ;
					new StaticMethods().popUp(LoginPage.this, msg);
				}
			}
			catch(Exception e)
			{
				Log.e("log_tag", "Error converting result---- Searchq" + e.toString());

			}
			//	Calling LISTVIEW WITH ADAPTER
			
			//			Progress dialog dismiss
			pDialog.dismiss();

		}

	}

}
