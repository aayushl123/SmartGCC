package View;

import Backend.Debugger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.Element;


public class DebugView extends JFrame implements ActionListener {

    private  JTextArea textArea;
    private  JTextArea lines;
    private JScrollPane jsp;

    private JScrollPane jspConsole;
    int caretPosition;


    Debugger dataModel;
    JFrame outerPanel;
    JTextArea console;

    JPanel innerPanel;
    JLabel checkpoints;
    JLabel curCheckpoints;
    Process process;
    PrintWriter writer;
    String path;
    public JFrame getOuterPanel() {
        return outerPanel;
    }

    public void setOuterPanel(JFrame outerPanel) {
        this.outerPanel = outerPanel;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public StringBuilder getConsole() {
        return dataModel.getConsoleContent();
    }

    public void setConsole(String content) {
        this.console.setText(content);
    }

    public void setDataModel(Debugger dataModel) {
        this.dataModel = dataModel;
    }

    public Debugger getDataModel() {
        return dataModel;
    }

    public JPanel getInnerPanel() {
        return innerPanel;
    }

    public void setInnerPanel(JPanel innerPanel) {
        this.innerPanel = innerPanel;
    }

    public DebugView(String path, Process process) {
        this.process = process;
        dataModel = new Debugger();
        outerPanel =  new JFrame("Debugger");
        outerPanel.setLayout(new GridLayout(2,2));
        textArea = new JTextArea();
        textArea.setSize(500,720);
        textArea.setLocation(24,0);
        textArea.setHighlighter(null);
        textArea.setEditable(false);
        textArea.setEnabled(false);
        console = new JTextArea();
        console.setSize(500, 400);
        console.setLocation(500,320);
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.append("Hello! This is console for the debugger!...");
        lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setEditable(false);
        lines.setEnabled(false);
        lines.setHighlighter(null);
        lines.setEditable(false);
        jsp = new JScrollPane(textArea);
        jsp.setRowHeaderView(lines);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jspConsole = new JScrollPane(console);
        jspConsole.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jspConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        innerPanel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setVgap(3);
        layout.setHgap(2);
        layout.setAlignment(3);
        innerPanel.setLayout(layout);
        JLabel addLabel = new JLabel("Enter line number & click to create a checkpoint ");
        JTextArea add = new JTextArea(1, 2);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        innerPanel.add(addLabel);
        innerPanel.add(add);
        innerPanel.add(addButton);
        writer = new PrintWriter(new BufferedWriter
                (new OutputStreamWriter(process.getOutputStream())),true);
        JLabel removeLabel = new JLabel("Enter line number & click to remove a checkpoint ");
        JTextArea remove = new JTextArea(1, 2);
        JButton removeButton = new JButton("Del");
        removeButton.addActionListener(this);
        innerPanel.add(removeLabel);
        innerPanel.add(remove);
        innerPanel.add(removeButton);

        JButton next = new JButton("Step up to next line");
        innerPanel.add(next);
        next.addActionListener(this);

        JButton starNewSession =  new JButton("Start new session");
        innerPanel.add(starNewSession);
        starNewSession.addActionListener(this);

        JButton quit =  new JButton("Quit Session");
        innerPanel.add(quit);
        quit.addActionListener(this);

        checkpoints = new JLabel("The currently marked checkpoints in the program are  "
                +Arrays.toString(dataModel.getBreakPoints().toArray()));

        curCheckpoints = new JLabel("Your current checkpoint is at Line number:  "+ this.getDataModel().getCurrentCheckpoint());

        outerPanel.add(jsp);
        outerPanel.add(jspConsole);
        outerPanel.add(curCheckpoints);
        outerPanel.add(checkpoints);
        outerPanel.add(innerPanel);
        outerPanel.setSize(1504, 720);
        outerPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.path = path;
        this.readToText(path);
    }

    public void display(){
        outerPanel.setVisible(true);
    }

    public void refresh(){
        outerPanel.setVisible(false);
        console.setText(this.dataModel.getConsoleContent().toString());
        checkpoints.setText("The currently marked checkpoints in the program are  "
                +Arrays.toString(dataModel.getBreakPoints().toArray()));
        curCheckpoints.setText("Your are at the Line number:  "+"      "+ this.getDataModel().getCurrentCheckpoint());
        outerPanel.add(jsp);
        outerPanel.add(jspConsole);
        outerPanel.add(curCheckpoints);
        outerPanel.add(checkpoints);
        outerPanel.add(innerPanel);
    }

    public void readToText(String path) {
        try {
            String textLine;
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);

            while (reader.readLine() != null) {
                textArea.read(reader, "jTextArea1");
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
        lineEditor();

    }

    public void lineEditor(){
        caretPosition = textArea.getDocument().getLength();
        Element root = textArea.getDocument().getDefaultRootElement();
        String text = "1" + System.getProperty("line.separator");
        for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
            text += i + System.getProperty("line.separator");
        }
        lines.setText(text);

    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextArea getLines() {
        return lines;
    }

    public JScrollPane getJsp() {
        return jsp;
    }

    public int getCurrentCheckpoint() {
        return this.getDataModel().getCurrentCheckpoint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Quit Session":
                String value = "q\n";
                writer.write(value, 0, value.length());
                writer.println();
                value = "Y\n";
                writer.write(value, 0, value.length());
                writer.println();
                JOptionPane.showMessageDialog(null, "The session has been closed");
//                outerPanel.setVisible(false);
                break;
            case "Start new session":
                value = "run\n";
                writer.write(value, 0, value.length());
                writer.println();
                this.setConsole("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("*****************************************************************************\n");
                this.getConsole().append("******** BEGINNING OF A NEW SESSION ********\n");
                this.getDataModel().setCurrentCheckpoint(0);
                this.refresh();
                JOptionPane.showMessageDialog(null, "New Session has started");
                break;
            case "Step up to next line":
                value = "n\n";
                writer.write(value, 0, value.length());
                writer.println();
                JOptionPane.showMessageDialog(null, "Stepped up to next line");
                break;
            case "Add":
                String str = ((JTextArea)(this.innerPanel.getComponent(1))).getText();
                str = str.trim();
                int line;
                try {
                    line = Integer.parseInt(str);
                    String filename = path.substring(path.lastIndexOf("/")+1);
                    value = "b "+ filename +":"+ line + "\n";
                    writer.write(value, 0, value.length());
                    writer.println();
                    str = this.getDataModel().addCheckpoint(line);
                    JOptionPane.showMessageDialog(null, str);
                    this.refresh();
                    this.display();
                } catch (Exception eInput) {
                    eInput.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please enter an integer value.");
                }
                JOptionPane.showMessageDialog(null, "New checkpoint has been added");
                break;
            case "Del":
                String str1 = ((JTextArea)(this.innerPanel.getComponent(4))).getText();
                str1 = str1.trim();
                int line1;
                try {
                    line1 = Integer.parseInt(str1);
                    String filename = path.substring(path.lastIndexOf("/")+1);
                    line1 = this.getDataModel().delCheckpoint(line1);
                    if(line1 != 0) {
                        value = "br del " + line1 + "\n";
                        writer.write(value, 0, value.length());
                        writer.println();
                        JOptionPane.showMessageDialog(null, "Checkpoint has been deleted");
                        this.getDataModel().getBreakPoints().remove(this.getDataModel().getBreakPoints().get(line1-1));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "The entered checkpoint does not exist");
                    }
                    this.refresh();
                    this.display();
                } catch (Exception eInput) {
                    eInput.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please enter an integer value.");
                }
                break;

        }
    }
}