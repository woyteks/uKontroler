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
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author java
 */
public class Rx extends javax.swing.JDialog {

  /**
   * Creates new form Rx
   */
  int dialX = 0;
  int dialY = 0;
  JDialog frame;
  String[] naglowek;
  Object[][] data;
  int ind;
  int odplyw;
  boolean ukryty = true;
  DefaultTableModel dm, dmWz;
  JTable jt;
  Color color = new Color(255, 255, 255);
  public static JComboBox bx1 = new JComboBox();
  DigTextField jtxField = new DigTextField();
  public Rx() {
    initComponents();
  }

  public Rx(JDialog frame, boolean modal, int ind, int odplyw) {
    super(frame, modal);
    this.frame = frame;
    this.ind = ind;
    this.odplyw = odplyw;
//    setModal(modal);
//    frame.setEnabled(!modal);
    initComponents();
    bx1 = generateBox();

    uzupelnij();
    CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();

    naglData();

    defTable(checkBoxRenderer);

    formatuj();

    if (Eeprom.rx == null || Eeprom.rx.length == 0) {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      Eeprom.rx = new Object[l][k];
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          Eeprom.rx[j][i] = jTable1.getValueAt(j, i);
        }
      }
    } else {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          jTable1.setValueAt(Eeprom.rx[j][i], j, i);
        }
      }
    }

    jTable1.getTableHeader().setPreferredSize(new Dimension(jTable1.
    getColumnModel().getTotalColumnWidth(), 32));

    dmWz = defTabMod(dmWz);
    jt = new JTable(dmWz);

    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTMouseClicked(evt);
      }

    });

    final JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem("Uwagi - poka¿/ukryj");
    deleteItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (ukryty) {
          u.nagraj(jTable1, jt);
          int szer = 3 * dialX / 13;
          u.formCol(jTable1, 17, szer);
          dm.setDataVector(data, naglowek);
          rendererCol(checkBoxRenderer);
          rozmiar();
          u.nagraj(jt, jTable1);
          jTable1.repaint();
        } else {
          u.nagraj(jTable1, jt);
          defTable(checkBoxRenderer);
          formatuj();
          jTable1.setComponentPopupMenu(popupMenu);
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

  private void defTable(CheckBoxRenderer checkBoxRenderer) {
    dm = defTabMod(dm);

    jTable1 = new JTable(dm);

    rendererCol(checkBoxRenderer);

    jTable1.setColumnSelectionAllowed(true);
    rozmiar();

    renderer();

    jTable1.setEditingColumn(0);
    jScrollPane1.getViewport().add(jTable1, null);

    /*    
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTMouseClicked(evt);
      }

    });
     */
  }

  private void rendererCol(CheckBoxRenderer checkBoxRenderer) {
    jTable1.getColumnModel().getColumn(5).setCellRenderer(checkBoxRenderer);
    jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(bx1));
    jTable1.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(
    txtField()));
    jTable1.getColumnModel().getColumn(8).setCellRenderer(checkBoxRenderer);
    jTable1.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(bx1));
    jTable1.getColumnModel().getColumn(10).setCellEditor(new DefaultCellEditor(
    txtField()));
    jTable1.getColumnModel().getColumn(11).setCellRenderer(checkBoxRenderer);
    jTable1.getColumnModel().getColumn(12).setCellEditor(new DefaultCellEditor(bx1));
    jTable1.getColumnModel().getColumn(13).setCellEditor(new DefaultCellEditor(
    txtField()));
    jTable1.getColumnModel().getColumn(14).setCellRenderer(checkBoxRenderer);
    jTable1.getColumnModel().getColumn(15).setCellEditor(new DefaultCellEditor(bx1));
    jTable1.getColumnModel().getColumn(16).setCellEditor(new DefaultCellEditor(
    txtField()));

  }

  private void renderer() {
    jTable1.setDefaultRenderer(String.class, new MultiLineTableCellRenderer() {

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected,
                                                     boolean hasFocus, int row,
                                                     int column) {
        return super.getTableCellRendererComponent(table, value, isSelected,
        hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
      }

    });

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

    u.formCol(jTable1, 17, 0);
  }

  private void rozmiar() {
    for (int i = 5; i < 17; i++) {
      if (!jTable1.getColumnName(i).contains(Integer.toString(ind))) {
        u.formCol(jTable1, i, 0);
      } else {
        u.formCol(jTable1, i, 90);
      }
    }
  }

  private void naglData() {
    naglowek = new String[]{"Lp",
      "<html><center>Nastawa<br/>symbol</center></html>", "Nastawa opis",
      "<html><center>Jedn<br/>zakres</center><html>", "Komunikat",
      "<html><center>R1<br/>T/N</center><html>",
      "<html><center>R1<br/>Wybór</center><html>",
      "<html><center>R1<br/>Wartoœæ</center><html>",
      "<html><center>R2<br/>T/N</center><html>",
      "<html><center>R2<br/>Wybór</center><html>",
      "<html><center>R2<br/>Wartoœæ</center><html>",
      "<html><center>R3<br/>T/N</center><html>",
      "<html><center>R3<br/>Wybór</center><html>",
      "<html><center>R3<br/>Wartoœæ</center><html>",
      "<html><center>R4<br/>T/N</center><html>",
      "<html><center>R4<br/>Wybór</center><html>",
      "<html><center>R4<br/>Wartoœæ</center><html>",
      "Uwagi"};
    data = new Object[][]{
      {"1", "C;B;C/B;IA", "Rodzaj zabezpieczenia rezystancyjnego", "n=0-4", "X",
        Boolean.FALSE, "nieaktywne", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "nieaktywne", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "nieaktywne", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "nieaktywne", Float.POSITIVE_INFINITY,
        "0- nieaktywne, 1-centralne, 2-blokuj¹ce, 3-centralno/blokuj¹ce, 4 - Inne aktywne"},
      {"2", "Ch-ka odwr.", "Charakterystyka odwrotna", "n=0-1", "X",
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        "0 - wy³acza po przekroczeniu rezystancji granicznej, 1 - za³acza po przekroczeniu rezystancji granicznej"},
      {"3", "Odp³yw " + odplyw, "Zabezpieczenie dzia³a na przekaŸniki odp³ywu "
        + odplyw, "n=0-1", "X",
        Boolean.TRUE, "X", Float.POSITIVE_INFINITY,
        Boolean.TRUE, "X", Float.POSITIVE_INFINITY,
        Boolean.TRUE, "X", Float.POSITIVE_INFINITY,
        Boolean.TRUE, "X", Float.POSITIVE_INFINITY,
        ""},
      {"4", "Rx1za³.", "Rezystancja za³aczenia zabezpieczenia Rx1 (Rx=R11-R41)",
        "[kohm]", "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        "gdy Rx jest zabezpieczeniem pojedynczym(centralnym, blokuj¹cym, innym) lub gdy zabezpieczenie "
        + "Rx jest zabezpieczeniem C/B - to nastawa dotyczy zabezpieczenia centralnego"},
      {"5", "Rx1wyl.", "Rezystancja wy³aczenia zabezpieczenia Rx1 (Rx=R11-R41)",
        "[kohm]", "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        "jw."},
      {"6", "Rx1komp,",
        "Rezystancja kompensacji zabezpieczenia Rx1 (Rx=R11-R41) ", "[kohm]",
        "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        "jw."},
      {"7", "Rx2za³.", "Rezystancja za³aczenia zabezpieczenia Rx2 (Rx=R1-R4)",
        "[kohm]", "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        "gdy Rx jest zabezpieczeniem C/B - to nastawa dotyczy zabezpieczenia blokuj¹cego"},
      {"8", "Rx2wyl.", "Rezystancja wy³aczenia zabezpieczenia Rx2 (Rx=R1-R4)",
        "[kohm]", "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        "jw."},
      {"9", "Rx2komp,", "Rezystancja kompensacji zabezpieczenia Rx2 (Rx=R1-R4)",
        "[kohm]", "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        "jw."},
      {"10", "Rxzwloka", "Zw³oka zabezpieczenia rezystancyjnego Rx (Rx=R1-R4)",
        "[ms]", "X",
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        Boolean.FALSE, "X", 0,
        ""},
      {"11", "Rxkomun", "Komunikat zadzia³ania zabezpieczenia Rx (Rx=R1-R4) nr1",
        "n=1-4", "komunikat 1",
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        ""},
      {"12", "Rxkomun", "Komunikat zadzia³ania zabezpieczenia Rx (Rx=R1-R4) nr2",
        "n=1-4", "komunikat 2",
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        ""},
      {"13", "Rxkomun", "Komunikat zadzia³ania zabezpieczenia Rx (Rx=R1-R4) nr3",
        "n=1-4", "komunikat 3",
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        ""},
      {"14", "Rxkomun", "Komunikat zadzia³ania zabezpieczenia Rx (Rx=R1-R4) nr4",
        "n=1-4", "komunikat 4",
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        Boolean.FALSE, "X", Float.POSITIVE_INFINITY,
        ""},};
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

  private void jTMouseClicked(java.awt.event.MouseEvent evt) {
    int row = jTable1.rowAtPoint(evt.getPoint());
    int col = jTable1.columnAtPoint(evt.getPoint());
    czynnosci(row, col);
  }

  private void czynnosci(int row, int col) {
    if (col == 4 && row > 9) {
      int i = 0;
      String gr = "";
      String pgr = "";
      String napis = jTable1.getValueAt(row, 4).toString();
      Komunikat kom = new Komunikat(this, true, "R" + ind, String.valueOf(row
      - 9), gr,
      pgr, napis, DigTextField.VAL_STRING, 20);
      kom.setVisible(true);
      if (kom.zwrot()) {
        jTable1.setValueAt(kom.napis(), row, col);
      }
    } else if ((col == 5 || col == 8 || col == 11 || col == 14) && row > 9) {
      for (int i = 10; i < 14; i++) {
        if (row != i) {
          jTable1.setValueAt(Boolean.FALSE, i, col);
        }
      }
    }
  }

  private void XjTMouseClicked(java.awt.event.MouseEvent evt) {
    int row = jTable1.rowAtPoint(evt.getPoint());
    int col = jTable1.columnAtPoint(evt.getPoint());
    String jedn = jTable1.getValueAt(row, 3).toString();
    String kom = jTable1.getValueAt(row, 1).toString();
    String wart = jTable1.getValueAt(row, col).toString();
    czynnosci(row, col, jedn, kom, wart);
  }

  private void czynnosci(int row, int col, String jedn, String komun,
                         String wart) {
    if (!jTable1.getColumnName(col).endsWith(Integer.toString(ind))) {
      return;
    }
    String skad = "R" + ind;
    String nr = "";
    String gr = "";
    String pgr = "";
    int zakres = 0;
    int colM;
    int val;
    char p = '0';
    Number napis = 0;
    if (!wart.isEmpty()) {
      napis = Float.valueOf(wart);
    }
    if (jedn.endsWith("0-1")) {
      zakres = 2;
      colM = 1;
      val = DigTextField.VAL_INT;
    } else if (jedn.contains("-")) {
      colM = 1;
      p = jedn.charAt(jedn.indexOf("-") - 1);
      char k = jedn.charAt(jedn.indexOf("-") + 1);
      zakres = k - p + 1;
      val = DigTextField.VAL_INT;
    } else {
      colM = 12;
      val = DigTextField.VAL_DECIMAL4;
    }
    Komunikat kom = new Komunikat(this, true, skad, nr, gr, pgr, napis, val,
    colM, komun, zakres, p);
    kom.setVisible(true);
    if (kom.zwrot()) {
      jTable1.setValueAt(kom.napis(), row, col);
    }

  }

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

  private JTextField txtField() {
    jtxField.setColumns(10);
    jtxField.setColumnsEdit(10);
    jtxField.setFormat(DigTextField.VAL_DECIMAL4);
    jtxField.setValue(0);
    return jtxField;
  }

  private JComboBox generateBox() {
    bx1.removeAllItems();
    bx1.addItem("nieaktywne");
    bx1.addItem("centralne");
    bx1.addItem("blokuj¹ce");
    bx1.addItem("<html>centralno<br/>blokuj¹ce</html>");
    bx1.addItem("<html>inne<br/>aktywne</html>");
    return bx1;
  }

  private void generateBox2() {
    bx1.removeAllItems();
    bx1.addItem("1");
    bx1.addItem("2");
    bx1.addItem("3");
    bx1.addItem("4");
  }

  private void ladujData() {
    int k = jTable1.getColumnCount();
    int l = jTable1.getRowCount();
    for (int i = 0; i < k; i++) {
      for (int j = 0; j < l; j++) {
        Eeprom.rx[j][i] = jTable1.getValueAt(j, i);
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

      if (column == 6 || column == 9 || column == 12 || column == 15) {
        String s = u.extractText(aValue.toString());
        super.setValueAt(s, row, column);
      } else if (column == 7 || column == 10 || column == 13 || column == 16) {
        Float fl = Float.parseFloat(aValue.toString());
        super.setValueAt(fl, row, column);
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
      java.lang.Boolean.class,
      java.lang.String.class,
      java.lang.Float.class,
      java.lang.Boolean.class,
      java.lang.String.class,
      java.lang.Float.class,
      java.lang.Boolean.class,
      java.lang.String.class,
      java.lang.Float.class,
      java.lang.Boolean.class,
      java.lang.String.class,
      java.lang.Float.class,
      java.lang.String.class,
      java.lang.String.class,
      java.lang.String.class,
      java.lang.String.class
    };

    public Class getColumnClass(int columnIndex) {
      return types[columnIndex];
    }

    /*
    public Class<String> getColumnClass(int columnIndex) {
      return String.class;
    }
     */
    public boolean isCellEditable(int row, int column) {
      if ((column == 5 || column == 8 || column == 11 || column == 14) && (row
      == 1 || row > 9)) {
        return true;
      }
      if ((column == 6 || column == 9 || column == 12 || column == 15) && row
      == 0) {
//        generateBox();
        return true;
      }
      /*
      if (column == 5 && row == 10) {
        generateBox2();
        return true;
      }
       */
      if ((column == 7 || column == 10 || column == 13 || column == 16) && (row
      > 2 && row < 10)) {
        return true;
      }
      return false;
    }

  }

  private class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    CheckBoxRenderer() {
      setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus, int row,
                                                   int column) {

      if ((column == 5 || column == 8 || column == 11 || column == 14)
      && !table.getModel().isCellEditable(row, column)) {
        setBackground(Color.orange);
      } else {
        setBackground(color);
      }
      setSelected((value != null && ((Boolean) value).booleanValue()));
      return this;
    }

  }

}
