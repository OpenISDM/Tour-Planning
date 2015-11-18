import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;


public class Ip {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		
				System.out.println("gulin aaaaa baffgfg");
				int w=2;
				int DEPOT=2;
				int NODES=7;
				int TOTAL=9;
				int L=9,K=1;
				int stCount;
				int f = 999;
				int d = 50;
				int zoom;
				
				
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
				try {
					  stCount = 0;
				      GRBEnv    env   = new GRBEnv("linbe.log");
				      GRBModel  model = new GRBModel(env);

				      // Create variables 
				      GRBVar[][][] x = new GRBVar[TOTAL][TOTAL][DEPOT];
				      GRBVar[][] u = new GRBVar[DEPOT][NODES];
				      
				      for(int i=0 ; i<TOTAL ; i++)
				      {
				    	  for(int j=0;j<TOTAL;j++)
				    	  {
				    		  for(int k=0;k<DEPOT;k++)
				    		  {
				    			  x[i][j][k] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x"+i+j+k);
				    		  }
				    		  
				    	  }
				      }
				      for(int j=0;j<DEPOT;j++)
			    	  {
			    		  for(int k=0;k<NODES;k++)
			    		  {
			    			  u[j][k] = model.addVar(0, L, 0, GRB.INTEGER, "u"+j+k);
			    		  }
			    		  
			    	  }
				      
				      GRBVar z = model.addVar(0, L*9999, 0, GRB.INTEGER, "z");
				      //GRBVar z = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "z");

				      // Integrate new variables

				      model.update();

				      // Set objective: minimize  z

				      GRBLinExpr expr = new GRBLinExpr();
				      expr.addTerm(1.0, z); 
				      model.setObjective(expr, GRB.MINIMIZE);
				      
				      // depot can't go through depots
				      
				      // Add constraint: ST1
				      // For every participant starting from a depot, goes only to one node in V^
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  expr = new GRBLinExpr();
				    	  for(int j=DEPOT ; j<TOTAL;j++)
				    	  {
			    			  expr.addTerm(1.0, x[i][j][i]);
				    	  }
				    	  model.addConstr(expr, GRB.EQUAL, 1.0, ""+stCount);
				    	  stCount++;
				      }
				      
				      // Add constraint: ST2
				      // Only one arc from V^' to D would any participant take in order to return to its depot
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  expr = new GRBLinExpr();
				    	  for(int j=DEPOT ; j<TOTAL;j++)
				    	  {
			    			  expr.addTerm(1.0, x[j][i][i]);
				    	  }
				    	  model.addConstr(expr, GRB.EQUAL, 1.0, ""+stCount);
				    	  stCount++;
				      }
				      
				      // Add constraint: ST3
				      // Participants cannot go to another depot.
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  for(int j=0 ; j<DEPOT ; j++)
				    	  {
				    		  expr = new GRBLinExpr();
				    		  expr.addTerm(1.0, x[j][i][i]);
				    		  expr.addTerm(1.0, x[i][j][i]);
				    		  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
					    	  stCount++;
				    	  }
				      }
				      
				      
				      // Add constraint: ST4
				      // Every node should be visited w times
				      for(int j=DEPOT ; j<TOTAL ; j++)
				      {
				    	  expr = new GRBLinExpr();
				    	  // every depot
				    	  for(int i=0 ; i<DEPOT ; i++)
				    	  {
				    		  expr.addTerm(1.0, x[i][j][i]);
				    		  // every other from 
				    		  for(int k= DEPOT ; k<TOTAL ; k++)
				    		  {
				    			  if(k==j)
				    				  continue;
				    			  expr.addTerm(1.0, x[k][j][i]);
				    		  }
				    	  }
				    	  model.addConstr(expr, GRB.EQUAL, (double)w, ""+stCount);
				    	  stCount++;
				      }
				      
				      
				      
				      // Add Constraint ST5
				      // Every node should be at most 1 times by one participant
				      for(int i=0 ; i<DEPOT ; i++)
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
				    		 
				    		  model.addConstr(expr, GRB.LESS_EQUAL, 1.0, ""+stCount);
					    	  stCount++;
				    	  }
				      }
				      
				      
				      // Add Constraint ST6
				      // route continuity 
				      for(int i=0 ; i<DEPOT ; i++)
						{
							//System.out.println("DEPOT NO."+i);
							for(int j=DEPOT ; j<TOTAL ; j++)
							{
								
								expr = new GRBLinExpr();
					    		expr.addTerm(1.0, x[i][j][i]);
								for(int k=DEPOT ; k<TOTAL ; k++)
								{
									expr.addTerm(1.0, x[k][j][i]);
									expr.addTerm(-1.0, x[j][k][i]);
								}
								expr.addTerm(-1.0, x[j][i][i]);
								model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
						    	stCount++;
								
							}
						}
				      	
			  
				      
				      
				     
				      
				      
				      // Add Constraint St8
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  expr = new GRBLinExpr();
				    	  for(int j=DEPOT ; j<TOTAL ; j++)
				    	  {
				    		  expr.addTerm((double)cost[i][j], x[i][j][i]);
				    		  expr.addTerm((double)cost[j][i], x[j][i][i]);
				    		  for(int k=DEPOT ; k<TOTAL ; k++)
				    		  {
				    			  //if(k==j)
				    				//  continue;
				    			  expr.addTerm((double)cost[k][j], x[k][j][i]);
				    		  }
				    	  }
				    	  expr.addTerm(-1.0, z);
				    	  model.addConstr(expr, GRB.LESS_EQUAL, 0.0, ""+stCount);
				    	  stCount++;
				      }
				      
				      // Add Constrain St9
				      
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  
				    	  for(int j=DEPOT ; j<TOTAL ; j++)
				    	  {
				    	  	  expr = new GRBLinExpr();
				    		  expr.addTerm(1.0, u[i][j-DEPOT]);
				    		  expr.addTerm((double)(L-2), x[i][j][i]);
				    		  expr.addTerm(-1.0, x[j][i][i]);
				    	  	  model.addConstr(expr, GRB.LESS_EQUAL, (double)(L-1), ""+stCount);
				    	  	  stCount++;
				    	  }
				    	  
				      }
				      
				      // Add Constraint St10
				      
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  
				    	  for(int j=DEPOT ; j<TOTAL ; j++)
				    	  {
				    	  	  expr = new GRBLinExpr();
				    		  expr.addTerm(1.0, u[i][j-DEPOT]);
				    		  expr.addTerm(1.0, x[i][j][i]);
				    		  expr.addTerm((double)(2-K), x[j][i][i]);
				    	  	  model.addConstr(expr, GRB.GREATER_EQUAL, (double)2, ""+stCount);
				    	  	  stCount++;
				    	  }
				    	  
				      }
				      
				      // Add Constraint St11
				      
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  for(int j = DEPOT ; j<TOTAL ; j++)
				    	  {
				    		  for(int k=DEPOT ; k<TOTAL ; k++)
				    		  {
				    			  if(j==k)
				    				  continue;
				    			  expr = new GRBLinExpr();
				    			  expr.addTerm(1.0, u[i][k-DEPOT]);
				    			  expr.addTerm(-1.0, u[i][j-DEPOT]);
				    			  expr.addTerm((double)L, x[k][j][i]);
				    			  expr.addTerm((double)(L-2), x[j][k][i]);
				    			  model.addConstr(expr, GRB.LESS_EQUAL, (double)(L-1), ""+stCount);
						    	  stCount++;
				    		  }
				    	  }
				      }
				      
				      // Add Constraints 12
				      for(int k=0 ; k<DEPOT ; k++)
				      {
				    	  expr = new GRBLinExpr();
				    	  for(int i=0 ; i<TOTAL ; i++)
				    	  {
				    		  for(int j=0 ; j<DEPOT ; j++)
				    		  {
				    			  if(j==k)
				    				  continue;
				    			  expr.addTerm(1.0, x[j][i][k]);
				    			  expr.addTerm(1.0, x[i][j][k]);
				    		  }
				    	  }
				    	  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
				    	  stCount++;
				      }
				      
				      
				      // Optimize model

				      model.optimize();

				      /*
				      System.out.println(x.get(GRB.StringAttr.VarName)
				                         + " " +x.get(GRB.DoubleAttr.X));
				      System.out.println(y.get(GRB.StringAttr.VarName)
				                         + " " +y.get(GRB.DoubleAttr.X));
				      System.out.println(z.get(GRB.StringAttr.VarName)
				                         + " " +z.get(GRB.DoubleAttr.X));
					  */
				      
				      
				      // output file name
				      String name = args[0].substring(0,args[0].length()-4)+"out.txt";
				      FileWriter output = new FileWriter(name,false);
					  BufferedWriter outBuf = new BufferedWriter(output);
					  
				      System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
				      
				      // assign the amount i;
				      int [] amount = new int[DEPOT]; 
				      
				      for(int i=0;i<DEPOT;i++)
				      {
				    	  //System.out.println("Depot "+i+"went through arc ");
				    	  int t=0;
				    	  for(int j=0 ; j<TOTAL ; j++)
				    	  {
				    		  for(int k=0 ; k<TOTAL ; k++)
				    		  {
				    			  int temp = (int) x[j][k][i].get(GRB.DoubleAttr.X);
				    			  if(temp == 1)
				    			  {
				    				  t++;
				    			  }
				    		  }
				    	  }
				    	  amount[i]=t;
				    	  //System.out.println();
				    	  //System.out.println("z == "+z.get(GRB.DoubleAttr.X));
				      }
				      
				      // outputing the paths
				      double tempo = 0;
				      for(int i=0;i<DEPOT;i++)
				      {
				    	  //System.out.println("Depot "+i+"went through arc ");
				    	  outBuf.write(""+amount[i]);
				    	  outBuf.newLine();
				    	  int j1=0 , k1=0;
				    	  for(int j=0 ; j<TOTAL ; j++)
				    	  {
				    		  for(int k=0 ; k<TOTAL ; k++)
				    		  {
				    			  int temp = (int) x[j][k][i].get(GRB.DoubleAttr.X);
				    			  if(temp == 1)
				    			  {
				    				  
				    				  if(j<DEPOT)
				    					  j1=j;
				    				  else
				    					  j1=j-DEPOT+1;
				    				  if(k<DEPOT)
				    					  k1=k;
				    				  else
				    					  k1=k-DEPOT+1;
				    				  
				    				  
				    				  outBuf.write(j1+" "+k1);
				    				  outBuf.newLine();
				    			  }
				    		  }
				    	  }
				    	  for(int j=DEPOT ; j<TOTAL ; j++)
				    	  {
				    		  tempo += cost[i][j]*x[i][j][i].get(GRB.DoubleAttr.X);
				    		  tempo += cost[j][i]*x[j][i][i].get(GRB.DoubleAttr.X);
				    		  for(int k=DEPOT ; k<TOTAL ; k++)
				    		  {
				    			  tempo += cost[k][j]*x[k][j][i].get(GRB.DoubleAttr.X);
				    		  }
				    	  }
			    		  outBuf.write(" "+tempo);
						  outBuf.newLine();
				    	  //System.out.println();
				    	  //System.out.println("z == "+z.get(GRB.DoubleAttr.X));
				      }
				      System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
				      System.out.println("depot = "+DEPOT);
				      outBuf.write("" + model.get(GRB.DoubleAttr.ObjVal));
				      outBuf.newLine();
				      outBuf.write("EOF");
				      outBuf.flush();
				      outBuf.close();
				      

			    	  for(int i=0 ; i<DEPOT ; i++)
			    	  {
			    		  System.out.println("depot "+i);
			    		  for(int j=0 ; j<TOTAL; j++)
			    		  {
			    			  for(int k=0 ; k<TOTAL ; k++)
			    			  {
			    				  System.out.print(" "+x[j][k][i].get(GRB.DoubleAttr.X));
			    				  
			    			  }
			    			  System.out.println();
			    		  }
			    	  }
			    	  System.out.println();
				      for(int i = 0; i<TOTAL ; i++)
				      {
				    	  for(int j = 0 ; j<TOTAL ; j++)
				    	  {
				    		  System.out.print(" "+cost[i][j]);
				    	  }
				    	  System.out.println();
				      }
				      
				      // checking constraint 8
				      double temp = 0;
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  temp = 0;
				    	  for(int j=DEPOT ; j<TOTAL ; j++)
				    	  {
				    		  temp += cost[i][j]*x[i][j][i].get(GRB.DoubleAttr.X);
				    		  temp += cost[j][i]*x[j][i][i].get(GRB.DoubleAttr.X);
				    		  for(int k=DEPOT ; k<TOTAL ; k++)
				    		  {
				    			  temp += cost[k][j]*x[k][j][i].get(GRB.DoubleAttr.X);
				    		  }
				    	  }
				    	  System.out.println(i+"  cost == "+temp);
				      }
				      
				      //checking constraint 1
				      
				      for(int i=0 ; i<DEPOT ; i++)
				      {
				    	  temp=0;
				    	  expr = new GRBLinExpr();
				    	  for(int j=DEPOT ; j<TOTAL;j++)
				    	  {
			    			  temp += x[i][j][i].get(GRB.DoubleAttr.X);
				    	  }
				    	  System.out.println(i+" st1 = "+temp);
				      }
				      //System.out.println(args[2]);
				      //System.out.println(args[1]);
				      
				      // Dispose of model and environment

				      model.dispose();
				      env.dispose();

				    } catch (GRBException e) {
				      System.out.println("Error code: " + e.getErrorCode() + ". " +
				                         e.getMessage());
				    }
	}

	
	public void IPgogo(String  args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("gulin aaaaa baffgfg");
		int w=2;
		int DEPOT=2;
		int NODES=7;
		int TOTAL=9;
		int L=9,K=1;
		int stCount;
		int f = 999;
		int d = 50;
		int zoom;
		
		
		File file = new File(args);
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
		try {
			  stCount = 0;
		      GRBEnv    env   = new GRBEnv("linbe.log");
		      GRBModel  model = new GRBModel(env);

		      // Create variables 
		      GRBVar[][][] x = new GRBVar[TOTAL][TOTAL][DEPOT];
		      GRBVar[][] u = new GRBVar[DEPOT][NODES];
		      
		      for(int i=0 ; i<TOTAL ; i++)
		      {
		    	  for(int j=0;j<TOTAL;j++)
		    	  {
		    		  for(int k=0;k<DEPOT;k++)
		    		  {
		    			  x[i][j][k] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x"+i+j+k);
		    		  }
		    		  
		    	  }
		      }
		      for(int j=0;j<DEPOT;j++)
	    	  {
	    		  for(int k=0;k<NODES;k++)
	    		  {
	    			  u[j][k] = model.addVar(0, L, 0, GRB.INTEGER, "u"+j+k);
	    		  }
	    		  
	    	  }
		      
		      GRBVar z = model.addVar(0, L*9999, 0, GRB.INTEGER, "z");
		      //GRBVar z = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "z");

		      // Integrate new variables

		      model.update();

		      // Set objective: minimize  z

		      GRBLinExpr expr = new GRBLinExpr();
		      expr.addTerm(1.0, z); 
		      model.setObjective(expr, GRB.MINIMIZE);
		      
		      // depot can't go through depots
		      
		      // Add constraint: ST1
		      // For every participant starting from a depot, goes only to one node in V^
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  expr = new GRBLinExpr();
		    	  for(int j=DEPOT ; j<TOTAL;j++)
		    	  {
	    			  expr.addTerm(1.0, x[i][j][i]);
		    	  }
		    	  model.addConstr(expr, GRB.EQUAL, 1.0, ""+stCount);
		    	  stCount++;
		      }
		      
		      // Add constraint: ST2
		      // Only one arc from V^' to D would any participant take in order to return to its depot
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  expr = new GRBLinExpr();
		    	  for(int j=DEPOT ; j<TOTAL;j++)
		    	  {
	    			  expr.addTerm(1.0, x[j][i][i]);
		    	  }
		    	  model.addConstr(expr, GRB.EQUAL, 1.0, ""+stCount);
		    	  stCount++;
		      }
		      
		      // Add constraint: ST3
		      // Participants cannot go to another depot.
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  for(int j=0 ; j<DEPOT ; j++)
		    	  {
		    		  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, x[j][i][i]);
		    		  expr.addTerm(1.0, x[i][j][i]);
		    		  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
			    	  stCount++;
		    	  }
		      }
		      
		      
		      // Add constraint: ST4
		      // Every node should be visited w times
		      for(int j=DEPOT ; j<TOTAL ; j++)
		      {
		    	  expr = new GRBLinExpr();
		    	  // every depot
		    	  for(int i=0 ; i<DEPOT ; i++)
		    	  {
		    		  expr.addTerm(1.0, x[i][j][i]);
		    		  // every other from 
		    		  for(int k= DEPOT ; k<TOTAL ; k++)
		    		  {
		    			  if(k==j)
		    				  continue;
		    			  expr.addTerm(1.0, x[k][j][i]);
		    		  }
		    	  }
		    	  model.addConstr(expr, GRB.EQUAL, (double)w, ""+stCount);
		    	  stCount++;
		      }
		      
		      
		      
		      // Add Constraint ST5
		      // Every node should be at most 1 times by one participant
		      for(int i=0 ; i<DEPOT ; i++)
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
		    		 
		    		  model.addConstr(expr, GRB.LESS_EQUAL, 1.0, ""+stCount);
			    	  stCount++;
		    	  }
		      }
		      
		      
		      // Add Constraint ST6
		      // route continuity 
		      for(int i=0 ; i<DEPOT ; i++)
				{
					//System.out.println("DEPOT NO."+i);
					for(int j=DEPOT ; j<TOTAL ; j++)
					{
						
						expr = new GRBLinExpr();
			    		expr.addTerm(1.0, x[i][j][i]);
						for(int k=DEPOT ; k<TOTAL ; k++)
						{
							expr.addTerm(1.0, x[k][j][i]);
							expr.addTerm(-1.0, x[j][k][i]);
						}
						expr.addTerm(-1.0, x[j][i][i]);
						model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
				    	stCount++;
						
					}
				}
		      	
	  
		      
		      
		     
		      
		      
		      // Add Constraint St8
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  expr = new GRBLinExpr();
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  expr.addTerm((double)cost[i][j], x[i][j][i]);
		    		  expr.addTerm((double)cost[j][i], x[j][i][i]);
		    		  for(int k=DEPOT ; k<TOTAL ; k++)
		    		  {
		    			  //if(k==j)
		    				//  continue;
		    			  expr.addTerm((double)cost[k][j], x[k][j][i]);
		    		  }
		    	  }
		    	  expr.addTerm(-1.0, z);
		    	  model.addConstr(expr, GRB.LESS_EQUAL, 0.0, ""+stCount);
		    	  stCount++;
		      }
		      
		      // Add Constrain St9
		      
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    	  	  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, u[i][j-DEPOT]);
		    		  expr.addTerm((double)(L-2), x[i][j][i]);
		    		  expr.addTerm(-1.0, x[j][i][i]);
		    	  	  model.addConstr(expr, GRB.LESS_EQUAL, (double)(L-1), ""+stCount);
		    	  	  stCount++;
		    	  }
		    	  
		      }
		      
		      // Add Constraint St10
		      
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    	  	  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, u[i][j-DEPOT]);
		    		  expr.addTerm(1.0, x[i][j][i]);
		    		  expr.addTerm((double)(2-K), x[j][i][i]);
		    	  	  model.addConstr(expr, GRB.GREATER_EQUAL, (double)2, ""+stCount);
		    	  	  stCount++;
		    	  }
		    	  
		      }
		      
		      // Add Constraint St11
		      
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  for(int j = DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  for(int k=DEPOT ; k<TOTAL ; k++)
		    		  {
		    			  if(j==k)
		    				  continue;
		    			  expr = new GRBLinExpr();
		    			  expr.addTerm(1.0, u[i][k-DEPOT]);
		    			  expr.addTerm(-1.0, u[i][j-DEPOT]);
		    			  expr.addTerm((double)L, x[k][j][i]);
		    			  expr.addTerm((double)(L-2), x[j][k][i]);
		    			  model.addConstr(expr, GRB.LESS_EQUAL, (double)(L-1), ""+stCount);
				    	  stCount++;
		    		  }
		    	  }
		      }
		      
		      // Add Constraints 12
		      for(int k=0 ; k<DEPOT ; k++)
		      {
		    	  expr = new GRBLinExpr();
		    	  for(int i=0 ; i<TOTAL ; i++)
		    	  {
		    		  for(int j=0 ; j<DEPOT ; j++)
		    		  {
		    			  if(j==k)
		    				  continue;
		    			  expr.addTerm(1.0, x[j][i][k]);
		    			  expr.addTerm(1.0, x[i][j][k]);
		    		  }
		    	  }
		    	  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
		    	  stCount++;
		      }
		      
		      
		      // Optimize model
		      model.getEnv().set(GRB.DoubleParam.TimeLimit, 120);

		      model.optimize();

		      /*
		      System.out.println(x.get(GRB.StringAttr.VarName)
		                         + " " +x.get(GRB.DoubleAttr.X));
		      System.out.println(y.get(GRB.StringAttr.VarName)
		                         + " " +y.get(GRB.DoubleAttr.X));
		      System.out.println(z.get(GRB.StringAttr.VarName)
		                         + " " +z.get(GRB.DoubleAttr.X));
			  */
		      
		      
		      // output file name
		      String name = args.substring(0,args.length()-4)+"out.txt";
		      FileWriter output = new FileWriter(name,false);
			  BufferedWriter outBuf = new BufferedWriter(output);
			  
		      System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
		      
		      // assign the amount i;
		      int [] amount = new int[DEPOT]; 
		      
		      for(int i=0;i<DEPOT;i++)
		      {
		    	  //System.out.println("Depot "+i+"went through arc ");
		    	  int t=0;
		    	  for(int j=0 ; j<TOTAL ; j++)
		    	  {
		    		  for(int k=0 ; k<TOTAL ; k++)
		    		  {
		    			  int temp = (int) x[j][k][i].get(GRB.DoubleAttr.X);
		    			  if(temp == 1)
		    			  {
		    				  t++;
		    			  }
		    		  }
		    	  }
		    	  amount[i]=t;
		    	  //System.out.println();
		    	  //System.out.println("z == "+z.get(GRB.DoubleAttr.X));
		      }
		      
		      // printing var x
		      
		      for(int i = 0 ; i < DEPOT ; i++)
		      {
		    	  System.out.println("SALES No."+i);System.out.println();System.out.println();
		    	  for(int j=0 ; j<TOTAL ; j++)
		    	  {
		    		  for(int k=0 ; k<TOTAL ; k++)
		    		  {
		    			  System.out.print("  "+x[j][k][i].get(GRB.DoubleAttr.X));
		    		  }
		    		  System.out.println();
		    	  }
		    	  System.out.println();System.out.println();System.out.println();
		      }
		      // printing var u
		      
		      for(int i = 0 ; i<DEPOT ; i++)
		      {
		    	  System.out.println("SALES No."+i);System.out.println();System.out.println();
		    	  
		    	  for(int j=0 ; j<NODES ; j++)
		    	  {
		    		  System.out.print("  "+u[i][j].get(GRB.DoubleAttr.X));
		    	  }
		      }
		      
		      // outputing the paths
		      double tempo = 0;
		      for(int i=0;i<DEPOT;i++)
		      {
		    	  
		    	  // initializing tour values
		    	  tempo=0;
		    	  
		    	  
		    	  //printing out depot i and it's tour
		    	  outBuf.write(""+amount[i]);
		    	  outBuf.newLine();
		    	  // j1 from index
		    	  // k1 to index
		    	  int j1=0 , k1=0;
		    	  for(int j=0 ; j<TOTAL ; j++)
		    	  {
		    		  for(int k=0 ; k<TOTAL ; k++)
		    		  {
		    			  int temp = (int) x[j][k][i].get(GRB.DoubleAttr.X);
		    			  if(temp == 1)
		    			  {
		    				  
		    				  /*
		    				  if(j<DEPOT)
		    					  j1=j;
		    				  else
		    					  j1=j-DEPOT+1;
		    				  if(k<DEPOT)
		    					  k1=k;
		    				  else
		    					  k1=k-DEPOT+1;
		    				  */
		    				  
		    				  outBuf.write(j+" "+k);
		    				  outBuf.newLine();
		    			  }
		    		  }
		    	  }
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  tempo += cost[i][j]*x[i][j][i].get(GRB.DoubleAttr.X);
		    		  tempo += cost[j][i]*x[j][i][i].get(GRB.DoubleAttr.X);
		    		  for(int k=DEPOT ; k<TOTAL ; k++)
		    		  {
		    			  tempo += cost[k][j]*x[k][j][i].get(GRB.DoubleAttr.X);
		    		  }
		    	  }
	    		  outBuf.write(" "+tempo);
				  outBuf.newLine();
		    	  System.out.println();
		    	  System.out.println("z == "+z.get(GRB.DoubleAttr.X));
		      }
		      System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
		      System.out.println("depot = "+DEPOT);
		      outBuf.write("" + model.get(GRB.DoubleAttr.ObjVal));
		      outBuf.newLine();
		      outBuf.write("EOF");
		      outBuf.flush();
		      outBuf.close();
		      

	    	  for(int i=0 ; i<DEPOT ; i++)
	    	  {
	    		  System.out.println("depot "+i);
	    		  for(int j=0 ; j<TOTAL; j++)
	    		  {
	    			  for(int k=0 ; k<TOTAL ; k++)
	    			  {
	    				  System.out.print(" "+x[j][k][i].get(GRB.DoubleAttr.X));
	    				  
	    			  }
	    			  System.out.println();
	    		  }
	    	  }
	    	  System.out.println();
		      for(int i = 0; i<TOTAL ; i++)
		      {
		    	  for(int j = 0 ; j<TOTAL ; j++)
		    	  {
		    		  System.out.print(" "+cost[i][j]);
		    	  }
		    	  System.out.println();
		      }
		      
		      // checking constraint 8
		      double temp = 0;
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  temp = 0;
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  temp += cost[i][j]*x[i][j][i].get(GRB.DoubleAttr.X);
		    		  temp += cost[j][i]*x[j][i][i].get(GRB.DoubleAttr.X);
		    		  for(int k=DEPOT ; k<TOTAL ; k++)
		    		  {
		    			  temp += cost[k][j]*x[k][j][i].get(GRB.DoubleAttr.X);
		    		  }
		    	  }
		    	  System.out.println(i+"  cost == "+temp);
		      }
		      
		      //checking constraint 1
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  temp=0;
		    	  expr = new GRBLinExpr();
		    	  for(int j=DEPOT ; j<TOTAL;j++)
		    	  {
	    			  temp += x[i][j][i].get(GRB.DoubleAttr.X);
		    	  }
		    	  System.out.println(i+" st1 = "+temp);
		      }
		      //System.out.println(args[2]);
		      //System.out.println(args[1]);
		      
		      // Dispose of model and environment

		      model.dispose();
		      env.dispose();

		    } catch (GRBException e) {
		      System.out.println("Error code: " + e.getErrorCode() + ". " +
		                         e.getMessage());
		    }
	}

}

