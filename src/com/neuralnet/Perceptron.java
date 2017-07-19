package com.neuralnet;

import com.neuralnet.Connection;
import java.util.ArrayList;

public class Perceptron {
	
	protected double output;
	private ArrayList<Connection> connections;
	private boolean hasBias = false;
	
	//perceptron simple
	public Perceptron(){
		output = 0;
		connections = new ArrayList<Connection>();
		hasBias = false;
	}
	
	//perceptron con bias
	public Perceptron(int i){
		output = i;
		connections = new ArrayList<Connection>();
		hasBias = true;
	}
	
	//funcion para calcular salida de la neurona
	//sumar entradas por los pesos y pasar por sigmoide
	public void calcOutput(){
		if (hasBias){
			//no hacer nada
		} else {
			double sum = 0;
			double bias = 0;
			//System.out.println("Conexiones: " + connections.size());
			for (int i=0;i<connections.size();i++){
				Connection con = (Connection) connections.get(i);
				Perceptron from = con.getFrom();
				Perceptron to = con.getTo();
				// conexión hacia nosotros
				// ignorar conexiones a las que enviamos conexion
				if (to == this){
					if (from.hasBias){ // no es necesario, pero se puede necesitar en algun momento
						bias = from.getOutput()*con.getWeight();
					} else {
						sum += from.getOutput()*con.getWeight();
					}
				}
			}
			// pasar sumar por la sigmoide
			output = f(bias+sum);
		}
	}
	
	public void addConnection(Connection c){
		connections.add(c);
	}
	
	public double getOutput(){
		return output;
	}
	
	// función de sigmoide
	public static double f(double x){
		return 1.0f / (1.0f+(float)Math.exp(-x));
	}
	
	public ArrayList<Connection> getConnections(){
		return connections;
	}

}
