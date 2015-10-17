package com.technoexponent.artwork;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.technoexponent.artwork.adapter.HomePhotoAdapter;
import com.technoexponent.artwork.data.JSONfunctions;
import com.technoexponent.artwork.staticclass.StaticMethods;
import com.technoexponent.artwork.url.Url;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class HomePage extends Activity implements OnMenuItemClickListener{
	Typeface tf1;
	String fontPath1 = null;
	TextView searchImg_txt,crossImg_txt,menuImg_txt;
	EditText searchText_edit;
	RelativeLayout autocmpltlist_rltv;
	ListView autocompltlistvw;
	GridView home_gridview;
	
	String emailAddress="",userPassword="";
	JSONObject jobj;
	String msg,data,result;
	HomePhotoAdapter homePhotoAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		hideKeyboard();

		fontPath1 = "fonts/fontawesome-webfont.ttf";
		tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
		
		searchImg_txt =(TextView)findViewById(R.id.searchImg_txt);
		crossImg_txt =(TextView)findViewById(R.id.crossImg_txt);
		menuImg_txt =(TextView)findViewById(R.id.menuImg_txt);
		searchText_edit =(EditText)findViewById(R.id.searchText_edit);
		autocmpltlist_rltv = (RelativeLayout)findViewById(R.id.autocmpltlist_rltv);
		autocompltlistvw = (ListView)findViewById(R.id.autocompltlistvw);
		home_gridview = (GridView) findViewById(R.id.home_gridview);
		 
		
		searchImg_txt.setTypeface(tf1);
		crossImg_txt.setTypeface(tf1);
		menuImg_txt.setTypeface(tf1);
		
		searchImg_txt.setText("");
		crossImg_txt.setText("");
		menuImg_txt.setText("");
		
		homePhotoAdapter= new HomePhotoAdapter(HomePage.this);
		home_gridview.setAdapter(homePhotoAdapter);
		
		menuImg_txt.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
				emailAddress = sharedPreferences.getString("emailAddress", "");
			userPassword = sharedPreferences.getString("userPassword", "");
			PopupMenu popupMenu = new PopupMenu(HomePage.this, v);
			popupMenu.setOnMenuItemClickListener(HomePage.this);
			//dotsetting visible or not
			if (emailAddress.equals("") && userPassword.equals(""))
			{
				
				popupMenu.inflate(R.menu.popup_menu_login);
				popupMenu.show();
				
			}
			else if (!emailAddress.equals("") && !userPassword.equals("")){
				
				popupMenu.inflate(R.menu.popup_menu);
				popupMenu.show();
				
			}}
		});
		
		searchText_edit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(searchText_edit.getText().toString().length()>0 && !searchText_edit.getText().toString().equals(" "))
				{
					showCrossIcon();
					new JsonForAutoComplete().execute();
				}
				else if(searchText_edit.getText().toString().length() == 0)
				{
					hideCrossIcon();
				}
				
			}
		});
		
		crossImg_txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchText_edit.setText("");
				hideListRltv();
				
			}
		});
		
		
		
		
		
	}
	/**
	 * on create end
	 */
	
	
	
	private void showCrossIcon()
	{
		crossImg_txt.setVisibility(View.VISIBLE);
	}
	
	private void hideCrossIcon()
	{
		crossImg_txt.setVisibility(View.GONE);
	}
	
	private void showListRltv()
	{
		autocmpltlist_rltv.setVisibility(View.VISIBLE);
	}
	
	private void hideListRltv()
	{
		autocmpltlist_rltv.setVisibility(View.GONE);
	}

	
	private class JsonForAutoComplete extends AsyncTask<Void, Void, Void> {


		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Showing progress dialog before making http request
			//							pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			
			String term = "zpzo";
			
			try{
				data = URLEncoder.encode("term", "UTF-8") 
						+ "=" + URLEncoder.encode(term, "UTF-8"); 

			}catch(Exception e)
			{e.printStackTrace();}


			JSONfunctions j = new JSONfunctions();
			String url = Url.URL_AutoComplete;
			Log.e("url", url);
			Log.e("data", data);
			try {
				result = j.getJSONfromURL(url,data);
				Log.e("result", result);
				if(result!= null && !result.equals("FileNotFoundException") && !result.equals("OtherException"))
				{
					jobj = new JSONObject(result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.d("sdafasdfdsfdsfsd", e.toString());
			}
			return null;
		}



		@Override
		protected void onPostExecute(Void  result2) {
			ArrayList<String> searchcity=new ArrayList<String>();
			boolean flagHasErrorValue=false;
			try{
				if(result!= null && !result.equals("FileNotFoundException") && !result.equals("OtherException"))
				{
					if(jobj.getString("has_error").equals("0"))
					{
						Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
					}
					else if(jobj.getString("has_error").equals("1")) 
					{
						Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
					}
				}
				else if(result.equals("FileNotFoundException"))
				{
					msg = "Sorry for the inconvenience caused to you!! We'll back shortly." ;
					new StaticMethods().popUp(HomePage.this, msg);
				}
				else if(result.equals("OtherException"))
				{
					msg = "Please Check Your Internet Connection!!!" ;
					new StaticMethods().popUp(HomePage.this, msg);
				}

			}
			catch(Exception e)
			{				
				Log.e("log_tag", "Error Homepage " + e.toString());
			}
			
		}
	}
	
	
	
	
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.loginmenu:
			Intent act21 = new Intent(HomePage.this, LoginPage.class);
			startActivity(act21);
			return true;
		case R.id.registermenu:
			Intent act211 = new Intent(HomePage.this, RegisterPage.class);
			startActivity(act211);
			return true;
			
		case R.id.myaccountmenu:
			Intent act2 = new Intent(HomePage.this, MyAccountPage.class);
			
			startActivity(act2);
			
			return true;
		case R.id.signoutmenu:
			SharedPreferences sharedPreferences1=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor1=sharedPreferences1.edit();
			editor1.putString("emailAddress","");
			editor1.putString("userPassword", "");
			editor1.commit();
			
			SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_RESPONSE", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor=sharedPreferences.edit();						
			editor.putString("firstName","");
			editor.putString("lastName", "");
			editor.putString("emailAddress","");
			editor.putString("userName","");						
			editor.commit();	

			return true;
		/*case R.id.appointments:
			Intent in = new Intent(HomePage.this,OrderDetails.class);

			startActivity(in);


			return true;*/
		
		}
		
		return false;
	}
	
	public void hideKeyboard() {   
		// Check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		//		Hiding Keyboard for first time
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
				);
	}

	private boolean exit = false;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
		emailAddress = sharedPreferences.getString("emailAddress", "");
//		Toast.makeText(getApplicationContext(), emailAddress, Toast.LENGTH_SHORT).show();
		if (emailAddress.equals(""))
		{
			
			Intent in = new Intent(HomePage.this,MainActivity.class);
			startActivity(in);
		}
		else if(!emailAddress.equals(""))
		{
			
			exit = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					exit = false;
				}
			}, 3 * 1000);

		}
	}
	
}
