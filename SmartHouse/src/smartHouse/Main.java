/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartHouse;

import java.io.FileReader;
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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        // TODO code application logic here
        
        NeuralNetwork network = new NeuralNetwork();
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("config.json"));

        JSONObject configObject = (JSONObject) obj;
        JSONObject inputobj = (JSONObject) configObject.get("input");
        
        JSONArray outputRetea = (JSONArray) configObject.get("output");
        network.addOutputLayer(outputRetea.size());
        List<Double> desiredOutput = new ArrayList<>();
        for(int i = 0; i < outputRetea.size(); i++)
            desiredOutput.add(-1.0);
        
        List<Double> input = new ArrayList<>();
        for(Iterator iterator = inputobj.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            JSONArray arr = (JSONArray) inputobj.get(key);
            for( int i = 0; i < arr.size(); i++ ) {
                input.add(0.0);
            }
        }
        
        JSONObject paramRetea = (JSONObject) parser.parse(new FileReader("inputs.js"));
        
        for(Iterator iterator = paramRetea.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            int idx = outputRetea.indexOf(key);
            int offset = 0;
            if( idx >= 0 )
            {
                desiredOutput.set(idx, 1.0);
                JSONObject inputRetea = (JSONObject) paramRetea.get(key);
                for( Iterator it = inputRetea.keySet().iterator(); it.hasNext(); ) {
                    String inKey = (String) it.next();
                    //System.out.println(inputRetea.get(inKey));
                    JSONArray inArr = (JSONArray) inputobj.get(inKey);
                    //System.out.println(inArr);
                    int inputInd = inArr.indexOf(inputRetea.get(inKey));
                    inputInd = inputInd + offset;
                    
                    input.set(inputInd, 1.0);
                    
                    offset = offset + inArr.size();
                }
            }
        }
        System.out.println(input);
        System.out.println(desiredOutput);
        
        network.setInputLayer(input);
        network.addNeurons();
        
        NeuralNetwork net2 = new NeuralNetwork();
        net2.addOutputLayer(3);
        net2.setInputLayer(input);
        net2.addNeurons();
        
        network.setDesiredOutput(desiredOutput);
        network.start();
        
        net2.start();
    }
    
}
