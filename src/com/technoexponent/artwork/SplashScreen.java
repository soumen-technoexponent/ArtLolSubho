package com.technoexponent.artwork;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends Activity {
	String emailAddress="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread background = new Thread() {
            public void run() {
                 
                try {
                    // Thread will sleep for 5 seconds
                    sleep(2*1000);
                    /*Single sign on code here...............*/
                    SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
                    emailAddress=sharedPreferences.getString("emailAddress","");
                   // Toast.makeText(getApplicationContext(),emailadd, Toast.LENGTH_LONG).show();
                    if(emailAddress.equals(""))
                    {
                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    }
                    else
                    {
                    	Intent i=new Intent(getBaseContext(),HomePage.class);
                        startActivity(i);
                    }
                    //Remove activity
                    finish();
                     
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
        };
         
        // start thread
        background.start();
         

    }
     
    @Override
    protected void onDestroy() {
         
        super.onDestroy();
         
    }
    
    

}
