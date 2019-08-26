package com.path_studio.arphatapp.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.path_studio.arphatapp.R;

import java.util.Date;

public class Booking_04_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;
    private Button mSet1, mSet2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_04_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNext = (TextView) view.findViewById(R.id.next_booking_05);
        mNext.setOnClickListener(this);

        mPrev = (TextView) view.findViewById(R.id.prev_booking_03);
        mPrev.setOnClickListener(this);

        mSet1 = view.findViewById(R.id.set_takeoff_datetime);
        mSet1.setOnClickListener(this);

        mSet2 = view.findViewById(R.id.set_Return_datetime);
        mSet2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.next_booking_05:
                ft.replace(R.id.fragment_container_booking, new Booking_05_Fragment(), "Next to Page 5 Booking");
                ft.commit();
                break;
            case R.id.prev_booking_03:
                ft.replace(R.id.fragment_container_booking, new Booking_03_Fragment(), "Back to Page 3 Booking");
                ft.commit();
                break;
            case R.id.set_takeoff_datetime:
                //calendar dan time picker
                new SingleDateAndTimePickerDialog.Builder(getActivity())
                        //.bottomSheet()
                        //.curved()
                        //.minutesStep(15)
                        //.displayHours(false)
                        //.displayMinutes(false)
                        //.todayText("aujourd'hui")
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                //retrieve the SingleDateAndTimePicker
                            }
                        })

                        .title("Take Off Date & Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {

                            }
                        }).display();
                break;
            case R.id.set_Return_datetime:
                //calendar dan time picker
                new SingleDateAndTimePickerDialog.Builder(getActivity())
                        //.bottomSheet()
                        //.curved()
                        //.minutesStep(15)
                        //.displayHours(false)
                        //.displayMinutes(false)
                        //.todayText("aujourd'hui")
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                //retrieve the SingleDateAndTimePicker
                            }
                        })

                        .title("Return Date & Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {

                            }
                        }).display();
                break;
        }
    }

}