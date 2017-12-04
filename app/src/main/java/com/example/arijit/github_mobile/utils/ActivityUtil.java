package com.example.arijit.github_mobile.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by arijit on 04/12/17.
 */

public class ActivityUtil {

    public static void replaceFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        Fragment f = fragmentManager.findFragmentById(frameId);
        if (f != null) {
            fragmentManager.beginTransaction().remove(f).commitAllowingStateLoss();
        }

        try {
            fragmentManager.beginTransaction().replace(frameId, fragment).commit();
        } catch (Exception e) {
            fragmentManager.beginTransaction().replace(frameId, fragment).commitAllowingStateLoss();
        }
    }
}
