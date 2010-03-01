package calculator;

import Settings.GenSettings;
import exceptions.InvalidVariableNameException;
import expressions.VariableList;
import expressions.Expression;
import expressions.ExpressionList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultEditorKit;

/**
 * A panel that can be used as a calculator.
 * @author Egor
 */
public class CalculatorTab extends JPanel implements ActionListener, Serializable, MouseListener, ClipboardOwner, KeyListener {

    int targetRow;
    JScrollPane exprScrollPane, varScrollPane;
    JPanel controlPanel, controlPanelEast;
    JPanel centerPanel, centerPanelEast, centerPanelWest, exprControlPanel, varControlPanel;
    JTextField txtInput;
    JButton btnEnter, btnAddVariable, btnRemoveVariable, btnClearExpressions, btnAppendToInput;
    JTable varTable, exprTable, targetTable;
    JPopupMenu mnuRightClick;
    JMenuItem miCopyExpression, miCopyValue, miRemoveRow;
    Clipboard clipBoard;

    /**
     * Constructor for Calculator Tab
     */
    public CalculatorTab() {
        this.setLayout(new BorderLayout());
        this.createControlPanel();
        this.createCenterPanel();
        this.createPopupMenu();

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        try {
            this.createTables();
        } catch (InvalidVariableNameException ex) {
            Logger.getLogger(CalculatorTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes and adds components to the Control Panel
     */
    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanelEast = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanelEast.setLayout(new FlowLayout());

        txtInput = new JTextField();

        btnEnter = new JButton("Enter");
        btnEnter.addActionListener(this);

        controlPanelEast.add(btnEnter);
        controlPanel.add(txtInput, BorderLayout.CENTER);
        controlPanel.add(controlPanelEast, BorderLayout.EAST);

        txtInput.addMouseListener(this);
        txtInput.addKeyListener(this);
    }

    /**
     * Creates the right click popup menu.
     */
    private void createPopupMenu() {
        mnuRightClick = new JPopupMenu();
        miCopyExpression = new JMenuItem("Copy Expression");
        miCopyValue = new JMenuItem("Copy Value");
        miRemoveRow = new JMenuItem("Remove Row");

        miCopyExpression.addActionListener(this);
        miCopyValue.addActionListener(this);
        miRemoveRow.addActionListener(this);
    }

    /**
     * Initializes and adds components to the Center Panel
     */
    private void createCenterPanel() {
        centerPanel = new JPanel();
        centerPanelEast = new JPanel();
        centerPanelWest = new JPanel();
        exprControlPanel = new JPanel();
        varControlPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanelWest.setLayout(new BorderLayout());
        centerPanelEast.setLayout(new BorderLayout());
        varControlPanel.setLayout(new FlowLayout());
        exprControlPanel.setLayout(new FlowLayout());

        //Create variable and expression tables
        varTable = new VariableTablePane();
        varTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exprTable = new ExpressionTablePane();
        exprTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Create Scroll Panes
        exprScrollPane = new JScrollPane(exprTable);
        varScrollPane = new JScrollPane(varTable);

        //Create Buttons
        btnAddVariable = new JButton(GenSettings.getImageIcon("/images/addSmall.png"));
        btnRemoveVariable = new JButton(GenSettings.getImageIcon("/images/removeSmall.png"));
        btnClearExpressions = new JButton("Clear", GenSettings.getImageIcon("/images/clear.png"));
        btnAppendToInput = new JButton("Append To Input", GenSettings.getImageIcon("/images/copy.png"));

        //Add Action Listeners
        btnAddVariable.addActionListener(this);
        btnRemoveVariable.addActionListener(this);
        btnClearExpressions.addActionListener(this);
        btnAppendToInput.addActionListener(this);

        //Add mouse listeners.
        exprTable.addMouseListener(this);
        varTable.addMouseListener(this);

        //Add buttons to control panels
        varControlPanel.add(new JLabel("Variable:"));
        varControlPanel.add(btnRemoveVariable);
        varControlPanel.add(btnAddVariable);
        exprControlPanel.add(btnClearExpressions);
        exprControlPanel.add(btnAppendToInput);

        //Add to variable pannel.
        centerPanelEast.add(varScrollPane, BorderLayout.CENTER);
        centerPanelEast.add(varControlPanel, BorderLayout.SOUTH);

        //Add to expression pannel
        centerPanelWest.add(exprScrollPane, BorderLayout.CENTER);
        centerPanelWest.add(exprControlPanel, BorderLayout.SOUTH);

        //Add to center panel
        centerPanel.add(centerPanelWest);
        centerPanel.add(centerPanelEast);
    }

    /**
     * If ExpressionTablePane and VariableTablePane are uninitialized, creates them.
     * Otherwise refreshes them.
     * @throws InvalidVariableNameException
     */
    private void createTables() throws InvalidVariableNameException {
        ExpressionTablePane.refreshTable();
        VariableTablePane.refreshTable();
    }

    public void actionPerformed(ActionEvent e) {
        //If Enter is pressed.
        if (e.getSource() == btnEnter) {
            Expression exp = new Expression(txtInput.getText());
            try {
                ExpressionList.addExpression(exp);
                ExpressionTablePane.refreshTable();
            } catch (Exception exc) {
                ExpressionList.removeExpression(exp);
                JOptionPane.showMessageDialog(this, exc, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        //If Add Variable is pressed.
        if (e.getSource() == btnAddVariable) {
            JFrame window = new AddVariableDialog();
            window.setLocationRelativeTo(this);
            window.setVisible(true);
            window.pack();
        }

        //If Remove Variable is pressed.
        if (e.getSource() == btnRemoveVariable) {
            if (varTable.getSelectedRow() >= 0) {
                try {
                    VariableList.removeVariable(varTable.getSelectedRow());
                    VariableTablePane.refreshTable();
                } catch (InvalidVariableNameException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid variable name.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {

                JOptionPane.showMessageDialog(this, "Please select a variable to remove.");
            }
        }

        if (e.getSource() == btnClearExpressions) {
            try {
                ExpressionList.clearExpressionList();
                ExpressionTablePane.refreshTable();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, exc, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnAppendToInput) {
            if (exprTable.getSelectedRow() >= 0) {
                String copy = ((Expression) ExpressionList.getExpressionList().get(exprTable.getSelectedRow())).getExpression();
                txtInput.setText(txtInput.getText() + "(" + copy + ")");
            } else {
                JOptionPane.showMessageDialog(this, "Please select an expression to copy.");
            }
        }

        if (e.getSource() == miCopyValue) {
            String value = targetTable.getModel().getValueAt(targetRow, 1).toString();
            StringSelection strS = new StringSelection(value);
            clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipBoard.setContents(strS, this);
        }

        if (e.getSource() == miCopyExpression) {
            String value = targetTable.getModel().getValueAt(targetRow, 0).toString();
            StringSelection strS = new StringSelection(value);
            clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipBoard.setContents(strS, this);
        }

        if (e.getSource() == miRemoveRow) {
            if (targetTable.equals(varTable)) {
                try {
                    VariableList.removeVariable(targetRow);
                    VariableTablePane.refreshTable();
                } catch (InvalidVariableNameException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid variable name.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (targetTable.equals(exprTable)) {
                ExpressionList.removeExpression(targetRow);
                ExpressionTablePane.refreshTable();
            }

        }
    }

    public void mouseClicked(MouseEvent e) {
        //
    }

    public void mousePressed(MouseEvent e) {
        if (e.getSource() == exprTable &&(e.isPopupTrigger() || e.getModifiers() == InputEvent.BUTTON3_MASK)) {
            targetTable = exprTable;
            targetRow = exprTable.rowAtPoint(e.getPoint());
            exprTable.addRowSelectionInterval(targetRow, targetRow);

            miCopyExpression.setText("Copy Expression");
            mnuRightClick.removeAll();
            mnuRightClick.add(miCopyExpression);
            mnuRightClick.add(miCopyValue);
            mnuRightClick.add(miRemoveRow);

            mnuRightClick.show(exprTable, e.getX() + 10, e.getY() + 5);
        }
        if (e.getSource() == varTable && (e.isPopupTrigger() || e.getModifiers() == InputEvent.BUTTON3_MASK)) {
            targetTable = varTable;
            targetRow = varTable.rowAtPoint(e.getPoint());
            varTable.addRowSelectionInterval(targetRow, targetRow);

            miCopyExpression.setText("Copy Variable Name");
            mnuRightClick.removeAll();
            mnuRightClick.add(miCopyExpression);
            mnuRightClick.add(miCopyValue);
            mnuRightClick.add(miRemoveRow);

            mnuRightClick.show(varTable, e.getX() + 10, e.getY() + 5);
        }

        if (e.getSource() == txtInput && (e.isPopupTrigger() || e.getModifiers() == InputEvent.BUTTON3_MASK)) {
            txtInput.requestFocus();
            JMenuItem mnuItem;
            mnuRightClick.removeAll();

            mnuItem = new JMenuItem(new DefaultEditorKit.CutAction());
            mnuItem.setText("Cut");
            mnuRightClick.add(mnuItem);
            mnuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
            mnuItem.setText("Copy");
            mnuRightClick.add(mnuItem);
            mnuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
            mnuItem.setText("Paste");
            mnuRightClick.add(mnuItem);


            mnuRightClick.show(txtInput, e.getX() + 10, e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        //
    }

    public void mouseEntered(MouseEvent e) {
        //
    }

    public void mouseExited(MouseEvent e) {
        //
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        //Lost ownership.
    }

    public void keyTyped(KeyEvent e) {
        //
    }

    public void keyPressed(KeyEvent e) {
        //
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == txtInput) {
            if (e.getKeyCode() == 10) {
                btnEnter.doClick();
            }
        }
    }
}
