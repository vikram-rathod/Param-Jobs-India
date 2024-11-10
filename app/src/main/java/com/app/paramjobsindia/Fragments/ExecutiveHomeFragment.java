package com.app.paramjobsindia.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.paramjobsindia.ExecutiveActivities.CmpCallListActivity;
import com.app.paramjobsindia.ExecutiveActivities.GetEqActivity;
import com.app.paramjobsindia.ExecutiveActivities.PendingListEqActivity;
import com.app.paramjobsindia.ExecutiveActivities.SortListEqActivity;
import com.app.paramjobsindia.ExecutiveActivities.TodaysCallListActivity;
import com.app.paramjobsindia.ExecutiveActivities.TotalCAllReportActivity;
import com.app.paramjobsindia.databinding.ExecutiveHomeFragmentBinding;

public class ExecutiveHomeFragment extends Fragment {

    ExecutiveHomeFragmentBinding executiveHomeFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        executiveHomeFragmentBinding=ExecutiveHomeFragmentBinding.inflate(inflater,container,false);

        executiveHomeFragmentBinding.cardaddeq.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), GetEqActivity.class));
        });

        executiveHomeFragmentBinding.cardsorteqcall.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), SortListEqActivity.class));
        });

        executiveHomeFragmentBinding.cardpendlistfl.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), PendingListEqActivity.class));
        });

        executiveHomeFragmentBinding.cardtcallist.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), TodaysCallListActivity.class));
        });

        executiveHomeFragmentBinding.cardtcompcalls.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), CmpCallListActivity.class));
        });
        executiveHomeFragmentBinding.cardtcallreport.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), TotalCAllReportActivity.class));
        });
        return executiveHomeFragmentBinding.getRoot();
    }
}
