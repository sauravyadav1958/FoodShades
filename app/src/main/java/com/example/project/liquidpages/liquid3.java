package com.example.project.liquidpages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.project.Activity.IntroActivity;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class liquid3 extends Fragment {

    FloatingActionButton floatingbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.liquidpage3, container, false);

        floatingbtn = root.findViewById(R.id.floatingbtn);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IntroActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
