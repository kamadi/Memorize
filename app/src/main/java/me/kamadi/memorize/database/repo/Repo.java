package me.kamadi.memorize.database.repo;

import android.content.Context;

import java.sql.SQLException;

import me.kamadi.memorize.database.main.DatabaseHelper;

/**
 * Created by Madiyar on 12.04.2016.
 */
public class Repo {

    private WordRepo wordRepo;
    private GroupRepo groupRepo;

    public Repo(Context context) throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        wordRepo = new WordRepo(databaseHelper);
        groupRepo = new GroupRepo(databaseHelper);
    }

    public WordRepo getWordRepo() {
        return wordRepo;
    }

    public GroupRepo getGroupRepo() {
        return groupRepo;
    }
}
