package com.example.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DegreesFragment extends Fragment {

    public static DegreesFragment create(Parcel parcel){
        DegreesFragment fragment = new DegreesFragment();

        Bundle args = new Bundle();
        args.putSerializable("index", parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel(){
        Parcel parcel = (Parcel) getArguments().getSerializable("index");
        return parcel;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.degrees_fragment, container, false);
        TextView cityName = layout.findViewById(R.id.textView);
        TextView degrees = layout.findViewById(R.id.textView1);
        Parcel parcel = getParcel();
        cityName.setText(parcel.getCityName());
        degrees.setText(parcel.getDegrees());
        return layout;

    }

}