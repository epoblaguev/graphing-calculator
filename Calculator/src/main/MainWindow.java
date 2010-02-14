/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Constants.Info;
import calculator.*;
import exceptions.InvalidVariableNameException;
import expressions.ExpressionList;
import expressions.MathEvaluator;
import expressions.VariableList;
import graphing.GraphingTab;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Egor
 */
public class MainWindow extends JFrame implements ActionListener, ChangeListener {

    JTabbedPane tabbedPane;
    CalculatorTab calculatorTab;
    GraphingTab graphingTab;
    JMenuBar menuBar;
    JMenu mnuFile, mnuSettings, mnuInfo;
    JMenuItem miExit, miSave, miAbout, miHelp, miLoad;
    JRadioButtonMenuItem rbDegrees, rbRadians;
    ButtonGroup bgAngle = new ButtonGroup();

    public MainWindow() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Egor's Calculator");

        this.createTabbedPane();
        this.createMenuBar();

        this.setJMenuBar(menuBar);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setSize(500, 550);
        this.setMinimumSize(this.getSize());
    }

    private void createMenuBar() {
        //Initialize Menu Bar
        menuBar = new JMenuBar();
        mnuFile = new JMenu("File");
        mnuSettings = new JMenu("Settings");
        mnuInfo = new JMenu("Info");

        //Initialize Menu Items
        miSave = new JMenuItem("Save State");
        miLoad = new JMenuItem("Load State");
        miExit = new JMenuItem("Exit");
        miAbout = new JMenuItem("About");
        miHelp = new JMenuItem("Help");

        //Initialize radio buttons.
        rbDegrees = new JRadioButtonMenuItem("Degrees");
        rbRadians = new JRadioButtonMenuItem("Radians");

        //Add to file menu.
        mnuFile.add(miLoad);
        mnuFile.add(miSave);
        mnuFile.add(miExit);

        //Add to Angle button group
        bgAngle.add(rbDegrees);
        bgAngle.add(rbRadians);

        //Add to settings menu
        mnuSettings.add(rbDegrees);
        mnuSettings.add(rbRadians);

        //Add to Info menu
        mnuInfo.add(miHelp);
        mnuInfo.add(miAbout);

        //Add to Menu Bar
        menuBar.add(mnuFile);
        menuBar.add(mnuSettings);
        menuBar.add(mnuInfo);

        //Add listeners.
        miLoad.addActionListener(this);
        miSave.addActionListener(this);
        miExit.addActionListener(this);
        miHelp.addActionListener(this);
        miAbout.addActionListener(this);
        rbDegrees.addChangeListener(this);
        rbRadians.addChangeListener(this);

        //Set angle type to degrees.
        rbDegrees.setSelected(true);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();

        calculatorTab = new CalculatorTab();
        graphingTab = new GraphingTab();
        tabbedPane.addTab("Calculator", calculatorTab);
        tabbedPane.addTab("Graphing", graphingTab);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miLoad) {
            JFileChooser fc = new JFileChooser();

            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ObjectInputStream in = null;
                try {
                    File file = fc.getSelectedFile();
                    in = new ObjectInputStream(new FileInputStream(file.getPath()));
                    Storage store = (Storage) in.readObject();
                    ExpressionList.setExpressions(store.getExpressions());
                    VariableList.setVariables(store.getVariables());
                    ExpressionTablePane.refreshTable();
                    VariableTablePane.refreshTable();
                    in.close();

                } catch (InvalidVariableNameException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }

            }

        }
        if (e.getSource() == miSave) {
            JFileChooser fc = new JFileChooser();

            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                Storage store = new Storage(ExpressionList.getExpressionList(), VariableList.getVariables());
                ObjectOutputStream objstream = null;
                try {
                    File file = fc.getSelectedFile();
                    objstream = new ObjectOutputStream(new FileOutputStream(file.getPath()));
                    objstream.writeObject(store);
                    objstream.close();
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        objstream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
        if (e.getSource() == miExit) {
            this.dispose();
        }
        if (e.getSource() == miAbout) {
            JOptionPane.showMessageDialog(this, Info.ABOUT, "About", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == miHelp) {
            JOptionPane.showMessageDialog(this, Info.HELP, "Help", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.rbDegrees || e.getSource() == this.rbRadians) {
            MathEvaluator m = new MathEvaluator();
            m.setUsingRadians(this.rbRadians.isSelected());
        }
    }
}
