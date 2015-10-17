package com.technoexponent.artwork.staticclass;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class StaticMethods extends Activity{

	public  String email=null,pass=null,response=null;
	public  ArrayList<String> user_data = new ArrayList<String>(); 
	Activity activity;
	public static void ImageParse()
	{
		System.out.println("Hello soumen");
		Log.e("------------Hello-----------------", "Hello Soumen bbc");
	}
	
	
	

	 public static boolean isConnected(Context context) {
		 
		

	        ConnectivityManager cm = (ConnectivityManager)context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	        try {
	            URL url = new URL("http://www.google.com/");
	            HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
	            urlc.setRequestProperty("User-Agent", "test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(1000); // mTimeout is in seconds
	            urlc.connect();
	            if (urlc.getResponseCode() == 200) {
	                return true;
	            } else {
	                return false;
	            }
	        }
	        catch(NetworkOnMainThreadException e){Log.i("warning", "Error checking internet connection", e);return false;}
	        catch (IOException e) {
	            Log.i("warning", "Error checking internet connection", e);
	            return false;
	        }
	    }

	    return false;
		  
	}
	public void popUp(Activity activity, String msg)
	{
		this.activity = activity;
		AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setCancelable(false);
		alertDialog.setMessage(msg);
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				

				response = "ok";
				Log.e("clk", "ok");

			}
		});
		alertDialog.show();

	}

	public boolean isValid(String email)
	{
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) 
		{
			return true;
		}
		else{
			return false;
		}

	}

	



}