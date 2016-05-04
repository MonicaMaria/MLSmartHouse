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
    
    public String suggestions;
    public int outputSize;

    private final JSONParser parser = new JSONParser();
    private JSONObject config;
    private String fileInput;

    public NeuralNetwork(boolean learn, String file) throws IOException, ParseException {
        startLearn = false;
        inputLayer = new ArrayList<>();
        outputLayer = new ArrayList<>();
        hiddenLayer = new ArrayList<>();
        desiredOutput = new ArrayList<>();
        trainLoaded = false;
        startLearn = learn;
        fileInput = file;

        config = (JSONObject) parser.parse(new FileReader("config.json"));
    }

    private void configInputLayer() {
        JSONObject inputobj = (JSONObject) config.get("input");
        List<Double> input = new ArrayList<>();
        for (Iterator iterator = inputobj.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            JSONArray arr = (JSONArray) inputobj.get(key);
            for (int i = 0; i < arr.size(); i++)
                input.add(0.0);
        }
        this.setInputLayer(input);
    }

    private void configOutputLayer() {
        JSONArray outputRetea = (JSONArray) config.get("output");
        this.outputSize = outputRetea.size();
        this.addOutputLayer(outputRetea.size());
        if (startLearn == true) {
            for (int i = 0; i < outputRetea.size(); i++)
                desiredOutput.add(-1.0);
        }
    }

    private void setInputLayer(List<Double> input) {
        for (int i = 0; i < input.size(); i++)
            inputLayer.add(input.get(i));
    }

    public List<Double> getInputLayer() {
        return inputLayer;
    }

    public void addOutputLayer(int num) {
        for (int i = 0; i < num; i++)
            outputLayer.add(0.0);
    }

    public void addNeurons() throws IOException, ParseException {
        //JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("neurons.json"));
        JSONObject jsonObject = (JSONObject) obj;
        trainLoaded = jsonObject.size() > 0;
        if (outputLayer.isEmpty()) {
            System.out.println("Add neurons error! Empty output layer..");
            return;
        }
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = new Neuron(inputLayer);
            if (trainLoaded == false) {
                System.out.println("Randomize weights");
                neuron.randomize();
            } else {
                String label = "n_" + i;
                List<Double> weights = new ArrayList<>();
                //weights = trainLoad[label];
                JSONArray wg = (JSONArray) jsonObject.get(label);

                for (int k = 0; k < wg.size(); k++) {
                    double w = (double) wg.get(k);
                    weights.add(w);
                }

                if (weights.isEmpty())
                    neuron.randomize();
                else
                    neuron.setWeights(weights);
            }
            hiddenLayer.add(neuron);
        }
    }

    public void setDesiredOutput(List<Double> output) {
        for (int i = 0; i < output.size(); i++)
            desiredOutput.add(output.get(i));
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
        for (int i = 0; i < trainingSet.size(); i++)
            hiddenLayer.set(i, trainingSet.get(i));
    }

    private void saveState() throws IOException {
        JSONObject obj = new JSONObject();

        for (int i = 0; i < hiddenLayer.size(); i++) {
            String label = "n_" + i;
            Neuron neur = hiddenLayer.get(i);
            JSONArray list = new JSONArray();
            for (int j = 0; j < neur.getWeights().size(); j++)
                list.add(neur.getWeights().get(j));
            obj.put(label, list);
            //outJson[label] = hiddenLayer.get(i).weights;
        }

        try (FileWriter file = new FileWriter("neurons.json")) {
            file.write(obj.toJSONString());
            //System.out.println(obj.toJSONString());
            file.flush();
        }
    }

    private void saveOutput() throws IOException {
        JSONObject saveOutput = new JSONObject();
        JSONArray outputRetea = (JSONArray) config.get("output");
        JSONArray outList = new JSONArray();
        for (int i = 0; i < outputLayer.size(); i++) {
            if (outputLayer.get(i) == 1.0)
                outList.add(outputRetea.get(i));
        }
        saveOutput.put("suggestions", outList);

        try (FileWriter file = new FileWriter("suggestions.json")) {
            file.write(saveOutput.toJSONString());
            System.out.println(saveOutput.toJSONString());
            this.suggestions = saveOutput.toJSONString();
            file.flush();
        }
    }

    private void setNetworkParams() throws IOException, ParseException {
        JSONObject paramRetea = (JSONObject) parser.parse(new FileReader(this.fileInput));
        JSONObject inputconfig = (JSONObject) config.get("input");
        int offset = 0;
        for (Iterator it = paramRetea.keySet().iterator(); it.hasNext();) {
            String inKey = (String) it.next();
            JSONArray inArr = (JSONArray) inputconfig.get(inKey);
            int inputInd = inArr.indexOf(paramRetea.get(inKey));
            inputInd = inputInd + offset;
            inputLayer.set(inputInd, 1.0);
            offset = offset + inArr.size();
        }
    }

    private void setLearnParams() throws IOException, ParseException {

        JSONObject paramRetea = (JSONObject) parser.parse(new FileReader("train.json"));
        JSONObject inputobj = (JSONObject) config.get("input");
        JSONArray outputRetea = (JSONArray) config.get("output");

        for (Iterator iterator = paramRetea.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            int idx = outputRetea.indexOf(key);
            int offset = 0;
            if (idx >= 0) {
                desiredOutput.set(idx, 1.0);
                JSONObject inputRetea = (JSONObject) paramRetea.get(key);
                for (Iterator it = inputRetea.keySet().iterator(); it.hasNext();) {
                    String inKey = (String) it.next();
                    //System.out.println(inputRetea.get(inKey));
                    JSONArray inArr = (JSONArray) inputobj.get(inKey);
                    //System.out.println(inArr);
                    int inputInd = inArr.indexOf(inputRetea.get(inKey));
                    inputInd = inputInd + offset;

                    inputLayer.set(inputInd, 1.0);

                    offset = offset + inArr.size();
                }
            }
        }
    }

    public void start() throws IOException, ParseException {

        this.configInputLayer();
        this.configOutputLayer();
        this.addNeurons();

        if (startLearn == true) {
            this.setLearnParams();
            LearningRule learningRule = new LearningRule(this);
            learningRule.setTrainingSet(hiddenLayer);
            learningRule.learn();
            updateWeights(learningRule.getTrainingSet());
            saveState();
        } else {
            this.setNetworkParams();
            for (int i = 0; i < hiddenLayer.size(); i++) {
                double output = hiddenLayer.get(i).compute();
                outputLayer.set(i, output);
            }
            System.out.println(outputLayer);
            saveOutput();
        }
    }
}
