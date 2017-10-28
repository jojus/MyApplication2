/*
package loyola.alex.com.studentcircularalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

*/
/**
 * Created by Justin Joy (jojus) on 28-10-2017.
 *//*


public class UserDetailsFromDb extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference;
    private static ArrayList<Users> userDetails;
    String mailId;
    SessionManager mSessionManager;
    public UserDetailsFromDb(String mailId) {
        this.mailId = mailId;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getApplicationContext());
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
                    if (mailId.equals(listdata.getEmailId())) {
                        String name1 = listdata.getFullName();
                        String email1 = listdata.getEmailId();
                        String userName1 = listdata.getUserName();
                        String mobileNumber1 = listdata.getMobileNumber();
                        String department1 = listdata
                                .getDepartment();
                        String role1 = reterivedUsers.getUserRole();
                        System.out.println(name1+email1+userName1+role1);
                        mSessionManager.createUserLoginSession(name1,email1,userName1,role1);

                        System.out.println("ALL USERS IN FIREBASE" + listdata.getEmailId());
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
*/
