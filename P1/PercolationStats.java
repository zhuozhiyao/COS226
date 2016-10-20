public class PercolationStats 
{
   public double[] Threshold;            //the array total Threshold is used to record the threshold number for every random experiment
   public int times;
   public int length;
   public PercolationStats(int N, int T)   // perform T independent experiments on an N-by-N grid
   {
       times=T;
       length=N;
       Threshold = new double[T];
       for (int i=0;i<T;i++)
       {
           Percolation perc = new Percolation(N);
           while(perc.percolates()==false)
           {
               int randomrow=StdRandom.uniform(0, N);
               int randomcol=StdRandom.uniform(0, N);
               if(!perc.isOpen(randomrow,randomcol))
                   {
                       perc.open(randomrow,randomcol);
                   }
           }
           Threshold[i]=perc.numberOfOpenSites();       
           Threshold[i]=Threshold[i]/(length*length);     
       }
   }
   public double mean()                    // sample mean of percolation threshold
   {
       return StdStats.mean(Threshold);
   }
   public double stddev()                  // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(Threshold);
   }
   public double confidenceLow()           // low  endpoint of 95% confidence interval
   {
       return mean()-1.96*stddev()/Math.sqrt(times);
   }
   public double confidenceHigh()          // high endpoint of 95% confidence interval
   {
       return mean()+1.96*stddev()/Math.sqrt(times);
   }
    public static void main(String[] args) {
        Stopwatch Clock = new Stopwatch();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);   
        PercolationStats perc = new PercolationStats(N,T);
        System.out.println(perc.mean());
        System.out.println(perc.stddev());
        System.out.println(perc.confidenceLow());
        System.out.println(perc.confidenceHigh());
        System.out.println("The elapsed time is " + Clock.elapsedTime());        
    }
}