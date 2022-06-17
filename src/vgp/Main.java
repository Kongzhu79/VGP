/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.io.*;
/**
 *
 * @author jsm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();
        IO io = new IO();
        Material m = new Material();
        FEM f = new FEM();
        Constants c = new Constants();
        io.InputOutput();
        c.setConstants(io.configList);

//Explicit solver, Specific heat, each layer is one element
        if(Constants.MODEL == 1){
            System.out.println("Model 1");
            m.material(io.inputList, false);
            f.fem(io.getTime(), m.getLayerList(), c.getAdiabatisk());
            io.SystemOut(f.getTarray());
        }

//Explicit solver, Specific heat, each layer is divided into multiple elements - Default model
        else if(Constants.MODEL == 2){
            System.out.println("Model 2");
            m.material(io.inputList, true);
            f.femSplit(io.getTime(), m.getLayerListSplit(), c.getAdiabatisk());
            io.SystemOutSplit(m.getLayerThickness(), f.getTarray(), m.getLayerCount());
        }

//Explicit solver, Enthalpy, each layer is divided into multiple elements
        else if(Constants.MODEL == 3){
            System.out.println("Model 3");
            m.material(io.inputList, true);
            f.femSplit(io.getTime(), m.getLayerListSplit(), c.getAdiabatisk());
            io.SystemOut(f.getTarray());
        }

        long endTime = System.nanoTime();
        io.printTime(startTime, endTime);
    }
}
