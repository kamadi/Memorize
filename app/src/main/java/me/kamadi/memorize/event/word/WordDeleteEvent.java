package me.kamadi.memorize.event.word;

import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 04.05.2016.
 */
public class WordDeleteEvent {

    Word word;

    public WordDeleteEvent(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }
}
