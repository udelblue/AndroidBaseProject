
package com.ncc.org;

import com.ncc.org.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Contact extends Activity {
	EditText edtSubject, edtMessage;
	Button btnSend;
	
	String subject, message;
	String[] email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		Intent iGet = getIntent();
		email = new String[]{iGet.getStringExtra("myEmail")};
		
		edtSubject = (EditText) findViewById(R.id.edtSubject);
		edtMessage = (EditText) findViewById(R.id.edtMessage);
		btnSend = (Button) findViewById(R.id.btnSend);
		
		
		btnSend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				subject = edtSubject.getText().toString();
				message = edtMessage.getText().toString();
				Intent iSend = new Intent(Intent.ACTION_SEND);
				iSend.putExtra(Intent.EXTRA_EMAIL, email);
				iSend.putExtra(Intent.EXTRA_SUBJECT, subject);
				iSend.setType("plain/text");
				iSend.putExtra(Intent.EXTRA_TEXT, message);
				
				startActivity(iSend);
			}
		});
	}
}
