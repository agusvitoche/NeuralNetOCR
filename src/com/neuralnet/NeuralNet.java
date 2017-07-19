package com.neuralnet;

import java.util.ArrayList;

public class NeuralNet {

	//capas
	InputPerceptron[] inputLayer;
	HiddenPerceptron[] hiddenLayer;
	OutputPerceptron[] outputLayer;
	
	public static final double LEARNING_CONSTANT = 0.5f;//constante de aprendizaje
	
	//constructor
	public NeuralNet(int numInputs, int numHiddens, int numOutputs){
		
		inputLayer = new InputPerceptron[numInputs+1];//añadir bias input
		hiddenLayer = new HiddenPerceptron[numHiddens+1];
		outputLayer = new OutputPerceptron[numOutputs];
		
		//crear neuronas de capa entrada
		for (int i=0;i<inputLayer.length-1;i++){
			inputLayer[i] = new InputPerceptron();
		}
		
		//crear neuronas de capa oculta
		for (int i=0;i<hiddenLayer.length-1;i++){
			hiddenLayer[i] = new HiddenPerceptron();
		}
		
		//crear neuronas bias
		inputLayer[inputLayer.length-1] = new InputPerceptron(1);
		hiddenLayer[hiddenLayer.length-1] = new HiddenPerceptron(1);
		
		//crear neuronas de capa salida
		for (int i=0;i<outputLayer.length-1;i++){
			outputLayer[i] = new OutputPerceptron();
		}
		
		//conectar capa entrada con capa oculta
		for (int i=0;i<inputLayer.length;i++){
			for (int j=0;j<hiddenLayer.length;j++){
				//crear objecto conexion y poner en las dos neuronas
				Connection con = new Connection(inputLayer[i],hiddenLayer[j]);
				inputLayer[i].addConnection(con);
				hiddenLayer[j].addConnection(con);
			}
		}
		
		//conectar capa oculta con capa salida
		for (int i=0;i<hiddenLayer.length;i++){
			for (int j=0;j<outputLayer.length-1;j++){
				//crear objecto conexion y poner en las dos neuronas
				Connection con = new Connection(hiddenLayer[i],outputLayer[j]);
				hiddenLayer[i].addConnection(con);
				outputLayer[j].addConnection(con);
			}
		}
		
	}
	
	public double[] feedForward(double[] inputValues){
		
		InputPerceptron entrada = inputLayer[0];
		
		//introducir valores en capa entrada
		for (int i=0;i<inputValues.length-1;i++){
			entrada.input(inputValues[i]);
		}
		
		//calcular salida de capa oculta
		for (int i=0;i<hiddenLayer.length-1;i++){
			hiddenLayer[i].calcOutput();
		}
		
		//calcular salida de capa salida y rellenar array de salidas
		double[] outputs = new double[outputLayer.length];
		for (int i=0;i<outputLayer.length-1;i++){
			outputLayer[i].calcOutput();
			outputs[i] = outputLayer[i].getOutput();
		}
		
		//return output
		return outputs;
		
	}
	
	//todo-muchas salidas
	public double[] train(double[] inputs, double[] answer){
		
		double[] result = feedForward(inputs);
		
		//comienza correcion de error
		//calcular cambios de peso para perceptrones salida
		double[] deltaOutput = new double[outputLayer.length];
		for (int i=0;i<outputLayer.length;i++){
			deltaOutput[i] = result[i]*(1-result[i])*(answer[i]-result[i]);
		}
		
		//BACKPROPAGATION
		//rellenar conexiones de la capa salida
		ArrayList<ArrayList<Connection>> conexionesOutputLayer = new ArrayList<ArrayList<Connection>>();
		for (int i=0;i<conexionesOutputLayer.size();i++){
			conexionesOutputLayer.add(outputLayer[i].getConnections());
		}
		//aplicar cambio de peso a conexiones entre capa salida y capa oculta
		ArrayList<Connection> conexionesPerceptronSalida = new ArrayList<Connection>();
		for (int i=0;i<conexionesOutputLayer.size();i++){
			conexionesPerceptronSalida = conexionesOutputLayer.get(i);
			for (int j=0;j<conexionesPerceptronSalida.size();j++){
				Connection conexion = (Connection) conexionesPerceptronSalida.get(j);
				Perceptron perceptron = conexion.getFrom();
				double output = perceptron.getOutput();
				double deltaWeight = output*deltaOutput[j];
				conexion.changeWeight(LEARNING_CONSTANT*deltaWeight);
			}
		}
		
		//cambiar pesos capa oculta
		ArrayList<Connection> conexionesPerceptronOculta = new ArrayList<Connection>();
		for (int i=0;i<hiddenLayer.length;i++){
			conexionesPerceptronOculta = hiddenLayer[i].getConnections();
			double sum = 0;
			//sumar deltaOutput*hiddenLayerConnection
			for (int j=0;j<conexionesPerceptronOculta.size();j++){
				Connection conexion = (Connection) conexionesPerceptronOculta.get(j);
				//conexión from capa oculta to capa salida
				if (conexion.getFrom()==hiddenLayer[i]){
					sum += conexion.getWeight()*deltaOutput[j];
				}
			}
			//ajustar los pesos
			//
			for (int j=0;j<conexionesPerceptronOculta.size();j++){
				Connection conexion = (Connection) conexionesPerceptronOculta.get(j);
				//conexión from capa entrada to capa oculta
				if (conexion.getTo()==hiddenLayer[j]){
					double output = hiddenLayer[i].getOutput();
					double deltaHidden = output * (1-output);//sigmoide
					deltaHidden *= sum;//
					Perceptron perceptron = conexion.getFrom();
					double deltaWeight = perceptron.getOutput()*deltaHidden;
					conexion.changeWeight(LEARNING_CONSTANT*deltaWeight);
				}
			}
		}
		
		return result;
		
	}
	
}
