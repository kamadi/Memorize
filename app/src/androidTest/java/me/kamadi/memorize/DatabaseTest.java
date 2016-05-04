package me.kamadi.memorize;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertTrue;

/**
 * Created by Madiyar on 03.05.2016.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    Repo repo;

    @Before
    public void setUp() throws Exception {
        repo = new Repo(getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        repo.close();
    }

    @Test
    public void testWordEntryCreate() throws SQLException {
        Word word = new Word("test","test","test","example",Language.ENGLISH);
        assertTrue(repo.getWordRepo().create(word));
    }

    @Test(expected=SQLException.class)
    public void testWordEntryDuplicate() throws SQLException {
        Word word = new Word("test","test","test","example",Language.ENGLISH);
        assertTrue(repo.getWordRepo().create(word));
    }
}
