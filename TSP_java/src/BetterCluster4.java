
// B4 mix clustering


public class BetterCluster4 {

	
	private Data data;
	
	public int salesTemp[][];
	public int indexsTemp[]; 
	public int nodesTemp[][];
	public boolean chosenTemp[][];
	
	public int remains;
	
	public BetterCluster4(Data data)
	{
		this.data = data;
		this.salesTemp = new int [data.SALESMAN][data.TOTALNODES+1];
		this.indexsTemp = new int [data.TOTALNODES];
		nodesTemp = new int [data.TOTALNODES+data.SALESMAN][3];
		chosenTemp = new boolean[data.SALESMAN][data.TOTALNODES + 1];
		
	}
	
	
	
	/**
	* Main function
	* Cluster the nodes until every group's centroids doesn't move 
	*/
	public void main(BetterCluster4 betterCluster)
	{
		boolean centerFlag = false;
		
		
		betterCluster.initCentroids();
		
		
		betterCluster.inTurnClustering2();

		// compute the remaining centroid
		betterCluster.group();
		// cluster the rest nodes
		centerFlag = betterCluster.findCenter();
	   	
	    data.iterations = 0;
	    System.out.println("out of while flag = "+centerFlag);
	    while(centerFlag==false && data.iterations<50)
	    {
	    	betterCluster.group();          
	        centerFlag = findCenter();
	        data.iterations++;
	    }
	    
	    assignTwo();
	}
	
	
	
	public void assignTwo()
	{
		
		for(int i=1;i<data.SALESMAN;i++)
		{
			for(int j=0;j<indexsTemp[i];j++)
			{
				insertSales(salesTemp[i][j],i);
			}
			
			
		}
		
		
	}
	
	public void assignClusters()
	{
		double min = 9999999;
		int whichMin = 0;
		boolean record [] = new boolean [data.SALESMAN];
		
		
		//Initialize
		for(int i=0;i<data.SALESMAN;i++)
			record[i] = false;
		
		//for each salesman, in each iteration, find the closest cluster to him , and assign it to him
		for(int i=1 ; i<data.SALESMAN ; i++)
		{
			min = 9999999;
			
			
			for(int j=1 ; j<data.SALESMAN; j++)
			{
				if(record[j])
					continue;
				
				// the distance between salesman I's depot to number J cluster's centroid
				double dist = data.distGeneral(data.depot[i][0],data.depot[i][1], data.centroids[j][0] , data.centroids[j][1]);
				if(dist < min)
				{
					min = data.distGeneral(data.depot[i][0],data.depot[i][1], data.centroids[j][0] , data.centroids[j][1]);
					whichMin = j;
				}
			}
			
			record[whichMin]=true;
			
			for(int j=0 ; j<indexsTemp[whichMin]; j++)
			{
				insertSales(salesTemp[whichMin][j],i);
			}
			
		}
		
		
		
		
	}
	
	public void determineDvalue()
	{
		double min = 9999999;
		
		for(int i=1;i<data.SALESMAN;i++)
		{
			min = 9999999;
			for(int j=1 ; j<data.SALESMAN ; j++)
			{
				if (i==j)
					continue;
				if(data.distMatrix[data.Nodebound+i][data.Nodebound+j]<min)
				{
					min = data.distMatrix[data.Nodebound+i][data.Nodebound+j];
				}
			}
			data.dValue[i] = (int) Math.floor(min/2);
			System.out.println(i+"dvalue = "+data.dValue[i]);
		}
		
		
	}
	
	
	/**
	* Grouping function 
	* take all the nodes inside the circle
	*/
	public void inTurnClustering2()
	{
		initSales();
		determineDvalue();
		boolean insertFlag = false;
		for(int i=1 ; i<data.SALESMAN ; i++)
		{
			
			for(int j=1 ; j<data.TOTALNODES ; j++)
			{
				if(dist(data.nodes[j][0],data.nodes[j][1],data.depot[i][0],data.depot[i][1]) < data.dValue[i] && data.chosen[i][j]==false && data.nodes[j][2]>0 )
				{
					insertSales(j,i);
				}
			}
		}
		remains = data.copies;
	}
	
	/**
	* Grouping function 
	* letting all the centroids takes turn to take in one node at a time as its cluster
	*/
	public void inTurnClustering()
	{
	    
		boolean insertFlag = false;
		boolean dead[] = new boolean[data.SALESMAN];
		boolean dcount [] = new boolean [data.SALESMAN];
		int temp = 0;
		int iter = 0;
		//Initialize
		for(int i=1;i<data.SALESMAN;i++)
			dead[i] = false;
		initSales();
		data.sortList();
		//While unassignedNodes is not empty 
		
		determineDvalue();
		
		
		while(data.copies>0 && temp<data.SALESMAN-1)
		{
			
			// for each salesman i, from i=1 to i=numberSalemen
			// choose from the set unassignedNodes the node closest to the
			// depot of salesman i, and assign it to salesman i.
			iter ++;
			temp = 0;
			for(int i = 1; i<data.SALESMAN ; i++)
				dcount[i]=false;
			for(int i=1 ; i<data.SALESMAN ; i++)
			{
				if(data.copies<1)
					break;
				//int whichNode = 0;
				insertFlag = false;
				// find the node that is closest to salesman i's centroid
				for(int j=1 ; j<data.TOTALNODES+1 ; j++)
				{
					// if node i is picked by someone else or already chosen by sales i
					if(data.nodes[data.list[i][j]][2]<1 || data.chosen[i][data.list[i][j]])
						continue;
					// the distance between the depot and the node
					
					if(data.distMatrix[data.list[i][j]][data.TOTALNODES+i] < data.dValue[i] )
					{
						insertFlag = true;
					}
					if(insertFlag)
					{
						insertSales(data.list[i][j],i);
						//whichNode = data.list[i][j];
						break;
					}
				}
				if(insertFlag == false)
				{
					dcount[i] = true;
				}
			}
			for(int i=1 ; i<data.SALESMAN ; i++)
			{
				if(dcount[i])
					temp++;
			}
		}
		remains = data.copies;
	}
	
	
	/**
	 *  	use original clustering to take care of the rest
	 * 
	 */

	
	/**
	*Grouping function which group every nodes with the centroid that is closest to it
	*/
	public void group()
	{
	    double[] distArray= new double[data.SALESMAN];
	    double min = 9999999;
	    int whichMin = 0;
	    int index = 0;
	    boolean insertFlag = false;
	    
	    initSalesTwo();
	    while (data.copies>0)
	    {
	    	
	    	index++;
	    	if(index==data.TOTALNODES+1)
	    	{
	    		index=1;
	    	}
	    	
	    	if(nodesTemp[index][2]<1)
	    		continue;
	    	
	    	insertFlag = false;
	    	for(int j=1 ; j<data.SALESMAN ; j++)
	    	{
	    		//IF this node No.index is already chosen by salesman No.j
	        	if(chosenTemp[j][index])
	            	continue;
	        	distArray[j] = (data.nodes[index][0]-data.centroids[j][0])*(data.nodes[index][0]-data.centroids[j][0]) 
	        				 + (data.nodes[index][1]-data.centroids[j][1])*(data.nodes[index][1]-data.centroids[j][1]);
	        	//System.out.println(j+" dis = "+distArray[j]+" iteration = "+data.iterations);
	            if(distArray[j]<=min)
	            {
	                whichMin = j;
	                min = distArray[j];     
	                insertFlag = true;
	            }
	    	}
	    	  //assign node No.whichMin to sales No.i by calling insertSales
	    	if(insertFlag)
	    	{
		        insertSalesTwo(index,whichMin);
		        
		        min = 9999999;
		        //System.out.println("copies = "+data.copies+" inserting node =  "+index+" into salesman = "+whichMin);
		        whichMin = 0;
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
	    	for(j=0;j<indexsTemp[i];j++)
	        {
	            // IF salesman No.i has run out of nodes
	    		if(salesTemp[i][j]==0)
	            {   
	    			// if this cluster is empty
	                if(j==0)
	                    emptyFlag++;
	                break;
	            }
	            else
	            {
	                // calculate centerX and centerY
	            	centerX += data.nodes[salesTemp[i][j]][0];
	                centerY += data.nodes[salesTemp[i][j]][1];            
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
	        //System.out.println("salesman = "+i+" iter = "+data.iterations+" centroid x :"+data.centroids[i][0]+" y :"+data.centroids[i][1]);
	        //System.out.println("total x = "+centerX+" total y = "+centerY);
	        //System.out.println("index = "+indexsTemp[i]);
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
			//data.centroids[i][0] = data.nodes[data.TOTALNODES+1-1+i][0];
			//data.centroids[i][1] = data.nodes[data.TOTALNODES+1-1+i][1];
			data.centroids[i][0] = data.depot[i][0];
			data.centroids[i][1] = data.depot[i][1];
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
	* initSales
	* initialize the salesman array and other stuff
	*/  
	public void initSalesTwo()
	{
	    int i,j;
	    
	    data.copies = remains;
	    
	    
	    for(i=0;i<data.SALESMAN;i++)
	    {
	    	for(j=0;j<data.TOTALNODES+1;j++)
			{
				chosenTemp[i][j] = data.chosen[i][j];
			}
	    }
	    
	    for(i=1 ; i<data.TOTALNODES+1; i++)
		{
			nodesTemp[i][2] = data.nodes[i][2];
		}
	    
	    for(i=0;i<data.SALESMAN;i++)
	    {   
	        indexsTemp[i] = 0;
	        for(j=0;j<data.TOTALNODES+1;j++)
	            salesTemp[i][j] = 0;    
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
	
	/**
	* insertSales
	* insert an node to the sales array
	* insert node No.nodeNum into salesman No.salesNum
	*/ 
	public void insertSalesTwo(int nodeNum ,int salesNum)
	{

	    salesTemp[salesNum][indexsTemp[salesNum]] = nodeNum;
	    nodesTemp[nodeNum][2]--;
	    data.copies--;
	    chosenTemp[salesNum][nodeNum] = true;
	    indexsTemp[salesNum]++;
	    return;
	}
	
}

