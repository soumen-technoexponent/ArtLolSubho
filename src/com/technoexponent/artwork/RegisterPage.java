package com.technoexponent.artwork;


import java.net.URLEncoder;

import org.json.JSONObject;

import com.technoexponent.artwork.data.JSONfunctions;
import com.technoexponent.artwork.staticclass.StaticMethods;
import com.technoexponent.artwork.url.Url;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPage extends Activity {

	private ProgressDialog pDialog;
	JSONObject jobj;
	String msg,data,result;
	ImageView fbBtn_imgvw,googleBtn_imgvw;
	EditText edit_firstName,edit_lastName,edit_email,edit_password,edit_confirmPassword,edit_userName;
	RelativeLayout rltv_back;
	TextView check,submit;

	String firstName="",lastName="",emailAddress="",userPassword="",re_password="",userName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		hideKeyboard();

		//		Progress dialog initialization
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);

		edit_firstName = (EditText)findViewById(R.id.edit_firstName);
		edit_lastName = (EditText)findViewById(R.id.edit_lastName);
		edit_email = (EditText)findViewById(R.id.edit_email);
		edit_password = (EditText)findViewById(R.id.edit_password);
		edit_confirmPassword = (EditText)findViewById(R.id.edit_confirmPassword);
		edit_userName = (EditText)findViewById(R.id.edit_userName);
		check = (TextView)findViewById(R.id.check);
		submit = (TextView)findViewById(R.id.submit);
		rltv_back = (RelativeLayout)findViewById(R.id.rltv_back);
		fbBtn_imgvw = (ImageView)findViewById(R.id.fbBtn_imgvw);
		googleBtn_imgvw = (ImageView)findViewById(R.id.googleBtn_imgvw);
		

		fbBtn_imgvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				msg = "under Constraction!";
				new StaticMethods().popUp(RegisterPage.this, msg);
			}
		});
		
		googleBtn_imgvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				msg = "under Constraction!";
				new StaticMethods().popUp(RegisterPage.this, msg);
			}
		});
		
		rltv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
				finish();
				Intent act2 = new Intent(RegisterPage.this, MainActivity.class);
				
				startActivity(act2);
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstName = edit_firstName.getText().toString();
				lastName = edit_lastName.getText().toString();
				emailAddress = edit_email.getText().toString();
				userPassword = edit_password.getText().toString();
				re_password = edit_confirmPassword.getText().toString();
				userName = edit_userName.getText().toString();
				

				if(!firstName.equals("") && !lastName.equals("") && !emailAddress.equals("") && !userPassword.equals("") && !re_password.equals("") && !userName.equals(""))
				{
					if(new StaticMethods().isValid(emailAddress))
					{
						if(userPassword.equals(re_password))
						{
							new JsonForRegister().execute();
						}
							else
							{
								msg = "Password and confirm password are not matching!";
								new StaticMethods().popUp(RegisterPage.this, msg);
							}
					}
					else
					{
						msg = "Please enter valid email address!";
						new StaticMethods().popUp(RegisterPage.this, msg);
					}

				}
				else if (!firstName.equals("") && !lastName.equals("") && !emailAddress.equals("") && !userPassword.equals("") && !re_password.equals("") && userName.equals(""))
				{
					msg = "The Username field is required.";
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
				else
				{
					msg = "Please fill up all the field!";
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
			}
		});

		check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = edit_userName.getText().toString();
				if(!userName.equals(""))
				{
					new JsonForCheckUserName().execute();
				}
				else {
					msg = "Please fill up the user name field!";
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
			}
		});


	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		Intent act2 = new Intent(RegisterPage.this, MainActivity.class);
		
		startActivity(act2);
	}
	

	private class JsonForRegister extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}
	
		@Override
		protected Void doInBackground(Void... params) {

			try{


				data = URLEncoder.encode("firstName", "UTF-8") 
						+ "=" + URLEncoder.encode(firstName, "UTF-8"); 

				data += "&" + URLEncoder.encode("lastName", "UTF-8") + "="
						+ URLEncoder.encode(lastName, "UTF-8"); 

				data += "&" + URLEncoder.encode("emailAddress", "UTF-8") + "="
						+ URLEncoder.encode(emailAddress, "UTF-8"); 

				data += "&" + URLEncoder.encode("userPassword", "UTF-8") + "="
						+ URLEncoder.encode(userPassword, "UTF-8"); 

				data += "&" + URLEncoder.encode("re_password", "UTF-8") + "="
						+ URLEncoder.encode(re_password, "UTF-8"); 

				data += "&" + URLEncoder.encode("userName", "UTF-8") + "="
						+ URLEncoder.encode(userName, "UTF-8"); 


			}
			catch(Exception e)
			{
				e.printStackTrace();

			}

			String url = Url.URL_REGISTER;
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

			String register_success = "",register_error="";
			try{
				if(result!= null && !result.equals("FileNotFoundException") && !result.equals("OtherException"))
				{
					if(jobj.getString("has_error").equals("0"))
					{
						register_success = jobj.getString("register_success");
						Toast.makeText(getApplicationContext(), register_success, Toast.LENGTH_LONG).show();
						Intent in = new Intent(RegisterPage.this,LoginPage.class);
						startActivity(in);
					}
					else if(jobj.getString("has_error").equals("1")) 
					{
						register_error = jobj.getString("register_error");
						Toast.makeText(getApplicationContext(), register_error, Toast.LENGTH_LONG).show();
					}
				}
				else if(result.equals("FileNotFoundException"))
				{
					msg = "Sorry for the inconvenience caused to you!! We'll back shortly." ;
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
				else if(result.equals("OtherException"))
				{
					msg = "Please Check Your Internet Connection!!!" ;
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
			}
			catch(Exception e)
			{
				Log.e("log_tag", "Error converting result---- Register" + e.toString());

			}

			//			Progress dialog dismiss
			pDialog.dismiss();

		}

	}


	private class JsonForCheckUserName extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {


			try{


				data = URLEncoder.encode("usrname", "UTF-8") 
						+ "=" + URLEncoder.encode(userName, "UTF-8"); 
				

			}
			catch(Exception e)
			{
				e.printStackTrace();

			}

			String url = Url.URL_Check_Availbility;
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

			String has_message = "";
			try{
				if(result!= null && !result.equals("FileNotFoundException") && !result.equals("OtherException"))
				{
					if(jobj.getString("has_error").equals("0"))
					{
						Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
						has_message = jobj.getString("has_message");
						new StaticMethods().popUp(RegisterPage.this, userName+" Is "+has_message);

					}
					else if(jobj.getString("has_error").equals("1")) 
					{
						Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
						has_message = jobj.getString("has_message");
						new StaticMethods().popUp(RegisterPage.this, userName+" Is "+has_message);
					}
				}
				else if(result.equals("FileNotFoundException"))
				{
					msg = "Sorry for the inconvenience caused to you!! We'll back shortly." ;
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
				else if(result.equals("OtherException"))
				{
					msg = "Please Check Your Internet Connection!!!" ;
					new StaticMethods().popUp(RegisterPage.this, msg);
				}
			}
			catch(Exception e)
			{
				Log.e("log_tag", "Error converting result---- Register" + e.toString());

			}

			//			Progress dialog dismiss
			pDialog.dismiss();

		}

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


}
