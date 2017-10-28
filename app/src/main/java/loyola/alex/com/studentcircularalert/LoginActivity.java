package loyola.alex.com.studentcircularalert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    private static ArrayList<Users> userDetails;
    Button btnClick, btnRegister;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference, mChild;
    SessionManager session;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        session = new SessionManager(getApplicationContext());
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnClick = (Button) findViewById(R.id.btnClick);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("Registration Types");

                Intent signup = new Intent(LoginActivity.this, UserRegisterActivity.class);
                startActivity(signup);
            }
        });

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Autenticate...");
                dialog.show();
                final String email_value = email.getText().toString();
                final String password_value = password.getText().toString();

                if (TextUtils.isEmpty(email_value)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(password_value)) {
                    Toast.makeText(getApplicationContext(), "Enter password!",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email_value, password_value)
                        .addOnCompleteListener(LoginActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "signInWithEmail:onComplete:"
                                                + task.isSuccessful());

                                        // If sign in fails, display a message to the
                                        // user. If
                                        // sign in succeeds
                                        // the auth state listener will be notified and
                                        // logic to
                                        // handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Log.w(TAG, "signInWithEmail:failed",
                                                    task.getException());
                                            dialog.dismiss();
                                            Toast.makeText(LoginActivity.this,
                                                    R.string.auth_failed,
                                                    Toast.LENGTH_SHORT).show();

                                        } else {
                                            //retrieveData();

                                            dialog.dismiss();
                                            Intent i = new Intent(getBaseContext(),
                                                    MainActivity.class);
                                            i.putExtra("email", email_value);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                });

            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void retrieveData() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference("users");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetails = new ArrayList<Users>();
                // StringBuffer stringbuffer = new StringBuffer();
                Intent i = getIntent();
                String mail = i.getStringExtra("email");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String children = dataSnapshot1.getKey();
                    System.out.println("childrens" + children);
                    mChild = mReference.child(children);
                    ReterivedUsers reterivedUsers =
                            dataSnapshot1.getValue(ReterivedUsers.class);

                    String name = reterivedUsers.getFullName();
                    String email = reterivedUsers.getEmailId();
                    String userName = reterivedUsers.getUserName();
                    String mobileNumber = reterivedUsers.getMobileNumber();
                    String department = reterivedUsers
                            .getDepartment();

                    Users listdata = new Users();
                    listdata.setFullName(name);
                    listdata.setEmailId(email);
                    listdata.setUserName(userName);
                    listdata.setMobileNumber(mobileNumber);
                    listdata.setDepartment(department);
                    userDetails.add(listdata);

                    System.out.println("ALL USERS IN FIREBASE" + listdata.getEmailId());
                    //session.createUserLoginSession(listdata.getFullName(),listdata.getUserName
                    // (),listdata.getEmailId(),listdata.getUserRole());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}