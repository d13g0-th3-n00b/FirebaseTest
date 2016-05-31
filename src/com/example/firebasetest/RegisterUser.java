package com.example.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends Activity {
	
	EditText editTextEmail, 
				editTextPassword, 
				editTextConfirmPassword,
				editTextScreenName;
	Button buttonOk, buttonCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
		
		editTextEmail = (EditText)findViewById(R.id.editTextEmail);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
		editTextScreenName = (EditText)findViewById(R.id.editTextScreenName);
		buttonOk = (Button)findViewById(R.id.buttonOk);
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		
		buttonOk.setOnClickListener(new OnRegisterUserListener());
	}
	
	
	
	
	
	
	private class OnRegisterUserListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.buttonOk) {
				if (editTextEmail.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Enter a valid email address.", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (editTextPassword.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Password is required.", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())) {
					Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (editTextScreenName.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Screen name is required.", Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent i = new Intent();
				i.putExtra("email", editTextEmail.getText().toString());
				i.putExtra("password", editTextPassword.getText().toString());
				i.putExtra("screenname", editTextScreenName.getText().toString());
				setResult(1, i);	
			}	
			else {
				setResult(0, null);
			}
			finish();
		}
	}
}
