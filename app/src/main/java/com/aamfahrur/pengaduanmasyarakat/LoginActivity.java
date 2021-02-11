package com.aamfahrur.pengaduanmasyarakat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView txtSignup;
    Button Login;
    EditText edtUser,edtPass;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtSignup = findViewById(R.id.signUp);
        Login = findViewById(R.id.btnLogin);
        edtPass = findViewById(R.id.password);
        edtUser = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();

        mAuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent toHome = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(toHome);
                    finish();
                }
            }
        };

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthState);
    }

    private void login() {
        String username = edtUser.getText().toString()+"@gmail.com";
        String password = edtPass.getText().toString();
        if (username.isEmpty()){
            edtUser.setError("Tolong masukan email");
            edtUser.requestFocus();
        } else if (password.isEmpty()){
            edtPass.setError("Tolong masukan password");
            edtPass.requestFocus();
        } else if (username.isEmpty() && password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Harap isi form untuk register", Toast.LENGTH_SHORT).show();
        } else if (!(username.isEmpty() && password.isEmpty())){
            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent toHome = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(toHome);
                        finish();
                    }
                }
            });
        } else{
            Toast.makeText(LoginActivity.this, "Error Djantjok", Toast.LENGTH_SHORT).show();
        }
    }
}