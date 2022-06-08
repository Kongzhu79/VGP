/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 *
 * @author jsm
 */
public class IO {

    String filNamn;
    String filNamnKonstanter = "VGP_config";
    ArrayList<String[]> inputList;
    ArrayList<String[]> constList;
    int time;

    public int getTime(){
        return time;
    }
    public void InputOutput()throws IOException{
        IO io = new IO();

        filNamn = this.folderName() + "in";
        filNamnKonstanter = this.folderName() + filNamnKonstanter;
        inputList = io.FReader(filNamn);

        constList = io.FReaderKonstanter(filNamnKonstanter);
        time = io.getTime();
    }
    public String folderName()throws IOException{

        FileReader fr = new FileReader("C:\\VGP\\VGP.txt");
        BufferedReader br = new BufferedReader(fr);

        String fName = br.readLine();
        br.close();

        return fName;

    }
    public ArrayList<String[]> FReader(String filNamn)throws IOException{

        String[] stringArray;
        ArrayList<String[]> stringList = new ArrayList<>();

        FileReader fr = new FileReader(filNamn + ".txt");
        BufferedReader br = new BufferedReader(fr);

        BufferedWriter print;
        print = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.folderName() + "ut.txt")));

        time = Integer.parseInt(br.readLine());
        String a = br.readLine();
        while(a != null){
            stringArray = a.split(" ");
            stringList.add(stringArray);
            a = br.readLine();
        }
        print.close();
        return stringList;
    }
    public ArrayList<String[]> FReaderKonstanter(String filNamn)throws IOException{

        String[] stringArray;
        ArrayList<String[]> tempV = new ArrayList<>();

        FileReader fr = new FileReader(filNamn + ".txt");
        BufferedReader br = new BufferedReader(fr);

        String a = br.readLine();
        while(a != null){
            stringArray = a.split(" ");
            tempV.add(stringArray);
            a = br.readLine();
        }
        return tempV;
    }
    public double[][] getFireTemperature()throws IOException{
        String[] stringArray;
        ArrayList<String> tempV = new ArrayList<>();

        FileReader fr = new FileReader(this.folderName() + "fire.txt");
        BufferedReader br = new BufferedReader(fr);

        String a = br.readLine();
        while(a != null){
            tempV.add(a);
            a = br.readLine();
        }

        a = tempV.get(0);
        int nLength = tempV.size();
        int nValues = a.split("\t").length;
        double[][] fireTemperature;

        fireTemperature = new double[nLength][nValues];
        for(int i = 0; i < nLength; i++){
            a = tempV.get(i);
            stringArray = a.split("\t");
            for(int j = 0; j < nValues; j++){
                fireTemperature[i][j] = Double.parseDouble(stringArray[j]);
            }
        }
        return fireTemperature;
    }

    public void SystemUt(double[][] Tarray)throws IOException{
        DecimalFormat df = new DecimalFormat("0.0");
        DecimalFormat dfTime = new DecimalFormat("0");
        BufferedWriter print;
        print = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.folderName() + "ut.txt")));

        print.write("Brandkurva på framsidan: " + Konstanter.BRANDKURVA);
        print.newLine();

        for(String[] input : inputList){
            print.write(input[0] + " " + input[1]);
            print.newLine();
System.out.println(input[0] + " " + input[1]);
        }
        print.write("Brandkurva på baksidan: " + Konstanter.BAKSIDEKURVA);
        print.newLine();
System.out.println("Boundary unexposed side: " + Konstanter.BAKSIDEKURVA);

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

            i = i + Konstanter.SEKUNDER_MELLAN_UTSKRIFT;
        }
        print.close();
    }

    public void SystemUtSplit(double[] skiktTjocklek, double[][] Tarray, int[] splitCount)throws IOException{

        DecimalFormat df1 = new DecimalFormat("0.0");
        BufferedWriter print;
        print = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.folderName() + "ut.txt")));

        print.write("Exponering på framsidan: " + Konstanter.BRANDKURVA);
        print.newLine();
System.out.println("Boundary exposed side: " + Konstanter.BRANDKURVA);

        for(String[] input : inputList){
            print.write(input[0] + " " + input[1]);
            print.newLine();
System.out.println(input[0] + " " + input[1]);
        }
        print.write("Exponering på baksidan: " + Konstanter.BAKSIDEKURVA);
        print.newLine();
System.out.println("Boundary unexposed side: " + Konstanter.BAKSIDEKURVA);

        String timeAlign = "%4s";
        String align = "%8s";
        
        int a = Tarray.length;
        double c = 0;
        
System.out.print("Tid     -1.0     " + c);
        print.write("Tid     -1.0     " + c);
        for(double tjocklek : skiktTjocklek){
            c = tjocklek + c;
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
            i = i + Konstanter.SEKUNDER_MELLAN_UTSKRIFT;
        }
        print.close();
    }

    public void printTime(long startTime, long endTime) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        System.out.println("Run time: " + df1.format((endTime - startTime) / 1000000000.0).replace(",",".") + " sec");
    }
}