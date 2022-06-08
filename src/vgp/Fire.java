
package vgp;

import java.io.*;

/**
 *
 * @author jsm
 */
public class Fire {

    public double getTf(double tid)throws IOException{
        
        double Tf = 0;

        if(Konstanter.BRANDKURVA == 1){
            Tf = Konstanter.KONSTANT_TEMPERATUR_FRAMSIDA;
        }
        else if(Konstanter.BRANDKURVA == 2){
            Tf = this.getISO(tid);
        }
        else if(Konstanter.BRANDKURVA == 3){
            Tf = this.getRadiativeConvectiveTemperature(tid, Konstanter.EPSILON, Konstanter.H_BRAND);
        }
        else if(Konstanter.BRANDKURVA == 4){
            Tf = this.getExternalFire(tid);
        }
        else if(Konstanter.BRANDKURVA == 5){
            Tf = this.getHydroCarbonFire(tid);
        }
        else if(Konstanter.BRANDKURVA == 6){
            Tf = this.getParametricFire(tid, Konstanter.TID_TILL_AVSVALNING, Konstanter.GAMMA);
        }
        return Tf;
    }

    public double getTbaksida(double tid)throws IOException{

        double Tf = 0;

        if(Konstanter.BAKSIDEKURVA == 1){
            Tf = Konstanter.KONSTANT_TEMPERATUR_BAKSIDA;
        }
        else if(Konstanter.BAKSIDEKURVA == 2){
            Tf = this.getISO(tid);
        }
        else if(Konstanter.BAKSIDEKURVA == 3){
            Tf = this.getRadiativeConvectiveTemperature(tid, Konstanter.EPSILON, Konstanter.H_KALL);
        }
        else if(Konstanter.BAKSIDEKURVA == 4){
            Tf = this.getExternalFire(tid);
        }
        else if(Konstanter.BAKSIDEKURVA == 5){
            Tf = this.getHydroCarbonFire(tid);
        }
        else if(Konstanter.BAKSIDEKURVA == 6){
            Tf = this.getParametricFire(tid, Konstanter.TID_TILL_AVSVALNING, Konstanter.GAMMA);
        }
        return Tf;
    }

    //The standard fire curve as defined in EN 1991-1-2
    public double getISO(double tid){

        return 20 + 345 * Math.log10(8.0 * tid / 60 + 1);
    }

    //Calculation of a singular fire temperature from an input file with time temperature pairs
    public double getRadiativeConvectiveTemperature(double tid, double epsilon, double hc)throws IOException{

        IO io = new IO();

        double[][] temperaturMatris = io.getFireTemperature();
        double Tf = 0;
        double[] T = new double[2];

        double a = temperaturMatris.length;

        if(temperaturMatris[0].length == 2){
            Tf = temperaturMatris[0][1];
            for(int i = 1; i < a; i++){
                if(tid < temperaturMatris[i][0]){
                    double tidOverSenasteIndatasteget = tid - temperaturMatris[i - 1][0];
                    double skillnadTfMellanIndatasteg = temperaturMatris[i][1] - temperaturMatris[i - 1][1];
                    double skillnadTidMellanIndatasteg = temperaturMatris[i][0] - temperaturMatris[i - 1][0];
                    double TfPerTidsstegMellanIndatasteg = skillnadTfMellanIndatasteg / skillnadTidMellanIndatasteg;

                    Tf = temperaturMatris[i - 1][1] + tidOverSenasteIndatasteget * TfPerTidsstegMellanIndatasteg;
                    break;
                }
            }
        }

        else if(temperaturMatris[0].length == 3){
            T[0] = temperaturMatris[0][1];
            T[1] = temperaturMatris[0][2];

            for(int j = 0; j < 2; j++){
                for(int i = 0; i < a; i++){
                    if(tid < temperaturMatris[i][0]){
                        double tidOverSenasteIndatasteget = tid - temperaturMatris[i - 1][0];
                        double skillnadTfMellanIndatasteg = temperaturMatris[i][j + 1] - temperaturMatris[i - 1][j + 1];
                        double skillnadTidMellanIndatasteg = temperaturMatris[i][0] - temperaturMatris[i - 1][0];
                        double TfPerTidsstegMellanIndatasteg = skillnadTfMellanIndatasteg / skillnadTidMellanIndatasteg;

                        T[j] = temperaturMatris[i - 1][j + 1] + tidOverSenasteIndatasteget * TfPerTidsstegMellanIndatasteg;
                        break;
                        }
                }
            }
            Tf = this.Malendowski(T[0], T[1], epsilon, hc);
//            Tf = this.getTast() - 273;
        }
        return Tf;
    }

    //Calculation of the adiabatic surface temperature as presented by Michael Malendowski (the value b in Malendowskis method is equal to hc, thus hc = b in the declaration)
    public double Malendowski(double Tr, double Tg, double epsilon, double b){

        double a = Konstanter.SIGMA * epsilon;
        double c = - (a * Math.pow(Tr + 273.15, 4) + b * (Tg + 273.15));

        double alfa = Math.pow(Math.sqrt(3.0) * Math.sqrt(27.0 * Math.pow(a, 2) * Math.pow(b, 4) - 256.0 * Math.pow(a, 3) * Math.pow(c, 3) + 9.0 * a * Math.pow(b, 2)),(1.0 / 3.0));
        double beta = 4.0 * Math.pow(2.0 / 3.0, 1.0 / 3.0) * c;
        double gamma = Math.pow(18.0, 1.0 / 3.0) * a;

        double M = Math.sqrt(beta / alfa + alfa / gamma);

        return 1.0 / 2.0 * (-M + Math.sqrt(2 * b / (a * M) - Math.pow(M, 2))) - 273.15;
    }

    //The external fire as defined EN 1991-1-2
     public double getExternalFire(double tid){
        return 20 + 660 * (1- 0.687 * Math.exp(-0.32 * tid / 60) - 0.313 * Math.exp(-3.8 * tid / 60));
    }

    //The hydrocarbon fire curve as defined in EN 1991-1-2
    public double getHydroCarbonFire(double tid){
        return 20 + 1080 * (1 - 0.325 * Math.exp(-0.167 * tid / 60) - 0.675 * Math.exp(-2.5 * tid / 60));
    }

    //Standard fire curve including a cooling phase starting from Konstanter.TID_TILL_AVSVALNING
    public double getParametricFire(double tid, double tidTillAvsvalning, double gamma){

        double Tf;
        double avsvalningshastighetPerSekund = Konstanter.AVSVALNINGSHASTIGHET / 60.0;
        double maxTemperatur = this.getISO(tidTillAvsvalning * 60);
        double tidTillSvalt = (tidTillAvsvalning + (maxTemperatur - Konstanter.KONSTANT_TEMPERATUR_BAKSIDA) / Konstanter.AVSVALNINGSHASTIGHET) * 60;

        if(tid <= tidTillAvsvalning * 60){
            Tf = 20 + 1325 * (1 - 0.324 * Math.exp(-0.2 * gamma * tid / 3600.0) - 0.204 * Math.exp(-1.7 * gamma * tid / 3600.0) - 0.472 * Math.exp(-19 * gamma * tid / 3600.0));
        }
        else if(tid <= tidTillSvalt){
            double tidOverTidForAvsvalning = tid - tidTillAvsvalning * 60;
            Tf = maxTemperatur - tidOverTidForAvsvalning * avsvalningshastighetPerSekund;
        }
        else{
            Tf = Konstanter.KONSTANT_TEMPERATUR_BAKSIDA;
        }
        return Tf;
    }
}
