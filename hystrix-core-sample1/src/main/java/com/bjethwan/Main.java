package com.bjethwan;

public class Main {
	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			CommandHelloWorld command = new CommandHelloWorld("Bipin: "+i);
			String result = command.execute(); 
			System.out.println("Result: " + result + (command.getExecutionException()==null?"":command.getExecutionException()));
		}
	}
}
