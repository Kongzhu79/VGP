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
public class Material {

    ArrayList<double[][]> layerList;
    ArrayList<ArrayList<double[][]>> layerListSplit;
    int[] skiktCount;
    double[] skiktTjocklek;

    public ArrayList<double[][]> getLayerList(){
        return layerList;
    }
    public ArrayList<ArrayList<double[][]>> getLayerListSplit(){
        return layerListSplit;
    }
    public int[] getSkiktCount(){
        return skiktCount;
    }
    public double[] getSkiktTjocklek(){
        return skiktTjocklek;
    }
    public void material(ArrayList<String[]> materialList, boolean splitModel){

        MaterialData md = new MaterialData();
        double[][] matl;
        int cnt = 1;
        skiktCount = new int[materialList.size() + 2];
        skiktCount[0] = 0;
        skiktCount[1] = 1;
        layerListSplit = new ArrayList<>();
        skiktTjocklek = new double[materialList.size()];

        for(int i = 0; i < materialList.size(); i++){
            String[] materialString = materialList.get(i);

            String materialNamn = materialString[1];

            ArrayList<double[][]> layerListTemp = new ArrayList<>();

            double x = Double.parseDouble(materialString[0]) / 1000;
            skiktTjocklek[i] = x;
            int mtlnr = this.materialNumber(materialNamn);

            if(splitModel){
                if(mtlnr == 0 || mtlnr == 1 || mtlnr == 2 || mtlnr == 6 || mtlnr == 7){
                    matl = md.md(x, mtlnr);
                    layerListTemp.add(matl);
                    cnt++;
                }
                else{
                    int y = 0;
                    double x1 = 1000 * x;
                    int x2 = (int) x1;

                    if(x > Konstanter.ANTAL_MM_PER_SKIKT / 1000){
                        y = x2 / Konstanter.ANTAL_MM_PER_SKIKT;
                        if(0 < x % (1.0 * Konstanter.ANTAL_MM_PER_SKIKT / 1000)){
                            y++;
                        }
                        x = x / y;
                    }
                    matl = md.md(x, mtlnr);

                    for(int j = 0; j < y; j++){
                        layerListTemp.add(matl);
                        cnt++;
                    }
                }
                layerListSplit.add(layerListTemp);
                skiktCount[i + 2] = cnt;
            }
        }
    }
    public int materialNumber(String materialNamn){
        int i = -1;

        if(materialNamn.equalsIgnoreCase("luftspalt")){
            i = 0;
        }
        else if(materialNamn.equalsIgnoreCase("GN") || materialNamn.equalsIgnoreCase("normalgips")){
            i = 1;
        }
        else if(materialNamn.equalsIgnoreCase("GF") || materialNamn.equalsIgnoreCase("brandgips")){
            i = 2;
        }
        else if(materialNamn.equalsIgnoreCase("steel") || materialNamn.equalsIgnoreCase("stål")){
            i = 3;
        }
        else if(materialNamn.equalsIgnoreCase("concrete")){
            i = 4;
        }
        else if(materialNamn.equalsIgnoreCase("betong")){
            i = 5;
        }
        else if(materialNamn.equalsIgnoreCase("GNsultan")){
            i = 6;
        }
        else if(materialNamn.equalsIgnoreCase("GFsultan")){
            i = 7;
        }
        else if(materialNamn.equalsIgnoreCase("RW30")){
            i = 8;
        }
        else if(materialNamn.equalsIgnoreCase("RW75")){
            i = 9; // ska vara 70 för att få bättre överensstämmelse med VGP
        }
        else if(materialNamn.equalsIgnoreCase("RW100")){
            i = 10; // ska vara 85 för att få bättre överensstämmelse med VGP
        }
        else if(materialNamn.equalsIgnoreCase("RW140")){
            i = 11; // ska vara 120 för att få bättre överensstämmelse med VGP
        }
        else if(materialNamn.equalsIgnoreCase("iso")){
            i = 12;
        }
        else if(materialNamn.equalsIgnoreCase("fibreglass") || materialNamn.equalsIgnoreCase("glasull")){
            i = 13;
        }
        else if(materialNamn.equalsIgnoreCase("minerit")){
            i = 14;
        }
        else if(materialNamn.equalsIgnoreCase("promatectH")){
            i = 15;
        }
        else if(materialNamn.equalsIgnoreCase("promatectL")){
            i = 16;
        }
        else if(materialNamn.equalsIgnoreCase("wood") || materialNamn.equalsIgnoreCase("trä")){
            i = 17;
        }
        else if(materialNamn.equalsIgnoreCase("vermiculux")){
            i = 18;
        }
        else if(materialNamn.equalsIgnoreCase("brennix") || materialNamn.equalsIgnoreCase("brennicks")){
            i = 19;
        }
        else if(materialNamn.equalsIgnoreCase("meyco1350") || materialNamn.equalsIgnoreCase("FireShield")){
            i = 20;
        }
        else if(materialNamn.equalsIgnoreCase("cellplast") || materialNamn.equalsIgnoreCase("EPS")){
            i = 21;
        }
        else if(materialNamn.equalsIgnoreCase("laminate")){
            i = 22;
        }
        else if(materialNamn.equalsIgnoreCase("core")){
            i = 23;
        }
        else if(materialNamn.equalsIgnoreCase("MarinePlusBlanket")){
            i = 24;
        }
        else if(materialNamn.equalsIgnoreCase("Adhesive")){
            i = 25;
        }
        else if(materialNamn.equalsIgnoreCase("tegel") || materialNamn.equalsIgnoreCase("brick")){
            i = 26;
        }
        else if(materialNamn.equalsIgnoreCase("custom") || materialNamn.equalsIgnoreCase("eget")){
            i = 27;
        }
        else if(materialNamn.equalsIgnoreCase("outerShell")){
            i = 28;
        }
        else if(materialNamn.equalsIgnoreCase("moistureBarrier")){
            i = 29;
        }
        else if(materialNamn.equalsIgnoreCase("thermalBarrier")){
            i = 30;
        }
        else if(materialNamn.equalsIgnoreCase("epidermis")){
            i = 31;
        }
        else if(materialNamn.equalsIgnoreCase("dermis")){
            i = 32;
        }
        else if(materialNamn.equalsIgnoreCase("subcutaneous")){
            i = 33;
        }
        else if(materialNamn.equalsIgnoreCase("cotton")){
            i = 34;
        }
        else if(materialNamn.equalsIgnoreCase("habito")){
            i = 35;
        }
        else if(materialNamn.equalsIgnoreCase("lwc")){
            i = 36;
        }
        else if(materialNamn.equalsIgnoreCase("RWB30")){
            i = 37;
        }
        else if(materialNamn.equalsIgnoreCase("RWB75")){
            i = 38; // ska vara 70 för att få bättre överensstämmelse med VGP
        }
        else if(materialNamn.equalsIgnoreCase("RWB100")){
            i = 39; // ska vara 85 för att få bättre överensstämmelse med VGP
        }
        else if(materialNamn.equalsIgnoreCase("RWB140")){
            i = 40; // ska vara 120 för att få bättre överensstämmelse med VGP
        }
        else if(materialNamn.equalsIgnoreCase("ultimate")){
            i = 41;
        }
        else if(materialNamn.equalsIgnoreCase("woodBoard")){
            i = 42;
        }
        else if(materialNamn.equalsIgnoreCase("ultimateTest")){
            i = 43;
        }
        else if(materialNamn.equalsIgnoreCase("aluminium")){
            i = 44;
        }
        else if(materialNamn.equalsIgnoreCase("renrumsvagg")){
            i = 45;
        }
        else if(materialNamn.equalsIgnoreCase("Robust")){
            i = 46;
        }
        else if(materialNamn.equalsIgnoreCase("puts")){
            i = 47;
        }
        else if(materialNamn.equalsIgnoreCase("fermacellA1")){
            i = 48;
        }
        else if(materialNamn.equalsIgnoreCase("RW170")){
            i = 49; // ska vara 120 för att få bättre överensstämmelse med VGP
        }
        else{
            System.out.println("Materialet " + materialNamn + " är inte definerat.");
        }

        return i;
    }
}