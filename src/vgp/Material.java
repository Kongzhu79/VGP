/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.io.IOException;
import java.util.*;
/**
 *
 * @author jsm
 */
public class Material {

    ArrayList<double[][]> layerList;
    ArrayList<ArrayList<double[][]>> layerListSplit;
    int[] layerCount;
    double[] layerThickness;

    public ArrayList<double[][]> getLayerList(){
        return layerList;
    }
    public ArrayList<ArrayList<double[][]>> getLayerListSplit(){
        return layerListSplit;
    }
    public int[] getLayerCount(){
        return layerCount;
    }
    public double[] getLayerThickness(){
        return layerThickness;
    }
    public void material(ArrayList<String[]> materialList, boolean splitModel) throws IOException {

        IO io = new IO();

        int cnt = 1;

        layerCount = new int[materialList.size() + 2];
        layerCount[0] = 0;
        layerCount[1] = 1;
        layerListSplit = new ArrayList<>();
        layerList = new ArrayList<>();
        layerThickness = new double[materialList.size()];

        for(int i = 0; i < materialList.size(); i++){
            String[] materialString = materialList.get(i);

            ArrayList<double[][]> layerListTemp = new ArrayList<>();

            double x = Double.parseDouble(materialString[0]) / 1000;
            layerThickness[i] = x;

            boolean split = io.materialReader(materialString[1]);

            if(splitModel){
                if(split){
                    int y = 0;
                    double x1 = 1000 * x;
                    int x2 = (int) x1;

                    if(x > Constants.NUMBER_OF_MM_PER_LAYER / 1000){
                        y = x2 / Constants.NUMBER_OF_MM_PER_LAYER;
                        if(0 < x % (Constants.NUMBER_OF_MM_PER_LAYER / 1000.0)){
                            y++;
                        }
                        x = x / y;
                    }
                    double[][] matl = io.materialReader(materialString[1], x);

                    for(int j = 0; j < y; j++){
                        layerListTemp.add(matl);
                        cnt++;
                    }
                }
                else{
                    layerListTemp.add(io.materialReader(materialString[1], x));
                    cnt++;
                }
                layerListSplit.add(layerListTemp);
                layerCount[i + 2] = cnt;
            }
            else{
                layerList.add(io.materialReader(materialString[1], x));
            }
        }
    }
}