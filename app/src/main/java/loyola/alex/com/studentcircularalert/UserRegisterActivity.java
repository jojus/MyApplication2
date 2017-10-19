package loyola.alex.com.studentcircularalert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Justin Joy (jojus) on 16-10-2017.
 */

public class UserRegisterActivity extends AppCompatActivity {
    private static final String TAG = UserRegisterActivity.class.getSimpleName();
    Button btnRegister;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    // Write a message to the database
    DatabaseReference mDatabaseReference;
    private EditText inputEmail, inputPassword, fullName, department, mobileNumber, userName;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        //auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //auth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        tool.setTitle("Signup");
        setSupportActionBar(tool);
        /*DATABASE REFERENCE*/
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.email1);
        inputPassword = (EditText) findViewById(R.id.password_edit);
        mobileNumber = (EditText) findViewById(R.id.mobileno1);
        department = (EditText) findViewById(R.id.department1);
        fullName = (EditText) findViewById(R.id.full_name);
        userName = (EditText) findViewById(R.id.user_name);
        btnRegister = (Button) findViewById(R.id.btnRegister);


       /* mAuthListener = new FirebaseAuth.AuthStateListener() {
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
        };*/

        mProgressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // getting user entered email and password
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String fname = fullName.getText().toString();
                String uname = userName.getText().toString();
                String mobileNum = mobileNumber.getText().toString();
                String dep = department.getText().toString();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(fname) && TextUtils.isEmpty(uname)
                        && TextUtils.isEmpty(mobileNum) && TextUtils.isEmpty(dep)) {
                    // email is empty
                    Toast.makeText(getApplicationContext(), "Enter email address!",
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(UserRegisterActivity.this, "user added",
                            Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String id = mDatabaseReference.push().getKey();
                    Users users = new Users(id, fname, uname, email, password, mobileNum, dep);
                    myRef.child(id).setValue(users);
                    /*myRef.child("email").setValue(email);
                    myRef.child("full_name").setValue(fname);
                    myRef.child("user_name").setValue(uname);
                    myRef.child("password").setValue(password);
                    myRef.child("mobile_number").setValue(mobileNum);
                    myRef.child("department").setValue(dep);*/
                }

                if (TextUtils.isEmpty(password)) {
                    // password is empty
                    Toast.makeText(getApplicationContext(), "Enter password!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressDialog.setMessage("Registering Please Wait....");
                mProgressDialog.show();

               /* if (password.length() < 6) {
                    // password length is minimum 6 chars
                    Toast.makeText(getApplicationContext(),
                            "Password too short, enter minimum 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }*/

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        UserRegisterActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(UserRegisterActivity.this,
                                            "Authentication Failed", Toast.LENGTH_LONG).show();
                                } else {
                                    Intent loginActivity = new Intent(UserRegisterActivity.this,
                                            LoginActivity.class);
                                    startActivity(loginActivity);
                                }
                            }
                        });

            }
        });
    }
}