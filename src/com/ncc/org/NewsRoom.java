
package com.ncc.org;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ncc.org.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class NewsRoom extends Activity {
	
	ListView listFeed;
	ProgressBar prgLoading;
	TextView txtAlert;
	
	ListAdapterNewsRoom la;
	
	static String[] title;
	static String[] pubDate;
	static String[] link;
	
	String URLFeed;
	
	URL Feed;
	DocumentBuilder db;
	Document doc;
	NodeList nodeList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsroom);
        
        Intent i_get = getIntent();
        URLFeed =i_get.getStringExtra("urlNewsRoomFeed");

        la = new ListAdapterNewsRoom(this);
        
       
        
        listFeed = (ListView) findViewById(R.id.listFeed);
        prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
        txtAlert = (TextView) findViewById(R.id.txtAlert);
        
		new getDataTask().execute();
        
		
		listFeed.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
			
				Menu(link[position], title[position]);
				
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
			getDataFromFeed();
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
    
    private void Menu(String tempLink, String tempTitle){
    	final String link = tempLink;
    	final String title = tempTitle;
		final CharSequence[] items = {"This App", "Browser"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Open with");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int position) {
		       if(position == 0){
		    	   Intent iDetailNewsRoom = new Intent(NewsRoom.this, DetailNewsRoom.class);
		    	   iDetailNewsRoom.putExtra("feedLink", link);
		    	   iDetailNewsRoom.putExtra("title", title);
		    	   startActivity(iDetailNewsRoom);
		       }else{
		    	   	Intent iBrowser = new Intent(Intent.ACTION_VIEW);
					iBrowser.setData(Uri.parse(link));
					startActivity(iBrowser);
		       }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
    
    
    /*
     * This code is used to get data from feed and store them
     * to array attributes
     */
    public void getDataFromFeed(){
    	
		try {
			Feed = new URL(URLFeed);
			DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
	    	db = dbf.newDocumentBuilder();
	    	doc = db.parse(new InputSource(Feed.openStream()));
			doc.getDocumentElement().normalize();
	    	nodeList = doc.getElementsByTagName("item");
	    	
	    	title = new String[nodeList.getLength()];
	    	pubDate = new String[nodeList.getLength()];
	    	link = new String[nodeList.getLength()];
	    	
	    	for(int i=0;i<nodeList.getLength();i++){
	    		Node node = nodeList.item(i);
	    		
	    		Element fstElmnt = (Element) node;
	    		
	    		NodeList titleList = fstElmnt.getElementsByTagName("title");
	    		Element titleElement = (Element) titleList.item(0);
	    		titleList = titleElement.getChildNodes();
	    		title[i] = ((Node) titleList.item(0)).getNodeValue();

	    		
	    		NodeList pubDateList = fstElmnt.getElementsByTagName("pubDate");
	    		Element pubDateElement = (Element) pubDateList.item(0);
	    		pubDateList = pubDateElement.getChildNodes();
	    		pubDate[i] = ((Node) pubDateList.item(0)).getNodeValue();
	    		
	    		
	    		NodeList linkList = fstElmnt.getElementsByTagName("link");
	    		Element linkElement = (Element) linkList.item(0);
	    		linkList = linkElement.getChildNodes();
	    		link[i] = ((Node) linkList.item(0)).getNodeValue();
	    		
	    	}
	    	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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