/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartHouse;

import static java.lang.System.console;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Monica
 */
public class NeuralNetwork {

    private List<Double> inputLayer;
    private List<Neuron> hiddenLayer;
    private List<Double> desiredOutput;
    private boolean startLearn;
    private List<Double> outputLayer;
    private boolean trainLoaded;

    public NeuralNetwork() {
        startLearn = false;
        inputLayer = new ArrayList<>();
        outputLayer = new ArrayList<>();
        hiddenLayer = new ArrayList<>();
        desiredOutput = new ArrayList<>();
        trainLoaded = false;
    }
    
    public void setInputLayer(List<Double> input)
    {
        for (int i = 0; i < input.size(); i++) {
            inputLayer.add(input.get(i));
        }
    }
    
    public List<Double> getInputLayer () {
        return inputLayer;
    }
            
 
    public void addOutputLayer(int num)
    {
        for (int i = 0; i < num; i++) {
            outputLayer.add(0.0);
        }
    }

     
    public void addNeurons()
    {
        if (outputLayer.size() == 0) {
            System.out.println("Add neurons error! Empty output layer..");
            return;
        }
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = new Neuron(inputLayer);
            if (trainLoaded == false) {
                neuron.randomize();
            } else {
                String label = "n_" + i;
                List<Double> weights = new ArrayList<>();
                weights = trainLoad[label];
                if (!weights) {
                    neuron.randomize();
                } else {
                    neuron.setWeights(weights);
                }
            }
            hiddenLayer.add(neuron);
        }

    }

     
    public void setDesiredOutput(List<Double> output)
    {
        for (int i = 0; i < output.size(); i++) {
            desiredOutput.add(output.get(i));
        }
        startLearn = true;

    }
    
    public List<Double> getDesiredOutput() {
        return desiredOutput;
    }

     
    public void updateWeights(List<Neuron> trainingSet)
    {
        if (trainingSet.size() != hiddenLayer.size()) {
            System.out.println("train erorr. more neurons than required!");
            return;
        }
        //hiddenLayer = trainingSet;
        for (int i = 0; i < trainingSet.size(); i++) {
            hiddenLayer.set(i,trainingSet.get(i));
        }
    }

    public void saveState()
    {
        var outJson = {};

        for (int i = 0; i < hiddenLayer.size(); i++) {
            String label = "n_" + i;
            outJson[label] = hiddenLayer.get(i).weights;
        }

        fs.writeFileSync('neurons.json'
        , JSON.stringify(outJson, null, 4)
    
    );
	}

	 
    public void start()
    {
        if (startLearn == true) {
            LearningRule learningRule = new LearningRule(this);
            //learningRule.setInputLayer(inputLayer);
            //learningRule.setDesiredOutput(desiredOutput);
            learningRule.setTrainingSet(hiddenLayer);
            learningRule.learn();
            updateWeights(learningRule.getTrainingSet());
            saveState();
        } else {
            for (int i = 0; i < hiddenLayer.size(); i++) {
                double output = hiddenLayer.get(i).compute();
                outputLayer.set(i,output);
            }
            //punem in fis json
            //console.log(root.outputLayer);
        }
    }
}
