package loyola.alex.com.studentcircularalert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private TextView moveToLogin;
    private Spinner userRole;
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
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        tool.setTitle("Signup");
        setSupportActionBar(tool);
        /*DATABASE REFERENCE*/


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.email1);
        inputPassword = (EditText) findViewById(R.id.password_edit);
        mobileNumber = (EditText) findViewById(R.id.mobileno1);
        department = (EditText) findViewById(R.id.department1);
        fullName = (EditText) findViewById(R.id.full_name);
        userName = (EditText) findViewById(R.id.user_name);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        moveToLogin = (TextView) findViewById(R.id.link_to_login);
        userRole = (Spinner) findViewById(R.id.user_role);

        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(UserRegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
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
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String fname = fullName.getText().toString();
                final String uname = userName.getText().toString();
                final String mobileNum = mobileNumber.getText().toString();
                final String dep = department.getText().toString();

                userRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                            int arg2, long arg3) {
                        // TODO Auto-generated method stub

                        Toast.makeText(getBaseContext(), userRole.getSelectedItem().toString(),
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                final String selectedItem = userRole.getSelectedItem().toString();
                System.out.println("selected item" + selectedItem);

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(fname) && TextUtils.isEmpty(uname)
                        && TextUtils.isEmpty(mobileNum) && TextUtils.isEmpty(dep)
                        && TextUtils.isEmpty(password)) {
                    // email is empty
                    Toast.makeText(getApplicationContext(), "Enter all fields!",
                            Toast.LENGTH_SHORT).show();

                    return;
                } else if (password.length() < 6) {
                    // password length is minimum 6 chars
                    Toast.makeText(getApplicationContext(),
                            "Password too short, enter minimum 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                            UserRegisterActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(UserRegisterActivity.this,
                                                "Authentication Failed:Mail id is already "
                                                        + "registered",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        mDatabaseReference =
                                                FirebaseDatabase.getInstance().getReference(
                                                        "users/" + selectedItem);
                                        String id = mDatabaseReference.push().getKey();
                                        Users users = new Users(id, fname, uname, email, password,
                                                mobileNum, dep,
                                                selectedItem);
                                        mDatabaseReference.child(id).setValue(users);
                    /*myRef.child("email").setValue(email);
                    myRef.child("full_name").setValue(fname);
                    myRef.child("user_name").setValue(uname);
                    myRef.child("password").setValue(password);
                    myRef.child("mobile_number").setValue(mobileNum);
                    myRef.child("department").setValue(dep);*/
                                        Toast.makeText(UserRegisterActivity.this,
                                                "User Registered Successfull",
                                                Toast.LENGTH_LONG).show();
                                        Intent loginActivity = new Intent(UserRegisterActivity.this,
                                                LoginActivity.class);
                                        startActivity(loginActivity);
                                    }
                                }
                            });


                    // Toast.makeText(UserRegisterActivity.this, "user added",
                    //Toast.LENGTH_LONG).show();
                }

             /*   if () {
                    // password is empty
                    Toast.makeText(getApplicationContext(), "Enter password!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }*/

                mProgressDialog.setMessage("Registering Please Wait....");
                mProgressDialog.show();
            }
        });
    }
}