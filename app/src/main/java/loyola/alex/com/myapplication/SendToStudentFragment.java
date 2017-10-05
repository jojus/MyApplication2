package loyola.alex.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class SendToStudentFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_to_student_message, container, false);
        String[] array = {"BSC", "BCA", "MCA", "MSC", "B.com", "BBA", "BA"};
        MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) view.findViewById(
                R.id.spinner);
        multiSelectionSpinner.setItems(array);
        //multiSelectionSpinner.setSelection(new int[]{2, 6});
        multiSelectionSpinner.setListener(this);
        return view;

    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(getContext(), strings.toString(), Toast.LENGTH_LONG).show();
    }
}
