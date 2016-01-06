
package com.ncc.org;


import com.ncc.org.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
	/*
	 * Put your Feed url, Facebook Page url, Twitter url, Youtube Channel url, and Email below. 
	 */
	final String Feed = "http://de-newcastlecounty.civicplus.com/support/calendar.xml";
	final String NewsRoom = "http://de-newcastlecounty.civicplus.com/support/calendar.xml";
	final String Facebook = "http://www.facebook.com/nccde";
	final String Twitter = "https://mobile.twitter.com/nccde";
	final String Youtube = "http://gdata.youtube.com/feeds/api/users/nccde/uploads?alt=jsonc&v=2";
	final String Email = "mobile.contact@nccde.org";
	
	GridView gridMenu;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        gridMenu = (GridView)findViewById(R.id.gridMenu);
        
        if(!isConnect()){
        	Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
       
        gridMenu.setAdapter(new ImageAdapter(this));
        gridMenu.setHorizontalScrollBarEnabled(false);
       
       
        gridMenu.setOnItemClickListener(new OnItemClickListener(){
       	public void onItemClick(AdapterView<?>parent, View v, int position, long id){
       		switch(position){
       		case 0: 
       			Intent iFeed = new Intent(Home.this, Feed.class);
       			iFeed.putExtra("urlFeed", Feed);
    			startActivity(iFeed);
   	            break;
       		case 1: 
       		case 2:
       			Intent iSocial = new Intent(Home.this, SocialMedia.class);
       			if(position == 1){
       				iSocial.putExtra("urlSocial", Facebook);
       			}else{
       				iSocial.putExtra("urlSocial", Twitter);
       			}
       			startActivity(iSocial);
   	            break;
       		case 3:
       			Intent iYoutube = new Intent(Home.this, Youtube.class);
       			iYoutube.putExtra("urlYoutube", Youtube);
       			startActivity(iYoutube);
       			break;
       		case 4:
       			Intent iNewsRoom = new Intent(Home.this, NewsRoom.class);
       			iNewsRoom.putExtra("urlNewsRoomFeed", NewsRoom);
       			startActivity(iNewsRoom);
       			break;
    		
       		}	
       	}
       }); 
    }
    
    /*
     * This method is used to check whether there is internet connection or not.
     */
    public boolean isConnect() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
public class ImageAdapter extends BaseAdapter {
    	
        Context mContext;
        public static final int ACTIVITY_CREATE = 10;
        
        public ImageAdapter(Context c){
        	mContext = c;
        }
        
        public int getCount(){
        	return MenuIcon.length;
        }
        
        public Object getItem(int position){
        	return null;
        }
        
        public long getItemId(int position){
        	return 0;
        }
        
        public View getView(int position, View convertView, ViewGroup parent){
        	View v;
        	if(convertView == null){
        		LayoutInflater li = getLayoutInflater();
        		v = li.inflate(R.layout.grid_layout, null);
        	}else{
        		v = convertView;
        	}

    		ImageView imgIcon = (ImageView) v.findViewById(R.id.imgIcon);
    		imgIcon.setImageResource(MenuIcon[position]);
    		TextView txtIcon = (TextView) v.findViewById (R.id.txtIcon);
    		txtIcon.setText(TextIcon[position]);
    		
        	return v;
        }

        private Integer[] MenuIcon = {
        		R.drawable.feed, R.drawable.facebook,
        		R.drawable.twitter, R.drawable.youtube, R.drawable.feed 
        };
        private String[] TextIcon = {
        		"Feed", "Facebook","Twitter","Youtube", "News"
        };
        
    }
   
}