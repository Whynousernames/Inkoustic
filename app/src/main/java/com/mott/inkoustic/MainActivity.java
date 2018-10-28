package com.mott.inkoustic;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;

    TextView changeSignupModeTextView;

    EditText passwordEditText;
    private File cacheDir = null;

    public void saveData(String data){
        File root_text = Environment.getExternalStorageDirectory();
        try{ File folder = new File(Environment.getExternalStorageDirectory() + "/InkousticImages");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }



            BufferedWriter fwv = new BufferedWriter(new FileWriter(new File("/sdcard/InkousticImages/SavedData.txt"), false));
            if (root_text.canWrite()) {
                fwv.write(data);
                fwv.close();
            }
        }catch (Exception e){
            Log.e("MODEL", "ERROR: " + e.toString());
        }
    }

    public void showHomeActivity()
    {


        saveData("hello");


        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override

    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {

        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
            signUp(view);
        }
        return false;
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.changeSignupModeTextView)
        {
            Button signupButton = (Button) findViewById(R.id.signupButton);

            if(signUpModeActive)
            {
                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");
            }
            else
            {
                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");
            }

        }
    }

    public void signUp(View view)
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        if(usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this, "A username and password are required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(signUpModeActive)
            {
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.setEmail(usernameEditText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null)
                        {
                            Toast.makeText(MainActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                            showHomeActivity();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user != null)
                        {
                            Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                            showHomeActivity();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    public void help(View view)
    {
        Toast.makeText(MainActivity.this, "Please enter a Username And Password to signup or login.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);
        changeSignupModeTextView.setOnClickListener(this);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText.setOnKeyListener(this);

        if(ParseUser.getCurrentUser() !=null)
        {
            showHomeActivity();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}
