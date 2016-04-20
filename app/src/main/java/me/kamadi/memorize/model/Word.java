package me.kamadi.memorize.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Madiyar on 12.04.2016.
 */

@DatabaseTable
public class Word implements Parcelable {
    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
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

    private boolean isCorrect;

    public Word() {
    }

    protected Word(Parcel in) {
        id = in.readLong();
        word = in.readString();
        translation = in.readString();
        transcript = in.readString();
        rating = in.readInt();
        language = in.readString();
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

        if (object != null && object instanceof Word) {
            sameSame = this.id == ((Word) object).id;
        }

        return sameSame;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(word);
        dest.writeString(translation);
        dest.writeString(transcript);
        dest.writeInt(rating);
        dest.writeString(language);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
