import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//command line arguments
//ExactMaxParser "testcaseNumber" , "howmany ways to parse" , "number of Clusters"
public class ExactMaxParser {
	public static void findMax(int max[] , int target[] , int clusters)
	{
		int tmax = 0;
		
		for(int i=1 ; i<clusters ; i++)
		{
			if(target[i]>tmax)
			{
				tmax = target[i];
				max[1] = i;
			}
		}
		
		max[0] = tmax;
	}
	
	public static void findWin(int win[] , int target[] , int clusters)
	{
		int tmin = 9999999;
		
		for(int i=1 ; i<clusters ; i++)
		{
			if(target[i]<tmin)
			{
				tmin = target[i];
				win[0] = i;
			}
		}
		win[1] = tmin;
	}
	
	public static void findSecond(int win[] , int target[] , int clusters)
	{
		int tmin = 9999999;
		
		for(int i=1 ; i<clusters ; i++)
		{
			if(i==win[0])
			{
				continue;
			}
			else if(target[i]<tmin)
			{
				tmin = target[i];
				win[2] = i;
			}
		}
		win[3] = tmin;
	}
	
	public static void findThird(int win[] , int target[] , int clusters)
	{
		int tmin = 9999999;
		
		for(int i=1 ; i<clusters ; i++)
		{
			if(i==win[0]||i==win[2])
			{
				continue;
			}
			else if(target[i]<tmin)
			{
				tmin = target[i];
				win[4] = i;
			}
		}
		win[5] = tmin;
	}
	
	
	public static void bubble_srt( int a[], int index[], int n )
	{
		  int i, j,t=0;
		  
		  for(i=1;i<n;i++)
		  {
			  index[i]=i;
		  }
		  
		  for(i = 0; i < n; i++)
		  {
			  for(j = 2; j < (n-i); j++)
			  {
				  if(a[j-1] > a[j])
				  {
					  t = a[j-1];
					  a[j-1]=a[j];
					  a[j]=t;
					  t = index[j-1];
					  index[j-1]=index[j];
					  index[j]=t;
					  
				  }
			  }
		  }
		  
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int ways = Integer.parseInt(args[1]);
		int clusters = Integer.parseInt(args[2]);
		ways++; 	// for array indexing starting from one
		clusters++; // for array indexing starting from one
		File output[][] = new File[ways][clusters];
		Scanner sout[][] = new Scanner[ways][clusters];
		String out[] = new String[ways];
		
		/**
		 * b1 = better cluster
		 * or = original cluster
		 * tt = the stupic take turns
		 * b2 = a worse better cluster with static d value and 
		 * b3 = original cluster with better assignments
		 * b4 = better cluster with easy assignment
		 * co = circle original
		 * cc = circle with centroid
		 */
		int co[] = new int[clusters];
		//int cc[] = new int[clusters];
		int b4[] = new int[clusters];
		int or[] = new int[clusters];
		
		// max cluster  [0] = the amount   [1] = whichmax
		int mco[] = new int[2];
		//int mcc[] = new int[2];
		int mb4[] = new int[2];
		int mor[] = new int[2];
		
		int win[] = new int[6];
		
		out[1] = "output_co_"+args[0]+"_";
		//out[2] = "output_cc_"+args[0]+"_";
		out[2] = "output_b4_"+args[0]+"_";
		out[3] = "output_or_"+args[0]+"_";
		
		// new output files
		for(int i=1 ; i<ways ; i++)
		{
			for(int j=1 ; j<clusters ; j++)
			{
				output[i][j] = new File(out[i]+j+".txt");
			}
		}
		
		// new output scanner
		try {
			
			for(int i=1 ; i<ways ; i++)
			{
				for(int j=1 ; j<clusters ; j++)
				{
					sout[i][j] = new Scanner(output[i][j]);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// eliminate unwanted infos
		for(int i=0 ; i<7 ; i++)
		{
			for(int j=1 ; j<ways ; j++)
			{
				for(int l=1 ; l<clusters ; l++)
				{
					sout[j][l].next();
				}
			}
		}
		
		// get the value of each clustering method's cluster's result
		for(int i=1 ; i<clusters ; i++)
		{
			co[i] = Integer.parseInt(sout[1][i].next());
			//cc[i] = Integer.parseInt(sout[2][i].next());
			b4[i] = Integer.parseInt(sout[2][i].next());
			or[i] = Integer.parseInt(sout[3][i].next());
		}
		
		// find max cluster with a m as a start of the array name
		findMax(mco,co,clusters);
		//findMax(mcc,cc,clusters);
		findMax(mb4,b4,clusters);
		findMax(mor,or,clusters);
		// find min amoung maxs
		
		//System.out.println("CO = "+mco[0]+"  CC ="+mcc[0]+"  b4 = "+mb4[0]+"  OR ="+mor[0]); 
		
		
		// assign each max ammound to an integer array

		/*
		for(int i=1 ; i<ways ; i++)
		{
			System.out.println("after bubble sort");
			System.out.println(i+" temp "+temp[i]+" index "+index[i]);
			
		}
		*/
		/*
		String str = "linbe";
		
		for(int i=1 ; i<7 ; i++)
		{
			switch(index[i])
			{
				case 1: str = "better1"; break;
				case 2: str = "original"; break;
				case 3: str = "takeTurn"; break;
				case 4: str = "worse better"; break;
				case 5: str = "original plus"; break;
				case 6: str = "better1 w/o"; break;
			}
			System.out.println(str);
			System.out.println(temp[i]);
			
		}
		*/
	
		FileWriter f[] = new FileWriter[ways];
		BufferedWriter o[] = new BufferedWriter[ways];
		
		f[1] = new FileWriter("MaxCO.txt",true);
		//f[2] = new FileWriter("ExactCC.txt",true);
		f[2] = new FileWriter("MaxBC.txt",true);
		f[3] = new FileWriter("MaxOR.txt",true);
		o[1] = new BufferedWriter(f[1]);
		//o[2] = new BufferedWriter(f[2]);
		o[2] = new BufferedWriter(f[2]);
		o[3] = new BufferedWriter(f[3]);
		
		o[1].write("+"+mco[0]);
		//o[2].write("+"+mcc[0]);
		o[2].write("+"+mb4[0]);
		o[3].write("+"+mor[0]);
		
		
		o[1].flush();
		o[1].close();
		//o[2].flush();
		//o[2].close();
		o[2].flush();
		o[2].close();
		o[3].flush();
		o[3].close();
		
	}
}
