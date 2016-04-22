package me.kamadi.memorize.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.adapter.WordPagerAdapter;
import me.kamadi.memorize.model.Word;

public class WordFullInfoActivity extends AppCompatActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private WordPagerAdapter wordPagerAdapter;
    private ArrayList<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_full_info);
        ButterKnife.bind(this);
        words = getIntent().getParcelableArrayListExtra("words");
        wordPagerAdapter = new WordPagerAdapter(this, getSupportFragmentManager(), words);
        viewPager.setAdapter(wordPagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("currentItem", 0));
    }
}
