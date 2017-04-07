package uKontroler;

//import common.u;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;
import javax.swing.text.TextAction;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class DigTextField extends JFormattedTextField implements FocusListener {

  int format = -1;
  int maxLen = -1;
  /**
   * @see #setFormat
   */
  public static final int VAL_STRING = 1;
  /**
   * @see #setFormat
   */
  public static final int VAL_INT = 2;
  /**
   * @see #setFormat
   */
  public static final int VAL_DATA = 3;
  /**
   * @see #setFormat
   */
  public static final int VAL_KWOTA = 4;
  /**
   * @see #setFormat
   */
  public static final int VAL_STRING_RIGHT = 5;
  public static final int VAL_DATA_TIME = 6;
  public static final int VAL_DECIMAL4 = 7;
  private int columnEdit = 0;
  private boolean valueDataAccept = false;
  private Object valueDataObject;
  private boolean firedChange = false;
  private Object firedChangeOldValue = null;
  private Object firedChangeNewValue = null;
//  private DigPopupMenu dpm = new DigPopupMenu(this);
  private boolean actionFromPopUp = false;
  private boolean edited;
  private boolean upperCase = false;
  public DigTextField() {
    this(-1, 10);
  }

  public DigTextField(int format) {
    this(format, 10);
  }

  public DigTextField(int format, int columns) {
    super();
    this.format = format;
    if (format < 0) {
      super.setValue("");
    }
//    setComponentPopupMenu(dpm);
    addMouseListener(new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent e) {
        digTextFieldMousePressed(e);
      }

    });
    addKeyListener(new KeyAdapter() {

      @Override
      public void keyTyped(KeyEvent e) {
        digTextFieldKeyTyped(e);
      }

    });
    addPropertyChangeListener(new PropertyChangeListener() {

      public void propertyChange(PropertyChangeEvent evt) {
        String pr = evt.getPropertyName();
        if (pr.equalsIgnoreCase("enabled")) {
          boolean fl = (Boolean) evt.getNewValue();
          if (fl) {
//            setComponentPopupMenu(dpm);
          } else {
            setComponentPopupMenu(null);
          }
        }
      }

    });
    setColumns(columns);
    addFocusListener(this);
    setVerifyInputWhenFocusTarget(true);

    Document doc = getDocument();
    doc.addDocumentListener(new DocumentListener() {

      public void insertUpdate(DocumentEvent e) {
        setEdited(true);
      }

      public void removeUpdate(DocumentEvent e) {
        setEdited(true);
      }

      public void changedUpdate(DocumentEvent e) {
      }

    });
    UndoManager manager = new UndoManager();
    doc.addUndoableEditListener(manager);
    Action undoAction = new UndoActionTF(manager);
    Action redoAction = new RedoActionTF(manager);
    registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(
    KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
    registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(
    KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
//    dpm.getUndo().addActionListener(undoAction);
    ActionMap am = this.getActionMap();
    am.put("reset-field-edit", new CancelDigAction());
    setFormatuj();
  }

  class CancelDigAction extends TextAction {

    public CancelDigAction() {
      super("reset-field-edit");
    }

    public boolean isEnabled() {
      JTextComponent target = getFocusedComponent();
      if (target instanceof DigTextField) {
        DigTextField ftf = (DigTextField) target;
        if (!ftf.isEdited()) {
          return false;
        }
        return true;
      }
      return super.isEnabled();
    }

    public void actionPerformed(ActionEvent e) {
      JTextComponent target = getFocusedComponent();
      if (target instanceof DigTextField) {
        DigTextField ftf = (DigTextField) target;
        String oldString = ftf.getText();
        ftf.setValue(ftf.getValue());
        AbstractFormatter f = ftf.getFormatter();
        boolean fl = true;
        Object oldValue = null;
        Object newValue = null;
        try {
          oldValue = f.stringToValue(oldString);
        } catch (ParseException ex) {
          fl = false;
        }
        if (!fl) {
          return;
        }
        try {
          newValue = f.stringToValue(ftf.getText());
        } catch (ParseException ex) {
          fl = false;
        }
        if (fl && (ftf.hasFocus() || ftf.isActionFromPopUp()) && oldValue
        != newValue) {
          ftf.firePropertyChange("changed", oldValue, newValue);
        }
      }
    }

  }

  protected int getColumnWidth() {
    FontMetrics f = getFontMetrics(getFont());
    if (format == VAL_STRING || format == VAL_STRING_RIGHT) {
      return getFontMetrics(getFont()).charWidth('B');
    }
    return getFontMetrics(getFont()).charWidth('0');
  }

  public void setColumns(int columns) {
    maxLen = columns;
    if (columnEdit == 0) {
      super.setColumns(columns);
    }
  }

  public void setColumnsEdit(int columns) {
    super.setColumns(columns);
    columnEdit = columns;
  }

  public void firePropertyChange(String propertyName, Object oldValue,
                                 Object newValue) {
    super.firePropertyChange(propertyName, oldValue, newValue);
  }

  //public
  private void setFormatuj() {
    switch (getFormat()) {
      case VAL_STRING:
        DefaultFormatter dd = new DefaultFormatter() {

          protected DocumentFilter getDocumentFilter() {
            return new DigTextFieldFDoc();
          }

        };
        dd.setValueClass(String.class);
        dd.setCommitsOnValidEdit(true);
        dd.setOverwriteMode(false);
        DefaultFormatterFactory df_s = new DefaultFormatterFactory(dd, dd,
        dd);
        setFormatterFactory(df_s);
        setHorizontalAlignment(DigTextField.LEFT);
        break;
      case VAL_STRING_RIGHT:
        DefaultFormatter dd2 = new DefaultFormatter() {

          protected DocumentFilter getDocumentFilter() {
            return new DigTextFieldFDoc();
          }

          public Object stringToValue(String text) throws ParseException {
            String s = (String) super.stringToValue(text);
            if (s != null) {
              s = justujDoPrawej(s.trim(), getColumns());
            }
            return s;
          }

          public String valueToString(Object value) throws ParseException {
            String zwrot = (String) value;
            if (zwrot != null) {
              zwrot = zwrot.trim();
            }
            return super.valueToString(zwrot);
          }

        };
        dd2.setValueClass(String.class);
        dd2.setCommitsOnValidEdit(true);
        dd2.setOverwriteMode(false);
        DefaultFormatter dd1 = new DefaultFormatter() {

          protected DocumentFilter getDocumentFilter() {
            return new DigTextFieldFDoc();
          }

        };
        dd1.setValueClass(String.class);
        dd1.setCommitsOnValidEdit(true);
        dd1.setOverwriteMode(false);
        DefaultFormatterFactory df_sr = new DefaultFormatterFactory(dd1, dd1,
        dd2);
        setFormatterFactory(df_sr);
        setHorizontalAlignment(DigTextField.RIGHT);
        break;
      case VAL_INT:
        Object vi = getValue();
        if (vi == null || vi.equals("")) {
          super.setValue(Integer.valueOf(0));
        }
        setHorizontalAlignment(DigTextField.RIGHT);
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false);
        NumberFormatter nff = new NumberFormatter(nf) {

          protected DocumentFilter getDocumentFilter() {
            return new DigTextFieldFDoc();
          }

          public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().length() == 0) {
              return Integer.valueOf(0);
            } else {
              return super.stringToValue(text);
            }
          }

          public String valueToString(Object value) throws ParseException {
            if (value == null) {
              return "0";
            } else {
              return super.valueToString(value);
            }
          }

        };
        nff.setMinimum(-100);
        nff.setValueClass(Integer.class);
        nff.setCommitsOnValidEdit(true);
        DefaultFormatterFactory df_i = new DefaultFormatterFactory(nff, nff,
        nff);
        setFormatterFactory(df_i);
        setInputVerifier(new InputVerifier() {

          public boolean verify(JComponent input) {
            JFormattedTextField textField = (JFormattedTextField) input;
            String content = textField.getText();
            if (content == null || content.trim().length() == 0) {
              textField.setValue(new Integer(0));
            }
            return true;
          }

        });
        break;
      case VAL_DECIMAL4:
        Object vid = getValue();
        if (vid == null || vid.equals("")) {
          super.setValue(BigDecimal.ZERO);
        }
        setHorizontalAlignment(DigTextField.RIGHT);
        DecimalFormatSymbols fs = new DecimalFormatSymbols(Locale.getDefault());
        fs.setDecimalSeparator('.');
        NumberFormat nfdec = new DecimalFormat("00.00", fs);
        //nfdec.setGroupingUsed(false);
        NumberFormatter nffdec = new NumberFormatter(nfdec) {

          protected DocumentFilter getDocumentFilter() {
            return new DigTextFieldFDoc();
          }

          public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().length() == 0) {
              return BigDecimal.ZERO;
            } else {
              return super.stringToValue(text);
            }
          }

          public String valueToString(Object value) throws ParseException {
            if (value == null) {
              return "0";
            } else {
              return super.valueToString(value);
            }
          }

        };
        //nffdec.setMinimum(-100);
        nffdec.setValueClass(BigDecimal.class);
        nffdec.setCommitsOnValidEdit(true);
        DefaultFormatterFactory df_idec = new DefaultFormatterFactory(nffdec,
        nffdec, nffdec);
        setFormatterFactory(df_idec);
        setInputVerifier(new InputVerifier() {

          public boolean verify(JComponent input) {
            JFormattedTextField textField = (JFormattedTextField) input;
            String content = textField.getText();
            if (content == null || content.trim().length() == 0) {
              textField.setValue(BigDecimal.ZERO);
            }
            return true;
          }

        });
        break;
      case VAL_DATA:
        Object v = getValue();
        if (v != null && v.equals("")) {
          super.setValue(null);
        }
        setFont(new Font("monospaced", Font.PLAIN, getFont().getSize()));
        setInputVerifier(new InputVerifier() {

          public boolean verify(JComponent input) {
            boolean returnValue = true;
            JFormattedTextField textField = (JFormattedTextField) input;
            DateFormatter fmt = (DateFormatter) textField.getFormatter();
            if (textField.getText().trim().length() == 0) {
              textField.setValue(null);
            } else {
              try {
                Object v = fmt.stringToValue(textField.getText());
                textField.setValue(v);
              } catch (ParseException ex) {
                returnValue = false;
              }
            }
            return returnValue;
          }

        });
        break;
      case VAL_DATA_TIME:
        Object v2 = getValue();
        if (v2 != null && v2.equals("")) {
          super.setValue(null);
        }
        setFont(new Font("monospaced", Font.PLAIN, getFont().getSize()));
        setInputVerifier(new InputVerifier() {

          public boolean verify(JComponent input) {
            boolean returnValue = true;
            JFormattedTextField textField = (JFormattedTextField) input;
            DateFormatter fmt = (DateFormatter) textField.getFormatter();
            if (textField.getText().trim().length() == 0) {
              textField.setValue(null);
            } else {
              try {
                Object v = fmt.stringToValue(textField.getText());
                textField.setValue(v);
              } catch (ParseException ex) {
                returnValue = false;
              }
            }
            return returnValue;
          }

        });
        break;
    }
  }

  public static boolean Weryfikacja(JComponent[][] components) {
    boolean fl = true;
    for (int i = 0; i < components.length; i++) {
      for (int ii = 0; ii < components[i].length; ii++) {
        if (components[i][ii] instanceof DigTextField) {
          DigTextField input = (DigTextField) components[i][ii];
          if (!input.isEditValid()) {
            fl = false;
            break;
          }
        }
      }
      if (!fl) {
        break;
      }
    }
    return fl;
  }

  public static boolean Weryfikacja(JComponent component) {
    boolean fl = true;
    if (component instanceof DigTextField) {
      DigTextField input = (DigTextField) component;
      if (!input.isEditValid()) {
        fl = false;
      }
    }
    return fl;
  }

  public void setValue(Object value) {
    if (format < 0) {
      if (value == null) {
        value = "";
      }
      super.setValue(value);
      if (value instanceof String) {
        setFormat(VAL_STRING);
      } else if (value instanceof Date) {
        setFormat(VAL_DATA);
      } else if (value instanceof Timestamp) {
        setFormat(VAL_DATA_TIME);
      } else if (value instanceof Integer || value instanceof Short) {
        setFormat(VAL_INT);
      } else if (value instanceof BigDecimal) {
        setFormat(VAL_DECIMAL4);
      }
    } else if (format == VAL_STRING_RIGHT && value != null) {
      String s = (String) value;
      super.setValue(justujDoPrawej(s.trim(), getColumns()));
    } else {
      super.setValue(value);
    }
    setEdited(false);
    valueDataAccept = false;
  }

  public int getFormat() {
    return format;
  }

  public boolean isEdited() {
    return edited;
  }

  public boolean isActionFromPopUp() {
    return actionFromPopUp;
  }

  public boolean isUpperCase() {
    return upperCase;
  }

  /**
   * Sets the format when focus is lost. This will be one of
   * <code>DigTextField.VAL_INT</code>, <code>DigTextField.VAL_STRING</code>,
   * <code>DigTextField.VAL_STRING_RIGHT</code> or
   * <code>DigTextField.VAL_DATA</code> <code>DigTextField.VAL_KWOTA</code> Note
   * that some <code>AbstractFormatter</code>s may push changes as they occur,
   * so that the value of this will have no effect.
   * <p>
   * This will throw an <code>IllegalArgumentException</code> if the object
   * passed in is not one of the afore mentioned values.
   * <p>
   * The default value of this property is <code>DigTextField.VAL_STRING</code>.
   *
   * @param format
   * @beaninfo preferred: true bound: true enum: VAL_STRING
   * DigTextField.VAL_STRING VAL_STRING_RIGHT DigTextField.VAL_STRING_RIGHT
   * VAL_DATA DigTextField.VAL_DATA VAL_KWOTA DigTextField.VAL_KWOTA VAL_INT
   * DigTextField.VAL_INT description: Format when component loses focus
   */
  public void setFormat(int format) {
    this.format = format;
    setFormatuj();
  }

  public void setEdited(boolean edited) {
    this.edited = edited;
  }

  public void setActionFromPopUp(boolean actionFromPopUp) {
    this.actionFromPopUp = actionFromPopUp;
  }

  public void focusGained(FocusEvent e) {
    if (this.isEditable() && this.isRequestFocusEnabled()) {
      Runnable doSelect = new Runnable() {

        public void run() {
          DigTextField.this.selectAll();
        }

      };
      SwingUtilities.invokeLater(doSelect);
    }
    this.setRequestFocusEnabled(true);
  }

  public void focusLost(FocusEvent e) {
  }

  class DigTextFieldFDoc extends DocumentFilter {

    public void insertString(FilterBypass fb, int offset, String str,
                             AttributeSet a) throws BadLocationException {
      replace(fb, offset, 0, str, a);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                        String str, AttributeSet attrs) throws
    BadLocationException {
      int lenDoc = fb.getDocument().getLength();
      String oldString = fb.getDocument().getText(0, lenDoc);
      String prevString = oldString.substring(0, offset);
      String nextString = oldString.substring(offset + length);
      int newLength = lenDoc - length + str.length();
      String newString = str;
      if (newLength > maxLen) {
        if (maxLen == lenDoc - length) {
          return;
        }
        int l = maxLen - lenDoc + length;
        if (l < 0) {
          l = 0;
        }
        newString = str.substring(0, l);
      }
      try {
        firedChange = false;
        firedChangeOldValue = null;
        firedChangeNewValue = null;
        newString = makeFireChange(oldString, newString,
        prevString + newString + nextString, true,
        offset);
        fb.replace(offset, length, newString, attrs);
        if (firedChange) {
          firePropertyChange("changed", firedChangeOldValue,
          firedChangeNewValue);
        }
      } catch (Exception ex) {
      }
    }

    public void remove(FilterBypass fb, int offset, int length) throws
    BadLocationException {
      int lenDoc = fb.getDocument().getLength();
      String oldString = fb.getDocument().getText(0, lenDoc);
      fb.remove(offset, length);
      try {
        firedChange = false;
        firedChangeOldValue = null;
        firedChangeNewValue = null;
        makeFireChange(oldString, "",
        fb.getDocument().getText(0, fb.getDocument().getLength()), false,
        offset);
        if (firedChange) {
          firePropertyChange("changed", firedChangeOldValue,
          firedChangeNewValue);
        }
      } catch (Exception ex) {
      }
    }

  }

  private String makeFireChange(String oldString, String newString,
                                String fullString, boolean insert, int offset)
  throws
  Exception {
    AbstractFormatter f = this.getFormatter();
    if (format == VAL_STRING || format == VAL_STRING_RIGHT) {
      firedChangeOldValue = f.stringToValue(oldString);
      firedChangeNewValue = f.stringToValue(fullString);
      if (hasFocus() || isActionFromPopUp()) {
        firedChange = true;
      }
      return newString;
    } else if (format == VAL_DATA) {
      if (valueDataAccept) {
        firedChangeOldValue = valueDataObject;
      } else {
        firedChangeOldValue = getValue();
      }
      fullString = fullString.trim().replaceAll(",", ".");
      newString = newString.trim().replaceAll(",", ".");
      if (newString.length() == 0 && insert) {
        throw new Exception("");
      }
      boolean fl = true;
      try {
        firedChangeNewValue = f.stringToValue(fullString);
        valueDataAccept = true;
        valueDataObject = firedChangeNewValue;
      } catch (ParseException ex) {
        fl = false;
      }
      if (fl) {
        if (hasFocus() || isActionFromPopUp()) {
          firedChange = true;
        }
        return newString;
      } else {
        if (!insert) {
          return newString;
        }
        if (newString.trim().length() == 1) {
          if (".0123456789".indexOf(newString) < 0) {
            throw new Exception("");
          }
          int len = fullString.length();
          if ((len == 4 && fullString.indexOf(".") == -1) || (len == 7
          && fullString.
          indexOf(".") == 4 && fullString.indexOf(".", 5) < 0)) {
            newString += ".";
          }
          return newString;
        } else {
          throw new Exception("");
        }
      }
    } else if (format == VAL_DATA_TIME) {
      if (valueDataAccept) {
        firedChangeOldValue = valueDataObject;
      } else {
        firedChangeOldValue = getValue();
      }
      fullString = fullString.trim().replaceAll(",", ".");
      newString = newString.replaceAll(",", ".");
      if (newString.length() == 0 && insert) {
        throw new Exception("");
      }
      boolean fl = true;
      try {
        firedChangeNewValue = f.stringToValue(fullString);
        valueDataAccept = true;
        valueDataObject = firedChangeNewValue;
      } catch (ParseException ex) {
        fl = false;
      }
      if (fl) {
        if (hasFocus() || isActionFromPopUp()) {
          firedChange = true;
        }
        return newString;
      } else {
        if (!insert) {
          return newString;
        }
        if (newString.equals(" ") || newString.trim().length() == 1) {
          if (".0123456789: ".indexOf(newString) < 0) {
            throw new Exception("");
          }
          int len = fullString.length();
          if ((len == 4 && fullString.indexOf(".") == -1) || (len == 7
          && fullString.
          indexOf(".") == 4 && fullString.indexOf(".", 5) < 0)) {
            newString += ".";
          } else if (len == 13 && fullString.indexOf(":") == -1) {
            newString += ":";
          }
          return newString;
        } else {
          throw new Exception("");
        }
      }
    } else if (format == VAL_INT) {
      if (newString.trim().length() == 0 && insert) {
        throw new Exception("");
      }
      firedChangeOldValue = f.stringToValue(oldString);
      String rezString = newString;
      StringBuilder buffer = new StringBuilder(newString);
      for (int i = buffer.length() - 1; i >= 0; i--) {
        char ch = buffer.charAt(i);
        if (!Character.isDigit(ch) && ch != '-') {
          buffer.deleteCharAt(i);
        }
      }
      newString = buffer.toString().trim();
      if (newString.length() == 0 && insert) {
        throw new Exception("");
      }
      fullString = fullString.replaceFirst(rezString, newString);
      if (fullString.trim().length() == 0 && !insert) {
        super.setValue(0);
        super.selectAll();
      }

      if (fullString.indexOf("-") > 0) {
        throw new Exception("");
      }
      firedChangeNewValue = f.stringToValue(fullString);
      if (hasFocus() || isActionFromPopUp()) {
        firedChange = true;
      }
      return newString;
    } else if (format == VAL_DECIMAL4) {
      if (newString.trim().length() == 0 && insert) {
        throw new Exception("");
      }
      if (valueDataAccept) {
        firedChangeOldValue = valueDataObject;
      } else {
        firedChangeOldValue = getValue();
      }
      fullString = fullString.trim().replaceAll(",", ".");
      newString = newString.replaceAll(",", ".");

      String rezString = newString;
      StringBuilder buffer = new StringBuilder(newString);
      boolean dot = oldString.contains(".");
      for (int i = buffer.length() - 1; i >= 0; i--) {
        char ch = buffer.charAt(i);
        if (!Character.isDigit(ch) && ch != '.') {
          buffer.deleteCharAt(i);
        }
        if (ch == '.') {
          if (dot) {
            buffer.deleteCharAt(i);
          } else {
            dot = true;
          }
        }
      }
      newString = buffer.toString().trim();
      if (newString.length() == 0 && insert) {
        throw new Exception("");
      }
      fullString = fullString.replaceFirst(rezString, newString);
      if (fullString.trim().length() == 0 && !insert) {
        super.setValue(BigDecimal.ZERO);
        super.selectAll();
      }
      boolean fl = true;
      try {
        firedChangeNewValue = f.stringToValue(fullString);
        valueDataAccept = true;
        valueDataObject = firedChangeNewValue;
      } catch (ParseException ex) {
        fl = false;
      }
      if (fl) {
        if (hasFocus() || isActionFromPopUp()) {
          firedChange = true;
        }
        return newString;
      } else {
        if (!insert) {
          return newString;
        }
        if (newString.trim().length() == 1) {
          if (".0123456789".indexOf(newString) < 0) {
            throw new Exception("");
          }
          return newString;
        } else {
          throw new Exception("");
        }
      }
    }
    return newString;
  }

  protected void setFormatter(AbstractFormatter format) {
    super.setFormatter(format);
    setEdited(false);
  }

  public void setUpperCase(boolean upperCase) {
    this.upperCase = upperCase;
  }

  public void digTextFieldKeyTyped(KeyEvent e) {
    if (upperCase) {
      e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
    }
  }

  public void digTextFieldMousePressed(MouseEvent e) {
    boolean fl = SwingUtilities.isRightMouseButton(e) && isEditable()
    && isEnabled() && !hasFocus();
    if (fl) {
      requestFocus();
    }
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

  class UndoActionTF extends AbstractAction {

    private UndoManager manager;
    public UndoActionTF(UndoManager manager) {
      this.manager = manager;
    }

    public void actionPerformed(ActionEvent evt) {
      try {
        DigTextField ftf = DigTextField.this;
        boolean canUndo = manager.canUndo() && ftf.isEnabled()
        && ftf.isEditable();
        if (canUndo) {
          String oldString = ftf.getText();
          AbstractFormatter f = ftf.getFormatter();
          boolean fl = true;
          Object oldValue = null;
          Object newValue = null;
          try {
            oldValue = f.stringToValue(oldString);
          } catch (ParseException ex) {
            fl = false;
          }
          manager.undo();
          if (!fl) {
            return;
          }
          try {
            newValue = f.stringToValue(ftf.getText());
          } catch (ParseException ex) {
            fl = false;
          }
          if (fl && oldValue != newValue) {
            ftf.firePropertyChange("changed", oldValue, newValue);
          }
        }
      } catch (CannotUndoException e) {
        Toolkit.getDefaultToolkit().beep();
      }
    }

  }

  class RedoActionTF extends AbstractAction {

    private UndoManager manager;
    public RedoActionTF(UndoManager manager) {
      this.manager = manager;
    }

    public void actionPerformed(ActionEvent evt) {
      try {
        DigTextField ftf = DigTextField.this;
        boolean canRedo = manager.canRedo() && ftf.isEnabled()
        && ftf.isEditable();
        if (canRedo) {
          String oldString = ftf.getText();
          AbstractFormatter f = ftf.getFormatter();
          boolean fl = true;
          Object oldValue = null;
          Object newValue = null;
          try {
            oldValue = f.stringToValue(oldString);
          } catch (ParseException ex) {
            fl = false;
          }
          manager.redo();
          if (!fl) {
            return;
          }
          try {
            newValue = f.stringToValue(ftf.getText());
          } catch (ParseException ex) {
            fl = false;
          }
          if (fl && oldValue != newValue) {
            ftf.firePropertyChange("changed", oldValue, newValue);
          }
        }
      } catch (CannotRedoException e) {
        Toolkit.getDefaultToolkit().beep();
      }
    }

  }

}
