import java.util.*;
import java.lang.Math; 

public class A_Star
{
   int allSteps = 1;
   int lengthRoad = -1; //bo liczy od punktu (0,0)
   int ROW = readFile.returnRozmiar(Main.nameFile);
   int COL = readFile.returnRozmiar(Main.nameFile);

   cell[][] cellDetails;
   
   boolean isValid(int row, int col)
   {
    // Returns true if row number and column number
    // is in range
    return (row >= 0) && (row < ROW) &&
        (col >= 0) && (col < COL);
    }
    
    boolean isUnBlocked(int maze[][], int row, int col)
    {
        // Returns true if the cell is not blocked else false
            if (maze[row][col] == 0)
                return (true);
            else
                return (false);
    }
    
    boolean isDestination(int row, int col, Pair dest)
    {
        if (row == dest.x && col == dest.y)
            return (true);
        else
          return (false);
    }
    
    double calculateHValue(int row, int col, Pair dest)
    {
        // Return using the distance formula
     //  return ((double)Math.sqrt (((row-dest.x)*(row-dest.x))
                  //      + ((col-dest.y)*(col-dest.y))));
                  int zm = Math.abs(row-dest.x)+Math.abs(col-dest.y);
                    return zm;
    }
    
    public void printMaze(int maze[][])
    {
        
        for(int i=0;i<ROW;i++)
        {
            for(int j=0; j<COL; j++)
            {
                System.out.print(maze[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    void tracePath(cell cellDetails[][], Pair dest)
    {
        int row = dest.x;
        int col = dest.y;

        Stack Path = new Stack<Pair>();

        while ( !(cellDetails[row][col].parent_i == row
           && cellDetails[row][col].parent_j == col ))
           {
               Path.push (new Pair(row, col));
               int temp_row = cellDetails[row][col].parent_i;
               int temp_col = cellDetails[row][col].parent_j;
               row = temp_row;
               col = temp_col;
           }

           Path.push (new Pair(row, col));
           while (!Path.empty())
           {
               Pair p = (Pair)Path.peek();
               Path.pop();
               System.out.printf(">(%d,%d)",p.x, p.y);
               lengthRoad++;
               Main.pathMaze[p.x][p.y] = 2;
           }    
          
          System.out.println("\n\nDługość drogi: "+ lengthRoad+"\n"); 
          printMaze(Main.pathMaze);
          System.out.println("\nLiczba kroków: "+allSteps);
          //System.out.println("Log10(Łączna Liczba kroków): "+Math.log10(allSteps));
    }
    
    void aStarSearch(int grid[][], Pair src, Pair dest)
{
    // If the source is out of range
    if (isValid (src.x, src.y) == false)
    {
        System.out.println("Źródło jest nieprawidłowe");
        return;
    }

    // If the destination is out of range
    if (isValid (dest.x, dest.y) == false)
    {
        System.out.println("Cel jest nieprawidłowy");
        return;
    }

    // Either the source or the destination is blocked
    if (isUnBlocked(grid, src.x, src.y) == false ||
            isUnBlocked(grid, dest.x, dest.y) == false)
    {
        System.out.println("Źródło lub Cel jest zablokoway");
        return;
    }

    // If the destination cell is the same as source cell
    if (isDestination(src.x, src.y, dest) == true)
    {
        System.out.println("Juz jestesmy na mecie");
        return;
    }

    // Create a closed listand initialise it to false which means
    // that no cell has been included yet
    // This closed list is implemented as a boolean 2D array
    boolean[][] closedList = new boolean[ROW][COL];
    
    for(int i=0;i<ROW;i++)
    {
        for(int j=0;j<COL;j++)
         closedList[i][j] = false;
    }
  
    // Declare a 2D array of structure to hold the details
    //of that cell
    cellDetails = new cell[ROW][COL];

    int i;
    int j;


    for (i=0; i<ROW; i++)
    {
        for (j=0; j<COL; j++)
        {
            cellDetails[i][j] = new cell();
            cellDetails[i][j].f = Float.MAX_VALUE;
            cellDetails[i][j].g = Float.MAX_VALUE;
            cellDetails[i][j].h = Float.MAX_VALUE;
            cellDetails[i][j].parent_i = -1;
            cellDetails[i][j].parent_j = -1;
        }
    }

    // Initialising the parameters of the starting node
    i = src.x; 
    j = src.y;
    cellDetails[i][j].f = 0.0;
    cellDetails[i][j].g = 0.0;
    cellDetails[i][j].h = 0.0;
    cellDetails[i][j].parent_i = i;
    cellDetails[i][j].parent_j = j;

    /*
    Create an open list having information as-
    <f, <i, j>>
    where f = g + h,
    and i, j are the row and column index of that cell
    Note that 0 <= i <= ROW-1 & 0 <= j <= COL-1
    This open list is implenented as a set of pair of pair.
    */
    
     List<pPair> openList = new LinkedList<pPair>();
    
    openList.add( new pPair(0, new Pair(i,j)));
    
    boolean foundDest = false;
    int licznik=1;

    while (!openList.isEmpty())
    {
        allSteps++;
        pPair p = openList.get(0);
       
        // Remove this vertex from the open list
        openList.remove(0);
   

        // Add this vertex to the closed list
        i = p.getPair().x;
        j = p.getPair().y;
        closedList[i][j] = true;

    /*
        Generating all the 4 successor of this cell
        
                  N
                  |
            W----Cell----E
                  | 
                  S

        Cell-->Popped Cell (i, j)
        N --> North  (i-1, j)
        S --> South  (i+1, j)
        E --> East   (i, j+1)
        W --> West   (i, j-1)
        
        */
        // To store the 'g', 'h' and 'f' of the 4 successors
        double gNew, hNew, fNew;

        //----------- 1st Successor (North) ------------

        // Only process this cell if this is a valid one
        if (isValid(i-1, j) == true)
        {

            // If the destination cell is the same as the
            // current successor
            if (isDestination(i-1, j, dest) == true)

{
                // Set the Parent of the destination cell
                cellDetails[i-1][j].parent_i = i;
                cellDetails[i-1][j].parent_j = j;
                System.out.print("Znaleziona droga");
                tracePath (cellDetails, dest);
                foundDest = true;
                return;
            }
            // If the successor is already on the closed
            // list or if it is blocked, then ignore it.
            // Else do the following
            else if (closedList[i-1][j] == false &&
                    isUnBlocked(grid, i-1, j) == true)
            {
                gNew = cellDetails[i][j].g + 1.0;
                hNew = calculateHValue (i-1, j, dest);
                fNew = gNew + hNew;

                // If it isn’t on the open list, add it to
                // the open list. Make the current square
                // the parent of this square. Record the
                // f, g, and h costs of the square cell
                //           OR
                // If it is on the open list already, check
                // to see if this path to that square is better,
                // using 'f' cost as the measure.
                if (cellDetails[i-1][j].f == Float.MAX_VALUE ||
                        cellDetails[i-1][j].f > fNew)
                {
                    licznik++;
                    openList.add(new pPair(fNew,
                                            new Pair(i-1, j)));
                           
                    // Update the details of this cell
                    cellDetails[i-1][j].f = fNew;
                    cellDetails[i-1][j].g = gNew;
                    cellDetails[i-1][j].h = hNew;
                    cellDetails[i-1][j].parent_i = i;
                    cellDetails[i-1][j].parent_j = j;
                }
            }
        }

        //----------- 2nd Successor (South) ------------

        // Only process this cell if this is a valid one
        if (isValid(i+1, j) == true)
        {
 
            // If the destination cell is the same as the
            // current successor
            if (isDestination(i+1, j, dest) == true)
            {
                // Set the Parent of the destination cell
                cellDetails[i+1][j].parent_i = i;
                cellDetails[i+1][j].parent_j = j;
                System.out.print("Znaleziona droga");
                tracePath(cellDetails, dest);
                foundDest = true;
                return;
            }
            // If the successor is already on the closed
            // list or if it is blocked, then ignore it.
            // Else do the following
            else if (closedList[i+1][j] == false &&
                    isUnBlocked(grid, i+1, j) == true)
            {
                gNew = cellDetails[i][j].g + 1.0;
                hNew = calculateHValue(i+1, j, dest);
                fNew = gNew + hNew;

                // If it isn’t on the open list, add it to
                // the open list. Make the current square
                // the parent of this square. Record the
                // f, g, and h costs of the square cell
                //           OR
                // If it is on the open list already, check
                // to see if this path to that square is better,
                // using 'f' cost as the measure.
                if (cellDetails[i+1][j].f == Float.MAX_VALUE ||
                        cellDetails[i+1][j].f > fNew)
                {
                    licznik++;
                    openList.add(new pPair (fNew, new Pair (i+1, j)));
                
                    // Update the details of this cell
                    cellDetails[i+1][j].f = fNew;
                    cellDetails[i+1][j].g = gNew;
                    cellDetails[i+1][j].h = hNew;
                    cellDetails[i+1][j].parent_i = i;
                    cellDetails[i+1][j].parent_j = j;
                }
            }
        }

        //----------- 3rd Successor (East) ------------

        // Only process this cell if this is a valid one
        if (isValid (i, j+1) == true)
        {
            // If the destination cell is the same as the
            // current successor
            if (isDestination(i, j+1, dest) == true)
            {
                // Set the Parent of the destination cell
                cellDetails[i][j+1].parent_i = i;
                cellDetails[i][j+1].parent_j = j;
                System.out.println("Współrzędne drogi");
                tracePath(cellDetails, dest);
                foundDest = true;
                return;
            }

            // If the successor is already on the closed
            // list or if it is blocked, then ignore it.
            // Else do the following
            else if (closedList[i][j+1] == false &&
                    isUnBlocked (grid, i, j+1) == true)
            {
                gNew = cellDetails[i][j].g + 1.0;
                hNew = calculateHValue (i, j+1, dest);
                fNew = gNew + hNew;

                // If it isn’t on the open list, add it to
                // the open list. Make the current square
                // the parent of this square. Record the
                // f, g, and h costs of the square cell
                //           OR
                // If it is on the open list already, check
                // to see if this path to that square is better,
                // using 'f' cost as the measure.
                if (cellDetails[i][j+1].f == Float.MAX_VALUE ||
                        cellDetails[i][j+1].f > fNew)
                {
                    licznik++;
                    openList.add( new pPair(fNew,
                                        new Pair (i, j+1)));
           
                    // Update the details of this cell
                    cellDetails[i][j+1].f = fNew;
                    cellDetails[i][j+1].g = gNew;
                    cellDetails[i][j+1].h = hNew;
                    cellDetails[i][j+1].parent_i = i;
                    cellDetails[i][j+1].parent_j = j;
                }
            }
        }

        //----------- 4th Successor (West) ------------

        // Only process this cell if this is a valid one
        if (isValid(i, j-1) == true)
        {
            // If the destination cell is the same as the
            // current successor
            if (isDestination(i, j-1, dest) == true)
            {
                // Set the Parent of the destination cell
                cellDetails[i][j-1].parent_i = i;
                cellDetails[i][j-1].parent_j = j;
                System.out.println("Nie znaleziono ścieżki");
                tracePath(cellDetails, dest);
                foundDest = true;
                return;
            }

            // If the successor is already on the closed
            // list or if it is blocked, then ignore it.
            // Else do the following
            else if (closedList[i][j-1] == false &&
                    isUnBlocked(grid, i, j-1) == true)
            {
                gNew = cellDetails[i][j].g + 1.0;
                hNew = calculateHValue(i, j-1, dest);
                fNew = gNew + hNew;

                // If it isn’t on the open list, add it to
                // the open list. Make the current square
                // the parent of this square. Record the
                // f, g, and h costs of the square cell
                //           OR
                // If it is on the open list already, check
                // to see if this path to that square is better,
                // using 'f' cost as the measure.
                if (cellDetails[i][j-1].f == Float.MAX_VALUE ||
                        cellDetails[i][j-1].f > fNew)
                {
                    licznik++;
                    openList.add(new pPair (fNew,
                                        new Pair (i, j-1)));
              
                    // Update the details of this cell
                    cellDetails[i][j-1].f = fNew;
                    cellDetails[i][j-1].g = gNew;
                    cellDetails[i][j-1].h = hNew;
                    cellDetails[i][j-1].parent_i = i;
                    cellDetails[i][j-1].parent_j = j;
                }
            }
        }
       
    }
    // When the destination cell is not found and the open
    // list is empty, then we conclude that we failed to
    // reach the destiantion cell. This may happen when the
    // there is no way to destination cell (due to blockages)
    if (foundDest == false)
        System.out.println("Nie znaleziono ścieżki");

        
      
    return;
}




}
