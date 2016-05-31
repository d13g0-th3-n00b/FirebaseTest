package com.example.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText editTextEmail, editTextPassword;
	Button buttonLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		editTextEmail = (EditText)findViewById(R.id.editTextEmail);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		buttonLogin = (Button)findViewById(R.id.buttonLogin);
		
		buttonLogin.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (editTextEmail.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Enter registered email address.", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (editTextPassword.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent i = new Intent();
				i.putExtra("email", editTextEmail.getText().toString());
				i.putExtra("password", editTextPassword.getText().toString());
				setResult(1, i);
				finish();
			}
		});
	}
}
