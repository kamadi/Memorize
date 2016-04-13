package me.kamadi.memorize.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Madiyar on 12.04.2016.
 */

@DatabaseTable
public class Word {
    @DatabaseField(columnName = "id", id = true)
    private long id;

    @DatabaseField
    private String word;

    @DatabaseField
    private String translation;

    @DatabaseField
    private int transcript;

    @DatabaseField
    private int rating;

    @DatabaseField(foreign = true, foreignColumnName = "id")
    private Group group;

    @DatabaseField
    private String language;

    public Word() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public int getTranscript() {
        return transcript;
    }

    public void setTranscript(int transcript) {
        this.transcript = transcript;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
