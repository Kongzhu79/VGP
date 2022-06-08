/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vgp;

/**
 *
 * @author jsm
 */
public class MaterialData {
    
    public double[][] md(double x, int i){
        MaterialData md = new MaterialData();
        double[][] temp;
        
        if (i == 0){
            temp = md.Luftspalt();
        }
        else if(i == 1){
            temp = md.normalgips(x);
        }
        else if(i == 2){
            temp = md.GFsultan(x);
        }
        else if(i == 3){
            temp = md.Steel(x);
        }
        else if(i == 4){
            temp = md.Concrete(x);
        }
        else if(i == 5){
            temp = md.Betong(x);
        }
        else if(i == 6){
            temp = md.GNsultan(x);
        }
        else if(i == 7){
            temp = md.GFsultan(x);
        }
        else if(i == 8){
            temp = md.RW(x, 30.0);
        }
        else if(i == 9){
            temp = md.RW(x, 70.0); // ska vara 70 för att få bättre överensstämmelse med VGP
        }
        else if(i == 10){
            temp = md.RW(x, 85.0); // ska vara 85 för att få bättre överensstämmelse med VGP
        }
        else if(i == 11){
            temp = md.RW(x, 120.0); // ska vara 120 för att få bättre överensstämmelse med VGP
        }
        else if(i == 12){
            temp = md.iso(x);
        }
        else if(i == 13){
            temp = md.fibreGlass(x);
        }
        else if(i == 14){
            temp = md.Minerit(x);
        }
        else if(i == 15){
            temp = md.PromatectH(x);
        }
        else if(i == 16){
            temp = md.PromatectL(x);
        }
        else if(i == 17){
            temp = md.wood(x);
        }
        else if(i == 18){
            temp = md.Vermiculux(x);
        }
        else if(i == 19){
            temp = md.Brennix(x);
        }
        else if(i == 20){
            temp = md.meyco1350(x);
        }
        else if(i == 21){
            temp = md.cellplast(x);
        }
        else if(i == 22){
            temp = md.laminate(x);
        }
        else if(i == 23){
            temp = md.core(x);
        }
        else if(i == 24){
            temp = md.marinePlusBlanket(x);
        }
        else if(i == 25){
            temp = md.adhesive(x);
        }
        else if(i == 26){
            temp = md.tegel(x);
        }
        else if(i == 27){
            temp = md.custom(x);
        }
        else if(i == 28){
            temp = md.outerShell(x);
        }
        else if(i == 29){
            temp = md.moistureBarrier(x);
        }
        else if(i == 30){
            temp = md.thermalBarrier(x);
        }
        else if(i == 31){
            temp = md.epidermis(x);
        }
        else if(i == 32){
            temp = md.dermis(x);
        }
        else if(i == 33){
            temp = md.subcutaneous(x);
        }
        else if(i == 34){
            temp = md.cotton(x);
        }
        else{
            System.out.println("Materialet " + " finns inte");
            temp = null;
        }
        
        return temp;
    }
    
    public double[][] iso(double x){
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 12000;
        //värmeledningstal
        temp[0][1] = 0.13;
        temp[1][1] = 0.13;
        //Specifikt värme
        temp[0][2] = 750;
        temp[1][2] = 750;
        //Densitet
        temp[0][3] = 1500;
        temp[1][3] = 1500;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }

    public double[][] normalgips(double x){
        double[][] temp = new double [13][6];

//De här materialdata är de som används som standard för normalgips

//Justering av gipsvärden för att stämma bättre med VGP.
//Jämförande kontroll av temperaturer gjorda för gipsväggar med luftspalt och olika isolering.
//Justeringen mot GN består enbart i att temp[0][2] är höjt från 2435 till 12000 så att fuktavgången stämmer bättre.

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixade till nya värden
        temp[0][1] = 0.2124;
        temp[1][1] = 0.2124;
        temp[2][1] = 0.1416;
        temp[3][1] = 0.1652;
        temp[4][1] = 0.1888;
        temp[5][1] = 0.354;
        temp[6][1] = 1.18;
        temp[7][1] = 3.54;
        temp[8][1] = 7.08;
        temp[9][1] = 11.8;
        temp[10][1] = 11.8;
        temp[11][1] = 11.8;
        temp[12][1] = 11.8;

        //c
        temp[0][2] = 266;
        temp[1][2] = 12000;
        temp[2][2] = 266;
        temp[3][2] = 266;
        temp[4][2] = 1063;
        temp[5][2] = 1063;
        temp[6][2] = 1063;
        temp[7][2] = 1063;
        temp[8][2] = 1063;
        temp[9][2] = 1063;
        temp[10][2] = 1063;
        temp[11][2] = 1063;
        temp[12][2] = 1063;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 790;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 21;
        }

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }


    public double[][] GN(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixade till nya värden
        temp[0][1] = 0.2124;
        temp[1][1] = 0.2124;
        temp[2][1] = 0.1416;
        temp[3][1] = 0.1652;
        temp[4][1] = 0.1888;
        temp[5][1] = 0.354;
        temp[6][1] = 1.18;
        temp[7][1] = 3.54;
        temp[8][1] = 7.08;
        temp[9][1] = 11.8;
        temp[10][1] = 11.8;
        temp[11][1] = 11.8;
        temp[12][1] = 11.8;

        //c
        temp[0][2] = 223;
        temp[1][2] = 2435;
        temp[2][2] = 266;
        temp[3][2] = 266;
        temp[4][2] = 1063;
        temp[5][2] = 1063;
        temp[6][2] = 1063;
        temp[7][2] = 1063;
        temp[8][2] = 1063;
        temp[9][2] = 1063;
        temp[10][2] = 1063;
        temp[11][2] = 1063;
        temp[12][2] = 1063;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 790;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 21;
        }

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] GF(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - Fixat till nya värden
        temp[0][1] = 0.299;
        temp[1][1] = 0.299;
        temp[2][1] = 0.180;
        temp[3][1] = 0.180;
        temp[4][1] = 0.180;
        temp[5][1] = 0.180;
        temp[6][1] = 0.180;
        temp[7][1] = 0.180;
        temp[8][1] = 0.239;
        temp[9][1] = 0.958;
        temp[10][1] = 11.97;
        temp[11][1] = 23.94;
        temp[12][1] = 47.9;

        //c
        temp[0][2] = 400;
        temp[1][2] = 1500;
        temp[2][2] = 500;
        temp[3][2] = 500;
        temp[4][2] = 1000;
        temp[5][2] = 1000;
        temp[6][2] = 1000;
        temp[7][2] = 700;
        temp[8][2] = 1000;
        temp[9][2] = 1000;
        temp[10][2] = 1000;
        temp[11][2] = 900;
        temp[12][2] = 900;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 900;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 21;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] Steel(double x){
        double[][] temp = new double [9][6];

        //Temperaturer
        temp[0][0] = 20;
        temp[1][0] = 600;
        temp[2][0] = 690;
        temp[3][0] = 720;
        temp[4][0] = 735;
        temp[5][0] = 750;
        temp[6][0] = 790;
        temp[7][0] = 900;
        temp[8][0] = 1200;

        //k
        temp[0][1] = 53.3;
        temp[1][1] = 34.0;
        temp[2][1] = 31.0;
        temp[3][1] = 30.0;
        temp[4][1] = 29.5;
        temp[5][1] = 29.0;
        temp[6][1] = 27.7;
        temp[7][1] = 27.3;
        temp[8][1] = 27.3;

        //c
//        for (int i = 0; i < temp.length; i++){
        temp[0][2] = 440;
        temp[1][2] = 760;
        temp[2][2] = 937;
        temp[3][2] = 1388;
        temp[4][2] = 5000;
        temp[5][2] = 1438;
        temp[6][2] = 847;
        temp[7][2] = 650;
        temp[8][2] = 650;
//        }

        //Densitet
        for(int i = 0; i < temp.length; i++){
            temp[i][3] = 7850;
        }

        //Fukt
        for(int i = 0; i < temp.length; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < temp.length; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] GFsultan(double x){
        double[][] temp = new double [14][6];

        //Temperaturer
        temp[0][0] = 0;
        temp[1][0] = 75;
        temp[2][0] = 98;
        temp[3][0] = 102;
        temp[4][0] = 125;
        temp[5][0] = 135;
        temp[6][0] = 145;
        temp[7][0] = 398;
        temp[8][0] = 400;
        temp[9][0] = 610;
        temp[10][0] = 670;
        temp[11][0] = 685;
        temp[12][0] = 800;
        temp[13][0] = 1200;

        //k
        temp[0][1] = 0.208;
        temp[1][1] = 0.208;
        temp[2][1] = 0.208;
        temp[3][1] = 0.119;
        temp[4][1] = 0.119;
        temp[5][1] = 0.119;
        temp[6][1] = 0.119;
        temp[7][1] = 0.119;
        temp[8][1] = 0.128;
        temp[9][1] = 0.205;
        temp[10][1] = 0.227;
        temp[11][1] = 0.223;
        temp[12][1] = 0.275;
        temp[13][1] = 5.531;

        //c
        temp[0][2] = 1500;
        temp[1][2] = 1500;
        temp[2][2] = 9560;
        temp[3][2] = 10900;
        temp[4][2] = 18615;
        temp[5][2] = 2000;
        temp[6][2] = 925;
        temp[7][2] = 756;
        temp[8][2] = 755;
        temp[9][2] = 615;
        temp[10][2] = 3076;
        temp[11][2] = 615;
        temp[12][2] = 615;
        temp[13][2] = 615;

        //Densitet
        for(int i = 0; i < 14; i++){
            temp[i][3] = 800;
        }

        //Fukt
        for(int i = 0; i < 14; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 14; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] GNsultan(double x){
        double[][] temp = new double [14][6];

        //Temperaturer
        temp[0][0] = 0;
        temp[1][0] = 75;
        temp[2][0] = 98;
        temp[3][0] = 102;
        temp[4][0] = 125;
        temp[5][0] = 135;
        temp[6][0] = 145;
        temp[7][0] = 398;
        temp[8][0] = 400;
        temp[9][0] = 610;
        temp[10][0] = 670;
        temp[11][0] = 685;
        temp[12][0] = 800;
        temp[13][0] = 1200;

        //k
        temp[0][1] = 0.208;
        temp[1][1] = 0.208;
        temp[2][1] = 0.208;
        temp[3][1] = 0.119;
        temp[4][1] = 0.119;
        temp[5][1] = 0.119;
        temp[6][1] = 0.119;
        temp[7][1] = 0.119;
        temp[8][1] = 1.128;
        temp[9][1] = 1.205;
        temp[10][1] = 2.227;
        temp[11][1] = 3.223;
        temp[12][1] = 5.275;
        temp[13][1] = 5.531;

        //c
        temp[0][2] = 1500;
        temp[1][2] = 1500;
        temp[2][2] = 9560;
        temp[3][2] = 10900;
        temp[4][2] = 18615;
        temp[5][2] = 2000;
        temp[6][2] = 925;
        temp[7][2] = 756;
        temp[8][2] = 755;
        temp[9][2] = 615;
        temp[10][2] = 3076;
        temp[11][2] = 615;
        temp[12][2] = 615;
        temp[13][2] = 615;

        //Densitet
        for(int i = 0; i < 14; i++){
            temp[i][3] = 800;
        }

        //Fukt
        for(int i = 0; i < 14; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 14; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] RW(double x, double dens){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        if(dens < 60){
            temp[0][1] = 0.0354;
            temp[1][1] = 0.0590;
            temp[2][1] = 0.0944;
            temp[3][1] = 0.1534;
            temp[4][1] = 0.2242;
            temp[5][1] = 0.3186;
            temp[6][1] = 0.4366;
            temp[7][1] = 0.649;
            temp[8][1] = 1.475;
            temp[9][1] = 2.36;
            temp[10][1] = 4.956;
            temp[11][1] = 10.62;
            temp[12][1] = 17.7;
        }
        else if(dens < 80){
            temp[0][1] = 0.0354;
            temp[1][1] = 0.0472;
            temp[2][1] = 0.0708;
            temp[3][1] = 0.1062;
            temp[4][1] = 0.1652;
            temp[5][1] = 0.236;
            temp[6][1] = 0.3186;
            temp[7][1] = 0.5074;
            temp[8][1] = 0.944;
            temp[9][1] = 1.18;
            temp[10][1] = 1.77;
            temp[11][1] = 2.36;
            temp[12][1] = 4.72;
        }
        else if(dens < 120){
            temp[0][1] = 0.0354;
            temp[1][1] = 0.0472;
            temp[2][1] = 0.0590;
            temp[3][1] = 0.0826;
            temp[4][1] = 0.118;
            temp[5][1] = 0.177;
            temp[6][1] = 0.2242;
            temp[7][1] = 0.354;
            temp[8][1] = 0.5192;
            temp[9][1] = 0.7316;
            temp[10][1] = 0.944;
            temp[11][1] = 1.18;
            temp[12][1] = 1.475;
        }
        else{
            temp[0][1] = 0.0354;
            temp[1][1] = 0.0472;
            temp[2][1] = 0.0590;
            temp[3][1] = 0.0826;
            temp[4][1] = 0.0944;
            temp[5][1] = 0.118;
            temp[6][1] = 0.1652;
            temp[7][1] = 0.2242;
            temp[8][1] = 0.295;
            temp[9][1] = 0.4248;
            temp[10][1] = 0.59;
            temp[11][1] = 0.767;
            temp[12][1] = 0.944;
        }

        //c
        temp[0][2] = 750;
        temp[1][2] = 850;
        temp[2][2] = 950;
        temp[3][2] = 1000;
        temp[4][2] = 1100;
        temp[5][2] = 1000;
        temp[6][2] = 1300;
        temp[7][2] = 1430;
        temp[8][2] = 1600;
        temp[9][2] = 1600;
        temp[10][2] = 1600;
        temp[11][2] = 1600;
        temp[12][2] = 1600;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = dens;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] fibreGlass(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k
        temp[0][1] = 0.0354;
        temp[1][1] = 0.0590;
        temp[2][1] = 0.0944;
        temp[3][1] = 0.1534;
        temp[4][1] = 0.2242;
        temp[5][1] = 0.3186;
        temp[6][1] = 0.4366;
        temp[7][1] = 0.649;
        temp[8][1] = 25.0;
        temp[9][1] = 50.0;
        temp[10][1] = 75.0;
        temp[11][1] = 90.0;
        temp[12][1] = 100.0;

        //c
        temp[0][2] = 1340;
        temp[1][2] = 1340;
        temp[2][2] = 1380;
        temp[3][2] = 1380;
        temp[4][2] = 1380;
        temp[5][2] = 1390;
        temp[6][2] = 1390;
        temp[7][2] = 1390;
        temp[8][2] = 1390;
        temp[9][2] = 1390;
        temp[10][2] = 1390;
        temp[11][2] = 1400;
        temp[12][2] = 1400;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 20;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] Minerit(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        temp[0][1] = 0.2596;
        temp[1][1] = 0.2478;
        temp[2][1] = 0.2360;
        temp[3][1] = 0.2242;
        temp[4][1] = 0.1770;
        temp[5][1] = 0.2124;
        temp[6][1] = 0.2360;
        temp[7][1] = 0.2360;
        temp[8][1] = 0.2596;
        temp[9][1] = 0.4720;
        temp[10][1] = 0.7080;
        temp[11][1] = 0.9440;
        temp[12][1] = 1.1800;

        //c
        temp[0][2] = 1120;
        temp[1][2] = 1120;
        temp[2][2] = 1120;
        temp[3][2] = 1120;
        temp[4][2] = 1120;
        temp[5][2] = 1120;
        temp[6][2] = 1120;
        temp[7][2] = 1120;
        temp[8][2] = 1120;
        temp[9][2] = 1120;
        temp[10][2] = 1120;
        temp[11][2] = 1120;
        temp[12][2] = 1120;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 900;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] PromatectH(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        temp[0][1] = 0.1770;
        temp[1][1] = 0.1770;
        temp[2][1] = 0.1888;
        temp[3][1] = 0.1888;
        temp[4][1] = 0.2006;
        temp[5][1] = 0.2006;
        temp[6][1] = 0.2124;
        temp[7][1] = 0.2124;
        temp[8][1] = 0.2242;
        temp[9][1] = 0.2242;
        temp[10][1] = 0.2360;
        temp[11][1] = 0.2360;
        temp[12][1] = 0.2478;

        //c
        for(int i = 0; i < 13; i++){
            temp[i][2] = 1120;
        }

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 900;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] PromatectL(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        temp[0][1] = 0.0826;
        temp[1][1] = 0.0838;
        temp[2][1] = 0.0897;
        temp[3][1] = 0.0968;
        temp[4][1] = 0.1062;
        temp[5][1] = 0.1180;
        temp[6][1] = 0.1357;
        temp[7][1] = 0.1534;
        temp[8][1] = 0.1711;
        temp[9][1] = 0.1888;
        temp[10][1] = 0.2065;
        temp[11][1] = 0.2242;
        temp[12][1] = 0.2360;

        //c
        for(int i = 0; i < 13; i++){
            temp[i][2] = 1120;
        }

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 450;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] wood(double x){
        double[][] temp = new double [14][6];

        IO io = new IO();
        double densitet = 450;
        double fukt = 5;

        //Temperaturer
        temp[0][0] = 0;
        temp[1][0] = 98;
        temp[2][0] = 99;
        temp[3][0] = 120;
        temp[4][0] = 121;
        temp[5][0] = 200;
        temp[6][0] = 250;
        temp[7][0] = 300;
        temp[8][0] = 350;
        temp[9][0] = 400;
        temp[10][0] = 500;
        temp[11][0] = 600;
        temp[12][0] = 800;
        temp[13][0] = 1200;

        //k
        temp[0][1] = 0.12;
        temp[1][1] = 0.126;
        temp[2][1] = 0.132;
        temp[3][1] = 0.138;
        temp[4][1] = 0.144;
        temp[5][1] = 0.15;
        temp[6][1] = 0.123;
        temp[7][1] = 0.097;
        temp[8][1] = 0.07;
        temp[9][1] = 0.08;
        temp[10][1] = 0.09;
        temp[11][1] = 0.22;
        temp[12][1] = 1.5; //i orginal 0.35
        temp[13][1] = 10.5;  //i orginal 1.50

        //c
        temp[0][2] = 1530 * (1 + fukt / 100.0);
        temp[1][2] = 1770 * (1 + fukt / 100.0);
        temp[2][2] = 13600 * (1 + fukt / 100.0);
        temp[3][2] = 13500 * 1;
        temp[4][2] = 2120 * 1;
        temp[5][2] = 2000 * 1;
        temp[6][2] = 1620 * 0.93;
        temp[7][2] = 710 * 0.76;
        temp[8][2] = 850 * 0.52;
        temp[9][2] = 1000 * 0.38;
        temp[10][2] = 1200 * 0.33;
        temp[11][2] = 1400 * 0.28;
        temp[12][2] = 1650 * 0.26;
        temp[13][2] = 1650 * 0;

        //Densitet
        for(int i = 0; i < 14; i++){
            temp[i][3] = densitet;
        }

        //Fukt
        for(int i = 0; i < 14; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 14; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] Concrete(double x){
        double[][] temp = new double [13][6];
        double fuktFaktor = 5.5;

        //Temperaturer
        temp[0][0] = 0;
        temp[1][0] = 99;
        temp[2][0] = 100;
        temp[3][0] = 115;
        temp[4][0] = 200;
        temp[5][0] = 300;
        temp[6][0] = 400;
        temp[7][0] = 500;
        temp[8][0] = 600;
        temp[9][0] = 700;
        temp[10][0] = 800;
        temp[11][0] = 1000;
        temp[12][0] = 1200;

        //k
        temp[0][1] = 1.36;
        temp[1][1] = 1.236;
        temp[2][1] = 1.223;
        temp[3][1] = 1.211;
        temp[4][1] = 1.111;
        temp[5][1] = 1.003;
        temp[6][1] = 0.907;
        temp[7][1] = 0.823;
        temp[8][1] = 0.749;
        temp[9][1] = 0.687;
        temp[10][1] = 0.637;
        temp[11][1] = 0.570;
        temp[12][1] = 0.549;

        //c
        temp[0][2] = 900;
        temp[1][2] = 900;
        temp[2][2] = 900.0 + 380 * fuktFaktor;
        temp[3][2] = 900.0 + 380 * fuktFaktor;
        temp[4][2] = 1000;
        temp[5][2] = 1050;
        temp[6][2] = 1100;
        temp[7][2] = 1100;
        temp[8][2] = 1100;
        temp[9][2] = 1100;
        temp[10][2] = 1100;
        temp[11][2] = 1100;
        temp[12][2] = 1100;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 2400;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] Betong(double x){
        double[][] temp = new double [13][6];
        double fuktFaktor = 5.5;

        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        temp[0][1] = 1.7936;
        temp[1][1] = 1.4160;
        temp[2][1] = 1.2390;
        temp[3][1] = 1.1682;
        temp[4][1] = 1.0974;
        temp[5][1] = 1.0148;
        temp[6][1] = 0.9440;
        temp[7][1] = 0.8732;
        temp[8][1] = 0.8024;
        temp[9][1] = 0.7316;
        temp[10][1] = 0.6372;
        temp[11][1] = 0.5428;
        temp[12][1] = 0.4484;

        //c
        temp[0][2] = 900;
        temp[1][2] = 900;
        temp[2][2] = 900.0 + 380 * fuktFaktor;
        temp[3][2] = 900.0 + 380 * fuktFaktor;
        temp[4][2] = 1000;
        temp[5][2] = 1050;
        temp[6][2] = 1100;
        temp[7][2] = 1100;
        temp[8][2] = 1100;
        temp[9][2] = 1100;
        temp[10][2] = 1100;
        temp[11][2] = 1100;
        temp[12][2] = 1100;

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 2400;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }

    public double[][] Vermiculux(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        temp[0][0] = 0.0826;
        temp[0][1] = 0.0826;
        temp[0][2] = 0.0826;
        temp[0][3] = 0.0944;
        temp[0][4] = 0.1062;
        temp[0][5] = 0.1298;
        temp[0][6] = 0.1534;
        temp[0][7] = 0.1888;
        temp[0][8] = 0.2124;
        temp[0][9] = 0.2478;
        temp[0][10] = 0.2714;
        temp[0][11] = 0.2950;
        temp[0][12] = 0.3540;

        //c
        for(int i = 0; i < 13; i++){
            temp[i][2] = 720;
        }

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 700;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }

    public double[][] Brennix(double x){
        double[][] temp = new double [13][6];

        //Temperaturer
        for(int i = 0; i < 13; i++){
            temp[i][0] = i * 100;
        }

        //k - fixat till nya värden
        temp[0][0] = 0.0165;
        temp[0][1] = 0.0165;
        temp[0][2] = 0.0165;
        temp[0][3] = 0.0165;
        temp[0][4] = 0.0165;
        temp[0][5] = 0.0165;
        temp[0][6] = 0.0165;
        temp[0][7] = 0.0201;
        temp[0][8] = 0.0236;
        temp[0][9] = 0.0354;
        temp[0][10] = 0.0590;
        temp[0][11] = 0.1180;
        temp[0][12] = 0.2360;

        //c
        for(int i = 0; i < 13; i++){
            temp[i][2] = 420;
        }

        //Densitet
        for(int i = 0; i < 13; i++){
            temp[i][3] = 2000;
        }

        //Fukt
        for(int i = 0; i < 13; i++){
            temp[i][4] = 0;
        }

        //Tjocklek
        for(int i = 0; i < 13; i++){
            temp[i][5] = x;
        }
        return temp;
    }
    public double[][] meyco1350(double x){
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 2000;
        //värmeledningstal
        temp[0][1] = 0.31;
        temp[1][1] = 0.31;
        //Specifikt värme
        temp[0][2] = 830;
        temp[1][2] = 830;
        //Densitet
        temp[0][3] = 1500;
        temp[1][3] = 1500;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }

    public double[][] cellplast(double x){
        double[][] temp = new double[5][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 200;
        temp[2][0] = 300;
        temp[3][0] = 600;
        temp[4][0] = 1200;
        //värmeledningstal
        temp[0][1] = 0.035;
        temp[1][1] = 0.035;
        temp[2][1] = 0.5;
        temp[3][1] = 100.0;
        temp[4][1] = 100.0;
        //Specifikt värme
        temp[0][2] = 1000;
        temp[1][2] = 1000;
        temp[2][2] = 1000;
        temp[3][2] = 1000;
        temp[4][2] = 1000;
        //Densitet
        temp[0][3] = 28;
        temp[1][3] = 28;
        temp[2][3] = 28;
        temp[3][3] = 28;
        temp[4][3] = 28;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;
        temp[4][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;
        temp[4][5] = x;

        return temp;
    }
    public double[][] laminate(double x){
        double[][] temp = new double[5][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 30;
        temp[2][0] = 80;
        temp[3][0] = 150;
        temp[4][0] = 1200;
        //värmeledningstal
        temp[0][1] = 0.04;
        temp[1][1] = 0.045;
        temp[2][1] = 0.05;
        temp[3][1] = 0.051;
        temp[4][1] = 0.052;
        //Specifikt värme
        temp[0][2] = 740;
        temp[1][2] = 750;
        temp[2][2] = 960;
        temp[3][2] = 1110;
        temp[4][2] = 1300;
        //Densitet
        temp[0][3] = 1870;
        temp[1][3] = 1870;
        temp[2][3] = 1870;
        temp[3][3] = 1870;
        temp[4][3] = 1870;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;
        temp[4][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;
        temp[4][5] = x;

        return temp;
    }

    public double[][] core(double x){
        double[][] temp = new double[5][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 20;
        temp[2][0] = 80;
        temp[3][0] = 150;
        temp[4][0] = 1200;
        //värmeledningstal
        temp[0][1] = 0.028;
        temp[1][1] = 0.029;
        temp[2][1] = 0.030;
        temp[3][1] = 0.045;
        temp[4][1] = 0.050;
        //Specifikt värme
        temp[0][2] = 1250;
        temp[1][2] = 1450;
        temp[2][2] = 2120;
        temp[3][2] = 2380;
        temp[4][2] = 2380;
        //Densitet
        temp[0][3] = 80;
        temp[1][3] = 80;
        temp[2][3] = 80;
        temp[3][3] = 80;
        temp[4][3] = 80;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;
        temp[4][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;
        temp[4][5] = x;

        return temp;
    }

    public double[][] marinePlusBlanket(double x){
        double[][] temp = new double[6][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 200;
        temp[2][0] = 400;
        temp[3][0] = 600;
        temp[4][0] = 800;
        temp[5][0] = 1000;
        //värmeledningstal
        temp[0][1] = 0.03;
        temp[1][1] = 0.06;
        temp[2][1] = 0.11;
        temp[3][1] = 0.17;
        temp[4][1] = 0.26;
        temp[5][1] = 0.38;
        //Specifikt värme
        temp[0][2] = 800;
        temp[1][2] = 800;
        temp[2][2] = 800;
        temp[3][2] = 800;
        temp[4][2] = 800;
        temp[5][2] = 800;
        //Densitet
        temp[0][3] = 64;
        temp[1][3] = 64;
        temp[2][3] = 64;
        temp[3][3] = 64;
        temp[4][3] = 64;
        temp[5][3] = 64;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;
        temp[4][4] = 0;
        temp[5][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;
        temp[4][5] = x;
        temp[5][5] = x;

        return temp;
    }

    public double[][] adhesive(double x){
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 1200;
        //värmeledningstal
        temp[0][1] = 0.3;
        temp[1][1] = 0.3;
        //Specifikt värme
        temp[0][2] = 840;
        temp[1][2] = 840;
        //Densitet
        temp[0][3] = 1400;
        temp[1][3] = 1400;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }

    public double[][] tegel(double x){
        double[][] temp = new double[6][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 20;
        temp[2][0] = 95;
        temp[3][0] = 110;
        temp[4][0] = 200;
        temp[5][0] = 1200;
        //värmeledningstal
        temp[0][1] = 0.42 * Math.pow(1.18, 2);
        temp[1][1] = 0.42 * Math.pow(1.18, 2);
        temp[2][1] = 0.77 * Math.pow(1.18, 2);
        temp[3][1] = 0.82 * Math.pow(1.18, 2);
        temp[4][1] = 1.26 * Math.pow(1.18, 2);
        temp[5][1] = 1.26 * Math.pow(1.18, 2);
        //Specifikt värme
        temp[0][2] = 564;
        temp[1][2] = 564;
        temp[2][2] = 564;
        temp[3][2] = 10716;
        temp[4][2] = 2820;
        temp[5][2] = 564;
        //Densitet
        temp[0][3] = 900;
        temp[1][3] = 900;
        temp[2][3] = 900;
        temp[3][3] = 900;
        temp[4][3] = 900;
        temp[5][3] = 900;
        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;
        temp[4][4] = 0;
        temp[5][4] = 0;
        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;
        temp[4][5] = x;
        temp[5][5] = x;

        return temp;
    }

    public double[][] custom(double x){
        
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.118;
        temp[1][1] = 0.118;

        //Specifikt värme
        temp[0][2] = 840;
        temp[1][2] = 840;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }

    public double[][] outerShell(double x){
        
        double[][] temp = new double[4][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 520;
        temp[2][0] = 600;
        temp[3][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.053;
        temp[1][1] = 0.053;
        temp[2][1] = 0.053;
        temp[3][1] = 0.053;

        //Specifikt värme
        temp[0][2] = 580;
        temp[1][2] = 580;
        temp[2][2] = 580;
        temp[3][2] = 580;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;
        temp[2][3] = 1000;
        temp[3][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;

        return temp;
    }
    public double[][] moistureBarrier(double x){
        
        double[][] temp = new double[4][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 520;
        temp[2][0] = 600;
        temp[3][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.043;
        temp[1][1] = 0.043;
        temp[2][1] = 0.043;
        temp[3][1] = 0.043;

        //Specifikt värme
        temp[0][2] = 860;
        temp[1][2] = 860;
        temp[2][2] = 860;
        temp[3][2] = 860;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;
        temp[2][3] = 1000;
        temp[3][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;

        return temp;
    }
    public double[][] thermalBarrier(double x){
        
        double[][] temp = new double[4][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 520;
        temp[2][0] = 600;
        temp[3][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.051;
        temp[1][1] = 0.051;
        temp[2][1] = 0.051;
        temp[3][1] = 0.051;

        //Specifikt värme
        temp[0][2] = 500;
        temp[1][2] = 500;
        temp[2][2] = 500;
        temp[3][2] = 500;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;
        temp[2][3] = 1000;
        temp[3][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;
        temp[2][4] = 0;
        temp[3][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;
        temp[2][5] = x;
        temp[3][5] = x;

        return temp;
    }
    public double[][] epidermis(double x){
        
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.255;
        temp[1][1] = 0.255;

        //Specifikt värme
        temp[0][2] = 4320;
        temp[1][2] = 4320;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }
    public double[][] dermis(double x){
        
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.523;
        temp[1][1] = 0.523;

        //Specifikt värme
        temp[0][2] = 3870;
        temp[1][2] = 3870;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }
    public double[][] subcutaneous(double x){
        
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.167;
        temp[1][1] = 0.167;

        //Specifikt värme
        temp[0][2] = 2760;
        temp[1][2] = 2760;

        //Densitet
        temp[0][3] = 1000;
        temp[1][3] = 1000;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }

    public double[][] cotton(double x){
        
        double[][] temp = new double[2][6];

        //Temperaturspann
        temp[0][0] = 0;
        temp[1][0] = 1200;

        //värmeledningstal
        temp[0][1] = 0.04;
        temp[1][1] = 0.04;

        //Specifikt värme
        temp[0][2] = 1300;
        temp[1][2] = 1300;

        //Densitet
        temp[0][3] = 300;
        temp[1][3] = 300;

        //Fuktkvot
        temp[0][4] = 0;
        temp[1][4] = 0;

        //Skikttjocklek
        temp[0][5] = x;
        temp[1][5] = x;

        return temp;
    }

    public double[][] Luftspalt(){
        double[][] temp = new double[2][6];
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[0].length; j++){
                temp[i][j] = 0.0;
            }
        }
        return temp;
    }

}
