import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;


public class Gurobibi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("gulin aaaaa ba");
		int w=1;
		int DEPOT=2;
		int NODES=3;
		int TOTAL=5;
		int L=5,K=1;
		int stCount;
		int [][] cost = new int[TOTAL][TOTAL];
		cost = new int[][]{{999,999,12,7,2},{999,999,2,7,12},{12,2,999,3,10},{7,7,3,999,5},{2,12,10,5,999}};
		
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
		      
		      GRBVar z = model.addVar(0, L*DEPOT, 0, GRB.INTEGER, "z");
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
		      
		      // Adding depots can't pass constraint
		      
		      /*
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  for(int j=0;j<DEPOT;j++)
		    	  {
		    		  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, x[i][j][i]);
		    		  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
			    	  stCount++;
			    	  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, x[j][i][i]);
		    		  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
			    	  stCount++;
			    	  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, x[i][i][i]);
		    		  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
			    	  stCount++;
			    	  expr = new GRBLinExpr();
		    		  expr.addTerm(1.0, x[j][j][i]);
		    		  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
			    	  stCount++;
			    	  
			    	  
		    	  }
		    	  
		      }
		      */
		      /*
		      // Add constraint: ST2
		      
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
		      */
		      
		      
		      
		      // Add Constrain St3
		      // every depot
		       
		      
		      /*
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
		    		 
		    		  model.addConstr(expr, GRB.EQUAL, 1.0, ""+stCount);
			    	  stCount++;
		    	  }
		      }
			  */
		      // Add Constrain St4
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  expr = new GRBLinExpr();
		    	  
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  expr.addTerm(1.0, x[i][j][i]);
		    		  expr.addTerm(-1.0, x[j][i][i]);
		    	  }
		    	  model.addConstr(expr, GRB.EQUAL, 0.0, ""+stCount);
		    	  stCount++;
		      }
		      
		      
		      // Add Constrain St5
		      
		      /*
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  expr = new GRBLinExpr();
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  expr.addTerm(1.0, u[i][j-DEPOT]);
		    		  expr.addTerm((double)(L-2), x[i][j][i]);
		    		  expr.addTerm(-1.0, x[j][i][i]);
		    	  }
		    	  model.addConstr(expr, GRB.LESS_EQUAL, (double)(L-1), ""+stCount);
		    	  stCount++;
		      }
		      
		      // Add Constraint St6
		      
		      
		      for(int i=0 ; i<DEPOT ; i++)
		      {
		    	  expr = new GRBLinExpr();
		    	  for(int j=DEPOT ; j<TOTAL ; j++)
		    	  {
		    		  expr.addTerm(1.0, u[i][j-DEPOT]);
		    		  expr.addTerm(1.0, x[i][j][i]);
		    		  expr.addTerm((double)(2-K), x[j][i][i]);
		    	  }
		    	  model.addConstr(expr, GRB.GREATER_EQUAL, (double)2, ""+stCount);
		    	  stCount++;
		      }
		      
		      
		      
		      
		      // Add Constraint St7
		      
		      
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
		      */
		      
		      
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
		      
		      System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
		      for(int i=0;i<DEPOT;i++)
		      {
		    	  System.out.println("Depot "+i+"went through arc ");
		    	  for(int j=0 ; j<TOTAL ; j++)
		    	  {
		    		  for(int k=0 ; k<TOTAL ; k++)
		    		  {
		    			  int temp = (int) x[j][k][i].get(GRB.DoubleAttr.X);
		    			  if(temp == 1)
		    			  {
		    				  System.out.println(j+" "+k);
		    			  }
		    		  }
		    	  }
		    	  System.out.println();
		    	  System.out.println("z == "+z.get(GRB.DoubleAttr.X));
		    	  
		      }

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
		      
		      // Dispose of model and environment

		      model.dispose();
		      env.dispose();

		    } catch (GRBException e) {
		      System.out.println("Error code: " + e.getErrorCode() + ". " +
		                         e.getMessage());
		    }
	}

}

