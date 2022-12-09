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
    ArrayList<ArrayList<double[][]>> layerListUpdate = new ArrayList<>();
    ArrayList<ArrayList<double[][]>> layerListSplit;
    ArrayList<ArrayList<ArrayList<double[][]>>> layerListSplitUpdate = new ArrayList<>();
    static ArrayList<double[]> splitNodes;
    int[] layerCount;
    ArrayList<int[]> layerCountUpdate = new ArrayList<>();
    double[] layerThickness;
    public ArrayList<double[][]> getLayerList(){
        return layerList;
    }
    public ArrayList<ArrayList<double[][]>> getLayerListSplit(){
        return layerListSplit;
    }
    public ArrayList<ArrayList<ArrayList<double[][]>>> getLayerListSplitUpdate(){
        return layerListSplitUpdate;
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
        splitNodes = new ArrayList<>();
        int i = 0;

        for(String[] materialString : materialList) {
            ArrayList<double[][]> layerListTemp = new ArrayList<>();

            double x = Double.parseDouble(materialString[0]) / 1000;
            layerThickness[i] = x;

            double[] split = io.materialReaderUpdate(materialString[1]);
            if (splitModel) {
                if (split[0] == 0) {
                    int y = 0;
                    double x1 = 1000 * x;
                    int x2 = (int) x1;

                    if (x > Constants.NUMBER_OF_MM_PER_LAYER / 1000) {
                        y = x2 / Constants.NUMBER_OF_MM_PER_LAYER;
                        if (0 < x % (Constants.NUMBER_OF_MM_PER_LAYER / 1000.0)) {
                            y++;
                        }
                        x = x / y;
                    }
                    double[][] matl = io.materialReader(materialString[1], x);

                    for (int j = 0; j < y; j++) {
                        layerListTemp.add(matl);
                        cnt++;
                    }
                } else {
                    layerListTemp.add(io.materialReader(materialString[1], x));
                    cnt++;
                    double[] fallOffTemp = new double[2];
                    fallOffTemp[0] = cnt;
                    fallOffTemp[1] = split[0];
                    splitNodes.add(fallOffTemp);
                }
                layerListSplit.add(layerListTemp);
                layerCount[i + 2] = cnt;
            } else {
                layerList.add(io.materialReader(materialString[1], x));
            }
            i++;
            if (splitModel) {
                layerListSplitUpdate.add(layerListSplit);
                layerCountUpdate.add(layerCount);
            } else {
                layerListUpdate.add(layerList);
            }
        }
    }
    public void materialTASEF(ArrayList<String[]> materialList, boolean splitModel) throws IOException {

        IO io = new IO();

        int cnt = 1;

        layerCount = new int[materialList.size() + 2];
        layerCount[0] = 0;
        layerCount[1] = 1;
        layerListSplit = new ArrayList<>();
        layerList = new ArrayList<>();
        layerThickness = new double[materialList.size()];
        splitNodes = new ArrayList<>();
        int i = 0;
        int cnt1 = 0;

        for(String[] materialString : materialList) {
            ArrayList<double[][]> layerListTemp = new ArrayList<>();

            double x = Double.parseDouble(materialString[0]) / 1000;
            layerThickness[i] = x;

            double[] split = io.materialReaderUpdate(materialString[1]);
            if (splitModel) {
                if (split[0] == 0) {
                    int y = 0;
                    double x1 = 1000 * x;
                    int x2 = (int) x1;

                    if (x > Constants.NUMBER_OF_MM_PER_LAYER / 1000) {
                        y = x2 / Constants.NUMBER_OF_MM_PER_LAYER;
                        if (0 < x % (Constants.NUMBER_OF_MM_PER_LAYER / 1000.0)) {
                            y++;
                        }
                        x = x / y;
                    }
                    double[][] matl = io.materialReaderTASEF(materialString[1], x);

                    for (int j = 0; j < y; j++) {
                        layerListTemp.add(matl);
                        cnt++;
                    }
                } else {
                    layerListTemp.add(io.materialReaderTASEF(materialString[1], x));
                    cnt++;
                    double[] fallOffTemp = new double[2];
                    fallOffTemp[0] = cnt;
                    fallOffTemp[1] = split[0];
                    splitNodes.add(fallOffTemp);
                }
                layerListSplit.add(layerListTemp);
                layerCount[i + 2] = cnt;
            } else {
                layerList.add(io.materialReaderTASEF(materialString[1], x));
            }
            i++;
            if (splitModel) {
                layerListSplitUpdate.add(layerListSplit);
                layerCountUpdate.add(layerCount);
            } else {
                layerListUpdate.add(layerList);
            }
        }
    }
}