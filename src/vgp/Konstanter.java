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
public class Konstanter {

    public static int MODEL;

/* 1 = Specific heat, each layer is one element
 * 2 = Specific heat, each layer is divided into multiple elements
 * 3 = Specific heat, each layer is divided into multiple elements. Considers Pennes' Bio-heat equation.
 * 4 = Enthalpy, each layer is divided into multiple elements
 */

    public static int ANTAL_SKIKT_PER_MM;
    public static int ANTAL_MM_PER_SKIKT;
    public static int TIDSSTEG_PER_SEKUND;
    public static int SEKUNDER_MELLAN_UTSKRIFT;

    static int BRANDKURVA;
    static int BAKSIDEKURVA;

/*
 * 0 = Adiabatiskt randvillkor
 * 1 = Konstant temperatur
 * 2 = ISO 834 (om den här används som alternativ på baksidan ska hKall justeras till 25)
 * 3 = Enligt indatafil med tid och Tf eller tid, Tr och Tg
 * 4 = Utvändig brand
 * 5 = Hydrocarbonbrand
 * 6 = Arkivbrand där tiden till avsvalning måste specificeras samt avsvalningshastigheten per minut.
 */

    //Epsilon kan sättas till 0.7 för stål och betong. För övriga material bör värdet sättas till 0.8
    static double EPSILON;

    //Stefan-Boltzmanns konstant för översättning av temperatur till strålningsintensitet.
    static double SIGMA;

    //Konvektivt värmeövergångstal på den brandutsatta sidan. Sätts till 25 för standardbrandkurvan och 35 för naturligt brandförlopp
    static double H_BRAND;

    //Konvektivt värmeövergångstal i luftspalter. Kan antas till 4 då naturlig konvektion förväntas och strålningen inkluderas i beräkningen.
    static double H_VOID;

    //Konvektivt värmeövergångstal på baksidan. Kan antas till 4 då naturlig konvektion förväntas och strålningen inkluderas i beräkningen.
    static double H_KALL;

    //Den temperatur som antas som brandpåverkan om konstant temperatur på framsidan används.
    static int KONSTANT_TEMPERATUR_FRAMSIDA;

    //Initialtemperatur för konstruktionen tillika den temperatur som antas som brandpåverkan om konstant temperatur på baksidan används.
    static int KONSTANT_TEMPERATUR_BAKSIDA;

    //Initialtemperatur för hud.
    static int KONSTANT_TEMPERATUR_HUD;

    //Vid beräkning med arkivbrand sätts tid till påbörjad avsvalning.
    static double TID_TILL_AVSVALNING; //Minuter

    //Gamma factor for calculation of parametric fire
    static double GAMMA;
    //Vid beräkning med arkivbrand sätts avsvalningshastighet.
    static double AVSVALNINGSHASTIGHET; //grader per minut

    static int PENNES_HUD_START = 0;
    static int PENNES_HUD_SLUT = 0;
    static double PENNES_G = 0.00125 * 3990000 * 1;

    public void setConstants(ArrayList<String[]> configList){
        
        String[] nameListArray = new String[17];
        double[] constListArray = new double[17];

        nameListArray[0] = "MODELL";
        nameListArray[1] = "SKIKT_PER_MM";
        nameListArray[2] = "MM_PER_SKIKT";
        nameListArray[3] = "TIDSSTEG_PER_SEKUND";
        nameListArray[4] = "UTSKRIFTSTIDSSTEG";
        nameListArray[5] = "BRANDKURVA_FRAMSIDA";
        nameListArray[6] = "BRANDKURVA_BAKSIDA";
        nameListArray[7] = "EPSILON";
        nameListArray[8] = "SIGMA";
        nameListArray[9] = "H_FRAMSIDA";
        nameListArray[10] = "H_BAKSIDA";
        nameListArray[11] = "H_VOID";
        nameListArray[12] = "KONSTANT_TEMPERATUR_FRAMSIDA";
        nameListArray[13] = "KONSTANT_TEMPERATUR_BAKSIDA";
        nameListArray[14] = "TID_TILL_MAXTEMPERATUR";
        nameListArray[15] = "AVSVALNINGSHASTIGHET";
        nameListArray[16] = "GAMMA";

        constListArray[0] = 2;       //Avser vilken modell som ska användas
        constListArray[1] = 1.;      //skikt per mm vid delning
        constListArray[2] = 5.;      //mm per skikt vid delning
        constListArray[3] = 20.;     //Antal tidssteg per sekund
        constListArray[4] = 300.;    //Avstånd i sekunder mellan temperaturutskrifter
        constListArray[5] = 2;       //Brandvillkor på exponerad sida
        constListArray[6] = 1;       //Brandvillkor på oexponerad sida
        constListArray[7] = 0.8;     //Emissivitet
        constListArray[8] = 5.67E-8; //Stefan-Boltzmanns konstant
        constListArray[9] = 25;      //Konvektionskoefficient för brandutsatt sida
        constListArray[10] = 4;      //Konvektionskoefficient för icke brandutsatt sida
        constListArray[11] = 4;      //Konvektionskoefficient för luftspalter
        constListArray[12] = 20;     //Konstant temperatur för framsida
        constListArray[13] = 20;     //Konstant temperatur för baksida
        constListArray[14] = 60;     //Tid till maxtemperatur i minuter
        constListArray[15] = 10;     //Avsvalningshastighet i minuter
        constListArray[16] = 1;      //Gamma factor for calculation of parametric fire

        for(String[] nameList : configList){
            for(int j = 0; j < nameListArray.length; j++){
                if(nameList[0].equalsIgnoreCase(nameListArray[j])){
                    constListArray[j] = Double.parseDouble(nameList[1]);
                }
            }
        }

        MODEL = (int) constListArray[0];
        ANTAL_SKIKT_PER_MM = (int) constListArray[1];
        ANTAL_MM_PER_SKIKT = (int) constListArray[2];
        TIDSSTEG_PER_SEKUND = (int) constListArray[3];
        SEKUNDER_MELLAN_UTSKRIFT = (int) constListArray[4];

        BRANDKURVA = (int) constListArray[5];
        BAKSIDEKURVA = (int) constListArray[6];

        EPSILON = constListArray[7];
        SIGMA = constListArray[8];
        H_BRAND = constListArray[9];
        H_KALL = constListArray[10];
        H_VOID = constListArray[11];

        KONSTANT_TEMPERATUR_FRAMSIDA = (int) constListArray[12];
        KONSTANT_TEMPERATUR_BAKSIDA = (int) constListArray[13];

        TID_TILL_AVSVALNING = constListArray[14];
        AVSVALNINGSHASTIGHET = constListArray[15];
        GAMMA = constListArray[16];
    }
    
    public boolean getAdiabatisk(){
        if(BAKSIDEKURVA == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
