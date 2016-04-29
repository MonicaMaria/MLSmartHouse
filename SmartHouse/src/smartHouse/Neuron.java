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
public class Neuron {

    private List<Double> weights;
    private List<Double> inputs;

    public Neuron() {
        weights = new ArrayList<>();
    }
    
    public Neuron(List<Double> inputLayer) {
        weights = new ArrayList<>();
        inputs = inputLayer;
    }

    public double compute() {
        double sum = 0;
        for (int i = 0; i < inputs.size(); i++) {
            sum += this.weights.get(i) * inputs.get(i);
        }
        if (sum > 0) {
            return 1;
        } else {
            return -1;
        }
    }

     
    public void randomize()
    {
        for (int i = 0; i < inputs.size(); i++) {
            // de facut random intre [-1, 1)
            weights.add(Math.random() * 2 - 1);
        }
    }

     
    public void train(double desiredOutput, double trainConstant)
    {
        double output = this.compute();
        double err = desiredOutput - output;
        double aux;
        for (int i = 0; i < weights.size(); i++) {
            aux = weights.get(i);
            aux += trainConstant * err * inputs.get(i);
            weights.set(i, aux);
        }
    }

     
    public void setWeights(List<Double> weights)
    {
        for (int i = 0; i < weights.size(); i++) {
            this.weights.set(i,weights.get(i));
        }
    }
    
    public List<Double> getWeights() {
        return this.weights;
    }

}
