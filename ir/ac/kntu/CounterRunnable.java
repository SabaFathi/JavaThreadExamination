package ir.ac.kntu;

public class CounterRunnable implements Runnable{
    private double sumOfInputArray;
    private final double[] input;
    private int from;
    private int to;

    public CounterRunnable(double[] input, int from, int to) {
        this.input = input;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        //Arrays.stream(input).collect(Collectors.toList()).forEach((n)->{sumOfInputArray += n;});
        for(int i=from ; i<to ; i++){
            sumOfInputArray += input[i];
            //tarkib(5,5);
        }
    }

    public double getSumOfInputArray() {
        return sumOfInputArray;
    }

    public static long tarkib(int n, int r){
        if(n<=0 || r<=0){
            return 1;
        }

        return tarkib(n-1,r) + tarkib(n-1, r-1);
    }
}
