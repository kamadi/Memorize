package me.kamadi.memorize.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 26.04.2016.
 */
public class ExcelUtil {
    private static final String LOG_TAG = ExcelUtil.class.getSimpleName();
    private AssetManager assetManager;
    private Repo repo;
    public ExcelUtil(Context context) throws SQLException {
        this.assetManager = context.getAssets();
        this.repo = new Repo(context);
    }

    public void importData(String inputFile) throws IOException, BiffException, SQLException {
        File file = new File(Environment.getExternalStorageDirectory() + inputFile);
        Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        Word word;
        for (int i = 1; i < sheet.getRows(); i++) {
            word = new Word();
            word.setLanguage(Language.ENGLISH);
            Cell cell = sheet.getCell(0, i);
            if (!cell.getType().equals(CellType.EMPTY)) {
                Log.e(LOG_TAG, cell.getContents());
                word.setWord(cell.getContents());
            }

            cell = sheet.getCell(1, i);
            if (!cell.getType().equals(CellType.EMPTY)) {
                Log.e(LOG_TAG, cell.getContents());
                word.setTranslation(cell.getContents());
            }
            try {
                cell = sheet.getCell(2, i);
                if (!cell.getType().equals(CellType.EMPTY)) {
                    Log.e(LOG_TAG, cell.getContents());
                    word.setTranscript(cell.getContents());
                }
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
            repo.getWordRepo().create(word);
        }
    }
}
