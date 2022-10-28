# VGP

Det stora städprojektet för VGP 2.0. Koden ska rensas på onödig kod och förhoppningen är att kunna skapa en beräkningsrutin som bygger på entalpi istället för som  idag, att alla beräkningar måste gå genom specifikt värme. Förhoppningen är att beräkningsrutinen blir stabilare. 

## Installation

1. Installera IntelliJ eller annat IDE som kan hantera java-projekt.
2. Kopiera koden härifrån antingen direkt via en .zip-fil eller genom att klona den med Github desktop eller motsvarande.
3. Skapa en fil som heter VGP.txt och lägg den i en mapp som heter c:\VGP\. Denna fil ska innehålla sökvägar till mappen med övriga indatafiler på formen

        CONFIG_PATH '<sökväg><filnamn för configfilen>.txt  
        INPUT_PATH '<sökväg><filnamn för inputfilen>.txt  
        OUTPUT_PATH '<sökväg><filnamn för outputfilen>.txt  
        MATERIAL_PATH '<sökväg><filnamn för mappen med material>\  
        FIRE_PATH '<sökväg><filnamn för brandfilen>.txt  

4. I  mappen  med indatafiler, skapa en fil med samma namn som <filnamn för configfilen>. I den filen skrivs övergripande inställningar in som tidssteg, beräkningsmodell, värmeövergångstal, randvillkor osv... Vilka val som kan göras finns noterade i filen Konstanter och skrivs på den form som anges i rutinen setConstants på raderna 83 till 99. Viss förklaring till varje parameter ges vid definitionen i samma fils inledning.
5. I mappen  med indatafiler, skapa en fil med samma namn som <filnamn för inputfilen>. I den filen skrivs det som ska beräknas på formen

        <tid i sekunder>  
        <tjocklek i mm> <material1>  
        <tjocklek i mm> <material2>  
        osv.

Efter sista materialet se till så att det blir en radbrytning.

6. För det fall branden ska behöver specificeras i en separat fil görs det i filen <filnamn för branfilen> på formen

        <tid i sekunder> <Tf>
        <tid i sekunder> <Tf>
        
Eller på formen 

        <tid i sekunder> <Tr> <Tg>
        <tid i sekunder> <Tr> <Tg>

för de fall olika strålnings- och gastemperatur önskas. Dessa omräknas till en adiabatisk yttemperatur i VGP och ansätts sedan som randvillkor.
    
## Körning
  
Genom att exekvera projektet i det valda IDE:et läses filerna in och beräknas. Det skapas utdata både i en fil som heter <filnamn för outputfilen>.txt och direkt i IDE:ns terminal. 

## Att göra
    
Saker som ska fixas:

- [x] Rensa bland beräkningsmodeller  
- [x] Byta ut alla "Vector" mot ArrayList  
- [x] Strukturera och kommentera koden så att den blir begriplig för eftervärlden  
- [x] Rensa bort överflödig kod  
- [x] Flytta ut materialdatabasen  
- [x] Ge möjlighet att ha konstanta materialdata i avsvalningsfasen  
- [x] Separata sökvägar i VGP-filen för de olika indatafilerna  
- [ ] Skriv ny beräkningsrutin som bygger på entalpi  
- [x] Inkorporera Jacobs arbete med bortfall  
- [ ] Förbättra tidsstegsgeneratorn att motsvara TASEF?  
- [ ] Skapa grafisk output?  
