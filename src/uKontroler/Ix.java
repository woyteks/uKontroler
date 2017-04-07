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
public class Ix extends javax.swing.JDialog {

  /**
   * Creates new form Ix
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
  DigTextField jtxField = new DigTextField();
  public Ix() {
    initComponents();
  }

  public Ix(JDialog frame, boolean modal, int ind, int odplyw) {
    super(frame, modal);
    this.frame = frame;
    this.ind = ind;
    this.odplyw = odplyw;
//    frame.setEnabled(!modal);
    initComponents();
    uzupelnij();

    CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
    naglData();

    defTable(checkBoxRenderer);

    formatuj();

    if (Eeprom.ix == null || Eeprom.ix.length == 0) {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      Eeprom.ix = new Object[l][k];
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          Eeprom.ix[j][i] = jTable1.getValueAt(j, i);
        }
      }
    } else {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          jTable1.setValueAt(Eeprom.ix[j][i], j, i);
        }
      }
    }

    jTable1.getTableHeader().setPreferredSize(new Dimension(jTable1.
    getColumnModel().getTotalColumnWidth(), 32));

    dmWz = defTabMod(dmWz);
    jt = new JTable(dmWz);

    final JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem("Uwagi - poka�/ukryj");
    deleteItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (ukryty) {
          u.nagraj(jTable1, jt);
          int szer = 3 * dialX / 13;
          u.formCol(jTable1, 9, szer);
          dm.setDataVector(data, naglowek);
          renderer(checkBoxRenderer);
          rozmiar();
          u.nagraj(jt, jTable1);
          jTable1.repaint();
        } else {
          u.nagraj(jTable1, jt);
          defTable(checkBoxRenderer);
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

  private void defTable(CheckBoxRenderer checkBoxRenderer) {

    dm = defTabMod(dm);
    jTable1 = new JTable(dm);

    renderer(checkBoxRenderer);
    rozmiar();

    jTable1.setDefaultRenderer(String.class, new MultiLineTableCellRenderer());
    jTable1.setEditingColumn(0);
    jScrollPane1.getViewport().add(jTable1, null);
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTMouseClicked(evt);
      }

    });
  }

  private void renderer(CheckBoxRenderer checkBoxRenderer) {
    jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(
    txtField()));
    jTable1.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(
    txtField()));
    jTable1.getColumnModel().getColumn(5).setCellRenderer(checkBoxRenderer);
    jTable1.getColumnModel().getColumn(7).setCellRenderer(checkBoxRenderer);

  }

  private DefaultTableModel defTabMod(DefaultTableModel dm) {
    dm = new DefaultTableModel() {
      @Override
      public void setValueAt(Object aValue, int row, int column) {
        if (column == 6 || column == 8) {
          Float fl = Float.parseFloat(aValue.toString());
          super.setValueAt(fl, row, column);
        } else {
          super.setValueAt(aValue, row, column); //To change body of generated methods, choose Tools | Templates.
        }
      }

      Class[] types = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Boolean.class,
        java.lang.Float.class,
        java.lang.Boolean.class,
        java.lang.Float.class,
        java.lang.String.class
      };

      public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
      }

      public boolean isCellEditable(int row, int column) {
        if ((column == 5 || column == 7) && (row > 10 || row == 8)) {
          return true;
        }
        if ((column == 6 || column == 8) && (row < 11 && row != 8)) {
          return true;
        }
        return false;
      }

    };
    dm.setDataVector(data, naglowek);
    return dm;
  }

  private void formatuj() {
    u.formCol(jTable1, 0, 30);
    u.formCol(jTable1, 1, 80);
    u.formCol(jTable1, 3, 60);

    u.formCol(jTable1, 9, 0);
  }

  private void rozmiar() {
    for (int i = 5; i < 9; i++) {
      if (!jTable1.getColumnName(i).contains(Integer.toString(ind))) {
        u.formCol(jTable1, i, 0);
      }
    }
  }

  private void naglData() {
    naglowek = new String[]{"Lp",
      "<html><center>Nastawa<br/>symbol</center></html>", "Nastawa opis",
      "<html><center>Jedn<br/>zakres</center><html>", "Komunikat",
      "<html><center>ODP�YW 1<br/>T/N</center><html>",
      "<html><center>ODP�YW 1<br/>Warto��</center><html>",
      "<html><center>ODP�YW 2<br/>T/N</center><html>",
      "<html><center>ODP�YW 2<br/>Warto��</center><html>",
      "Uwagi"};
    data = new Object[][]{
      {"1", "Pp1", "Przek�adnik pradowy odp�yw " + odplyw, "mV/A", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        ""},
      {"2", "Inom", "Nastawa pr�du znamionowego odp�ywu", "[A]", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        "1-1000 A"},
      {"3", "Wsp.Zw.",
        "Nastawa zabezpieczenia zwarciowego - wsp�czynnik zwarcia", "[n/n]",
        "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        ""},
      {"4", "Zw.Zwlo", "Nastawa zabezpieczenia zwarciowego - Zw�oka zwarcia",
        "[ms]", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        ""},
      {"5", "Wsp.p./sf",
        "Nastawa wsp�czynnika przeci��enia/Nastawa Serwis factor. Powi�zane z opcj� wyboru sposobu dzia�ania zabezpieczenia przeci��eniowego",
        "", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        "Wyb�r wp�czynnika przeci��enia tylko w�wczas gdy wybrano spos�b dzia�ania  wg wsp�czynnika przeci��enia.   Wyb�r wsp�czynnika Sf  gdy wybrano spos�b dzia�ania wg charakterystyki"},
      {"6", "Przec.Zwl.",
        "Nastawa  zabezpieczenia przeci��eniowego - Zw�oka zadzia�ania", "s",
        "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        "dzia�a tylko w�wczas, gdy wybrano charakt. przeci��enia wg wsp�czynnika przeci��enia"},
      {"7", "Asymetria",
        "Nastawa zabezpieczenia asymetrowego wsp�czynnik asymetrii", "[%]", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        ""},
      {"8", "Asym.Zwlo",
        "Nastawa zabezpieczenia asymetrowego - zw�oka zadzia�ania", "[ms]", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        ""},
      {"9", "Char.Przec.",
        "Wyb�r sposobu dzia�ania zabezpieczenia przeci��eniowego ", "0-1", "X",
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        "0- wg wsp�czynnika przeci��enia, 1-wg charakterystyki"},
      {"10", "Przekr.P.",
        "Nastawa zabezpieczenia nadmiarowego - warto�� przekroczenia pr�du",
        "[A]", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        "Mo�e by� u�ywana  dla sterowania silnik�w dwubiegowych"},
      {"11", "PP.Zwloka",
        "Nastawa zabezpieczenia nadmiarowego - zw�oka zadzia�ania", "[s]", "X",
        Boolean.FALSE, 0,
        Boolean.FALSE, 0,
        ""},
      {"12", "Kom[0-3]", "wyb�r komunikatu jednego z trzech", "0-3",
        "Komunikat 1",
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        ""},
      {"13", "Kom[0-3]", "wyb�r komunikatu jednego z trzech", "0-3",
        "Komunikat 2",
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        ""},
      {"14", "Kom[0-3]", "wyb�r komunikatu jednego z trzech", "0-3",
        "Komunikat 3",
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        ""},
      {"15", "Kom[0-3]", "wyb�r komunikatu jednego z trzech", "0-3",
        "Komunikat 4",
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        Boolean.FALSE, Float.POSITIVE_INFINITY,
        ""},};
  }

  private void uzupelnij() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle rect = new Rectangle();
    int przesX = 150;
    int przesY = 100;
    dialX = screenSize.width - 3 * przesX;
    dialY = screenSize.height - 4 * przesY;
    rect.setBounds(przesX, przesY, dialX, dialY);
    this.setBounds(rect);
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

  private void jTMouseClicked(java.awt.event.MouseEvent evt) {
    int row = jTable1.rowAtPoint(evt.getPoint());
    int col = jTable1.columnAtPoint(evt.getPoint());
    czynnosci(row, col);
  }

  private void czynnosci(int row, int col) {
    if (col == 4 && row > 10) {
      int i = 0;
      String gr = "";
      String pgr = "";
      String napis = jTable1.getValueAt(row, 4).toString();
      Komunikat kom = new Komunikat(this, true, "ZEW" + ind, String.valueOf(row
      - 6), gr,
      pgr, napis, DigTextField.VAL_STRING, 20);
      kom.setVisible(true);
      if (kom.zwrot()) {
        jTable1.setValueAt(kom.napis(), row, col);
      }
    } else if ((col == 5 || col == 7) && row > 10) {
      for (int i = 11; i < 15; i++) {
        if (row != i) {
          jTable1.setValueAt(Boolean.FALSE, i, col);
        }
      }
    }
  }

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
  dispose();
  ladujData();
//  frame.setEnabled(true);
//  frame.requestFocus();
  }//GEN-LAST:event_jMenuItem1ActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
  dispose();
//  frame.setEnabled(true);
//  frame.requestFocus();
  }//GEN-LAST:event_jMenuItem2ActionPerformed

  private void ladujData() {
    int k = jTable1.getColumnCount();
    int l = jTable1.getRowCount();
    for (int i = 0; i < k; i++) {
      for (int j = 0; j < l; j++) {
        Eeprom.ix[j][i] = jTable1.getValueAt(j, i);
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

  private class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    CheckBoxRenderer() {
      setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus, int row,
                                                   int column) {

      if ((column == 5 || column == 7)
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
