package cen3024;

//Buddy Skinner
//April 10th 2023
//CEN 3024
//Module 9 DSLC (Part2) Add JavaDoc usage to your Word Occurrences application.  

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Module9_JavaDoc extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectFileButton;

    public Module9_JavaDoc() {
        initUI();
    }

    private void initUI() {
        setTitle("Word Searcher");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        selectFileButton = new JButton("src//theraven.txt");
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
        topPanel.add(selectFileButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Word");
        tableModel.addColumn("Frequency");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            readFile(file);
        }
    }

    private void readFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            // creating an empty map used to store word and frequencies of all words
            Map<String, Integer> wordCounts = new HashMap<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // splitting line by use regular expression
                String[] words = line.split("[\\s.;,?:!()\"]+");
                // iterate all words
                for (String word : words) {
                    word = word.trim();
                    if (word.length() > 0) {
                        if (wordCounts.containsKey(word)) {
                            wordCounts.put(word, wordCounts.get(word) + 1);
                        } else {
                            wordCounts.put(word, 1);
                        }
                    }
                }
            }

            // sorting wordCounts by frequency
            Map<String, Integer> sortedWordCounts = wordCounts.entrySet().stream()
                    .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            // clear existing data from table
            tableModel.setRowCount(0);

            // add word and frequencies of all words to table
            for (Map.Entry<String, Integer> entry : sortedWordCounts.entrySet()) {
                tableModel.addRow(new Object[] { entry.getKey(), entry.getValue() });
            }
            
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        
    }
        
    



    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Module9_JavaDoc ex = new Module9_JavaDoc();
            ex.setVisible(true);
        }
    );}
    
};

