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
        Konstanter kons = new Konstanter();

        io.InputOutput();
        kons.setConstants(io.constList);

//Explicit solver, Specific heat, each layer is one element
        if(Konstanter.MODEL == 1){
            matl.material(io.inputList, false);
            fem.ulfFem(io.getTime(), matl.getLayerList(), kons.getAdiabatisk());
            io.SystemUt(fem.getTarray());
        }

//Explicit solver, Specific heat, each layer is divided into multiple elements - Default model
        else if(Konstanter.MODEL == 30){
            matl.material(io.inputList, true);
            fem.FemSplit(io.getTime(), matl.getLayerListSplit(), kons.getAdiabatisk());
            io.SystemUtSplit(matl.getSkiktTjocklek(), fem.getTarray(), matl.getSkiktCount());
        }

//Explicit solver, Specific heat, each layer is divided into multiple elements. Considers Pennes' Bio-heat equation.
        else if(Konstanter.MODEL == 3){
            matl.material(io.inputList, true);
            fem.FemSplit(io.getTime(), matl.getLayerListSplit(), kons.getAdiabatisk());
            io.SystemUtSplit(matl.getSkiktTjocklek(), fem.getTarray(), matl.getSkiktCount());
        }
        
//Explicit solver, Enthalpy, each layer is divided into multiple elements
        else if(Konstanter.MODEL == 4){
            matl.material(io.inputList, true);
            fem.FemSplit(io.getTime(), matl.getLayerListSplit(), kons.getAdiabatisk());
            io.SystemUt(fem.getTarray());
        }
        long endTime = System.nanoTime();
        io.printTime(startTime, endTime);
    }
}
