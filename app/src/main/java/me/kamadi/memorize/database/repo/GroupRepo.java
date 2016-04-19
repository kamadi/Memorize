package me.kamadi.memorize.database.repo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import me.kamadi.memorize.database.DatabaseHelper;
import me.kamadi.memorize.model.Group;

/**
 * Created by Madiyar on 12.04.2016.
 */
public class GroupRepo {
    private Dao<Group, String> groupDao;

    public GroupRepo(DatabaseHelper databaseHelper) throws SQLException {
        groupDao = databaseHelper.getGroupDao();
    }

    public boolean create(Group group) throws SQLException {
        return groupDao.create(group) == 1;
    }

    public boolean update(Group group) throws SQLException {
        return groupDao.update(group) == 1;
    }

    public boolean delete(Group group) throws SQLException {
        return groupDao.delete(group) == 1;
    }

    public List<Group> getByLanguage(String language) throws SQLException {
        QueryBuilder<Group, String> qb = groupDao.queryBuilder();
        qb.where().eq("language_code", language);
        return qb.query();
    }

}
