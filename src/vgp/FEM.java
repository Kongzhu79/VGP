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

    public void fem(int time, ArrayList<double[][]> material, boolean adiabatic)throws IOException{

        Matrix m = new Matrix();

        double[] T = new double[material.size() + 1];
        double[] Q;
        Tarray = new double[time + 1][T.length + 2];
        for(int i = 0; i < T.length; i++){
            T[i] = Constants.CONSTANT_TEMPERATURE_UNEXPOSED;
        }

        //Initialization: build the matrices C^1, K and the arrays Q and T
        //material consists of matrices with the material composition of each layer

        double[][] K;
        double Texposed;
        double Tunexposed;
        int cnt = 0;
        int c = 1;
        System.out.print("Progression of the calculation: ");

        for(int i = 0; i < time * Constants.TIME_STEPS_PER_SECOND + 1; i++){
            if(i >= time * Constants.TIME_STEPS_PER_SECOND / 10 * c){
                System.out.print("%");
                c++;
            }
            Texposed = fire.getTexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);
            Tunexposed = fire.getTunexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);
            if((i % Constants.TIME_STEPS_PER_SECOND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Texposed;
                if (Tarray[0].length - 2 >= 0) System.arraycopy(T, 0, Tarray[cnt], 2, Tarray[0].length - 2);
                cnt++;
            }

            //Step 1: calculate T[j+1] = C[j]^-1 * (Q[j] - K[j]T[j]) * deltaT + T[j]

            //Step 2: update C^1, Q and K
            //C is the matrix where energy content is specified
            //Q is an array where the boundary conditions are specified
            //K is the matrix where conduction is specified

            //Step 3: repeat until final time is reached

            fc.Cp(material, T);
            C = fc.getC();
            Cinv = m.CInverted(C);
            K = fc.K(material, T);
            Q = fc.getQ(T, Texposed, Tunexposed, adiabatic, fc.getVoidLayer());

            double[] A = m.matrisXarray(Cinv, Q);
            double[][] A2 = m.matrisXmatris(Cinv, K);
            double[] B = m.matrisXarray(A2, T);
            double[] D = m.arraySubtraction(A, B);
            double[] E = m.arrayXconstant(D, (1.0 / Constants.TIME_STEPS_PER_SECOND));

            T = m.arrayAddition(E, T);
       }
       System.out.println();
    }

    public void femSplit(int time, ArrayList<ArrayList<double[][]>> materialSplit, boolean adiabatic)throws IOException{

        Matrix m = new Matrix();
        fc.globalMatris(materialSplit);

        double[] Tsplit = new double[fc.getGlobalMatrixM()];
        double[] TsplitMax = new double[Tsplit.length];
        double[] Q;
        Tarray = new double[time + 1][Tsplit.length + 2];

        for(int i = 0; i < Tsplit.length; i++){
            Tsplit[i] = Constants.CONSTANT_TEMPERATURE_UNEXPOSED;
        }
        for (int i = 0; i < Tsplit.length; i++) {
            TsplitMax[i] = Tsplit[i];
        }

        //Initialization: build the matrices C^1, K and the arrays Q and T
        //material consists of matrices with the material composition of each layer

        double[][] K;
        double Texposed;
        double Tunexposed;
        int cnt = 0;
        int c = 1;
        System.out.print("Progression of the calculation: ");

        for(int i = 0; i < time * Constants.TIME_STEPS_PER_SECOND + 1; i++){
            if(i >= time * Constants.TIME_STEPS_PER_SECOND / 10 * c){
                System.out.print("%");
                c++;
            }
            Texposed = fire.getTexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);
            Tunexposed = fire.getTunexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);
            if((i % Constants.TIME_STEPS_PER_SECOND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Texposed;
                if (Tarray[0].length - 2 >= 0) System.arraycopy(Tsplit, 0, Tarray[cnt], 2, Tarray[0].length - 2);
                cnt++;
            }

            //Step 1: calculate T[j+1] = C[j]^-1 * (Q[j] - K[j]T[j]) * deltaT + T[j]

            //Step 2: update C^1, Q and K
            //C is the matrix where energy content is specified
            //Q is an array where the boundary conditions are specified
            //K is the matrix where conduction is specified

            //Step 3: repeat until final time is reached

            C = fc.CpSplit(materialSplit, TsplitMax, fc.getGlobalMatrixM());
            Cinv = m.CInverted(C);
            K = fc.KSplit(materialSplit, TsplitMax, fc.getGlobalMatrixM());
            Q = fc.getQ(Tsplit, Texposed, Tunexposed, adiabatic, fc.getVoidLayer());

            double[] A = m.matrisXarray(Cinv, Q);
            double[][] A2 = m.matrisXmatris(Cinv, K);
            double[] B = m.matrisXarray(A2, Tsplit);
            double[] D = m.arraySubtraction(A, B);
            double[] E = m.arrayXconstant(D, 1.0 / Constants.TIME_STEPS_PER_SECOND);
            Tsplit = m.arrayAddition(E, Tsplit);

            for (int j = 0; j < Tsplit.length; j++) {
                if (Constants.KC_MAX == 1.0){
                    TsplitMax[j] = Math.max(TsplitMax[j], Tsplit[j]);
                }
                else{
                    TsplitMax[j] = Tsplit[j];
                }
            }
        }
        System.out.println();
    }
}
