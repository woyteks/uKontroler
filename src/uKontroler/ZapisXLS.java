package uKontroler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import jxl.write.*;
import jxl.write.Number;
import jxl.format.Colour;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ZapisXLS {

  /**
   * @param args the command line arguments
   */
  File file;
  public ZapisXLS(File file) throws IOException, WriteException {
    this.file = file;
    WritableWorkbook skoroszyt = Workbook.createWorkbook(file);
    WritableSheet arkusz = skoroszyt.createSheet("Arkusz", 0);
    WorkbookSettings ustawienia = new WorkbookSettings();
    ustawienia.setLocale(new Locale("pl", "PL"));

    WritableFont font = new WritableFont(WritableFont.TAHOMA, 12);
    WritableCellFormat tahoma = new WritableCellFormat(font);
    tahoma.setBackground(Colour.LIGHT_BLUE);

    Label tekst = new Label(1, 0, "Liczby:", tahoma);
    arkusz.addCell(tekst);

    WritableCellFormat format = new WritableCellFormat(NumberFormats.FLOAT);
    Number number = new Number(1, 1, 5.1, format);
    arkusz.addCell(number);
    number = new Number(1, 2, 2.6754, format);
    arkusz.addCell(number);
    number = new Number(1, 3, 6.75, format);
    arkusz.addCell(number);

    Formula formula = new Formula(1, 4, "SUM(B2:B4)", tahoma);
    arkusz.addCell(formula);

    WritableCellFormat formatDaty = new WritableCellFormat(new DateFormat(
    "dd-MM-yyyy"));
    DateTime data = new DateTime(1, 6, Calendar.getInstance().getTime(),
    formatDaty);
    arkusz.addCell(data);

    skoroszyt.write();
    skoroszyt.close();
    System.out.println("Dokument zosta³‚ wygenerowany.");
  }

}
