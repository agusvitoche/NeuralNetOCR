package com.neuralnet;

public class Connection {
	
	private Perceptron from = null;//de donde viene la conexi�n
	private Perceptron to = null;//hacia donde va
	private double weight;//peso de la conexi�n
	//private double LEARNING_CONSTANT = 0.1;//constante de aprendizaje
	
	//constructor con peso aleatorio
	public Connection(Perceptron from, Perceptron to){
		this.from = from;
		this.to= to;
		weight = random(-1,1);
	}
	
	//constructor indicando peso
	public Connection(Perceptron from, Perceptron to, double weight){
		this.from = from;
		this.to= to;
		this.weight = weight;
	}
	
	public Perceptron getFrom(){
		return from;
	}
	
	public Perceptron getTo(){
		return to;
	}
	
	public double getWeight(){
		return weight;
	}
	
	//cambiar el peso de la conexi�n
	//deltaWeight = error * input
	public void changeWeight(double deltaWeight){
		weight += deltaWeight;
	}
	
	//m�todo numero aleatorio
	private double random(int min, int max){
		return min+Math.random()*(max-min);
	}

}
