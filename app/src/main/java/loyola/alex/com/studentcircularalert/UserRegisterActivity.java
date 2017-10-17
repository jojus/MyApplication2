package loyola.alex.com.studentcircularalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Justin Joy (jojus) on 16-10-2017.
 */

public class UserRegisterActivity extends AppCompatActivity {
    private static final String TAG = UserRegisterActivity.class.getSimpleName();
    Button btnRegister;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        inputEmail = (EditText) findViewById(R.id.email1);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(UserRegisterActivity.this,
                            "Successfully signed in with: " + user.getEmail(),
                            Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(UserRegisterActivity.this, "Successfully signed out.",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // getting user entered email and password
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    // email is empty
                    Toast.makeText(getApplicationContext(), "Enter email address!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    // password is empty
                    Toast.makeText(getApplicationContext(), "Enter password!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    // password length is minimum 6 chars
                    Toast.makeText(getApplicationContext(),
                            "Password too short, enter minimum 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // authentication failed
                                    Toast.makeText(UserRegisterActivity.this,
                                            "Authentication failed",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // Successfully register then move to login activity
                                    startActivity(
                                            new Intent(UserRegisterActivity.this,
                                                    LoginActivity.class));
                                }
                            }
                        });
            }
        });
    }
}