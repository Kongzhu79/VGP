/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.*;
import java.text.*;

/**
 *
 * @author jsm
 */
public class IO {

    String configFilePath;
    String inputFilePath;
    String outputFilePath;
    String fireFilePath;
    public static String materialFolderPath;
    ArrayList<String[]> inputList;
    ArrayList<String[]> configList;
    int time;

    public int getTime(){
        return time;
    }
    public void InputOutput()throws IOException{

        configFilePath = this.folderName()[0];
        inputFilePath = this.folderName()[1];
        outputFilePath = this.folderName()[2];
        materialFolderPath = this.folderName()[3];
        fireFilePath = this.folderName()[4];

        inputList = this.inputFileReader(inputFilePath);
        configList = this.configFileReader(configFilePath);

        time = this.getTime();
    }
    public String[] folderName()throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("C:\\VGP\\VGP.txt"));
        String[] folderList= new String[5];
        String a = br.readLine();
        String s = "";

        while (a != null){
            String[] fName = a.split(" ");
            if(fName[0].equalsIgnoreCase("CONFIG_PATH")){
                s = a.substring(a.indexOf("'") + 1);
                folderList[0] = s;
            }
            else if(fName[0].equalsIgnoreCase("INPUT_PATH")){
                s = a.substring(a.indexOf("'") + 1);
                folderList[1] = s;
            }
            else if(fName[0].equalsIgnoreCase("OUTPUT_PATH")){
                s = a.substring(a.indexOf("'") + 1);
                folderList[2] = s;
            }
            else if(fName[0].equalsIgnoreCase("MATERIAL_PATH")){
                s = a.substring(a.indexOf("'") + 1);
                folderList[3] = s;
            }
            else if(fName[0].equalsIgnoreCase("FIRE_PATH")){
                s = a.substring(a.indexOf("'") + 1);
                folderList[4] = s;
            }
            a = br.readLine();
        }
        br.close();

        return folderList;

    }
    public ArrayList<String[]> inputFileReader(String fileName)throws IOException{

        String[] stringArray;
        ArrayList<String[]> stringList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        BufferedWriter print = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Path.of(outputFilePath))));

        time = Integer.parseInt(br.readLine());
        String a = br.readLine();
        while(a != null){
            stringArray = a.split(" ");
            stringList.add(stringArray);
            a = br.readLine();
        }
        br.close();
        print.close();

        return stringList;
    }
    public ArrayList<String[]> configFileReader(String filNamn)throws IOException{

        String[] stringArray;
        ArrayList<String[]> configListTemp = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(configFilePath));

        String a = br.readLine();
        while(a != null){
            stringArray = a.split(" ");
            configListTemp.add(stringArray);
            a = br.readLine();
        }
        br.close();
        return configListTemp;
    }

    public double[][] getFireTemperature()throws IOException{
        String[] stringArray;
        ArrayList<String> fireTemperatureList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(fireFilePath));

        String a = br.readLine();
        while(a != null){
            fireTemperatureList.add(a);
            a = br.readLine();
        }

        a = fireTemperatureList.get(0);
        int nLength = fireTemperatureList.size();
        int nValues = a.split("\t").length;
        double[][] fireTemperature;

        fireTemperature = new double[nLength][nValues];
        for(int i = 0; i < nLength; i++){
            a = fireTemperatureList.get(i);
            stringArray = a.split("\t");
            for(int j = 0; j < nValues; j++){
                fireTemperature[i][j] = Double.parseDouble(stringArray[j]);
            }
        }
        return fireTemperature;
    }

    public void SystemOut(double[][] Tarray)throws IOException{
        DecimalFormat df = new DecimalFormat("0.0");
        DecimalFormat dfTime = new DecimalFormat("0");
        BufferedWriter print = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Path.of(outputFilePath))));

        System.out.println("Boundary exposed side: " + Constants.FIRE_CURVE_EXPOSED);
        print.write("Boundary exposed side: " + Constants.FIRE_CURVE_EXPOSED);
        print.newLine();

        for(String[] input : inputList){
            System.out.println(input[0] + " " + input[1]);
            print.write(input[0] + " " + input[1]);
            print.newLine();
        }
        System.out.println("Boundary unexposed side: " + Constants.FIRE_CURVE_UNEXPOSED);
        print.write("Boundary unexposed side: " + Constants.FIRE_CURVE_UNEXPOSED);
        print.newLine();

        int a = Tarray.length;
        for(int i = 0; i < a;){
            System.out.print(dfTime.format((i / 60.0)).replace(",", ".") + "\t");
            print.write(dfTime.format((i / 60.0)).replace(",", ".") + "\t");
            for(int j = 1; j < Tarray[0].length; j++){
                System.out.print(df.format(Tarray[i][j]).replace(",", ".") + "\t");
                print.write(df.format(Tarray[i][j]).replace(",", ".") + "\t");
            }
            System.out.println();
            print.newLine();

            i = i + Constants.SECONDS_BETWEEN_PRINT_OUT;
        }
        print.close();
    }

    public void SystemOutSplit(double[] layerThickness, double[][] Tarray, int[] splitCount)throws IOException{

        DecimalFormat df1 = new DecimalFormat("0.0");
        BufferedWriter print = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Path.of(outputFilePath))));

        System.out.println("Boundary exposed side: " + Constants.FIRE_CURVE_EXPOSED);
        print.write("Exponering på framsidan: " + Constants.FIRE_CURVE_EXPOSED);
        print.newLine();

        for(String[] input : inputList){
            System.out.println(input[0] + " " + input[1]);
            print.write(input[0] + " " + input[1]);
            print.newLine();
        }
        System.out.println("Boundary unexposed side: " + Constants.FIRE_CURVE_UNEXPOSED);
        print.write("Exponering på baksidan: " + Constants.FIRE_CURVE_UNEXPOSED);
        print.newLine();

        String timeAlign = "%4s";
        String align = "%8s";
        
        int a = Tarray.length;
        double c = 0;
        
        System.out.print("Tid     -1.0     " + c);
        print.write("Tid     -1.0     " + c);

        for(double thickness : layerThickness){
            c = thickness + c;
            System.out.printf(align, df1.format(c * 1000).replace(",", "."));
            print.write(String.format(align, df1.format(c * 1000).replace(",", ".")));
        }
        System.out.println();
        print.newLine();

        for(int i = 0; i < a;){
            System.out.printf(timeAlign, (i / 60));
            print.write(String.format(timeAlign, (i / 60)));

            System.out.printf(align, df1.format(Tarray[i][splitCount[1]]).replace(",", "."));
            print.write(String.format(align, df1.format(Tarray[i][splitCount[1]]).replace(",", ".")));

            for(int j = 1; j < splitCount.length; j++){
                System.out.printf(align, df1.format(Tarray[i][splitCount[j] + 1]).replace(",", "."));
                print.write(String.format(align, df1.format(Tarray[i][splitCount[j] + 1]).replace(",", ".")));
            }

            System.out.println();
            print.newLine();
            i = i + Constants.SECONDS_BETWEEN_PRINT_OUT;
        }
        print.close();
    }

    public void printTime(long startTime, long endTime) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        System.out.println("Run time: " + df1.format((endTime - startTime) / 1000000000.0).replace(",",".") + " sec");
    }

    public double[][] materialReader(String materialName, double x) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(materialFolderPath + materialName + ".txt"));
        ArrayList<String[]> materialArray = new ArrayList<>();

        String a = br.readLine();
        while(a != null){
            materialArray.add(a.split("\t"));
            a = br.readLine();
        }
        double[][] materialMatrix = new double[materialArray.size()][6];
        for(int i = 0 ; i < materialArray.size(); i++){
            for(int j = 0; j < materialArray.get(i).length - 1; j++){
                materialMatrix[i][j] = Double.parseDouble(String.valueOf(materialArray.get(i)[j]));
            }
            materialMatrix[i][5] = x;
        }
        return materialMatrix;
    }
    public boolean materialReader(String materialName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(materialFolderPath + materialName + ".txt"));

        String a = br.readLine();
        boolean split = false;

        if(a.split("\t")[5].equalsIgnoreCase("1")){
            split = true;
        }
        return split;
    }
}