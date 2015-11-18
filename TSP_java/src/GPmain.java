import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class GPmain {

	/**
	 * @param args
	 */
	public int n;
	public int k;
	public int m;
	public int w;
	public int nodes[][];
	public int mat[][];
	public int remat[][];
	 
	 
	public int distGeneral(int fromX, int fromY , int toX , int toY){

		double result = 0;
		int xSquare = 0;
		int ySquare = 0;
		int ret;
		xSquare = fromX - toX;
		xSquare = xSquare*xSquare;	
		ySquare = fromY - toY;
		ySquare = ySquare*ySquare;	
		result = Math.sqrt((double)(xSquare + ySquare));
		ret = (int)Math.round(result);
		return ret;

	}

	// read graph file and run gpmetis
		public void gpGo (int tnum) throws IOException {
			// TODO Auto-generated method stub
			
			String in = tnum+".txt";
			File file = new File(in);
			Scanner sc = null;
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int max;
			
			GPmain gp = new GPmain();
			n = sc.nextInt();
			k = sc.nextInt() - 1;
			// the zoom parameter
			sc.nextInt();
			// the w
			w = sc.nextInt();
			
			// initialize nodes and matrices
			nodes = new int[n+1+k][2];
			mat = new int[n+1+k][n+1+k];
			remat = new int[n+1+k][n+1+k];

			// parse depots
			for(int i=1 ; i<=k ; i++)
			{
				nodes[i][0] = sc.nextInt();
				nodes[i][1] = sc.nextInt();
			}
			// get rid of centroids
			for(int i=1 ; i<=k ; i++)
			{
				sc.nextInt();
				sc.nextInt();
			}
			
			// parse nodes
			for(int i=k+1 ; i<= n+k ; i++)
			{
				nodes[i][0] = sc.nextInt();
				nodes[i][1] = sc.nextInt();
			}
			
			//test nodes
			for(int i=1 ; i<= k+n ; i++)
			{
				System.out.println(i+"  "+nodes[i][0]+" "+nodes[i][1]);
				
			}
			// construct mat
			max = 0;
			
			// zone 1
			// depots cannot go to anthoer depot
			for(int i=1 ; i<=k ; i++)
			{
				for(int j=1 ; j<=k ; j++)
				{
					mat[i][j] = 99999;
				}
			}
			
			// zone 2 finding max
			for(int i=1 ; i<=k ; i++)
			{
				for(int j=k+1 ; j<=k+n ; j++)
				{
					mat[i][j] = distGeneral(nodes[i][0],nodes[i][1],nodes[j][0],nodes[j][1]);
					if(mat[i][j] > max)
						max = mat[i][j];
				}
			}
			
			// zone 3 finding max
			for(int i=k+1 ; i<=k+n ; i++)
			{
				for(int j=1 ; j<=k ; j++)
				{
					mat[i][j] = distGeneral(nodes[i][0],nodes[i][1],nodes[j][0],nodes[j][1]);
					if(mat[i][j] > max)
						max = mat[i][j];
				}
			}
			
			// zone 4 finding max
			for(int i = k+1 ; i<= k+n ; i++)
			{
				for(int j = k+1 ; j <= k+n ; j++)
				{
					if(i==j)
					{
						mat[i][j] = 99999;
					}
					else
					{
						mat[i][j] = distGeneral(nodes[i][0],nodes[i][1],nodes[j][0],nodes[j][1]);
						if(mat[i][j] > max)
							max = mat[i][j];
					}
				}
			}
			
			/*
			//test mat
			for(int i=1 ; i<= k+n ; i++)
			{
				for(int j = k+1 ; j <= k+n ; j++)
				{
					System.out.print(mat[i][j]+" ");
				}
				System.out.println();
			}
			*/
			// construct remat
			
			// zone 1
			
			for(int i=1 ; i<=k ; i++)
			{
				for(int j=1 ; j<=k ; j++)
				{
					remat[i][j] = 1;
				}
			}
			
			// zone 2
			for(int i=1 ; i<=k ; i++)
			{
				for(int j=k+1 ; j<=k+n ; j++)
				{
					remat[i][j] = max + 1 - mat[i][j]; 
					remat[i][j] = remat[i][j]*remat[i][j]*remat[i][j];
				}
			}
			
			// zone 3
			for(int i=k+1 ; i<=k+n ; i++)
			{
				for(int j=1 ; j<=k ; j++)
				{
					remat[i][j] = max + 1 - mat[i][j]; 
					remat[i][j] = remat[i][j]*remat[i][j]*remat[i][j]; 
				}
			}
			
			// zone 4
			for(int i = k+1 ; i<= k+n ; i++)
			{
				for(int j = k+1 ; j <= k+n ; j++)
				{
					if(i==j)
					{
						remat[i][j] = 99999;
					}
					else
					{
						remat[i][j] = max + 1 - mat[i][j]; 
						remat[i][j] = remat[i][j]*remat[i][j]*remat[i][j];
					}
				}
			}
			
			
			
			
			// output GP file
			String gpFileName = tnum+"gp.txt";
			FileWriter output = new FileWriter(gpFileName,false);
			BufferedWriter buf = new BufferedWriter(output);
			buf.write(""+(n+k)+" "+(n+k)*((n+k)-1)/2+" 001");
			buf.newLine();
			
			// first k depots
			for(int i=1 ; i<=k ; i++)
			{	
				// node weight
				//buf.write(""+(n*10)+" ");
				for(int j=1 ; j<=k+n ; j++)
				{
					if(i==j)
						continue;
					// node edgeweight
					buf.write(""+j+" "+remat[i][j]+" ");
				}
				buf.newLine();
			}
			
			
			
			// the rest nodes
			for(int i=k+1 ; i<=k+n ; i++)
			{
				//buf.write("1 ");
				for(int j=1 ; j<=k+n ; j++)
				{
					if(i==j)
						continue;
					// node edgeweight
					buf.write(""+j+" "+remat[i][j]+" ");
					
				}
				buf.newLine();
			}
			buf.flush();
			buf.close();
		
			// run gpmetis
			FileWriter gpFile = new FileWriter("gp.bat",false);
			BufferedWriter gpBuf = new BufferedWriter(gpFile);
			
			gpBuf.write("gpmetis.exe "+gpFileName+" "+k);
			gpBuf.newLine();
			gpBuf.flush();
			gpBuf.close();
			String cmd[] = new String[3];
			cmd[0] = "cmd";
			cmd[1] = "/C";
			cmd[2] = "gp.bat";
			Process p = Runtime.getRuntime().exec(cmd);
			
			try {
				p.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			p.destroy();
			
			
		}
		
	
	// read graph file and run gpmetis
	// run gp without the depots
	public void gpGo2 (int tnum) throws IOException {
		// TODO Auto-generated method stub
		
		String in = tnum+".txt";
		File file = new File(in);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int max;
		
		GPmain gp = new GPmain();
		
		n = sc.nextInt(); // num of nodes
		k = sc.nextInt() - 1; // num of sales
		
		// the zoom parameter redundant
		sc.nextInt();
		// the w
		w = sc.nextInt();
		
		// initialize nodes and matrices
		
		nodes = new int[n+1][2];
		mat = new int[n+1][n+1];
		remat = new int[n+1][n+1];

		// parse depots redundant here in the gpGo2
		for(int i=1 ; i<=k ; i++)
		{
			sc.nextInt();
			sc.nextInt();
		}
		// get rid of centroids
		for(int i=1 ; i<=k ; i++)
		{
			sc.nextInt();
			sc.nextInt();
		}
		
		// parse nodes
		for(int i=1 ; i<= n ; i++)
		{
			nodes[i][0] = sc.nextInt();
			nodes[i][1] = sc.nextInt();
		}
		
		//test nodes
		for(int i=1 ; i<= n ; i++)
		{
			System.out.println(i+"  "+nodes[i][0]+" "+nodes[i][1]);
			
		}
		// construct mat
		max = 0;
		
		
		
		// zone 4 finding max and constructing mat[i][j]
		for(int i = 1 ; i<= n ; i++)
		{
			for(int j = 1 ; j <= n ; j++)
			{
				if(i==j)
				{
					mat[i][j] = 99999;
				}
				else
				{
					mat[i][j] = distGeneral(nodes[i][0],nodes[i][1],nodes[j][0],nodes[j][1]);
					if(mat[i][j] > max)
						max = mat[i][j];
				}
			}
		}
		
		/*
		//test mat
		for(int i=1 ; i<= k+n ; i++)
		{
			for(int j = k+1 ; j <= k+n ; j++)
			{
				System.out.print(mat[i][j]+" ");
			}
			System.out.println();
		}
		*/
		// construct remat
		for(int i = 1 ; i<= n ; i++)
		{
			for(int j = 1 ; j <= n ; j++)
			{
				if(i==j)
				{
					remat[i][j] = 99999;
				}
				else
				{
					remat[i][j] = max + 1 - mat[i][j]; 
					remat[i][j] = remat[i][j]*remat[i][j]*remat[i][j];
				}
			}
		}

		// output GP file
		String gpFileName = tnum+"gpNodes.txt";
		FileWriter output = new FileWriter(gpFileName,false);
		BufferedWriter buf = new BufferedWriter(output);
		buf.write(""+(n)+" "+(n)*((n)-1)/2+" 001");
		buf.newLine();
		

		// the rest nodes
		for(int i=1 ; i<=n ; i++)
		{
			//buf.write("1 ");
			for(int j=1 ; j<=k+n ; j++)
			{
				if(i==j)
					continue;
				// node edgeweight
				buf.write(""+j+" "+remat[i][j]+" ");
				
			}
			buf.newLine();
		}
		buf.flush();
		buf.close();
	
		// run gpmetis
		FileWriter gpFile = new FileWriter("gp.bat",false);
		BufferedWriter gpBuf = new BufferedWriter(gpFile);
		
		gpBuf.write("gpmetis.exe "+gpFileName+" "+k);
		gpBuf.newLine();
		gpBuf.flush();
		gpBuf.close();
		String cmd[] = new String[3];
		cmd[0] = "cmd";
		cmd[1] = "/C";
		cmd[2] = "gp.bat";
		Process p = Runtime.getRuntime().exec(cmd);
		
		try {
			p.waitFor();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		p.destroy();
		
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		File file = new File(args[0]);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int max;
		
		GPmain gp = new GPmain();
		gp.n = sc.nextInt();
		gp.k = sc.nextInt() - 1;
		// the zoom parameter
		sc.nextInt();
		// the w
		gp.w = sc.nextInt();
		
		// initialize nodes and matrices
		gp.nodes = new int[gp.n+1+gp.k][2];
		gp.mat = new int[gp.n+1+gp.k][gp.n+1+gp.k];
		gp.remat = new int[gp.n+1+gp.k][gp.n+1+gp.k];

		// parse depots
		for(int i=1 ; i<=gp.k ; i++)
		{
			gp.nodes[i][0] = sc.nextInt();
			gp.nodes[i][1] = sc.nextInt();
		}
		// get rid of centroids
		for(int i=1 ; i<=gp.k ; i++)
		{
			sc.nextInt();
			sc.nextInt();
		}
		
		// parse nodes
		for(int i=gp.k+1 ; i<= gp.n+gp.k ; i++)
		{
			gp.nodes[i][0] = sc.nextInt();
			gp.nodes[i][1] = sc.nextInt();
		}
		
		//test gp.nodes
		for(int i=1 ; i<= gp.k+gp.n ; i++)
		{
			System.out.println(i+"  "+gp.nodes[i][0]+" "+gp.nodes[i][1]);
			
		}
		// construct mat
		max = 0;
		
		// zone 1
		// depots cannot go to anthoer depot
		for(int i=1 ; i<=gp.k ; i++)
		{
			for(int j=1 ; j<=gp.k ; j++)
			{
				gp.mat[i][j] = 99999;
			}
		}
		
		// zone 2 finding max
		for(int i=1 ; i<=gp.k ; i++)
		{
			for(int j=gp.k+1 ; j<=gp.k+gp.n ; j++)
			{
				gp.mat[i][j] = gp.distGeneral(gp.nodes[i][0],gp.nodes[i][1],gp.nodes[j][0],gp.nodes[j][1]);
				if(gp.mat[i][j] > max)
					max = gp.mat[i][j];
			}
		}
		
		// zone 3 finding max
		for(int i=gp.k+1 ; i<=gp.k+gp.n ; i++)
		{
			for(int j=1 ; j<=gp.k ; j++)
			{
				gp.mat[i][j] = gp.distGeneral(gp.nodes[i][0],gp.nodes[i][1],gp.nodes[j][0],gp.nodes[j][1]);
				if(gp.mat[i][j] > max)
					max = gp.mat[i][j];
			}
		}
		
		// zone 4 finding max
		for(int i = gp.k+1 ; i<= gp.k+gp.n ; i++)
		{
			for(int j = gp.k+1 ; j <= gp.k+gp.n ; j++)
			{
				if(i==j)
				{
					gp.mat[i][j] = 99999;
				}
				else
				{
					gp.mat[i][j] = gp.distGeneral(gp.nodes[i][0],gp.nodes[i][1],gp.nodes[j][0],gp.nodes[j][1]);
					if(gp.mat[i][j] > max)
						max = gp.mat[i][j];
				}
			}
		}
		
		/*
		//test gp.mat
		for(int i=1 ; i<= gp.k+gp.n ; i++)
		{
			for(int j = gp.k+1 ; j <= gp.k+gp.n ; j++)
			{
				System.out.print(gp.mat[i][j]+" ");
			}
			System.out.println();
		}
		*/
		// construct remat
		
		// zone 1
		
		for(int i=1 ; i<=gp.k ; i++)
		{
			for(int j=1 ; j<=gp.k ; j++)
			{
				gp.remat[i][j] = 1;
			}
		}
		
		// zone 2
		for(int i=1 ; i<=gp.k ; i++)
		{
			for(int j=gp.k+1 ; j<=gp.k+gp.n ; j++)
			{
				gp.remat[i][j] = max + 1 - gp.mat[i][j]; 
				gp.remat[i][j] = gp.remat[i][j]*gp.remat[i][j];
			}
		}
		
		// zone 3
		for(int i=gp.k+1 ; i<=gp.k+gp.n ; i++)
		{
			for(int j=1 ; j<=gp.k ; j++)
			{
				gp.remat[i][j] = max + 1 - gp.mat[i][j]; 
				gp.remat[i][j] = gp.remat[i][j]*gp.remat[i][j]; 
			}
		}
		
		// zone 4
		for(int i = gp.k+1 ; i<= gp.k+gp.n ; i++)
		{
			for(int j = gp.k+1 ; j <= gp.k+gp.n ; j++)
			{
				if(i==j)
				{
					gp.remat[i][j] = 99999;
				}
				else
				{
					gp.remat[i][j] = max + 1 - gp.mat[i][j]; 
					gp.remat[i][j] = gp.remat[i][j]*gp.remat[i][j];
				}
			}
		}
		
		
		
		
		// output GP file
		
		FileWriter output = new FileWriter("gp.txt",false);
		BufferedWriter buf = new BufferedWriter(output);
		buf.write(""+(gp.n+gp.k)+" "+(gp.n+gp.k)*((gp.n+gp.k)-1)/2+" 011");
		buf.newLine();
		
		// first k depots
		for(int i=1 ; i<=gp.k ; i++)
		{	
			// node weight
			buf.write(""+(gp.n*10)+" ");
			//buf.write("1 ");
			for(int j=1 ; j<=gp.k+gp.n ; j++)
			{
				if(i==j)
					continue;
				// node edgeweight
				buf.write(""+j+" "+gp.remat[i][j]+" ");
			}
			buf.newLine();
		}
		
		
		
		// the rest nodes
		for(int i=gp.k+1 ; i<=gp.k+gp.n ; i++)
		{
			buf.write("1 ");
			for(int j=1 ; j<=gp.k+gp.n ; j++)
			{
				if(i==j)
					continue;
				// node edgeweight
				buf.write(""+j+" "+gp.remat[i][j]+" ");
				
			}
			buf.newLine();
		}
		buf.flush();
		buf.close();
		
	}

}
