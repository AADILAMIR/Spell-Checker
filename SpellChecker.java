import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;

class Trie {

    public TrieNode root;

    public Trie() {
        //set root is Empty
        root = new TrieNode();
    }
    
    public void insertWord(String word) {

        if (word == null || word.isEmpty()) {
            System.out.println("INVALID INPUT");
        }
        ///CONVERT WORD INTO LOWERCASE////
        word = word.toLowerCase();
        TrieNode current = root;//starting from root

        for (int i = 0; i < word.length(); i++) {

            if (word.contains("-") || word.contains(".") || word.contains(",") || word.contains("'")) {
                //i++;
                continue;
            }
            //read word character by characterf
            char c = word.charAt(i);
            //getting real index of word in array after subtracting it from its ASCII value
            int index = c - 'a';
            if (current.children[index] == null) {
                TrieNode node = new TrieNode();//to insert chrac in trie we make it
                current.children[index] = node; // assign value to it
                current = node; // now update root
            } else {
                current = current.children[index]; //if its current index is not null
            }
        }
        current.isWord = true; //it's mean word is completey inserted
    }

    public boolean search(String word) {
        //store in a node status of word
        TrieNode node = findNode(word);
        return node != null && node.isWord;

    }
    // finding a node
    private TrieNode findNode(String word) {

        TrieNode current = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            //String into character (get charcter of string at 'i')
            char c = word.charAt(i);
            
            if (word.contains("-") || word.contains(".") || word.contains(",") || word.contains("'")) {
                continue;
            }
            int index = c - 'a';
            if (current.children[index] == null) {
                //return null where index not found(searching get stop here)
                return null;
            } else {
                current = current.children[index];
            }
        }
        return current;
    }

    public boolean prefix(String prefix) {
        TrieNode node = root;
        prefix = prefix.toLowerCase();
        for (int i = 0; i < prefix.length(); i++) {
            if (node.children[prefix.charAt(i) - 'a'] == null) {
                return false;
            }
            node = node.children[prefix.charAt(i) - 'a'];
        }
        return true;
    }

    public void grammerChecker(String input) {
        boolean validGrammer = true;
        if (!input.endsWith(".")) {//built in
            System.out.println("FullStop is Missing");
            validGrammer= false;
        } else if (input == null) {
            validGrammer = true;
        } 
    }

    class TrieNode {

        public boolean isWord;
        public TrieNode[] children;

        //constructor
        public TrieNode() {
            this.children = new TrieNode[26];//ALL english Alphabets
            this.isWord = false;
        }
    }
}

public class SpellChecker {

    public static void main(String[] args) {

        Trie trie = new Trie();
        String DictonaryPath, ReadFile = null;
        
        try {
            DictonaryPath = JOptionPane.showInputDialog("Please Enter Dictonary File Path");
            ReadFile = JOptionPane.showInputDialog("Please Enter Read File Path");
        
            File file = new File(DictonaryPath);
            Scanner input = new Scanner(file);
            
            while (input.hasNext()) {
                
                String word = input.next();
                trie.insertWord(word);
            
            }
            
        } catch (FileNotFoundException e) {
            
            System.out.println("ERROR");
        
        }

        try {
            //File file = new File("read.txt");
            //Scanner input = new Scanner(file);   //it dont read text line by line
            BufferedReader bufferedReader = null;  //used to read line by line
            //store line
            String line = "";
            int lineCount = 0;
            //Location of file
            bufferedReader = new BufferedReader(new FileReader(ReadFile));
        
            while ((line = bufferedReader.readLine()) != null) {
                //line Counter
                lineCount++;
                String[] words = line.split(" ");
                //trie.grammerChecker(line);
        
                for (int i = 0; i < words.length; i++) {
                    // 'i' is used for words 
                    if (trie.search(words[i]) == false) {
                    
                        FileWriter fileWriter = new FileWriter("MissSpellWords1.txt", true);
                        fileWriter.write(+lineCount + ":" + (i + 1) + " " + words[i] + "\n");
                        //System.out.println("lineCount:word" + lineCount + ":" + (i + 1) + " " + words[i] + "\n");
                        fileWriter.close();
                        
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Your Incorrect words have been stored in new File Sucessfully");

            String CheckPrefix = null;
            String Prefix = null;
            CheckPrefix = JOptionPane.showInputDialog("Do you want to check Word Starts with(prefix) exists or not\n "
                    + "Enter 'Yes' or 'No'" + CheckPrefix);
            if (CheckPrefix.equals("yes") || CheckPrefix.equals("YES") || CheckPrefix.equals("Yes")) {

                Prefix = JOptionPane.showInputDialog("Enter Prefix");
                if (trie.prefix(Prefix) == true) {
                    JOptionPane.showMessageDialog(null, "Your Entered Prefix words Exits in Dictionary");
                } else {
                    JOptionPane.showMessageDialog(null, "Your Entered Prefix words dosen't Exits in Dictionary");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Thank You!");
            }
            
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }
}