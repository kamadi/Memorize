package me.kamadi.memorize.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.kamadi.memorize.R;
import me.kamadi.memorize.fragment.GroupFragment;
import me.kamadi.memorize.fragment.WordFragment;

/**
 * Created by Madiyar on 13.04.2016.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private final String[] tabs;
    private GroupFragment groupFragment;
    private WordFragment wordFragment;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        tabs = context.getResources().getStringArray(R.array.main_tabs);
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            groupFragment = new GroupFragment();
            return groupFragment;
        } else {
            wordFragment = new WordFragment();
            return wordFragment;
        }

    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    public GroupFragment getGroupFragment() {
        return groupFragment;
    }

    public WordFragment getWordFragment() {
        return wordFragment;
    }
}
