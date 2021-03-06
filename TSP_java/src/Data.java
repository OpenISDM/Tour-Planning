import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
/**
 * This is the Main class for a TSP cluster solver
 * @author William
 * @version 1.0
 */

public class Data {

	/**
	 * howmany cities 
	 */
	public int TOTALNODES;
	
	/**
	 * maximum number of cities 
	 */
	public int Nodebound;
	
	
	
	/**
	 * howmany salesman + 1
	 */
	public int SALESMAN;
	
	/**
	 *  ip result
	 */
	public double ipResult;
	/**
	 *  ip number of edges
	 */
	public int ipEdgeNum [];
	
	/**
	 * nodes x and y coordinates: nodes[citynumber][0], nodes[citynumber][1] 
	 */
	public int nodes[][];
	/**
	 *  lkh path for each salesman
	 */
	public int lkhNodes[][][];
	/**
	 *  Ip edges for each salesman
	 */
	public int ipPath[][][];
	/**
	 * salesman and the cities to be traveled sales[whichSalesMan][cities to-be-visit] 
	 */
	public int sales[][];
	/**
	 * the path every sales man should take path[whichSalesMan] [the route]  
	 */
	public int path[][];
	/**
	 * initial centroids for clutering methods 
	 */
	public int centroids[][];
	/**
	 * keeps track of howmany nodes for a salesman to travel 
	 */
	public int indexs[]; 
	/**
	 * keeps track of howmany nodes for a salesman to travel 
	 */
	public int probIndex[]; 
	/**
	 * keeps track of howmany iterations done in clustering
	 */
	public int iterations;
	/**
	 * initial depot for all salesmen 
	 */
	public int[][] depot;
	/**
	 * keep track on which nodes the salesman chosen
	 */
	public boolean chosen[][];
	 /**
     *  k means howmany times a node needs to be traveled
     */
    public int k;
    /**
     * zoom : how many times bigger to zoom in the frame
     */
    public int zoom;
	/**
	 * stores the result of all salesman
	 */
    public double results[];
    /**
     * determining how many copies are left
     */
    public int copies;
    
    /**
     *  each salesman's list 
     */
    public int list [][];
    
    /**
     *  distance Matrix
     */
    public double distMatrix[][];
    
    /**
     *  cost Matrix
     */
    public double costMatrix[][];
    // determining if the problem is euclidean or not
    public boolean gx;
    
    
    /**
     *  temper,times,iter[]
     *  is for simulated annealing
     */
    public int temper;
    public int times;
    public int iter[];
    
    public int dValue[];
    
    
    
    /**
	 * Main constructor
	 * initializing some variables
	 * adding six nodes at the end of public nodes[][]
	 *  @param total : stands for TOTALNODES
	 *  @param sales : stands for SALESMAN
	 */
	public Data(int total,int sales)
	{
		TOTALNODES = total;
		SALESMAN = sales;
		
		this.nodes = new int [TOTALNODES+SALESMAN][3];
		lkhNodes = new int[SALESMAN][TOTALNODES*2][2];
		ipPath = new int[SALESMAN][TOTALNODES*2][2];
		ipEdgeNum = new int[SALESMAN];
		this.sales = new int [SALESMAN][TOTALNODES+1];
		path = new int[SALESMAN][TOTALNODES*2];
		centroids = new int [SALESMAN][2];
		indexs = new int[SALESMAN];      
		results = new double[SALESMAN];
		chosen = new boolean[SALESMAN][TOTALNODES + 1];
		depot = new int[SALESMAN][2];
		list = new int[SALESMAN][TOTALNODES + 1];
		iter = new int[SALESMAN];
		dValue = new int[SALESMAN];
		distMatrix = new double [TOTALNODES+SALESMAN][TOTALNODES+SALESMAN];
		costMatrix = new double [TOTALNODES+SALESMAN][TOTALNODES+SALESMAN];
		gx = false;
		
		iterations = 0;
		//System.out.println(indexs.length);
		for(int i=0;i<SALESMAN;i++)
		{
			indexs[i] = 0;
			for(int j=0 ; j<TOTALNODES + 1;j++)
			{
				this.sales[i][j] = 0;
			}
			
		}
		
	}
    /**
     * 
     *  Cost Matrix GO 
     *  computing a cost matrix of randomness
     * @throws IOException 
     * 
     */
	public void costco() throws IOException
	{
		System.out.println("@costco \n\n\n");
		Random rn = new Random();
		System.out.println();
		for(int i=1 ; i<TOTALNODES+SALESMAN ; i++)
		{
			for(int j=1 ; j<TOTALNODES+SALESMAN ; j++)
			{
				if(Math.abs(rn.nextInt()%3) == 0)
					costMatrix[i][j] = 99999;
				else
				{
					switch (Math.abs(rn.nextInt()%4))
					{
						case 0:
							costMatrix[i][j] = distMatrix[i][j]*0.5;
							break;
						case 1:
							costMatrix[i][j] = distMatrix[i][j]*0.75;
							break;
						case 2:
							costMatrix[i][j] = distMatrix[i][j]*1.25;
							break;
						case 3:
							costMatrix[i][j] = distMatrix[i][j]*1.5;
							break;
						default:
							costMatrix[i][j] = distMatrix[i][j];
							break;
					}
					
				}
			
				
			}
		}
		
		 
		
	}
	
	/**
	 * addnode
	 * add a node to the map
	 * in put the x y coordinates
	 */
	public void addNode (int x, int y, int k)
	{
		// set the coordinates
		this.TOTALNODES++;
		this.nodes[TOTALNODES][0] = x;
		this.nodes[TOTALNODES][1] = y;
		this.nodes[TOTALNODES][2] = k;
		
		// dynamically update the distance matrix
		if(TOTALNODES > 1)
			for(int i = 1 ; i < TOTALNODES ; i++)
				distMatrix[i][TOTALNODES] = distMatrix[TOTALNODES][i] = distGeneral(nodes[i][0],nodes[i][1],x,y);
		
		copies = TOTALNODES*k;
	}
	/**
	 * addSales
	 * add a node to the map
	 * in put the x y coordinates
	 */
	public void addSales (int x, int y)
	{
		// set the coordinates
		this.depot[SALESMAN][0] = x;
		this.depot[SALESMAN][1] = y;
		this.SALESMAN++;
		// dynamicall update the distance matrix
		if (SALESMAN > 2)
		{
			for(int i=nodes.length - depot.length + 1 ; i<nodes.length - depot.length + SALESMAN - 1 ; i++)
			{
				distMatrix[i][nodes.length - depot.length + SALESMAN-1] = distMatrix[nodes.length - depot.length + SALESMAN-1][i] =
				distGeneral(depot[i-nodes.length+depot.length][0],depot[i-nodes.length+depot.length][1],x,y);
				System.out.println("for the salse dist is "+distMatrix[i][nodes.length - depot.length + SALESMAN-1]);
			}
		}
	}
	
	
	/**
	 * Shooter
	 * Shoot out test case info created by mouse
	 * @throws IOException
	 */
	public void shooter(String name) throws IOException
	{
		FileWriter output = new FileWriter(name,false);
		BufferedWriter outBuf = new BufferedWriter(output);
		
		outBuf.write(""+TOTALNODES);
		outBuf.newLine();
		outBuf.write(""+SALESMAN);
		outBuf.newLine();
		outBuf.write(""+zoom);
		outBuf.newLine();
		outBuf.write(""+k);
		outBuf.newLine();
		for(int i=0;i<2;i++)
		{
			for(int j=1;j<SALESMAN;j++)
			{
				outBuf.write(""+depot[j][0]+" "+depot[j][1]);
				outBuf.newLine();
			}
		}
		for(int i=1 ; i<=TOTALNODES ; i++)
		{
			outBuf.write(""+nodes[i][0]+" "+nodes[i][1]);
			outBuf.newLine();
		}
		outBuf.write("EOF");
		outBuf.flush();
		outBuf.close();
	}
	
	/**
	 * GPMaster 2
	 * read gp output without depots
	 * merge the gp partition with depots 
	 * dump out the partitions for LKH
	 * run LKH
	 */
	public void GPMaster(int tNum)throws IOException
	{
		String in = tNum+"gp.txt.part."+(SALESMAN-1);
		System.out.println("GPM input file "+in);
		// map sales num to partition num
		int mapping [] = new int[SALESMAN];
		int count [] = new int[SALESMAN];
		
		File file = new File(in);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// init
		for(int i=0; i<SALESMAN; i++)
		{
			mapping[i] = 0;
			count[i] = 0;
		}
		
		// scan over the redundants 
		
		// store k depots to sales
		// scan the depots
		for(int i=1; i<SALESMAN; i++)
		{
			mapping[i] = sc.nextInt();
		}
		// store the nodes into sales
		for(int i=0 ; i<TOTALNODES ; i++)
		{
			int nInt = sc.nextInt();
			// mapping[nInt] means which sales node i belongs to
			
			System.out.print(i+" "+nInt);
			int whichSales = mapping[nInt];
			System.out.println(" whichSales = "+whichSales);
			sales[whichSales][count[whichSales]] = i;
			count[whichSales]++;
		}
		
		// write k LKH files
		FileWriter[] parafile = new FileWriter[SALESMAN];
    	FileWriter[] probfile = new FileWriter[SALESMAN];
    	BufferedWriter[] outPara = new BufferedWriter[SALESMAN];
    	BufferedWriter[] outProb = new BufferedWriter[SALESMAN];
    	String para = "parafile_"+"GP"+"_"+tNum+"_";
    	String prob = "probfile_"+"GP"+"_"+tNum+"_";
    	String output = "output_"+"GP"+"_"+tNum+"_";
    	
    	//init LKH files
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		
    		parafile[i] = new FileWriter(para+i+".txt",false);
    		probfile[i] = new FileWriter(prob+i+".tsp",false);
    		outPara[i] = new BufferedWriter(parafile[i]);
    		outProb[i] = new BufferedWriter(probfile[i]); 
    	}
    	// writing para file
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		outPara[i].write("PROBLEM_FILE = "+prob+i+".tsp"); 
    		outPara[i].newLine();
    		outPara[i].write("trace_level = "+"0"); 
    		outPara[i].newLine();
    		outPara[i].write("output_tour_file = "+output+i+".txt"); 
    		outPara[i].newLine();
    		outPara[i].flush();
    		outPara[i].close();
    	}
    	
    	// writing prob file
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		outProb[i].write("TYPE: TSP");
    		outProb[i].newLine();
    		outProb[i].write("DIMENSION: "+(count[i]+1));
    		outProb[i].newLine();
    		if(gx == false)
    		{
				outProb[i].write("EDGE_WEIGHT_TYPE : EUC_2D");
		
				outProb[i].newLine();
				outProb[i].write("NODE_COORD_SECTION");
				outProb[i].newLine();
				
				for(int j=0 ; j<count[i] ; j++)
				{
					outProb[i].write((j+1)+" "+nodes[sales[i][j]][0]+" "+nodes[sales[i][j]][1]);
		    		outProb[i].newLine();
				}
				outProb[i].write((count[i]+1)+" "+depot[i][0]+" "+depot[i][1]);
				outProb[i].newLine();
				outProb[i].write("EOF");
				outProb[i].flush();
				outProb[i].close();
			
				if(indexs[i]+1 < 3)
				{
					dumpless("GP",tNum,i);
				}
    		}
    		else
    		{
    			outProb[i].write("EDGE_WEIGHT_TYPE : EXPLICIT");
				outProb[i].newLine();
				outProb[i].write("EDGE_WEIGHT_FORMAT: FULL_MATRIX");
				outProb[i].newLine();
				outProb[i].write("EDGE_WEIGHT_SECTION");
				outProb[i].newLine();
				int j1 , k1;
				for(int j=0 ; j<count[i] ; j++)
				{
					for(int k=0 ; k<count[i]; k++)
					{
						j1 = sales[i][j];
						k1 = sales[i][k];
						outProb[i].write(""+costMatrix[j1][k1]+"   ");
					}
					outProb[i].newLine();
				}
				outProb[i].write("EOF");
				outProb[i].flush();
				outProb[i].close();
    		}
    	}
    	// Run LKH
    	try {
    		
			FileWriter lkhFile = new FileWriter("lkhGP.bat",false);
			BufferedWriter lkhBuf = new BufferedWriter(lkhFile);
			for(int i=1 ; i<SALESMAN;i++)
			{
				System.out.println("JENNIFER   "+SALESMAN);
				lkhBuf.write("lkh.exe parafile_"+"GP"+"_"+tNum+"_"+i+".txt");
				lkhBuf.newLine();
			}
			lkhBuf.flush();
			lkhBuf.close();
			String cmd[] = new String[3];
    		cmd[0] = "cmd";
    		cmd[1] = "/C";
    		cmd[2] = "lkhGP.bat";
    		Process p = Runtime.getRuntime().exec(cmd);
    		
    		try {
				p.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		p.destroy();
    		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
	}
	
	
	
	/**
	 * GPMaster
	 * reads gp output then
	 * dumps out the gp partition for LKH
	 * run LKH
	 */
	public void GPMaster2(int tNum)throws IOException
	{
		
		//initializing
		String in = tNum+"gp.txt.part."+(SALESMAN-1);
		System.out.println("GPM input file "+in);
		// map sales num to partition num
		int mapping [] = new int[SALESMAN];
		int count [] = new int[SALESMAN];
		int partitions[][] = new int[SALESMAN][TOTALNODES];
		
		File file = new File(in);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// init
		for(int i=0; i<SALESMAN; i++)
		{
			mapping[i] = 0;
			count[i] = 0;
		}
		
		
		
		// scan and store nodes to partition array
		for(int i=1; i<=TOTALNODES; i++)
		{
			int whichSales =sc.nextInt();
			partitions[whichSales][count[whichSales]]= i;
			count[whichSales]++;
		}
		
		int avrDist [][] = new int[SALESMAN][SALESMAN];
		int min = 99999999; //magic number
		int choiseToMerge [] = new int[SALESMAN];
		boolean chosen [] = new boolean[SALESMAN];  
		for(int i=1 ; i<SALESMAN ; i++)
		{
			chosen[i] = false;
		}
		// merge partitions with depots
		// merging depot no.i
		
		for(int i=1 ; i<SALESMAN; i++)
		{
			
			// find the closest partition on average
			// scan through all partitions
			for (int j=1 ; j<SALESMAN ; j++)
			{
				// skip ahead if this partition is chosen
				if(chosen[i]==true)
				{
					continue;
				}
				int temp = 0;
				
				// to do!! int connectivity = 0;
				// scan through nodes in partition
				for (int k=0 ; k<count[j] ; k++)
				{
					int currentNode = sales[j][k];
					temp += distMatrix[i][currentNode];
				}
				// compute average distance toward partition j for depot i
				avrDist[i][j] = temp / (count[j]+1);
				if(min > avrDist[i][j])
				{
					min = avrDist[i][j];
					choiseToMerge[i] = j;
				}
			}
			chosen[choiseToMerge[i]] = true;
			// check if the connectivity is great enough
			// to do!! check connectivity for 
			// merge it to sales
			for(int j=0 ; j<count[choiseToMerge[i]] ; j++)
			{
				sales[i][j] = partitions[choiseToMerge[i]][j];
			}
		}
		
		//tbc
		
		// write k LKH files
		FileWriter[] parafile = new FileWriter[SALESMAN];
    	FileWriter[] probfile = new FileWriter[SALESMAN];
    	BufferedWriter[] outPara = new BufferedWriter[SALESMAN];
    	BufferedWriter[] outProb = new BufferedWriter[SALESMAN];
    	String para = "parafile_"+"GP"+"_"+tNum+"_";
    	String prob = "probfile_"+"GP"+"_"+tNum+"_";
    	String output = "output_"+"GP"+"_"+tNum+"_";
    	
    	//init LKH files
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		
    		parafile[i] = new FileWriter(para+i+".txt",false);
    		probfile[i] = new FileWriter(prob+i+".tsp",false);
    		outPara[i] = new BufferedWriter(parafile[i]);
    		outProb[i] = new BufferedWriter(probfile[i]); 
    	}
    	// writing para file
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		outPara[i].write("PROBLEM_FILE = "+prob+i+".tsp"); 
    		outPara[i].newLine();
    		outPara[i].write("trace_level = "+"0"); 
    		outPara[i].newLine();
    		outPara[i].write("output_tour_file = "+output+i+".txt"); 
    		outPara[i].newLine();
    		outPara[i].flush();
    		outPara[i].close();
    	}

    	// writing prob file
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		outProb[i].write("TYPE: TSP");
    		outProb[i].newLine();
    		outProb[i].write("DIMENSION: "+(count[i]+1));
    		outProb[i].newLine();
    		if(gx == false)
    		{
				outProb[i].write("EDGE_WEIGHT_TYPE : EUC_2D");
		
				outProb[i].newLine();
				outProb[i].write("NODE_COORD_SECTION");
				outProb[i].newLine();
				
				for(int j=0 ; j<count[i] ; j++)
				{
					outProb[i].write((j+1)+" "+nodes[sales[i][j]][0]+" "+nodes[sales[i][j]][1]);
		    		outProb[i].newLine();
				}
				outProb[i].write((count[i]+1)+" "+depot[i][0]+" "+depot[i][1]);
				outProb[i].newLine();
				outProb[i].write("EOF");
				outProb[i].flush();
				outProb[i].close();
			
				if(indexs[i]+1 < 3)
				{
					dumpless("GP",tNum,i);
				}
    		}
    		else
    		{
    			outProb[i].write("EDGE_WEIGHT_TYPE : EXPLICIT");
				outProb[i].newLine();
				outProb[i].write("EDGE_WEIGHT_FORMAT: FULL_MATRIX");
				outProb[i].newLine();
				outProb[i].write("EDGE_WEIGHT_SECTION");
				outProb[i].newLine();
				int j1 , k1;
				for(int j=0 ; j<count[i] ; j++)
				{
					for(int k=0 ; k<count[i]; k++)
					{
						j1 = sales[i][j];
						k1 = sales[i][k];
						outProb[i].write(""+costMatrix[j1][k1]+"   ");
					}
					outProb[i].newLine();
				}
				outProb[i].write("EOF");
				outProb[i].flush();
				outProb[i].close();
    		}
    	}
    	// Run LKH
    	try {
    		
			FileWriter lkhFile = new FileWriter("lkhGP.bat",false);
			BufferedWriter lkhBuf = new BufferedWriter(lkhFile);
			for(int i=1 ; i<SALESMAN;i++)
			{
				System.out.println("JENNIFER   "+SALESMAN);
				lkhBuf.write("lkh.exe parafile_"+"GP"+"_"+tNum+"_"+i+".txt");
				lkhBuf.newLine();
			}
			lkhBuf.flush();
			lkhBuf.close();
			String cmd[] = new String[3];
    		cmd[0] = "cmd";
    		cmd[1] = "/C";
    		cmd[2] = "lkhGP.bat";
    		Process p = Runtime.getRuntime().exec(cmd);
    		
    		try {
				p.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		p.destroy();
    		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
	}
	
    /**
     * dumper 
     * dumps out the salesman for LKH
     * @throws IOException 
     */
	public void dumper(String type , int caseNum) throws IOException
    {
    	
    	FileWriter[] parafile = new FileWriter[SALESMAN];
    	FileWriter[] probfile = new FileWriter[SALESMAN];
    	BufferedWriter[] outPara = new BufferedWriter[SALESMAN];
    	BufferedWriter[] outProb = new BufferedWriter[SALESMAN];
    	String para = "parafile_"+type+"_"+caseNum+"_";
    	String prob = "probfile_"+type+"_"+caseNum+"_";
    	String output = "output_"+type+"_"+caseNum+"_";
    	
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		
    		parafile[i] = new FileWriter(para+i+".txt",false);
    		probfile[i] = new FileWriter(prob+i+".tsp",false);
    		outPara[i] = new BufferedWriter(parafile[i]);
    		outProb[i] = new BufferedWriter(probfile[i]); 
    	}
    	
    	// writing para file
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		outPara[i].write("PROBLEM_FILE = "+prob+i+".tsp"); 
    		outPara[i].newLine();
    		outPara[i].write("trace_level = "+"0"); 
    		outPara[i].newLine();
    		outPara[i].write("output_tour_file = "+output+i+".txt"); 
    		outPara[i].newLine();
    		outPara[i].flush();
    		outPara[i].close();
    	}
    	
    	// writing prob file
    	for(int i=1 ; i<SALESMAN ; i++)
    	{
    		outProb[i].write("TYPE: TSP");
    		outProb[i].newLine();
    		outProb[i].write("DIMENSION: "+(indexs[i]+1));
    		outProb[i].newLine();
    		if(gx == false)
    		{
				outProb[i].write("EDGE_WEIGHT_TYPE : EUC_2D");
		
				outProb[i].newLine();
				outProb[i].write("NODE_COORD_SECTION");
				outProb[i].newLine();
				
				for(int j=0 ; j<indexs[i] ; j++)
				{
					outProb[i].write((j+1)+" "+nodes[sales[i][j]][0]+" "+nodes[sales[i][j]][1]);
		    		outProb[i].newLine();
				}
				outProb[i].write((indexs[i]+1)+" "+depot[i][0]+" "+depot[i][1]);
				outProb[i].newLine();
				outProb[i].write("EOF");
				outProb[i].flush();
				outProb[i].close();
			
				if(indexs[i]+1 < 3)
				{
					dumpless(type,caseNum,i);
				}
    		}
    		else
    		{
    			outProb[i].write("EDGE_WEIGHT_TYPE : EXPLICIT");
				outProb[i].newLine();
				outProb[i].write("EDGE_WEIGHT_FORMAT: FULL_MATRIX");
				outProb[i].newLine();
				outProb[i].write("EDGE_WEIGHT_SECTION");
				outProb[i].newLine();
				int j1 , k1;
				for(int j=0 ; j<indexs[i] ; j++)
				{
					for(int k=0 ; k<indexs[i]; k++)
					{
						j1 = sales[i][j];
						k1 = sales[i][k];
						outProb[i].write(""+costMatrix[j1][k1]+"   ");
					}
					outProb[i].newLine();
				}
				outProb[i].write("EOF");
				outProb[i].flush();
				outProb[i].close();
    		}
    	}
    	// run lkh
    	
    	
    	
   
    }
	
	public void dumpless(String type , int caseNum , int salesNum) throws IOException
	{
		String output = "output_"+type+"_"+caseNum+"_"+salesNum+".txt";
		FileWriter file = new FileWriter(output,false);
		BufferedWriter out = new BufferedWriter(file);
		double myDist=0.0;
		
		out.write("linbe ");
		out.write("linbe ");
		out.write("linbe ");
		out.write("linbe ");
		out.write("linbe ");
		out.write("linbe ");
		out.write("linbe ");
		
		//public double distGeneral(int fromX, int fromY , int toX , int toY)
		if(indexs[salesNum]==1)
		{
			myDist = distGeneral(depot[salesNum][0],depot[salesNum][1],nodes[sales[salesNum][0]][0],nodes[sales[salesNum][0]][1]);
			myDist = myDist*2;
			myDist = (int)myDist;
			out.write((int)myDist+"");
		}
		else if(indexs[salesNum]==0)
		{
			out.write(0+"");
		}
		for(int i=0 ; i<19 ; i++)
			out.write(" linbe ");
		out.flush();
		out.close();
	}
	/**
	 * printer
	 * prints out what's inside the salesman
	 *
	 */
	public void printer()
	{
		System.out.println("copies  ==  "+copies);
		System.out.println(" k == "+k);
		System.out.println("first Node (x,y) = ("+nodes[1][0]+" "+nodes[1][1]+")");
		System.out.println("last Node (x,y) = ("+nodes[TOTALNODES][0]+" "+nodes[TOTALNODES][1]+")");
		for(int i=1; i<SALESMAN ; i++)
		{
			System.out.printf("\n\n");
			System.out.println("Salesman NO."+i);
			System.out.printf("\n\n%d 0\n",indexs[i]+1);
			for(int j=0; j<indexs[i]; j++)
			{
				if(sales[i][j]==0)
					break;
				System.out.println(nodes[sales[i][j]][0]+" "+nodes[sales[i][j]][1]);
			}
		}
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			System.out.println("result "+i+" = "+results[i]);
		}
	}
	
	
	
	/**
	 *  compute the distance of a node to a depot
	 */
	public int findDist(int nodeNum , int salesNum)
	{
		int xSquare , ySquare;
		
		xSquare = depot[salesNum][0] - nodes[nodeNum][0];
		ySquare = depot[salesNum][1] - nodes[nodeNum][1];
		xSquare *= xSquare;
		ySquare *= ySquare;
		
		return xSquare + ySquare;
	}
	
	
	/**
	 *  sort the list of all Salesman
	 *  
	 *  contruct an array of node ids list for each salesman
	 *  
	 *  
	 */
	public void sortList()
	{
		int temp[] = new int [TOTALNODES + 1];
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			for(int j=1 ; j<TOTALNODES + 1 ; j++)
			{
				list[i][j]=j;
			}
		}
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			for(int j=1 ; j<TOTALNODES + 1 ; j++)
			{
				temp[j] = findDist(j,i);
			}
			quicksort(1,TOTALNODES + 1-1,temp,i);
		}
	}
	
	/**
	 *  sort the list of all Salesman's centroid
	 *  
	 *  for all salesman
	 *  contruct an array
	 *  
	 *  
	 */
	public void sortListCent()
	{
		int temp[] = new int [TOTALNODES + 1];
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			for(int j=1 ; j<TOTALNODES + 1 ; j++)
			{
				list[i][j]=j;
			}
		}
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			for(int j=1 ; j<TOTALNODES + 1 ; j++)
			{
				temp[j] = (int)distCent(i,j);
			}
			
			quicksort(1,TOTALNODES + 1-1,temp,i);
			
		}
	}
	public void quicksort(int low, int high, int numbers[],int salesnum) 
	{
	int i = low, j = high;
	// Get the pivot element from the middle of the list
	int pivot = numbers[low + (high-low)/2];
	
	// Divide into two lists
	while (i <= j) {
	  // If the current value from the left list is smaller then the pivot
	  // element then get the next element from the left list
	  while (numbers[i] < pivot) {
	    i++;
	  }
	  // If the current value from the right list is larger then the pivot
	  // element then get the next element from the right list
	  while (numbers[j] > pivot) {
	    j--;
	  }
	
	  // If we have found a values in the left list which is larger then
	  // the pivot element and if we have found a value in the right list
	  // which is smaller then the pivot element then we exchange the
	  // values.
	  // As we are done we can increase i and j
	  if (i <= j) {
	    exchange(i, j,numbers,salesnum);
	    i++;
	    j--;
	  }
	}
	// Recursion
	if (low < j)
	  quicksort(low, j,numbers,salesnum);
	if (i < high)
	  quicksort(i, high,numbers,salesnum);
	}

	  public void exchange(int i, int j , int numbers[], int salesnum) {
	    int temp = numbers[i];
	    numbers[i] = numbers[j];
	    numbers[j] = temp;
	    
	    temp = list[salesnum][i]; 
	    list[salesnum][i] = list[salesnum][j];
	    list[salesnum][j] = temp;
	  }
	
	
	
	/**
	*findCenter finds the centroids after grouping
	*@return has all the cluster stayed unchanged for this iteration
	* true for yes
	* flase for no
	*/
	public boolean findCenter()
	{
		
		// numOfNodes : used to store howmany nodes in a cluster
		// numFlag : indicates howmany nodes stayed unchanged for this
		//           iteration
		// emptyFlag : indicates howmany cluster has no nodes
		// centerX : sum of a cluster's x-coordinate
		// centerY : sum of a cluster's y-coordinate
		// i , j : indexs
		
	    int i,j,numOfNodes,centerX,centerY,numFlag;
	    int emptyFlag = 0;
	    numFlag = numOfNodes = centerX = centerY = 0;
	    
	    System.out.println("hello");
	    
	    //FOR i=1 to total number of centroids
	    for(i=1;i<SALESMAN;i++)
	    {
	    	//FOR j=0 to total number of nodes
	    	for(j=0;j<indexs[i];j++)
	        {
	            // IF salesman No.i has run out of nodes
	    		if(sales[i][j]==0)
	            {   
	    			// if this cluster is empty
	                if(j==0)
	                    emptyFlag++;
	                break;
	            }
	            else
	            {
	                // calculate centerX and centerY
	            	centerX += nodes[sales[i][j]][0];
	                centerY += nodes[sales[i][j]][1];            
	                numOfNodes++;
	            }
	        }
	    	if(i==4 || i == 3)
	    	{
	    		System.out.println(i);
	    		System.out.println(indexs[i]+" index");
	    	}
	        if(numOfNodes == 0)
	        {
	        	// do nothing
				// this is to prevent having the denominator to be zero
	        }
	        else
	        {
	        	//IF the previous centroids's x coordinate equals centerX/numOfNodes
	            if(centroids[i][0] == centerX / numOfNodes && centroids[i][1] == centerY / numOfNodes )
	            {
	                numFlag++;
	                
	            }
	            
	            centroids[i][0] = centerX / numOfNodes;
	            centroids[i][1] = centerY / numOfNodes;
	        }
	        // initialize local variable
	        numOfNodes = centerX = centerY = 0;
	    }
	    // IF none of the clusters has changed
	    if (numFlag + emptyFlag == SALESMAN-1)
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}
	
	
	/**
	 * conting the distance for int from node to int to node
	 * 
	 * @param from
	 * @param to
	 * @return the distance from node from to node to
	 */
	public double distGeneral(int fromX, int fromY , int toX , int toY)
	{

		double result = 0;
		int xSquare = 0;
		int ySquare = 0;

		xSquare = fromX - toX;
		xSquare = xSquare*xSquare;	
		ySquare = fromY - toY;
		ySquare = ySquare*ySquare;	
		result = Math.sqrt((double)(xSquare + ySquare));
		return result;

	}
	
	// reading the output of IP formulation 
	// ipPath[i][j][2], sales no.i 's no.j edge is  [0] -> [1]
	
	public void ipReader(String input) throws FileNotFoundException
	{
		File in = new File(input);
		Scanner scan = new Scanner(in);
		int amount;
		
		for(int i=1;i<SALESMAN;i++)
		{
			ipEdgeNum[i] = scan.nextInt();
			for(int j=0 ; j<ipEdgeNum[i] ; j++)
			{
				ipPath[i][j][0] = scan.nextInt();
				ipPath[i][j][1] = scan.nextInt();
				//ipPath[i][j][0]++;
				//ipPath[i][j][1]++;
			}
			results[i] = scan.nextDouble();
		}
		ipResult = scan.nextDouble();
		
	}
	
	public void lkhReader(String method, String testCase) throws FileNotFoundException
	{
		// read SALESMAN number of prob files 
		String prob[] = new String[SALESMAN];
		File probFile[] = new File[SALESMAN];
		probIndex = new int[SALESMAN];
		System.out.println("Starting reading lkh...");
		System.out.println("method "+method+" testCase "+testCase);
		// new files
		for(int i=1 ; i<SALESMAN ; i++)
		{
			prob[i] = "probfile_"+method+"_"+testCase+"_"+i+".tsp";
			probFile[i] = new File(prob[i]);
		}
		
		// new scanners
		Scanner probSc[] = new Scanner[SALESMAN];
		for(int i=1 ; i<SALESMAN ; i++)
		{
			probSc[i] = new Scanner(probFile[i]);
		}
		
		// construct lkh nodes by parsing prob files
		for(int i=1 ; i<SALESMAN ;i++)
		{
			System.out.println("parsing prob file No."+i);
			// eliminate useless infos
			for(int j=0 ; j<3 ; j++)
				probSc[i].next();
			
			probIndex[i] = probSc[i].nextInt();
			
			// eliminate useless infos
			for(int j=0 ; j<4 ; j++)
				probSc[i].next();
			
			// parsing nodes for node No.1 to dimension
			for(int j=1 ; j<=probIndex[i] ; j++)
			{
				// eliminate the index number
				probSc[i].next();
				System.out.println("salenum  "+i+"  node no "+j+" SALES = "+SALESMAN+" TOTAL = "+TOTALNODES);
				// parse nodes
				lkhNodes[i][j][0] = probSc[i].nextInt();
				lkhNodes[i][j][1] = probSc[i].nextInt();
			}
			
		}
		System.out.println("Done parsing prob files...");
		// open SALESMAN number of output files
		String output[] = new String[SALESMAN];
		File outFile[] = new File[SALESMAN];
		
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			output[i] = "output_"+method+"_"+testCase+"_"+i+".txt";
			outFile[i] = new File(output[i]);
		}
		
		// new scanners
		Scanner outSc[] = new Scanner[SALESMAN];
		for(int i=1 ; i<SALESMAN ; i++)
		{
			outSc[i] = new Scanner(outFile[i]);
		}
		for(int i=1 ; i<SALESMAN ;i++)
		{
			System.out.println("parsing output file No."+i);
			for(int j=0 ; j<7 ; j++)
				outSc[i].next();
			
			// constuct result by parsing output files
			results[i] = outSc[i].nextInt();
			
			for(int j=0 ; j<19 ; j++)
				outSc[i].next();
			
			// constuct path by parsing output files			
			for(int j=1 ; j<=probIndex[i] ; j++)
			{
				path[i][j] = outSc[i].nextInt();
			}
			
			
		} 
		
	}
	
	
	
	
	
	
	
	/**
	 *  an optimization method done by taking the load off the maximum cluster 
	 *  
	 *  fisrt, find out the maximum cluster
	 *  for every cluster, if it isn't the max cluster
	 *  then take one node in the max cluster that is closest to it
	 *  if it won't become bigger than the original cluster than take it
	 *  else give it back
	 *  
	 *  
	 */
	
	public void loadBalance()
	{
		double max = 0;
		double min = 9999999;
		int whichMin = 0;
		int whichMax = 0;
		int counter = 0;
		int iterations = 0;
		int jTemp;
		BruteForce brute = new BruteForce(this);
		
		
		
		while(counter < SALESMAN - 2 && iterations < 20)
		{
			iterations++;
			counter = 0;
			
			
			for(int i=1 ; i<SALESMAN ; i++)
			{
				
				// Initialize local variable
				max = 0;
				// increase iterations
				iterations++;
				
				// find out which cluster is the maximum
				for(int j=1;j<SALESMAN;j++)
				{
					if(results[j]>max)
					{
						max = results[j];
						whichMax = j;
					}
				}
				
				// skip the max cluster
				if(i == whichMax)
					continue;
				
				// Initialize local variable
				min = 9999999;
				whichMin = 0;
				jTemp = 0;
				
				//find a node in max cluster that is closest to cluster i
				for(int j=0 ; j<indexs[whichMax] ; j++)
				{
					double dist = distCent(i,sales[whichMax][j]);
					
					if(dist < min)
					{
						min = dist; 
						whichMin = sales[whichMax][j];
						jTemp = j;
					}
				}
				// try it, see if the result is good
				insertSales(whichMin,i);
				brute.bruteForce(indexs[i], i, 0);
				
				// if the result is bad than undo
				if(results[i]>max)
				{
					System.out.println("salesman No."+i+" needs "+results[i]);
					sales[i][indexs[i]] = 0;
					indexs[i]--;
					brute.bruteForce(indexs[i], i, 0);
					counter++;
				}
				// else, remove it from the max cluster
				else
				{
					// kill node from max cluster
					for(int j=jTemp ; j<indexs[whichMax] ; j++)
					{
						sales[whichMax][j] = sales[whichMax][j+1];
					}
					indexs[whichMax]--;
					brute.bruteForce(indexs[i], i, 0);
					brute.bruteForce(indexs[whichMax], whichMax , 0);
				}
				findCenter();
			}
		}
		
		
	}
	
	
	public double distLine(int whichSales ,int whichNode)
	{
		double slope = 0;
		double top = 0;
		double bottom = 0;
		
		if (centroids[whichSales][0] - depot[whichSales][0] != 0)
			slope = centroids[whichSales][1] - depot[whichSales][1] / centroids[whichSales][0] - depot[whichSales][0]; 
		else 
		{
			return Math.abs(depot[whichSales][1] - nodes[whichNode][1]);
		}
		
		top = Math.abs(slope*nodes[whichNode][0] - nodes[whichNode][1] - (slope*depot[whichSales][0] + depot[whichSales][1]));
		bottom = Math.sqrt(slope*slope + 1);
		return top/bottom;
		
	}
	
	
	
	/**
	 *  makes minimum larger
	 */
	public void demin()
	{
		int whichNext = 0;
		int whichMin = 0;
		double min = 9999999.9;
		double max = 0;
		BruteForce brute = new BruteForce(this);
		
		// find the min cluster
		// find the closest cluster to min cluster
		// get a node from that cluster to min cluster
		
		for(int i=1 ; i<SALESMAN ; i++)
		{
			if(results[i]<min)
			{
				min = results[i];
				whichMin = i;
			}
		}
		
		min = 9999999.9;
		for(int i=1 ; i<SALESMAN ; i++)
		{
			if(i == whichMin)
				continue;
			
			if(dist(TOTALNODES + 1-1+whichMin,TOTALNODES + 1-1+i)<min)
			{
				min = dist(TOTALNODES + 1-1+whichMin,TOTALNODES + 1-1+i);
				whichNext = i;
			}
		}
		
		// get a node trans from closest
		
		int trans = 0;
		min = 9999999.9;
		for(int i=0 ; i<indexs[whichNext] ; i++)
		{
			if(dist(sales[whichNext][i],TOTALNODES + 1-1+whichMin)<min)
			{
				min = dist(sales[whichNext][i],TOTALNODES + 1-1+whichMin);
				trans = sales[whichNext][i];
			}
		}
		
		System.out.println("before");
		for(int i=0 ; i<indexs[whichNext] ; i++)
		{
			System.out.printf("%d, ",sales[whichNext][i]);
		}
		System.out.println();
		for(int i=trans ; i<indexs[whichNext];i++ )
		{
			sales[whichNext][i] = sales[whichNext][i+1];
		}
		indexs[whichNext]--;
		System.out.println("after");
		for(int i=0 ; i<indexs[whichNext] ; i++)
		{
			System.out.printf("%d, ",sales[whichNext][i]);
		}
		System.out.println();
		insertSales(trans,whichMin);
		findCenter();
		brute.bruteForce(indexs[whichMin], whichMin, 0);
		brute.bruteForce(indexs[whichNext], whichNext, 0);
		
	}
	
	
	/**
	 * 
	 */
	public void demax()
	{
		double max = 0.0;
		double min = 999999999.9;
		double oldMax = 0.0;
		double newMax = 0.0;
		double nodeMax = 0.0;
		int whichMax = 0;
		int nodeTemp=0;
		int centTemp = 0;
		int iTemp = 0;
		BruteForce brute = new BruteForce(this);
		
		//do
		//{
			// find the max cluster
			for(int i=1;i<SALESMAN;i++)
			{
				if(results[i]>max)
				{
					max = results[i];
					whichMax = i;
				}
			}
			oldMax = max;
			
			// find the farthest node in the cluster
			max = 0.0;
			for(int i=0 ; i<indexs[whichMax] ; i++)
			{
				double dist = distCent(whichMax,sales[whichMax][i]);
				
				if(dist > max)
				{
					max = dist;
					nodeTemp=sales[whichMax][i];
					iTemp = i;
				}
			}
			
			// kill node from original cluster
			for(int i=iTemp ; i<indexs[whichMax];i++ )
			{
				sales[whichMax][i] = sales[whichMax][i+1];
			}
			indexs[whichMax]--;
			System.out.printf("after %d %d \n", centroids[whichMax][0],centroids[whichMax][1]);
			
			// find a centroids which is closest to the node
			for(int i=1 ; i<SALESMAN ; i++)
			{
				double dist = distCent(i,nodeTemp);
				if(i==whichMax)
					continue;
				if(dist < min)
				{
					min = dist;
					centTemp=i;
				}
			}
			
			// inseet the node to another cluster
			System.out.println(nodeTemp+"  "+centTemp);
			insertSales(nodeTemp,centTemp);
			
			findCenter();
			//for(int i=1;i<SALESMAN;i++)
	    	//{	
	    		brute.bruteForce(indexs[centTemp], centTemp, 0);
	    		brute.bruteForce(indexs[whichMax], whichMax, 0);
	    	//}
			
			// find the max cluster
			max = 0.0;
			for(int i=1;i<SALESMAN;i++)
			{
				if(results[i]>max)
				{
					max = results[i];
					whichMax = i;
				}
			}
			newMax = max;
		
		//}
		//while(newMax < oldMax);
		
	}
	
	/**
	* insertSales
	* insert an node to the sales array
	*/ 
	public void insertSales(int nodeNum ,int salesNum)
	{

	    sales[salesNum][indexs[salesNum]] = nodeNum;

	    indexs[salesNum]++;
	    return;
	}
	
	
	
	/**
	 * conting the distance for int from node to int to node
	 * 
	 * @param from
	 * @param to
	 * @return the distance from node from to node to
	 */
	public double distCent(int cen , int to)
	{

		double result = 0;
		int xSquare = 0;
		int ySquare = 0;

		xSquare = centroids[cen][0] - nodes[to][0];
		xSquare = xSquare*xSquare;	
		ySquare = centroids[cen][1] - nodes[to][1];
		ySquare = ySquare*ySquare;	

		result = Math.sqrt((double)(xSquare + ySquare));
		return result;
	}
	
	/**
	 * conting the distance for int from node to int to node
	 * 
	 * @param from
	 * @param to
	 * @return the distance from node from to node to
	 */
	public double dist(int from , int to)
	{

		double result = 0;
		int xSquare = 0;
		int ySquare = 0;

		xSquare = nodes[from][0] - nodes[to][0];
		xSquare = xSquare*xSquare;	
		ySquare = nodes[from][1] - nodes[to][1];
		ySquare = ySquare*ySquare;	

		result = Math.sqrt((double)(xSquare + ySquare));
		return result;

	}
	
	/**
	 * main function for Data class
	 * parsing the datas from the input file
	 *  @param args is the input file name
	 */
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		File file = new File(args[0]);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data main = new Data(sc.nextInt(),sc.nextInt());
		main.depot = sc.nextInt();
	    
		for(int i=1 ; i<main.TOTALNODES + 1 + main.SALESMAN - 1 ; i++)
		{
			main.nodes[i][0] = sc.nextInt();
			main.nodes[i][1] = sc.nextInt();
			if(i>=main.TOTALNODES + 1)
			{
				main.centroids[i-main.TOTALNODES + 1+1][0] = main.nodes[i][0];
				main.centroids[i-main.TOTALNODES + 1+1][1] = main.nodes[i][1];
			}
		}
		
		Cluster cluster = new Cluster(main);
		cluster.main(cluster);
	}
	*/
}
