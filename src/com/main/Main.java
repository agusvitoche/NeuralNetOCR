package com.main;

import com.neuralnet.*;
//import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args){
		
		//String [] abecedario = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M","N", "Ñ","O","P","Q","R","S","T","U","V","W", "X","Y","Z"};
		double[] inputs = new double[370];
		NeuralNet redNeuronal = new NeuralNet(1,6,36);
		
		//crear lista de entramiento
		int count=0;
		for (int i=0;i<370;i++){
			double input = (double) count;
			if (count==37){
				count=0;
			} else {
				count++;
			}
			inputs[i] = input;
		}
		
		//llamada metodo entrenamiento
		redNeuronal.train(inputs, inputs);
		
	}

}
