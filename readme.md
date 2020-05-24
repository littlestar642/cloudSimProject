# Overview 
This project implements a paper named "An Greedy-Based Job Scheduling Algorithm in Cloud Computing". In this project we make use of cloudsim to make the simulations. We have two types of jobs, namely, Time type jobs and Bandwidth type jobs with an expected amount of time required (expectedTime) and bandwidth required (expectedBandwidth) respectively. Our first and foremost task is preprocessing our jobs to time type and bandwidth type. Then we use a scheduling stratergy which will be explained below. Finally we calculated the Justice Evaluation Function (JEF) value of each of the jobs (also shown below) and thereby decide the priority of the jobs. In the following iterations, we do the calculations again in an order decided by the priority. 

### Greedy Stratergy
We use a basic greedy stratergy which dictates to take a decision which favours the current situation and may not necessarily lead to a global optimum. We assign the job with the least bandwidth to the Virtual Machine with most bandwidth, the second least job with second least Virtual Machine and so on. We follow a similar process for the time type jobs. We submit this permutation to the broker and start the cloudsim simulation. We get the start time (startTime) and end time (endTime) from this simulation. We calculate the Time Preference (TP) for every time type job. It can be calculated as:

![](https://latex.codecogs.com/gif.latex?TP_%7Bi%7D%3D%20%5Cfrac%7BTR_%7Bi%7D%7D%7BTR_%7Bmax%7D-TR_%7Bmin%7D&plus;1%7D) 

Here,  ![](https://latex.codecogs.com/gif.latex?TR_%7Bi%7D%3D%20T_%7BiFin%7D-T_%7BiStart%7D),      ![](https://latex.codecogs.com/gif.latex?T_%7BiFin%7D) is the finish time of the ![](https://latex.codecogs.com/gif.latex?i%5E%7Bth%7D) job. ![](https://latex.codecogs.com/gif.latex?TR_%7Bmax%7D) if the time taken by the slowest job and ![](https://latex.codecogs.com/gif.latex?TR_%7Bmin%7D) is the time take by the fastest job. 

After that we find the Expectation Time Preference (ETP) for each of the jobs. We calculate it for the ![](https://latex.codecogs.com/gif.latex?i%5E%7Bth%7D) job by:

![](https://latex.codecogs.com/gif.latex?ETP_%7Bi%7D%3D%5Cfrac%7BETR_%7Bi%7D%7D%7BETR_%7Bmax%7D-ETR_%7Bmin%7D&plus;1%7D)

Here, ![](https://latex.codecogs.com/gif.latex?ETR_%7Bi%7D) is the expected time of the ![](https://latex.codecogs.com/gif.latex?i%5E%7Bth%7D) job. ![](https://latex.codecogs.com/gif.latex?ETR_%7Bmax%7D) and ![](https://latex.codecogs.com/gif.latex?ETR_%7Bmin%7D) are the maximum and minimum values respectively. 

We finally calculate the Justice Evaltion Function (JEF) for all of the jobs. We calculate it by the following way: 

![](https://latex.codecogs.com/gif.latex?JEF_%7Bi%7D%3D%5CTheta%20*%28TP_%7Bi%7D/ETP_%7Bi%7D%29)

Here, ![](https://latex.codecogs.com/gif.latex?%5CTheta)  denotes a constant ![](https://latex.codecogs.com/gif.latex?%280%3C%20%5CTheta%5Cleq%201%29). When JEF is zero, it achieves fairness. Others are not fair. The role of the function is to judge the outcome of the allocation resources whether fair or not. So we intend to minimize the JEF. To do this we introduce a priority and assign a higher priority to the job with the lowest JEF and in the following iteration, we assign the jobs to the Virtual machines according to this priority. We repeat the same process and perform any number of intended iteration till the JEF is satisfactory. We perform a similar process for the jobs of bandwidth type

## Steps to run
In the main folder (i.e cloudSimProject), you will find three folders, namely org, source, and screenshots and two files namely 5.pdf and readme.md. The file 5.pdf is the research paper on which the project was built whereas the readme.md contains the complete details regarding the project. The screenshot folder contains the screenshots of the project. The main files to be compiled and run is present in the source folder. 
The first step is to compile and run the file named index.java. This will lead to the main page which will look like this: 
![Main Page](https://user-images.githubusercontent.com/30263342/82764682-38644100-9e2e-11ea-98ff-b27e9a391a7f.png)

After clicking the allocate button given above, we will get the output of the simulation which will be as follows:

![Result Page](https://user-images.githubusercontent.com/30263342/82764661-12d73780-9e2e-11ea-9b79-48963d3783e1.png)

The first iteration contains the output of the time-type and bandwidth-type job whereas all the following iterations are of time-type which follow the method explained above. 


In this way, we have implemented the given research paper.