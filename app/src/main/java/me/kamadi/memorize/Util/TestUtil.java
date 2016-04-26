package me.kamadi.memorize.util;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import kz.kamadi.sauran.model.Delivery;
import kz.kamadi.sauran.model.Driver;
import kz.kamadi.sauran.model.Request;
import kz.kamadi.sauran.model.Shop;

/**
 * Created by Madiyar on 16.03.2015.
 */
public class TestUtil {


    private String inputFile;
    private AssetManager assetManager;

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void write(List<Request> requests, Shop shop) throws IOException, WriteException, BiffException {
        copyAssets();

        File sdCard = Environment.getExternalStorageDirectory();

        File dir = new File(sdCard.getAbsolutePath() + "/Сауран/Заявки/" + DateUtil.getPath());

        dir.mkdirs();
        File template = new File(sdCard.getAbsolutePath() + "/Сауран/Шаблоны", "template.xls");

        File file = new File(dir, inputFile);


        Workbook workbook = Workbook.getWorkbook(template);
        WritableWorkbook copy = Workbook.createWorkbook(file, workbook);
        WritableSheet sheet = copy.getSheet(0);

        Label label = new Label(0, 3, "Покупатель:  " + shop.getName());
        sheet.addCell(label);
        label = new Label(5, 3, DateUtil.getDate());
        sheet.addCell(label);
        label = new Label(5, 4, DateUtil.getCurrentTime());
        sheet.addCell(label);

        int i = 1, j = 7;
        int common = 0;
        for (Request request : requests) {
            sheet.insertRow(j);
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 0, j, String.valueOf(i));
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 1, j, request.getProduct().getName());
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 2, j, request.getProduct().getWeight());
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 3, j, request.getAmount() + "");
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 4, j, request.getProduct().getPrice() + "");
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 5, j, request.getPayment() + "");
            common += request.getPayment();
            i++;
            j++;
        }
        WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 12);
        cellFont.setBoldStyle(WritableFont.BOLD);

        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        Label total = new Label(5, 7 + i, String.valueOf(common) + " тг", cellFormat);
        sheet.addCell(total);
        double percentValue = common * 12 / 100;
        Label percent = new Label(5, 8 + i, String.valueOf(percentValue) + " тг", cellFormat);
        sheet.addCell(percent);

        Label all = new Label(0, 9 + i, "Всего наименований " + requests.size() + ", на сумму " + common, getCellFormat(UnderlineStyle.SINGLE));
        sheet.addCell(all);
        Label sum = new Label(0, 10 + i, "Итого сумма: " + Money.digits2text((double) common), cellFormat);
        sheet.addCell(sum);
        boolean isFirst = true;

        for (Request request : requests) {

            if (request.getExchange() > 0) {
                if (isFirst) {
                    label = new Label(0, 15 + i, "Обмен", getCellFormat(UnderlineStyle.SINGLE));
                    sheet.addCell(label);
                    isFirst = false;
                    i++;
                }
                Label exchange = new Label(0, 15 + i, request.getProduct().getName() + ":" + request.getExchange() + " " + request.getProduct().getWeight());
                sheet.addCell(exchange);
                i++;
            }
        }
        copy.write();
        copy.close();
        workbook.close();
    }

    public void createReport(ArrayList<Request> requests) throws IOException, WriteException, BiffException {
        copyAssets();

        File sdCard = Environment.getExternalStorageDirectory();

        File dir = new File(sdCard.getAbsolutePath() + "/Сауран/Отчеты/" + DateUtil.getPath());

        dir.mkdirs();
        File template = new File(sdCard.getAbsolutePath() + "/Сауран/Шаблоны", "report.xls");
        File file = new File(dir, inputFile);


        Workbook workbook = Workbook.getWorkbook(template);
        WritableWorkbook copy = Workbook.createWorkbook(file, workbook);
        WritableSheet sheet = copy.getSheet(0);
        Label label = new Label(3, 1, DateUtil.getDate());
        sheet.addCell(label);
        label = new Label(3, 2, DateUtil.getCurrentTime());
        sheet.addCell(label);
        int i = 1, j = 5;
        for (Request request : requests) {
            sheet.insertRow(j);
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 0, j, String.valueOf(i));
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 1, j, request.getProduct().getName());
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 2, j, "" + request.getProduct().getWeight());
            int temp = request.getAmount() + request.getExchange();
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 3, j, temp + "");
            i++;
            j++;
        }
        copy.write();
        copy.close();
        workbook.close();
    }

    public void createDriverReport(List<Delivery> deliveries, List<Integer> payments, Driver driver) throws IOException, WriteException, BiffException {
        copyAssets();

        File sdCard = Environment.getExternalStorageDirectory();

        File dir = new File(sdCard.getAbsolutePath() + "/Сауран/Водители/" + driver.getName()
                + "/" + DateUtil.getPath());

        dir.mkdirs();
        File template = new File(sdCard.getAbsolutePath() + "/Сауран/Шаблоны", "driver.xls");
        File file = new File(dir, inputFile);


        Workbook workbook = Workbook.getWorkbook(template);
        WritableWorkbook copy = Workbook.createWorkbook(file, workbook);
        WritableSheet sheet = copy.getSheet(0);
        Label label = new Label(1, 2, "Контрагент:" + driver.getName());
        sheet.addCell(label);
        label = new Label(1, 3, "Дата:" + DateUtil.getDate());
        sheet.addCell(label);
        int i = 1, j = 7;
        Integer total = 0;
        for (int k = 0; i < deliveries.size(); k++) {
            Delivery delivery = deliveries.get(k);
            Integer payment = payments.get(k);
            total += payment;
            sheet.insertRow(j);
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 1, j, delivery.getShop().getName());
            addCell(sheet, Border.ALL, BorderLineStyle.THIN, 2, j, payment + "");
            i++;
            j++;
        }
        label = new Label(1, j, "Итого:");
        sheet.addCell(label);
        label = new Label(2, j, total + " тг");
        sheet.addCell(label);
        copy.write();
        copy.close();
        workbook.close();
    }

    private void addCell(WritableSheet sheet,
                         Border border,
                         BorderLineStyle borderLineStyle,
                         int col, int row, String desc) throws WriteException {

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setBorder(border, borderLineStyle);
        Label label = new Label(col, row, desc, cellFormat);
        sheet.addCell(label);
    }

    private void copyAssets() {
        String[] files = {"template.xls", "report.xls", "driver.xls"};
        InputStream in = null;
        OutputStream out = null;
        for (String filename : files) {
            try {
                in = assetManager.open(filename);
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/Сауран/Шаблоны");
                dir.mkdirs();
                File outFile = new File(dir, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

    }

    private WritableCellFormat getCellFormat(
            UnderlineStyle underlineStyle) throws WriteException {

        WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 12);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFont.setUnderlineStyle(underlineStyle);
        return cellFormat;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


}
