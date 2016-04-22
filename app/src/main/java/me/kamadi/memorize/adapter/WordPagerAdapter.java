package me.kamadi.memorize.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import me.kamadi.memorize.fragment.WordFullInfoFragment;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 22.04.2016.
 */
public class WordPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private ArrayList<Word> words;

    public WordPagerAdapter(Context context, FragmentManager fragmentManager, ArrayList<Word> words) {
        super(fragmentManager);
        this.context = context;
        this.words = words;
    }

    @Override
    public Fragment getItem(int position) {
        return WordFullInfoFragment.newInstance(words.get(position));
    }

    @Override
    public int getCount() {
        return words.size();
    }
}
