//Implement the Percolation data type using the quick-find data type QuickFindUF.java from algs4.jar.
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation{
   private boolean[] conduct;               //*to record whether the grid is conduct
   private int count;                       //*to account the number of component
   private int length;
   //QuickFindUF uf, uf2;						//uf2 is used to solve the backwash problem
   WeightedQuickUnionUF uf, uf2;
   public Percolation(int N)                // create N-by-N grid, with all sites initially blocked
   {       
       length=N;
       count=N*N;
       //uf = new QuickFindUF(count+2);
       //uf2 = new QuickFindUF(count+1);
       uf = new WeightedQuickUnionUF(count+2);
	   uf2 = new WeightedQuickUnionUF(count+1);
       for (int i=0;i<length;i++)
       {
           uf.union(i,count);               //*for the top row of node, connect them to the top node
           uf.union(i+count-length,count+1);//*for the bottom row of node, connect them to the top node
           uf2.union(i,count);               //*for the top row of node, connect them to the top node
       }
       conduct=new boolean[count+2];        //the extra two nodes are for the top and bottom nodes, i[count] is the top node, i[count+1] is the bottom node
       for (int i=0;i<count+2;i++)                //*initialization, every component is not conductive
           conduct[i]=false;
   }
   public void open(int row, int col)       // open the site (row, col) if it is not open already
   {
       int number=row*length+col;             // N=4, (2,3)=2*4+3=11      
       //First, turn on the block
       if(isOpen(row, col)==false)
       {           
           conduct[number]=true;
       }
       //Second, union the block with adjacent places, if the adjacent place is open       
       if(col!=0 && isOpen(row, col-1))     //left
       {
           uf.union(number,number-1);
           uf2.union(number,number-1);
       }
       if(col!=length-1 && isOpen(row, col+1))     //right
       {
           uf.union(number,number+1);
           uf2.union(number,number+1);
       }
       if(row!=0 && isOpen(row-1, col))     //top
       {
           uf.union(number,number-length);
           uf2.union(number,number-length);
       }
       if(row!=length-1 && isOpen(row+1, col))     //bottom
       {
           uf.union(number,number+length);   
           uf2.union(number,number+length);   
       }
       
   }
   public boolean isOpen(int row, int col)  // is the site (row, col) open?
   {
       int number=row*length+col;
       if(conduct[number]==false)
           return false;
       else
           return true;
   }
   public boolean isFull(int row, int col)  // is the site (row, col) full?
   {
       if (uf2.find(row*length+col)==uf2.find(count) && isOpen(row, col))
           return true;
       else
           return false;
   }
   public int numberOfOpenSites()           // number of open sites
   {
       int opensites=0;
       for (int i=0;i<length;i++)                //*initialization, every component is not conductive
       {
           for (int j=0;j<length;j++)
           {
               if (isOpen(i,j)==true)
                   opensites++;
           }
       }
       return opensites;
   }
   public boolean percolates()              // does the system percolate?
   {
       if (uf.find(count)==uf.find(count+1))
           return true;
       else
           return false;
   }
}
