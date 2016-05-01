/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartHouse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public void setInputLayer(List<Double> input) {
        for (int i = 0; i < input.size(); i++) {
            inputLayer.add(input.get(i));
        }
    }

    public List<Double> getInputLayer() {
        return inputLayer;
    }

    public void addOutputLayer(int num) {
        for (int i = 0; i < num; i++) {
            outputLayer.add(0.0);
        }
    }

    public void addNeurons() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("neurons.json"));

        JSONObject jsonObject = (JSONObject) obj;
        
        if (jsonObject.size() > 0) {
            trainLoaded = true;
        }
        else trainLoaded = false;

        if (outputLayer.isEmpty()) {
            System.out.println("Add neurons error! Empty output layer..");
            return;
        }
        

        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = new Neuron(inputLayer);
            if (trainLoaded == false) {
                neuron.randomize();
            } 
            else {
                String label = "n_" + i;
                List<Double> weights = new ArrayList<>();
                //weights = trainLoad[label];
                JSONArray wg = (JSONArray) jsonObject.get(label);
                
                for( int k = 0; k < wg.size(); k++ ) {
                    double w = (double)wg.get(k);
                    weights.add(w);
                }
                
                if (weights.isEmpty()) {
                    neuron.randomize();
                } else {
                    neuron.setWeights(weights);
                }
            }
            hiddenLayer.add(neuron);
        }

    }

    public void setDesiredOutput(List<Double> output) {
        for (int i = 0; i < output.size(); i++) {
            desiredOutput.add(output.get(i));
        }
        startLearn = true;
    }

    public List<Double> getDesiredOutput() {
        return desiredOutput;
    }

    public void updateWeights(List<Neuron> trainingSet) {
        if (trainingSet.size() != hiddenLayer.size()) {
            System.out.println("train erorr. more neurons than required!");
            return;
        }
        //hiddenLayer = trainingSet;
        for (int i = 0; i < trainingSet.size(); i++) {
            hiddenLayer.set(i, trainingSet.get(i));
        }
    }

    public void saveState() throws IOException {
        JSONObject obj = new JSONObject();

        for (int i = 0; i < hiddenLayer.size(); i++) {
            String label = "n_" + i;
            Neuron neur = hiddenLayer.get(i);
            JSONArray list = new JSONArray();
            for(int j=0;j<neur.getWeights().size();j++)
                list.add(neur.getWeights().get(j)); 
            obj.put(label, list);
            
            //outJson[label] = hiddenLayer.get(i).weights;
        }

        try (FileWriter file = new FileWriter("neurons.json")) {
            file.write(obj.toJSONString());
            System.out.println(obj.toJSONString());
            file.flush();
        }
        
        //fs.writeFileSync('neurons.json', JSON.stringify(outJson, null, 4)
    }

	 
    public void start() throws IOException {
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
                outputLayer.set(i, output);
            }
            //punem in fis json
            //console.log(root.outputLayer);
        }
    }
}
