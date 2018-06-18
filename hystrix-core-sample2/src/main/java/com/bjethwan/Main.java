package com.bjethwan;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {

		try(PrintStream out 
				= new PrintStream(
						new FileOutputStream("C:\\Users\\KH1783\\results.txt"))) 
		{
			out.println("start");
			ExecutorService executorService = Executors.newFixedThreadPool(15);

			for (int k=0;k<15;k++) {
				executorService.execute(new Runnable() {
					public void run() {
						for(int i=0;i<100;i++) {
							CommandHelloWorld command = new CommandHelloWorld("Bipin: "+i);
							String result = command.execute(); 
							out.println("Result: " + result + (command.getExecutionException()));
						}
					}
				});
			}
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
