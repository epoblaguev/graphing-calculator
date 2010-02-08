/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Constants.Info;
import calculator.*;
import expressions.MathEvaluator;
import graphing.GraphingTab;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
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
    JComponent calculatorTab;
    JComponent graphingTab;
    JMenuBar menuBar;
    JMenu mnuFile, mnuSettings, mnuInfo;
    JMenuItem miExit, miSave, miAbout, miHelp;
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
        this.setSize(500, 500);
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
        miExit = new JMenuItem("Exit");
        miAbout = new JMenuItem("About");
        miHelp = new JMenuItem("Help");

        //Initialize radio buttons.
        rbDegrees = new JRadioButtonMenuItem("Degrees");
        rbRadians = new JRadioButtonMenuItem("Radians");

        //Add to file menu.
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

        tabbedPane.setPreferredSize(new Dimension(300, 450));
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == miSave){
            JOptionPane.showMessageDialog(this, "Not Yet Implemented");
        }
        if(e.getSource() == miExit){
            this.dispose();
        }
        if(e.getSource() == miAbout){
            JOptionPane.showMessageDialog(this, Info.ABOUT);
        }
        if(e.getSource() == miHelp){
            JOptionPane.showMessageDialog(this, Info.HELP);
        }
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.rbDegrees || e.getSource() == this.rbRadians) {
            MathEvaluator m = new MathEvaluator();
            m.setUsingRadians(this.rbRadians.isSelected());
        }
    }
}
