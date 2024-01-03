package com.example.expeditionsupportapp;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePageFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;


    public HomePageFragmentAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;

    }

    @Override
    public int getCount() {
        return totalTabs;
    }



    public Fragment getItem(int position){

        switch (position)
        {
            case 0:
                Log.d("TAG", "Blogs from adapter");
                BlogsRecyclerViewFragment blogsRecyclerViewFragment = new BlogsRecyclerViewFragment();
                return blogsRecyclerViewFragment;
            case 1:
                Log.d("TAG", "Current Expeditition ");
                CurrentExpeditionFragment currentExpeditionFragment = new CurrentExpeditionFragment();
                return currentExpeditionFragment;
            default:
                return null;

        }
    }
}
