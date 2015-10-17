package com.technoexponent.artwork;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.technoexponent.artwork.data.JSONfunctions;
import com.technoexponent.artwork.staticclass.StaticMethods;
import com.technoexponent.artwork.url.Url;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class MyAccountPage extends Activity implements OnMenuItemClickListener{
	Typeface tf1;
	String fontPath1 = null;
	private static int RESULT_LOAD_IMAGE = 1;
	private ProgressDialog pDialog;
	JSONObject jobj;
	String msg="",data="",result="";
	
	EditText edit_fname,edit_lname,edit_email,edit_address,edit_weblink,edit_profile,edit_paypalemail,edit_skrillemail,edit_currentpassword,edit_newpassword,edit_confirmnewpassword;
	TextView saveChange,menuImg_txt;
	LinearLayout back_linear;
	ImageView profile_img;
	String userID = "",firstName="",lastName="",emailAddress="",address="",web_link="",profile_desc="",paypal_email="",skrill_email="",userPassword="",newPassword="",re_password="";
	 int serverResponseCode = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount_activity);
		hideKeyboard();
//		Progress dialog initialization
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		
		fontPath1 = "fonts/fontawesome-webfont.ttf";
		tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
		
		edit_fname = (EditText)findViewById(R.id.edit_fname);
		edit_lname = (EditText)findViewById(R.id.edit_lname);
		edit_email = (EditText)findViewById(R.id.edit_email);	
		edit_address = (EditText)findViewById(R.id.edit_address);
		edit_weblink = (EditText)findViewById(R.id.edit_weblink);
		edit_profile = (EditText)findViewById(R.id.edit_profile);
		edit_paypalemail = (EditText)findViewById(R.id.edit_paypalemail);
		edit_skrillemail = (EditText)findViewById(R.id.edit_skrillemail);
		edit_currentpassword = (EditText)findViewById(R.id.edit_currentpassword);
		edit_newpassword = (EditText)findViewById(R.id.edit_newpassword);
		edit_confirmnewpassword = (EditText)findViewById(R.id.edit_confirmnewpassword);
		profile_img= (ImageView)findViewById(R.id.profile_img);
		saveChange = (TextView)findViewById(R.id.saveChange);
		menuImg_txt = (TextView)findViewById(R.id.menuImg_txt);
		back_linear = (LinearLayout)findViewById(R.id.back_linear);
		
		
		
		SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_RESPONSE", Context.MODE_PRIVATE);
		firstName = sharedPreferences.getString("firstName", "");
		lastName = sharedPreferences.getString("lastName", "");
		emailAddress = sharedPreferences.getString("emailAddress", "");
		address = sharedPreferences.getString("address", "");
		web_link = sharedPreferences.getString("web_link", "");
		profile_desc = sharedPreferences.getString("profile_desc", "");
		paypal_email = sharedPreferences.getString("paypal_email", "");
		skrill_email = sharedPreferences.getString("skrill_email", "");
		
		
//		Toast.makeText(getApplicationContext(), firstName, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), lastName, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), emailAddress, Toast.LENGTH_SHORT).show();
		edit_fname.setText(firstName);
		edit_lname.setText(lastName);
		edit_email.setText(emailAddress);
		edit_address.setText(address);
		edit_weblink.setText(web_link);
		edit_profile.setText(profile_desc);
		edit_paypalemail.setText(paypal_email);
		edit_skrillemail.setText(skrill_email);
		
		
		
		
		menuImg_txt.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_INFO", Context.MODE_PRIVATE);
				emailAddress = sharedPreferences.getString("emailAddress", "");
			userPassword = sharedPreferences.getString("userPassword", "");
			
			PopupMenu popupMenu = new PopupMenu(MyAccountPage.this, v);
			popupMenu.setOnMenuItemClickListener(MyAccountPage.this);
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
		
		back_linear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		profile_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		menuImg_txt.setTypeface(tf1);
		menuImg_txt.setText("");
		
		saveChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				firstName = edit_fname.getText().toString();
				lastName = edit_lname.getText().toString();
				emailAddress = edit_email.getText().toString();
				address = edit_address.getText().toString();
				web_link = edit_weblink.getText().toString();
				profile_desc = edit_profile.getText().toString();
				paypal_email = edit_paypalemail.getText().toString();
				skrill_email = edit_skrillemail.getText().toString();
				userPassword = edit_currentpassword.getText().toString();
				newPassword = edit_newpassword.getText().toString();
				re_password = edit_confirmnewpassword.getText().toString();
				
				new JsonForupdateProfile().execute();
			}
		});
		
	}
	
//	For displaying the service list json parsing
	private class JsonForupdateProfile extends AsyncTask<Void, Void, Void> {



		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//			Check

			pDialog.show();

		}
		@Override
		protected Void doInBackground(Void... params) {


			try{

				SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_RESPONSE", Context.MODE_PRIVATE);
				userID= sharedPreferences.getString("userID", "");
				data = URLEncoder.encode("firstName", "UTF-8") 
						+ "=" + URLEncoder.encode(firstName, "UTF-8"); 

				data += "&" + URLEncoder.encode("lastName", "UTF-8") + "="
						+ URLEncoder.encode(lastName, "UTF-8"); 

				data += "&" + URLEncoder.encode("emailAddress", "UTF-8") + "="
						+ URLEncoder.encode(emailAddress, "UTF-8"); 

				data += "&" + URLEncoder.encode("address", "UTF-8") + "="
						+ URLEncoder.encode(address, "UTF-8"); 

				data += "&" + URLEncoder.encode("web_link", "UTF-8") + "="
						+ URLEncoder.encode(web_link, "UTF-8"); 

				data += "&" + URLEncoder.encode("profile_desc", "UTF-8") + "="
						+ URLEncoder.encode(profile_desc, "UTF-8"); 

				data += "&" + URLEncoder.encode("paypal_email", "UTF-8") + "="
						+ URLEncoder.encode(paypal_email, "UTF-8"); 

				data += "&" + URLEncoder.encode("skrill_email", "UTF-8") + "="
						+ URLEncoder.encode(skrill_email, "UTF-8"); 

				data += "&" + URLEncoder.encode("userPassword", "UTF-8") + "="
						+ URLEncoder.encode(userPassword, "UTF-8"); 
				
				data += "&" + URLEncoder.encode("newPassword", "UTF-8") + "="
						+ URLEncoder.encode(newPassword, "UTF-8"); 
				
				data += "&" + URLEncoder.encode("re_password", "UTF-8") + "="
						+ URLEncoder.encode(re_password, "UTF-8"); 
				
				data += "&" + URLEncoder.encode("userID", "UTF-8") + "="
						+ URLEncoder.encode(userID, "UTF-8"); 

			}
			catch(Exception e)
			{
				e.printStackTrace();

			}

			String url = Url.URL_Update_Profile;
			JSONfunctions j = new JSONfunctions();

			try {
				Log.d("--------Data Value----", data);
				result = j.getJSONfromURL(url,data);
				Log.d("--------result Value----", result);
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
						
						String profile_update_success = jobj.getString("profile_update_success");
						Toast.makeText(getApplicationContext(), profile_update_success, Toast.LENGTH_SHORT).show();
						SharedPreferences sharedPreferences=getSharedPreferences("CUSTOMER_LOGIN_RESPONSE", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor=sharedPreferences.edit();
						
						editor.putString("firstName", firstName);
						editor.putString("lastName",lastName);
						editor.putString("emailAddress",emailAddress);
										
						editor.putString("address", address);
						editor.putString("web_link",web_link);
						editor.putString("profile_desc",profile_desc);
						editor.putString("paypal_email", paypal_email);
						editor.putString("skrill_email",skrill_email);
						
						editor.commit();
					}
					else if(jobj.getString("has_error").equals("1")) 
					{
						Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
						
					}
				}
				else if(result.equals("FileNotFoundException"))
				{
					msg = "Sorry for the inconvenience caused to you!! We'll back shortly." ;
					new StaticMethods().popUp(MyAccountPage.this, msg);
				}
				else if(result.equals("OtherException"))
				{
					msg = "Please Check Your Internet Connection!!!" ;
					new StaticMethods().popUp(MyAccountPage.this, msg);
				}
			}
			catch(Exception e)
			{
				Log.e("log_tag", "Error converting result---- Register" + e.toString());

			}
			//	Calling LISTVIEW WITH ADAPTER

			//			Progress dialog dismiss
			pDialog.dismiss();

		}

	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	    		try{
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_LONG).show();
				cursor.close();
				
				
				profile_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
				Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_SHORT).show();
				int response=uploadFile(picturePath);
				Toast.makeText(getApplicationContext(), Integer.toString(response), Toast.LENGTH_SHORT).show();
	    		}
	    		catch(Exception e)
	    		{
	    			e.printStackTrace();
	    		}
			}
	    
	    
	    }
	 
	 public int uploadFile(String sourceFileUri) {
         String upLoadServerUri = "http://10.0.2.2/upload_test/upload_media_test.php";
         String fileName = sourceFileUri;

         HttpURLConnection conn = null;
         DataOutputStream dos = null;  
         String lineEnd = "\r\n";
         String twoHyphens = "--";
         String boundary = "*****";
         int bytesRead, bytesAvailable, bufferSize;
         byte[] buffer;
         int maxBufferSize = 1 * 1024 * 1024; 
         File sourceFile = new File(sourceFileUri); 
         if (!sourceFile.isFile()) {
          Log.e("uploadFile", "Source File Does not exist");
          return 0;
         }
             try { // open a URL connection to the Servlet
              FileInputStream fileInputStream = new FileInputStream(sourceFile);
              URL url = new URL(upLoadServerUri);
              conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
              conn.setDoInput(true); // Allow Inputs
              conn.setDoOutput(true); // Allow Outputs
              conn.setUseCaches(false); // Don't use a Cached Copy
              conn.setRequestMethod("POST");
              conn.setRequestProperty("Connection", "Keep-Alive");
              conn.setRequestProperty("ENCTYPE", "multipart/form-data");
              conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
              conn.setRequestProperty("image_url", fileName); 
              dos = new DataOutputStream(conn.getOutputStream());
              
//              http://10.0.2.2/upload_test/upload_media_test.php
            	  
              dos.writeBytes(twoHyphens + boundary + lineEnd); 
              dos.writeBytes("Content-Disposition: form-data; name=\"image_url\";filename=\""+ fileName + "\"" + lineEnd);
              Log.d("print url ", dos.toString());
              dos.writeBytes(lineEnd);
    
              bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
    
              bufferSize = Math.min(bytesAvailable, maxBufferSize);
              buffer = new byte[bufferSize];
    
              // read file and write it into form...
              bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                
              while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);               
               }
    
              // send multipart form data necesssary after file data...
              dos.writeBytes(lineEnd);
              dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
    
              // Responses from the server (code and message)
              serverResponseCode = conn.getResponseCode();
              String serverResponseMessage = conn.getResponseMessage();
               
              Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
              if(serverResponseCode == 200){
                  runOnUiThread(new Runnable() {
                       public void run() {
                           
                           Toast.makeText(getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                       }
                   });                
              }    
              
              //close the streams //
              fileInputStream.close();
              dos.flush();
              dos.close();
               
         } catch (MalformedURLException ex) {  
//             dialog.dismiss();  
             ex.printStackTrace();
             Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
             Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
         } catch (Exception e) {
//             dialog.dismiss();  
             e.printStackTrace();
             Toast.makeText(getApplicationContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
             Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
         }
//         dialog.dismiss();       
         return serverResponseCode;  
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
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.loginmenu:
			Intent act21 = new Intent(MyAccountPage.this, LoginPage.class);
			startActivity(act21);
			return true;
		case R.id.registermenu:
			Intent act211 = new Intent(MyAccountPage.this, RegisterPage.class);
			startActivity(act211);
			return true;
			
		case R.id.myaccountmenu:
			finish();
			Intent act2 = new Intent(MyAccountPage.this, MyAccountPage.class);
			
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
			finish();
			Intent act22 = new Intent(MyAccountPage.this, MainActivity.class);
			
			startActivity(act22);
			return true;
		/*case R.id.appointments:
			Intent in = new Intent(HomePage.this,OrderDetails.class);

			startActivity(in);


			return true;*/
		
		}
		
		return false;
	}

}
