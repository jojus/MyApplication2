package loyola.alex.com.studentcircularalert;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static ArrayList<Users> userDetails;
    Toolbar toolbar;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference, mChild;
    String associateRole;
    String uRole;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sent Messages");
        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());

        Intent i = getIntent();
        final String mail = i.getStringExtra("email");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference("users");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDetails = new ArrayList<>();
                // StringBuffer stringbuffer = new StringBuffer();
                String child = dataSnapshot.getKey();
                DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child(
                        "users").child(child);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ReterivedUsers reterivedUsers =
                                    ds.getValue(ReterivedUsers.class);

                            String name = reterivedUsers.getFullName();
                            String email = reterivedUsers.getEmailId();
                            String userName = reterivedUsers.getUserName();
                            String mobileNumber = reterivedUsers.getMobileNumber();
                            String department = reterivedUsers
                                    .getDepartment();
                            String role = reterivedUsers.getUserRole();

                            Users listdata = new Users();
                            listdata.setFullName(name);
                            listdata.setEmailId(email);
                            listdata.setUserName(userName);
                            listdata.setMobileNumber(mobileNumber);
                            listdata.setDepartment(department);
                            listdata.setUserRole(role);
                            userDetails.add(listdata);
                            // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();
                            if (mail.equals(listdata.getEmailId())) {
                                String name1 = listdata.getFullName();
                                String email1 = listdata.getEmailId();
                                String userName1 = listdata.getUserName();
                                String mobileNumber1 = listdata.getMobileNumber();
                                String department1 = listdata
                                        .getDepartment();
                                String role1 = reterivedUsers.getUserRole();
                                session.createUserLoginSession(name1, userName1, email1, role1);

                            }
                            System.out.println("ALL USERS IN FIREBASE" + listdata.getEmailId());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String children = dataSnapshot1.getKey();

                    mChild = mReference.child(children);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*-----NAVIGATION DRAWER BASED ON LOGIN ID-------*/
        /*if(islogin)
        {
            *//*==== ADMIN ====*//*
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        } else
        {
            *//*===== USERS =====*//*
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.user_navigation_drawer);
        }*/

        HashMap<String, String> user = session.getUserDetails();

        // get name
        uRole = user.get(SessionManager.KEY_USER_ROLE);
        System.out.println("role" + uRole);

        // get email
        String email = user.get(SessionManager.KEY_EMAIL);
        System.out.println("email" + email);
        System.out.println(session.getUserDetails());
        // String email = SessionManager.KEY_EMAIL;
        //String uRole = SessionManager.KEY_USER_ROLE;
        /*String emailId = sp.getString("email", "");
        String role = sp.getString("role", "");*/


        /*if (email.equals(mail)) {
            associateRole = uRole;
        }
        System.out.println("role"+associateRole+"#######"+"urole"+uRole);*/

        if (uRole.equals("admin")) {
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.lyt_frame_layout,
                    new loyola.alex.com.studentcircularalert.HomeFragment());
            transaction.commit();
        } else {
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.user_navigation_drawer);
            FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.lyt_frame_layout,
                    new loyola.alex.com.studentcircularalert.HomeFragment());
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (uRole.equals("admin")) {
            if (id == R.id.nav_sent_message) {
                FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.lyt_frame_layout, new HomeFragment());
                transaction.commit();
                toolbar.setTitle("Sent Messages");
            } else if (id == R.id.nav_new_message) {
                FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.lyt_frame_layout,
                        new loyola.alex.com.studentcircularalert.NewMessageFragment());
                transaction.commit();
                toolbar.setTitle("New Messages");

            } else if (id == R.id.nav_about_us) {
                FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.lyt_frame_layout,
                        new loyola.alex.com.studentcircularalert.AboutUsFragment());
                transaction.commit();
                toolbar.setTitle("About Us");

            } else if (id == R.id.nav_logout) {
                startActivity(new Intent(MainActivity.this,
                        loyola.alex.com.studentcircularalert.LoginActivity.class));
                finish();
            }
        } else {
            if (id == R.id.nav_sent_message) {
                FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.lyt_frame_layout, new HomeFragment());
                transaction.commit();
                toolbar.setTitle("Sent Messages");

            } else if (id == R.id.nav_about_us) {
                FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.lyt_frame_layout,
                        new loyola.alex.com.studentcircularalert.AboutUsFragment());
                transaction.commit();
                toolbar.setTitle("About Us");

            } else if (id == R.id.nav_logout) {
                startActivity(new Intent(MainActivity.this,
                        loyola.alex.com.studentcircularalert.LoginActivity.class));
                finish();
            }
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calender:
                System.out.print("click");
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                startActivity(intent);
                break;
            case R.id.action_settings:


                break;
        }
        return true;
    }
}
