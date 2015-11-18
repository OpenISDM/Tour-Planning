import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Checker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int w=2;
		int DEPOT=2;
		int NODES=7;
		int TOTAL=9;
		int L=9,K=1;
		int stCount;
		int f = 999;
		int d = 50;
		int zoom;
		int [][][] x = new int[TOTAL][TOTAL][DEPOT];
		int [][]xx0 = new int[TOTAL][TOTAL];
		int [][]xx1 = new int[TOTAL][TOTAL];
		
		File file = new File(args[0]);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TOTAL = sc.nextInt();
		L=TOTAL;
		
		DEPOT = sc.nextInt();
		DEPOT--;
		NODES = TOTAL - DEPOT;
		zoom = sc.nextInt();
		w = sc.nextInt();
		double [][] cost = new double[TOTAL+DEPOT][TOTAL+DEPOT];
		int [][] nodes = new int[TOTAL+DEPOT][2];
		
		// parsing depot
		for(int i=0 ; i<DEPOT ; i++)
		{
			nodes[i][0] = sc.nextInt();
			nodes[i][1] = sc.nextInt();
		}
		// parsing depot again
		for(int i=0 ; i<DEPOT ; i++)
		{
			nodes[i][0] = sc.nextInt();
			nodes[i][1] = sc.nextInt();
		}
		// parsing nodes
		for(int i=DEPOT ; i<TOTAL+DEPOT ; i++)
		{
			nodes[i][0] = sc.nextInt();
			nodes[i][1] = sc.nextInt();
		}
		
		// computing cost matrix
		int x1 , y;
		for(int i=0 ; i<DEPOT ; i++)
		{
			for(int j=0 ; j<DEPOT ; j++)
			{
				cost[i][j] = 99999;
			}
		}
		for(int i=0 ; i<DEPOT ; i++)
		{
			for(int j=DEPOT ; j<TOTAL+DEPOT ; j++)
			{
				x1= nodes[i][0]-nodes[j][0];
				y= nodes[i][1]-nodes[j][1];
				x1= x1*x1;
				y = y*y;
				cost[i][j] = Math.sqrt(x1+y); 
			}
		}
		for(int i=DEPOT ; i<TOTAL+DEPOT ; i++)
		{
			for(int j=0 ; j<DEPOT ; j++)
			{
				x1= nodes[i][0]-nodes[j][0];
				y= nodes[i][1]-nodes[j][1];
				x1= x1*x1;
				y = y*y;
				cost[i][j] = Math.sqrt(x1+y); 
			}
		}
		for(int i=DEPOT ; i<TOTAL+DEPOT ; i++)
		{
			for(int j=DEPOT ; j<TOTAL+DEPOT ; j++)
			{
				if(i==j)
				{
					cost[i][j]=99999;
					continue;
				}
				x1= nodes[i][0]-nodes[j][0];
				y= nodes[i][1]-nodes[j][1];
				x1= x1*x1;
				y = y*y;
				cost[i][j] = Math.sqrt(x1+y); 
			}
		}
		System.out.println("L = "+L+" K = "+K);
		TOTAL = TOTAL+DEPOT;
		NODES = TOTAL-DEPOT;
		xx0 = new int[][]{{0,0,1,0,0,0,0},
						 {0,0,0,0,0,0,0},
						 {1,0,0,0,0,0,0},
						 {0,0,0,0,1,0,0},
						 {0,0,0,1,0,0,0},
						 {0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0}};
		
		xx1 = new int[][]{{0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,1},
						 {0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0},
						 {0,1,0,0,0,0,0},
						 {0,0,0,0,0,1,0}};
		
		for(int i=0 ; i<TOTAL ; i++)
		{
			for(int j=0 ; j<TOTAL ; j++)
			{
				x[i][j][0] = xx0[i][j];
				x[i][j][1] = xx1[i][j];
			}
		}
		
		
		
		// checking st 6
		System.out.println("checking constraint 6...");
		for(int i=0 ; i<DEPOT ; i++)
		{
			System.out.println("DEPOT NO."+i);
			for(int j=DEPOT ; j<TOTAL ; j++)
			{
				
				int temp = 0;
				temp += x[i][j][i];
				for(int k=DEPOT ; k<TOTAL ; k++)
				{
					temp += x[k][j][i];
					temp = temp -x[j][k][i];
				}
				temp = temp-x[j][i][i];
				
				if(temp!= 0)
				{
					System.out.println("failled st6 temp = "+temp+" node = "+j+" depot = "+i);
				}
			}
		}
		
		// checking st 1
		System.out.println("checking constraint 1...");
		for(int i=0 ; i<DEPOT ; i++)
		{
			int temp = 0;
			for(int j = DEPOT ; j<TOTAL ; j++)
			{
				temp += x[i][j][i];
				
			}
			if(temp!= 1)
			{
				System.out.println("failled st1  temp = "+temp+" depot "+i);
			}
		}
		
		// checking st 2
		System.out.println("checking constraint 2...");
		for(int i=0 ; i<DEPOT ; i++)
		{
			int temp = 0;
			for(int j = DEPOT ; j<TOTAL ; j++)
			{
				temp += x[j][i][i];
				
			}
			if(temp!= 1)
			{
				System.out.println("failled st2  temp = "+temp+" depot "+i);
			}
		}
		
		//checking st 3
		System.out.println("checking constraint 3...");
		int t3 = 0;
		for(int i=0 ; i<DEPOT ; i++)
		{
			for(int j=0 ; j<DEPOT ; j++)
			{
				t3+=x[i][j][i];
				t3+=x[j][i][j];
			}
		}
		if(t3!= 0)
		{
			System.out.println("failled st3 t3 = "+t3);
		}
		
		//checking st 4
		System.out.println("checking constraint 4...");
		for(int i=DEPOT ; i<TOTAL ; i++)
		{
			int temp = 0;
			for(int j=0 ; j<DEPOT ; j++)
			{
				temp += x[j][i][j];
			}
			for(int j=0 ; j<DEPOT ; j++)
			{
				for(int k=DEPOT ; k<TOTAL ; k++)
				{
					if(k==j)
						continue;
					temp += x[k][i][j];
				}
			}
			if(temp != w)
			{
				System.out.println("failled st4 temp= "+temp+" node no."+i);
			}
		}
		// Add Constrain ST5
	      // Every node should be visited 1 times
	      /*for(int i=0 ; i<DEPOT ; i++)
	      {
	    	  for(int j=DEPOT ; j<TOTAL ; j++)
	    	  {
	    		  expr = new GRBLinExpr();
	    		  expr.addTerm(1.0, x[i][j][i]);
	    		  
	    		  for(int k=DEPOT ; k<TOTAL ; k++)
	    		  {
	    			  if(j==k)
	    				  continue;
	    			  expr.addTerm(1.0, x[k][j][i]);
	    		  }
	    		 
	    		  model.addConstr(expr, GRB.EQUAL, 1.0, ""+stCount);
		    	  stCount++;
	    	  }
	      }
	      */
		//checking st 5
				System.out.println("checking constraint 5...");
		for(int i=0 ; i<DEPOT ; i++)
		{
			int temp;
			for(int j=DEPOT ; j<TOTAL ; j++)
			{
				temp = 0;
				temp += x[i][j][i];
				
				for(int k=DEPOT ; k<TOTAL ; k++)
				{
					if(j==k)
						continue;
					temp += x[k][j][i];
				}
				if(temp>1)
				{
					System.out.println("failled st5 temp= "+temp+" node no."+j+" depot no."+i);
				}
			}
		}
		// check
	
	}
	
	

}
