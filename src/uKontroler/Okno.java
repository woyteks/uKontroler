/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uKontroler;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 *
 * @author wojtek
 */
public class Okno extends javax.swing.JFrame {

  /**
   * Creates new form Okno
   */
  int dialX = 0;
  int dialY = 0;
  public static StringBuffer[] strAKx;
  public static StringBuffer[] strAKKom;
  public static StringBuffer[] strRx;
  public static StringBuffer strRKom;
  public static StringBuffer strZEWx;
  public static StringBuffer strZEWKom;
  public static StringBuffer strIx;
  public static StringBuffer strIKom;
  public static StringBuffer strSter0x;
  public static StringBuffer strSter12x;
  public static StringBuffer strParamx;
  public static StringBuffer strCharaktx;
  public static StringBuffer strSekwenx;
  public static StringBuffer strSekwTx;
  public static BufferedReader input;
  public static OutputStream output;

  public Okno() {
    initComponents();
    uzupelnij();
  }

  private void uzupelnij() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle rect = new Rectangle();
    int przesX = 150;
    int przesY = 100;
    dialX = screenSize.width - 6 * przesX;
    dialY = screenSize.height - 4 * przesY;
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

    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenu3 = new javax.swing.JMenu();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenuItem4 = new javax.swing.JMenuItem();
    jMenuItem6 = new javax.swing.JMenuItem();
    jMenu4 = new javax.swing.JMenu();
    jMenuItem3 = new javax.swing.JMenuItem();
    jMenuItem5 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

    jMenu1.setText("Login");

    jMenuItem1.setText("Zamknij");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }

    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Urządzenie");

    jMenu3.setText("Odczyt");

    jMenuItem2.setText("Urządzenie");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }

    });
    jMenu3.add(jMenuItem2);

    jMenuItem4.setText("Plik");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem4ActionPerformed(evt);
      }

    });
    jMenu3.add(jMenuItem4);

    jMenuItem6.setText("Czysty");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem6ActionPerformed(evt);
      }

    });
    jMenu3.add(jMenuItem6);

    jMenu2.add(jMenu3);

    jMenu4.setText("Zapis");

    jMenuItem3.setText("Urządzenie");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }

    });
    jMenu4.add(jMenuItem3);

    jMenuItem5.setText("Plik");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem5ActionPerformed(evt);
      }

    });
    jMenu4.add(jMenuItem5);

    jMenu2.add(jMenu4);

    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
    getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGap(0, 279, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
  dispose();
  System.exit(0);
  }//GEN-LAST:event_jMenuItem1ActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

//  SerialClass sc = new SerialClass();
//  sc.initialize();
 
  GUI gui = new GUI(this, true);

  int baud = gui.baudRate();
  String port = gui.portName();
  System.out.println("uKontroler.Okno.jMenuItem2ActionPerformed()");
//    czytajPort();

  /*
  SerialClass sc = new SerialClass();
  sc.initialize();
  input = SerialClass.input;
  output = SerialClass.output;
  InputStreamReader ir = new InputStreamReader(System.in);
  BufferedReader br = new BufferedReader(ir);
  System.out.println("uKontroler.Okno.jMenuItem2ActionPerformed()");
   */
//      JFrame fr = new Eeprom(this, true);
//      fr.setVisible(true);
  }//GEN-LAST:event_jMenuItem2ActionPerformed

private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
  // TODO add your handling code here:
  }//GEN-LAST:event_jMenuItem3ActionPerformed

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
  String pr = System.getProperty("user.home");
  File fg = null;
  File f = new File(pr);
  if (f.exists()) {
    File f1 = new File(f, "");
    if (f1.exists()) {
      fg = f1;
    }
  }
  JFileChooser fc = null;
  if (fg == null) {
    fc = new JFileChooser();
  } else {
    fc = new JFileChooser(fg);
  }
  //   fc.addChoosableFileFilter(new FileChooserExtensionsFilter("dat"));
  fc.setAcceptAllFileFilterUsed(false);
  fc.setSelectedFile(new File("eeprom.dat"));
  fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
  fc.setMultiSelectionEnabled(false);
  fc.setDialogTitle("Czytaj");
  int a = fc.showOpenDialog(this);
  if (a != JFileChooser.APPROVE_OPTION) {
    return;
  }
  }//GEN-LAST:event_jMenuItem4ActionPerformed

private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
  String pr = System.getProperty("user.home");
  File fg = null;
  File f = new File(pr);
  if (f.exists()) {
    File f1 = new File(f, "");
    if (f1.exists()) {
      fg = f1;
    }
  }
  JFileChooser fc = null;
  if (fg == null) {
    fc = new JFileChooser();
  } else {
    fc = new JFileChooser(fg);
  }
  //   fc.addChoosableFileFilter(new FileChooserExtensionsFilter("dat"));
  fc.setAcceptAllFileFilterUsed(false);
  fc.setSelectedFile(new File("eeprom.dat"));
  fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
  fc.setMultiSelectionEnabled(false);
  fc.setDialogTitle("Czytaj");
  int a = fc.showOpenDialog(this);
  if (a != JFileChooser.APPROVE_OPTION) {
    return;
  }
  }//GEN-LAST:event_jMenuItem5ActionPerformed

private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
  JDialog fr = new Eeprom(this, true);
  fr.setVisible(true);
  }//GEN-LAST:event_jMenuItem6ActionPerformed

  /**
   * @param args the command line arguments
   */

// Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenu jMenu4;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItem4;
  private javax.swing.JMenuItem jMenuItem5;
  private javax.swing.JMenuItem jMenuItem6;
// End of variables declaration//GEN-END:variables
}
