

public class NewInTurns {

	private Data data;
	
	public NewInTurns(Data data)
	{
		this.data = data;
	}
	
	
	
	/**
	* Main function
	* Cluster the nodes until every group's centroids doesn't move 
	*/
	public void main(NewInTurns original)
	{
		boolean centerFlag = false;
		
		long startTime = System.currentTimeMillis();
		original.initCentroids();
		original.inTurnClustering();
		long endTime   = System.currentTimeMillis();

		long totalTime = endTime - startTime;
		System.out.println(totalTime+"   mine");
	    
		/*
		centerFlag = original.findCenter();
	    
	    data.iterations = 0;
	    while(centerFlag==false && data.iterations<11)
	    {
	    	original.inTurnClustering();          
	        centerFlag = findCenter();
	        data.iterations++;
	    }*/
	    original.printIt();
	}
	
	/**
	* Grouping function 
	* letting all the centroids takes turn to take in one node at a time as its cluster
	*/
	public void inTurnClustering()
	{
	    
		boolean insertFlag = false;
		
		
		//Initialize
		initSales();
		//While unassignedNodes is not empty 
		while(data.copies>0)
		{
			// for each salesman i, from i=1 to i=numberSalemen
			// choose from the set unassignedNodes the node closest to the
			// depot of salesman i, and assign it to salesman i.
			
			
			
			for(int i=1 ; i<data.SALESMAN ; i++)
			{
				if(data.copies<1)
					break;
				
				
				double min = 9999999;
				int whichNode = 0;
				insertFlag = false;
				
				// find the node that is closest to salesman i's centroid
				for(int j=1 ; j<data.TOTALNODES+1 ; j++)
				{
					
					if(data.nodes[j][2]<1)
						continue;
					
					double dist = dist(data.centroids[i][0],data.centroids[i][1],data.nodes[j][0],data.nodes[j][1]);
					if (dist < min && data.chosen[i][j] == false)
					{
						min = dist;
						whichNode = j;
						insertFlag = true;
					}
				}
				
				System.out.println("copies = "+data.copies+" sales = "+i+" whichNode = "+whichNode);
				
				// if such node is found, assign it to the salesman
				if(insertFlag)
				{
					insertSales(whichNode,i);
				}
			}
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
