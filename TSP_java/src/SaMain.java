	import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
	public class SaMain {



	
		
		/**
		 *  data the datastructure set used in the static main function
		 */
	    private static Data dat;
	    /**
	     *  a cluster object used to do clustering
	     */
	    public Cluster cluster;
	    /**
	     *  another object used to do another form of clustering
	     */
	    public Other other;
	    /**
	     *  inturns clustering class with centroids
	     */
	    public Inturns inturns;
	    /**
	     *  inturns clustering class with centroids
	     */
	    public NewInTurns newInTurns;
	    public New newTest;
	    public BetterCluster better;
	    /**
	     * indicating it's in clutering mode
	     */
	    public boolean clus = false;
	    /**
	     *  indicating it's in bruteforce mode
	     */
	    public boolean bru = false;
	    /**
	     * an object used for bruteforce solution
	     */
	    public BruteForce brute;
	    /**
	     *  SA
	     */
	    public Sa sa;
	    /**
	     * public datastructure set
	     */
	    public Data data;
	    /**
	     *  determining which salesman's path are to be solved
	     */
	    public int which;
	    /**
	     *  determine selecting mode
	     */
	    public boolean select = false;
	    /**
	     *  determine which is selected
	     */
	    public int swhich;
	    /**
	     *  int mode : 1 for original 2 for in turns;
	     */
	    public int mode;
	    
	    
	    public int temper;
	    public int times;
		private static BufferedWriter out;
	    /**
	     * Main's constructor : set's the setting of the Frame
	     * @param data
	     */
	    public SaMain(Data data)
	    {
	        inturns = new Inturns(data);
	        cluster = new Cluster(data);
	        other = new Other(data);
	    	brute = new BruteForce(data);
	    	sa = new Sa(data);
	    	better = new BetterCluster(data);
	    	newInTurns = new NewInTurns(data);
	    	newTest = new New(data);
	    	this.data = data;
	    	which = 0;
	    	swhich = 0;
	    	mode = 0;
	    }
	    
	    
	
	    
	    public static Data parser(String[] args)
	    {
	    	
			/**
			 *   Input Format,
			 *   total nodes + 1
			 * 	 num of salesmen +1
			 *	 frame size * zoom
			 *	 k value
			 *	 temper
			 *	 times
			 *	 centroids // num of salesmen of them
			 *	 depots    // num of salesmen of them
			 *	 nodes // starting from node No.1 to total number of nodes
			 */

	    	File file = new File(args[0]);
			Scanner sc = null;
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Data data = new Data(sc.nextInt(),sc.nextInt());
			data.zoom = sc.nextInt();
			data.k = sc.nextInt();
			//data.temper = Integer.parseInt(args[1]);
			//data.times = Integer.parseInt(args[2]);
			
			//data.dValue =Integer.parseInt(args[3]);
			// centroids
			for(int i=1 ; i<data.SALESMAN ; i++)
			{
				data.centroids[i][0] = sc.nextInt();
				data.centroids[i][1] = sc.nextInt();
			}
			
			// depots
			for(int i=1 ; i<data.SALESMAN ; i++)
			{
				data.nodes[data.TOTALNODES+i][0] = data.depot[i][0] = sc.nextInt();
				data.nodes[data.TOTALNODES+i][1] = data.depot[i][1] = sc.nextInt();
			}
			
			// nodes
			for(int i=1 ; i<data.TOTALNODES+1; i++)
			{
				data.nodes[i][0] = sc.nextInt();
				data.nodes[i][1] = sc.nextInt();
				data.nodes[i][2] = data.k;
				
			}
			// distance matrix
			for(int i=1 ; i<data.TOTALNODES+data.SALESMAN ; i++)
			{
				for(int j=1 ; j<data.TOTALNODES+data.SALESMAN ; j++)
				{
						data.distMatrix[i][j] = data.distGeneral(data.nodes[i][0],data.nodes[i][1] , data.nodes[j][0], data.nodes[j][1]);
				}
			}
									
			
			// determining how many node copies there are
			data.copies = (data.TOTALNODES)*data.k;
			return data;
	    }
	    
		/**
		 * main function, parseing the Data
		 * @param args
		 * @throws IOException 
		 */
		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub

			double max = 0;
			double fMax,sMax;
			double min = 9999999;
			double total = 0;
			double delta = 0;
			long time;
			long startTime,endTime;
		    FileWriter fstream = new FileWriter(args[4],true);
		    out = new BufferedWriter(fstream);
				 
			
			SaMain main = new SaMain(parser(args));
			
			
			main.better.main(main.better);
			
			startTime = System.currentTimeMillis();
			for(int i=1 ; i<main.data.SALESMAN ; i++)
			{
				main.sa.sa(i);
			}
			endTime = System.currentTimeMillis();
			
			for(int i=1 ; i<main.data.SALESMAN ; i++)
			{
				if(main.data.results[i]>max)
				{
					max = main.data.results[i];
				}
				if(main.data.results[i]<min)
				{
					min = main.data.results[i];
				}
				total += main.data.results[i];
			}
			delta = max - min;
			time = endTime - startTime;
			sMax = max;
			out.newLine();
			out.write("IN  TURNS!!!!");
			out.newLine();
			out.write("Init Teme = "+main.data.temper+" run times = "+main.data.times);
			out.newLine();out.newLine();
			out.write("max = "+max);
			out.newLine();
			out.write("min = "+min);
			out.newLine();
			out.write("total = "+total);
			out.newLine();
			out.write("time consumed = "+time);
			out.newLine();
			/*
			for(int i=1 ; i<main.data.SALESMAN ; i++)
			{
				out.write("iterations = "+main.data.iter[i]);
				out.newLine();
			}
			*/
			
			main = new SaMain(parser(args));
			main.cluster.main(main.cluster);
			
			startTime = System.currentTimeMillis();
			for(int i=1 ; i<main.data.SALESMAN ; i++)
			{
				main.sa.sa(i);
			}
			endTime = System.currentTimeMillis();
			
			max = 0;
			min = 9999999;
			total = 0;
			delta = 0;
			
			for(int i=1 ; i<main.data.SALESMAN ; i++)
			{
				if(main.data.results[i]>max)
				{
					max = main.data.results[i];
				}
				if(main.data.results[i]<min)
				{
					min = main.data.results[i];
				}
				total += main.data.results[i];
			}
			delta = max - min;
			fMax = max;
			time = endTime - startTime;
			out.newLine();
			out.write("Original!!!!");
			out.newLine();
			out.write("Init Teme = "+main.data.temper+" run times = "+main.data.times);
			out.newLine();out.newLine();
			out.write("max = "+max);
			out.newLine();
			out.write("min = "+min);
			out.newLine();
			out.write("total = "+total);
			out.newLine();
			out.write("time consumed = "+time);
			out.newLine();
			
			/*
			for(int i=1 ; i<main.data.SALESMAN ; i++)
			{
				out.write("iterations = "+main.data.iter[i]);
				out.newLine();
			}
			*/
			
			
			out.flush();
			out.close();
			
			String str;
			if(fMax>sMax)
				str = "win by "+(fMax-sMax);
			else 
				str = "lose by "+(sMax-fMax);
			
			FileWriter stm = new FileWriter(str,true);
		    out = new BufferedWriter(stm);
		    out.newLine();
		    out.flush();
		    out.close();
			
		}
	}


