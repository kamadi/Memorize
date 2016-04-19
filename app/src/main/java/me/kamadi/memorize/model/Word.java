package me.kamadi.memorize.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Madiyar on 12.04.2016.
 */

@DatabaseTable
public class Word {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String word;

    @DatabaseField
    private String translation;

    @DatabaseField
    private String transcript;

    @DatabaseField
    private int rating;


    @DatabaseField(columnName = "language_code")
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


    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Word)
        {
            sameSame = this.id == ((Word) object).id;
        }

        return sameSame;
    }
}
