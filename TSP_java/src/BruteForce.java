

/**
 * The class for brute force methods
 * @author William
 *
 */
public class BruteForce {
	
	/**
	 * A input class that holds the data structure of the TSP problem
	 */
	private Data data;
	
	/**
	 * BruteForce constructor 
	 * @param data link BruteForce to the datastructure
	 * 
	 */
	public BruteForce(Data data)
	{
		this.data = data;
	}
	
	
	/**
	 * decides which bruteForce method should be apply
	 * @param numOfNodes how many nodes
	 * @param whichSales which sales
	 * @param mode  0 single depot 1 multiple depot
	 */
	public void bruteForce(int numOfNodes,int whichSales,int mode)
	{
	    
    	for(int i=0 ; i<data.path[whichSales].length;i++)
    	{
    		data.path[whichSales][i]=0;
    	}
    	
    	data.results[whichSales] = 0;
    	
	    
		switch(numOfNodes)
	    {
			case 1:
				brute1(whichSales,mode);
				break;
			case 2:
				brute2(whichSales,mode);
				break;
			case 3:
				brute3(whichSales,mode);
				break;
			case 4:
				brute4(whichSales,mode);
				break;
			case 5:
				brute5(whichSales,mode);
				break;
			case 6:
				brute6(whichSales,mode);
				break;
			case 7:
				brute7(whichSales,mode);
				break;
			case 8:
				brute8(whichSales,mode);
				break;
			case 9:
				brute9(whichSales,mode);
				break;
			case 10:
				brute10(whichSales,mode);
				break;
			default :
				System.out.println("what are you trying to brute sales No."+whichSales+" numOfNodes "+numOfNodes );
				break;
		}
	    
	}
	
	public double distDepot(int depotNum , int to)
	{
		double result = 0;
		int xSquare = 0;
		int ySquare = 0;

		xSquare = data.depot[depotNum][0] - data.nodes[to][0];
		xSquare = xSquare*xSquare;	
		ySquare = data.depot[depotNum][1] - data.nodes[to][1];
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

		xSquare = data.nodes[from][0] - data.nodes[to][0];
		xSquare = xSquare*xSquare;	
		ySquare = data.nodes[from][1] - data.nodes[to][1];
		ySquare = ySquare*ySquare;	

		result = Math.sqrt((double)(xSquare + ySquare));
		return result;

	}

	/**
	 * compute how far must a salesman travel over 1 node and back
	 * @param whichSales
	 * @param mode
	 */
	void brute1(int whichSales,int mode)
	{
		double result = 0;
		int xSquare = 0;
		int ySquare = 0;
	    
	    if(mode == 0)
	    {
	    	xSquare = data.nodes[data.sales[whichSales][0]][0] - data.depot[whichSales][0];
	    	xSquare = xSquare*xSquare;	
	    	ySquare = data.nodes[data.sales[whichSales][0]][1] - data.depot[whichSales][1];
	    	ySquare = ySquare*ySquare;
	    }
	    else
	    {
	       	xSquare = data.nodes[data.sales[whichSales][0]][0] - data.nodes[data.TOTALNODES+whichSales][0];
	    	xSquare = xSquare*xSquare;	
	    	ySquare = data.nodes[data.sales[whichSales][0]][1] - data.nodes[data.TOTALNODES+whichSales][1];
	    	ySquare = ySquare*ySquare;
	    }
		result = Math.sqrt((double)(xSquare+ySquare))*2;
		
		
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,result);
		data.results[whichSales]=result;
		data.path[whichSales][0]=data.sales[whichSales][0];
		return;
	}
	/**
	 * compute how far must a salesman travel over 2 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute2(int whichSales , int mode)
	{
		double result[] = new double[3];
		int xSquare = 0;
		int ySquare = 0;
		int i;
		int path[] = new int[2];
		result[0] = result [1] = result [2] = 0;
		
		for(i=0;i<2;i++)
		{

			path[i] = data.sales[whichSales][i];
			
			if(mode ==0)
			{
	            xSquare = data.nodes[data.sales[whichSales][i]][0] - data.depot[whichSales][0];
	    		xSquare = xSquare*xSquare;	
	    		ySquare = data.nodes[data.sales[whichSales][i]][1] - data.depot[whichSales][1];
	    		ySquare = ySquare*ySquare;
	    		result[i] = Math.sqrt((double)(xSquare+ySquare));
	        }
	        else
	        {
	            xSquare = data.nodes[data.sales[whichSales][i]][0] - data.nodes[data.TOTALNODES+whichSales][0];
	    		xSquare = xSquare*xSquare;	
	    		ySquare = data.nodes[data.sales[whichSales][i]][1] - data.nodes[data.TOTALNODES+whichSales][1];
	    		ySquare = ySquare*ySquare;
	    		result[i] = Math.sqrt((double)(xSquare+ySquare));
	        }
		}
		xSquare = data.nodes[data.sales[whichSales][0]][0] - data.nodes[data.sales[whichSales][1]][0];
		xSquare = xSquare*xSquare;	
		ySquare = data.nodes[data.sales[whichSales][0]][1] - data.nodes[data.sales[whichSales][1]][1];
		ySquare = ySquare*ySquare;
		result[2] = Math.sqrt((double)(xSquare+ySquare));
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,result[0]+result[1]+result[2]);
		data.results[whichSales]=result[0]+result[1]+result[2];
		for(int index = 0 ; index < path.length ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
		
		return;
	}
	/**
	 * compute how far must a salesman travel over 3 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute3(int whichSales,int mode)
	{

		int i,j,k;
		int salesI = 0, salesJ = 0, salesK = 0;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[3];
		

		

		for(i=0;i<=2;i++)
		{
			for(j=0;j<=2;j++)
			{
				for(k=0;k<=2;k++)
				{
					if(i!=j&&j!=k&&k!=i)
					{
	         		    salesI = data.sales[whichSales][i];
						salesJ = data.sales[whichSales][j];
						salesK = data.sales[whichSales][k];
	                    if(mode == 0)
						{
	                        tempDistance = distDepot(whichSales,data.sales[whichSales][i])+dist(data.sales[whichSales][i],data.sales[whichSales][j])
							+dist(data.sales[whichSales][j],data.sales[whichSales][k])+distDepot(whichSales,data.sales[whichSales][k]);
	                    }
	                    else
	                    {
	                        tempDistance = dist(data.TOTALNODES+whichSales,data.sales[whichSales][i])+dist(data.sales[whichSales][i],data.sales[whichSales][j])
							+dist(data.sales[whichSales][j],data.sales[whichSales][k])+dist(data.sales[whichSales][k],data.TOTALNODES+whichSales);
	                    }
						if(tempDistance < minDistance)
						{
							minDistance = tempDistance;
							path[0] = salesI; path[1] = salesJ; path[2] = salesK;
							
						}
					}
				
				}
			}

		}
		
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
			System.out.printf("The Route %d-%d-%d\n",path[0],path[1],path[2]);
			for(int index = 0 ; index < path.length ; index++)
			{
				data.path[whichSales][index] = path[index];
			}
	}

	/**
	 * compute how far must a salesman travel over 4 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute4(int whichSales,int mode)
	{

		int i,j,k,l;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[4];
		int salesI = 0, salesJ = 0, salesK = 0, salesL = 0;

		

		for(i=0;i<=3;i++)
		{
			for(j=0;j<=3;j++)
			{
				for(k=0;k<=3;k++)
				{
					for(l=0;l<=3;l++)
					{
						if(i!=j&&i!=k&&i!=l&&j!=k&&j!=l&&k!=l)
						{
							
	                        salesI = data.sales[whichSales][i];
							salesJ = data.sales[whichSales][j];
							salesK = data.sales[whichSales][k];
							salesL = data.sales[whichSales][l];
	                        if(mode == 0)
	                        {                    
	                            tempDistance = distDepot(whichSales,data.sales[whichSales][i])+dist(data.sales[whichSales][i],data.sales[whichSales][j])
	    						+dist(data.sales[whichSales][j],data.sales[whichSales][k])+dist(data.sales[whichSales][k],data.sales[whichSales][l])+distDepot(whichSales,data.sales[whichSales][l]);
	                        }
	                        else 
	                        {
	                            tempDistance = dist(data.TOTALNODES+whichSales,data.sales[whichSales][i])+dist(data.sales[whichSales][i],data.sales[whichSales][j])
	    						+dist(data.sales[whichSales][j],data.sales[whichSales][k])+dist(data.sales[whichSales][k],data.sales[whichSales][l])+dist(data.sales[whichSales][l],data.TOTALNODES+whichSales);
	                        }
							if(tempDistance < minDistance)
							{
								minDistance = tempDistance;
								path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL;
								
							}
						}
					}
				}
			}

		}
		
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
		System.out.printf("The Route %d-%d-%d-%d \n",path[0],path[1],path[2],path[3]);
		for(int index = 0 ; index < path.length ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
	}
	/**
	 * compute how far must a salesman travel over 5 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute5 (int whichSales,int mode)
	{
		int i,j,k,l,m;
			double minDistance = 999999999;
			double tempDistance =0;
			int path[] = new int[5];
			int howmany;
			int salesI = 0, salesJ = 0, salesK = 0, salesL = 0, salesM = 0;
		    int tempM = 0;	
		
			
		
			for(i=0;i<=4;i++)
			{
				for(j=0;j<=4;j++)
				{
					for(k=0;k<=4;k++)
					{
						for(l=0;l<=4;l++)
						{
							for(m=0;m<=4;m++)
							{

								if(i!=j && i!=k && i!=l && i!=m && 
								   		   j!=k && j!=l && j!=m && 
								   				   k!=l && k!=m && 
								   				           l!=m 
								)
								{
									salesI = data.sales[whichSales][i];
									salesJ = data.sales[whichSales][j];
									salesK = data.sales[whichSales][k];
									salesL = data.sales[whichSales][l];
									salesM = data.sales[whichSales][m];

									if(mode == 0)
									{
	                                    tempDistance = distDepot(whichSales,salesI)+dist(salesI,salesJ)
	                                    			   +dist(salesJ,salesK)+dist(salesK,salesL)+dist(salesL,salesM)
	                                    			   +distDepot(whichSales,salesM);
	                                }
	                                else
	                                {
	                                    tempDistance = dist(data.TOTALNODES+whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)+dist(salesL,salesM)+dist(salesM,data.TOTALNODES+whichSales);
	                                }
									if(tempDistance < minDistance)
									{
										minDistance = tempDistance;
										path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL; path[4] = salesM;
										tempM = m;
										
									}
								}
							}
						}
					}
				}
		
			}
			
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
		System.out.printf("The Route %d-%d-%d-%d-%d \n",path[0],path[1],path[2],path[3],path[4]);
		for(int index = 0 ; index < path.length ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
	}

	/**
	 * compute how far must a salesman travel over 6 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute6(int whichSales,int mode)
	{

		int i,j,k,l,m,n;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[6];
		int salesI = 0, salesJ = 0, salesK = 0, salesL = 0, salesM = 0,salesN = 0;
			
		
			
		
		for(i=0;i<=5;i++)
		{
			for(j=0;j<=5;j++)
			{
				for(k=0;k<=5;k++)
				{
					for(l=0;l<=5;l++)
					{
						for(m=0;m<=5;m++)
						{
							for(n=0;n<=5;n++)
							{
								if(i!=j && i!=k && i!=l && i!=m && i!=n && 
								   		   j!=k && j!=l && j!=m && j!=n && 
								   				   k!=l && k!=m && k!=n && 
								   				           l!=m && l!=n && 
								   				           	       m!=n 
								)
								{
									salesI = data.sales[whichSales][i];
									salesJ = data.sales[whichSales][j];
									salesK = data.sales[whichSales][k];
									salesL = data.sales[whichSales][l];
									salesM = data.sales[whichSales][m];
									salesN = data.sales[whichSales][n];
									
									if(mode == 0)
									{
									    tempDistance = distDepot(whichSales,salesI)+dist(salesI,salesJ)
									    		       +dist(salesJ,salesK)+dist(salesK,salesL)+dist(salesL,salesM)
									    		       +dist(salesM,salesN)+distDepot(whichSales,salesN);
	                                }
	                                else
	                                {
	                                    tempDistance = dist(data.TOTALNODES+whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)+dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,data.TOTALNODES+whichSales);                                
	                                }
									if(tempDistance < minDistance)
									{
										minDistance = tempDistance;
										path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL; path[4] = salesM; path[5] = salesN;
										
									}
								}
							}
						}
					}
				}
			}
		}
			
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
			System.out.printf("The Route %d-%d-%d-%d-%d-%d \n",path[0],path[1],path[2],path[3],path[4],path[5]);
			for(int index = 0 ; index < path.length ; index++)
			{
				data.path[whichSales][index] = path[index];
			}
	}

	/**
	 * compute how far must a salesman travel over 7 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute7(int whichSales,int mode)
	{

		int i,j,k,l,m,n,o;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[7];
		int salesI = 0, salesJ = 0, salesK = 0, salesL = 0, salesM = 0,salesN = 0,salesO = 0;
			
		
			
		
		for(i=0;i<=6;i++)
		{
			for(j=0;j<=6;j++)
			{
				for(k=0;k<=6;k++)
				{
					for(l=0;l<=6;l++)
					{
						for(m=0;m<=6;m++)
						{
							for(n=0;n<=6;n++)
							{
								for(o=0;o<=6;o++)
								{
									if(i!=j && i!=k && i!=l && i!=m && i!=n && i!=o &&
									   		   j!=k && j!=l && j!=m && j!=n && j!=o && 
									   				   k!=l && k!=m && k!=n && k!=o &&
									   				           l!=m && l!=n && l!=o &&
									   				           	       m!=n && m!=o &&
																			   n!=o
									)
									{
										salesI = data.sales[whichSales][i];
										salesJ = data.sales[whichSales][j];
										salesK = data.sales[whichSales][k];
										salesL = data.sales[whichSales][l];
										salesM = data.sales[whichSales][m];
										salesN = data.sales[whichSales][n];
										salesO = data.sales[whichSales][o];
										
										if(mode == 0)
										{
	                                        tempDistance = distDepot(whichSales,salesI)+dist(salesI,salesJ)
	                                        				+dist(salesJ,salesK)+dist(salesK,salesL)+dist(salesL,salesM)
	                                        				+dist(salesM,salesN)+dist(salesN,salesO)+distDepot(whichSales,salesO);
	                                    }
	                                    else
	                                    {
	                                        tempDistance = dist(data.TOTALNODES+whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
											+dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,data.TOTALNODES+whichSales);
	                                    }
										if(tempDistance < minDistance)
										{
											minDistance = tempDistance;
					                        path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL; path[4] = salesM; 
	                                        path[5] = salesN; path[6] = salesO;
										}
									}
								}
							}
						}
					}
				}
			}
		}
			
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
		System.out.printf("The Route %d-%d-%d-%d-%d-%d-%d \n",path[0],path[1],path[2],path[3],path[4],path[5],path[6]);
		for(int index = 0 ; index < path.length ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
	}

	/**
	 * compute how far must a salesman travel over 8 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute8(int whichSales ,int mode)
	{

		int i,j,k,l,m,n,o,p;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[8];
		int howmany;
		int salesI = 0, salesJ = 0, salesK = 0, salesL = 0, salesM = 0,salesN = 0,salesO = 0,salesP=0;
			
		
			
		
		for(i=0;i<=7;i++)
		{
			for(j=0;j<=7;j++)
			{
				for(k=0;k<=7;k++)
				{
					for(l=0;l<=7;l++)
					{
						for(m=0;m<=7;m++)
						{
							for(n=0;n<=7;n++)
							{
								for(o=0;o<=7;o++)
								{
									for(p=0;p<=7;p++)
									{
										
										if(i!=j && i!=k && i!=l && i!=m && i!=n && i!=o && i!=p &&
									   		   	   j!=k && j!=l && j!=m && j!=n && j!=o && j!=p &&
									   				       k!=l && k!=m && k!=n && k!=o && k!=p &&
									   				               l!=m && l!=n && l!=o && l!=p &&
									   				           	           m!=n && m!=o && m!=p &&
									   				           	           		   n!=o && n!=p &&	
																						   o!=p	
										)

										{
											salesI = data.sales[whichSales][i];
											salesJ = data.sales[whichSales][j];
											salesK = data.sales[whichSales][k];
											salesL = data.sales[whichSales][l];
											salesM = data.sales[whichSales][m];
											salesN = data.sales[whichSales][n];
											salesO = data.sales[whichSales][o];
											salesP = data.sales[whichSales][p];
											if(mode == 0)
											{
	    										tempDistance = distDepot(whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
	    										+dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,salesP)+distDepot(whichSales,salesP);
	                                        }
	                                        else
	                                        {
	                                            tempDistance = dist(data.TOTALNODES+whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
	    										+dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,salesP)+dist(salesP,data.TOTALNODES+whichSales);
	                                        }
											if(tempDistance < minDistance)
											{
												minDistance = tempDistance;
	                                            path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL; path[4] = salesM; 
	                                            path[5] = salesN; path[6] = salesO;	path[7] = salesP;									}
										}
									}	
								}
							}
						}
					}
				}
			}
		}
			
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
		System.out.printf("The Route %d-%d-%d-%d-%d-%d-%d-%d \n",path[0],path[1],path[2],path[3],path[4],path[5],path[6],path[7]);
		for(int index = 0 ; index < path.length ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
	}

	/**
	 * compute how far must a salesman travel over 9 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute9(int whichSales,int mode)
	{

		int i,j,k,l,m,n,o,p,q;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[9];
		int howmany;
		int salesI = 0, salesJ = 0, salesK = 0, salesL = 0, salesM = 0,salesN = 0,salesO = 0,salesP = 0,salesQ = 0;
			
		
			
		
		for(i=0;i<=8;i++)
		{
			for(j=0;j<=8;j++)
			{
				for(k=0;k<=8;k++)
				{
					for(l=0;l<=8;l++)
					{
						for(m=0;m<=8;m++)
						{
							for(n=0;n<=8;n++)
							{
								for(o=0;o<=8;o++)
								{
									for(p=0;p<=8;p++)
									{
										for(q=0;q<=8;q++)
										{
											if(i!=j && i!=k && i!=l && i!=m && i!=n && i!=o && i!=p && i!=q &&
									   		   		   j!=k && j!=l && j!=m && j!=n && j!=o && j!=p && j!=q &&
									   					       k!=l && k!=m && k!=n && k!=o && k!=p && k!=q &&
									   					               l!=m && l!=n && l!=o && l!=p && l!=q &&
									   				    	       	           m!=n && m!=o && m!=p && m!=q &&
									   				        	   	           		   n!=o && n!=p && n!=q	&&
									   				        	   	           		   		   o!=p && o!=q &&
									   				        	   	           		   		   		   p!=q

											)
											{
												salesI = data.sales[whichSales][i];
												salesJ = data.sales[whichSales][j];
												salesK = data.sales[whichSales][k];
												salesL = data.sales[whichSales][l];
												salesM = data.sales[whichSales][m];
												salesN = data.sales[whichSales][n];
												salesO = data.sales[whichSales][o];
												salesP = data.sales[whichSales][p];
												salesQ = data.sales[whichSales][q];
												if(mode == 0)
												{
	                                                tempDistance = distDepot(whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
												    +dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,salesP)
	    											+dist(salesP,salesQ)+distDepot(whichSales,salesQ);
	                                            }
	                                            else
	                                            {
	                                                tempDistance = dist(data.TOTALNODES+whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
												    +dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,salesP)
	    											+dist(salesP,salesQ)+dist(salesQ,data.TOTALNODES+whichSales);
	                                            }
												if(tempDistance < minDistance)
												{
													minDistance = tempDistance;
													path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL; 
	                                                path[4] = salesM; path[5] = salesN;	path[6] = salesO; path[7] = salesP; 
	                                                path[8] = salesQ;
												}
											}
										}
									}	
								}
							}
						}
					}
				}
			}
		}
			
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
		System.out.printf("The Route %d-%d-%d-%d-%d-%d-%d-%d-%d\n",path[0],path[1],path[2],path[3],path[4],path[5],path[6],path[7],path[8]);
		for(int index = 0 ; index < 9 ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
	}

	/**
	 * compute how far must a salesman travel over 10 node and back
	 * @param whichSales
	 * @param mode stands for multiple depot is it's 1, 0 for single depot
	 */
	void brute10(int whichSales,int mode)
	{

		int i,j,k,l,m,n,o,p,q,r;
		double minDistance = 999999999;
		double tempDistance =0;
		int path[] = new int[10];
		int howmany;
		int salesI = 0, salesJ = 0, salesK = 0, salesL = 0, salesM = 0,salesN = 0,salesO = 0,salesP = 0,salesQ = 0,salesR = 0;
		
		for(i=0;i<=9;i++)
		{
			for(j=0;j<=9;j++)
			{
				for(k=0;k<=9;k++)
				{
					for(l=0;l<=9;l++)
					{
						for(m=0;m<=9;m++)
						{
							for(n=0;n<=9;n++)
							{
								for(o=0;o<=9;o++)
								{
									for(p=0;p<=9;p++)
									{
										for(q=0;q<=9;q++)
										{
											for(r=0;r<=9;r++)
											{
						
	                                            if(i!=j && i!=k && i!=l && i!=m && i!=n && i!=o && i!=p && i!=q && i!=r &&
										   		   		   j!=k && j!=l && j!=m && j!=n && j!=o && j!=p && j!=q && j!=r &&
										   					       k!=l && k!=m && k!=n && k!=o && k!=p && k!=q && k!=r &&
										   					               l!=m && l!=n && l!=o && l!=p && l!=q && l!=r &&
										   				    	       	           m!=n && m!=o && m!=p && m!=q && m!=r &&
										   				        	   	           		   n!=o && n!=p && n!=q	&& n!=r &&
										   				        	   	           		   		   o!=p && o!=q && o!=r &&
										   				        	   	           		   		   		   p!=q && p!=r &&
										   				        	   	           		   		   		   		   q!=r

												)
												{
													salesI = data.sales[whichSales][i];
													salesJ = data.sales[whichSales][j];
													salesK = data.sales[whichSales][k];
													salesL = data.sales[whichSales][l];
													salesM = data.sales[whichSales][m];
													salesN = data.sales[whichSales][n];
													salesO = data.sales[whichSales][o];
													salesP = data.sales[whichSales][p];
													salesQ = data.sales[whichSales][q];
													salesR = data.sales[whichSales][r];
													if(mode == 0)
													{
	    												tempDistance = distDepot(whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
	    												+dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,salesP)
	    												+dist(salesP,salesQ)+dist(salesQ,salesR)+distDepot(whichSales,salesR);
	                                                }
	                                                else
	                                                {
	                                                    tempDistance = dist(data.TOTALNODES+whichSales,salesI)+dist(salesI,salesJ)+dist(salesJ,salesK)+dist(salesK,salesL)
	    												+dist(salesL,salesM)+dist(salesM,salesN)+dist(salesN,salesO)+dist(salesO,salesP)
	    												+dist(salesP,salesQ)+dist(salesQ,salesR)+dist(salesR,data.TOTALNODES+whichSales);
	                                                }
	                                                
													if(tempDistance < minDistance)
													{
														minDistance = tempDistance;
														path[0] = salesI; path[1] = salesJ; path[2] = salesK; path[3] = salesL; 
	                                                    path[4] = salesM; path[5] = salesN;	path[6] = salesO; path[7] = salesP; 
	                                                    path[8] = salesQ; path[9] = salesR;
													}
												}
											}	
										}
									}	
								}
							}
						}
					}
				}
			}
		}
			
		System.out.printf("Sales No.%d needs to travel %f distance\n",whichSales,minDistance);
		data.results[whichSales]=minDistance;
		System.out.printf("The Route %d-%d-%d-%d-%d-%d-%d-%d-%d-%d\n",path[0],path[1],path[2],path[3],path[4],path[5],path[6],path[7],path[8],path[9]);
		
		for(int index = 0 ; index < 10 ; index++)
		{
			data.path[whichSales][index] = path[index];
		}
	}
}
