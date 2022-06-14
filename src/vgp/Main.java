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
        Material matl = new Material();
        FEM fem = new FEM();
        Constants cons = new Constants();
        io.InputOutput();
        cons.setConstants(io.configList);

//Explicit solver, Specific heat, each layer is one element
        if(Constants.MODEL == 1){
            System.out.println("Model 1");
            matl.material(io.inputList, false);
            fem.fem(io.getTime(), matl.getLayerList(), cons.getAdiabatisk());
            io.SystemOut(fem.getTarray());
        }

//Explicit solver, Specific heat, each layer is divided into multiple elements - Default model
        else if(Constants.MODEL == 2){
            System.out.println("Model 2");
            matl.material(io.inputList, true);
            fem.femSplit(io.getTime(), matl.getLayerListSplit(), cons.getAdiabatisk());
            io.SystemOutSplit(matl.getLayerThickness(), fem.getTarray(), matl.getLayerCount());
        }

//Explicit solver, Enthalpy, each layer is divided into multiple elements
        else if(Constants.MODEL == 3){
            System.out.println("Model 3");
            matl.material(io.inputList, true);
            fem.femSplit(io.getTime(), matl.getLayerListSplit(), cons.getAdiabatisk());
            io.SystemOut(fem.getTarray());
        }

//Explicit solver, Specific heat, each layer is divided into multiple elements - external database
        else if(Constants.MODEL == 4){
            System.out.println("Model 4");
            matl.materialFind(io.inputList, true);
            fem.femSplit(io.getTime(), matl.getLayerListSplit(), cons.getAdiabatisk());
            io.SystemOutSplit(matl.getLayerThickness(), fem.getTarray(), matl.getLayerCount());
        }
        long endTime = System.nanoTime();
        io.printTime(startTime, endTime);
    }
}
