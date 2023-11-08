package com.example.project1_6318248;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Project1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart");

        ArrayList<LineChart> lineCharts = new ArrayList<>();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Iterations");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(22);
        xAxis.setTickUnit(1);
        xAxis.setMinorTickVisible(false);

        yAxis.setLabel("Error");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1);
        yAxis.setTickUnit(.02);
        yAxis.setMinorTickVisible(false);

        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Iterations");
        xAxis1.setAutoRanging(false);
        xAxis1.setLowerBound(0);
        xAxis1.setUpperBound(22);
        xAxis1.setTickUnit(1);
        xAxis1.setMinorTickVisible(false);

        yAxis1.setLabel("Error");
        yAxis1.setAutoRanging(false);
        yAxis1.setLowerBound(0);
        yAxis1.setUpperBound(1);
        yAxis1.setTickUnit(.02);
        yAxis1.setMinorTickVisible(false);

        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Iterations");
        xAxis2.setAutoRanging(false);
        xAxis2.setLowerBound(0);
        xAxis2.setUpperBound(10);
        xAxis2.setTickUnit(1);
        xAxis2.setMinorTickVisible(false);

        yAxis2.setLabel("Error");
        yAxis2.setAutoRanging(false);
        yAxis2.setLowerBound(0);
        yAxis2.setUpperBound(20);
        yAxis2.setTickUnit(1);
        yAxis2.setMinorTickVisible(false);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setPrefSize(500, 500);
        final LineChart<Number, Number> lineChart2 = new LineChart<>(xAxis1, yAxis1);
        lineChart2.setPrefSize(500, 500);
        final LineChart<Number, Number> lineChart3 = new LineChart<>(xAxis2, yAxis2);
        lineChart3.setPrefSize(500, 500);

        lineChart.setTitle("Error 1 Chart");
        lineChart2.setTitle("Error 2 Chart");
        lineChart3.setTitle("Error 3 Chart");

        lineCharts.add(lineChart);
        lineCharts.add(lineChart2);
        lineCharts.add(lineChart3);

        FlowPane root = new FlowPane();
        root.getChildren().addAll(lineChart, lineChart2, lineChart3);
        Scene scene = new Scene(root, 1500, 700);
        stage.setScene(scene);
        stage.show();

        System.out.println("Bisection Method: ");
        bisectionMethod(1, 2, .000001, 1000, lineCharts);

        System.out.println("\n\nFixed Point Iteration: ");
        fixedPointIteration(2, .000001, 1000, lineCharts);

        System.out.println("\n\nNewton Method: ");
        NewtonMethod(1.5, .000001, 1000, lineCharts);

        System.out.println("\n\nSecant Method: ");
        SecantMethod(1, 2, .000001, 1000, lineCharts);

    }

    /* The bisection method is otherwise known as the binary search method (49).
     * Based on IVT, where f(a) and f(b) have opposite signs.
     */
    public static void bisectionMethod(double a, double b, double TOL, int N, ArrayList<LineChart> lineCharts) { // Where N = iterations
        double p = 0;
        double FA = Math.pow(a, 4) - (3 * (Math.pow(a, 2))) - 3; // FA = f(a)
        double FP;
        double prev;

        ArrayList<XYChart.Series> SeriesArray = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SeriesArray.add(new XYChart.Series());
            SeriesArray.get(i).setName("Bisection");
            lineCharts.get(i).getData().add(SeriesArray.get(i));
        }

        boolean isLeft = false;
        for (int i = 1; i <= N; i++) {
            p = a + (b - a) / 2; // Compute p(i)
            FP = Math.pow(p, 4) - (3 * (Math.pow(p, 2))) - 3;

            System.out.printf("Value of p at %d iteration(s): %.8f\n", i, p);

            prev = isLeft ? a : b;
            ErrorCalculation(i, p, prev, lineCharts, SeriesArray);

            if (FP == 0 || (b - a)/2 < TOL) {
                System.out.printf("\nFinal value of p at %d iteration(s): %.8f", i, p); // Procedure completed successfully
                break;
            }

            if (FA * FP > 0) { // Explain TOL here (50).
                a = p;
                FA = FP;
                isLeft = true;
            } else {
                b = p;
                isLeft = false;
            }
        } // try call exception here
    }

    /* Approximate a fixed point for the function through sequencing (59-60) instead of
     * solving for p = g(p).
     */
    public static void fixedPointIteration(double p, double TOL, int N, ArrayList<LineChart> lineCharts) { // Initial approx p, tolerance TOL, N = iterations
        double next = 0;

        ArrayList<XYChart.Series> SeriesArray = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SeriesArray.add(new XYChart.Series());
            SeriesArray.get(i).setName("Fixed-Point");
            lineCharts.get(i).getData().add(SeriesArray.get(i));
        }

        for (int i = 1; i <= N; i++) {
            next = Math.pow(( (3 * Math.pow(p, 2)) + 3 ), .25); // p = g(p0)
            ErrorCalculation(i, next, p, lineCharts, SeriesArray);
            System.out.printf("Value of p at %d iteration(s): %.8f\n", i, next);

            if ((Math.abs(next - p)) < TOL) { // Successful completion of procedure
                System.out.printf("\nFinal value of p after %d iterations: %.8f", i, next);
                break;
            }
            p = next;
        }
        // Try call exception here
        //System.out.printf("The method failed after %d iterations: ", N);
    }

    // SEE PAGE 67 FOR TOL LEVELS
    /*
     * To find a solution to f(x) = 0 given an approximation p0 (67).
     */
    public static void NewtonMethod(double p, double TOL, int N, ArrayList<LineChart> lineCharts) {
        double next = 0;

        ArrayList<XYChart.Series> SeriesArray = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SeriesArray.add(new XYChart.Series());
            SeriesArray.get(i).setName("Newton");
            lineCharts.get(i).getData().add(SeriesArray.get(i));
        }

        for (int i = 1; i <= N; i++) {
            next = p - (( Math.pow(p, 4) - 3 * (Math.pow(p, 2)) - 3 )/( 4 * (Math.pow(p, 3)) - (6 * p))); // p - f(p) / f'(p)
            ErrorCalculation(i, next, p, lineCharts, SeriesArray);
            System.out.printf("Value of p at %d iteration(s): %.8f\n", i, next);

            if (Math.abs(next - p) < TOL) {
                System.out.printf("\nFinal value of p after %d iterations: %.8f", i, next);
                break;
            }
            p = next;
        }
        // Try call exception here
    }

    /*
     * The secant method is used when arithmetic operations for f' are too complex (70-71).
     * To circumvent this, a slight variation is used to find the secant line.
     */
    public static void SecantMethod(double p0, double p1, double TOL, int N, ArrayList<LineChart> lineCharts) {
        double q0 = (Math.pow(p0, 4)) - (3* (Math.pow(p0, 2))) - 3; // f(p0)
        double q1 = (Math.pow(p1, 4)) - (3* (Math.pow(p1, 2))) - 3; // f(p1)
        double next = 0;

        ArrayList<XYChart.Series> SeriesArray = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SeriesArray.add(new XYChart.Series());
            SeriesArray.get(i).setName("Secant");
            lineCharts.get(i).getData().add(SeriesArray.get(i));
        }

        for (int i = 1; i <= N; i++) {
            next = p1 - q1 * (p1 - p0) / (q1 - q0);
            ErrorCalculation(i, next, p1, lineCharts, SeriesArray);

            System.out.printf("Final value of p at %d iteration(s): %.8f\n", i, next);
            if (Math.abs(next - p1) < TOL) {
                System.out.printf("\nFinal value of p after %d iterations: %.8f", i, next);
                break;
            }
            // Update p0, q0, p1, q1
            p0 = p1;
            q0 = q1;
            p1 = next;
            q1 = (Math.pow(next, 4)) - (3* (Math.pow(next, 2))) - 3; // f(p)
        }
    }

    public static void ErrorCalculation(int N, double p, double prev, ArrayList<LineChart> lineCharts, ArrayList<XYChart.Series> seriesArrayList) {
        double err2_rate = Math.abs(p - prev);
        seriesArrayList.get(1).getData().add(new XYChart.Data(N, err2_rate));

        double err1_rate = (Math.abs(p-prev)/p);
        seriesArrayList.get(0).getData().add(new XYChart.Data(N, err1_rate));

        double err3_rate = Math.abs( Math.pow(p, 4) - ( 3 * Math.pow(p, 2) ) - 3 );
        seriesArrayList.get(2).getData().add(new XYChart.Data(N, err3_rate));
    }


}

