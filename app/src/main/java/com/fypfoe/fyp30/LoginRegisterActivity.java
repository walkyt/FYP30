package com.fypfoe.fyp30;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginRegisterActivity extends AppCompatActivity {
    private Button userLoginButton;
    private Button userRegisterButton;
    private TextView userCreateAccount;
    private EditText userEmail;
    private EditText userPassword;

    private DatabaseReference userDatabaseReference;
    private String onlineUserID;

    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    public void registerUser(View view){
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginRegisterActivity.this,"Please fill in your email address or password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("User Registration");
            loadingBar.setMessage("Please wait for a moment.");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                public void onComplete(@NonNull Task<AuthResult> task){
                    if(task.isSuccessful()){
                        onlineUserID = mAuth.getCurrentUser().getUid();
                        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(onlineUserID);

                        userDatabaseReference.setValue(true);

                        Intent userIntent = new Intent(LoginRegisterActivity.this, SetTimerActivity.class);
                        startActivity(userIntent);

                        Toast.makeText(LoginRegisterActivity.this," User Register Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else{
                        Toast.makeText(LoginRegisterActivity.this, "User Register Unsuccessful.Please try again.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Log.d(" Firebase ", "Register unsuccessful: " + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void loginUser(View view){
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginRegisterActivity.this,"Please fill in your email address or password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("User Logging In");
            loadingBar.setMessage("Please wait for a moment.");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                public void onComplete(@NonNull Task<AuthResult> task){
                    if(task.isSuccessful()){
                        Intent loginSuccessful = new Intent (LoginRegisterActivity.this, SetTimerActivity.class);
                        startActivity(loginSuccessful);
                        Toast.makeText(LoginRegisterActivity.this," User Login Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else{
                        Toast.makeText(LoginRegisterActivity.this, "User Login Unsuccessful.Please try again.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();


                        Log.d(" Firebase ", "Login unsuccessful: " + task.getException().getMessage());
                }};
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        mAuth = FirebaseAuth.getInstance();

        userLoginButton = (Button) findViewById(R.id.loginUser);
        userRegisterButton = (Button) findViewById (R.id.registerUser);
        userCreateAccount = (TextView) findViewById(R.id.userRegisterLink);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPassword = (EditText) findViewById(R.id.userPassword);
        loadingBar =  new ProgressDialog(this);


        userRegisterButton.setVisibility(View.INVISIBLE);
        userRegisterButton.setEnabled(false);

        userCreateAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                userLoginButton.setVisibility(View.INVISIBLE);
                userCreateAccount.setVisibility(View.INVISIBLE);

                userRegisterButton.setVisibility(View.VISIBLE);
                userRegisterButton.setEnabled(true);
            }
        });
    }
}
