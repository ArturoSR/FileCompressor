/**
 * 
 */
package project1.compressor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.AbstractSequentialList;
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
        System.out.println("Do you wish to compress or decompress a file?");
        System.out.println("1. Compress");
        System.out.println("2. Decompress");
        System.out.println("3. Quit");
        Scanner console = new Scanner(System.in);
        int userChoice = console.nextInt();
        String filename = "";
        
        if(userChoice == 1) {
            System.out.println("What is the name of the file to compress?");
            console.nextLine();
            filename = console.nextLine();
            filename = filename.trim();
            compressFile(filename);
        } else if (userChoice == 2) {
            System.out.println("What is the name of the file to decompress?");
        } else {
            System.out.println("Invalid Input");
        }
        
        console.close();
    }
    
    /**
     * Method that handles the IO
     */
    public static void compressFile(String filename) {
        Scanner fileReader;
        System.out.println("The file to compress is: " + filename);
        try {
            fileReader = new Scanner(new FileInputStream(filename));
            while(fileReader.hasNextLine()) {
                
                
                
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("failed");
        }
        
        
        
    }

}




