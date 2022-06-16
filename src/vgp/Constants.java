/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.util.*;

/**
 *
 * @author jsm
 */
public class Constants {

    public static int MODEL;

/* 1 = Specific heat, each layer is one element
 * 30 = Specific heat, each layer is divided into multiple elements
 * 3 = Enthalpy, each layer is divided into multiple elements
 */

    public static int NUMBER_OF_LAYERS_PER_MM;
    public static int NUMBER_OF_MM_PER_LAYER;
    public static int TIME_STEPS_PER_SECOND;
    public static int SECONDS_BETWEEN_PRINT_OUT;

    static int FIRE_CURVE_EXPOSED;
    static int FIRE_CURVE_UNEXPOSED;

/* The different fire curves used in the code is the following:
 * 0 = Adiabatic boundary
 * 1 = Constant temperature
 * 2 = Standard fire curve (if used on unexposed side, adjust H_UNEXPOSED to 25)
 * 3 = According to input file (fire.txt) on the form <time> and <Tf> or <time>, <Tr> and <Tg>
 * 4 = External fire
 * 5 = Hydro carbon fire
 * 6 = Parametric fire where GAMMA, time to max temperature and cooling rate is to be specified.
 */

    //The emissivity of the exposed surface.
    static double EPSILON;

    //Stefan-Boltzmanns constant.
    static double SIGMA;

    //Convective heat transfer coefficient for the exposed side.
    static double H_EXPOSED;

    //Convective heat transfer coefficient for voids.
    static double H_VOID;

    //Convective heat transfer coefficient for the unexposed side.
    static double H_UNEXPOSED;

    //Fire temperature if constant temperature is assumed
    static int CONSTANT_TEMPERATURE_EXPOSED;

    //Boundary temperature on unexposed side and inside materials
    static int CONSTANT_TEMPERATURE_UNEXPOSED;

    //For calculation using parametric fire curve (given in minutes).
    static double TIME_TO_MAX_TEMPERATURE;

    //For calculation using parametric fire curve (given in degrees per minutes).
    static double COOLING_RATE;

    //For calculation using parametric fire curve.
    static double GAMMA;

    public void setConstants(ArrayList<String[]> configList){
        
        String[] nameListArray = new String[17];
        double[] constListArray = new double[17];

        nameListArray[0] = "MODEL";
        constListArray[0] = 2;

        nameListArray[1] = "NUMBER_OF_LAYERS_PER_MM";
        constListArray[1] = 1.;

        nameListArray[2] = "NUMBER_OF_MM_PER_LAYER";
        constListArray[2] = 5.;

        nameListArray[3] = "TIME_STEPS_PER_SECOND";
        constListArray[3] = 20.;

        nameListArray[4] = "SECONDS_BETWEEN_PRINT_OUT";
        constListArray[4] = 300.;

        nameListArray[5] = "FIRE_CURVE_EXPOSED";
        constListArray[5] = 2;

        nameListArray[6] = "FIRE_CURVE_UNEXPOSED";
        constListArray[6] = 1;

        nameListArray[7] = "EPSILON";
        constListArray[7] = 0.8;

        nameListArray[8] = "SIGMA";
        constListArray[8] = 5.67E-8;

        nameListArray[9] = "H_EXPOSED";
        constListArray[9] = 25;

        nameListArray[10] = "H_UNEXPOSED";
        constListArray[10] = 4;

        nameListArray[11] = "H_VOID";
        constListArray[11] = 4;

        nameListArray[12] = "CONSTANT_TEMPERATURE_EXPOSED";
        constListArray[12] = 20;

        nameListArray[13] = "CONSTANT_TEMPERATURE_UNEXPOSED";
        constListArray[13] = 20;

        nameListArray[14] = "TIME_TO_MAX_TEMPERATURE";
        constListArray[14] = 60;

        nameListArray[15] = "COOLING_RATE";
        constListArray[15] = 10;

        nameListArray[16] = "GAMMA";
        constListArray[16] = 1;

        for(String[] nameList : configList){
            for(int j = 0; j < nameListArray.length; j++){
                if(nameList[0].equalsIgnoreCase(nameListArray[j])){
                    constListArray[j] = Double.parseDouble(nameList[1]);
                }
            }
        }

        MODEL = (int) constListArray[0];
        NUMBER_OF_LAYERS_PER_MM = (int) constListArray[1];
        NUMBER_OF_MM_PER_LAYER = (int) constListArray[2];
        TIME_STEPS_PER_SECOND = (int) constListArray[3];
        SECONDS_BETWEEN_PRINT_OUT = (int) constListArray[4];
        FIRE_CURVE_EXPOSED = (int) constListArray[5];
        FIRE_CURVE_UNEXPOSED = (int) constListArray[6];
        EPSILON = constListArray[7];
        SIGMA = constListArray[8];
        H_EXPOSED = constListArray[9];
        H_UNEXPOSED = constListArray[10];
        H_VOID = constListArray[11];
        CONSTANT_TEMPERATURE_EXPOSED = (int) constListArray[12];
        CONSTANT_TEMPERATURE_UNEXPOSED = (int) constListArray[13];
        TIME_TO_MAX_TEMPERATURE = constListArray[14];
        COOLING_RATE = constListArray[15];
        GAMMA = constListArray[16];
    }
    
    public boolean getAdiabatisk(){
        if(FIRE_CURVE_UNEXPOSED == 0){
            return true;
        }
        else{
            return false;
        }
    }
}