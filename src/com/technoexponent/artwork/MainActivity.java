package com.technoexponent.artwork;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView login_txt,registration_txt;
	ImageView logo_imgvw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		login_txt = (TextView)findViewById(R.id.login_txt);
		registration_txt = (TextView)findViewById(R.id.registration_txt);
		logo_imgvw = (ImageView)findViewById(R.id.logo_imgvw);
		
		logo_imgvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent in = new Intent(MainActivity.this,HomePage.class);
				startActivity(in);
			}
		});
		
		login_txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent in = new Intent(MainActivity.this,LoginPage.class);
				startActivity(in);
			}
		});

		registration_txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent in = new Intent(MainActivity.this,RegisterPage.class);
				startActivity(in);
			}
		});
	}
	
	private boolean exit = false;
	@Override
	public void onBackPressed() {
		if (exit)
			MainActivity.this.finish();
		else {
			Toast.makeText(this, "Press Back again to Exit.",
					Toast.LENGTH_SHORT).show();
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
