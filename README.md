# VGP

VGP 2.0 is a finite element code for temperature calculations in one dimension targeted on fire safety design. It is useful when calculating thermal penetration in walls and fire protection of for example steel members. The name VGP (VärmeGenomströmningsProgram) comes from a code developed by Jörgen Thor solving the same set of problems.

## Installation

1. Install [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) or any IDE that can handle java-code projects.
2. Copy the code by downloading the code or clone it using github desktop. A description of how-to is given [here](https://docs.github.com/en/desktop/contributing-and-collaborating-using-github-desktop/adding-and-cloning-repositories/cloning-and-forking-repositories-from-github-desktop).
3. Create a .txt-file called VGP.txt and save it in a folder called c:\VGP\. This file should contain paths to files and folders of other input files used by VGP. The file should be written on the form

        CONFIG_PATH '<path><file name of the config file>.txt  
        INPUT_PATH '<path><file name of input file>.txt  
        OUTPUT_PATH '<path><file name of the output file>.txt  
        MATERIAL_PATH '<path><name of the folder with materials>\  
        FIRE_PATH '<path><file name of the fire file>.txt  

4. In the folder with the config file, create a file with the same name as <file name of the config file>. This file is used to specify general settings of the calculations such as time step, calculation model, heat transfer coefficient, boundary conditions etc... The choices are specified in the class Constants and is written on the form given in the object setConstants() on the rows 137 to 153. Each choice is there presented with a brief description of the parameter a the beginning of the [class](https://github.com/Kongzhu79/VGP/blob/main/src/vgp/Constants.java).
5. In the folder with the input file, create a file with the same name as <file name of the input file>. In this file, the layers in the calculation is specified on the form

        <time in seconds>  
        <thickness of layer1 in mm> <material1>  
        <thickness of layer2 in mm> <material2>  
        etc...

6. If the fire needs to be specified as time-temperature pairs, create a file with the same name as <file name of the fire file>. The time-temperature pairs in this file is specified on the form

        <time in seconds> <Tf>
        <time in seconds> <Tf>
        etc...
        
or as 

        <time in seconds> <Tr> <Tg>
        <time in seconds> <Tr> <Tg>
        etc...

for cases where different radiation- and gas temperatures are required.

7. Material are created as text files saved in the folder specified in VGP.txt. One file per material is needed and the file is written on the form

        <temperature> <k> <cp> <density> <moisture content> <fall-off temperature>
        <temperature> <k> <cp> <density> <moisture content>
        etc...
        
When calling for a material in the input file, use the file name of the material (without .txt at the end), i.e., \<material1\> etc. There are no limitations as to how many materials can be created or used in the input file.

## Running the code
  
By executing the the main class, the files are read and calculated. The output is generated in the prompt of the IDE but also in a file named <file name of the output file> located as specified in VGP.txt. 

## To-Do
    
- [x] Clean up amongst calculation models  
- [x] Change all "Vectors" to ArrayList  
- [x] Structure and comment the code  
- [x] Clean abundant code  
- [x] Make material database as external files  
- [x] Include possibility of constant material properties in cooling phase  
- [x] Separate paths in VGP.txt for the different files  
- [x] Write new calculation model based on enthalpy  
- [x] Include fall-off
- [ ] Gaussian Quadrature (maybe-project)
- [ ] Improve time step generator (maybe-project)  
- [ ] Create UI (maybe-project)  
