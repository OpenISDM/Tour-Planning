import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class OutputParser {

	
	
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
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		File output[][] = new File[7][7];
		Scanner sout[][] = new Scanner[7][7];
		String out[] = new String[7];
		
		/**
		 * b1 = better cluster
		 * or = original cluster
		 * tt = the stupic take turns
		 * b2 = a worse better cluster with static d value and 
		 * b3 = original cluster with better assignments
		 * b4 = better cluster with easy assignment
		 */
		int b1[] = new int[7];
		int or[] = new int[7];
		int tt[] = new int[7];
		int b2[] = new int[7];
		int b3[] = new int[7];
		int b4[] = new int[7];
		
		// max cluster  [0] = the amount   [1] = whichmax
		int mb1[] = new int[2];
		int mor[] = new int[2];
		int mtt[] = new int[2];
		int mb2[] = new int[2];
		int mb3[] = new int[2];
		int mb4[] = new int[2];
		
		int win[] = new int[6];
		
		out[1] = "output_b1_"+args[0]+"_";
		out[2] = "output_or_"+args[0]+"_";
		out[3] = "output_tt_"+args[0]+"_";
		out[4] = "output_b2_"+args[0]+"_";
		out[5] = "output_b3_"+args[0]+"_";
		out[6] = "output_b4_"+args[0]+"_";
		
		for(int i=1 ; i<7 ; i++)
		{
			for(int j=1 ; j<7 ; j++)
			{
				output[i][j] = new File(out[i]+j+".txt");
			}
		}
		
		try {
			
			for(int i=1 ; i<7 ; i++)
			{
				for(int j=1 ; j<7 ; j++)
				{
					sout[i][j] = new Scanner(output[i][j]);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i=0 ; i<7 ; i++)
		{
			for(int j=1 ; j<7 ; j++)
			{
				for(int l=1 ; l<7 ; l++)
				{
					sout[j][l].next();
				}
			}
		}
		
		
		for(int i=1 ; i<7 ; i++)
		{
			b1[i] = Integer.parseInt(sout[1][i].next());
			or[i] = Integer.parseInt(sout[2][i].next());
			tt[i] = Integer.parseInt(sout[3][i].next());
			b2[i] = Integer.parseInt(sout[4][i].next());
			b3[i] = Integer.parseInt(sout[5][i].next());
			b4[i] = Integer.parseInt(sout[6][i].next());
		}
		
		// find max cluster
		findMax(mb1,b1,7);
		findMax(mor,or,7);
		findMax(mtt,tt,7);
		findMax(mb2,b2,7);
		findMax(mb3,b3,7);
		findMax(mb4,b4,7);
		// find min amoung maxs
		
		int temp [] = new int [7];
		temp[1] = mb1[0];
		temp[2] = mor[0];
		temp[3] = mtt[0];
		temp[4] = mb2[0];
		temp[5] = mb3[0];
		temp[6] = mb4[0];
		findWin(win,temp,7);
		
		// find second min amoung maxs
		findSecond(win,temp,7);
		findThird(win,temp,7);
		String str="linbe";
		String str2 ="lowshu";
		switch(win[0])
		{
			case 1: str = "better1"; break;
			case 2: str = "original"; break;
			case 3: str = "takeTurn"; break;
			case 4: str = "worse better"; break;
			case 5: str = "original plus"; break;
			case 6: str = "better1 w/o"; break;
		}
		
		switch(win[2])
		{
			case 1: str2 = "better1"; break;
			case 2: str2 = "original"; break;
			case 3: str2 = "takeTurn"; break;
			case 4: str2 = "worse better"; break;
			case 5: str2 = "original plus"; break;
			case 6: str2 = "better1 w/o"; break;
		}
		
		
		FileWriter f[] = new FileWriter[7];
		BufferedWriter o[] = new BufferedWriter[7];
		
		f[1] = new FileWriter("scoreBetter1.txt",true);
		f[2] = new FileWriter("scoreOriginal.txt",true);
		f[3] = new FileWriter("scoreTakeTurn.txt",true);
		f[4] = new FileWriter("scoreBetterWithCentroid.txt",true);
		f[5] = new FileWriter("scoreOriginalWithAssignment.txt",true);
		f[6] = new FileWriter("scoreBetter1WithNoAssignment.txt",true);
		o[1] = new BufferedWriter(f[1]);
		o[2] = new BufferedWriter(f[2]);
		o[3] = new BufferedWriter(f[3]);
		o[4] = new BufferedWriter(f[4]);
		o[5] = new BufferedWriter(f[5]);
		o[6] = new BufferedWriter(f[6]);
		
		
		o[1].write("+"+mb1[0]);
		o[2].write("+"+mor[0]);
		o[3].write("+"+mtt[0]);
		o[4].write("+"+mb2[0]);
		o[5].write("+"+mb3[0]);
		o[6].write("+"+mb4[0]);
		o[1].flush();
		o[1].close();
		o[2].flush();
		o[2].close();
		o[3].flush();
		o[3].close();
		o[4].flush();
		o[4].close();
		o[5].flush();
		o[5].close();
		o[6].flush();
		o[6].close();
		
	}

}
