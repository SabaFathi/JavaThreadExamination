# JavaThreadExamination
Comparison between Parallel and Sequential Processing + Visualization


To compare parallel and sequential processing, 4 parameters were considered:
workload (light/heavy task), Task Frequency, number of threads, type of thread management (directly use Thread/use ExecutorService).

So 4 scenarios were investigated:
1- directly used Thread, for light task
2- used ExecutorService, for light task
3- directly used Thread, for heavy task
4- used ExecutorService, for heavy task

In each scenario, 2 charts were drawn:
First, the number of threads is fixed and task frequency varies. Second, task frequency is fixed and the number of threads varies.

The results are as follows:
*note that "ArrayLength" is equal to task frequency.

1- directly used Thread, for light task

![Light-Thread](https://github.com/SabaFathi/JavaThreadExamination/blob/main/outcomes/LightTask_Threads.JPG?raw=true)

2- used ExecutorService, for light task

![Light-ExecutorService](https://github.com/SabaFathi/JavaThreadExamination/blob/main/outcomes/LightTask_ExecutorService.JPG?raw=true)

3- directly used Thread, for heavy task

![Heavy-Thread](https://github.com/SabaFathi/JavaThreadExamination/blob/main/outcomes/HeavyTask_Threads.JPG?raw=true)

4- used ExecutorService, for heavy task

![Heavy-ExecutorService](https://github.com/SabaFathi/JavaThreadExamination/blob/main/outcomes/HeavyTask_ExecutorService.JPG?raw=true)
