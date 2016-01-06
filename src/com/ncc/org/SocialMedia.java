
package com.ncc.org;


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

public class SocialMedia extends Activity {
    WebView web;
    ProgressBar prgPageLoading;
    ImageButton btnBack, btnForward, btnRefresh, btnStop;
    String URLSocial;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.social_media);
        
        Intent iGet = getIntent();
        URLSocial = iGet.getStringExtra("urlSocial");

        web = (WebView) findViewById(R.id.web);
        prgPageLoading = (ProgressBar) findViewById(R.id.prgPageLoading);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setJavaScriptEnabled(true);
      //  web.getSettings().setPluginsEnabled(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        
        web.loadUrl(URLSocial);
        
        web.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(SocialMedia.this, web.getUrl(), Toast.LENGTH_LONG).show();
					
			}
		});
        
        btnStop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				web.stopLoading();
			}
		});
        
        btnRefresh.setOnClickListener(new View.OnClickListener() {
			
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