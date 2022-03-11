package ir.ac.kntu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class eventChart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(20);

        addChartByLength(root);
        addChartByNThread(root);

        System.out.println("Chart Is Prepared.");

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Chart Is Drawing... \nPlease Wait...");

        launch();
    }

    private Long getSeqTime(int arrayLength){
        final int length = 5;
        long[] times = new long[length];
        double[] input = ArraySum.createArray(arrayLength);

        for(int i=0 ; i<length ; i++){
            long start = System.currentTimeMillis();
            ArraySum.seqArraySum(input);
            long finish = System.currentTimeMillis();

            times[i] = finish-start;
        }

        return getAverage(times);
    }

    private Long getParTime(int arrayLength, int numberOfThreads){
        final int length = 5;
        long[] times = new long[length];
        double[] input = ArraySum.createArray(arrayLength);

        for(int i=0 ; i<length ; i++){
            long start = System.currentTimeMillis();
            ArraySum.parArraySum(input, numberOfThreads);
            long finish = System.currentTimeMillis();

            times[i] = finish-start;
        }

        return getAverage(times);
    }

    private long getAverage(long... numbers){
        int length = numbers.length;
        long average = 0;

        for(int i=0 ; i<length ; i++){
            average += numbers[i];
        }

        average /= length;

        return average;
    }

    private void addChartByLength(Pane root){
        final int UPPER_BOUND_LENGTH = 10_000_000;
        final int TICK_UNIT_LENGTH = 1_000_000;
        final int TIME_SCALE = 1;

        final NumberAxis dataSizeAxis = new NumberAxis(0, UPPER_BOUND_LENGTH, TICK_UNIT_LENGTH);
        final NumberAxis timeAxis = new NumberAxis(0, 100, 10);//0, 1000, 100

        dataSizeAxis.setLabel("Array Length");
        timeAxis.setLabel("Time(milli second*" + TIME_SCALE + ")");

        LineChart<Number, Number> lineChart = new LineChart<>(dataSizeAxis, timeAxis);
        lineChart.setTitle("Chart1");

        XYChart.Series<Number, Number> sequenceSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> parallelSeries4Thread = new XYChart.Series<>();
        XYChart.Series<Number, Number> parallelSeries32Thread = new XYChart.Series<>();
        XYChart.Series<Number, Number> parallelSeries100Thread = new XYChart.Series<>();

        sequenceSeries.setName("Sequential Summation");
        parallelSeries4Thread.setName("Parallel Summation (4 Threads)");
        parallelSeries32Thread.setName("Parallel Summation (32 Threads)");
        parallelSeries100Thread.setName("Parallel Summation (100 Threads)");

        for(int i=0 ; i<=UPPER_BOUND_LENGTH ; i+=TICK_UNIT_LENGTH){
            sequenceSeries.getData().add( new XYChart.Data<>(i, TIME_SCALE*getSeqTime(i)) );
            parallelSeries4Thread.getData().add( new XYChart.Data<>(i, TIME_SCALE*getParTime(i, 4)) );
            parallelSeries32Thread.getData().add( new XYChart.Data<>(i, TIME_SCALE*getParTime(i, 32)) );
            parallelSeries100Thread.getData().add( new XYChart.Data<>(i, TIME_SCALE*getParTime(i, 100)) );
        }

        lineChart.getData().add(sequenceSeries);
        lineChart.getData().add(parallelSeries4Thread);
        lineChart.getData().add(parallelSeries32Thread);
        lineChart.getData().add(parallelSeries100Thread);

        root.getChildren().add(lineChart);
    }

    private void addChartByNThread(Pane root){
        final int UPPER_BOUND_Thread = 100;
        final int TICK_UNIT_Thread = 5;
        final int TIME_SCALE = 1;

        final NumberAxis nThreadAxis = new NumberAxis(0, UPPER_BOUND_Thread, TICK_UNIT_Thread);
        final NumberAxis timeAxis = new NumberAxis(0, 20, 2);//0 , 100, 5

        timeAxis.setLabel("Time(milli second*" + TIME_SCALE + ")");
        nThreadAxis.setLabel("Number Of Threads");

        LineChart<Number, Number> lineChart = new LineChart<>(nThreadAxis, timeAxis);
        lineChart.setTitle("Chart2");

        XYChart.Series<Number, Number> array1_000 = new XYChart.Series<>();
        XYChart.Series<Number, Number> array100_000 = new XYChart.Series<>();
        XYChart.Series<Number, Number> array1_000_000 = new XYChart.Series<>();

        array1_000.setName("Array Length 1_000");
        array100_000.setName("Array Length 100_000");
        array1_000_000.setName("Array Length 1_000_000");

        for(int i=2 ; i<=UPPER_BOUND_Thread ; i+=TICK_UNIT_Thread){
            array1_000.getData().add(new XYChart.Data<>(i, TIME_SCALE*getParTime(1_000, i)));
            array100_000.getData().add(new XYChart.Data<>(i, TIME_SCALE*getParTime(100_000, i)));
            array1_000_000.getData().add(new XYChart.Data<>(i, TIME_SCALE*getParTime(1_000_000, i)));
        }

        lineChart.getData().add(array1_000);
        lineChart.getData().add(array100_000);
        lineChart.getData().add(array1_000_000);

        root.getChildren().add(lineChart);
    }
}
