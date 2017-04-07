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
 * @author wojtek
 */
public class SterowaniaPp extends javax.swing.JDialog {

  /**
   * Creates new form SterowaniaPp
   */
  int dialX = 0;
  int dialY = 0;
  JDialog frame;
  String[] naglowek;
  Object[][] data;
  int ind;
  boolean ukryty = true;
  DefaultTableModel dm, dmWz;
  JTable jt;
  Color color = new Color(255, 255, 255);
  DigTextField jtxField = new DigTextField();
  public SterowaniaPp() {
    initComponents();
    uzupelnij();
  }

  public SterowaniaPp(JDialog frame, boolean modal, int ind) {
    super(frame, modal);
    this.frame = frame;
    this.ind = ind;
//    frame.setEnabled(!modal);
    initComponents();
    uzupelnij();

    CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();

    naglData(ind);

    defTable(checkBoxRenderer);

    formatuj();

    if (ind == 0) {
      if (Eeprom.sterow0 == null || Eeprom.sterow0.length == 0) {
        int k = jTable1.getColumnCount();
        int l = jTable1.getRowCount();
        Eeprom.sterow0 = new Object[l][k];
        for (int i = 0; i < k; i++) {
          for (int j = 0; j < l; j++) {
            Eeprom.sterow0[j][i] = jTable1.getValueAt(j, i);
          }
        }
      } else {
        int k = jTable1.getColumnCount();
        int l = jTable1.getRowCount();
        for (int i = 0; i < k; i++) {
          for (int j = 0; j < l; j++) {
            jTable1.setValueAt(Eeprom.sterow0[j][i], j, i);
          }
        }
      }
    } else if (Eeprom.sterow12 == null || Eeprom.sterow12.length == 0) {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      Eeprom.sterow12 = new Object[l][k];
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          Eeprom.sterow12[j][i] = jTable1.getValueAt(j, i);
        }
      }
    } else {
      int k = jTable1.getColumnCount();
      int l = jTable1.getRowCount();
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < l; j++) {
          jTable1.setValueAt(Eeprom.sterow12[j][i], j, i);
        }
      }
    }

    dmWz = defTabMod(dmWz);
    jt = new JTable(dmWz);

    jTable1.getTableHeader().setPreferredSize(new Dimension(jTable1.
    getColumnModel().getTotalColumnWidth(), 32));

    final JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem uwagiItem = new JMenuItem("Uwagi - poka¿/ukryj");
    uwagiItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (ukryty) {
          u.nagraj(jTable1, jt);
          int szer = 3 * dialX / 13;
          if (ind == 0) {
            u.formCol(jTable1, 5, szer);
          } else {
            u.formCol(jTable1, 8, szer);
          }
          dm.setDataVector(data, naglowek);
          rozmiar();
          renderer(checkBoxRenderer);
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
    popupMenu.add(uwagiItem);
    jTable1.setComponentPopupMenu(popupMenu);
  }

  private void defTable(CheckBoxRenderer checkBoxRenderer) {

    dm = defTabMod(dm);

    jTable1 = new JTable(dm);
    jTable1.setDefaultRenderer(String.class, new MultiLineTableCellRenderer());
    renderer(checkBoxRenderer);
    jTable1.setEditingColumn(0);
    jScrollPane1.getViewport().add(jTable1, null);

    rozmiar();
  }

  private void renderer(CheckBoxRenderer checkBoxRenderer) {
    if (ind > 0) {
      jTable1.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(
      txtField()));
      jTable1.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(
      txtField()));
      jTable1.getColumnModel().getColumn(4).setCellRenderer(checkBoxRenderer);
      jTable1.getColumnModel().getColumn(6).setCellRenderer(checkBoxRenderer);
    }
  }

  private JTextField txtField() {
    jtxField.setColumns(10);
    jtxField.setColumnsEdit(10);
    jtxField.setFormat(DigTextField.VAL_DECIMAL4);
    jtxField.setValue(0);
    return jtxField;
  }

  private DefaultTableModel defTabMod(DefaultTableModel dm) {
    dm = new DefaultTableModel() {
      @Override
      public void setValueAt(Object aValue, int row, int column) {
        if (ind > 0 && (column == 5 || column == 7)) {
          Float fl = Float.parseFloat(aValue.toString());
          super.setValueAt(fl, row, column);
        } else {
          super.setValueAt(aValue, row, column); //To change body of generated methods, choose Tools | Templates.
        }
      }

      Class[] types12 = new Class[]{
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
      Class[] types0 = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Boolean.class,
        java.lang.String.class
      };

      public Class<String> getColumnClass(int columnIndex) {
        if (ind == 0) {
          return types0[columnIndex];
        }
        return types12[columnIndex];
      }

      public boolean isCellEditable(int row, int column) {
        if (ind == 0 && column == 4) {
          return true;
        }
        if ((ind > 0) && (column == 4 || column == 6) && (row < 4)) {
          return true;
        }
        if ((ind > 0) && (column == 5 || column == 7) && (row == 4)) {
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
    u.formCol(jTable1, 1, 155);
    u.formCol(jTable1, 3, 60);

    if (ind == 0) {
      u.formCol(jTable1, 5, 0);
    } else {
      u.formCol(jTable1, 8, 0);
    }

  }

  private void naglData(int ind) {
    if (ind == 0) {
      naglowek = new String[]{"Lp", "", "", "<html>Jedn<br/>zakres<html>",
        "Nastawa", "Uwagi"};
      data = new Object[][]{
        {"1", "Odp³yw 1", "Deklaracja  odp³ywu 1", "0-1", Boolean.FALSE,
          "0 - brak odp³ywu O1; 1- odp³yw O1 wystêpuje"},
        {"2", "Odp³yw 2", "Deklaracja  odp³ywu 2", "0-1", Boolean.FALSE,
          "0 - brak odp³ywu O2; 1- odp³yw O2 wystêpuje"},
        {"3", "Niezale¿ne LED", "Wg opisów", "0-1", Boolean.FALSE, ""},
        {"4", "wysw.LED do kas.",
          "Po ust¹pieniu blokady wyœwietla w dalszym ci¹gu blokadê na sygnalizatorach LED a¿ do momentu jej skasowania",
          "0-1", Boolean.FALSE,
          "0-na LEDach pokazywane s¹ blokady od chwili zadzia³ania blokady do czsu jej ust¹pienia, 1 - w przypadku zadeklarowania kasowania blokady przyciskiem stop LED pokazuje stan blokady od chwili jej wyst¹pienia do momentu jej skasowania pomimo wczeœniejszego ust¹pienia stanu blokady"},
        {"5", "Inne awarie",
          "Inne awarie: awarie wewnêtrzne sterownika, reset od watchdoga, nieprawid³owy pomiar wewnêtrzny pr¹du",
          "0-1", Boolean.FALSE,
          "0 - w przypadku wyst¹pienia awarii wewnêtrznej  resetuje sterownik i przechodzi do dalszej pracy; 1 - resetuje sterownik i przed podjêciem dalszej pracy sygnalizuje awariê i oczekuje na skasowanie przy pomocy przycisku STOP"},};
    } else {
      naglowek = new String[]{"Lp", "", "",
        "<html><center>Jedn<br/>zakres</center><html>",
        "<html><center>Nastawa 1<br/>T/N</center><html>",
        "<html><center>Nastawa 1<br/>Wartoœæ</center><html>",
        "<html><center>Nastawa 2<br/>T/N</center><html>",
        "<html><center>Nastawa 2<br/>Wartoœæ</center><html>",
        "Uwagi"};
      data = new Object[][]{
        {"1", "Zdalne/lokalne", "Ustawienia sterowania O" + ind
          + " jako zdalne lub lokalne", "0-1",
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          "1 - Zdalne ,  0 - lokalne "},
        {"2", "Silnik 2biegowy", "Sterowanie silnikiem 2biegowym", "0-1",
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          "0 - silnik normalny 1 - dwubieg"},
        {"3", "Zalaczenie 2biegu od 1",
          "Po spadku pr¹du - rozruch za³aczany 2gi bieg", "0-1",
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          "0 - bez za³aczania ; 1 - za³acza II bieg"},
        {"4", "Sterowanie funkcyjne",
          "Wykonuje nieprzerwanie sekwencjê za³aczania tak d³ugo jak za³aczony start",
          "0-1",
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          Boolean.FALSE, Float.POSITIVE_INFINITY,
          "0-wykonuje wszystkie sekwencje, 1 - wykonuje w pêtli sekwencjê za³aczania odp³ywu tak d³ugo jak d³ugo aktywny jest sygna³ start"},
        {"5", "Zw³oka asymetrii",
          "Pomija zabezp. asymetrowe przez zadany czas zw³oki liczony od za³aczenia odp³ywu",
          "[s]",
          Boolean.FALSE, 0,
          Boolean.FALSE, 0,
          ""},};
    }

  }

  private void uzupelnij() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle rect = new Rectangle();
    int przesX = 150;
    int przesY = 100;
    dialX = screenSize.width - 3 * przesX;
    dialY = screenSize.height - 5 * przesY - 50;
    rect.setBounds(przesX, przesY, dialX, dialY);
    this.setBounds(rect);
  }

  private void rozmiar() {
    if (ind > 0) {
      for (int i = 4; i < 8; i++) {
        if (!jTable1.getColumnName(i).contains(Integer.toString(ind))) {
          u.formCol(jTable1, i, 0);
        } else {
          u.formCol(jTable1, i, 90);
        }
      }
    }
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

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
  dispose();
//  frame.setEnabled(true);
//  frame.requestFocus();
  }//GEN-LAST:event_jMenuItem2ActionPerformed

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
  dispose();
  ladujData();
//  frame.setEnabled(true);
//  frame.requestFocus();
  }//GEN-LAST:event_jMenuItem1ActionPerformed

  private void ladujData() {
    int k = jTable1.getColumnCount();
    int l = jTable1.getRowCount();
    for (int i = 0; i < k; i++) {
      for (int j = 0; j < l; j++) {
        if (ind == 0) {
          Eeprom.sterow0[j][i] = jTable1.getValueAt(j, i);
        } else {
          Eeprom.sterow12[j][i] = jTable1.getValueAt(j, i);
        }
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

      if ((column == 4 || column == 6)
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
