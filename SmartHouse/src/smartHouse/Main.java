/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartHouse;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Monica
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {
        // TODO code application logic here
        NeuralNetwork network = new NeuralNetwork(true, null);
        network.start();
        NeuralNetwork net2 = new NeuralNetwork(false, "input.json");
        net2.start();
    }
    
    public String getSuggestionList(NeuralNetwork net) {
        return net.suggestions;
    }
}
