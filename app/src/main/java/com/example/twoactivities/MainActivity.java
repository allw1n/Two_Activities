package com.example.twoactivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String reply;
    private EditText editTextMain;
    private TextView replyText, replyHeader;

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (replyHeader.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
            outState.putString("reply_text", replyText.getText().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "--------");
        Log.d(LOG_TAG, "onCreate");

        Button buttonMain = findViewById(R.id.buttonMain);
        editTextMain = findViewById(R.id.editTextMain);
        replyText = findViewById(R.id.replyText);
        replyHeader = findViewById(R.id.replyHeader);
        Intent intent = new Intent(this, SecondActivity.class);

        if (savedInstanceState != null) {
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");
            if (isVisible) {
                replyHeader.setVisibility(View.VISIBLE);
                replyText.setVisibility(View.VISIBLE);
                replyText.setText(savedInstanceState.getString("reply_text"));
            }
        }

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                            }
                            replyText.setText(reply);
                            replyHeader.setVisibility(View.VISIBLE);
                            replyText.setVisibility(View.VISIBLE);
                            editTextMain.getText().clear();
                        }
                    }
                });

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextMain.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                activityResultLauncher.launch(intent);
            }
        });
    }
}