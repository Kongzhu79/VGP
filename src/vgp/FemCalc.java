package vgp;

import java.util.*;

/**
 * @author jsm
 */

public class FemCalc {

    double[][] C;
    int[] voidLayer;
    int globalMatrixM;
    int numberMatrials;
    public double[][] getC(){
        return C;
    }
    public int[] getVoidLayer(){
        return voidLayer;
    }
    public int getGlobalMatrixM(){
        return globalMatrixM;
    }
    public double getK(double[][] layerMaterial, double[] T, int elementNumber){
        double k = layerMaterial[0][1];
        double a = layerMaterial.length;
        double elementTemperature = (T[elementNumber] + T[elementNumber + 1]) / 2;
        for(int i = 1; i < a; i++){
            if(elementTemperature < layerMaterial[i][0]){
                double temperatureOverLastInput = elementTemperature - layerMaterial[i - 1][0];
                double differenceInput = layerMaterial[i][1] - layerMaterial[i - 1][1];
                double differenceTemperature = layerMaterial[i][0] - layerMaterial[i - 1][0];
                double kPerTemperatureIncrement = differenceInput / differenceTemperature;

                k = layerMaterial[i - 1][1] + temperatureOverLastInput * kPerTemperatureIncrement;
                break;
            }
        }
        return k;
    }
    public double getC(double[][] layerMaterial, double[] T, int elementNumber){
        double c = layerMaterial[0][2];
        double a = layerMaterial.length;
        double elementTemperature = (T[elementNumber] + T[elementNumber + 1]) / 2;
        for(int i = 1; i < a; i++){
            if(elementTemperature < layerMaterial[i][0]){
                double temperatureOverLastInput = elementTemperature - layerMaterial[i - 1][0];
                double differenceInput = layerMaterial[i][2] - layerMaterial[i - 1][2];
                double differenceTemperature = layerMaterial[i][0] - layerMaterial[i - 1][0];
                double kPerTemperatureIncrement = differenceInput / differenceTemperature;

                c = layerMaterial[i - 1][2] + temperatureOverLastInput * kPerTemperatureIncrement;
                break;
            }
        }
        return c;
    }
    public double getRho(double[][] layerData){
        return layerData[0][3];
    }
    public double getMoist(double[][] layerData){
        return layerData[0][4];
    }
    public double getX(double[][] layerData){
        return layerData[0][5];
    }
    public double[] getQ(double[] T, double Texposed, double Tunexposed, boolean adiabatic, int[] voidL){
        double Qin = Constants.EPSILON * Constants.SIGMA * (Math.pow(Texposed + 273.15, 4) - Math.pow(T[0] + 273.15, 4)) + Constants.H_EXPOSED * (Texposed - T[0]);
        double Qut;

        if(adiabatic){
            Qut = 0;
        }
        else{
            Qut = - (Constants.EPSILON * Constants.SIGMA * (Math.pow(T[T.length - 1] + 273.15, 4) - Math.pow(Tunexposed + 273.15, 4)) + Constants.H_UNEXPOSED * (T[T.length - 1] - Tunexposed));
        }

        double[] Q = new double[T.length];
        for(int i = 1; i < Q.length - 1; i++){
            Q[i] = 0;
        }
        Q[0] = Qin;
        Q[T.length - 1] = Qut;

        for(int i = 0; i < voidL.length; i++){
            if(voidL[i] != 0){
                Q[i] = Constants.EPSILON * Constants.SIGMA * (Math.pow(T[i + 1] + 273.15, 4) - Math.pow(T[i] + 273.15, 4)) + Constants.H_VOID * ((T[i + 1] + T[i]) / 2 - T[i]);
                Q[i + 1] = Constants.EPSILON * Constants.SIGMA * (Math.pow(T[i] + 273.15, 4) - Math.pow(T[i + 1] + 273.15, 4)) + Constants.H_VOID * ((T[i + 1] + T[i]) / 2 - T[i + 1]);
            }
        }
        return Q;
    }
    public void globalMatris(ArrayList<ArrayList<double[][]>> materialSplit){

        int b = 0;
        int i = 0;

        for(ArrayList<double[][]> c : materialSplit){
            b = b + c.size();
            i++;
        }
        globalMatrixM = b + 1;
        numberMatrials = i + 1;
    }
    public void Cp(ArrayList<double[][]> material, double[] T){
        int a = material.size();
        C = new double[a + 1][a + 1];
        voidLayer = new int[a + 1];
        double[][] layerData;

        for(int i = 0; i < a; i++){
            layerData = material.get(i);
            if(layerData[0][4] != 0){
                double Cnew;
                if(T[i] > 105){
                    layerData[0][4] = 0;
                    Cnew = this.getC(layerData, T ,i);
                }
                else if(T[i] > 100){
                    Cnew = this.getC(layerData, T ,i) * this.getRho(layerData) * this.getX(layerData) + this.getMoist(layerData) / 100 * this.getRho(layerData) * this.getX(layerData);
                }
                else{
                    Cnew = this.getC(layerData, T ,i);
                }

                C[i][i] = C[i][i] + Cnew / 2;
                C[i + 1][i + 1] = Cnew * this.getRho(layerData) * this.getX(layerData) / 2;
            }
            else{
                if(layerData[0][1] == 0){
                    voidLayer[i] = 1;
                }
                C[i][i] = C[i][i] + 1.0 * this.getC(layerData, T ,i) * this.getRho(layerData) * this.getX(layerData) / 2;
                C[i + 1][i + 1] = 1.0 * this.getC(layerData, T ,i) * this.getRho(layerData) * this.getX(layerData) / 2;
            }
        }
    }
    public double[][] K(ArrayList<double[][]> material, double[] T){
        int a = material.size() + 1;
        double[][] K = new double[a][a];
        double[][] layerData;
        for(int i = 0; i < a - 1; i++){
            layerData = material.get(i);
            if(this.getK(layerData, T, i) == 0.0){
                K[i][i] = K[i][i] + 0.0;
                K[i + 1][i] = 0.0;
                K[i][i + 1] = 0.0;
                K[i + 1][i + 1] = 0.0;
            }
            else{
                K[i][i] = K[i][i] + this.getK(layerData, T, i) / this.getX(layerData);
                K[i + 1][i] = - 1.0 * this.getK(layerData, T, i) / this.getX(layerData);
                K[i][i + 1] = - 1.0 * this.getK(layerData, T, i) / this.getX(layerData);
                K[i + 1][i + 1] = this.getK(layerData, T, i) / this.getX(layerData);
            }
        }
        return K;
    }
    public double[][] CpSplit(ArrayList<ArrayList<double[][]>> materialSplit, double[] T, int globalM){
        int cnt = 0;
        voidLayer = new int[globalM];
        double[][] CSplit = new double[globalM][globalM];
        for(ArrayList<double[][]> materialTemp : materialSplit){
            for(double[][] layerData : materialTemp){
                if(layerData[0][1] == 0){
                    voidLayer[cnt] = 1;
                }
                CSplit[cnt][cnt] = CSplit[cnt][cnt] + 1.0 * this.getC(layerData, T ,cnt) * this.getRho(layerData) * this.getX(layerData) / 2;
                CSplit[cnt + 1][cnt + 1] = 1.0 * this.getC(layerData, T ,cnt) * this.getRho(layerData) * this.getX(layerData) / 2;
                cnt++;
            }
        }
        return CSplit;
    }
    public double[][] KSplit(ArrayList<ArrayList<double[][]>> materialSplit, double[] T, int globalM){
        int a = materialSplit.size();
        int cnt = 0;
        ArrayList<double[][]> materialTemp;
        double[][] K = new double[globalM][globalM];
        for(int i = 0; i < a; i++){
            materialTemp = materialSplit.get(i);
            for(double[][] layerData : materialTemp){
                if(this.getK(layerData, T, i) == 0.0){
                    K[cnt][cnt] = K[cnt][cnt] + 0.0;
                    K[cnt + 1][cnt] = 0.0;
                    K[cnt][cnt + 1] = 0.0;
                    K[cnt + 1][cnt + 1] = 0.0;
                }
                else{
                    K[cnt][cnt] = K[cnt][cnt] + this.getK(layerData, T, cnt) / this.getX(layerData);
                    K[cnt + 1][cnt] = - 1.0 * this.getK(layerData, T, cnt) / this.getX(layerData);
                    K[cnt][cnt + 1] = - 1.0 * this.getK(layerData, T, cnt) / this.getX(layerData);
                    K[cnt + 1][cnt + 1] = this.getK(layerData, T, cnt) / this.getX(layerData);
                }
                cnt++;
            }
        }
        return K;
    }
    public void globalMatrixNull() {
        globalMatrixM = 0;
        numberMatrials = 0;
    }
}
