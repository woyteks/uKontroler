package uKontroler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.Workbook;

public class OdczytXLS {

  File file;
  InputStream is;
  int colPrz;
  int colIl;
  int rowIl = 0;
  String[] columnNames;
  Object[][] data;
  public OdczytXLS(File file, String skad) throws IOException, BiffException {
    this.file = file;
    Workbook skoroszyt = Workbook.getWorkbook(file);
    Sheet arkusz = skoroszyt.getSheet(0);

    Cell komorka;

    for (int j = 0; j < arkusz.getColumns(); j++) {
      komorka = arkusz.getCell(j, 0);
      if (komorka.getContents().matches(skad)) {
        colPrz = j;
      }
      if (komorka.getContents().matches("Uwagi")) {
        colIl = j;
        break;
      }
    }
    for (int j = 0; j < arkusz.getRows(); j++) {
      komorka = arkusz.getCell(3, j);
      if (komorka.getContents().contains("omunikat")) {
        rowIl++;
      }
    }

    columnNames = new String[colIl + 1];
    for (int j = 0; j <= colIl; j++) {
      columnNames[j] = arkusz.getCell(j, 0).getContents();
      if (columnNames[j].contains("/")) {
        columnNames[j] = "<html><center>" + columnNames[j].replace("/", "<br/>")
        + "</center></html>";
      }
    }
    boolean dodaj = false;
    int l = 0;
    int zapX = 0;
    int zapY = 0;
    data = new Object[arkusz.getRows() + 3 * rowIl - 1][colIl + 1];
    for (int j = 1; j < arkusz.getRows(); j++) {
      for (int i = 0; i <= colIl; i++) {
        data[j - 1 + l][i] = arkusz.getCell(i, j).getContents();
        if (i == 3 && data[j - 1 + l][3].toString().contains("omunikat")) {
          data[j - 1 + l][i] = "Komunikat";
          dodaj = true;
          if (zapX > 0) {
            data[zapY][zapX + 2] = "";
            data[zapY][zapX + 10] = "";
          }
          zapX = i;
          zapY = j - 1 + l;
        }
      }
      if (dodaj) {
        dodaj = false;
        for (int k = 0; k < 3; k++) {
          l++;
          data[j + l - 1] = data[j + l - k - 2].clone();
          data[j + l - 1][3] = data[j + l - k - 2][3].toString() + (k + 1);
        }
        data[zapY][zapX] = "Brak komunikatu/w³asny";
      }
    }
    data[zapY][zapX + 2] = "";
    int k = 0;
    for (int j = 0; j < data.length; j++) {
      data[j][0] = ++k;
    }
    skoroszyt.close();
  }

  public OdczytXLS(InputStream is, String skad) throws IOException,
  BiffException {
    this.is = is;
    Workbook skoroszyt = Workbook.getWorkbook(is);
    Sheet arkusz = skoroszyt.getSheet(0);

    Cell komorka;

    for (int j = 0; j < arkusz.getColumns() - 1; j++) {
      komorka = arkusz.getCell(j, 0);
      if (komorka.getContents().matches(skad)) {
        colPrz = j;
      }
      if (komorka.getContents().matches("Uwagi")) {
        colIl = j;
        break;
      }
    }
    columnNames = new String[colIl + 1];
    for (int j = 0; j <= colIl; j++) {
      columnNames[j] = arkusz.getCell(j, 0).getContents();
    }
    data = new Object[arkusz.getRows()][colIl + 1];
    for (int i = 0; i <= colIl; i++) {
      for (int j = 1; j < arkusz.getRows(); j++) {
        data[j - 1][i] = arkusz.getCell(i, j).getContents();
      }
    }
    int k = 0;
    for (int j = 0; j < arkusz.getRows(); j++) {
      data[j][0] = ++k;
    }
    skoroszyt.close();
  }

  public String[] colNames() {
    return columnNames;
  }

  public Object[][] data() {
    return data;
  }

}
