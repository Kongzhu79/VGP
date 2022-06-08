/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

/**
 *
 * @author jsm
 */
public class Matris {

    public double[] matrisXarray(double[][] matris, double[] array){ //matrisen är uppbyggd med kolumn x rad
        double[] arrayNew = new double[array.length];

        for(int i = 0; i < array.length; i++){
            arrayNew[i] = 0;
        }
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < matris.length; j++){
                arrayNew[i] = arrayNew[i] + matris[j][i] * array[j];
            }
        }
        return arrayNew;
    }
    public double[][] CtoInvers(double[][] matris){ //kolumn x rad
        int a = matris.length;
        int b = matris[0].length;

        for(int i = 0; i < a; i++){
            for(int j = 0; j < b; j++){
                if(matris[i][j] != 0){
                    matris[i][j] = 1 / matris[i][j];
                }
            }
        }
        return matris;
    }
    public double[] arrayAddition(double[] array1, double[] array2){
        int a = array1.length;
        double[] arrayNew = new double[array1.length];

        for(int i = 0; i < a; i++){
            arrayNew[i] = array1[i] + array2[i];
        }
        return arrayNew;
    }
    public double[] arrayXconstant(double[] array, double constant){

        for(int i = 0; i < array.length; i++){
            array[i] = array[i] * constant;
        }
        return array;
    }
    public double[] arraySubtraktion(double[] array1, double[] array2){

        double[] arrayNew = new double[array1.length];

        for(int i = 0; i < array1.length; i++){
            arrayNew[i] = array1[i] - array2[i];
        }
        return arrayNew;
    }
    public double[][] matrisXmatris(double[][] matris1, double[][] matris2){
        double[][] matrisNew = new double[matris1.length][matris1[0].length];

        for(int i = 0; i < matrisNew.length; i++){
            for(int j = 0; j < matrisNew[0].length; j++){
                matrisNew[i][j] = 0;
            }
        }
        for(int i = 0; i < matrisNew.length; i++){
            for(int j = 0; j < matrisNew[0].length; j++){
                for(int k = 0; k < matrisNew.length; k++){
                        matrisNew[i][j] = matrisNew[i][j] + matris1[k][j] * matris2[i][k];
                }
            }
        }
        return matrisNew;
    }

    public double[] arraySubtraktionHud(double[] array1, double[] array2){
        int a = array1.length;
        double[] arrayNew = new double[array1.length];

        for(int i = 0; i < a; i++){
            if(i > Konstanter.PENNES_HUD_START && i < Konstanter.PENNES_HUD_SLUT){
                arrayNew[i] = array1[i] - array2[i];
            }
            arrayNew[i] = array1[i] - array2[i];
        }
        return arrayNew;
    }

}
