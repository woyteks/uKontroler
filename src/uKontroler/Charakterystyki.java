/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uKontroler;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author java
 */
public class Charakterystyki extends javax.swing.JDialog {

  /**
   * Creates new form Charakterystyki
   */
  int dialX = 0;
  int dialY = 0;
  JDialog frame;
  String[] naglowek;
  Object[][] data;
  int odplyw;
  boolean ukryty = true;
  DefaultTableModel dm, dmWz;
  JTable jt;
  JComboBox bx1 = new JComboBox();
  DigTextField jtxField = new DigTextField();
  public Charakterystyki() {
    initComponents();
  }

  public Charakterystyki(JDialog frame, boolean modal, int odplyw) {
    super(frame, modal);
    this.frame = frame;
    this.odplyw = odplyw;
//    frame.setEnabled(!modal);
    initComponents();
    uzupelnij();

    naglData();

    defTable();

    formatuj();

    if (Eeprom.charakt == null || Eeprom.charakt.length == 0) {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      Eeprom.charakt = new Object[l][k];
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          Eeprom.charakt[j][i] = jTable1.getValueAt(j, i);
        }
      }
    } else {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          jTable1.setValueAt(Eeprom.charakt[j][i], j, i);
        }
      }
    }

    jTable1.getTableHeader().setPreferredSize(new Dimension(jTable1.
    getColumnModel().getTotalColumnWidth(), 32));

    dmWz = defTabMod(dmWz);
    jt = new JTable(dmWz);

    final JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem("Uwagi - poka¿/ukryj");
    deleteItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (ukryty) {
          u.nagraj(jTable1, jt);
          int szer = 3 * dialX / 13;
          u.formCol(jTable1, 8, szer);
          dm.setDataVector(data, naglowek);
          rozmiar();
          renderer();
          u.nagraj(jt, jTable1);
          jTable1.repaint();
        } else {
          u.nagraj(jTable1, jt);
          defTable();
          formatuj();
          jTable1.setComponentPopupMenu(popupMenu);
          rozmiar();
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
  }

  private void defTable() {

    dm = defTabMod(dm);
    jTable1 = new JTable(dm);

    renderer();

    jTable1.setColumnSelectionAllowed(true);
    rozmiar();

    jTable1.setDefaultRenderer(String.class, new MultiLineTableCellRenderer());
    jTable1.setEditingColumn(0);
    jScrollPane1.getViewport().add(jTable1, null);
  }

  private void renderer() {
    jTable1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(
    generateBox()));
    jTable1.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(
    txtField()));
    jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(
    generateBox()));
    jTable1.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(
    txtField()));

  }

  private DefaultTableModel defTabMod(DefaultTableModel dm) {
    dm = new DefaultTableModelImpl();
    dm.setDataVector(data, naglowek);
    return dm;
  }

  private void formatuj() {
    u.formCol(jTable1, 0, 30);
    u.formCol(jTable1, 1, 90);
    u.formCol(jTable1, 3, 60);

    u.formCol(jTable1, 8, 0);
  }

  private void rozmiar() {
    for (int i = 4; i < 8; i++) {
      if (!jTable1.getColumnName(i).contains(Integer.toString(odplyw))) {
        u.formCol(jTable1, i, 0);
      } else {
        u.formCol(jTable1, i, 90);
      }
    }
  }

  private void naglData() {
    naglowek = new String[]{"Lp", "Opcja", "Opis",
      "<html><center>Jedn<br/>miary</center></html>",
      "<html><center>Nastawa 1<br/>Wybór</center></html>",
      "<html><center>Nastawa 1<br/>Wartoœæ</center></html>",
      "<html><center>Nastawa 2<br/>Wybór</center></html>",
      "<html><center>Nastawa 2<br/>Wartoœæ</center></html>", "Uwagi"};
    data = new Object[][]{
      {"1", "Typ", "Typ charakterystyki stygniêcia silnika", "1-4",
        "Wl w³asna", Float.POSITIVE_INFINITY, "Wl w³asna", Float.POSITIVE_INFINITY,
        "0-Wl-w³asna, 1 W³asna IEC, 2- XI - extremly inverse IEC,  3-VI very inverse IEC 4-INV inverse IEC, Charakterystyka w³asna wprowadzana tak jak w poprzedniej wersji przekaŸnika INVERTIMU"},
      {"2", "TMS", "wspó³czynnik  charakterystyki IEC", "", "X", 1.70, "X", 1.70,
        "Dzia³a tylko w przypadku wyboru charakterystyki w³asnej IEC opcja 1 w pkt.1"},
      {"3", "tr", "wspó³czynnik  charakterystyki IEC", "", "X", 136.00, "X",
        136.00, "jw."},
      {"4", "k", "wspó³czynnik  charakterystyki IEC", "", "X", 140.00, "X", 140.00,
        "jw."},
      {"5", "c", "wspó³czynnik  charakterystyki IEC", "", "X", 00.00, "X", 00.00,
        "jw."},
      {"6", "alfa", "wspó³czynnik  charakterystyki IEC", "", "X", 1.88, "X", 1.88,
        "jw."}
    };
  }

  private void uzupelnij() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle rect = new Rectangle();
    int przesX = 150;
    int przesY = 100;
    dialX = screenSize.width - 3 * przesX;
    dialY = screenSize.height - 5 * przesY;
    rect.setBounds(przesX, przesY, dialX, dialY);
    this.setBounds(rect);
  }

  private JComboBox generateBox() {
    bx1.removeAllItems();
    bx1.addItem("Wl w³asna");
    bx1.addItem("W³asna IEC");
    bx1.addItem("<html>XI extremly<br/>inverse IEC</html>");
    bx1.addItem("<html>VI very<br/>inverse IEC</html>");
    bx1.addItem("<html>INV<br/>inverse IEC</html>");
    return bx1;
  }

  private JTextField txtField() {
    jtxField.setColumns(10);
    jtxField.setColumnsEdit(10);
    jtxField.setFormat(DigTextField.VAL_DECIMAL4);
    jtxField.setValue(0);
    return jtxField;
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

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
    new Object[][]{
      {},
      {},
      {},
      {},
      {}
    },
    new String[]{}
    ));
    jScrollPane1.setViewportView(jTable1);

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

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
    getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGap(0, 400, Short.MAX_VALUE)
    .addGroup(layout.createParallelGroup(
    javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
    .addGap(12, 12, 12)
    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375,
    Short.MAX_VALUE)
    .addGap(13, 13, 13)))
    );
    layout.setVerticalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGap(0, 279, Short.MAX_VALUE)
    .addGroup(layout.createParallelGroup(
    javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
    .addGap(2, 2, 2)
    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275,
    Short.MAX_VALUE)
    .addGap(2, 2, 2)))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    dispose();
    ladujData();
//    frame.setEnabled(true);
//    frame.requestFocus();
  }//GEN-LAST:event_jMenuItem1ActionPerformed

  private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    dispose();
//    frame.setEnabled(true);
//    frame.requestFocus();
  }//GEN-LAST:event_jMenuItem2ActionPerformed

  private void ladujData() {
    int k = jTable1.getColumnCount();
    int l = jTable1.getRowCount();
    for (int i = 0; i < k; i++) {
      for (int j = 0; j < l; j++) {
        Eeprom.charakt[j][i] = jTable1.getValueAt(j, i);
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

  private class DefaultTableModelImpl extends DefaultTableModel {

    @Override
    public void setValueAt(Object aValue, int row, int column) {
      if (column == 5 || column == 7) {
        Float fl = Float.parseFloat(aValue.toString().isEmpty() ? "0" : aValue.
        toString());
        super.setValueAt(fl, row, column);
      }else if (column == 4 || column == 6) {
        String s = u.extractText(aValue.toString());
        super.setValueAt(s, row, column);
      } else {
        super.setValueAt(aValue, row, column); //To change body of generated methods, choose Tools | Templates.
      }
    }

    public DefaultTableModelImpl() {
    }

    Class[] types = new Class[]{
      java.lang.String.class,
      java.lang.String.class,
      java.lang.String.class,
      java.lang.String.class,
      java.lang.String.class,
      java.lang.Float.class,
      java.lang.String.class,
      java.lang.Float.class,
      java.lang.String.class
    };

    public Class getColumnClass(int columnIndex) {
      return types[columnIndex];
    }

    public boolean isCellEditable(int row, int column) {
      if ((column == 4 || column == 6) && row == 0) {
        return true;
      }
      if ((column == 5 || column == 7) && row > 0) {
        return true;
      }
      return false;
    }

  }

}
