/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.util.*;
import java.io.*;

/**
 *
 * @author jsm
 */
public class FEM {

    FemCalc fc = new FemCalc();
    Fire fire = new Fire();

    double[][] Tarray;
    double[][] C;
    double[][] Cinv;

    public double[][] getTarray(){
        return Tarray;
    }

    public void ulfFem(int tid, ArrayList<double[][]> material, boolean adiabatisk)throws IOException{

        Matris m = new Matris();

        double[] T = new double[material.size() + 1];
        double[] Q;
        Tarray = new double[tid + 1][T.length + 2];
        for(int i = 0; i < T.length; i++){
            T[i] = Konstanter.KONSTANT_TEMPERATUR_BAKSIDA;
        }
    //Initiering: bygg matriserna C^-1, K och vektorerna Q och T
    //material består av matriser med varje skikts materialsammansättning

        double[][] K;
        double Tf;
        double Tbaksida;
        int cnt = 0;

        for(int i = 0; i < tid * Konstanter.TIDSSTEG_PER_SEKUND + 1; i++){
            Tf = fire.getTf(i * 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            Tbaksida = fire.getTbaksida(i * 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            if((i % Konstanter.TIDSSTEG_PER_SEKUND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Tf;
                for(int j = 2; j < Tarray[0].length; j++){
                    Tarray[cnt][j] = T[j - 2];
                }
                cnt++;
            }

    //Steg 1: beräkna T[j+1] = C[j]^-1 * (Q[j] - K[j]T[j]) * deltaT + T[j]

            fc.Cp(material, T);
            C = fc.getC();
            Cinv = m.CtoInvers(C);
            K = fc.K(material, T);
            Q = fc.getQ(T, Tf, Tbaksida, adiabatisk, fc.getLuftspalt());

            double[] A = m.matrisXarray(Cinv, Q);
            double[][] A2 = m.matrisXmatris(Cinv, K);
            double[] B = m.matrisXarray(A2, T);
            double[] D = m.arraySubtraktion(A, B);
            double[] E = m.arrayXconstant(D, (1.0 / Konstanter.TIDSSTEG_PER_SEKUND));

            T = m.arrayAddition(E, T);
       }

    //Steg 2: uppdatera C^-1, Q, K
    //C är den matris där elementens energiinnehåll specificeras
    //Q är en vektor där randvillkoren specificeras...
    //K är den matris där värmeledningen specificeras

    //Steg 3: upprepa proceduren tills sluttiden

    }

    public void FemSplit(int tid, ArrayList<ArrayList<double[][]>> materialSplit, boolean adiabatisk)throws IOException{

        Matris m = new Matris();
        fc.globalMatris(materialSplit);

        double[] Tsplit = new double[fc.getGlobalMatrisM()];
        double[] Q;
        Tarray = new double[tid + 1][Tsplit.length + 2];

        for(int i = 0; i < Tsplit.length; i++){
            Tsplit[i] = Konstanter.KONSTANT_TEMPERATUR_BAKSIDA;
        }
        //Initiering: bygg matriserna C^-1, K och vektorerna Q och T
        //material består av matriser med varje skikts materialsammansättning
        double[][] K;
        double Tf;
        double Tbaksida;
        int cnt = 0;

        for(int i = 0; i < tid * Konstanter.TIDSSTEG_PER_SEKUND + 1; i++){
            Tf = fire.getTf(i * 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            Tbaksida = fire.getTbaksida(i * 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            if((i % Konstanter.TIDSSTEG_PER_SEKUND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Tf;
                for(int j = 2; j < Tarray[0].length; j++){
                    Tarray[cnt][j] = Tsplit[j - 2];
                }
                cnt++;
            }

            //Steg 1: beräkna T[j+1] = C[j]^-1 * (Q[j] - K[j]T[j]) * deltaT + T[j]

            C = fc.CpSplit(materialSplit, Tsplit, fc.getGlobalMatrisM());
            Cinv = m.CtoInvers(C);
            K = fc.KSplit(materialSplit, Tsplit, fc.getGlobalMatrisM());
            Q = fc.getQ(Tsplit, Tf, Tbaksida, adiabatisk, fc.getLuftspalt());

            double[] A = m.matrisXarray(Cinv, Q);
            double[][] A2 = m.matrisXmatris(Cinv, K);
            double[] B = m.matrisXarray(A2, Tsplit);
            double[] D = m.arraySubtraktion(A, B);
            double[] E = m.arrayXconstant(D, 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            Tsplit = m.arrayAddition(E, Tsplit);
        }

        //Steg 2: uppdatera C^-1, Q, K
        //C är den matris där elementens energiinnehåll specificeras
        //Q är en vektor där randvillkoren specificeras...
        //K är den matris där värmeledningen specificeras

        //Steg 3: upprepa proceduren tills sluttiden
    }

    public void ulfFemSplitHud(int tid, ArrayList<ArrayList<double[][]>> materialSplit, boolean adiabatisk)throws IOException{

        Matris m = new Matris();
        fc.globalMatris(materialSplit);

        double[] Tsplit = new double[fc.getGlobalMatrisM()];
        double[] Q = new double[fc.getGlobalMatrisM()];

//skulle kunna justeras till en gradient
        Tarray = new double[tid + 1][Tsplit.length + 2];
        for(int i = 0; i < Tsplit.length; i++){
            Tsplit[i] = Konstanter.KONSTANT_TEMPERATUR_HUD;
        }
    //Initiering: bygg matriserna C^-1, K och vektorerna Q och T
    //material består av matriser med varje skikts materialsammansättning
        double[][] K;
        double Tf;
        double Tbaksida;
        int cnt = 0;

        for(int i = 0; i < tid * Konstanter.TIDSSTEG_PER_SEKUND + 1; i++){
            Tf = fire.getTf(i * 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            Tbaksida = fire.getTbaksida(i * 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            if((i % Konstanter.TIDSSTEG_PER_SEKUND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Tf;
                for(int j = 2; j < Tarray[0].length; j++){
                    Tarray[cnt][j] = Tsplit[j - 2];
                }
                cnt++;
            }

    //Steg 1: beräkna T[j+1] = C[j]^-1 * (Q[j] - K[j]T[j]) * deltaT + T[j]

            C = fc.CpSplitHud(materialSplit, Tsplit, fc.getGlobalMatrisM());
            Cinv = m.CtoInvers(C);
            K = fc.KSplit(materialSplit, Tsplit, fc.getGlobalMatrisM());
            Q = fc.getQ(Tsplit, Tf, Tbaksida, adiabatisk, fc.getLuftspalt());

            double[] A = m.matrisXarray(Cinv, Q);
            double[][] A2 = m.matrisXmatris(Cinv, K);
            double[] B = m.matrisXarray(A2, Tsplit);
            double[] D = m.arraySubtraktionHud(A, B);
            double[] E = m.arrayXconstant(D, 1.0 / Konstanter.TIDSSTEG_PER_SEKUND);
            Tsplit = m.arrayAddition(E, Tsplit);
       }

    //Steg 2: uppdatera C^-1, Q, K
    //C är den matris där elementens energiinnehåll specificeras
    //Q är en vektor där randvillkoren specificeras...
    //K är den matris där värmeledningen specificeras

    //Steg 3: upprepa proceduren tills sluttiden
    }
}

