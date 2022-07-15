import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readFile
{
   public static int returnRozmiar(String plik){
        FileReader fileReader = null;
        BufferedReader reader = null;
        int m=0;
        try {
            fileReader = new FileReader(plik);
            reader = new BufferedReader(fileReader);
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                m++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return m;
    }
    public static int[][] odczyt(String nazwa)
    {
        FileReader fileReader = null;
        BufferedReader reader = null;
        int licznik = 0;
        int licznik2 = 1;
        int licznikpom = 0;
        int m = returnRozmiar(nazwa);
        int[][] tab = new int[m][m];
        boolean flaga = false;
        int j=0;
        try {
            fileReader = new FileReader(nazwa);
            reader = new BufferedReader(fileReader);
            String nextLine = null;
       
            while ((nextLine = reader.readLine()) != null) {
    
                if(flaga == true){
               licznik = 0;
               licznik2 = 1;
               
            }
             
             for(int i=0;i<m;i++){
                 
             
                    if(i==0){
                      tab[j][i] =  Integer.parseInt(nextLine.substring(licznik, 1));
                      //System.out.print(nextLine.substring(licznik, 1)+" ; ");
                    }
                      else
                       tab[j][i] =  Integer.parseInt(nextLine.substring(licznik, licznik2));
                      // System.out.print(nextLine.substring(licznik, licznik2)+" ; ");
                      
           
                licznik = licznik + 1;
                licznik2 = licznik2+1;
                
                
           }
               flaga = true;
               j++;
               
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   
        return tab;
    }
}
