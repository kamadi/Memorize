package me.kamadi.memorize.database.repo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import me.kamadi.memorize.database.DatabaseHelper;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.WordGroup;

/**
 * Created by Madiyar on 19.04.2016.
 */
public class WordGroupRepo {

    private Dao<WordGroup, String> wordGroupDao;

    public WordGroupRepo(DatabaseHelper databaseHelper) throws SQLException {
        wordGroupDao = databaseHelper.getWordGroupDao();
    }

    public boolean create(WordGroup wordGroup) throws SQLException {
        return wordGroupDao.create(wordGroup) == 1;
    }

    public boolean update(WordGroup wordGroup) throws SQLException {
        return wordGroupDao.update(wordGroup) == 1;
    }

    public boolean delete(WordGroup wordGroup) throws SQLException {
        return wordGroupDao.delete(wordGroup) == 1;
    }

    public List<WordGroup> getByGroup(Group group) throws SQLException {
        QueryBuilder<WordGroup, String> qb = wordGroupDao.queryBuilder();
        qb.where().eq("group_id", group.getId());
        return qb.query();
    }


}
