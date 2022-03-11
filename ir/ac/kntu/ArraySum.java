package ir.ac.kntu;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArraySum {
    /**
     * Sequentially compute the sum of the values for a given array.
     *
     * @param input Input array
     * @return The sum of the array input
     */
    public static double seqArraySum(final double[] input) {
        double sum = 0;

        for (int i = 0; i < input.length; i++) {
            sum += input[i];
            //CounterRunnable.tarkib(5, 5);
        }

        return sum;
    }
    private static int getNCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Computes the size of each chunk, given the number of chunks to create
     * across a given number of elements.
     *
     * @param nChunks The number of chunks to create
     * @param nElements The number of elements to chunk across
     * @return The default chunk size
     */
    private static int getChunkSize(final int nChunks, final int nElements) {
        // Integer ceil
        return (nElements + nChunks - 1) / nChunks;
    }

    /**
     * Computes the inclusive element index that the provided chunk starts at,
     * given there are a certain number of chunks.
     *
     * @param chunk The chunk to compute the start of
     * @param nChunks The number of chunks created
     * @param nElements The number of elements to chunk across
     * @return The inclusive index that this chunk starts at in the set of
     * nElements
     */
    private static int getChunkStartInclusive(final int chunk,
                                              final int nChunks, final int nElements) {
        final int chunkSize = getChunkSize(nChunks, nElements);
        return chunk * chunkSize;
    }

    /**
     * Computes the exclusive element index that the provided chunk ends at,
     * given there are a certain number of chunks.
     *
     * @param chunk The chunk to compute the end of
     * @param nChunks The number of chunks created
     * @param nElements The number of elements to chunk across
     * @return The exclusive end index for this chunk
     */
    private static int getChunkEndExclusive(final int chunk, final int nChunks,
                                            final int nElements) {
        final int chunkSize = getChunkSize(nChunks, nElements);
        final int end = (chunk + 1) * chunkSize;
        if (end > nElements) {
            return nElements;
        } else {
            return end;
        }
    }

    /**
     * TODO: Modify this method to compute the same sum as seqArraySum, but use
     * Threads. You may assume that the length of the input array is evenly
     * divisible by 2. TODO: Try to find the optimal numberOfThreads
     *
     * @param input Input array
     * @return The sum of the array input
     */
    public static double parArraySum(final double[] input, int numberOfThreads) {
        assert input.length % 2 == 0;

        double result = 0;
        CounterRunnable[] counterRunnables = new CounterRunnable[numberOfThreads];
        //ExecutorService executorService = Executors.newCachedThreadPool();

        Thread[] threads = new Thread[numberOfThreads];

        for(int i=0 ; i<numberOfThreads ; i++){
            int endLimit = getChunkEndExclusive(i, numberOfThreads, input.length);
            int startLimit = getChunkStartInclusive(i, numberOfThreads, input.length);

            counterRunnables[i] = new CounterRunnable(input, startLimit, endLimit);
            //executorService.execute(counterRunnables[i]);

            threads[i] = new Thread(counterRunnables[i]);
            threads[i].start();

        }

        for(Thread thread : threads){
            try{
                thread.join();
            }catch(InterruptedException ex){
                System.out.println("interrupted!");
            }
        }

        //executorService.shutdown();
        //while( !executorService.isTerminated() );

        for(int i=0 ; i<numberOfThreads ; i++){
            result += counterRunnables[i].getSumOfInputArray();
        }

        ////executorService.shutdown();

        return result;
    }

    public static void main(String[] args) {
        long startProcess;
        long endProcess;
        int arrayLength = 10_000_000;
        int numberOfThreads = 25;
        double[] input = createArray(arrayLength);

        System.out.println("cores : " + getNCores());
        System.out.println("result for array length : " + arrayLength + " and number of threads : " + numberOfThreads);
        System.out.println();

        startProcess = System.currentTimeMillis();
        System.out.println(seqArraySum(input));
        endProcess = System.currentTimeMillis();
        System.out.println("sequence process finished in " + (endProcess-startProcess) + " milli seconds.");
        System.out.println();

        startProcess = System.currentTimeMillis();
        System.out.println(parArraySum(input, numberOfThreads));
        endProcess = System.currentTimeMillis();
        System.out.println("parallel process finished in " + (endProcess-startProcess) + " milli seconds.");
        System.out.println();

        eventChart.main(args);

        System.out.println();
        System.out.println("Process Finished Successfully");
    }
    //private static double parallelComputeThread(final Double[] input){
    //    CounterRunnable1 c = new CounterRunnable1(input);
    //    c.run();
    //    return (Double)(c.getSumOfInputArray());
    //}

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    /*private*/public static double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = rand.nextInt(100);
            // Don't allow zero values in the input array to prevent divide-by-zero
            if (input[i] == 0.0) {
                i--;
            }
        }

        return input;
    }

}
