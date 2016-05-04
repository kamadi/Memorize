package me.kamadi.memorize.event.word;

import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 04.05.2016.
 */
public class WordUpdateEvent {
    Word word;

    public WordUpdateEvent(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }
}
