
package com.ncc.org;


import com.ncc.org.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;



public class DetailNewsRoom extends Activity {
    WebView web;
    ProgressBar prgPageLoading;
    ImageButton btnBack, btnForward, btnRefresh, btnStop, btnShare;
    String URLFeed, titleFeed;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.detail_newsroom);
        
        Intent iGet = getIntent();
        URLFeed = iGet.getStringExtra("feedLink");
        titleFeed = iGet.getStringExtra("title");

        web = (WebView) findViewById(R.id.web);
        prgPageLoading = (ProgressBar) findViewById(R.id.prgPageLoading);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnShare = (ImageButton) findViewById(R.id.btnShare);

        web.setHorizontalScrollBarEnabled(true); 
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setJavaScriptEnabled(true);
        setProgressBarVisibility(true);
      //  web.getSettings().setPluginsEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setUseWideViewPort(true);
        web.setInitialScale(1);
        
        web.loadUrl(URLFeed);
        
        btnShare.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iShare = new Intent(Intent.ACTION_SEND);
				
				iShare.setType("text/plain");
				iShare.putExtra(Intent.EXTRA_SUBJECT,titleFeed);
				iShare.putExtra(Intent.EXTRA_TEXT, URLFeed);
				startActivity(iShare.createChooser(iShare, "Share via"));
			}
		});
        
        btnStop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				web.stopLoading();
			}
		});
        
        btnRefresh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				web.reload();
			}
		});
        
        btnBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(web.canGoBack()){
					web.goBack();
				}
			}
		});
        
        btnForward.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(web.canGoForward()){
					web.goForward();
				}
			}
		});
        
        final Activity act = this;
        web.setWebChromeClient(new WebChromeClient(){
        	public void onProgressChanged(WebView webview, int progress){
        		
        		act.setProgress(progress*100);
        		prgPageLoading.setProgress(progress);
        		
        	}
        	
        	
        });
        
        web.setWebViewClient(new WebViewClient() {
        	@Override
            public void onPageStarted( WebView view, String url, Bitmap favicon ) {

                super.onPageStarted( web, url, favicon );
                prgPageLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished( WebView view, String url ) {

                super.onPageFinished( web, url );
                prgPageLoading.setProgress(0);
                prgPageLoading.setVisibility(View.GONE);
                
                if(web.canGoBack()){
                	btnBack.setEnabled(true);
                	btnBack.setImageResource(R.drawable.navigation_back);
                }else{
                	btnBack.setEnabled(false);
                	btnBack.setImageResource(R.drawable.navigation_back2);
                }
                
                if(web.canGoForward()){
                	btnForward.setEnabled(true);
                	btnForward.setImageResource(R.drawable.navigation_forward);
                }else{
                	btnForward.setEnabled(false);
                	btnForward.setImageResource(R.drawable.navigation_forward2);
                }
            }   
        	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        	     Toast.makeText(act, description, Toast.LENGTH_SHORT).show();
        	   }
        	
        	
        	});
        
        	
        		
    }

    
}