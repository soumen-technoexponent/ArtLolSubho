
package com.technoexponent.artwork.data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

public class JSONfunctions {

	public String getJSONfromURL(String url, String data) throws UnsupportedEncodingException {

		String result = null;
		JSONObject jobj = null;
		int i = 3;
		String  s =null;
		
		Log.d("System.....JSONfunctions", data);
		
		// Convert response to string
		try {
			BufferedReader reader = null;
			
			URL url2 = new URL(url);

			// Send POST data request

			URLConnection conn = url2.openConnection(); 
			conn.setDoOutput(true); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write(data); 
			wr.flush(); 

			reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			result = sb.toString();
			Log.d("system.....JSONfunctions", result);
		}
		catch(FileNotFoundException e){
			Log.e("log_tag1", "Error FileNotFoundException " + e.toString());
			result = "FileNotFoundException";
		}
		
		catch (Exception e) {
			Log.e("log_tag2", "Error converting result " + e.toString());
			result = "OtherException";
		}

		
		return result;


	}
}
