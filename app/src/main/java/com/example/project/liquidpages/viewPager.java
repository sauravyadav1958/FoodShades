package com.example.project.liquidpages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPager extends FragmentPagerAdapter {

    public viewPager(@NonNull FragmentManager fm, int behaviour) {
        super(fm, behaviour);
    }

    @NonNull

    @Override

    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new liquid1();
            case 1:
                return new liquid2();
            case 2:
                return new liquid3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

