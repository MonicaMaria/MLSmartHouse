/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartHouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        
        List<Double> input = new ArrayList<>();
        for( int i = 0; i < 31; i++ ) {
                input.add(0.0);
        }
        input.set(0, 1.0);
        input.set(9, 1.0);

        List<Double> desiredOutput = new ArrayList<>();
        desiredOutput.add(1.0);
        desiredOutput.add(-1.0);
        desiredOutput.add(-1.0);
        desiredOutput.add(-1.0);

        NeuralNetwork network = new NeuralNetwork();

        network.setInputLayer(input);
        network.addOutputLayer(4);
        network.addNeurons();
        //network.setDesiredOutput(desiredOutput);

        network.start();
        
    }
    
}
