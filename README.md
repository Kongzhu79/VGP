# VGP

Det stora städprojektet för VGP 2.0. Koden ska rensas på onödig kod och förhoppningen är att kunna skapa en beräkningsrutin som bygger på entalpi istället för som  idag,  att alla beräkningar måste gå genom  specifikt värme. Förhoppningen är att beräkningsrutinen blir stabilare. Saker som ska fixas:

1. Rensa bland beräkningsmodeller
2. Byta ut alla "Vector"  mot ArrayList
3. Strukturera och kommentera koden så att den blir begriplig för eftervärlden
4. Rensa bort överflödig kod
5. Flytta ut materialdatabasen
6. Skriv ny beräkningsrutin  som bygger på entalpi
7. Inkorporera Jacobs arbete med bortfall
8. Förbättra tidsstegsgeneratorn att motsvara TASEF?
9. Skapa grafisk output?

# Installation

Skapa en fil som heter VGP.txt och lägg den i en mapp som heter c:/VGP/. Denna fil ska innehålla sökvägen till mappen med indatafiler. I övrigt körs programmet som "vanligt".
