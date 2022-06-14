
package vgp;

import java.io.*;

/**
 *
 * @author jsm
 */
public class Fire {

    public double getTexposed(double tid)throws IOException{
        
        double Tf = 0;

        if(Constants.FIRE_CURVE_EXPOSED == 1){
            Tf = Constants.CONSTANT_TEMPERATURE_EXPOSED;
        }
        else if(Constants.FIRE_CURVE_EXPOSED == 2){
            Tf = this.getStandardFire(tid);
        }
        else if(Constants.FIRE_CURVE_EXPOSED == 3){
            Tf = this.getRadiativeConvectiveTemperature(tid, Constants.EPSILON, Constants.H_EXPOSED);
        }
        else if(Constants.FIRE_CURVE_EXPOSED == 4){
            Tf = this.getExternalFire(tid);
        }
        else if(Constants.FIRE_CURVE_EXPOSED == 5){
            Tf = this.getHydroCarbonFire(tid);
        }
        else if(Constants.FIRE_CURVE_EXPOSED == 6){
            Tf = this.getParametricFire(tid);
        }
        return Tf;
    }

    public double getTunexposed(double tid)throws IOException{

        double Tf = 0;

        if(Constants.FIRE_CURVE_UNEXPOSED == 1){
            Tf = Constants.CONSTANT_TEMPERATURE_UNEXPOSED;
        }
        else if(Constants.FIRE_CURVE_UNEXPOSED == 2){
            Tf = this.getStandardFire(tid);
        }
        else if(Constants.FIRE_CURVE_UNEXPOSED == 3){
            Tf = this.getRadiativeConvectiveTemperature(tid, Constants.EPSILON, Constants.H_UNEXPOSED);
        }
        else if(Constants.FIRE_CURVE_UNEXPOSED == 4){
            Tf = this.getExternalFire(tid);
        }
        else if(Constants.FIRE_CURVE_UNEXPOSED == 5){
            Tf = this.getHydroCarbonFire(tid);
        }
        else if(Constants.FIRE_CURVE_UNEXPOSED == 6){
            Tf = this.getParametricFire(tid);
        }
        return Tf;
    }

    //The standard fire curve as defined in EN 1991-1-2
    public double getStandardFire(double tid){

        return 20 + 345 * Math.log10(8.0 * tid / 60 + 1);
    }

    //Calculation of a singular fire temperature from an input file with time temperature pairs
    public double getRadiativeConvectiveTemperature(double tid, double epsilon, double hc)throws IOException{

        IO io = new IO();

        double[][] temperatureMatrix = io.getFireTemperature();
        double Tf = 0;
        double[] T = new double[2];

        double a = temperatureMatrix.length;

        if(temperatureMatrix[0].length == 2){
            Tf = temperatureMatrix[0][1];
            for(int i = 1; i < a; i++){
                if(tid < temperatureMatrix[i][0]){
                    double timeOverLastTimeStep = tid - temperatureMatrix[i - 1][0];
                    double differenceTfBetweenInput = temperatureMatrix[i][1] - temperatureMatrix[i - 1][1];
                    double differenceTimeBetweenInput = temperatureMatrix[i][0] - temperatureMatrix[i - 1][0];
                    double TfIncrement = differenceTfBetweenInput / differenceTimeBetweenInput;

                    Tf = temperatureMatrix[i - 1][1] + timeOverLastTimeStep * TfIncrement;
                    break;
                }
            }
        }

        else if(temperatureMatrix[0].length == 3){
            T[0] = temperatureMatrix[0][1];
            T[1] = temperatureMatrix[0][2];

            for(int j = 0; j < 2; j++){
                for(int i = 0; i < a; i++){
                    if(tid < temperatureMatrix[i][0]){
                        double timeOverLastTimeStep = tid - temperatureMatrix[i - 1][0];
                        double differenceTfBetweenInput = temperatureMatrix[i][j + 1] - temperatureMatrix[i - 1][j + 1];
                        double differenceTimeBetweenInput = temperatureMatrix[i][0] - temperatureMatrix[i - 1][0];
                        double TfIncrement = differenceTfBetweenInput / differenceTimeBetweenInput;

                        T[j] = temperatureMatrix[i - 1][j + 1] + timeOverLastTimeStep * TfIncrement;
                        break;
                        }
                }
            }
            Tf = this.Malendowski(T[0], T[1], epsilon, hc);
        }
        return Tf;
    }

    //Calculation of the adiabatic surface temperature as presented by Michael Malendowski (the value b in Malendowskis method is equal to hc, thus hc = b in the declaration)
    //The external fire as defined EN 1991-1-2
     public double getExternalFire(double tid){
        return 20 + 660 * (1- 0.687 * Math.exp(-0.32 * tid / 60) - 0.313 * Math.exp(-3.8 * tid / 60));
    }

    //The hydrocarbon fire curve as defined in EN 1991-1-2
    public double getHydroCarbonFire(double tid){
        return 20 + 1080 * (1 - 0.325 * Math.exp(-0.167 * tid / 60) - 0.675 * Math.exp(-2.5 * tid / 60));
    }

    //Standard fire curve including a cooling phase starting from Constants.TID_TILL_AVSVALNING
    public double getParametricFire(double tid){

        double Tf;
        double maxTemperature = 20 + 1325 * (1 - 0.324 * Math.exp(-0.2 * Constants.GAMMA * Constants.TIME_TO_MAX_TEMPERATURE / 60.0) - 0.204 * Math.exp(-1.7 * Constants.GAMMA * Constants.TIME_TO_MAX_TEMPERATURE / 60.0) - 0.472 * Math.exp(-19 * Constants.GAMMA * Constants.TIME_TO_MAX_TEMPERATURE / 60.0));
        double timeToCool = (Constants.TIME_TO_MAX_TEMPERATURE + (maxTemperature - Constants.CONSTANT_TEMPERATURE_UNEXPOSED) / Constants.COOLING_RATE) * 60;

        if(tid <= Constants.TIME_TO_MAX_TEMPERATURE * 60){
            Tf = 20 + 1325 * (1 - 0.324 * Math.exp(-0.2 * Constants.GAMMA * tid / 3600.0) - 0.204 * Math.exp(-1.7 * Constants.GAMMA * tid / 3600.0) - 0.472 * Math.exp(-19 * Constants.GAMMA * tid / 3600.0));
        }
        else if(tid <= timeToCool){
            double timeToMaxTemperature = tid - Constants.TIME_TO_MAX_TEMPERATURE * 60;
            Tf = maxTemperature - timeToMaxTemperature * Constants.COOLING_RATE / 60.0;
        }
        else{
            Tf = Constants.CONSTANT_TEMPERATURE_UNEXPOSED;
        }
        return Tf;
    }

    //Calculation of AST from Tr and Tg.
    public double Malendowski(double Tr, double Tg, double epsilon, double hc){

        //For coding purposes, b in Malendowskis method is changed to hc as they are essentially the same value.

        double a = Constants.SIGMA * epsilon;
        double c = - (a * Math.pow(Tr + 273.15, 4) + hc * (Tg + 273.15));

        double alfa = Math.pow(Math.sqrt(3.0) * Math.sqrt(27.0 * Math.pow(a, 2) * Math.pow(hc, 4) - 256.0 * Math.pow(a, 3) * Math.pow(c, 3) + 9.0 * a * Math.pow(hc, 2)),(1.0 / 3.0));
        double beta = 4.0 * Math.pow(2.0 / 3.0, 1.0 / 3.0) * c;
        double gamma = Math.pow(18.0, 1.0 / 3.0) * a;

        double M = Math.sqrt(beta / alfa + alfa / gamma);

        return 1.0 / 2.0 * (-M + Math.sqrt(2 * hc / (a * M) - Math.pow(M, 2))) - 273.15;
    }

}
