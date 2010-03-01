/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Constants.Info;
import Settings.GenSettings;
import Settings.GraphSettings;
import calculator.*;
import exceptions.InvalidVariableNameException;
import expressions.ExpressionList;
import expressions.MathEvaluator;
import expressions.VariableList;
import graphing.GraphingTab;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;

/**
 *
 * @author Egor
 */
public class MainWindow extends JFrame implements ActionListener {

    JTabbedPane tabbedPane;
    CalculatorTab calculatorTab;
    GraphingTab graphingTab;
    JMenuBar menuBar;
    JMenu mnuFile, mnuSettings, mnuInfo, mnuLineWidth, mnuGraphColor;
    JMenuItem miExit, miSave, miAbout, miHelp, miLoad;
    JRadioButtonMenuItem rbDegrees, rbRadians, rbThin, rbMedium, rbThick, rbCustThickness, rbWhite, rbLightGray, rbGray, rbCustColor;
    JCheckBoxMenuItem ckAntiAlias;
    ButtonGroup bgAngle, bgLineWidth, bgGraphColor;

    public MainWindow() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Egor's Calculator");
        this.setIconImage(GenSettings.getImage("/images/calculator.png"));

        this.createTabbedPane();
        this.createMenuBar();

        this.setJMenuBar(menuBar);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setSize(520, 550);
        this.setMinimumSize(this.getSize());
    }

    private void createMenuBar() {
        //Initialize Button Groups;
        bgAngle = new ButtonGroup();
        bgLineWidth = new ButtonGroup();
        bgGraphColor = new ButtonGroup();

        //Initialize Menu Bar
        menuBar = new JMenuBar();
        mnuFile = new JMenu("File");
        mnuSettings = new JMenu("Settings");
        mnuInfo = new JMenu("Info");
        mnuLineWidth = new JMenu("Line Width");
        mnuGraphColor = new JMenu("Graph Background");

        //Initialize Menu Items
        miSave = new JMenuItem("Save State", GenSettings.getImageIcon("/images/saveSmall.png"));
        miLoad = new JMenuItem("Load State",GenSettings.getImageIcon("/images/loadSmall.png"));
        miExit = new JMenuItem("Exit",GenSettings.getImageIcon("/images/exitSmall.png"));
        miAbout = new JMenuItem("About");
        miHelp = new JMenuItem("Help",GenSettings.getImageIcon("/images/helpSmall.png"));

        //Initialize radio buttons.
        rbDegrees = new JRadioButtonMenuItem("Degrees");
        rbRadians = new JRadioButtonMenuItem("Radians");
        rbThin = new JRadioButtonMenuItem("Thin");
        rbMedium = new JRadioButtonMenuItem("Medium");
        rbThick = new JRadioButtonMenuItem("Thick");
        rbCustThickness = new JRadioButtonMenuItem("Custom");
        rbWhite = new JRadioButtonMenuItem("White");
        rbLightGray = new JRadioButtonMenuItem("Light Gray");
        rbGray = new JRadioButtonMenuItem("Gray");
        rbCustColor = new JRadioButtonMenuItem("Custom");

        //Initialize check buttons.
        ckAntiAlias = new JCheckBoxMenuItem("Use Antialiasing");

        //Add to file menu.
        mnuFile.add(miLoad);
        mnuFile.add(miSave);
        mnuFile.add(miExit);

        //Add to Angle button group
        bgAngle.add(rbDegrees);
        bgAngle.add(rbRadians);

        //Add to Line thickness button group.
        bgLineWidth.add(rbThin);
        bgLineWidth.add(rbMedium);
        bgLineWidth.add(rbThick);
        bgLineWidth.add(rbCustThickness);

        //Add to graph color button group
        bgGraphColor.add(rbWhite);
        bgGraphColor.add(rbLightGray);
        bgGraphColor.add(rbGray);
        bgGraphColor.add(rbCustColor);

        //Add to graph color menu
        mnuGraphColor.add(rbWhite);
        mnuGraphColor.add(rbLightGray);
        mnuGraphColor.add(rbGray);
        mnuGraphColor.add(rbCustColor);

        //Add to line thickness menu
        mnuLineWidth.add(rbThin);
        mnuLineWidth.add(rbMedium);
        mnuLineWidth.add(rbThick);
        mnuLineWidth.add(rbCustThickness);

        //Add to settings menu
        mnuSettings.add(rbDegrees);
        mnuSettings.add(rbRadians);
        mnuSettings.addSeparator();
        mnuSettings.add(ckAntiAlias);
        mnuSettings.addSeparator();
        mnuSettings.add(mnuLineWidth);
        mnuSettings.add(mnuGraphColor);

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
        rbDegrees.addActionListener(this);
        rbRadians.addActionListener(this);
        ckAntiAlias.addActionListener(this);
        rbThin.addActionListener(this);
        rbMedium.addActionListener(this);
        rbThick.addActionListener(this);
        rbCustThickness.addActionListener(this);
        rbWhite.addActionListener(this);
        rbLightGray.addActionListener(this);
        rbGray.addActionListener(this);
        rbCustColor.addActionListener(this);

        //Set default settings..
        rbDegrees.doClick();
        rbThin.doClick();
        ckAntiAlias.doClick();
        rbLightGray.doClick();
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();

        calculatorTab = new CalculatorTab();
        graphingTab = new GraphingTab();
        tabbedPane.addTab("Calculator",GenSettings.getImageIcon("/images/calcSmall.png"), calculatorTab);
        tabbedPane.addTab("Graphing",GenSettings.getImageIcon("/images/graphSmall.png"), graphingTab);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miLoad) {
            FileDialog fd = new FileDialog(this, "Load State", FileDialog.LOAD);
            fd.setVisible(true);

            if (fd.getFile() != null) {
                ObjectInputStream in = null;
                try {
                    String filePath = fd.getDirectory() + fd.getFile();
                    in = new ObjectInputStream(new FileInputStream(filePath));
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
            FileDialog fd = new FileDialog(this, "Load State", FileDialog.LOAD);
            fd.setVisible(true);

            if (fd.getFile() != null) {
                Storage store = new Storage(ExpressionList.getExpressionList(), VariableList.getVariables());
                ObjectOutputStream objstream = null;
                try {
                    String filePath = fd.getDirectory() + fd.getFile();
                    objstream = new ObjectOutputStream(new FileOutputStream(filePath));
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

        //Settings items.
        if (e.getSource() == this.rbDegrees || e.getSource() == this.rbRadians) {
            MathEvaluator m = new MathEvaluator();
            m.setUsingRadians(this.rbRadians.isSelected());
        }
        if (e.getSource() == this.ckAntiAlias) {
            GraphSettings.setAntialiased(this.ckAntiAlias.isSelected());
        }
        if (e.getSource() == this.rbThin || e.getSource() == this.rbMedium || e.getSource() == this.rbThick || e.getSource() == this.rbCustThickness) {
            if (this.rbThin.isSelected()) {
                GraphSettings.setLineWidth(1);
            } else if (this.rbMedium.isSelected()) {
                GraphSettings.setLineWidth(1.5f);
            } else if (this.rbThick.isSelected()) {
                GraphSettings.setLineWidth(2);
            } else if (this.rbCustThickness.isSelected()) {
                try {
                    float thickness = Float.parseFloat(JOptionPane.showInputDialog(rbCustThickness, "Enter the custom thickness:"));
                    GraphSettings.setLineWidth(thickness);

                } catch (Exception nfe) {
                    JOptionPane.showMessageDialog(this.rbCustThickness, nfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == this.rbWhite || e.getSource() == this.rbLightGray || e.getSource() == this.rbGray || e.getSource() == this.rbCustColor) {
            if (this.rbWhite.isSelected()) {
                GraphSettings.setBgColor(Color.WHITE);
            } else if (this.rbLightGray.isSelected()) {
                GraphSettings.setBgColor(Color.LIGHT_GRAY);
            } else if (this.rbGray.isSelected()) {
                GraphSettings.setBgColor(Color.GRAY);
            } else if (this.rbCustColor.isSelected()) {
                Color clr = JColorChooser.showDialog(rbCustColor, "Color Chooser", GraphSettings.getBgColor());
                if (clr != null) {
                    GraphSettings.setBgColor(clr);
                }
            }
        }
        this.repaint();
    }
}
