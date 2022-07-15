import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Main
{
    public static String nameFile = "";
    public static  int pathMaze[][];
    
    public static void printOptions()
    {
        System.out.println("Wybierz labirynt:");
        System.out.println("1: 5x5");
        System.out.println("2: 7x7");
        System.out.println("3: 10x10");
        System.out.println("4: 12x12");
        System.out.println("5: 15x15");
    }
    
    
    public static void main()
    {
        printOptions();
        
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        
        switch(choice)
        {
           case 1:
                System.out.println("\nLabirynt 5x5\n");
                nameFile = "Labirynty/5x5.txt"; 
                break;
           case 2:
             System.out.println("\nLabirynt 7x7\n");
                nameFile = "Labirynty/7x7.txt";
                break;
           case 3:
                System.out.println("\nLabirynt 10x10\n");
                nameFile = "Labirynty/10x10.txt"; 
                break;
           case 4:
                System.out.println("\nLabirynt 12x12\n");
                nameFile = "Labirynty/12x12.txt"; 
                break;
           case 5: 
                 System.out.println("\nLabirynt 15x15\n");
                 nameFile = "Labirynty/15x15.txt"; 
                 break;
           default:
                 System.out.println("Niepoprawny wybór");
        }
        
     
    int row = readFile.returnRozmiar(nameFile)-1;
    int col = readFile.returnRozmiar(nameFile)-1;
        
    /* Description of the Grid-
    0--> The cell is not blocked
    1--> The cell is blocked */
    
    int maze[][] = readFile.odczyt(nameFile);
    pathMaze = readFile.odczyt(nameFile); //tablica do pokazania drogi 
    
    // Start jest w lewym gornym rogu
    Pair source = new Pair(0, 0);
    // Koniec jest w prawym dolnym rogu
    Pair destination = new Pair(row, col);
    
    A_Star a_star = new A_Star();
    //wywołanie funkcji z argumentami: labirynt, start, meta
    a_star.aStarSearch(maze, source , destination);
   
    Run();
    
    }
  
   public static void Run(){ 
      EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
