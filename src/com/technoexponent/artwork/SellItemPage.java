package com.technoexponent.artwork;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupMenu.OnMenuItemClickListener;

@SuppressLint("NewApi")
public class SellItemPage extends Activity implements OnMenuItemClickListener{

	Typeface tf1;
	String fontPath1 = null;

	RelativeLayout rltv_back;
	TextView menuImgTxt,txt_image_upload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellitem_activity);

		fontPath1 = "fonts/fontawesome-webfont.ttf";
		tf1 = Typeface.createFromAsset(getAssets(), fontPath1);

		rltv_back=(RelativeLayout) findViewById(R.id.rltv_back);
		menuImgTxt=(TextView) findViewById(R.id.menuImgTxt);
		txt_image_upload=(TextView) findViewById(R.id.txt_image_upload);

		menuImgTxt.setTypeface(tf1);
		txt_image_upload.setTypeface(tf1);


		menuImgTxt.setText("");
		txt_image_upload.setText("");


		menuImgTxt.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
				String emailAddress = sharedPreferences.getString("emailAddress", "");
				String userPassword = sharedPreferences.getString("userPassword", "");
				PopupMenu popupMenu = new PopupMenu(SellItemPage.this, v);
				popupMenu.setOnMenuItemClickListener(SellItemPage.this);
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



		rltv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				/*Intent act2 = new Intent(SellItemActivity.this, MainActivity.class);
				startActivity(act2);*/
			}
		});
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.loginmenu:
			Intent act21 = new Intent(SellItemPage.this, LoginPage.class);
			startActivity(act21);
			return true;
		case R.id.registermenu:
			Intent act211 = new Intent(SellItemPage.this, RegisterPage.class);
			startActivity(act211);
			return true;

		case R.id.myaccountmenu:
			Intent act2 = new Intent(SellItemPage.this, MyAccountPage.class);

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

}
