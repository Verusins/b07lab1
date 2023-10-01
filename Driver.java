import java.io.*;
import java.util.*;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = {6,5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = {-2,-9};
        int[] e2 = {1,4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        s.print();
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        double[] c3 = {1, 1, 1, 1};
        int[] e3 = {2, 3, 1, 0};
        Polynomial p3 = new Polynomial(c3, e3);
        double[] c4 = {-2, -2, -1};
        int[] e4 = {1, 3, 4};
        Polynomial p4 = new Polynomial(c4, e4);
        Polynomial s2 = p3.add(p4);
        s2.print();
        System.out.println("s(0.1) = " + s2.evaluate(0.1));

        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        Polynomial ans = p1.multiply(p2);
        ans.print();

        String filename = "testfile.txt"; // File name

        try {
            File file = new File(filename); // Create a File object for "testfile.txt"
            Polynomial polynomial = new Polynomial(file); // Pass the File object to the constructor
            System.out.println("File is readable.");
            polynomial.print();
        } catch (IOException e) {
            System.out.println("File is not readable.");
            e.printStackTrace();
        }

        try {
            // Create a Polynomial object
            Polynomial polynomial = s;

            // Save the polynomial to a file
            polynomial.saveToFile("output.txt");

            System.out.println("Polynomial saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving polynomial to file.");
            e.printStackTrace();
        }
    }
}