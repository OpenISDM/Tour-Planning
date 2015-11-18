import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;




public class Gams {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		
				System.out.println("gulin aaaaa baffgfg");
				int w=1;
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
						cost[i][j] = Math.round(Math.sqrt(x1+y)*10000.0)/10000.0;
						
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
						cost[i][j] = Math.round(Math.sqrt(x1+y)*10000.0)/10000.0; 
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
						cost[i][j] = Math.round(Math.sqrt(x1+y)*10000.0)/10000.0; 
					}
				}
				System.out.println("L = "+L+" K = "+K);
				TOTAL = TOTAL+DEPOT;
				NODES = TOTAL-DEPOT;
				
				String name = args[0].substring(0,args[0].length()-4)+".gams";
			    FileWriter output = new FileWriter(name,false);
				BufferedWriter o = new BufferedWriter(output);
				o.write("SET");
				o.newLine();
				o.write("I  first ");
				o.write("/"+1+"*"+TOTAL+"/");
				o.newLine();
				o.write("K(I)  depot1 ");
				o.write("/"+1+"*"+DEPOT+"/");
				o.newLine();
				o.write("O(I)  node1 ");
				o.write("/"+(DEPOT+1)+"*"+TOTAL+"/");
				o.newLine();
				o.write("alias(I,J);");
				o.newLine();
				o.write("alias(K,L);");
				o.newLine();
				o.write("alias(O,P);");
				o.newLine();
				
				
				o.write("TABLE C(I,J)  node to node");
				o.newLine();
				o.write("                          ");
				for(int i=1 ; i<=TOTAL ; i++)
				{
					String temp = ""+i; 
					o.write(""+i);
					for(int j=0 ; j<26-temp.length();j++)
					{
						o.write(" ");
					}
					
				}
				o.newLine();
				
				for(int i=1 ; i<=TOTAL ; i++)
				{
					String temp = ""+i; 
					o.write(""+i);
					for(int j=0 ; j<26-temp.length();j++)
					{
						o.write(" ");
					}
					
					for(int j=1 ; j<=TOTAL ; j++)
					{
						String temp2 = ""+cost[i-1][j-1];
						o.write(temp2);
						for(int l=0 ; l<26-temp2.length();l++)
						{
							o.write(" ");
						}
					}
					o.newLine();
				}
				o.write(";");
				o.newLine();
				
				//SCALARS
				o.write("SCALAR TOTAL "+" /"+TOTAL+"/;");
				o.newLine();
				o.write("SCALAR NODES "+" /"+NODES+"/;");
				o.newLine();
				o.write("SCALAR W "+" /"+w+"/;");
				o.newLine();
				o.write("SCALAR Y "+" /"+TOTAL*w+"/;");
				o.newLine();
				o.write("SCALAR M "+" /"+1+"/;");
				o.newLine();
				
				//VARIABLES
				o.write("BINARY VARIABLE X(I,J,K)  x variable ;");
				o.newLine();
				o.write("INTEGER VARIABLE U(K,O)    u variable ;");
				o.newLine();
				o.write("VARIABLE   Z         objective ;");
				o.newLine();
				
				o.write("EQUATIONS");
				o.newLine();
				//EQUATIONS
				o.write("CON1(K)   constraint 1");
				o.newLine();
				o.write("CON2(K)   constraint 2");
				o.newLine();
				o.write("CON3   constraint 3");
				o.newLine();
				o.write("CON4(O)   constraint 4");
				o.newLine();
				o.write("CON5(O,K)   constraint 5");
				o.newLine();
				o.write("CON6(O,K)   constraint 6");
				o.newLine();
				o.write("CON7(K)   constraint 7");
				o.newLine();
				
				o.write("CON10(O,P,K)   constraint 10");
				o.newLine();
				o.write("CON11(O,K)   constraint 11;");
				o.newLine();
				
				
				o.write("CON1(K).. SUM(O,X(K,O,K)) =E= 1;");
				o.newLine();
				o.write("CON2(K).. SUM(O,X(O,K,K)) =E= 1;");
				o.newLine();
				o.write("CON3..  SUM((K,L),X(K,L,K)) =E= 0;");
				o.newLine();
				o.write("CON4(O) .. SUM(K,X(K,O,K)) + SUM((P,K),X(P,O,K)) =e= W;");
				o.newLine();
				o.write("CON5(O,K) .. X(K,O,K) + SUM(P,X(P,O,K)) =L= 1;");
				o.newLine();
				o.write("CON6(O,K) .. X(K,O,K) + SUM(P,X(P,O,K)) - X(O,K,K) - SUM(P,X(O,P,K)) =e= 0;");
				o.newLine();
				o.write("CON7(K) .. Z =G= SUM(O,C(K,O)*X(K,O,K) + C(O,K)*X(O,K,K)) + SUM((P,O),C(P,O)*X(P,O,K));");
				o.newLine();
				
				o.write("CON10(O,P,K) .. U(K,O) - U(K,P) + (TOTAL-1)*X(O,P,K) + (TOTAL-3)*X(P,O,K) =L= (TOTAL-2);");
				o.newLine();
				o.write("CON11(O,K) .. X(O,O,K) =e= 0;");
				o.newLine();
				
				// remain stuff
				o.write("MODEL TRANSPORT /ALL/;");
				o.newLine();
				o.write("SOLVE TRANSPORT USING MIP MINIMIZING Z ;");
				o.newLine();
				o.flush();
				o.close();
				
	}
	
	public void GamsGo(String  args) throws IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		
				System.out.println("gulin aaaaa baffgfg");
				int w=1;
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
						cost[i][j] = Math.round(Math.sqrt(x1+y)*10000.0)/10000.0;
						
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
						cost[i][j] = Math.round(Math.sqrt(x1+y)*10000.0)/10000.0; 
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
						cost[i][j] = Math.round(Math.sqrt(x1+y)*10000.0)/10000.0; 
					}
				}
				
				
				
				System.out.println("L = "+L+" K = "+K);
				TOTAL = TOTAL+DEPOT;
				NODES = TOTAL-DEPOT;
				
				String name = args.substring(0,args.length()-4)+".gams";
			    FileWriter output = new FileWriter(name,false);
				BufferedWriter o = new BufferedWriter(output);
				o.write("SET");
				o.newLine();
				o.write("I  first ");
				o.write("/"+1+"*"+TOTAL+"/");
				o.newLine();
				o.write("K(I)  depot1 ");
				o.write("/"+1+"*"+DEPOT+"/");
				o.newLine();
				o.write("O(I)  node1 ");
				o.write("/"+(DEPOT+1)+"*"+TOTAL+"/");
				o.newLine();
				o.write("alias(I,J);");
				o.newLine();
				o.write("alias(K,L);");
				o.newLine();
				o.write("alias(O,P);");
				o.newLine();
				
				
				o.write("TABLE C(I,J)  node to node");
				o.newLine();
				o.write("                          ");
				for(int i=1 ; i<=TOTAL ; i++)
				{
					String temp = ""+i; 
					o.write(""+i);
					for(int j=0 ; j<26-temp.length();j++)
					{
						o.write(" ");
					}
					
				}
				o.newLine();
				
				for(int i=1 ; i<=TOTAL ; i++)
				{
					String temp = ""+i; 
					o.write(""+i);
					for(int j=0 ; j<26-temp.length();j++)
					{
						o.write(" ");
					}
					
					for(int j=1 ; j<=TOTAL ; j++)
					{
						String temp2 = ""+cost[i-1][j-1];
						o.write(temp2);
						for(int l=0 ; l<26-temp2.length();l++)
						{
							o.write(" ");
						}
					}
					o.newLine();
				}
				o.write(";");
				o.newLine();
				
				//SCALARS
				o.write("SCALAR TOTAL "+" /"+TOTAL+"/;");
				o.newLine();
				o.write("SCALAR NODES "+" /"+NODES+"/;");
				o.newLine();
				o.write("SCALAR W "+" /"+w+"/;");
				o.newLine();
				o.write("SCALAR Y "+" /"+TOTAL*w+"/;");
				o.newLine();
				o.write("SCALAR M "+" /"+1+"/;");
				o.newLine();
				
				//VARIABLES
				o.write("BINARY VARIABLE X(I,J,K)  x variable ;");
				o.newLine();
				o.write("INTEGER VARIABLE U(K,O)    u variable ;");
				o.newLine();
				o.write("VARIABLE   Z         objective ;");
				o.newLine();
				
				o.write("EQUATIONS");
				o.newLine();
				//EQUATIONS

				o.write("CON1(K)   constraint 1");
				o.newLine();
				o.write("CON2(K)   constraint 2");
				o.newLine();
				o.write("CON3   constraint 3");
				o.newLine();
				o.write("CON4(O)   constraint 4");
				o.newLine();
				o.write("CON5(O,K)   constraint 5");
				o.newLine();
				o.write("CON6(O,K)   constraint 6");
				o.newLine();
				o.write("CON7(K)   constraint 7");
				o.newLine();
				
				o.write("CON10(O,P,K)   constraint 10");
				o.newLine();
				o.write("CON11(O,K)   constraint 11;");
				o.newLine();
				
				
				o.write("CON1(K).. SUM(O,X(K,O,K)) =E= 1;");
				o.newLine();
				o.write("CON2(K).. SUM(O,X(O,K,K)) =E= 1;");
				o.newLine();
				o.write("CON3..  SUM((K,L),X(K,L,K)) =E= 0;");
				o.newLine();
				o.write("CON4(O) .. SUM(K,X(K,O,K)) + SUM((P,K),X(P,O,K)) =e= W;");
				o.newLine();
				o.write("CON5(O,K) .. X(K,O,K) + SUM(P,X(P,O,K)) =L= 1;");
				o.newLine();
				o.write("CON6(O,K) .. X(K,O,K) + SUM(P,X(P,O,K)) - X(O,K,K) - SUM(P,X(O,P,K)) =e= 0;");
				o.newLine();
				o.write("CON7(K) .. Z =G= SUM(O,C(K,O)*X(K,O,K) + C(O,K)*X(O,K,K)) + SUM((P,O),C(P,O)*X(P,O,K));");
				o.newLine();
				
				o.write("CON10(O,P,K) .. U(K,O) - U(K,P) + (TOTAL-1)*X(O,P,K) + (TOTAL-3)*X(P,O,K) =L= (TOTAL-2);");
				o.newLine();
				o.write("CON11(O,K) .. X(O,O,K) =e= 0;");
				o.newLine();
				// remain stuff
				o.write("MODEL TRANSPORT /ALL/;");
				o.newLine();
				o.write("Option Reslim = 28800;");
				o.newLine();
				o.write("SOLVE TRANSPORT USING MIP MINIMIZING Z ;");
				o.newLine();
				o.flush();
				o.close();
				
	}

	
	public void GamsGoForGen(String  args, Data data) throws IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		
				System.out.println("gulin aaaaa baffgfg");
				int w=1;
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
						cost[i][j] = data.costMatrix[i+NODES+1][j-DEPOT+1];
						
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
						cost[i][j] = data.costMatrix[i-DEPOT+1][j+NODES+1];
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
						cost[i][j] = data.costMatrix[i-DEPOT+1][j-DEPOT+1]; 
					}
				}
				
				
				
				System.out.println("L = "+L+" K = "+K);
				TOTAL = TOTAL+DEPOT;
				NODES = TOTAL-DEPOT;
				
				String name = args.substring(0,args.length()-4)+".gams";
			    FileWriter output = new FileWriter(name,false);
				BufferedWriter o = new BufferedWriter(output);
				o.write("SET");
				o.newLine();
				o.write("I  first ");
				o.write("/"+1+"*"+TOTAL+"/");
				o.newLine();
				o.write("K(I)  depot1 ");
				o.write("/"+1+"*"+DEPOT+"/");
				o.newLine();
				o.write("O(I)  node1 ");
				o.write("/"+(DEPOT+1)+"*"+TOTAL+"/");
				o.newLine();
				o.write("alias(I,J);");
				o.newLine();
				o.write("alias(K,L);");
				o.newLine();
				o.write("alias(O,P);");
				o.newLine();
				
				
				o.write("TABLE C(I,J)  node to node");
				o.newLine();
				o.write("                          ");
				for(int i=1 ; i<=TOTAL ; i++)
				{
					String temp = ""+i; 
					o.write(""+i);
					for(int j=0 ; j<26-temp.length();j++)
					{
						o.write(" ");
					}
					
				}
				o.newLine();
				
				for(int i=1 ; i<=TOTAL ; i++)
				{
					String temp = ""+i; 
					o.write(""+i);
					for(int j=0 ; j<26-temp.length();j++)
					{
						o.write(" ");
					}
					
					for(int j=1 ; j<=TOTAL ; j++)
					{
						String temp2 = ""+cost[i-1][j-1];
						o.write(temp2);
						for(int l=0 ; l<26-temp2.length();l++)
						{
							o.write(" ");
						}
					}
					o.newLine();
				}
				o.write(";");
				o.newLine();
				
				//SCALARS
				o.write("SCALAR TOTAL "+" /"+TOTAL+"/;");
				o.newLine();
				o.write("SCALAR NODES "+" /"+NODES+"/;");
				o.newLine();
				o.write("SCALAR W "+" /"+w+"/;");
				o.newLine();
				o.write("SCALAR Y "+" /"+TOTAL*w+"/;");
				o.newLine();
				o.write("SCALAR M "+" /"+1+"/;");
				o.newLine();
				
				//VARIABLES
				o.write("BINARY VARIABLE X(I,J,K)  x variable ;");
				o.newLine();
				o.write("INTEGER VARIABLE U(K,O)    u variable ;");
				o.newLine();
				o.write("VARIABLE   Z         objective ;");
				o.newLine();
				
				o.write("EQUATIONS");
				o.newLine();
				//EQUATIONS

				o.write("CON1(K)   constraint 1");
				o.newLine();
				o.write("CON2(K)   constraint 2");
				o.newLine();
				o.write("CON3   constraint 3");
				o.newLine();
				o.write("CON4(O)   constraint 4");
				o.newLine();
				o.write("CON5(O,K)   constraint 5");
				o.newLine();
				o.write("CON6(O,K)   constraint 6");
				o.newLine();
				o.write("CON7(K)   constraint 7");
				o.newLine();
				
				o.write("CON10(O,P,K)   constraint 10");
				o.newLine();
				o.write("CON11(O,K)   constraint 11;");
				o.newLine();
				
				
				o.write("CON1(K).. SUM(O,X(K,O,K)) =E= 1;");
				o.newLine();
				o.write("CON2(K).. SUM(O,X(O,K,K)) =E= 1;");
				o.newLine();
				o.write("CON3..  SUM((K,L),X(K,L,K)) =E= 0;");
				o.newLine();
				o.write("CON4(O) .. SUM(K,X(K,O,K)) + SUM((P,K),X(P,O,K)) =e= W;");
				o.newLine();
				o.write("CON5(O,K) .. X(K,O,K) + SUM(P,X(P,O,K)) =L= 1;");
				o.newLine();
				o.write("CON6(O,K) .. X(K,O,K) + SUM(P,X(P,O,K)) - X(O,K,K) - SUM(P,X(O,P,K)) =e= 0;");
				o.newLine();
				o.write("CON7(K) .. Z =G= SUM(O,C(K,O)*X(K,O,K) + C(O,K)*X(O,K,K)) + SUM((P,O),C(P,O)*X(P,O,K));");
				o.newLine();
				
				o.write("CON10(O,P,K) .. U(K,O) - U(K,P) + (TOTAL-1)*X(O,P,K) + (TOTAL-3)*X(P,O,K) =L= (TOTAL-2);");
				o.newLine();
				o.write("CON11(O,K) .. X(O,O,K) =e= 0;");
				o.newLine();
				// remain stuff
				o.write("MODEL TRANSPORT /ALL/;");
				o.newLine();
				o.write("Option Reslim = 28800;");
				o.newLine();
				o.write("SOLVE TRANSPORT USING MIP MINIMIZING Z ;");
				o.newLine();
				o.flush();
				o.close();
				
	}

	

	
	
}

