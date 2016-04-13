package me.kamadi.memorize.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Madiyar on 12.04.2016.
 */
@DatabaseTable
public class Group {
    @DatabaseField(columnName = "id", id = true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String language;

    public Group() {
    }

    public Group(long id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
