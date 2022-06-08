/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.util.*;

/**
 * @author jsm
 */

public class FemCalc {

    double[][] C;
    int[] luftspalt;
    int globalMatrisM;
    int antalMaterial;

    public double[][] getC(){
        return C;
    }
    public int[] getLuftspalt(){
        return luftspalt;
    }
    public int getGlobalMatrisM(){
        return globalMatrisM;
    }

    public double getK(double[][] skiktMaterial, double[] T, int elementNummer){
        double k = skiktMaterial[0][1];
        double a = skiktMaterial.length;
        double elementTemperatur = (T[elementNummer] + T[elementNummer + 1]) / 2;
        for(int i = 1; i < a; i++){
            if(elementTemperatur < skiktMaterial[i][0]){
                double temperaturOverSenasteIndatasteget = elementTemperatur - skiktMaterial[i - 1][0];
                double skillnadKMellanIndatasteg = skiktMaterial[i][1] - skiktMaterial[i - 1][1];
                double skillnadTemperaturMellanIndatasteg = skiktMaterial[i][0] - skiktMaterial[i - 1][0];
                double kPerTemperaturstegMellanIndatasteg = skillnadKMellanIndatasteg / skillnadTemperaturMellanIndatasteg;

                k = skiktMaterial[i - 1][1] + temperaturOverSenasteIndatasteget * kPerTemperaturstegMellanIndatasteg;
                break;
            }
            else{
            }
        }
        return k;
    }
    public double getC(double[][] skiktMaterial, double[] T, int elementNummer){
        double c = skiktMaterial[0][2];
        double a = skiktMaterial.length;
        double elementTemperatur = (T[elementNummer] + T[elementNummer + 1]) / 2;
        for(int i = 1; i < a; i++){
            if(elementTemperatur < skiktMaterial[i][0]){
                double temperaturOverSenasteIndatasteget = elementTemperatur - skiktMaterial[i - 1][0];
                double skillnadKMellanIndatasteg = skiktMaterial[i][2] - skiktMaterial[i - 1][2];
                double skillnadTemperaturMellanIndatasteg = skiktMaterial[i][0] - skiktMaterial[i - 1][0];
                double kPerTemperaturstegMellanIndatasteg = skillnadKMellanIndatasteg / skillnadTemperaturMellanIndatasteg;

                c = skiktMaterial[i - 1][2] + temperaturOverSenasteIndatasteget * kPerTemperaturstegMellanIndatasteg;
                break;
            }
            else{
            }
        }
        return c;
    }
    public double getRho(double[][] skiktData){
        return skiktData[0][3];
    }
    public double getFukt(double[][] skiktData){
        return skiktData[0][4];
    }
    public double getX(double[][] skiktData){
        return skiktData[0][5];
    }
    public double[] getQ(double[] T, double Tf, double Tbaksida, boolean adiabatisk, int[] luftspalt){
        double Qin = Konstanter.EPSILON * Konstanter.SIGMA * (Math.pow(Tf + 273.15, 4) - Math.pow(T[0] + 273.15, 4)) + Konstanter.H_BRAND * (Tf - T[0]);
        double Qut;
        if(adiabatisk){
            Qut = 0;
        }
        else{
            Qut = - (Konstanter.EPSILON * Konstanter.SIGMA * (Math.pow(T[T.length - 1] + 273.15, 4) - Math.pow(Tbaksida + 273.15, 4)) + Konstanter.H_KALL * (T[T.length - 1] - Tbaksida));
        }

        double[] Q = new double[T.length];
        for(int i = 1; i < Q.length - 1; i++){
            Q[i] = 0;
        }
        Q[0] = Qin;
        Q[T.length - 1] = Qut;

        for(int i = 0; i < luftspalt.length; i++){
            if(luftspalt[i] != 0){
                Q[i] = Konstanter.EPSILON * Konstanter.SIGMA * (Math.pow(T[i + 1] + 273.15, 4) - Math.pow(T[i] + 273.15, 4)) + Konstanter.H_VOID * ((T[i + 1] + T[i]) / 2 - T[i]);
                Q[i + 1] = Konstanter.EPSILON * Konstanter.SIGMA * (Math.pow(T[i] + 273.15, 4) - Math.pow(T[i + 1] + 273.15, 4)) + Konstanter.H_VOID * ((T[i + 1] + T[i]) / 2 - T[i + 1]);
            }
        }
        return Q;
    }

    public void globalMatris(ArrayList<ArrayList<double[][]>> materialSplit){
        int a = materialSplit.size();
        int b = 0;
        int i = 0;

        ArrayList<double[][]> c;

        for(; i < a; i++){
            c = materialSplit.get(i);
            b = b + c.size();
        }
        globalMatrisM = b + 1;
        antalMaterial = i + 1;
    }
    public void Cp(ArrayList<double[][]> material, double[] T){
        FemCalc fc = new FemCalc();
        int a = material.size();
        C = new double[a + 1][a + 1];
        luftspalt = new int[a + 1];
        double[][] skiktData;

        for(int i = 0; i < a; i++){
            skiktData = material.get(i);
            if(skiktData[0][4] != 0){
                double Cnew = 0;
                if(T[i] > 105){
                    skiktData[0][4] = 0;
                    Cnew = fc.getC(skiktData, T ,i);
                }
                else if(T[i] > 100){
                    Cnew = fc.getC(skiktData, T ,i) * fc.getRho(skiktData) * fc.getX(skiktData) + fc.getFukt(skiktData) / 100 * fc.getRho(skiktData) * fc.getX(skiktData);
                }
                else{
                    Cnew = fc.getC(skiktData, T ,i);
                }

                C[i][i] = C[i][i] + Cnew / 2;
                C[i + 1][i + 1] = Cnew * fc.getRho(skiktData) * fc.getX(skiktData) / 2;
            }
            else{
                if(skiktData[0][1] == 0){
                    luftspalt[i] = 1;
                }
                C[i][i] = C[i][i] + 1.0 * fc.getC(skiktData, T ,i) * fc.getRho(skiktData) * fc.getX(skiktData) / 2;
                C[i + 1][i + 1] = 1.0 * fc.getC(skiktData, T ,i) * fc.getRho(skiktData) * fc.getX(skiktData) / 2;
            }
        }
    }

    public double[][] K(ArrayList<double[][]> material, double[] T){
        FemCalc fc = new FemCalc();
        int a = material.size() + 1;
        double[][] K = new double[a][a];
        double[][] skiktData;
        for(int i = 0; i < a - 1; i++){
            skiktData = material.get(i);
            if(fc.getK(skiktData, T, i) == 0.0){
                K[i][i] = K[i][i] + 0.0;
                K[i + 1][i] = 0.0;
                K[i][i + 1] = 0.0;
                K[i + 1][i + 1] = 0.0;
            }
            else{
                K[i][i] = K[i][i] + fc.getK(skiktData, T, i) / fc.getX(skiktData);
                K[i + 1][i] = - 1.0 * fc.getK(skiktData, T, i) / fc.getX(skiktData);
                K[i][i + 1] = - 1.0 * fc.getK(skiktData, T, i) / fc.getX(skiktData);
                K[i + 1][i + 1] = fc.getK(skiktData, T, i) / fc.getX(skiktData);
            }
        }
        return K;
    }
    public double[][] CpSplit(ArrayList<ArrayList<double[][]>> materialSplit, double[] T, int globalM){
        FemCalc fc = new FemCalc();
        int a = materialSplit.size();
        int cnt = 0;
        ArrayList<double[][]> materialTemp;
        luftspalt = new int[globalM];
        double[][] CSplit = new double[globalM][globalM];
        double[][] skiktData;

        for(int i = 0; i < a; i++){
            materialTemp = materialSplit.get(i);
            int b = materialTemp.size();
            for(int j = 0; j < b; j++){
                skiktData = materialTemp.get(j);
                if(skiktData[0][1] == 0){
                    luftspalt[cnt] = 1;
                }
                CSplit[cnt][cnt] = CSplit[cnt][cnt] + 1.0 * fc.getC(skiktData, T ,cnt) * fc.getRho(skiktData) * fc.getX(skiktData) / 2;
                CSplit[cnt + 1][cnt + 1] = 1.0 * fc.getC(skiktData, T ,cnt) * fc.getRho(skiktData) * fc.getX(skiktData) / 2;
                cnt++;
            }
        }
        return CSplit;
    }

    public double[][] CpSplitHud(ArrayList<ArrayList<double[][]>> materialSplit, double[] T, int globalM){
        FemCalc fc = new FemCalc();
        int a = materialSplit.size();
        int cnt = 0;
        ArrayList<double[][]> materialTemp;
        luftspalt = new int[globalM];
        double[][] CSplit = new double[globalM][globalM];
        double[][] skiktData;
        double G;

        for(int i = 0; i < a; i++){
            materialTemp = materialSplit.get(i);
            if(i >= Konstanter.PENNES_HUD_START && i <= Konstanter.PENNES_HUD_SLUT){
                G = Konstanter.PENNES_G;
            }
            else{
                G = 0.;
            }
            int b = materialTemp.size();
            for(int j = 0; j < b; j++){
                skiktData = materialTemp.get(j);
                if(skiktData[0][1] == 0){
                    luftspalt[cnt] = 1;
                }
                CSplit[cnt][cnt] = CSplit[cnt][cnt] + 1.0 * fc.getC(skiktData, T ,cnt) * fc.getRho(skiktData) * fc.getX(skiktData) / 2 + G * fc.getX(skiktData) / 2 * (T[cnt] - Konstanter.KONSTANT_TEMPERATUR_HUD);
                CSplit[cnt + 1][cnt + 1] = 1.0 * fc.getC(skiktData, T ,cnt) * fc.getRho(skiktData) * fc.getX(skiktData) / 2 + G * fc.getX(skiktData) / 2 * (T[cnt] - Konstanter.KONSTANT_TEMPERATUR_HUD);

                cnt++;
            }
        }
        return CSplit;
    }
    public double[][] KSplit(ArrayList<ArrayList<double[][]>> materialSplit, double[] T, int globalM){
        FemCalc fc = new FemCalc();
        int a = materialSplit.size();
        int cnt = 0;
        ArrayList<double[][]> materialTemp;
        double[][] K = new double[globalM][globalM];
        double[][] skiktData;
        for(int i = 0; i < a; i++){
            materialTemp = materialSplit.get(i);
            int b = materialTemp.size();
            for(int j = 0; j < b; j++){
                skiktData = materialTemp.get(j);
                if(fc.getK(skiktData, T, i) == 0.0){
                    K[cnt][cnt] = K[cnt][cnt] + 0.0;
                    K[cnt + 1][cnt] = 0.0;
                    K[cnt][cnt + 1] = 0.0;
                    K[cnt + 1][cnt + 1] = 0.0;
                }
                else{
                    K[cnt][cnt] = K[cnt][cnt] + fc.getK(skiktData, T, cnt) / fc.getX(skiktData);
                    K[cnt + 1][cnt] = - 1.0 * fc.getK(skiktData, T, cnt) / fc.getX(skiktData);
                    K[cnt][cnt + 1] = - 1.0 * fc.getK(skiktData, T, cnt) / fc.getX(skiktData);
                    K[cnt + 1][cnt + 1] = fc.getK(skiktData, T, cnt) / fc.getX(skiktData);
                }
                cnt++;
            }
        }
        return K;
    }
}
