package com.company;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WorkerThread implements Runnable {

    private static int num = 10000;
    private String command;

    public WorkerThread(String s){
        this.command=s;
    }

    public static double getPi(int numThrows){
        int inCircle= 0;
        for(int i= 0;i < numThrows;i++){
            //a square with a side of length 2 centered at 0 has
            //x and y range of -1 to 1
            double randX= (Math.random() * 2) - 1;//range -1 to 1
            double randY= (Math.random() * 2) - 1;//range -1 to 1
            //distance from (0,0) = sqrt((x-0)^2+(y-0)^2)
            double dist= Math.sqrt(randX * randX + randY * randY);
            //^ or in Java 1.5+: double dist= Math.hypot(randX, randY);
            if(dist < 1){//circle with diameter of 2 has radius of 1
                inCircle++;
            }
        }
        return 4.0 * inCircle / numThrows;
    }

    @Override
    public void run() {
        System.out.println(getPi(num )+ " " + Thread.currentThread().getName() + " iterations: " + num);
        num+= 20000;

    }



    @Override
    public String toString(){
        return this.command;
    }
}

public class Main {



    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            Runnable worker = new WorkerThread("Thread #" + i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");


    }


}
