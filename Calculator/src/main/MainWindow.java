/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import calculator.*;
import graphing.GraphingTab;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author Egor
 */
public class MainWindow extends JFrame implements ActionListener{

    JTabbedPane tabbedPane;
    JComponent calculatorTab;
    JComponent graphingTab;

    public MainWindow(){
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Egor's Calculator");

        this.createTabbedPane();
        
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setSize(500, 500);
        this.setMinimumSize(this.getSize());
    }

    private void createTabbedPane(){
        tabbedPane = new JTabbedPane();

        calculatorTab = new CalculatorTab();
        graphingTab = new GraphingTab();
        tabbedPane.addTab("Calculator", calculatorTab);
        tabbedPane.addTab("Graphing", graphingTab);

        tabbedPane.setPreferredSize(new Dimension(300, 450));
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
