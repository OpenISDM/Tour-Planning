

// take turn with centroids
public class CircleDepAndCent {

	



	private Data data;
	
	public CircleDepAndCent(Data data)
	{
		this.data = data;
	}
	
	
	
	/**
	* Main function
	* Cluster the nodes until every group's centroids doesn't move 
	*/
	public void main(CircleDepAndCent original)
	{
		boolean centerFlag = false;
		
		
		original.initCentroids();
		original.inTurnClustering();
		//System.out.println(totalTime+"  jane's");

		
		centerFlag = original.findCenter();
	    
	    data.iterations = 0;
	    while(centerFlag==false && data.iterations<50)
	    {
	    	original.inTurnClustering();          
	        centerFlag = findCenter();
	        data.iterations++;
	    }
	    System.out.println(data.iterations);
	    //original.printIt();
	}
	
	/**
	* Grouping function 
	* letting all the centroids takes turn to take in one node at a time as its cluster
	*/
	public void inTurnClustering()
	{
	    
		boolean insertFlag = false;
		boolean dead[] = new boolean[data.SALESMAN];
		int dcount = 0;
		int iter = 0;
		
		
		//Initialize
		for(int i=1;i<data.SALESMAN;i++)
			dead[i] = false;
		initSales();
		data.sortListCent();
		determineDvalue();
		
		
		//While unassignedNodes is not empty 
		while(data.copies>0)
		{
			// for each salesman i, from i=1 to i=numberSalemen
			// choose from the set unassignedNodes the node closest to the
			// depot of salesman i, and assign it to salesman i.
			iter++;
			dcount = 0;
			for(int i=1 ; i<data.SALESMAN ; i++)
			{
				if(data.copies<1)
					break;
				
				int whichNode = 0;
				double dist;
				insertFlag = false;
				
				// find the node that is closest to salesman i's centroid
				for(int j=1 ; j<data.TOTALNODES+1 ; j++)
				{
					
					// if node i is picked by someone else or already chosen by sales i
					if(data.nodes[data.list[i][j]][2]<1 || data.chosen[i][data.list[i][j]])
						continue;
					
					// the distance between the depot and the node
					dist = dist(data.nodes[data.list[i][j]][0],data.nodes[data.list[i][j]][1],(data.centroids[i][0]+data.depot[i][0])/2,(data.centroids[i][1]+data.depot[i][1])/2);
					
					if(dist < data.dValue[i] )
					{
						insertFlag = true;
					}
					if(insertFlag)
					{
						insertSales(data.list[i][j],i);
						whichNode = data.list[i][j];
						break;
					}
					
				}
				
				if(insertFlag == false)
				{
					dcount++;
				}
			}
			

			
			// while all the left nodes are too far for the depot 
			if (data.copies>0 && dcount==data.SALESMAN-1)
			{
				double min;
				int whichMin;
				double dist;
				findCenter();
				
				for(int i=1 ; i<data.TOTALNODES+1 ; i++)
				{
					if(data.nodes[i][2]<1)
						continue;
					
					min = 9999999;
					whichMin = 0;
					dist = 0;
					for(int j=1 ; j<data.SALESMAN ; j++)
					{
						if(data.chosen[j][i])
							continue;
						
						dist = dist(data.nodes[i][0],data.nodes[i][1],(data.centroids[j][0]+data.depot[j][0])/2,(data.centroids[j][1]+data.depot[j][1])/2);
						if(dist<min)
						{
							min = dist;
							whichMin = j;
						}
					}
					insertSales(i,whichMin);
				}
				
			}
			
		}
	  
	}
	
	// for every salesman find one that is closest to him
	// set half of their distance as their circle's diameter
	public void determineDvalue2()
	{
		double min = 9999999;
		
		
		for(int i=1;i<data.SALESMAN;i++)
		{
			min = 9999999;
			for(int j=1 ; j<data.SALESMAN ; j++)
			{
				if (i==j)
					continue;
				if(data.distMatrix[data.TOTALNODES+i][data.TOTALNODES+j]<min)
				{
					min = data.distMatrix[data.TOTALNODES+i][data.TOTALNODES+j];
				}
			}
			data.dValue[i] = (int) Math.floor(min/2);
			//System.out.println(i+"dvalue = "+data.dValue[i]);
		}
		
	}
	// for every salesman find one that is closest to him
	// set half of their distance as their circle's diameter
	public void determineDvalue()
	{
		double min = 9999999;
		double distt = 0;
		
		for(int i=1;i<data.SALESMAN;i++)
		{
			min = 9999999;
			for(int j=1 ; j<data.SALESMAN ; j++)
			{
				if (i==j)
					continue;
				distt = dist((data.centroids[i][0]+data.depot[i][0])/2,(data.centroids[i][1]+data.depot[i][1])/2,(data.centroids[j][0]+data.depot[j][0])/2,(data.centroids[j][1]+data.depot[j][1])/2);
				if(distt<min)
				{
					min = distt;
				}
			}
			data.dValue[i] = (int) Math.floor(min/2);
			//System.out.println(i+"dvalue = "+data.dValue[i]);
		}
		
	}
	
	/**
	 * conting the distance for int from node to int to node
	 * 
	 * @param from
	 * @param to
	 * @return the distance from node from to node to
	 */
	public double dist(int fromX, int fromY , int toX , int toY)
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
	    
	    //FOR i=1 to total number of centroids
	    for(i=1;i<data.SALESMAN;i++)
	    {
	    	//FOR j=0 to total number of nodes
	    	for(j=0;j<data.indexs[i];j++)
	        {
	            // IF salesman No.i has run out of nodes
	    		if(data.sales[i][j]==0)
	            {   
	    			// if this cluster is empty
	                if(j==0)
	                    emptyFlag++;
	                break;
	            }
	            else
	            {
	                // calculate centerX and centerY
	            	centerX += data.nodes[data.sales[i][j]][0];
	                centerY += data.nodes[data.sales[i][j]][1];            
	                numOfNodes++;
	            }
	        }
	        if(numOfNodes == 0)
	        {
	        	// do nothing
				// this is to prevent having the denominator to be zero
	        }
	        else
	        {
	        	//IF the previous centroids's x coordinate equals centerX/numOfNodes
	            if(data.centroids[i][0] == centerX / numOfNodes && data.centroids[i][1] == centerY / numOfNodes )
	            {
	                numFlag++;
	                
	            }
	            
	            data.centroids[i][0] = centerX / numOfNodes;
	            data.centroids[i][1] = centerY / numOfNodes;
	        }
	        // initialize local variable
	        numOfNodes = centerX = centerY = 0;
	    }
	    // IF none of the clusters has changed
	    if (numFlag + emptyFlag == data.SALESMAN-1)
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}
	/**
	* A function to print the nodes out 
	*/
	public void printIt()
	{
	    int i,j;
	    
	    System.out.println("inside  NewInTurns!!!!!!!!!!!!!");
	    for(i=1;i<data.SALESMAN;i++)
	    {   
	        
	    
	        
	        System.out.printf("%d salesMan\n\n\n",i);
	        
	        System.out.printf("nodes are\n");
	        for(j=0;j<data.TOTALNODES+1;j++)
	        {
	            if(data.sales[i][j] == 0)
	                break;
	            System.out.printf("%d ",data.sales[i][j]);
	        }
	        System.out.printf("\n\n");
	    }
	}
	
	
	
	/**
	 * Initialize centroids
	 * 
	 */
	
	public void initCentroids()
	{
		for(int i=1; i<data.SALESMAN; i++ )
		{
			data.centroids[i][0] = data.nodes[data.TOTALNODES+1-1+i][0];
			data.centroids[i][1] = data.nodes[data.TOTALNODES+1-1+i][1];
		}

	}
	
	/**
	* initSales
	* initialize the salesman array and other stuff
	*/  
	public void initSales()
	{
	    int i,j;
	    
	    data.copies = (data.TOTALNODES+1-1)*data.k;
	    for(i=0;i<data.SALESMAN;i++)
	    {
	    	for(j=0;j<data.TOTALNODES+1;j++)
			{
				data.chosen[i][j] = false;
			}
	    }
	    
	    for(i=1 ; i<data.TOTALNODES+1; i++)
		{
			data.nodes[i][2] = data.k;
		}
	    for(i=0;i<data.SALESMAN;i++)
	    {   
	        data.indexs[i] = 0;
	        for(j=0;j<data.TOTALNODES+1;j++)
	            data.sales[i][j] = 0;    
	    }
	    for(i = 1 ; i<data.SALESMAN ; i++)
	    {
	    	data.results[i] = 0;
	    }
	    
	}

	/**
	* insertSales
	* insert an node to the sales array
	* insert node No.nodeNum into salesman No.salesNum
	*/ 
	public void insertSales(int nodeNum ,int salesNum)
	{

	    data.sales[salesNum][data.indexs[salesNum]] = nodeNum;
	    data.nodes[nodeNum][2]--;
	    data.copies--;
	    data.chosen[salesNum][nodeNum] = true;
	    data.indexs[salesNum]++;
	    return;
	}
	
	
}

