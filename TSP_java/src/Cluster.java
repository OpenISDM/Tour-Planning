/**
 * This is the cluster class
 * @author William
 * @version 1.0
 */
public class Cluster {
	
	/**
	 * A input object that holds the data structure of the TSP problem
	 */
	private Data data;
	
	/**
	 * Cluster's constructor 
	 * @param data set this.data = data links the datastructure to the Cluster class
	 */
	public Cluster(Data data)
	{
		this.data = data;
	}
	
	/**
	* Main function
	* Cluster the nodes until every group's centroids doesn't move 
	*/
	public void main(Cluster original)
	{
		boolean centerFlag = false;
		
		original.initCentroids();
		original.group();
	    centerFlag = original.findCenter();
	    data.iterations = 0;
	    while(centerFlag==false && data.iterations<50)
	    {
	    	original.group();          
	        centerFlag = findCenter();
	        data.iterations++;
	    }
	    //System.out.println(data.iterations);
	    //original.printIt();
	}
	
	/**
	*Grouping function which group every nodes with the centroid that is closest to it
	*
	*/
	public void group()
	{
	    int i,j;
	    double[] distArray= new double[data.SALESMAN];
	    double min = 9999999;
	    int whichMin = 0;
	    boolean insertFlag;
	    initSales();
	    
	    //FOR index = 0 to total number of node copies
	    for(int index=0;index<(data.TOTALNODES+1-1)*data.k;index++)
	    {
	    	// i goes like 1,2,3,....number of nodes,1,2,3... 
	    	// for k times
	    	i = index % (data.TOTALNODES+1-1);
	        i++;
	    	if(data.nodes[i][2]<1)
	            continue;
	    	
	    	insertFlag = false;
	    	// find out which cluster is closest to the node i
	        for(j=1;j<data.SALESMAN;j++)
	        {
	            //IF this node is already chosen by salesman No.j
	        	if(data.chosen[j][i])
	            	continue;
	        	
	        	//set distArray[j] = distance between centroid No.j and node No.i
	        	distArray[j] = (data.nodes[i][0]-data.centroids[j][0])*(data.nodes[i][0]-data.centroids[j][0]) + (data.nodes[i][1]-data.centroids[j][1])*(data.nodes[i][1]-data.centroids[j][1]);
	        	//System.out.println(j+" dis = "+distArray[j]);
	        	if(distArray[j]<=min)
	            {
	                whichMin = j;
	                min = distArray[j];  
	                insertFlag = true;
	            }
	        }
	        //assign node No.whichMin to sales No.i by calling insertSales
	        if(i==5)
	        {
	        	//System.out.println(data.nodes[i][0]);
	        	//System.out.println("inserting to "+whichMin);
	        }
	        if(insertFlag)
	        {
		        insertSales(i,whichMin);
		        //mark node No.whichMin chosen by salesman No.i
		        data.chosen[whichMin][i] = true;
				//kill one copy of node No.whichMin
		        data.nodes[i][2]--;
		        whichMin = 0;
		        min = 9999999;
	        }
	        //System.out.println("after grouping the indexs are == "+data.indexs[i]);
	    }
	    
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
	* 
	* 
	*/
	public void printIt()
	{
	    int i,j;
	    
	   System.out.println("inside  Cluter!!!!!!!!!!!!!");
	    for(i=1;i<data.SALESMAN;i++)
	    {   

	        System.out.printf("%d salesMan\n\n\n",i);
	        System.out.println("howmany nodes!!"+data.indexs[i]);
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
	        data.indexs[i] = 0;
	        for(j=0;j<data.TOTALNODES+1;j++)
	            data.sales[i][j] = 0;    
	    }
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
	    for(i = 1 ; i<data.SALESMAN ; i++)
	    {
	    	data.results[i] = 0;
	    }
	}

	/**
	* insertSales
	* insert an node to the sales array
	*/ 
	public void insertSales(int nodeNum ,int salesNum)
	{

	    data.sales[salesNum][data.indexs[salesNum]] = nodeNum;
	    data.copies--;
	    data.indexs[salesNum]++;
	    return;
	}
	

}
