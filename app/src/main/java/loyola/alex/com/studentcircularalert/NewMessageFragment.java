package loyola.alex.com.studentcircularalert;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewMessageFragment extends Fragment {

    private View mNewMessageFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mNewMessageFragment = inflater.inflate(R.layout.fragment_new_message, container, false);
        viewPager = (ViewPager) mNewMessageFragment.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        // viewPager.setAdapter(mViewPagerAdapter);
        tabLayout = (TabLayout) mNewMessageFragment.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return mNewMessageFragment;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new SendToAllFragment(), "All");
        adapter.addFragment(new SendToStaffFragment(), "Staff");
        adapter.addFragment(new SendToStudentFragment(), "Student");
        viewPager.setAdapter(adapter);
    }
}
