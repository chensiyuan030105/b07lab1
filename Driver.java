import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver { 
    public static void main(String [] args) throws IOException { 
        Polynomial p = new Polynomial(); 
        System.out.println(p.evaluate(3)); 
        double [] c1 = {6,5}; 
        double [] d1 = {0,3}; 
        Polynomial p1 = new Polynomial(c1, d1); 
        double [] c2 = {-2,-9}; 
        double [] d2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, d2); 
        Polynomial s1 = p1.add(p2); 
        System.out.println("s1(0.1) = " + s1.evaluate(0.1)); 
        if(s1.hasRoot(1)) 
            System.out.println("1 is a root of s"); 
        else 
            System.out.println("1 is not a root of s"); 
        Polynomial s2 = p1.multiply(p2);
        System.out.println("s2(1) = " + s2.evaluate(1));           

        File file = new File("./test1.txt");
        Polynomial polynomial = new Polynomial(file); 
        polynomial.saveToFile("./output1.txt");
    } 
} 
