package loyola.alex.com.studentcircularalert;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SendToAllFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText detailedMessage;
    private Button send;
    private String TAG = SendToAllFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_to_all_message, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        detailedMessage = (EditText) view.findViewById(R.id.msgbox);
        send = (Button) view.findViewById(R.id.send);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = detailedMessage.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Log.i(TAG, "message1" + message);
                System.out.println("message" + message);
                System.out.println("currentTime" + currentTime);
                System.out.println("time" + currentDateTimeString);
                DateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy");
                dateFormatter.setLenient(false);
                Date today = new Date();
                String s = dateFormatter.format(today);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String time = dateFormat.format(new Date());


                String key = myRef.push().getKey();
                Message messageDetails = new Message();

                messageDetails.setMessage(message);
                messageDetails.setDate(s);
                messageDetails.setTime(time);
                messageDetails.setActivity("Send All");

                myRef.child(key).setValue(messageDetails);
                detailedMessage.setText("");


            }
        });
        return view;
    }
}
