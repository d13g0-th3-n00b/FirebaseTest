package com.example.firebasetest;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class MainActivity extends Activity {

	EditText editTextId;
	EditText editTextValue;
	Button buttonSetValue;
	Button buttonRegister;
	Button buttonLogin;
	Firebase fb; 
	
	boolean isLoggedIn = false;	
	
	User thisUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Firebase.setAndroidContext(getApplicationContext());
		
		fb = new Firebase("https://incandescent-heat-8271.firebaseio.com/");
		
		fb.child("message").addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot data, String arg1) {
				// TODO Auto-generated method stub
				Log.i("MESSAGE - CHILDEVENT LISTENER", data.toString());
				String message = "";
				if (data.child("screenname").getValue() != null && 
						data.child("message").getValue() != null) {
					message = data.child("screenname").getValue().toString() + ": " +
								data.child("message").getValue().toString();					  
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				}
			}
			
			@Override
			public void onChildAdded(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*fb.child("message").addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot data) {
				//Log.i("MESSAGE - CHILDEVENT LISTENER", data.toString());				
				String message = "";
				if (data.child("screenname").getValue() != null && 
						data.child("message").getValue() != null) {
					message = data.child("screenname").getValue().toString() + ": " +
								data.child("message").getValue().toString();					  
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				}	
			}
			
			@Override
			public void onCancelled(FirebaseError error) {
				
			}
		});*/
				
				
		
/*		fb.child("messages").addValueEventListener(new ValueEventListener() {

			  @Override
			  public void onDataChange(DataSnapshot snapshot) {		
				  Log.i("MESSAGES - VALUE EVENT LISTENER", snapshot.toString());
				  for (DataSnapshot data : snapshot.getChildren()) {
					  String message = "";
					  if (data.child("screenname").getValue() != null && 
							  data.child("message").getValue() != null) {
						  message = data.child("screenname").getValue().toString() + ": " +
								  	data.child("message").getValue().toString();
						  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
					  }					  
				  }
			  }
			  		  

			  @Override 
			  public void onCancelled(FirebaseError error) { 
				  Toast.makeText(getApplicationContext(), "ERROR" + error.toString(), Toast.LENGTH_LONG).show();
			  }			  	  
		});*/		
		
		
		fb.child("users").addValueEventListener(new ValueEventListener() {

			  @Override
			  public void onDataChange(DataSnapshot snapshot) {
				  for (DataSnapshot data : snapshot.getChildren()) {
					  Log.i("USERS", data.getValue().toString());
				  }
			  }
			  		  

			  @Override 
			  public void onCancelled(FirebaseError error) { 
				  Toast.makeText(getApplicationContext(), "ERROR" + error.toString(), Toast.LENGTH_LONG).show();
			  }			  	  
		});
				
		fb.addAuthStateListener(new Firebase.AuthStateListener() {
		    @Override
		    public void onAuthStateChanged(AuthData authData) {
		        if (authData != null) {
		            isLoggedIn = true;
		        } else {
		            isLoggedIn = false;
		        }
		    }			
		});
				
		editTextId = (EditText)findViewById(R.id.editTextId);
		editTextValue = (EditText)findViewById(R.id.editTextValue);
		buttonSetValue = (Button)findViewById(R.id.buttonSetValue);
		buttonRegister = (Button)findViewById(R.id.buttonRegister);
		buttonLogin = (Button)findViewById(R.id.buttonLogin);
		
		buttonSetValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLoggedIn) {
					Toast.makeText(getApplicationContext(), "User is not logged in.", Toast.LENGTH_LONG).show();
					return;
				}
				if (!editTextValue.getText().toString().equals("")) {					
					fb.child("message").child("screenname").setValue(thisUser.getScreenName());
					fb.child("message").child("message").setValue(editTextValue.getText().toString());
					editTextValue.setText("");
				}
			}
		});		
		
		buttonRegister.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
				startActivityForResult(intent, 1);
			}
		});
		
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivityForResult(intent, 2);
			}
		});
	}

	
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == 1) {
				final String email, password, screenName;
				email = data.getStringExtra("email");
				password = data.getStringExtra("password");
				screenName = data.getStringExtra("screenname");
				fb.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
				    @Override
				    public void onSuccess(Map<String, Object> result) {
				    	Log.i("RESULT",result.get("uid").toString());
				    	String uid = result.get("uid").toString();
				    	fb.child("users").child(uid).child("uid").setValue(uid);
				    	fb.child("users").child(uid).child("screenname").setValue(screenName);
				    	fb.child("users").child(uid).child("email").setValue(email);				    	
				    	fb.child("users").child(uid).child("totalmoney").setValue("0");
				    	Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
				    }

				    @Override
				    public void onError(FirebaseError firebaseError) {
				    	Log.i("REGISTRATION ERROR", firebaseError.toString());
				    }
				});
			}
			break;
		case 2:
			if (resultCode == 1) {
				String email, password;
				email = data.getStringExtra("email");
				password = data.getStringExtra("password");				
				LoginUser(email, password);
			}
			break;				
		}		
	}	
	
	
	
	
	
	
	private void LoginUser(final String email, String password) {
		// Create a handler to handle the result of the authentication
		Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
		    @Override
		    public void onAuthenticated(final AuthData authData) {
		    	String uid = authData.getUid();
		    	Log.i("UUID", uid);		    	
		    	Firebase temp = fb.child("users").child(uid);
		    	temp.addListenerForSingleValueEvent(new ValueEventListener() {
					
					@Override
					public void onDataChange(DataSnapshot snapshot) {
						Log.i("QUERY Q", snapshot.toString());
						thisUser = new User();
						thisUser.setUid(authData.getUid());
						thisUser.setEmail(snapshot.child("email").getValue().toString());
						thisUser.setScreenName(snapshot.child("screenname").getValue().toString());						
						thisUser.setTotalMoney(Double.valueOf(snapshot.child("totalmoney").getValue().toString()));
						editTextId.setText("Hello, I am " + thisUser.getScreenName());
					}
					
					@Override
					public void onCancelled(FirebaseError arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		    }

		    @Override
		    public void onAuthenticationError(FirebaseError firebaseError) {		    	
		        Toast.makeText(getApplicationContext(), firebaseError.toString(), Toast.LENGTH_LONG).show();
		        isLoggedIn = false;
		    }
		};

		fb.authWithPassword(email, password, authResultHandler);
	}






	@Override
	protected void onDestroy() {
		if (fb != null) {
			fb.unauth();
		}
		super.onDestroy();
	}
	
	
	
}
