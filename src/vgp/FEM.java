/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

import java.util.*;
import java.io.*;

import static java.lang.System.arraycopy;

/**
 *
 * @author jsm
 */
public class FEM {

    FemCalc fc = new FemCalc();
    Fire fire = new Fire();
    ArrayList<ArrayList<double[]>> TList = new ArrayList<>();
    ArrayList<int[]> layerCountUpdate = new ArrayList<>();
    public ArrayList<int[]> getLayerCountUpdate() {
        return layerCountUpdate;
    }
    int finished = -1;
    int c = 1;
    int cnt = 0;
    double[] Tsplit;
    public double[] Esplit;
    double[][] Tarray;
    double[][] C;
    double[][] Cinv;
    double start;
    public double[][] getTarray(){
        return Tarray;
    }
    public void fem(int time, ArrayList<double[][]> material, boolean adiabatic)throws IOException{

        Matrix m = new Matrix();

        double[] T = new double[material.size() + 1];
        double[] Q;
        Arrays.fill(T, Constants.CONSTANT_TEMPERATURE_UNEXPOSED);
        if(time == 0) {
            Tarray = new double[time + 1][T.length + 2];
        }
        else T = Tarray[Tarray.length - 1];

        //Initialization: build the matrices C^1, K and the arrays Q and T
        //material consists of matrices with the material composition of each layer

        double[][] K;
        double Texposed;
        double Tunexposed;
        int cnt = 0;
//        int c = 1;
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
                if (Tarray[0].length - 2 >= 0) arraycopy(T, 0, Tarray[cnt], 2, Tarray[0].length - 2);
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
    public void femSplit1(double start, int time, ArrayList<ArrayList<double[][]>> materialSplit, boolean adiabatic)throws IOException{

        Matrix m = new Matrix();
        fc.globalMatrixNull();
        fc.globalMatris(materialSplit);
        this.finished = 1;

        if(this.start == 0){
            this.Tsplit = new double[fc.getGlobalMatrixM()];
        }

        for(int i = 0; i < fc.getGlobalMatrixM(); i++){
            if(start == 0){
                this.Tsplit[i] = Constants.CONSTANT_TEMPERATURE_UNEXPOSED;
            }
        }

        double[] TsplitMax = new double[this.Tsplit.length];
        double[] Q;
        Tarray = new double[time + 1][this.Tsplit.length + 2];
        ArrayList<double[]> Ltemp = new ArrayList<>();

        for(int i = 0; i < this.Tsplit.length; i++) {
            TsplitMax[i] = this.Tsplit[i];
        }

        //Initialization: build the matrices C^1, K and the arrays Q and T
        //material consists of matrices with the material composition of each layer

        double[][] K;
        double Texposed;
        double Tunexposed;
        for(int i = (int) Math.round(start * Constants.TIME_STEPS_PER_SECOND); i < time * Constants.TIME_STEPS_PER_SECOND + 1; i++){
            if(i >= time * Constants.TIME_STEPS_PER_SECOND / 10 * c){
                System.out.print("%");
                c++;
            }

            double[] Ttemp = new double[this.Tsplit.length + 2];
            Texposed = fire.getTexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);
            Tunexposed = fire.getTunexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);

            if((i % Constants.TIME_STEPS_PER_SECOND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Texposed;
                if (Tarray[0].length - 2 >= 0) arraycopy(this.Tsplit, 0, Tarray[cnt], 2, Tarray[0].length - 2);

                Ttemp[0] = cnt;
                Ttemp[1] = Texposed;
                arraycopy(this.Tsplit, 0, Ttemp, 2, Ttemp.length - 2);
                Ltemp.add(Ttemp);
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
            Q = fc.getQ(this.Tsplit, Texposed, Tunexposed, adiabatic, fc.getVoidLayer());

            double[] A = m.matrisXarray(Cinv, Q);
            double[][] A2 = m.matrisXmatris(Cinv, K);
            double[] B = m.matrisXarray(A2, this.Tsplit);
            double[] D = m.arraySubtraction(A, B);
            double[] E = m.arrayXconstant(D, 1.0 / Constants.TIME_STEPS_PER_SECOND);
            this.Tsplit = m.arrayAddition(E, this.Tsplit);

            for (int j = 0; j < Tsplit.length; j++) {
                if (Constants.KC_MAX == 1.0){
                    TsplitMax[j] = Math.max(TsplitMax[j], this.Tsplit[j]);
                }
                else{
                    TsplitMax[j] = this.Tsplit[j];
                }
            }
            if(Material.splitNodes.size() != 0) {
                if (this.Tcheck(Material.splitNodes, Tsplit) && Constants.MODEL > 1 ) {
                    this.start = (i + 1) * 1.0 / Constants.TIME_STEPS_PER_SECOND;
                    arraycopy(this.Tsplit, 0, Ttemp, 2, Ttemp.length - 2);
                    this.finished = -1;
                    break;
                }
            }
        }
    this.TList.add(Ltemp);
    }
    public void femSplitFallOff(int time, ArrayList<ArrayList<ArrayList<double[][]>>> materialSplit, boolean adiabatic, ArrayList<String[]> inputList, boolean splitModel)throws IOException {

        IO io = new IO();
        Material m = new Material();
        System.out.print("Progression of the calculation: ");
        double[] tempT;
        int listLength = materialSplit.size();
        int cnt = 0;
        ArrayList<ArrayList<ArrayList<double[][]>>> materialSplitU = new ArrayList<>();
        inputList = io.inputList;

        while(this.start < time) {
            m.material(inputList, splitModel);
            materialSplitU.add(m.getLayerListSplit());
            layerCountUpdate.add(m.layerCount);
            this.femSplit1(this.start, time, materialSplitU.get(cnt), adiabatic);
            if (finished != 1) {
//System.out.println("hej");
                inputList = this.removeFirstInputList(inputList);
                Tsplit = this.removeFirst(Tsplit);
                cnt++;
            }
            if (this.finished == 1) {
                break;
            }
        }
        System.out.println();
    }
    public void femTASEFFallOff(int time, ArrayList<ArrayList<ArrayList<double[][]>>> materialSplit, boolean adiabatic, ArrayList<String[]> inputList, boolean splitModel)throws IOException {

        IO io = new IO();
        Material m = new Material();
        System.out.print("Progression of the calculation: ");
        double[] tempT;
        int listLength = materialSplit.size();
        int cnt = 0;
        ArrayList<ArrayList<ArrayList<double[][]>>> materialSplitU = new ArrayList<>();
        inputList = io.inputList;

        while(this.start < time) {
            m.materialTASEF(inputList, splitModel);
            materialSplitU.add(m.getLayerListSplit());
            layerCountUpdate.add(m.layerCount);
            this.femTASEF(this.start, time, materialSplitU.get(cnt), adiabatic);
            if (finished != 1) {
                inputList = this.removeFirstInputList(inputList);
                Tsplit = this.removeFirst(Tsplit);
                cnt++;
            }
            if (this.finished == 1) {
                break;
            }
        }
        System.out.println();
    }
    public double[] removeFirst(double[] a) {
        double[] t = new double[a.length - 1];
        arraycopy(a, 1, t, 0, a.length - 1);
        return t;
    }
    public ArrayList<String[]> removeFirstInputList(ArrayList<String[]> a) {
        ArrayList<String[]> t = new ArrayList<>();
        for(int i = 1; i < a.size(); i++){
            t.add(a.get(i));
        }
        return t;
    }
    public void femSplit(int time, ArrayList<ArrayList<double[][]>> materialSplit, boolean adiabatic)throws IOException {

        System.out.print("Progression of the calculation: ");
        this.femSplit1(0, time, materialSplit, adiabatic);
        System.out.println();
    }
    public boolean Tcheck(ArrayList<double[]> splitNodes, double[] Tsplit){

        boolean tCheck = splitNodes.get(0)[0] == 2 && Tsplit[1] > splitNodes.get(0)[1];

        return tCheck;
    }
    public void femTASEF(double start, int time, ArrayList<ArrayList<double[][]>> materialSplit, boolean adiabatic)throws IOException{

        Matrix m = new Matrix();
        fc.globalMatrixNull();
        fc.globalMatris(materialSplit);
        this.finished = 1;

        if(this.start == 0){
            this.Tsplit = new double[fc.getGlobalMatrixM()];
        }

        for(int i = 0; i < fc.getGlobalMatrixM(); i++){
            if(start == 0){
                this.Tsplit[i] = Constants.CONSTANT_TEMPERATURE_UNEXPOSED;
            }
        }
        this.Esplit = new double[this.Tsplit.length];

        //Initalize the entalphy in each node according to the method by Thor.
        ArrayList<double[][]> material = new ArrayList<>();
        for(int i = 0; i < materialSplit.size(); i++) {
            material.addAll(materialSplit.get(i));
        }
        for(int i = 0; i < this.Tsplit.length; i++){
            if(i == 0) {
                this.Esplit[i] = fc.getE(0, material.get(i), this.Tsplit, i);
            }
            else if(i == this.Tsplit.length - 1) {
                this.Esplit[i] = fc.getE(material.get(i - 1), 0, this.Tsplit, i);
            }
            else{
                this.Esplit[i] = fc.getE(material.get(i - 1), material.get(i), this.Tsplit, i);
            }
        }
        double[] TsplitMax = new double[this.Tsplit.length];
        double[] Q;
        Tarray = new double[time + 1][this.Tsplit.length + 2];
        ArrayList<double[]> Ltemp = new ArrayList<>();

        for(int i = 0; i < this.Tsplit.length; i++) {
            TsplitMax[i] = this.Tsplit[i];
        }

        //Initialization: build the matrices Cm, K and the arrays Q, E and T
        //material consists of matrices with the material composition of each layer
        double[][] K;
        double Texposed;
        double Tunexposed;
        for(int i = (int) Math.round(start * Constants.TIME_STEPS_PER_SECOND); i < time * Constants.TIME_STEPS_PER_SECOND + 1; i++){
            if(i >= time * Constants.TIME_STEPS_PER_SECOND / 10 * c){
                System.out.print("%");
                c++;
            }
            double[] Ttemp = new double[this.Tsplit.length + 2];
            Texposed = fire.getTexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);
            Tunexposed = fire.getTunexposed(i * 1.0 / Constants.TIME_STEPS_PER_SECOND);

            if((i % Constants.TIME_STEPS_PER_SECOND) == 0){
                Tarray[cnt][0] = cnt;
                Tarray[cnt][1] = Texposed;
                if (Tarray[0].length - 2 >= 0) arraycopy(this.Tsplit, 0, Tarray[cnt], 2, Tarray[0].length - 2);

                Ttemp[0] = cnt;
                Ttemp[1] = Texposed;
                arraycopy(this.Tsplit, 0, Ttemp, 2, Ttemp.length - 2);
                Ltemp.add(Ttemp);
                cnt++;
            }
            //Step 1: calculate E[j+1] = (Q[j] - K[j]T[j]) * deltaT + E[j]

            //Step 2: update C^1, Q and K
            //C is the matrix where energy content is specified
            //Q is an array where the boundary conditions are specified
            //K is the matrix where conduction is specified

            //Step 3: repeat until final time is reached
            //voidL is initiated when using m.CInverted, need some change to be able to skip this row.
            double[][] cmInv = m.CInverted(fc.CmSplit(materialSplit, this.Tsplit, fc.getGlobalMatrixM()));
            K = fc.KSplit(materialSplit, TsplitMax, fc.getGlobalMatrixM());
            Q = fc.getQ(this.Tsplit, Texposed, Tunexposed, adiabatic, fc.getVoidLayer());

            double[] D = m.arraySubtraction(Q, m.matrisXarray(K, this.Tsplit));
            double[] E = m.arrayXconstant(D, 1.0 / Constants.TIME_STEPS_PER_SECOND);

            this.Esplit = m.arrayAddition(this.Esplit, m.arrayAddition(Q, E));
            this.Tsplit = fc.getTfromE(materialSplit, this.Tsplit, this.Esplit);

            for (int j = 0; j < Tsplit.length; j++) {
                if (Constants.KC_MAX == 1.0){
                    TsplitMax[j] = Math.max(TsplitMax[j], this.Tsplit[j]);
                }
                else{
                    TsplitMax[j] = this.Tsplit[j];
                }
            }

            if(Material.splitNodes.size() != 0) {
                if (this.Tcheck(Material.splitNodes, Tsplit) && Constants.MODEL > 1 ) {
                    this.start = (i + 1) * 1.0 / Constants.TIME_STEPS_PER_SECOND;
                    arraycopy(this.Tsplit, 0, Ttemp, 2, Ttemp.length - 2);
                    this.finished = -1;
                    break;
                }
            }
        }
        this.TList.add(Ltemp);

    }
}
