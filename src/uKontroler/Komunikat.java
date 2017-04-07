/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uKontroler;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

/**
 *
 * @author wojtek
 */
public class Komunikat extends javax.swing.JDialog {

  /**
   * Creates new form Komunikat
   */
  String skad;
  String nr;
  String gr;
  String pgr;
  int dialX = 0;
  int dialY = 0;
  Boolean flaga = false;
  JCheckBox jcb;
  JComboBox<Integer> jcbx;
  int zakres;
  int val;
  Object napis;
  char p;
  int colW;
  String kom;
  public Komunikat(java.awt.Frame parent, boolean modal, String skad, String nr,
                   String gr, String pgr, Object napis, int val, int colW) {
    this(parent, modal, skad, nr,
    gr, pgr, napis, val, colW, "Komunikat ", 0, '0');

  }

  public Komunikat(java.awt.Dialog parent, boolean modal, String skad, String nr,
                   String gr, String pgr, Object napis, int val, int colW) {
    this(parent, modal, skad, nr,
    gr, pgr, napis, val, colW, "Komunikat ", 0, '0');

  }

  public Komunikat(java.awt.Frame parent, boolean modal, String skad, String nr,
                   String gr, String pgr, Object napis, int val, int colW,
                   String kom, int zakres) {
    this(parent, modal, skad, nr,
    gr, pgr, napis, val, colW, kom, 0, '0');

  }

  public Komunikat(java.awt.Dialog parent, boolean modal, String skad, String nr,
                   String gr, String pgr, Object napis, int val, int colW,
                   String kom, int zakres) {
    this(parent, modal, skad, nr,
    gr, pgr, napis, val, colW, kom, 0, '0');

  }

  public Komunikat(java.awt.Dialog parent, boolean modal, String skad, String nr,
                   String gr, String pgr, Object napis, int val, int colW,
                   String kom, int zakres, char p) {
    super(parent, modal);
    this.skad = skad;
    this.nr = nr;
    this.pgr = pgr;
    this.gr = gr;
    this.zakres = zakres;
    this.val = val;
    this.napis = napis;
    this.p = p;
    this.colW = colW;
    this.kom = kom;
    initComponents();
    body();
  }

  public Komunikat(java.awt.Frame parent, boolean modal, String skad, String nr,
                   String gr, String pgr, Object napis, int val, int colW,
                   String kom, int zakres, char p) {
    super(parent, modal);
    this.skad = skad;
    this.nr = nr;
    this.pgr = pgr;
    this.gr = gr;
    this.zakres = zakres;
    this.val = val;
    this.napis = napis;
    this.p = p;
    this.colW = colW;
    this.kom = kom;
    initComponents();
    body();
  }

  private void body() {
    TitledBorder borderText = javax.swing.BorderFactory.createTitledBorder(
    javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.
    createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(-5, 0, 0,
    0)), skad);
    borderText.setTitleFont(new java.awt.Font("Tahoma", 1, 10));

    if (zakres == 2) {
      jPanel1.remove(jtxField);
      jcb = new JCheckBox();
      if (!napis.toString().startsWith("0")) {
        jcb.setSelected(true);
      } else {
        jcb.setSelected(false);
      }
      jPanel1.add(jcb);
    }
    if (zakres > 2) {
      jPanel1.remove(jtxField);
      jcbx = new JComboBox<Integer>();
      int l = Character.getNumericValue(p);
      for (int i = 0; i < zakres; i++) {
        jcbx.addItem(l++);
      }
      Integer k = 0;
      if (!napis.toString().substring(0, 1).startsWith("0")) {
        k = Integer.parseInt(napis.toString().substring(0, 1));
      }
      jcbx.setSelectedItem(k);
      jPanel1.add(jcbx);
    }
    jtxField.setColumns(colW);
    jtxField.setColumnsEdit(colW);
    jtxField.setFormat(val);
    jtxField.setValue(napis);
    this.setTitle(gr + "/" + pgr);
    jLabel1.setText(kom + nr);

    jPanel1.setBorder(borderText);

    pack();
    uzupelnij();

    jBZapisz.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBZapiszActionPerformed(evt);
      }

    });
    jBAnuluj.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBAnulujActionPerformed(evt);
      }

    });
  }

  public String napis() {
    if (zakres == 2) {
      if (jcb.isSelected()) {
        return "1";
      }
      return "0";
    } else if (zakres > 2) {
      if (jcbx.getSelectedItem() != null) {
        return jcbx.getSelectedItem().toString();
      }
      return "0";
    }
    String str = jtxField.getText();
    if (val == DigTextField.VAL_DECIMAL4 && !str.contains(".")) {
      str = str.trim() + ".00";
    }
    return str;
  }

  public Boolean zwrot() {
    return flaga;
  }

  private void uzupelnij() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle rect = new Rectangle();
    int przesX = 150;
    int przesY = 100;
    dialX = screenSize.width - 6 * przesX;
    dialY = screenSize.height - 6 * przesY;
    rect.setBounds(2 * przesX, 2 * przesY, dialX, dialY);
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
    java.awt.GridBagConstraints gridBagConstraints;

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jtxField = new DigTextField();
    jPanel2 = new javax.swing.JPanel();
    jBZapisz = new javax.swing.JButton();
    jBAnuluj = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    getContentPane().setLayout(new java.awt.GridBagLayout());

    jPanel1.setLayout(new java.awt.GridBagLayout());

    jLabel1.setText("Komunikat");
    jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

    jtxField.setText("jTextField2");
    jtxField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        jtxFieldKeyPressed(evt);
      }

    });
    jPanel1.add(jtxField, new java.awt.GridBagConstraints());

    getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

    jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2,
    javax.swing.BoxLayout.LINE_AXIS));

    jBZapisz.setText("Zapisz");
    jBZapisz.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBZapiszActionPerformed(evt);
      }

    });
    jPanel2.add(jBZapisz);

    jBAnuluj.setText("Anuluj");
    jBAnuluj.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBAnulujActionPerformed(evt);
      }

    });
    jPanel2.add(jBAnuluj);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    getContentPane().add(jPanel2, gridBagConstraints);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jtxFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxFieldKeyPressed
    int k = evt.getKeyCode();

    if (k == KeyEvent.VK_ESCAPE) {
      jBAnulujActionPerformed(evt);
    }
    if (k == KeyEvent.VK_ENTER) {
      jBZapiszActionPerformed(evt);
    }

  }//GEN-LAST:event_jtxFieldKeyPressed

  private void jBZapiszActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBZapiszActionPerformed
    flaga = true;
    dispose();
  }//GEN-LAST:event_jBZapiszActionPerformed

  private void jBAnulujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAnulujActionPerformed
    flaga = false;
    dispose();
  }//GEN-LAST:event_jBAnulujActionPerformed

  private void jBAnulujActionPerformed(java.awt.event.KeyEvent evt) {
    flaga = false;
    dispose();
  }

  private void jBZapiszActionPerformed(java.awt.event.KeyEvent evt) {
    flaga = true;
    dispose();
  }

  /**
   * @param args the command line arguments
   */

// Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jBAnuluj;
  private javax.swing.JButton jBZapisz;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private DigTextField jtxField;
// End of variables declaration//GEN-END:variables
}
