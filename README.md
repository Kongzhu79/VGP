# VGP

Det stora städprojektet för VGP 2.0. Koden ska rensas på onödig kod och förhoppningen är att kunna skapa en beräkningsrutin som bygger på entalpi istället för som  idag,  att alla beräkningar måste gå genom  specifikt värme. Förhoppningen är att beräkningsrutinen blir stabilare. 

## Installation

1. Installera IntelliJ eller annat IDE som kan hantera java-projekt.
2. Kopiera koden härifrån antingen direkt via en .zip-fil eller genom att klona den med Github desktop eller motsvarande.
3. Skapa en fil som heter VGP.txt och lägg den i en mapp som heter c:/VGP/. Denna fil ska innehålla sökvägen till mappen med övriga  indatafiler.
4. I  mappen  med indatafiler, skapa en fil som heter VGP_config.txt. I den filen skrivs övergripande inställningar in som tidssteg, beräkningsmodell, värmeövergångstal, randvillkor osv... Vilka val som kan göras finns noterade i  filen Konstanter och  skrivs på den form som anges i  rutinen setConstants på raderna  83 till 99. Viss förklaring till varje parameter ges vid definitionen i samma fils inledning.
5. I mappen  med indatafiler,  skapa en fil som heter in.txt. I den filen skrivs det som ska beräknas på formen

<tid i sekunder>
<tjocklek i  mm> <material1>
<tjocklek i  mm> <material2>
osv.

Efter sista materialet se till så att det blir en radbrytning.
    
## Körning
  
Genom att exekvera  projektet i  det  valda IDE:et läses filerna in och beräknas. Det skapas utdata både i en fil som heter ut.txt och direkt  i IDE:ns terminal. 

## Att göra
    
Saker som ska fixas:

1. ~~Rensa bland beräkningsmodeller~~
2. ~~Byta ut alla "Vector" mot ArrayList~~
3. Strukturera och kommentera koden så att den blir begriplig för eftervärlden - pågående (på engelska?)
4. Rensa bort överflödig kod - pågående
5. Flytta ut materialdatabasen
6. Skriv ny beräkningsrutin  som bygger på entalpi
7. Inkorporera Jacobs arbete med bortfall
8. Förbättra tidsstegsgeneratorn att motsvara TASEF?
9. Skapa grafisk output?
