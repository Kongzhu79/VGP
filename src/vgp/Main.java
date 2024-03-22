/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.io.*;

import static java.lang.System.*;

/**
 *
 * @author jsm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        long startTime = nanoTime();
        IO io = new IO();
        Material m = new Material();
        FEM f = new FEM();
        Constants c = new Constants();
        io.InputOutput();
        c.setConstants(io.configList);

//Explicit solver, Specific heat, each layer is one element
//Can be approximated using the default model by setting the fall-off temperature to
//a value higher than the max fire temperature
        if (Constants.MODEL == 1) {
            out.println("Model 1");
            m.material(IO.inputList, false);
            f.fem(io.getTime(), m.getLayerList(), c.getAdiabatic());
            io.SystemOutSimple(f.getTarray());
        }

//Explicit solver, Specific heat, each layer is divided into multiple elements - default model
        else if (Constants.MODEL == 2) {
            out.println("Model 2");
            m.material(IO.inputList, true);
            f.femSplitFallOff(io.getTime(), m.getLayerListSplitUpdate(), c.getAdiabatic(), IO.inputList, true);
            io.SystemOut(m.getLayerThickness(), f.TList, f.getLayerCountUpdate());
        }
//Explicit solver, Specific heat, each layer is divided into multiple elements
//Can be approximated using the default model by setting the fall-off temperature to 0
        else if (Constants.MODEL == 3) {
            out.println("Model 3");
            m.material(IO.inputList, true);
            f.femSplit(io.getTime(), m.getLayerListSplit(), c.getAdiabatic());
            io.SystemOutOld(m.getLayerThickness(), f.getTarray(), m.getLayerCount());
        }
//Explicit solver, Specific heat, each layer is divided into multiple elements - default model
        else if (Constants.MODEL == 4) {
            out.println("Model 4");
            m.materialTASEF(IO.inputList, true);
            f.femTASEFFallOff(io.getTime(), m.getLayerListSplitUpdate(), c.getAdiabatic(), IO.inputList, true);
            io.SystemOut(m.getLayerThickness(), f.TList, f.getLayerCountUpdate());
        }
        long endTime = nanoTime();
        io.printTime(startTime, endTime);
    }
}
