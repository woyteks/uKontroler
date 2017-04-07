package uKontroler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.FocusManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import static jxl.biff.FormatRecord.logger;

@SuppressWarnings("StaticNonFinalUsedInInitialization")
public final class u {

  public static final int OSTYPE_98 = 1;
  public static final int OSTYPE_NT = 2;
  public static final int OSTYPE_UNIX = 3;
  private static ResourceBundle digProperties;
  public static ResourceBundle serviceProperties;
  private static boolean running = false;
  private static boolean ejb = false;
  private static URL xmlCfgBase = null;
  private static int userId = 0;
  private static int grUserId = 0;
  private static String tempDir = "";
  private static String tempBase = "";
  private static String transDir = "";
  private static String transBase = "";
  private static String localDir = "";
  private static String localBase = "";
  private static String workDir = "";
  private static String userName = "";
  private static String userNameFull = "";
  private static Integer wlascId = 0;
  private static String wlascNameFull = "";
  private static String wlascNameSkrot = "";
  private static String userEJBName = "";
  private static String userEJBNameFull = "";
  private static boolean updSchema = false;
  private static boolean dropCfg = false;
  private static boolean cacheable = false;
  private static Date dataSystem = Calendar.getInstance().getTime();
  private static Date digDataKontr = null;
  private static String digRodzKontr = "";
  private static String dateFormat;
  private static String dateTimeFormat;
  private static String remoteServer = "localhost";
  private static String remotePort = "localhost";
  private static String backupFile = null;
  private static boolean jar = false;
  private static boolean webStart = false;
  private static URL applicationURL = null;
  private static JFrame rootFrame = null;
  private static Integer sprawa = 0;
  private static int applicationVersion = 0;
  private static int applicationOldVersion = 0;
  private static boolean textMode = false;
  private static boolean licWS = false;
  private static boolean noBin = false;
  private static boolean installBin = false;
  private static boolean komunikatorRun = false;
  private static SortedSet<String> supportedImageFormats = new TreeSet<String>();
  private static Properties systemCfg = new Properties();
  private static HTMLParserCallback parser;
  static {
    try {
      digProperties = ResourceBundle.getBundle("etc.Dig");
    } catch (Exception exception) {
    }
    serviceProperties = ResourceBundle.
    getBundle("sun.print.resources.serviceui");
    if (digProperties != null) {
      dateFormat = digProperties.getString("df");
    } else {
      dateFormat = "yyyy.MM.dd";
    }
    dateTimeFormat = dateFormat + " HH:mm";
  }

  public static ResourceBundle getDigProperties() {
    return digProperties;
  }

  public static String getServiceUIName(String key) {
    String zwrot = key;
    try {
      zwrot = serviceProperties.getString(key);
    } catch (Exception exception) {
    }
    return zwrot;
  }

  public static boolean isNoBin() {
    return noBin;
  }

  public static void setNoBin(boolean noBin) {
    u.noBin = noBin;
  }

  public static boolean isInstallBin() {
    return installBin;
  }

  public static void setInstallBin(boolean installBin) {
    u.installBin = installBin;
  }

  public static boolean isTextMode() {
    return textMode;
  }

  public static void setTextMode(boolean textMode) {
    u.textMode = textMode;
  }

  public static boolean isLicWS() {
    return licWS;
  }

  public static void setLicWS(boolean licWS) {
    u.licWS = licWS;
  }

  public static boolean isRunning() {
    return running;
  }

  public static void setRunning(boolean run) {
    running = run;
  }

  public static synchronized void setEjb(boolean ej) {
    ejb = ej;
  }

  public static synchronized boolean isEjb() {
    return ejb;
  }

  public static void setTempDir(String tempD) throws Exception {
    if (u.isInstallBin()) {
      return;
    }
    File d = new File(tempD);
    if (!d.exists()) {
      if (!d.mkdirs()) {
        throw new Exception("");
      }
    }
    tempDir = tempD;
  }

  public static void setTempBase(String nameBase) {
    tempBase = tempDir + File.separator + nameBase;
  }

  public static void setTransBase(String nameBase) {
    transBase = transDir + File.separator + nameBase;
  }

  public static void setLocalDir(String localD) throws Exception {
    if (u.isInstallBin()) {
      return;
    }
    File d = new File(localD);
    if (!d.exists()) {
      if (!d.mkdirs()) {
        throw new Exception("");
      }
    }
    localDir = localD;
    System.setProperty("home.localdir", localDir);
  }

  public static void setLocalBase(String nameBase) {
    localBase = localDir + File.separator + nameBase;
  }

  public static void setUserName(String userN) {
    userName = userN;
  }

  public static void setUserId(int uId) {
    userId = uId;
  }

  public static int getUserId() {
    return userId;
  }

  public static void setUserNameFull(String userNameFul) {
    userNameFull = userNameFul;
  }

  public static void setWlascNameFull(String wlascNameFul) {
    wlascNameFull = wlascNameFul;
  }

  public static void setWlascNameSkrot(String wlascNameSkr) {
    wlascNameSkrot = wlascNameSkr;
  }

  public static void setWlascId(Integer wlascI) {
    wlascId = wlascI;
  }

  public static void setUserEJBNameFull(String userEJBNameFul) {
    userEJBNameFull = userEJBNameFul;
  }

  public static void setUserEJBName(String userEJBNam) {
    userEJBName = userEJBNam;
  }

  public static String getTempDir() {
    return tempDir;
  }

  public static String getUserName() {
    return userName;
  }

  public static int getGrUserId() {
    return grUserId;
  }

  public static boolean isAdminUser() {
    return grUserId <= 2;
  }

  public static boolean isAdminSuper() {
    return grUserId <= 1;
  }

  public static String getUserNameFull() {
    return userNameFull;
  }

  public static String getWlascNameFull() {
    return wlascNameFull;
  }

  public static String getWlascNameSkrot() {
    return wlascNameSkrot;
  }

  public static Integer getWlascId() {
    return wlascId;
  }

  public static String getUserEJBNameFull() {
    return userEJBNameFull;
  }

  public static String getUserEJBName() {
    return userEJBName;
  }

  public static boolean isUpdSchema() {
    return updSchema;
  }

  public static boolean isDropCfg() {
    return dropCfg;
  }

  public static boolean isCacheable() {
    return cacheable;
  }

  public static String getTempBase() {
    return tempBase;
  }

  public static String getTransBase() {
    return transBase;
  }

  public static String getLocalDir() {
    return localDir;
  }

  public static String getLocalBase() {
    return localBase;
  }

  public static Date getDataSystem() {
    return dataSystem;
  }

  public static String getDateFormat() {
    return dateFormat;
  }

  public static String getDateTimeFormat() {
    return dateTimeFormat;
  }

  public static Component getLastVisibleDigFrame() {
    Container c = FocusManager.getCurrentManager().getCurrentFocusCycleRoot();
    if (c == null) {
      c = getRootFrame();
    }
    return c;
  }

  public static String getRemoteServer() {
    return remoteServer;
  }

  public static String getRemotePort() {
    return remotePort;
  }

  public static String getBackupFile() {
    return backupFile;
  }

  public static String getTransDir() {
    return transDir;
  }

  public static URL getXmlCfgBase() {
    return xmlCfgBase;
  }

  public static boolean isJar() {
    return jar;
  }

  public static boolean isWebStart() {
    return webStart;
  }

  public static void error(String error) {
    if (isTextMode()) {
      System.out.println(error);
      return;
    }
    error(error, getLastVisibleDigFrame());
  }

  public static void error(String error, Component c) {
    if (isTextMode()) {
      System.out.println(error);
      return;
    }
    JOptionPane.showMessageDialog(c, getMessagePanel(error), "B³¹d",
    JOptionPane.ERROR_MESSAGE);
  }

  public static void info(String info) {
    if (isTextMode()) {
      System.out.println(info);
      return;
    }
    info(info, getLastVisibleDigFrame());
  }

  public static void info(String info, Component c) {
    if (isTextMode()) {
      System.out.println(info);
      return;
    }
    JOptionPane.showMessageDialog(c, getMessagePanel(info), "",
    JOptionPane.INFORMATION_MESSAGE);
  }

  public static int wybOkAn(String info, String bOk) {
    return wybOkAn(info, getLastVisibleDigFrame(), bOk, "");
  }

  public static int wybOkAn(String info) {
    int n = wybOkAn(info, getLastVisibleDigFrame(), null, "");
    return n;
  }

  public static int wybOkAn(String info, Component parent, String bOk) {
    return wybOkAn(info, parent, bOk, "");
  }

  public static int wybOkAn(String info, Component parent, String bOk,
                            String title) {
    JButton buttonOK = new JButton();
    if (bOk == null) {
      buttonOK.setText("OK");
    } else {
      buttonOK.setText(bOk);
    }
    buttonOK.setMnemonic(buttonOK.getText().charAt(0));
    buttonOK.setPreferredSize(new Dimension(80, 25));
    JButton buttonAnuluj = new JButton();
    buttonAnuluj.setText("Anuluj");
    buttonAnuluj.setMnemonic(buttonAnuluj.getText().charAt(0));
    buttonAnuluj.setPreferredSize(new Dimension(80, 25));
    Object[] options = new Object[]{buttonOK, buttonAnuluj};
    final JOptionPane pane = new JOptionPane(getMessagePanel(info),
    JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, options,
    options[1]);
    if (title == null) {
      title = "";
    }
    final JDialog dialog = pane.createDialog(parent, title);
    buttonOK.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        pane.setValue(JOptionPane.YES_OPTION);
        dialog.dispose();
      }

    });
    buttonAnuluj.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        pane.setValue(JOptionPane.CANCEL_OPTION);
        dialog.dispose();
      }

    });
    buttonAnuluj.requestFocus();
    dialog.setVisible(true);
    Integer n1 = (Integer) pane.getValue();
    int n = -1;
    if (n1 != null) {
      n = n1.intValue();
    }
    if (n == -1) {
      n = JOptionPane.CANCEL_OPTION;
    }
    return n;
  }

  public static int wyb3An(String query, String one, String two) {
    return wyb3An(query, getLastVisibleDigFrame(), one, two);
  }

  public static int wyb3An(String query, Component parent, String one,
                           String two) {
    return wyb3An(query, parent, one, two, "Anuluj", null);
  }

  public static int wyb3An(String query, Component parent, String one,
                           String two, String an, ActionListener actLisAn) {
    JButton buttonOne = new JButton();
    buttonOne.setText(one);
    buttonOne.setMnemonic(buttonOne.getText().charAt(0));
    buttonOne.setPreferredSize(new Dimension(80, 25));

    JButton buttonTwo = new JButton();
    buttonTwo.setText(two);
    buttonTwo.setMnemonic(buttonTwo.getText().charAt(0));
    buttonTwo.setPreferredSize(new Dimension(80, 25));

    JButton buttonAnuluj = new JButton();
    buttonAnuluj.setText(an);
    buttonAnuluj.setMnemonic(buttonAnuluj.getText().charAt(0));
    buttonAnuluj.setPreferredSize(new Dimension(80, 25));
    Object[] options = new Object[]{buttonOne, buttonTwo, buttonAnuluj};
    final JOptionPane pane = new JOptionPane(getMessagePanel(query),
    JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null,
    options, options[1]);
    final JDialog dialog = pane.createDialog(parent, "");

    buttonOne.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        pane.setValue(JOptionPane.YES_OPTION);
        dialog.dispose();
      }

    });
    buttonTwo.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        pane.setValue(JOptionPane.NO_OPTION);
        dialog.dispose();
      }

    });
    if (actLisAn == null) {
      buttonAnuluj.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          pane.setValue(JOptionPane.CANCEL_OPTION);
          dialog.dispose();
        }

      });
    } else {
      buttonAnuluj.addActionListener(actLisAn);
    }
    buttonAnuluj.requestFocus();
    dialog.setVisible(true);
    Integer n1 = (Integer) pane.getValue();
    int n = -1;
    if (n1 != null) {
      n = n1.intValue();
    }
    if (n == -1) {
      n = JOptionPane.CANCEL_OPTION;
    }
    return n;
  }

  private static JPanel getMessagePanel(String text) {
    BorderLayout ss = new BorderLayout();
    final int fsize = 13;
    final String fname = Font.MONOSPACED;
    JPanel pan = new JPanel(ss);
    if (text.startsWith("<html>")) {
      JLabel d = new JLabel();
      d.setFont(new Font(fname, Font.PLAIN, fsize));
      d.setText(text);
      pan.add(d, BorderLayout.CENTER);
    } else {
      JTextPane d = new JTextPane();
      d.selectAll();
      d.setEditable(false);
      d.setFocusable(false);
      SimpleAttributeSet attribs = new SimpleAttributeSet();
      StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
      StyleConstants.setFontFamily(attribs, fname);
      StyleConstants.setFontSize(attribs, fsize);
      d.setParagraphAttributes(attribs, true);
      d.setText(text);
      d.setBackground(pan.getBackground());
      pan.add(d, BorderLayout.CENTER);
    }
    return pan;
  }

  public static FilenameFilter filter(String maska) {
    return (new filter(maska));
  }

  public static Date getDateTimeFromString(String dateString) {
    return getDateFromString(dateString, getDateTimeFormat());
  }

  public static Date getDateFromString(String dateString) {
    return getDateFromString(dateString, getDateFormat());
  }

  public static Date getDateFromString(String dateString, String format) {
    Date zwrot = null;
    try {
      SimpleDateFormat sf = new SimpleDateFormat(format);
      zwrot = sf.parse(dateString.trim());
    } catch (Exception ex) {
    }
    return zwrot;
  }

  public static String getStringFromCurrDateTime() {
    return getStringFromDateTime(Calendar.getInstance().getTime());
  }

  public static String getStringFromDateTime(Date d) {
    return getStringFromDate(d, getDateTimeFormat());
  }

  public static String getStringFromDate(Date d) {
    return getStringFromDate(d, getDateFormat());
  }

  public static String getStringFromDate(Date d, String format) {
    if (d == null) {
      return "";
    }
    SimpleDateFormat sf = new SimpleDateFormat(format);
    return sf.format(d);
  }

  public static String getStringFromCurrDate() {
    return getStringFromCurrDate(getDateFormat());
  }

  public static String getStringFromCurrDate(String format) {
    SimpleDateFormat sf = new SimpleDateFormat(format);
    return sf.format(Calendar.getInstance().getTime());
  }

  public static String getDataSystemS() {
    return getStringFromDate(dataSystem);
  }

  public synchronized static boolean deleteFiles(String path) {
    return deleteFiles(path, "", new HashSet());
  }

  public synchronized static boolean deleteFiles(String path, String mask) {
    return deleteFiles(path, mask, new HashSet());
  }

  public synchronized static boolean deleteFiles(String path, Set excludeFile) {
    return deleteFiles(path, "", excludeFile);
  }

  public synchronized static boolean deleteFiles(String path, String mask,
                                                 Set excludeFile) {
    boolean fl = true;
    if (path == null || path.trim().length() == 0) {
      return true;
    }
    File ff = new File(path);
    if (ff.exists()) {
      if (!ff.isDirectory()) {
        return true;
      }
      File[] lf = ff.listFiles(u.filter(mask));
      for (int i = 0; i < lf.length; i++) {
        if (lf[i].isDirectory()) {
          fl = deleteFiles(lf[i].getAbsolutePath(), mask, excludeFile) || fl;
        }
        if (!fl) {
          return fl;
        }
        if (!excludeFile.contains(lf[i].getName())) {
          fl = lf[i].delete() || fl;
          if (!fl) {
            return fl;
          }
        } else {
          excludeFile.add(ff.getName());
        }
      }
    }
    return fl;
  }

  public synchronized static boolean deleteDir(String path) {
    boolean fl = true;
    if (path == null || path.trim().length() == 0) {
      return true;
    }
    File ff = new File(path);
    if (ff.exists()) {
      if (ff.isDirectory()) {
        File[] lf = ff.listFiles();
        for (int i = 0; i < lf.length; i++) {
          if (lf[i].isDirectory()) {
            deleteDir(lf[i].getAbsolutePath());
          } else {
            lf[i].delete();
          }
        }
      }
      fl = ff.delete();
    }
    //seslog(path, 0);
    return fl;
  }

  public synchronized static boolean createDir(String path) {
    boolean fl = true;
    File ff = new File(path);
    if (ff.exists()) {
      //seslog(path, 1);
      return fl;
    }
    if (!ff.mkdirs()) {
      System.out.println("nie utworzyl");
      return false;
    }
    //(path, 1);
    return true;
  }

  public synchronized static void seslog(String path, int skad) {

    String p = path;
    int i = path.lastIndexOf("/");
    if (i >= 0) {
      p = path.substring(0, path.lastIndexOf("/"));
    }
    String logl = p + File.separator + "session.log";
    String name = path.substring(path.lastIndexOf("/") + 1);
    File flog = new File(logl);
    try {
      if (!flog.exists()) {
        flog.createNewFile();
        if (skad == 0) {
          return;
        }
      }
      BufferedReader in = new BufferedReader(new FileReader(flog));
      StringBuffer sb = new StringBuffer();
      String s = "";
      while ((s = in.readLine()) != null) {
        if (!s.equals(name) && !s.equals("\n") && !s.isEmpty()) {
          sb.append(s).append('\n');
        }
      }
      if (skad == 1) {
        sb.append(name);
      }
      PrintWriter out
      = new PrintWriter(new BufferedWriter(new FileWriter(flog)));
      out.println(sb);
      out.close();
    } catch (Exception ex) {
    }
  }

  public synchronized static boolean zipuj(String str, String path) {
    return zipuj(str, path, false);
  }

  public synchronized static boolean zipuj(String str, String path,
                                           boolean isSourceRemove) {
    File fil = new File(path);
    String[] lista = fil.list(u.filter(str));
    File path2 = new File(path + File.separator + str);
    ZipOutputStream zos = null;
    try {
      FileOutputStream fos = new FileOutputStream(path2);
      CheckedOutputStream csum = new CheckedOutputStream(fos, new Adler32());
      zos = new ZipOutputStream(csum);
      zos.setMethod(ZipOutputStream.DEFLATED);
      zos.setLevel(Deflater.BEST_COMPRESSION);
      for (int i = 0; i < lista.length; i++) {
        if (lista[i].equalsIgnoreCase(str)) {
          continue;
        }
        String f = fil + File.separator + lista[i];
        File sourceFile = new File(f);
        if (sourceFile.isDirectory()) {
          zipDir(fil, sourceFile, zos);
          if (isSourceRemove) {
            deleteDir(sourceFile.getAbsolutePath());
          }
        } else {
          zipEntry(fil, sourceFile, zos);
          if (isSourceRemove) {
            sourceFile.delete();
          }
        }
      }
    } catch (Exception ex) {
      return false;
    } finally {
      if (zos != null) {
        try {
          zos.close();
        } catch (IOException ex1) {
        }
      }
    }
    return true;
  }

  private static void zipDir(File parentDir, File dir, ZipOutputStream out)
  throws Exception {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      File f = files[i];
      if (f.isDirectory()) {
        zipDir(parentDir, f, out);
      } else {
        zipEntry(parentDir, f, out);
      }
    }
  }

  private static void zipEntry(File parentDir, File file, ZipOutputStream out)
  throws Exception {
    if (file.isDirectory()) {
      zipDir(parentDir, file, out);
      return;
    }
    BufferedInputStream bufin = null;
    try {
      bufin = new BufferedInputStream(new FileInputStream(file));
      String zipFilePath = file.getCanonicalPath().substring(parentDir.
      getCanonicalPath().length() + 1, file.getCanonicalPath().length());
      out.putNextEntry(new ZipEntry(zipFilePath));
      int bytes_read;
      byte[] buf = new byte[8192];
      while ((bytes_read = bufin.read(buf, 0, buf.length)) != -1) {
        out.write(buf, 0, bytes_read);
      }
      out.flush();
      out.closeEntry();
    } catch (IOException ex) {
      throw new Exception();
    } finally {
      if (bufin != null) {
        try {
          bufin.close();
        } catch (IOException ex) {
        }
      }
    }
  }

  public synchronized static void oczysc(String fil, String patt) {
    if (patt.trim().length() == 0) {
      return;
    }
    File[] plik;
    plik = new File(fil).listFiles();
    for (int a = 0; a < plik.length; a++) {
      if (!plik[a].getName().equals(patt)) {
        if (plik[a].getName().matches(patt + ".*$")) {
          if (plik[a].isDirectory()) {
            try {
              u.oczysc(plik[a].getCanonicalPath().toString(), patt);
              plik[a].delete();
            } catch (IOException ex) {
            }
          } else {
            plik[a].delete();
          }
        }
      }
    }
  }

  public synchronized static void oczyscWzor(String fil, String patt) {
    if (patt.trim().length() == 0) {
      return;
    }
    File[] plik;
    plik = new File(fil).listFiles();
    for (int a = 0; a < plik.length; a++) {
      if (plik[a].getName().equals(patt)) {
        if (plik[a].getName().matches(patt + ".*$")) {
          if (plik[a].isDirectory()) {
            try {
              u.oczysc(plik[a].getCanonicalPath().toString(), patt);
              plik[a].delete();
            } catch (IOException ex) {
            }
          } else {
            plik[a].delete();
          }
        }
      }
    }
  }

  public static void oczysc(String fil) {
    File[] plik;
    plik = new File(fil).listFiles();
    for (int a = 0; a < plik.length; a++) {
      if (plik[a].isDirectory()) {
        try {
          u.oczysc(plik[a].getCanonicalPath().toString());
          plik[a].delete();
        } catch (IOException ex) {
        }
      } else {
        plik[a].delete();
      }
    }
  }

  public synchronized static boolean odzipuj(File file) {
    if (file == null || !file.exists() || !file.isFile()) {
      return false;
    }
    boolean fl = false;
    try {
      ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
      new CheckedInputStream(new FileInputStream(file), new Adler32())));
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        String ename = file.getParent() + File.separator + entry.getName();
        File newFile = new File(ename);
        if (entry.isDirectory()) {
          if (!newFile.mkdirs()) {
            break;
          }
          continue;
        }
        File pf = newFile.getParentFile();
        if (!pf.exists()) {
          if (!pf.mkdirs()) {
            break;
          }
        }
        BufferedOutputStream bos = new BufferedOutputStream(
        new FileOutputStream(newFile));
        byte[] data = new byte[8192];
        int c;
        while ((c = zis.read(data)) != -1) {
          bos.write(data, 0, c);
          fl = true;
        }
        bos.flush();
        bos.close();
        zis.closeEntry();
      }
      zis.close();
    } catch (Exception ex) {
    }
    return fl;
  }

  public synchronized static boolean odzipuj(String fil) {
    return odzipuj(new File(fil));
  }

  public synchronized static int hasCodeBinary(byte[] b, int limit) {
    if (b == null) {
      return 0;
    }
    int hasCode = 0;
    for (int i = 0; i < limit; i++) {
      hasCode += b[i];
    }
    return hasCode;
  }

  public static Properties getSystemCfg() {
    return systemCfg;
  }

  private static Properties loadWorkProp() {
    Properties workCfg = new Properties();
    InputStream digo = null;
    try {
      URL url = new URL("file:" + workDir + "work.properties");
      File f = new File(url.getPath());
      if (!f.exists()) {
        boolean fl = false;
        if (webStart) {
          fl = f.createNewFile();
        }
        if (!fl) {
          return workCfg;
        }
      }
      digo = url.openStream();
      workCfg.load(digo);
    } catch (Throwable ex) {
    } finally {
      if (digo != null) {
        try {
          digo.close();
        } catch (IOException ex1) {
        }
      }
    }
    return workCfg;
  }

  private static Properties loadLocalProp() {
    Properties localCfg = new Properties();
    InputStream digo = null;
    try {
      String dircfg = systemCfg.getProperty("localcfg");
      if (dircfg == null) {
        dircfg = "";
      }
      boolean forceCfg = false;
      if (!dircfg.isEmpty()) {
        File d = new File(dircfg);
        if (!d.exists()) {
          if (!d.mkdirs()) {
            return localCfg;
          }
        }
        if (d.isAbsolute()) {
          dircfg += File.separator;
        } else {
          dircfg += "/";
        }
        forceCfg = true;
      }

      URL url = new URL("file:" + dircfg + "local.properties");
      File f = new File(url.getPath());
      if (!f.exists()) {
        boolean fl = false;
        if (forceCfg) {
          fl = f.createNewFile();
        }
        if (!fl) {
          return localCfg;
        }
      }
      digo = url.openStream();
      localCfg.load(digo);
    } catch (Throwable ex) {
    } finally {
      if (digo != null) {
        try {
          digo.close();
        } catch (IOException ex1) {
        }
      }
    }
    return localCfg;
  }

  public static void setDropCfg(boolean b) {
    dropCfg = b;
  }

  public static void setUpdSchema(boolean b) {
    updSchema = b;
  }

  public static void setCacheable(boolean cache) {
    cacheable = cache;
  }

  public static void setDataSystem(Date dataS) {
    dataSystem = dataS;
  }

  public static void setdigDataKontr(Date dataK) {
    digDataKontr = dataK;
  }

  public static Date getdigDataKontr() {
    return digDataKontr;
  }

  public static void setdigRodzKontr(String rodzK) {
    digRodzKontr = rodzK;
  }

  public static String getdigRodzKontr() {
    return digRodzKontr;
  }

  public static void setDataFormat(String dataF) {
    dateFormat = dataF;
  }

  public static void setGrUserId(int grUserI) {
    grUserId = grUserI;
  }

  public static void setRemoteServer(String remoteServer) {
    if (remoteServer == null || remoteServer.isEmpty()) {
      return;
    }
    String[] sp = remoteServer.split(":");
    if (sp.length > 0) {
      u.remoteServer = sp[0];
    }
    if (sp.length > 1) {
      u.remotePort = sp[1];
    }
  }

  public static void setBackupFile(String backupFile) {
    u.backupFile = backupFile;
  }

  public static void setTransDir(String transD) throws Exception {
    if (u.isInstallBin()) {
      return;
    }
    File d = new File(transD);
    if (!d.exists()) {
      if (!d.mkdirs()) {
        throw new Exception("");
      }
    }
    transDir = transD;
  }

  public static String decode(String CodedBufor) {
    String text_rozk = "";
    String klucz
    = "A. 2a-fBF9QzgIdDq4MpVOKLiy6N0ePC8rljRE_3ucYT5txwJnX7ZWHko1mSUGbhsv\"^&%*$(#)@+!=[{;:']},./?><|`~\\";
    int lib_al_n1 = 0;
    int lib_al_n3 = klucz.length();
    String coded = CodedBufor.trim();
    if (coded.length() == 0) {
      return text_rozk;
    }
    try {
      while (lib_al_n1 < coded.length()) {
        int lib_al_n2 = Integer.valueOf(coded.substring(lib_al_n1,
        lib_al_n1 + 2).
        trim());
        if (lib_al_n3 + lib_al_n2 <= klucz.length()) {
          lib_al_n3 += lib_al_n2;
        } else {
          lib_al_n3 = lib_al_n2 - (klucz.length() - lib_al_n3);
        }
        text_rozk += klucz.substring(lib_al_n3 - 1, lib_al_n3);
        lib_al_n1 += 2;
      }
    } catch (NumberFormatException ex) {
      text_rozk = "";
    }
    return text_rozk;
  }

  public static String encode(String CodedBufor) {
    String klucz
    = "A. 2a-fBF9QzgIdDq4MpVOKLiy6N0ePC8rljRE_3ucYT5txwJnX7ZWHko1mSUGbhsv\"^&%*$(#)@+!=[{;:']},./?><|`~\\";
    int lib_al_n1 = CodedBufor.length();
    int lib_al_n2 = 1;
    String text_rozk = "";
    if (lib_al_n1 == 0) {
      return text_rozk;
    }
    String lib_znak = CodedBufor.substring(0, 1);
    int licznik = klucz.indexOf(lib_znak) + 1;
    int lib_al_n3 = licznik;
    Integer tmp_id = licznik;
    if (tmp_id.intValue() < 10) {
      text_rozk += "0";
    }
    text_rozk += tmp_id.toString();
    while (lib_al_n2 < lib_al_n1) {
      lib_znak = CodedBufor.trim().substring(lib_al_n2, lib_al_n2 + 1);
      licznik = klucz.indexOf(lib_znak) + 1;
      if (licznik >= lib_al_n3) {
        tmp_id = licznik - lib_al_n3;
      } else {
        tmp_id = licznik + (klucz.length() - lib_al_n3);
      }
      lib_al_n3 = licznik;
      if (tmp_id.intValue() < 10) {
        text_rozk += "0";
      }
      text_rozk += tmp_id.toString();
      lib_al_n2 += 1;
    }
    return text_rozk;
  }

  public static String justujDoPrawej(String tekst, int len) {
    String wyjustowanyTekst = "";
    tekst = tekst.trim();
    if (tekst.length() > len) {
      wyjustowanyTekst = tekst.substring(0, len);
    } else if (tekst.length() < len) {
      for (int i = 0; i < len - tekst.length(); i++) {
        wyjustowanyTekst += " ";
      }
      wyjustowanyTekst = wyjustowanyTekst + tekst;
    } else {
      return tekst;
    }
    return wyjustowanyTekst;
  }

  public static String justujDoLewej(String tekst, int len) {
    String wyjustowanyTekst = "";
    if (tekst.length() > len) {
      wyjustowanyTekst = tekst.substring(0, len);
    } else if (tekst.length() < len) {
      for (int i = 0; i < len - tekst.length(); i++) {
        wyjustowanyTekst += " ";
      }
      wyjustowanyTekst = tekst + wyjustowanyTekst;
    } else {
      return tekst;
    }
    return wyjustowanyTekst;
  }

  public static String justujDoCentruj(String tekst, int len) {
    return justujDoCentruj(tekst, len, false);
  }

  public static String justujDoCentruj(String tekst, int len,
                                       boolean trimIfGreater) {
    StringBuilder wyjustowanyTekst = new StringBuilder();
    String t = tekst.trim();
    if (t.length() < len) {
      int pom = len - t.length();
      int ofl = pom / 2;
      int ofr = ofl + (pom % 2);
      wyjustowanyTekst.append(replicateString(" ", ofl));
      wyjustowanyTekst.append(t);
      wyjustowanyTekst.append(replicateString(" ", ofr));
    } else if (trimIfGreater) {
      return t.substring(0, len);
    } else {
      return t;
    }
    return wyjustowanyTekst.toString();

  }

  public static void setMaxFieldLength(JTextComponent input, int filterSize) {
    AbstractDocument doc = (AbstractDocument) input.getDocument();
    doc.setDocumentFilter(new SizeFilter(filterSize));
  }

  public static int getOsType() {
    String osNameP = System.getProperty("os.name");
    String osVersion = System.getProperty("os.version");
    String[] osNames = new String[]{"Windows 3.1", "Windows 95", "Windows 98",
      "Windows Me", "Windows NT", "Windows NT (unknown)", "Windows 2000",
      "Windows XP", "Windows Vista", "Windows 7", "UNIX"};
    String osName = ((osNameP.equals(osNames[2]) && osVersion.startsWith("4.9"))
    ? "Windows Me" : osNameP);
    int osLevel = Arrays.asList(osNames).indexOf(osName);
    if (osLevel < 0) {
      return u.OSTYPE_UNIX;
    } else if (osLevel <= 3) {
      return u.OSTYPE_98;
    } else if (osLevel <= 9) {
      return u.OSTYPE_NT;
    } else {
      return u.OSTYPE_UNIX;
    }
  }

  public static List getCommandShell() {
    List l = new ArrayList();
    String osName = System.getProperty("os.name");
    if (osName.equals("Windows 95") || osName.equals("Windows 98")) {
      l.add("command.com");
      l.add("/C");
    } else if (osName.matches(".*Windows.*")) {
      l.add("cmd.exe");
      l.add("/C");
    } else if (osName.matches(".*Linux.*")) {
      l.add("Linux");
    }
    return l;
  }

  public static int execProgram(boolean isWait, String... args) {
    return execProgram(null, isWait, args);
  }

  public static int execProgram(String[] output, boolean isWait, String... args) {

    if (args == null || args.length < 1) {
      return 1;
    }
    try {
      List<String> l = getCommandShell();
      if (l == null || l.isEmpty()) {
        return 1;
      }
      if (l.size() == 1 && l.get(0).equals("Linux")) {
        l.clear();
      }
      l.addAll(Arrays.asList(args));

      ProcessBuilder pb = new ProcessBuilder(l);
      Process proc = pb.start();
      int exitVal = 0;
      if (isWait) {
        StreamGobbler out = null;
        StreamGobbler err = null;
        if (output != null && output.length == 2) {
          out = new StreamGobbler(proc.getInputStream());
          err = new StreamGobbler(proc.getErrorStream());
          out.start();
          err.start();
        }
        exitVal = proc.waitFor();
        if (output != null && output.length == 2) {
          out.join();
          err.join();
          output[0] = out.getResult();
          output[1] = err.getResult();
        }
      } else if (output != null && output.length == 2) {
        output[0] = StreamGobbler.readFromInputStrean(proc.getInputStream());
        output[1] = StreamGobbler.readFromInputStrean(proc.getErrorStream());
      }
      return exitVal;
    } catch (Throwable ex) {
      return 1;
    }
  }

  //funckja dziala dla comboboxow
  //rdId jest wartoscia identyfikatora dla ktorego trzeba ustawic
  //aktualna wartosc wyswietlana w combo
  public static void setComboSelectedIndex(Integer rdId, JComboBox comboBox,
                                           List dataList) {
    if (dataList == null) {
      return;
    }
    int selectedIndex = 0;
    for (int i = 0; i < dataList.size(); i++) {
      int selectedRdId = (Integer) ((Object[]) dataList.get(i))[0];
      if (selectedRdId == rdId) {
        selectedIndex = i;
        break;
      }
    }
    comboBox.setSelectedIndex(selectedIndex);
  }

  public static Icon getSearchButtonIcon() {
    return getImage("search.gif");
  }

  public static Icon getRemoveButtonIcon() {
    return getImage("remove.gif");
  }

  public static ImageIcon getImage(String file) {
    if (file == null) {
      return null;
    }
    ImageIcon icon = null;
    URL url = null;
    try {
      url = getResource("/images/" + file);
      icon = new ImageIcon(url);
    } catch (Exception ex) {
    }
    return icon;
  }

  public static BufferedImage getBufferedImage(String file) {
    if (file == null) {
      return null;
    }
    BufferedImage bufImage = null;
    URL url = null;
    try {
      url = getResource("/images/" + file);
      /*
      if (!isRunning()) {
      url = new URL("file:/D:/java/projekty/uv/src/images/" + file);
      } else {
      //url = new URL(u.getApplicationURL() + "images/" + file);
      url = getResource("/images/" + file);
      }
       */
      bufImage = ImageIO.read(url);
    } catch (Exception ex) {
    }
    return bufImage;
  }

  public static String replicateString(String pattern, int length) {
    String zwrot = "";
    for (int i = 0; i < length; i++) {
      zwrot += pattern;
    }
    return zwrot;
  }

  public static JFrame getRootFrame() {
    return rootFrame;
  }

  public static String getFileExtension(File file) {
    return getFileExtension(file.getAbsolutePath());
  }

  public static String getFileExtension(String path) {
    int rozszerzInd = path.lastIndexOf(".");
    String rozszerzenie = path.substring(rozszerzInd + 1);
    return rozszerzenie;
  }

  public static String getFileName(File file) {
    return getFileName(file.getAbsolutePath());
  }

  public static String getFileName(String path) {
    int pos = path.lastIndexOf(".");
    String name = path;
    if (pos >= 0) {
      name = path.substring(0, pos);
    }
    return name;
  }

  public static String getColorToString(Color color) {
    String zwrot = "#";
    String c = Integer.toHexString(color.getRed());
    if (c.trim().length() == 1) {
      c = "0" + c.trim();
    }
    zwrot += c;
    c = Integer.toHexString(color.getGreen());
    if (c.trim().length() == 1) {
      c = "0" + c.trim();
    }
    zwrot += c;
    c = Integer.toHexString(color.getBlue());
    if (c.trim().length() == 1) {
      c = "0" + c.trim();
    }
    zwrot += c;
    return zwrot;
  }

  public static String getFontToString(Font font) {
    if (font == null) {
      return "";
    }
    String fn = font.getFontName();
    String style = "";
    if (font.isPlain()) {
      style = "PLAIN";
    } else if (font.isBold() && !font.isItalic()) {
      style = "BOLD";
    } else if (!font.isBold() && font.isItalic()) {
      style = "ITALIC";
    } else if (font.isBold() && font.isItalic()) {
      style = "BOLDITALIC";
    }
    String size = String.valueOf(font.getSize());
    return fn + "-" + style + "-" + size;
  }

  public static String getLTrim(String source) {
    if (source == null) {
      return "";
    }
    return source.replaceAll("^\\s+", "");
  }

  public static String getRTrim(String source) {
    if (source == null) {
      return "";
    }
    return source.replaceAll("\\s+$", "");
  }

  public static String getITrim(String source) {
    if (source == null) {
      return "";
    }
    return source.replaceAll("\\b\\s{2,}\\b", " ");
  }

  public static float[] getStringSize(int size, Font font,
                                      FontRenderContext frc) {
    return getStringSize(u.replicateString("W", size), font, frc);
  }

  public static float[] getStringSize(String tekst, Font font,
                                      FontRenderContext frc) {
    Rectangle2D r = font.getStringBounds(tekst, frc);
    return new float[]{(float) r.getWidth(), (float) r.getHeight()};
  }

  public static String replaceIfCourier(String font) {
    if (font == null || font.trim().length() == 0) {
      return "";
    }
    String tabF = font.trim().replaceAll(",", "-");
    String[] t = tabF.split("-");
    if (t.length > 0) {
      if (u.getOsType() == u.OSTYPE_98 && t[0].trim().equalsIgnoreCase(
      "Courier New")) {
        tabF = "Courier-" + t[1] + "-" + t[2];
      } else if (u.getOsType() == u.OSTYPE_NT && t[0].trim().equalsIgnoreCase(
      "Courier")) {
        tabF = "Courier New-" + t[1] + "-" + t[2];
      }
    }
    return tabF;
  }

  public synchronized static byte[] copyOfByte(byte[] original, int newLength) {
    byte[] copy = new byte[newLength];
    System.arraycopy(original, 0, copy, 0,
    Math.min(original.length, newLength));
    return copy;
  }

  public static URL getApplicationURL() {
    return applicationURL;
  }

  public static Integer getSprawa() {
    return sprawa;
  }

  public static String getWorkDir() {
    return workDir;
  }

  public static int getApplicationVersion() {
    return applicationVersion;
  }

  public static int getApplicationOldVersion() {
    return applicationOldVersion;
  }

  public static void setApplicationURL() throws Exception {
    if (applicationURL != null) {
      return;
    }
    CodeSource source = u.class.getProtectionDomain().getCodeSource();
    URL location = source.getLocation();
    if (location == null) {
      String p = System.getProperty("uv.start");
      applicationURL = new URL(p);
      if (p.startsWith("jar")) {
        jar = true;
      }
    } else {
      applicationURL = location;
      String pr = applicationURL.getProtocol();
      File f = new File(applicationURL.getFile());
      if (f.isFile() || (pr != null && pr.startsWith("http"))) {
        jar = true;
        if (pr != null && pr.startsWith("http")) {
          webStart = true;
          systemCfg.put("remoteserver", applicationURL.getHost());
        }
        applicationURL = new URL("jar:" + applicationURL + "!/");
      }
    }
  }

  public static <T> T cloneObject(T object) throws Exception {
    ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(bout);
      out.writeObject(object);
    } catch (IOException ex) {
      throw new Exception("could not serialize", ex);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException ignored) {
      }
    }
    if (bout == null) {
      throw new Exception("The InputStream must not be null");
    }
    ByteArrayInputStream bais = new ByteArrayInputStream(bout.toByteArray());
    ObjectInputStream in = null;
    try {
      in = new ObjectInputStream(bais);
      return (T) in.readObject();
    } catch (Exception ex) {
      throw new Exception("could not deserialize", ex);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
      }
    }
  }

  public static synchronized boolean channelCopy(String path, File target) {
    long size = -1;
    URL r = getResource(path);
    if (r == null) {
      return false;
    }
    String pr = r.getProtocol();
    if (pr.equalsIgnoreCase("file")) {
      try {
        size = new File(r.toURI()).length();
      } catch (Exception ex) {
      }
    } else if (pr.equalsIgnoreCase("jar")) {
      try {
        JarURLConnection jurl = (JarURLConnection) getApplicationURL().
        openConnection();
        JarFile jarFile = jurl.getJarFile();
        JarEntry je = jarFile.getJarEntry(path);
        if (je != null) {
          size = je.getSize();
        }
      } catch (Exception ex) {
      }
    } else {
      return false;
    }
    if (size <= 0) {
      return false;
    }
    boolean fl = false;
    try {
      fl = channelCopy(r.openStream(), size, target);
    } catch (Exception ex) {
    }
    return fl;
  }

  public static synchronized boolean channelCopy(File source, File target) {
    InputStream in = null;
    try {
      in = new FileInputStream(source);
      return channelCopy(in, source.length(), target);
    } catch (Exception ex) {
      return false;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException ex2) {
        }
      }
    }

  }

  public static synchronized boolean channelCopy(InputStream source,
                                                 long sizeSource, File target) {
    ReadableByteChannel in = null;
    FileChannel out = null;
    try {
      in = Channels.newChannel(source);
      out = new FileOutputStream(target).getChannel();
      out.transferFrom(in, 0, sizeSource);
    } catch (Exception ex) {
      return false;
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException ex2) {
        }
      }
      if (in != null) {
        try {
          in.close();
        } catch (IOException ex2) {
        }
      }
    }
    return true;
  }

  public static void setRootFrame(JFrame frame) {
    if (frame == null) {
      rootFrame = new JFrame();
    } else {
      rootFrame = frame;
    }
  }

  public static void setSprawa(Integer spraw) {
    sprawa = spraw;
  }

  public static void setWorkDir(String workD) {
    workDir = workD;
  }

  public static void setApplicationVersion(int applicationVer) {
    applicationVersion = applicationVer;
  }

  public static void setApplicationOldVersion(int applicationOldVer) {
    applicationOldVersion = applicationOldVer;
  }

  public static void setXmlCfgBase(String xmlCfgBaseS, boolean xmlFromArg)
  throws
  MalformedURLException {
    if (xmlFromArg) {
      xmlCfgBase = new URL("file:" + xmlCfgBaseS);
    } else {
      xmlCfgBase = new URL(u.getApplicationURL() + xmlCfgBaseS);
    }
  }

  public static boolean isOneInstance() {
    try {
      File file = new File(workDir + "file.lock");
      if (!file.exists()) {
        if (!file.createNewFile()) {
          return false;
        }
      }
      FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
      FileLock lock = channel.tryLock();
      if (lock == null) {
        return false;
      }
      return true;
    } catch (FileNotFoundException ex) {
    } catch (IOException ex) {
    }
    return false;
  }

  public synchronized static long getChecksumFile(File fname) {
    if (fname == null || !fname.exists()) {
      return 0;
    }
    Checksum checksum = new Adler32();
    BufferedInputStream is = null;
    try {
      is = new BufferedInputStream(new FileInputStream(fname));
      byte[] bytes = new byte[4096];
      int len = 0;
      while ((len = is.read(bytes)) >= 0) {
        checksum.update(bytes, 0, len);
      }
    } catch (Exception e) {
      return 0;
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException ex) {
        }
        is = null;
      }
    }
    return checksum.getValue();
  }

  public static Icon getStatusIcon(final Color color) {
    Icon i = new Icon() {

      public void paintIcon(Component c, Graphics g, int x1, int y1) {
        int h = 15;
        int w = 15;
        Color oldColor = g.getColor();
        Color brighter = color.brighter();
        Color darker = color.darker();
        Color bbrighter = brighter.brighter();
        Color ddarker = darker.darker();
        g.translate(x1, y1);
        g.setColor(color);
        g.fillRect(2, 2, w - 2, h - 2);

        g.setColor(bbrighter);
        g.drawLine(0, 0, 0, h - 2);
        g.drawLine(1, 0, w - 2, 0);

        g.setColor(brighter);
        g.drawLine(1, 1, 1, h - 3);
        g.drawLine(2, 1, w - 3, 1);

        g.setColor(ddarker);
        g.drawLine(0, h - 1, w - 1, h - 1);
        g.drawLine(w - 1, 0, w - 1, h - 2);

        g.setColor(darker);
        g.drawLine(1, h - 2, w - 2, h - 2);
        g.drawLine(w - 2, 1, w - 2, h - 3);
        g.translate(-x1, -y1);
        g.setColor(oldColor);
      }

      public int getIconWidth() {
        return 15;
      }

      public int getIconHeight() {
        return 15;
      }

    };
    return i;
  }

  public static FontRenderContext getFontRenderContext(Component c) {
    if (c == null) {
      return new FontRenderContext(null, false, false);
    } else {
      return c.getFontMetrics(c.getFont()).getFontRenderContext();
    }
  }

  public static Set<String> getSupportedImageFormats() {
    if (supportedImageFormats.isEmpty()) {
      String names[] = ImageIO.getReaderFormatNames();
      for (int i = 0; i < names.length; ++i) {
        supportedImageFormats.add(names[i]);
      }
    }
    return supportedImageFormats;
  }

  public static void setPrivateValue(Object object, String fieldName,
                                     Object value) {
    setPrivateValue(object, object.getClass(), fieldName, value);
  }

  public static void setPrivateValue(Object object, Class c, String fieldName,
                                     Object value) {
    try {
      Field field = c.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(object, value);
    } catch (Exception ex) {
    }

  }

  public static Object[] getPrivateValue(Object object, String[] fieldNames) {
    return getPrivateValue(object, object.getClass(), fieldNames);
  }

  public static Object[] getPrivateValue(Object object, Class c,
                                         String[] fieldNames) {
    Object[] zwrot = new Object[fieldNames.length];
    Field field;
    String fieldName;
    try {
      for (int i = 0; i < fieldNames.length; i++) {
        fieldName = fieldNames[i];
        field = c.getDeclaredField(fieldName);
        if (field != null) {
          field.setAccessible(true);
          zwrot[i] = field.get(object);
        }
      }
    } catch (Exception ex) {
      return null;
    }
    return zwrot;

  }

  public static Object getPrivateValue(Object object, String fieldName) {
    return getPrivateValue(object, object.getClass(), fieldName);
  }

  public static Object getPrivateValue(Object object, Class c, String fieldName) {
    Object zwrot = null;
    Field field;
    try {
      field = c.getDeclaredField(fieldName);
      if (field != null) {
        field.setAccessible(true);
        zwrot = field.get(object);
      }
    } catch (Exception ex) {
      return null;
    }
    return zwrot;
  }

  public static void putPrivateValue(Object object, Class c, String fieldName,
                                     Object value) {
    Field field;
    try {
      field = c.getDeclaredField(fieldName);
      if (field != null) {
        field.setAccessible(true);
        field.set(object, value);
      }
    } catch (Exception ex) {
    }
  }

  public static void setGroupEnabled(Container c, boolean enabled) {
    setGroupEnabled(c, enabled, true);
  }

  public static void setGroupEnabled(Container c, boolean enabled,
                                     boolean withRoot) {
    if (withRoot) {
      if (c instanceof JTextComponent) {
        ((JTextComponent) c).setEditable(enabled);
      } else if (!(c instanceof JLabel)) {
        c.setEnabled(enabled);
      }

    }
    int count = c.getComponentCount();
    Component component;
    for (int i = 0; i < count; i++) {
      component = c.getComponent(i);
      if (component instanceof Container) {
        setGroupEnabled((Container) component, enabled);
      } else {
        component.setEnabled(enabled);
      }
    }
    if (c instanceof JMenu) {
      int mc = ((JMenu) c).getMenuComponentCount();
      for (int i = 0; i < mc; i++) {
        component = ((JMenu) c).getMenuComponent(i);
        if (component instanceof Container) {
          setGroupEnabled((Container) component, enabled);
        } else {
          component.setEnabled(enabled);
        }
      }
    }
  }

  public static Date maxDate(Date date1, Date date2) {
    if (date1 == null && date2 == null) {
      return null;
    } else if (date1 == null) {
      return date2;
    } else if (date2 == null) {
      return date1;
    }
    int c = date1.compareTo(date2);
    if (c < 0) {
      return date2;
    } else {
      return date1;
    }
  }

  public static int compareToWithTime(Date date1, Date date2) {
    return compareTo(date1, date2, true);
  }

  public static int compareTo(Date date1, Date date2) {
    return compareTo(date1, date2, false);
  }

  private static int compareTo(Date date1, Date date2, boolean withTime) {
    if (date1 == null && date2 == null) {
      return 0;
    } else if (date1 == null) {
      return -1;
    } else if (date2 == null) {
      return 1;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(date1);
    if (!withTime) {
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
    }
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    Calendar c1 = Calendar.getInstance();
    c1.setTime(date2);
    if (!withTime) {
      c1.set(Calendar.HOUR_OF_DAY, 0);
      c1.set(Calendar.MINUTE, 0);
    }
    c1.set(Calendar.SECOND, 0);
    c1.set(Calendar.MILLISECOND, 0);
    return c.compareTo(c1);
  }

  public static Object convertToString(Object o) {
    return convertToString(o, false);
  }

  public static Object convertToString(Object o, boolean isTrim) {
    Object v;
    if (o instanceof Object[]) {
      Object[] ob = (Object[]) o;
      String[] zwrot = new String[ob.length];
      for (int i = 0; i < ob.length; i++) {
        v = ob[i];
        if (v == null) {
          zwrot[i] = "";
        } else if (v instanceof Date) {
          zwrot[i] = u.getStringFromDate((Date) v);
        } else {
          zwrot[i] = String.valueOf(v);
        }
        if (isTrim) {
          zwrot[i] = zwrot[i].trim();
        }

      }
      return zwrot;
    } else {
      String zwrot;
      if (o == null) {
        zwrot = "";
      } else if (o instanceof Date) {
        zwrot = u.getStringFromDate((Date) o);
      } else {
        zwrot = String.valueOf(o);
      }
      if (isTrim) {
        zwrot = zwrot.trim();
      }
      return zwrot;
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

  public static void execGui(Runnable cmd) {
    if (SwingUtilities.isEventDispatchThread()) {
      cmd.run();
    } else {
      SwingUtilities.invokeLater(cmd);
    }
  }

  public static void execGuiWait(Runnable cmd) {
    if (SwingUtilities.isEventDispatchThread()) {
      cmd.run();
    } else {
      try {
        SwingUtilities.invokeAndWait(cmd);
      } catch (Throwable ex) {
      }
    }
  }

  public static URL getResource(String name) {
    URL url = u.class.getResource(name);
    if (url == null) {
      if (name.startsWith("/")) {
        name = name.substring(1);
      }
      try {
        url = new URL(applicationURL, name);
      } catch (MalformedURLException malformedURLException) {
      }
    }
    return url;
  }

  public static boolean isExistURL(URL url) {
    if (url == null) {
      return false;
    }
    InputStream st = null;
    try {
      st = url.openStream();
      if (st != null) {
        return true;
      }
    } catch (Exception ex) {
    } finally {
      if (st != null) {
        try {
          st.close();
        } catch (IOException iOException) {
        }
      }
    }
    return false;
  }

  public static boolean equals(Object f1, Object f2) {
    if (f1 instanceof String && f2 instanceof String) {
      return !((f1 == null) ? (f2 != null) : !f1.equals(f2));
    } else if (f1 instanceof Integer && f2 instanceof Integer) {
      return !(f1 != f2 && (f1 == null || !f1.equals(f2)));
    } else if (f1 == null && f2 == null) {
      return true;
    } else if (f1 == null || f2 == null) {
      return false;
    } else {
      return f1.equals(f2);
    }
  }

  public static String findMac() throws Throwable {
    StringBuilder sb = new StringBuilder();
    Enumeration<NetworkInterface> nn = NetworkInterface.getNetworkInterfaces();
    InetAddress ie = InetAddress.getByName(remoteServer);
    boolean isLo = remoteServer.toLowerCase().startsWith("lo");
    while (nn.hasMoreElements()) {
      NetworkInterface ne = nn.nextElement();
      if (isLo) {
        if (!ne.isLoopback()) {
          continue;
        }
        sb.append("FF-FF-FF-FF-FF-FF");
        break;
      }
      boolean isPing = ie.isReachable(ne, 0, 2000);
      if (!isPing) {
        continue;
      }
      List<InterfaceAddress> il = ne.getInterfaceAddresses();
      if (il == null || il.isEmpty()) {
        continue;
      }
      byte[] mac = ne.getHardwareAddress();
      if (mac == null) {
        continue;
      }
      for (int i = 0; i < mac.length; i++) {
        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-"
        : ""));
      }
      break;
    }
    return sb.toString();
  }

  public static String getMotherboardSN() {
    String zwrot = "";
    File file = null;
    FileWriter fw = null;
    try {
      String[] args = null;
      if (getOsType() == OSTYPE_UNIX) {
        args
        = new String[]{"sudo", "dmidecode", "-s", "baseboard-serial-number"};
      } else {
        file = File.createTempFile("realhowto", ".vbs");
//        args = new String[]{"c:/windows/system32/cscript", "//NoLogo", file.getPath()};
        args = new String[]{"cscript", "//NoLogo", file.getPath()};
        fw = new FileWriter(file);
        String vbs
        = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
        + "Set colItems = objWMIService.ExecQuery _ \n"
        + "   (\"Select * from Win32_BaseBoard\") \n"
        + "For Each objItem in colItems \n"
        + "    Wscript.Echo objItem.SerialNumber \n"
        + "    exit for  ' do the first cpu only! \n"
        + "Next \n";
        fw.write(vbs);
        fw.close();
      }
      String[] out = new String[]{"", ""};
      int w = execProgram(out, false, args);
      if (w == 0 && out[0] != null) {
        zwrot = out[0];
      }
    } catch (Exception e) {
    } finally {
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException iOException) {
        }
      }
      if (file != null) {
        boolean delete = file.delete();
        if (!delete) {
          file.deleteOnExit();
        }
      }
    }
    return zwrot.trim();
  }

  public static Date getDateMinSec(Date d) {
    if (d == null) {
      return d;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    return c.getTime();
  }

  public static Date getDateMaxSec(Date d) {
    if (d == null) {
      return d;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    c.set(Calendar.SECOND, 59);
    c.set(Calendar.MILLISECOND, 999);
    return c.getTime();
  }

  public static void formCol(JTable jt, int col, int width) {
    jt.getColumnModel().getColumn(col).setPreferredWidth(width);
    jt.getColumnModel().getColumn(col).setMinWidth(width);
    jt.getColumnModel().getColumn(col).setWidth(width);
    jt.getColumnModel().getColumn(col).setMaxWidth(width);
  }

  public static void nagraj(JTable org, JTable target) {
    for (int i = 0; i < org.getRowCount(); i++) {
      for (int j = 0; j < org.getColumnCount(); j++) {
        target.setValueAt(org.getValueAt(i, j), i, j);
      }
    }
  }

  public static synchronized String extractText(String html) {
    if (html == null) {
      return null;
    }

    if (parser == null) {
      parser = new HTMLParserCallback();
    }

    try {
      StringReader in = new StringReader(html);

      try {
        return parser.parse(in);
      } finally {
        in.close();
      }
    } catch (Exception ex) {
      logger.info("Failed to extract plain text from html=" + html, ex);
    }
    return html;
  }

} //koniec klasy u

class StreamGobbler extends Thread {

  private InputStream is;
  private String result;
  public StreamGobbler(InputStream is) {
    this.is = is;
  }

  public void run() {
    result = readFromInputStrean(is);
  }

  static String readFromInputStrean(InputStream is) {
    StringBuilder buffer = new StringBuilder();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      String line = null;
      while ((line = br.readLine()) != null) {
        if (buffer.length() > 0) {
          buffer.append("\n");
        }
        buffer.append(line);
      }
    } catch (Exception ex) {
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (Exception ex) {
        }
      }
      return buffer.toString();
    }
  }

  public String getResult() {
    return result;
  }

}

class filter
implements FilenameFilter {

  Pattern patt;
  public filter(String suf) {
    //patt = Pattern.compile(".*" + suf + "$");
    patt = Pattern.compile(".*" + suf + ".*");
  }

  public boolean accept(File dir, String name) {
    return patt.matcher(name).matches();
  }

}

class SizeFilter
extends DocumentFilter {

  int maxSize;  // limit is the maximum number of characters allowed.
  public SizeFilter(int limit) {
    maxSize = limit;
  }

  // This method is called when characters are inserted into the document
  public void insertString(DocumentFilter.FilterBypass fb, int offset,
                           String str,
                           AttributeSet attr) throws BadLocationException {
    replace(fb, offset, 0, str, attr);
  }

  // This method is called when characters in the document are replace with other characters
  public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                      String str, AttributeSet attrs) throws
  BadLocationException {
    int newLength = fb.getDocument().getLength() - length + str.length();
    if (newLength <= maxSize) {
      fb.replace(offset, length, str, attrs);
    } else {
      throw new BadLocationException(
      "New characters exceeds max size of document",
      offset);
    }
  }

}

class HTMLParserCallback extends HTMLEditorKit.ParserCallback {

  /**
   * The &lt;tt&gt;StringBuilder&lt;/tt&gt; which accumulates the parsed text
   * while it is being parsed.
   */
  private StringBuilder sb;

  /**
   * Parses the text contained in the given reader.
   *
   * @param in the reader to parse.
   * @return the parsed text
   * @throws IOException thrown if we fail to parse the reader.
   */
  public String parse(Reader in)
  throws IOException {
    sb = new StringBuilder();

    String s;

    try {
      new ParserDelegator().parse(in, this,
      /* ignoreCharSet */ true);
      s = sb.toString();
    } finally {
      /*
                 * Since the Html2Text class keeps this instance in a static
                 * reference, the field sb should be reset to null as soon as
                 * completing its goad in order to avoid keeping the parsed
                 * text in memory after it is no longer needed i.e. to prevent
                 * a memory leak. This method has been converted to return the
                 * parsed string instead of having a separate getter method for
                 * the parsed string for the same purpose.
       */
      sb = null;
    }
    return s;
  }

  /**
   * Appends the given text to the string buffer.
   *
   * @param text the text of a text node which has been parsed from the
   * specified HTML
   * @param pos the zero-based position of the specified
   * &lt;tt&gt;text&lt;/tt&gt; in the specified HTML
   */
  @Override
  public void handleText(char[] text, int pos) {
    sb.append(text);
    sb.append(" "); // po znaczniku ³¹czy wyrazy
  }

}
