/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartHouse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Monica
 */
public class LearningRule {

    private final double trainConstant = 0.01;
    private final NeuralNetwork network;
    private List<Neuron> trainingSet;

    public LearningRule(NeuralNetwork netw) {
        network = netw;
        trainingSet = new ArrayList<>();
    }

    public void setTrainingSet(List<Neuron> neuronsTrainingSet) {
        trainingSet = neuronsTrainingSet;
    }

    public List<Neuron> getTrainingSet() {
        return trainingSet;
    }

    public void train() {
        for (int i = 0; i < trainingSet.size(); i++)
            trainingSet.get(i).train(network.getDesiredOutput().get(i), trainConstant);
    }

    public void learn() {
        train();
    }
}
