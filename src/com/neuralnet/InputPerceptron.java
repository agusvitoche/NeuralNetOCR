package com.neuralnet;

public class InputPerceptron extends Perceptron {
	
	public InputPerceptron(){
		super();
	}
	
	public InputPerceptron(int i){
		super(i);
	}
	
	public void input(double d){
		output = d;
	}

}
