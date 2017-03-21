package com.ubiqumproject.ubiqumproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Pedro on 20-Mar-17.
 */

public class Message extends AppCompatActivity implements
        View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_main);

        findViewById(R.id.log_out_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.log_out_button) {
            FirebaseAuth.getInstance().signOut();
            finish();
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(Message.this, MainActivity.class));
    }
}
