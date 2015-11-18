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


	public class MainForClusters {



	
		
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
	    //public NewInTurns newInTurns;
	    //public New newTest;
	    //public BetterCluster better;
	    //public BetterCluster2 better2;
	    //public BetterCluster3 better3;
	    public BetterCluster4 better4;
	    public Circle circle;
	    //public CircleWithCentroid circleWithCentroid;
	    //public CircleDepAndCent cdc;
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
	    //public BruteForce brute;
	    /**
	     *  SA
	     */
	    //public Sa sa;
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
	    public MainForClusters(Data data)
	    {
	        //inturns = new Inturns(data);
	        cluster = new Cluster(data);
	        //other = new Other(data);
	    	//brute = new BruteForce(data);
	    	//sa = new Sa(data);
	    	//better = new BetterCluster(data);
	    	//better2 = new BetterCluster2(data);
	    	//better3 = new BetterCluster3(data);
	    	better4 = new BetterCluster4(data);
	    	circle = new Circle(data);
	    	//circleWithCentroid = new CircleWithCentroid(data);
	    	//cdc = new CircleDepAndCent(data);
	    	//newInTurns = new NewInTurns(data);
	    	//newTest = new New(data);
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
			//data.k = 3;
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
			
			
			long startTime, endTime, deltaTime;
			
		    
		    

			
			MainForClusters  main = new MainForClusters(parser(args));
			
			startTime = System.nanoTime();
			main.cluster.main(main.cluster);
			endTime = System.nanoTime();
			deltaTime = endTime - startTime;
			//deltaTime = deltaTime/1000000000;
			System.out.println("k-means time   == "+deltaTime);
			main.data.dumper("or",Integer.parseInt(args[1]));
			
			
			startTime = System.nanoTime();
			try 
			{
				FileWriter lkhFile = new FileWriter("lkh.bat",false);
				BufferedWriter lkhBuf = new BufferedWriter(lkhFile);
				for(int i=1 ; i<main.data.SALESMAN;i++)
				{
					lkhBuf.write("lkh.exe parafile_or_"+Integer.parseInt(args[1])+"_"+i+".txt");
					lkhBuf.newLine();
				}
				lkhBuf.flush();
				lkhBuf.close();
				String cmd[] = new String[3];
	    		cmd[0] = "cmd";
	    		cmd[1] = "/C";
	    		cmd[2] = "lkh.bat";
	    		Process p = Runtime.getRuntime().exec(cmd);
	    		
	    		try {
					p.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		p.destroy();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			endTime = System.nanoTime();
			
			deltaTime = endTime - startTime;
			//deltaTime = deltaTime/1000000000;
			
			System.out.println("k-means LKH time   == "+deltaTime);
			
			main = new MainForClusters(parser(args));
			startTime = System.nanoTime();
			main.better4.main(main.better4);
			endTime = System.nanoTime();
			deltaTime = endTime - startTime;
			//deltaTime = deltaTime/1000000000;
			main.data.dumper("b4",Integer.parseInt(args[1]));
			System.out.println("mix time   == "+deltaTime);
			
			
			startTime = System.nanoTime();
			try 
			{
				FileWriter lkhFile = new FileWriter("lkh.bat",false);
				BufferedWriter lkhBuf = new BufferedWriter(lkhFile);
				for(int i=1 ; i<main.data.SALESMAN;i++)
				{
					lkhBuf.write("lkh.exe parafile_b4_"+Integer.parseInt(args[1])+"_"+i+".txt");
					lkhBuf.newLine();
				}
				lkhBuf.flush();
				lkhBuf.close();
				String cmd[] = new String[3];
	    		cmd[0] = "cmd";
	    		cmd[1] = "/C";
	    		cmd[2] = "lkh.bat";
	    		Process p = Runtime.getRuntime().exec(cmd);
	    		
	    		try {
					p.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		p.destroy();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			endTime = System.nanoTime();
			deltaTime = endTime - startTime;
			//deltaTime = deltaTime/1000000000;
			
			System.out.println("mix LKH time   == "+deltaTime);
			
			main = new MainForClusters(parser(args));
			startTime = System.nanoTime();
			main.circle.main(main.circle);
			endTime = System.nanoTime();
			deltaTime = endTime - startTime;
			//deltaTime = deltaTime/1000000000;
			System.out.println("Circle time   == "+deltaTime);
			
			main.data.dumper("co",Integer.parseInt(args[1]));
			startTime = System.nanoTime();
			try 
			{
				FileWriter lkhFile = new FileWriter("lkh.bat",false);
				BufferedWriter lkhBuf = new BufferedWriter(lkhFile);
				for(int i=1 ; i<main.data.SALESMAN;i++)
				{
					lkhBuf.write("lkh.exe parafile_co_"+Integer.parseInt(args[1])+"_"+i+".txt");
					lkhBuf.newLine();
				}
				lkhBuf.flush();
				lkhBuf.close();
				String cmd[] = new String[3];
	    		cmd[0] = "cmd";
	    		cmd[1] = "/C";
	    		cmd[2] = "lkh.bat";
	    		Process p = Runtime.getRuntime().exec(cmd);
	    		
	    		try {
					p.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		p.destroy();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			endTime = System.nanoTime();
			deltaTime = endTime - startTime;
			//deltaTime = deltaTime/1000000000;
			System.out.println("Circle LKH time   == "+deltaTime);
		}
		
		
	}


