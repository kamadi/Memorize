package me.kamadi.memorize.database;

import android.content.Context;

import java.sql.SQLException;

import me.kamadi.memorize.database.repo.GroupRepo;
import me.kamadi.memorize.database.repo.WordGroupRepo;
import me.kamadi.memorize.database.repo.WordRepo;

/**
 * Created by Madiyar on 12.04.2016.
 */
public class Repo {

    private final DatabaseHelper databaseHelper;
    private WordRepo wordRepo;
    private GroupRepo groupRepo;
    private WordGroupRepo wordGroupRepo;

    public Repo(Context context) throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        wordRepo = new WordRepo(databaseHelper);
        groupRepo = new GroupRepo(databaseHelper);
        wordGroupRepo = new WordGroupRepo(databaseHelper);
    }

    public WordRepo getWordRepo() {
        return wordRepo;
    }

    public GroupRepo getGroupRepo() {
        return groupRepo;
    }

    public WordGroupRepo getWordGroupRepo() {
        return wordGroupRepo;
    }

    public void close(){
        databaseHelper.close();
    }
}
