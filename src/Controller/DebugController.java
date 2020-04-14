package Controller;

import Backend.Debugger;
import View.DebugView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DebugController extends Thread implements ActionListener {
    DebugView view;
    Process process;
    String path;
    JFrame temp;
    JTextArea tempInput;
    ArrayList<Integer> checkpoints;
    BufferedReader reader;
    Thread t;
    PrintWriter writer;
    HashMap<Integer, String> fileHolder;

    public DebugController(String path, BufferedReader reader, Process process) throws IOException {
        this.process = process;
        this.path = path;
        this.reader = reader;
        writer = new PrintWriter(new BufferedWriter
                (new OutputStreamWriter(process.getOutputStream())),true);
        int reply = JOptionPane.showConfirmDialog(null, "Do you want to " +
                        "begin a debug session with the currently opened program?"
                , "Begin Debug?", JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.YES_OPTION){
            view =  new DebugView(path, process);
            temp = new JFrame("Initializer");
            temp.setLayout(new GridLayout(2,1));
            temp.add(view.getJsp());
            JPanel tempPanel = new JPanel();
            JLabel tempQuery = new JLabel("Enter one line number (checkpoint) from the above " +
                    "program to initiate the debugging");
            tempInput = new JTextArea(1,3);
            Button addBut = new Button("Initial Add");

            JLabel tempQuery1 = new JLabel("Note: If the program does not have any initial checkpoint it " +
                    "will execute until the end as per its control flow.");
            JButton debugBut = new JButton("Go to debug session");
            tempPanel.add(tempQuery);
            tempPanel.add(tempInput);
            tempPanel.add(addBut);
            tempPanel.add(tempQuery1);
            tempPanel.add(debugBut);
            temp.add(tempPanel);
            temp.setSize(1024, 720);
            temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            temp.setVisible(true);
            fileHolderLoader(path);
            addBut.addActionListener(this);
            debugBut.addActionListener(this);
        }


    }

    public void fileHolderLoader(String path) throws IOException {
        this.fileHolder = new HashMap<>();
        FileReader fr = new FileReader(path);
        BufferedReader reader = new BufferedReader(fr);
        String st;
        int count = 1;
        while ((st = reader.readLine()) != null) {
            fileHolder.put(count, st);
            count = count + 1;
        }
        fileHolder.remove(0);

    }

    public void run(){
        System.out.println("Debug Session is running...");

        String line;
        try {
            reader.readLine();
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                System.out.println(line);
                displayView(line);
                if(line.contains("Input the")){
                    if(view.getDataModel().getCurrentCheckpoint() == 0) {
                        int index = view.getDataModel().getBreakPoints().get(0);
                        view.getDataModel().setCurrentCheckpoint(index);
                        String val = JOptionPane.showInputDialog(line);
                        writer.write(val, 0, val.length());
                        writer.println();
                    }
                    else {
                        int index = view.getDataModel().getBreakPoints().
                                indexOf(view.getDataModel().getCurrentCheckpoint());
                        if (index >= view.getDataModel().getBreakPoints().size() - 1) {
                            view.getDataModel().setCurrentCheckpoint(index+1);
                            String val = JOptionPane.showInputDialog(line);
                            writer.write(val, 0, val.length());
                            writer.println();
                        }
                    }

                }
                else if((line.contains("stop reason ="))&&(line.contains("reason = breakpoint"))){
                    if(view.getDataModel().getCurrentCheckpoint() == 0){
//                        System.out.println("The current line number is "+view.getDataModel().getCurrentCheckpoint() );
//                        int Lineno = view.getDataModel().getBreakPoints().get(0);
//                        view.getDataModel().setCurrentCheckpoint(Lineno);
//                        displayView("\nThe current line under execution is: \n");
//                        for(int i = Lineno ; i<=fileHolder.keySet().size() ; i++) {
//                            if(i==Lineno) {
//                                displayView("--> " + i + "   " + fileHolder.get(i));
//                            }
//                            else{
//                                displayView("      " + i + "   " + fileHolder.get(i));
//                            }
//                        }
                    }
                    else{
                        System.out.println("The current line number is "+view.getDataModel().getCurrentCheckpoint() );
                        int index = view.getDataModel().getBreakPoints().
                                indexOf(view.getDataModel().getCurrentCheckpoint());
                        if(index < view.getDataModel().getBreakPoints().size()-1){
                            index = index +1;
                            int Lineno = view.getDataModel().getBreakPoints().get(index);
                            view.getDataModel().setCurrentCheckpoint(index+1);
                            for(int i = Lineno ; i<=fileHolder.keySet().size() ; i++) {
                                if(i==Lineno) {
                                    displayView("--> " + i + "   " + fileHolder.get(i));
                                }
                                else{
                                    displayView("      " + i + "   " + fileHolder.get(i));
                                }
                            }
                        }
                    }
                }
                else if(line.contains("stop reason = instruction step over")){
                    //Click on quit if do not want to evaluate the assembly code
//                    JOptionPane.showMessageDialog(null,
//                            "The previous lldb session has ended. Console is now generating" +
//                                    "assembly level code for debugging. Click on start new session button " +
//                                    "to \" +\n begin a new session with " +
//                                    "the existing checkpoints ");
                    view.getDataModel().setCurrentCheckpoint(0);
                }
                 else if(line.contains("exited with status = 0")){
                    //The program has executed, want to rerun it with the existing checkpoints OK button
                    JOptionPane.showMessageDialog(null,
                            "The previous lldb session has ended. Click on start new session button to " +
                                    "begin a new session with the existing checkpoints");
                    view.getDataModel().setCurrentCheckpoint(0);
                }
                 else if(line.contains("invalid thread")){
                    JOptionPane.showMessageDialog(null,
                            "The previous lldb session has ended. Click on start new session to " +
                                    "begin a new session with the existing checkpoints");
                    view.getDataModel().setCurrentCheckpoint(0);
                }
                 else if(line.contains("Quitting LLDB will kill one or more processes")){
                    //Popup for Y or N
                    int reply = JOptionPane.showConfirmDialog(null, "Quitting LLDB will " +
                                    "kill one or more processes, still want to quit?"
                            , "Kill Session?", JOptionPane.YES_NO_OPTION);
                    if(reply == JOptionPane.YES_OPTION){
                        String val = "Y\n";
                        writer.write(val, 0, val.length());
                        writer.println();
                        view.getDataModel().setCurrentCheckpoint(0);
                    }
                    else{
                        String val = "N\n";
                        writer.write(val, 0, val.length());
                        writer.println();
                        view.getDataModel().setCurrentCheckpoint(0);
                    }
                }
                else if(line.contains("zsh: command not found: run")){
                    JOptionPane.showMessageDialog(null,
                            "The previous debugging session has ended. Quit the window and restart debugging.");
                    view.getDataModel().setCurrentCheckpoint(0);
                    break;
                }

                if(view.getDataModel().getCurrentCheckpoint() == 0){
                    System.out.println("The current line number is "+view.getDataModel().getCurrentCheckpoint() );
                    int Lineno = view.getDataModel().getBreakPoints().get(0);
                    view.getDataModel().setCurrentCheckpoint(Lineno);
                    displayView("\nThe current line under execution is: \n");
                    for(int i = Lineno ; i<=fileHolder.keySet().size() ; i++) {
                        if(i==Lineno) {
                            displayView("--> " + i + "   " + fileHolder.get(i));
                        }
                        else{
                            displayView("      " + i + "   " + fileHolder.get(i));
                        }
                    }
                }
                else{
                    System.out.println("The current line number is "+view.getDataModel().getCurrentCheckpoint() );
                    int index = view.getDataModel().getCurrentCheckpoint();
                    if(index < fileHolder.keySet().size()-1){
                        index = index +1;
                        view.getDataModel().setCurrentCheckpoint(index);
                        int Lineno = view.getDataModel().getCurrentCheckpoint();
                        for(int i = Lineno ; i<=fileHolder.keySet().size() ; i++) {
                            if(i==Lineno) {
                                displayView("--> " + i + "   " + fileHolder.get(i));
                            }
                            else{
                                displayView("      " + i + "   " + fileHolder.get(i));
                            }
                        }
                    }
                }
                displayView("Action performed!! \n Waiting for next input...");
                view.refresh();
                view.display();
                synchronized (this){
                    wait(100);
                }
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            displayView("The LLDB session has been closed");
        }
        displayView("The LLDB session has been closed safely");
        view.getOuterPanel().setVisible(false);

    }

    public void initializeDebugSession() throws IOException, InterruptedException {
        String value;
        t = new Thread(this);
        t.start();
        if(!view.getDataModel().getBreakPoints().isEmpty()){
            for(int i:view.getDataModel().getBreakPoints()){

                String filename = path.substring(path.lastIndexOf("/")+1);
                value = "b "+ filename +":"+ i + "\n";
                writer.write(value, 0, value.length());
                writer.println();
            }
        }

        value = "run\n";
        writer.write(value, 0, value.length());
        writer.println();
        synchronized (this){
            wait(1000);
        }
        view.refresh();
        view.display();


    }

    public void displayView(String str){
        view.getConsole().append("\n").append(str);
        view.setConsole(view.getConsole().toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Go to debug session":
                view = new DebugView(path, process);
                temp.setVisible(false);
                view.refresh();
                view.display();
                try {
                    initializeDebugSession();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "Initial Add":
                String str = tempInput.getText();
                str = str.trim();
                int line;
                try {
                    line = Integer.parseInt(str);
                    str = view.getDataModel().addCheckpoint(line);
                    checkpoints = new ArrayList<>(view.getDataModel().getBreakPoints());
                    int curcheckP = view.getDataModel().getCurrentCheckpoint();
                    JOptionPane.showMessageDialog(null, str);
                    view = new DebugView(path, process);
                    view.getDataModel().setBreakPoints(checkpoints);
                    view.getDataModel().setCurrentCheckpoint(curcheckP);
                    view.refresh();
                    view.display();
                    temp.setVisible(false);
                    initializeDebugSession();
                } catch (Exception eInput) {
                    eInput.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please enter an integer value.");
                }
                break;
        }
    }

}
