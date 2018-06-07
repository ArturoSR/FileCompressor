/**
 * 
 */
package project1.compressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.AbstractList;
import java.util.Scanner;

/**
 * Program that uses the MoveToFront Heuristic to compress and decompress files.
 * 
 * @author Arturo Salinas
 */
public class Proj1 {

    /**
     * Main method for the programming that executes it. 
     * 
     * @param args is the command line arguments
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String filename = "";
        String outfile = "";

        System.out.println("What is the name of the file?");
        filename = console.nextLine();
        filename = filename.trim();
        System.out.println("What is the name of the file to print to?");
        outfile = console.nextLine();
        outfile = outfile.trim();
        
        try {
            Scanner fileChecker = new Scanner(new FileInputStream(filename));
            String firstFileElement = fileChecker.nextLine();
            
            if(firstFileElement.contains("0 ")) {
                decompressFile(filename, outfile);
            } else {
                compressFile(filename, outfile);  
            }
            
            fileChecker.close();
            console.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }
    
    /**
     * Method that handles the compression of a file. 
     * 
     * 
     * @param filename is the name of the file to compress
     * @param outfile is the name of the file to write to
     */
    public static void compressFile(String filename, String outfile) {
        Scanner fileReader;
        PrintStream writer;
        try {
            fileReader = new Scanner(new FileInputStream(filename));
            writer = new PrintStream(new File(outfile));
            LinkedMTFList<String> wordList = new LinkedMTFList<String>();
            String line;
            Scanner lineScanner;
            int idx;
            writer.print("0 ");
            
            while(fileReader.hasNextLine()) {
                line = fileReader.nextLine();
                line = line.replaceAll("[^\\w\\s]"," ");
                lineScanner = new Scanner(line);
                
                while(lineScanner.hasNext()) {
                    String wordToFind = lineScanner.next();
                    
                    idx = wordList.lookUp(wordToFind);
                    
                    if(idx == -1) {
                        //System.out.print(wordToFind + " ");
                        writer.print(wordToFind + " ");
                    } else {
                        //System.out.print(idx + " ");
                        writer.print(idx + " ");
                    }   
                }
                writer.print("\n");
                
            }
            File uncompFile = new File(filename); 
            File compFile = new File(outfile);
            long compressedLength = compFile.length();
            writer.print("0 ");
            writer.print("Uncompressed: " + uncompFile.length() + " bytes;  ");
            writer.print("Compressed: " + compressedLength + " bytes" );
            
            
            
        } catch (FileNotFoundException e) {
            System.out.println("Unable to process file.");
        }
        
        
        
    }

    /**
     * Method that is used to decompress a file. 
     * 
     * @param filename is the name of the file to decompress
     * @param outfile is the name of the file to write to. 
     */
    public static void decompressFile(String filename, String outfile) {
        Scanner fileReader;
        PrintStream writer;
        try {
            fileReader = new Scanner(new FileInputStream(filename));
            writer = new PrintStream(new File(outfile));
            LinkedMTFList<String> wordList = new LinkedMTFList<String>();
            String line;
            Scanner lineScanner;
            int idx;
            
            
            while(fileReader.hasNextLine()) {
                line = fileReader.nextLine();
                line = line.replaceAll("[^\\w\\s]"," ");
                lineScanner = new Scanner(line);
                idx = 0;
                
                if(line.contains("0 U")) {
                    break;
                }
                while(lineScanner.hasNext()) {
                    
                    
                    
                    if(lineScanner.hasNextInt()) {
                        idx = lineScanner.nextInt();
                        if(idx == 0) {
                            System.out.println("");
                        } else {
                            String indexedWord = wordList.get(idx);
                            //System.out.println(indexedWord);
                            wordList.moveToFront(indexedWord);
                            writer.print(indexedWord + " ");
                            //System.out.print(indexedWord + " "); 
                        }
                    } else {
                        String word = lineScanner.next();
                        wordList.lookUp(word);
                        writer.print(word + " ");
                        //System.out.print(word + " ");
                    }
                    
                }
                writer.print("\n");
               // System.out.println("\n");
            }
            
            
        }catch (FileNotFoundException e) {
            System.out.println("File was not Found");
        }
    }
    
}






/**
 * The LinkedMoveToFrontList Class.
 * 
 * @author Arturo Salinas
 * @param <E> the element type
 */
class LinkedMTFList<E> extends AbstractList<E> {

    /** The front of the list. */
    private ListNode head;

    /** The size. */
    private int size;


    /**
     * Constructor for the Move to Front List
     *
     */
    public LinkedMTFList() {
        head = null;
        size = 0;

    }

    /**
     * Method that looks to see if a word has been stored in the list.
     * 
     * @param element is the word to see if it exists in the list.
     * @return index of the word. If it is not found then return -1.
     */
    public int lookUp(E element) {
        int index = 1;
        boolean found = false;
        for (ListNode searcher = head; searcher != null; searcher = searcher.next, index++) {
            //System.out.println("Elements in list: " + searcher.data );
            if (searcher.data.equals(element)) {
                //System.out.print("'" + element + "'"+ "was found");
                found = true;
                break;
            } 
        }
        
        //If the element is not in the list create it in the list and return -1
        //If it was found then move that element to the front. 
        if(!found) {
            insertAtFront(element);
            return -1;
        } else {
            moveToFront(element);
            return index;
        }
        
        
    }
    
    /**
     * Method that looks for where a word is in the list and then moves it to the front.
     * Bridges gap where word used to be in order to keep list in order.
     * Old node gets garbage collected.
     * 
     * @param foundWord is the word to look for to move the list node of.
     */
    public void moveToFront(E foundWord) {
        ListNode word = head;
        ListNode prev = null;
        while(word != null) {
            //When it gets to the appropriate node
            if(word.data.equals(foundWord) && prev != null) {
                //Stop here and prev bridges gap between the nodes
                prev.next = word.next;
                
                //Next reference points to the head 
                word.next = head;
                //Word now at front
                head = word;
                
                break;
                
            }
            
            
            //Prev stays where word previously was and word node moves to next one.
            prev = word;
            word = word.next;
        }
    }
    
    /**
     * Method that adds a new element to the linkedList at the head (front).
     * 
     * @param element is the information to be put in a node. 
     */
    public void insertAtFront(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add a null element");
        }
        
        //If the list is empty then just add at head. 
        if (head == null) { 
           head = new ListNode(element, null);
        } else {
            //Create the new elements listNode
            ListNode newWord = new ListNode(element, null);
            newWord.next = head;
            //Point back to head
            head = newWord;
        }

        size++;
    }
    

    /**
     * Required method for getting a node. 
     * Part of the methods required for an abstract list.
     * 
     * @param idx the index to get the data from
     * @return the data held at the idx 
     */
    @Override
    public E get(int idx) {
        ListNode current = head;
//        for (int i = 0; i < idx && current != null; i++) {
//            current = current.next;
//        }
        int i = 0;
        while (i < idx) 
        {
            current = current.next;
            i++;
        }
        
        if (current != null) {
            return current.data;
        } else {
            return null;
        }
    }


    /**
     * Returns the list size
     * Part of methods required for an abstract list
     * 
     * @return an int representation of the size of the list.
     */
    @Override
    public int size() {
        return size;
    }

    
    
    /**
     * The ListNode inner class.
     */
    private class ListNode {

        /** The data of the listnodes */
        private E data;
    
        /** The next reference. */
        private ListNode next;
    
        /**
         * Constructor for the listnode
         *
         * @param data the value to store
         */
        public ListNode(E data){
            this.data = data;
        }
    
        
        /**
         * Constructor for the listnode with a next reference.
         *
         * @param data the value to store
         * @param next the next ListNode
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }
        
        
    }

}




