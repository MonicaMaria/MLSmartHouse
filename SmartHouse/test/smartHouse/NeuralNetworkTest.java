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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Monica
 */
public class NeuralNetworkTest {
    
    public NeuralNetworkTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInputLayer method, of class NeuralNetwork.
     */
    @Test
    public void testGetInputLayer() throws Exception {
        System.out.println("getInputLayer");
        NeuralNetwork instance = new NeuralNetwork(true, null);
        List<Double> expResult = new ArrayList<>();
        List<Double> result = instance.getInputLayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addOutputLayer method, of class NeuralNetwork.
     */
    @Test
    public void testAddOutputLayer() throws Exception {
        System.out.println("addOutputLayer");
        int num = 3;
        NeuralNetwork instance = new NeuralNetwork(true, null);
        instance.addOutputLayer(num);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addNeurons method, of class NeuralNetwork.
     */
    @Test
    public void testAddNeurons() throws Exception {
        System.out.println("addNeurons");
        NeuralNetwork instance = new NeuralNetwork(true, null);
        instance.addNeurons();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setDesiredOutput method, of class NeuralNetwork.
     */
    @Test
    public void testSetDesiredOutput() throws Exception {
        System.out.println("setDesiredOutput");
        NeuralNetwork net = new NeuralNetwork(true, null);
        List<Double> output = new ArrayList<>();
        for (int i=0;i<net.outputSize;i++)
            if((i*4+7)%4 < 3) output.add(0.0);
            else output.add(1.0);
        net.setDesiredOutput(output);
        List<Double> result = net.getDesiredOutput();
        assertEquals(result,output);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDesiredOutput method, of class NeuralNetwork.
     */
    @Test
    public void testGetDesiredOutput() throws Exception {
        System.out.println("getDesiredOutput");
        NeuralNetwork instance = new NeuralNetwork(true, null);
        List<Double> expResult = new ArrayList<>();
        List<Double> result = instance.getDesiredOutput();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of updateWeights method, of class NeuralNetwork.
     */
    @Test
    public void testUpdateWeights() throws Exception {
        System.out.println("updateWeights");
        List<Neuron> trainingSet = new ArrayList<>();
        NeuralNetwork instance = new NeuralNetwork(true,null);
        instance.updateWeights(trainingSet);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class NeuralNetwork.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        NeuralNetwork instance = new NeuralNetwork(true,null);
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
