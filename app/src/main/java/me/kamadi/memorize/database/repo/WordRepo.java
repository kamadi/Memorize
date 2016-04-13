package me.kamadi.memorize.database.repo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import me.kamadi.memorize.database.main.DatabaseHelper;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 12.04.2016.
 */
public class WordRepo {
    private Dao<Word, String> wordDao;

    public WordRepo(DatabaseHelper databaseHelper) throws SQLException {
        wordDao = databaseHelper.getWordDao();
    }

    public boolean create(Word word) throws SQLException {
        return wordDao.create(word) == 1;
    }

    public boolean update(Word word) throws SQLException {
        return wordDao.update(word) == 1;
    }

    public boolean delete(Word word) throws SQLException {
        return wordDao.delete(word) == 1;
    }

    public List<Word> getByGroup(Group group) throws SQLException {
        QueryBuilder<Word, String> qb = wordDao.queryBuilder();
        qb.where().eq("group", group.getId());
        return qb.query();
    }

    public List<Word> getByLanguage(String language) throws SQLException {
        QueryBuilder<Word, String> qb = wordDao.queryBuilder();
        qb.where().eq("language", language);
        return qb.query();
    }

}
