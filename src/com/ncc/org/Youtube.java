
package com.ncc.org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ncc.org.R;



import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Youtube extends Activity {
	
	ListView listFeed;
	ProgressBar prgLoading;
	TextView txtAlert;
	
	ListAdapterYoutube la;
	
	static String[] thumbnail;
	static String[] title;
	static String[] uploader;
	static String[] viewCount;
	static String[] ID;
	
	String URLYoutube;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);
        
        Intent i_get = getIntent();
        URLYoutube =i_get.getStringExtra("urlYoutube");

        la = new ListAdapterYoutube(this);
        
        listFeed = (ListView) findViewById(R.id.listFeed);
        prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
        txtAlert = (TextView) findViewById(R.id.txtAlert);
        
		new getDataTask().execute();
        
		
		listFeed.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
			
				Intent ivideo = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+ID[position]));
				startActivity(ivideo);
			}
		});
        
        
      
    }
    
    /** this class is used to handle thread */
    public class getDataTask extends AsyncTask<Void, Void, Void>{
    	
    	getDataTask(){
    		if(!prgLoading.isShown()){
    			prgLoading.setVisibility(0);
				txtAlert.setVisibility(8);
    		}
    	}
    	
    	@Override
		 protected void onPreExecute() {
		  // TODO Auto-generated method stub
    		
    	}
    	
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			getDataFromYoutube();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//dialog.dismiss();
			prgLoading.setVisibility(8);
			if(title != null){
				listFeed.setVisibility(0);
				listFeed.setAdapter(la);
			}else{
				txtAlert.setVisibility(0);
			}
		}
    }
    
    
    
    
    /*
     * This code is used to get data from feed and store them
     * to array attributes
     */
    public void getDataFromYoutube(){
    	
    	try {
	        
	        HttpClient client = new DefaultHttpClient();
			// Perform a GET request to YouTube for a JSON list of all the videos by a specific user
			HttpUriRequest request = new HttpGet(URLYoutube);
			// Get the response that YouTube sends back
			HttpResponse response = client.execute(request);
			// Convert this response into an inputstream for the parser to use
			InputStream atomInputStream = response.getEntity().getContent();

			
			 BufferedReader in = new BufferedReader(new InputStreamReader(atomInputStream));
		        
	        //BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
	        String line;
	        String str = "";
	        while ((line = in.readLine()) != null){
	        	str += line;
	        }
        
			
				JSONObject json = new JSONObject(str);
				JSONObject dataObject = json.getJSONObject("data"); // this is the "data": { } part
				JSONArray items = dataObject.getJSONArray("items"); // this is the "items: [ ] part
				
				thumbnail = new String[items.length()];
				title = new String[items.length()];
				uploader = new String[items.length()];
				viewCount = new String[items.length()];
				ID = new String[items.length()];
				
				
				for (int i = 0; i < items.length(); i++) {
				    JSONObject youtubeObject = items.getJSONObject(i); 
				    title[i] = youtubeObject.getString("title");
				    uploader[i] = youtubeObject.getString("uploader");
				    viewCount[i] = youtubeObject.getString("viewCount");
				    ID[i] = youtubeObject.getString("id");
	                
				    
				    thumbnail[i] = youtubeObject.getJSONObject("thumbnail").getString("sqDefault");
	             
				}
				
				
		} catch (MalformedURLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}	
    }
   
    
    @Override
	public void onConfigurationChanged(final Configuration newConfig)
	{
	    // Ignore orientation change to keep activity from restarting
	    super.onConfigurationChanged(newConfig);
	}
    
    
}