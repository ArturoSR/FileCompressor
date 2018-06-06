/**
 * 
 */
package project1.compressor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.AbstractList;
import java.util.Scanner;

/**
 * @author Arturo Salinas
 *
 */
public class Proj1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //TODO program should get first line of file and tell if it is to be compressed or decompressed
        Scanner console = new Scanner(System.in);
        String filename = "";

        System.out.println("What is the name of the file to compress?");
        console.nextLine();
        filename = console.nextLine();
        filename = filename.trim();
        try {
            Scanner fileChecker = new Scanner(new FileInputStream(filename));
            String firstFileElement = fileChecker.next();
            if(firstFileElement.substring(0, 1).equals("0 ")) {
                compressFile(filename);   
            } else {
                decompressFile(filename);
            }
            
        
            console.close();
        } catch (FileNotFoundException e) {
            
        }
    }
    
    /**
     * Method that handles the IO
     */
    public static void compressFile(String filename) {
        Scanner fileReader;
        System.out.println("The file to compress is: " + filename);
        try {
            fileReader = new Scanner(new FileInputStream(filename));
            LinkedMTFList<String> words = new LinkedMTFList<String>();
            while(fileReader.hasNextLine()) {
                
                
                
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("failed");
        }
        
        
        
    }

    /**
     * Method that is used to decompress a file. 
     * 
     * @param filename
     */
    public static void decompressFile(String filename) {
        
    }
    
}






/**
 * The LinkedAbstractList Class.
 * 
 * @author Arturo Salinas
 * @param <E> the element type
 */
class LinkedMTFList<E> extends AbstractList<E> {

    /** The front of the list. */
    private ListNode front;

    /** The size. */
    private int size;


    /**
     * Instantiates a new linked abstract list.
     *
     * @param capacity the maximum capacity of the list
     */
    public LinkedMTFList() {
        front = null;
        size = 0;

    }

    /**
     * Gets the list node.
     *
     * @param idx the index to get from
     * @return the list node at the index
     */
    private ListNode getListNode(int idx) {
        ListNode current = front;
        for (int i = 0; i < idx && current != null; i++) {
            current = current.next;
        }
        return current;
    }
    

    /**
     * Adds the list node
     * @param idx the index to add at
     * @param element the element to add
     */
    @Override
    public void add(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add a null element");
        }
        if (idx < 0 || idx > this.size()) {
            throw new IndexOutOfBoundsException("Problem with specified index");
        }
        if (checkDuplicate(element)) {
            throw new IllegalArgumentException("Cannot have duplicates");
        }
        ListNode node = new ListNode(element);
        ListNode current = front; // should be after the new ListNode
        ListNode last = null; // should be before the new ListNode
        for (int i = 0; i < idx && current != null; i++) {
            last = current;
            current = current.next;
        }
        if (last != null) {
            last.next = node;
        } else {
            front = node;
        }
        node.next = current;
        size++;
    }
    
    /**
     * Checks if value is already in the list
     * @param value the value to compare with
     * @return true if duplicate
     */
    public boolean checkDuplicate(E value) {
        for (ListNode e = front; e != null; e = e.next) {
            if (e.data.equals(value)) {
            return true;
            }
        }
        return false;
    }

    /**
     * Remove from the list
     * @param idx the index to remove from
     */
    @Override
    public E remove(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException("Problem with specified index");
        }
        ListNode old = getListNode(idx);
        getListNode(idx - 1).next = getListNode(idx + 1);
        size--;
        return old.data;
    }

    /**
     * Gets from the list.
     * @param idx the index to get from
     */
    @Override
    public E get(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException("Problem with specified index");
        }
        ListNode current = front;
        for (int i = 0; i < idx && current != null; i++) {
            current = current.next;
        }
        if (current != null) {
            return current.data;
        }
        return null;
    }

    /**
     * Sets the value of an element in the list
     * @param idx the index to set at
     * @param element the value to set
     */
    @Override
    public E set(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot set a null element");
        }
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException("Problem with specified index");
        }
        ListNode node = getListNode(idx);
        if (node != null) {
            ListNode newNode = new ListNode(element);
            getListNode(idx - 1).next = newNode;
            newNode.next = node.next;
            return node.data;
        }
        return null;
    }

    /**
     * Gets the size of the list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * The ListNode inner class.
     */
    private class ListNode {

        /** The data. */
        private E data;
    
        /** The next. */
        private ListNode next;
    
        /**
         * Instantiates a new list node.
         *
         * @param data the value to store
         */
        public ListNode(E data){
            this.data = data;
        }
    
        
        /**
         * Instantiates a new list node.
         *
         * @param data the value to store
         * @param next the next ListNode
         *//*
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }
        */
        
    }

}




