package com.messieyawo.unityassembly.Fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.messieyawo.unityassembly.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetingsFragment extends Fragment {

    public SetingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setings, container, false);
    }

}
