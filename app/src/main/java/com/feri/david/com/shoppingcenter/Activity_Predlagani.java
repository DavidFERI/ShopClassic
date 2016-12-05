package com.feri.david.com.shoppingcenter;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.lazy.KStar;
import weka.classifiers.meta.Bagging;
import weka.classifiers.rules.OneR;
import weka.core.FastVector;
import weka.core.Instances;


public class Activity_Predlagani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predlagani);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView txtHeader = (TextView) findViewById(R.id.firstLine);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Uspešno predvidevanje!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                try {
                /*try {
                    File myFile = new File( "/storage/sdcard1/bluetooth/misc/" + "Primerki.txt");
                    FileInputStream fin = new FileInputStream(myFile);
                    String ret = convertStreamToString(fin);
                    //Make sure you close all streams.
                    fin.close();
                    Log.i("File: ", ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                    String besedilo = "";
                    File myFile = new File( "/storage/sdcard1/bluetooth/misc/" + "Primerki.txt");
                    BufferedReader datafile = readDataFile(myFile.getPath());

                    Instances data = new Instances(datafile);
                    data.setClassIndex(data.numAttributes() - 1);

                    // Do 10-split cross validation
                    Instances[][] split = crossValidationSplit(data, 10);

                    // Separate split into training and testing arrays
                    Instances[] trainingSplits = split[0];
                    Instances[] testingSplits = split[1];

                    // Use a set of classifiers
                    Classifier[] models = {
                            new Bagging(),
                            new NaiveBayes(),
                            new OneR(),
                            new KStar()
                    };

                    // Run for each model
                    for (int j = 0; j < models.length; j++) {

                        // Collect every group of predictions for current model in a FastVector
                        FastVector predictions = new FastVector();

                        // For each training-testing split pair, train and test the classifier
                        for (int i = 0; i < trainingSplits.length; i++) {
                            Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);

                            predictions.appendElements(validation.predictions());

                            // Uncomment to see the summary for each training-testing pair.
                            //System.out.println(models[j].toString());
                        }

                        // Calculate overall accuracy of current classifier on all splits
                        double accuracy = calculateAccuracy(predictions);

                        // Print current classifier's name and accuracy in a complicated,
                        // but nice-looking way.

                        besedilo += ("Uspesnost " + models[j].getClass().getSimpleName() + ":\n " + String.format("%.2f%%", accuracy) + "\n\n");
                    }
                    txtHeader.setText(besedilo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static BufferedReader readDataFile(String fileName) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + fileName);
        }

        return inputReader;
    }

    public static Evaluation classify(Classifier model,
                                      Instances trainingSet, Instances testingSet) throws Exception {
        Evaluation evaluation = new Evaluation(trainingSet);

        model.buildClassifier(trainingSet);
        evaluation.evaluateModel(model, testingSet);

        return evaluation;
    }

    public static double calculateAccuracy(FastVector predictions) {
        double correct = 0;

        for (int i = 0; i < predictions.size(); i++) {
            NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
            if (np.predicted() == np.actual()) {
                correct++;
            }
        }

        return 100 * correct / predictions.size();
    }

    public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
        Instances[][] split = new Instances[2][numberOfFolds];

        for (int i = 0; i < numberOfFolds; i++) {
            split[0][i] = data.trainCV(numberOfFolds, i);
            split[1][i] = data.testCV(numberOfFolds, i);
        }

        return split;
    }

}
