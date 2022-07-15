import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.SystemColor.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Window extends JFrame implements ActionListener
{

    public JButton[] arrayButs;
    private Color kolor;
    private Container c;
  
    int rozmiar = readFile.returnRozmiar(Main.nameFile);
    static int[][] matrixPrint = Main.pathMaze;
    public Square[][] tabSquare; //= new Kwadrat[rozmiar][rozmiar];
        
    public Window()
    {
        super("Labirynt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(Main.nameFile=="Labirynty/5x5.txt")
            setSize(400, 400);
        else if(Main.nameFile=="Labirynty/15x15.txt")     
            setSize(900, 900);
        else if(Main.nameFile=="Labirynty/12x12.txt")     
            setSize(800, 800);
        else 
           setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(rozmiar, rozmiar));
        c = getContentPane();
        
        
        tabSquare = new Square[rozmiar][rozmiar];
        arrayButs=new JButton[rozmiar*rozmiar];

        
          for(int i=0;i<rozmiar;i++)
        {
            for(int j=0;j<rozmiar;j++)
            {
                tabSquare[i][j] = new Square(matrixPrint[i][j]);
               // System.out.print(tabKwadrat[i][j].getNumber()+ " ");
            }
           // System.out.println();
        }
        
        Square[] tab = new Square[rozmiar*rozmiar];
        int licz = 0;
        for(int k=0;k<rozmiar;k++)
            {
                for(int j=0;j<rozmiar;j++)
                {
                    tab[licz] = tabSquare[k][j];
                    licz++;
                }
            }
        
        
        for(int i=0; i<rozmiar*rozmiar; i++){
            arrayButs[i] = new JButton();
            add(arrayButs[i]);
            if(i==0)
                arrayButs[i].setText("Start");
            else if(i==rozmiar*rozmiar-1)
                arrayButs[i].setText("Koniec");
                
                arrayButs[i].setBackground(choiceColor(tab[i]));
            
        }

        
        setVisible(true);
        setResizable(false);
    } 
    
    public Color choiceColor(Square square)
    {
       Color color = null;
       if(square.getNumber() == 2)
           color = Color.RED;
        
        else if(square.getNumber() == 1)
            color = Color.BLACK;
        
        else
            color = Color.WHITE;
         
       return color;
    }

    public void actionPerformed(ActionEvent e)
    {
        String label = e.getActionCommand();
      
        if(label.equals("Zamknij"))
        {
            System.exit(0);
        }

    }
}