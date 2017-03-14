package com.ubiqumproject.ubiqumproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

/**
 * Created by Pedro on 01-Mar-17.
 */

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;

	private EditText emailField;
	private EditText passwordField;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // views
        emailField = (EditText) findViewById(R.id.field_email);
        passwordField = (EditText) findViewById(R.id.field_password);

        // buttons
        findViewById(R.id.sign_in_button).setOnClickListener(this);

		mAuth = FirebaseAuth.getInstance();

		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if(user != null) {
					//User is signed in
					Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
				} else {
					// User is signed out
					Log.d(TAG, "onAuthStateChanged:signed_out");
				}
			}
		};


	}

	@Override
	public void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if(mAuthListener != null) {
			mAuth.removeAuthStateListener(mAuthListener);
		}
	}

	private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "singInWithEmail:onComplete:" + task.isSuccessful());

                        if(!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

	private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if(TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if(TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        }
    }
}
