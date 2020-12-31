import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class TSP {
 public static void main(String[] args) throws IOException {

  
  BufferedReader userinput = new BufferedReader(new
    InputStreamReader(System.in));

  //Get file name
  String fileName = "";
  System.out.println("Place dataset in root folder and type filename (ex: data8.txt):");
  fileName = userinput.readLine();

  long startTime = System.currentTimeMillis();

  
  Scanner readFile;
  readFile = new Scanner(new File(fileName));

  //find size of problem based on input file
  int n = 0; 
  do{
   readFile.nextInt();
   n++;
  }while(readFile.hasNext());
  n = (int) Math.sqrt(n);
  readFile.close();



  //Create distance matrix
  int[][] cityDistances = new int[n][n];
  int i = 0;
  int j = 0;

  readFile = new Scanner(new File(fileName));

  //Populate distance matrix
  for(j=0;j<n;j++){
   for(i=0;i<n;i++){
    cityDistances [i][j] = readFile.nextInt();
   }
  }
  readFile.close();//close file


  int ha = 0;
 
  while(ha > n-1 || ha <= 0){ 

   System.out.println("Input consecutive maximum home/away games: ");
   ha = Integer.parseInt(userinput.readLine());

   if(ha > n-1 || ha <= 0){
    System.out.println("Invalid value entered. Value must be greater than 0 and less than (# of Teams - 1): ");
   }
  }



  int [][]starWeight = new int [n][1];
  int indexWeight = 0;
  for(j=0;j<n;j++){
   for(i=0;i<n;i++){
    starWeight[j][0] += cityDistances[i][j];
   }
  }

  
  int tempWeight = 10000000;

  for(i=0;i<n;i++){
   if(starWeight[i][0]<tempWeight){ 
    tempWeight = starWeight[i][0];
    indexWeight = i;
   }
  }
   
  int[]chosenPath = new int [n+1];
  int distance = 0;
    
  for(i=1;i<n;i++){
   if(i!=indexWeight){
    chosenPath[i] = i;
   }
  }
  
  chosenPath [0] = indexWeight;
  chosenPath [n] = indexWeight; 

  for(i=0;i<n;i++){
   distance += cityDistances[chosenPath[i]][chosenPath[i+1]];
  }
  
  int tempDistance=0;
  int [] tempPath = new int[n+1];
  int tempHold = 0;
  
  for(i=0;i<n+1;i++){ 
   tempPath[i] = chosenPath[i];
  }
  
  int count = 0;

  while(count!=100){
   
   for(j=1;j<n-1;j++){
    for(i=1;i<n-1;i++){
     tempHold=tempPath[j];
     tempPath[j]=tempPath[i];
     tempPath[i]=tempHold;
     tempDistance = 0;
     
     for(int z=0;z<n;z++){
      tempDistance += cityDistances[tempPath[z]][tempPath[z+1]];
     }
     if(tempDistance<distance){
      for(int b=0;b<n+1;b++){
       chosenPath[b]=tempPath[b];

      }
      distance = tempDistance; 
      
      
     }
     
     else{
      tempHold=tempPath[i];
      tempPath[i]=tempPath[j];
      tempPath[j]=tempHold;

     }

    }

   }
  
   count++;
  }

  int pathFormatted[] = new int [n];
  
  
  for(i=0;i<pathFormatted.length;i++){
   pathFormatted[i]=chosenPath[i+1];
  }
  
  
  int [][]schedule = new int [n][2*n-2];
  
  
  schedule = part2.part2(ha,pathFormatted,n); 

  
  int []teamDistance= new int [n];
  int totalDistance = 0;

  for(j=0;j<n;j++){
   for(i=0;i<(2*n-3);i++){

    if(i==0 && schedule[j][i]<0){
     
     teamDistance[j] += cityDistances[j][Math.abs(schedule[j][i])-1];
    }
    
    //2x home games or (1home 1away)
    if(schedule[j][i]>0){
     
     //not going anywhere 2x home
     if(schedule[j][i+1]>0){
      teamDistance[j] += cityDistances[j][j]; 
     }
     //1 home, 1 away
     else{
      teamDistance[j] += cityDistances[j][Math.abs(schedule[j][i+1])-1];
     }

    }
    //1away 1home
    else if(schedule[j][i+1]>0){
     teamDistance[j] += cityDistances[Math.abs(schedule[j][i])-1][j];
    }

    //2 away
    else{teamDistance[j] += cityDistances[Math.abs(schedule[j][i])-1][Math.abs(schedule[j][i+1])-1];
    }
    
    //If last game away return home
    if(i==(2*n-4) && schedule [j][i+1]<0){
     teamDistance[j] += cityDistances[j][Math.abs(schedule[j][i+1])-1];
    }
   }
   //Print out each team's travel distance
   System.out.println("Team "+(j+1)+ " has a total travel distance of: " + teamDistance[j]);
   totalDistance += teamDistance[j];
  }
  //Total distance traveled by all teams
  System.out.println();
  System.out.println("Total Team Travel Distance: " + totalDistance);
  System.out.println();
  //Total computation time
  long endTime = System.currentTimeMillis();
  System.out.println("Total time: " + (endTime-startTime) + "ms");

  //Write schedule to file
  BufferedWriter out= new BufferedWriter(new FileWriter("Solution "+fileName)); 
  for (int a=0; a<n;a++){
   for(int b=0;b<2*n-2;b++){
    out.write(String.valueOf(schedule[a][b]));
    out.write("\t");
   }
   out.newLine();
  }
  out.close();

 }

}