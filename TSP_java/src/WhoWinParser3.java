import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


//command line arguments
//WhoWinParser3 "testcaseNumber" , "howmany ways to parse" , "number of Clusters"

public class WhoWinParser3 {

	
	
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
		int cc[] = new int[clusters];
		int b4[] = new int[clusters];
		int or[] = new int[clusters];
		int cd[] = new int[clusters];
		
		// max cluster  [0] = the amount   [1] = whichmax
		int mco[] = new int[2];
		int mcc[] = new int[2];
		int mb4[] = new int[2];
		int mor[] = new int[2];
		int mcd[] = new int[2];
		
		int win[] = new int[6];
		
		out[1] = "output_co_"+args[0]+"_";
		out[2] = "output_cc_"+args[0]+"_";
		out[3] = "output_b4_"+args[0]+"_";
		out[4] = "output_or_"+args[0]+"_";
		out[5] = "output_cd_"+args[0]+"_";	
		
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
			cc[i] = Integer.parseInt(sout[2][i].next());
			b4[i] = Integer.parseInt(sout[3][i].next());
			or[i] = Integer.parseInt(sout[4][i].next());
			cd[i] = Integer.parseInt(sout[5][i].next());
		}
		
		// find max cluster with a m as a start of the array name
		findMax(mco,co,clusters);
		findMax(mcc,cc,clusters);
		findMax(mb4,b4,clusters);
		findMax(mor,or,clusters);
		findMax(mcd,cd,clusters);
		// find min amoung maxs
		
		//System.out.println("CO = "+mco[0]+"  CC ="+mcc[0]+"  b4 = "+mb4[0]+"  OR ="+mor[0]); 
		
		
		// assign each max ammound to an integer array
		int temp [] = new int [ways];
		int index[] = new int [ways];
		temp[1] = mco[0];
		temp[2] = mcc[0];
		temp[3] = mb4[0];
		temp[4] = mor[0];
		temp[5] = mcd[0];
		
		// sort the temp array containing each max amount
		// sort also the index number so we know which method has the smaller max-cluster
		bubble_srt(temp,index,ways);
		
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
		
		f[1] = new FileWriter("aWhichCO.txt",true);
		f[2] = new FileWriter("aWhichCC.txt",true);
		f[3] = new FileWriter("aWhichBC.txt",true);
		f[4] = new FileWriter("aWhichOR.txt",true);
		f[5] = new FileWriter("aWhichCD.txt",true);
		o[1] = new BufferedWriter(f[1]);
		o[2] = new BufferedWriter(f[2]);
		o[3] = new BufferedWriter(f[3]);
		o[4] = new BufferedWriter(f[4]);
		o[5] = new BufferedWriter(f[5]);
		
		FileWriter tf[] = new FileWriter[ways];
		BufferedWriter to[] = new BufferedWriter[ways];
		
		tf[1] = new FileWriter("aTieCO.txt",true);
		tf[2] = new FileWriter("aTieCC.txt",true);
		tf[3] = new FileWriter("aTieBC.txt",true);
		tf[4] = new FileWriter("aTieOR.txt",true);
		tf[5] = new FileWriter("aTieCD.txt",true);
		to[1] = new BufferedWriter(tf[1]);
		to[2] = new BufferedWriter(tf[2]);
		to[3] = new BufferedWriter(tf[3]);
		to[4] = new BufferedWriter(tf[4]);
		to[5] = new BufferedWriter(tf[5]);
		
		int tieCount = 0;
		
		if(temp[1]==temp[2])
		{
			tieCount++;
			if(temp[2]==temp[3])
			{
				tieCount++;
				if(temp[3]==temp[4])
				{
					tieCount++;
					if(temp[4]==temp[5])
					{
						tieCount++;
					}
				}
			}
		}
		
		
		//System.out.println("tieCount = "+tieCount);
		if(tieCount == 0)
		{
			o[index[1]].write(args[0]+"   ");
		}
		else
		{
			for(int i=1;i<=tieCount+1;i++)
			{
				to[index[i]].write(args[0]+"  ");
			}
		}
		for(int i=1;i<ways;i++)
		{
			o[i].flush();
			o[i].close();
			to[i].flush();
			to[i].close();
		}
		
		
	}

}
