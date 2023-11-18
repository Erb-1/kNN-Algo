package main;
/*
 * kNN Algorithm
 * Version: 0.1
 * Author: Erb-1
*/

//import necessary libraries
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Main{
    public static void main(String[] args){

        //create file objects for the input files.
        File in = new File("src/test_data.txt");
        File in2 = new File("src/train_data.txt");

        //create arraylists to store the data from the files.
        ArrayList<Double> data1 = new ArrayList<Double>();
        ArrayList<Double> data2 = new ArrayList<Double>();

        //try catch block to catch file not found exception
        try{
            //scanner objects to read the files
            Scanner sc = new Scanner(in);
            Scanner sc2 = new Scanner(in2);

            //read the file line by line.
            while(sc2.hasNextLine()){
                String line = sc2.nextLine();
                //split the line into array of strings using spaces as the delimiter
                String[] data22 = line.split(" ");

                //parse each string in the array as a double and add it to the arraylist
                for(int i = 0; i < data22.length; i++){
                    data2.add(Double.parseDouble(data22[i]));
                }
            }
            //close scanner2
            sc2.close();

            //read the second file line by line.
            //this while/for block is the same as the one above.
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] data11 = line.split(" ");

                for(int i = 0; i < data11.length; i++){
                    data1.add(Double.parseDouble(data11[i]));
                }
            }
            //close scanner
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        //call the EuclidianDistance function for each point in the two files
        for(int i = 0; i < data1.size(); i++){
            double distance = EuclidianDistance(data1.get(i), data2.get(i));
            //format the output to 6 decimal places
            String s = String.format("%.6f", distance);
            System.out.println(s);

        }
    }

    //Euclidian Distance Function-
    //Takes in two points and returns the distance between them
    public static double EuclidianDistance(double p1, double p2){
        double distance = 0;
        distance = Math.sqrt(Math.pow((p1-p2),2));
        return distance;
    }
    
}