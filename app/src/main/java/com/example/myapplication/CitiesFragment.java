package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.myapplication.DegreesFragment.PARCEL;

import java.util.zip.Inflater;

public class CitiesFragment extends Fragment {

    boolean isExist;
    Parcel currentParcel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExist = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null){
            currentParcel = (Parcel)savedInstanceState.getSerializable("CurrentCity");
        } else {
            currentParcel = new Parcel(0, getResources().getStringArray(R.id.cities)[0]);
        }

        if (isExist){
            showDegrees(currentParcel);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("CurrentCity", currentParcel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view){
        LinearLayout layoutView = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.cities);

        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];

            View item = layoutInflater.inflate(R.layout.item, layoutView, false);
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize(30);
            layoutView.addView(tv);

            final int fi = 0;
            tv.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.class);
                    builder.setTitle("")
                            .setMessage("")
                            .setCancelable(false)
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(CitiesFragment.this, "Ok", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .create()
                            .show();

                    currentParcel = new Parcel(fi, getResources().getStringArray(R.id.cities)[fi], getResources().getIntArray(R.id.degreesForWeek));
                    showDegrees(currentParcel);
                }
            });

        }
    }

    private void showDegrees(Parcel parcel){
        if (isExist){
            DegreesFragment degrees = (DegreesFragment)getFragmentManager().findFragmentById(R.id.degrees);
            if (degrees == null || degrees.getParcel().getDegrees() != parcel.getDegrees()){
                degrees = DegreesFragment.create(parcel);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.degrees, degrees);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), MainActivity2.class);
            intent.putExtra(PARCEL, parcel);
            startActivity(intent);
            }
        }
    }
