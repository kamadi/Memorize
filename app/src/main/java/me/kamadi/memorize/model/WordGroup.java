package me.kamadi.memorize.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Madiyar on 19.04.2016.
 */

@DatabaseTable(tableName = "word_group")
public class WordGroup {
    @DatabaseField(foreign = true, foreignColumnName = "id")
    private Word word;

    @DatabaseField(foreign = true, foreignColumnName = "id")
    private Group group;

    public WordGroup() {
    }

    public WordGroup(Group group, Word word) {
        this.group = group;
        this.word = word;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
