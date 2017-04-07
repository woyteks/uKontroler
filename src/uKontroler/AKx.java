/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uKontroler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import jxl.read.biff.BiffException;

/**
 *
 * @author wojtek
 */
public class AKx extends javax.swing.JDialog {

  /**
   * Creates new form AKx
   */
  JDialog frame;
  int dialX = 0;
  int dialY = 0;
  int colEdit;
  static int colIl;
  int zaczyn = 0;
  static OdczytXLS xls = null;
  DefaultTableModel dm, dmWz;
  String skad;
  List<Integer> rowsKom = new ArrayList<>();
  List<Integer> rowsKom0 = new ArrayList<>();
  String odplyw = "0";
  boolean ukryty = true;
  JTable jt;

  public AKx() {
    initComponents();
  }

  public AKx(JDialog frame, boolean modal, File file, String skad, String odplyw) {
    super(frame, modal);
    this.frame = frame;
    this.skad = skad;
    this.odplyw = odplyw;
//    frame.setEnabled(!modal);
    initComponents();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle rect = new Rectangle();
    int przesX = 15;
    int przesY = 10;
    dialX = screenSize.width - 30 * przesX;
    dialY = screenSize.height - 30 * przesY;
    rect.setBounds(10 * przesX, przesY, dialX, dialY);
    this.setBounds(rect);

    baza(file, skad);
    cellKom();
    if (Eeprom.akxKom == null || Eeprom.akxKom.length == 0) {
      Eeprom.akxKom = new String[rowsKom0.size()][7];
      for (int i = 0; i < rowsKom0.size(); i++) {
        for (int j = 0; j < 7; j++) {
          Eeprom.akxKom[i][j] = "";
        }
      }
    }

    Color[] color = new Color[xls.data.length];
    kolory(color);
    uzupKom();

    dm = defaultMan(dm);

    defTable(color, rect);

    formatuj();

    jTable1.getTableHeader().setPreferredSize(new Dimension(jTable1.
    getColumnModel().getTotalColumnWidth(), 32));

    dmWz = defaultMan(dmWz);
    jt = new JTable(dmWz);

    final JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem("Uwagi - poka¿/ukryj");
    deleteItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (ukryty) {
          u.nagraj(jTable1, jt);
          int szer = 3 * dialX / 13;
          u.formCol(jTable1, 13, szer);
          dm.setDataVector(xls.data, xls.columnNames);
          rozmiar(rect, skad);
          u.nagraj(jt, jTable1);
          jTable1.repaint();
        } else {
          u.nagraj(jTable1, jt);
          defTable(color, rect);
          formatuj();
          jTable1.setComponentPopupMenu(popupMenu);
          rozmiar(rect, skad);
          u.nagraj(jt, jTable1);
          jTable1.getTableHeader().setPreferredSize(new Dimension(jTable1.
          getColumnModel().getTotalColumnWidth(), 32));
          jTable1.repaint();
        }
        ukryty = !ukryty;
      }

    });
    popupMenu.add(deleteItem);
    jTable1.setComponentPopupMenu(popupMenu);

//    jTable1.scrollRectToVisible(new Rectangle(0, 2 * jTable1.getRowHeight(),
//      jTable1.getWidth(), jTable1.getRowHeight()));
//    jScrollPane1.transferFocusBackward();
//    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  private void defTable(Color[] color, Rectangle rect) {

    jTable1 = new JTable(dm) {

      public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }

      public Component prepareRenderer(TableCellRenderer renderer, int row,
                                       int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!c.getBackground().equals(getSelectionBackground())) {
          c.setBackground(color[row]);
        }
        return c;
      }

    };

    rozmiar(rect, skad);

//    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jTable1.setDefaultRenderer(String.class, new MultiLineTableCellRenderer());
//    jScrollPane1.getViewport().add(jTable1);
//    jScrollPane1.setViewportView(jTable1);
    jScrollPane1.getViewport().add(jTable1, null);

//    ((AbstractTableModel)(jTable1.getModel())).fireTableDataChanged();
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTMouseClicked(evt);
      }

    });

    jTable1.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
//        jTKeyClicked(e);
      }

      @Override
      public void keyPressed(KeyEvent e) {
        jTKeyClicked(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        //      jTKeyClicked(e);
      }

    });

  }

  private void formatuj() {
    u.formCol(jTable1, 0, 30);
    u.formCol(jTable1, 1, 150);
    u.formCol(jTable1, 2, 120);
    u.formCol(jTable1, 3, 180);
    u.formCol(jTable1, 5, 60);

    u.formCol(jTable1, 13, 0);
  }

  void cellKom() {
    int j = 0;
    for (int i = 0; i < xls.data.length; i++) {
      Boolean b1 = xls.data[i][3].toString().contains("omunikat");
      Boolean b2 = xls.data[i][3].toString().endsWith("1")
      || xls.data[i][3].toString().endsWith("2") || xls.data[i][3].
      toString().endsWith("3");
      if (b1 && b2 || b1) {
        rowsKom.add(i);
      }
      if (b1 && b2) {
        rowsKom0.add(i);
      }
    }

  }

  private void baza(File file, String skad) {
    if (Eeprom.data == null) {
      zaczyn = 0;
      try {
        xls = new OdczytXLS(file, skad);
      } catch (IOException ex) {
        Logger.getLogger(AKx.class.getName()).log(Level.SEVERE, null, ex);
      } catch (BiffException ex) {
        Logger.getLogger(AKx.class.getName()).log(Level.SEVERE, null, ex);
      }

      colEdit = xls.colPrz;
      colIl = xls.colIl;
      Eeprom.data = xls.data;
    } else {
      zaczyn = 1;
    }
    file.delete();
    for (int j = 0; j <= colIl; j++) {
      if (xls.colNames()[j].matches(skad)) {
        colEdit = j;
        break;
      }
    }

  }

  private void rozmiar(Rectangle rect, String skad) {

    jTable1.getColumnModel().getColumn(0).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(1).setPreferredWidth(2 * dialX / 13);
    jTable1.getColumnModel().getColumn(2).setPreferredWidth(dialX / 13);
    jTable1.getColumnModel().getColumn(3).setPreferredWidth(2 * dialX / 13);
    jTable1.getColumnModel().getColumn(4).setPreferredWidth(2 * dialX / 13);
    jTable1.getColumnModel().getColumn(5).setPreferredWidth(dialX / 13);
    jTable1.getColumnModel().getColumn(6).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(7).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(8).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(9).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(10).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(11).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(12).setPreferredWidth(dialX / 26);
    jTable1.getColumnModel().getColumn(13).setPreferredWidth(3 * dialX / 13);

//    jScrollPane1.setBounds(rect);
    for (int i = 6; i < 13; i++) {
      if (!jTable1.getColumnModel().getColumn(i).getHeaderValue().toString().
      equals(skad)) {
        u.formCol(jTable1, i, 0);
      } else {
        u.formCol(jTable1, i, 30);
      }
    }
//    jScrollPane1.getViewport().add(jTable1, null);
  }

  private void kolory(Color[] color) {
    Color c1 = Color.green;
//    Color c1 = Color.lightGray;
    Color c2 = Color.pink;
    Color col = c1;
    for (int i = 0; i < xls.data.length; i++) {
      if (!xls.data[i][2].toString().equals("") || !xls.data[i][1].toString().
      equals("")) {
        if (col == c1) {
          col = c2;
        } else {
          col = c1;
        }
      }
      color[i] = col;
      if (zaczyn == 0) {
        xls.data[i][6] = Boolean.FALSE;
        xls.data[i][7] = Boolean.FALSE;
        xls.data[i][8] = Boolean.FALSE;
        xls.data[i][9] = Boolean.FALSE;
        xls.data[i][10] = Boolean.FALSE;
        xls.data[i][11] = Boolean.FALSE;
        xls.data[i][12] = Boolean.FALSE;
      }
      Boolean b1 = xls.data[i][3].toString().contains("Odplyw");
      Boolean b2 = xls.data[i][3].toString().endsWith("1");
      Boolean b3 = xls.data[i][3].toString().endsWith("2");
      if (b1 && b2 && odplyw.equals("1") || b1 && b3 && odplyw.equals("2")) {
        xls.data[i][colEdit] = Boolean.TRUE;
      }
    }
  }

  private DefaultTableModel defaultMan(DefaultTableModel dm) {
    dm = new DefaultTableModel() {

      Class[] types = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Boolean.class,
        java.lang.Boolean.class,
        java.lang.Boolean.class,
        java.lang.Boolean.class,
        java.lang.Boolean.class,
        java.lang.Boolean.class,
        java.lang.Boolean.class,
        java.lang.String.class
      };

      public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
      }

      public boolean isCellEditable(int row, int column) {
        if (column == colEdit) {
          Boolean b1 = xls.data[row][3].toString().contains("Odplyw");
          Boolean b2 = xls.data[row][3].toString().endsWith("1");
          Boolean b3 = xls.data[row][3].toString().endsWith("2");
          Boolean b4 = xls.data[row][3].toString().contains("Stycznik");
          if (b1 && b2 || b1 && b3) {
            return false;
          } else if (b4 && odplyw.equals("0")) {
            return false;
          }
          return true;
        } else {
          return false;
        }
      }

    };
    dm.setDataVector(xls.data, xls.columnNames);
    return dm;
  }

  public Object[][] tabela() {
    return xls.data;
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenuItem2 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(),
    javax.swing.BoxLayout.LINE_AXIS));

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
    new Object[][]{
      {null, null, null, null},
      {null, null, null, null},
      {null, null, null, null},
      {null, null, null, null}
    },
    new String[]{
      "Title 1", "Title 2", "Title 3", "Title 4"
    }
    ));
    jScrollPane1.setViewportView(jTable1);

    getContentPane().add(jScrollPane1);

    jMenu1.setText("Nastawy");

    jMenuItem1.setText("Zapisz");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }

    });
    jMenu1.add(jMenuItem1);

    jMenuItem2.setText("Anuluj");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }

    });
    jMenu1.add(jMenuItem2);

    jMenuBar1.add(jMenu1);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
  dispose();
  ladujData();
  ladujAKx();
//  frame.setEnabled(true);
//  frame.requestFocus();
  }//GEN-LAST:event_jMenuItem1ActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
  dispose();
//  frame.setEnabled(true);
//  frame.requestFocus();
  }//GEN-LAST:event_jMenuItem2ActionPerformed

  private void jTKeyClicked(java.awt.event.KeyEvent evt) {
    int row = jTable1.getSelectedRow();
    int col = jTable1.getSelectedColumn();
    int key = evt.getKeyCode();
    if (key == KeyEvent.VK_SPACE && col != 4) {
      czynnosci(row, col);
    }
  }

  private void jTMouseClicked(java.awt.event.MouseEvent evt) {
    int row = jTable1.rowAtPoint(evt.getPoint());
    int col = jTable1.columnAtPoint(evt.getPoint());
    czynnosci(row, col);
  }

  private void czynnosci(int row, int col) {
    if (rowsKom.contains(row)) {
      if (col > 5 && col < 13) {
        Boolean ob = Boolean.FALSE;
        if (rowsKom.contains(row + 1)) {
          jTable1.setValueAt(ob, (row + 1), col);
          if (rowsKom.contains(row + 2)) {
            jTable1.setValueAt(ob, (row + 2), col);
            if (rowsKom.contains(row + 3)) {
              jTable1.setValueAt(ob, (row + 3), col);
            }
          }
        }
        if (rowsKom.contains(row - 1)) {
          jTable1.setValueAt(ob, (row - 1), col);
          if (rowsKom.contains(row - 2)) {
            jTable1.setValueAt(ob, (row - 2), col);
            if (rowsKom.contains(row - 3)) {
              jTable1.setValueAt(ob, (row - 3), col);
            }
          }
        }
      }
      sleep(100);
      for (int i = row - 3; i < row + 4; i++) {
        ((AbstractTableModel) jTable1.getModel()).fireTableCellUpdated(i, col);
      }
      sleep(100);
    }
    if (xls.data[row][3].toString().contains("Stycznik")) {
      for (int i = 6; i < 13; i++) {
        boolean grupa1 = (boolean) xls.data[row + 5][i];
        boolean grupa2 = (boolean) xls.data[row + 6][i];
        if (colEdit == i) {
          continue;
        }
        if (grupa1 && odplyw.contains("1") || grupa2 && odplyw.contains("2")) {
          xls.data[row][i] = Boolean.FALSE;
          jTable1.setValueAt(Boolean.FALSE, row, i);
        }
      }
    }
    if (col == 4 && rowsKom0.contains(row)) {
      int i = 0;
      String gr = "";
      String pgr = "";
      int len = xls.data[row][3].toString().length();
      String nr = xls.data[row][3].toString().substring(len - 1, len);
      len = xls.data[row][1].toString().length();
      if (len == 0) {
        while (true) {
          len = xls.data[row - i][1].toString().length();
          if (len > 0) {
            gr = xls.data[row - i][1].toString().substring(0, len);
            break;
          }
          i++;
        }
      } else {
        gr = xls.data[row - i][1].toString().substring(1, len);
      }
      len = xls.data[row][2].toString().length();
      if (len == 0) {
        i = 0;
        while (true) {
          len = xls.data[row - i][2].toString().length();
          if (len > 0) {
            pgr = xls.data[row - i][2].toString().substring(0, len);
            break;
          }
          i++;
        }
      } else {
        pgr = xls.data[row - i][2].toString().substring(1, len);
      }
      String napis = jTable1.getValueAt(row, 4).toString();
      Komunikat kom = new Komunikat(this, true, "przeka¿nik " + skad, nr, gr,
      pgr, napis, DigTextField.VAL_STRING, 20);
      kom.setVisible(true);
      if (kom.zwrot()) {
        jTable1.setValueAt(kom.napis(), row, col);
      }
    }
  }

  public static void sleep(int milisecond) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MILLISECOND, milisecond);
    Date czasp = c.getTime();
    Date czask;
    while (true) {
      czask = Calendar.getInstance().getTime();
      if (czask.after(czasp)) {
        break;
      }
    }
  }

  private void ladujData() {
    int k = jTable1.getColumnCount();
    int l = jTable1.getRowCount();
    for (int i = 0; i < k; i++) {
      for (int j = 0; j < l; j++) {
        Eeprom.data[j][i] = jTable1.getValueAt(j, i);
      }
    }
  }

  public Object[][] data() {
    return xls.data();
  }

  private void uzupKom() {
    int col = Integer.valueOf(skad.substring(2, 3));
    for (int j = 0; j < rowsKom0.size(); j++) {
      xls.data[rowsKom0.get(j)][4] = Eeprom.akxKom[j][col - 1];
    }
  }

  private void ladujAKx() {
    int col = Integer.valueOf(skad.substring(2, 3));
    int l = rowsKom0.size();
    for (int j = 0; j < l; j++) {
      Eeprom.akxKom[j][col - 1] = jTable1.getValueAt(rowsKom0.get(j), 4).
      toString();
      // petla dla przeniesienia komunikatu do innych przekaznikow
      for (int k = 0; k < 7; k++){
//        Eeprom.akxKom[j][k] = Eeprom.akxKom[j][col - 1];
      }
    }
  }

  /**
   * @param args the command line arguments
   */

// Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable jTable1;
// End of variables declaration//GEN-END:variables

}
