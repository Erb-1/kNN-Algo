/*
 * kNN1 Algorithm
 * V1.0
 * Intro to AI : Assignment 2
 * Author: eb523
*/

import java.util.*;
import java.io.*;

public class kNN1 {
    public static void main(String[] args)  throws FileNotFoundException {

        //------------------------------------------------------------------------------------------
        //LOAD DATA FROM FILES

        //load train data
        List<double[]> trainData = new ArrayList<>();
        Scanner scanner =  new Scanner(new File("train_data.txt"));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().trim().split("\\s");
            double[] point = new double[line.length];
            for (int i = 0; i < line.length; i++) {
                if (!line[i].isEmpty()) {
                    point[i] = Double.parseDouble(line[i]);
                }
            }
            trainData.add(point);
        }

        //load test data
        List<double[]> testData = new ArrayList<>();
        scanner = new Scanner(new File("test_data.txt"));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().trim().split("\\s");
            double[] point = new double[line.length];
            for (int i = 0; i < line.length; i++) {
                if (!line[i].isEmpty()) {
                    point[i] = Double.parseDouble(line[i]);
                }
            }
            testData.add(point);
        }

        //load test labels
        List<Integer> testLabels = new ArrayList<>();
        scanner = new Scanner(new File("test_label.txt"));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().trim().split("\\s+");
            for (String s : line) {
                if (!s.isEmpty()) {
                    testLabels.add(Integer.parseInt(s));
                }
            }
        }

        //load train labels
        List<Integer> trainLabels = new ArrayList<>();
        scanner = new Scanner(new File("train_label.txt"));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().trim().split("\\s+");
            for (String s : line) {
                if (!s.isEmpty()) {
                    trainLabels.add(Integer.parseInt(s));
                }
            }
        }
        //------------------------------------------------------------------------------------------
        //CLASSIFICATION AND ACCURACY CALCULATION

        PrintWriter writer = new PrintWriter("output1.txt");

        //initialise correct count to track correct classifications
        int correctCount = 0;

        //iterate over each point in test data.
        for (int i = 0; i < testData.size(); i++) {
            //grab the i-th point in the test data
            double[] testPoint = testData.get(i);
            //initialise min distance to max value
            double minDistance = Double.MAX_VALUE;
            int index = 0;

            //iterate over each point in train data
            for(int k = 0; k < trainData.size(); k++){
                //set distance to 0
                double distance = 0;

                //calculate the euclidian distance between the test point and each k-th train point
                for(int j = 0; j < testPoint.length; j++){
                    distance += Math.pow(trainData.get(k)[j] - testPoint[j], 2);
                }

                //take the square root of the distance so you get the euc distance
                distance = Math.sqrt(distance);

                //if the distance is less than the min distance, set the min distance to the distance
                if(distance < minDistance){
                    //updating
                    index = k;
                    minDistance = distance;
                }
            }

            //classify based on nearest neighbor
            int predictedLabel = trainLabels.get(index);

            //write to the output1.txt file
            writer.println(predictedLabel);

            if (predictedLabel == testLabels.get(i)) {
                correctCount++;
            }
        
        
        }
        writer.close();

        //calculate and print accuracy
        double accuracy = (double) correctCount / testData.size();
        System.out.println("Classification accuracy: " + (accuracy*100));
    }
}
