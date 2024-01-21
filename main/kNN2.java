/*
 * kNN2 Algorithm
 * V2.0
 * Intro to AI : Assignment 2
 * Author: eb523
*/

import java.util.*;
import java.io.*;

public class kNN2 {
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
        /*------------------------------------------------------------------------------------------
         * Z-SCORE STANDARDISATION
         * here is where the mean and standard deviation for each feature in the 
         * training data is calculated and applied to enhance accuracy
        */

        //initialise mean and standard deviation arrays
        double[] means = new double[trainData.get(0).length];
        double[] stanDevs = new double[trainData.get(0).length];

        //calculate the mean for each feature
        for (double[] point : trainData) {
            //iterate over each feature in the point
            //add the value of the feature to the mean array
            for (int i = 0; i < point.length; i++) {
                means[i] += point[i];
            }
        }
        //divide each value in the mean array by the number of points in the training data
        for (int i = 0; i < means.length; i++) {
            means[i] /= trainData.size();
        }
        //calculate the standard deviation for each feature
        for (double[] point : trainData) {
            //iterate over each feature in the point
            //add the squared difference between the feature value and the mean 
            //to the standard deviation array
            for (int i = 0; i < point.length; i++) {
                stanDevs[i] += Math.pow(point[i] - means[i], 2);
            }
        }
        //divide each value in the standard deviation array by the number of points in the training data
        for (int i = 0; i < stanDevs.length; i++) {
            stanDevs[i] = Math.sqrt(stanDevs[i] / trainData.size());
        }

        //standardize the training data
        for (double[] point : trainData) {
            for (int i = 0; i < point.length; i++) {
                point[i] = (point[i] - means[i]) / stanDevs[i];
            }
        }

        //standardize the test data
        for (double[] point : testData) {
            for (int i = 0; i < point.length; i++) {
                point[i] = (point[i] - means[i]) / stanDevs[i];
            }
        }
        //------------------------------------------------------------------------------------------
        //CLASSIFICATION AND ACCURACY CALCULATION

        PrintWriter writer = new PrintWriter("output2.txt");

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
