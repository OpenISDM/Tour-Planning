
public class Inturns {

	
private Data main;
	
	public Inturns(Data main)
	{
		this.main = main;
	}
	
	
	/**
	* Grouping function 
	* letting all the centroids takes turn to take in one node at a time as its cluster
	*/
	public void inTurnClustering()
	{
	    
	    int i,dist,jTemp,j;
	    int min = 9999999;
	    boolean insertFlag = false;
	    int whichSales = 0;
	    initSales();
	    
	    jTemp = 0;
	    dist = 9999999;
	    
	    
	    
	    // FOR i = 1 to total number of node-copies in the map
	    // total number of node-copies = totalnodes times k
		// k : number per times a nodes must be visit
	    for(i=1;i<(main.TOTALNODES+1-1)*main.k+1;i++)
	    {
	    	//the whichSales will appear as 1,2,3...,num of Sales,1,2,3 and so on
	    	whichSales = (i%(main.SALESMAN+-1))+1;
	    	
	    	//FOR j = 1 to total number of nodes 
	    	for(j=1;j<main.TOTALNODES+1;j++)
	        {
	            
	    		
	            
	    		//set int variable dist as the distance between 
				//node No.j and centroid No.whichSales
	            dist = (main.nodes[j][0]-main.centroids[whichSales][0])*
	            		(main.nodes[j][0]-main.centroids[whichSales][0])
	            		+ (main.nodes[j][1]-main.centroids[whichSales][1])*
	            		(main.nodes[j][1]-main.centroids[whichSales][1]);
	            
	            //IF dist is smaller than min AND node No.j is not chosen by 
				//salesman No.whichSales AND there is at least one copie of node No.j
	            if(dist<min && main.chosen[whichSales][j] == false && main.nodes[j][2]>0)
	            {
	                min = dist;
	                jTemp = j;
	                insertFlag = true;
	            }
	        }
	    	//END OF FOR
	    	
	        if(insertFlag == true)
	        {
	            //assign node No.jTemp to salesman No.whichSales
	        	insertSales(jTemp,(i%(main.SALESMAN+-1))+1);
	        	//decrease the number of node copies of node No.jTemp by 1
	            main.nodes[jTemp][2]--;
	            //mark this node as already chosen by salesman No.whichSales
	            main.chosen[whichSales][jTemp] = true;
	            
	            // Initialize local variable
	            min = 9999999;
	            jTemp = 0;
	            insertFlag = false;
	            
	        }
	        // else here is used to prevent the situation that at the end of 
 			// the outer-most for loop iterations, the node copies left to 
 			// be picked may be already selected by salesman No.whichSales
 			// i.e, there may be already a copy of the remaining nodes in
 			// the node No.whichSales's pocket
 			// so we must put the node elsewhere by finding which salesman
 			// is closest to the remaining node and assign the node 
 			// to the salesmans
	        else
	        {
	        	for(int k=1; k<main.TOTALNODES+1 ; k++)
	        	{
	        		// IF node No.k has more than one copies
	        		if(main.nodes[k][2]>0)
	        		{
	        			
	        			
	        			// local variable
	        			// tmin : temperary min value
	        			int tmin = 9999999;
	        			// ltemp : keep tract of l index
	        			int ltemp = 0;
	        			// dis : used to store the distance between nodes and centoids
	        			int dis = 9999999;
	        			// flag : to see if we find the node to insert to salesman
	        			boolean flag = false;
	        			
	        			for(int l=1 ; l<main.SALESMAN; l++)
	        			{
	        				dis = (main.nodes[k][0]-main.centroids[l][0])*
	  	        				  (main.nodes[k][0]-main.centroids[l][0])
	      	              	  	+ (main.nodes[k][1]-main.centroids[l][1])*
	      	            		  (main.nodes[k][1]-main.centroids[l][1]);
	        				if(dis < tmin)
	        				{
	        					tmin = dis;
	        					ltemp = l;
	        					flag = true;
	        				}
	        			}
	        			if(flag)
	        			{
	        				
	        				main.nodes[k][2]--;
	        				insertSales(k,ltemp);
	        				main.chosen[ltemp][k] = true;
	        				flag = false;
	        				tmin = 9999999;
	        				dis = 9999999;
	        			}
	        		}
	        	}
	        	// initialize variables
	        	min = 9999999;
	            jTemp = 0;
	            insertFlag = false;
	        }
	    }
	}
	/**
	*findCenter finds the centroids after grouping
	*@return howmany group changed their centroids
	*/
	public int findCenter()
	{
	    int i,j,num,tempX,tempY,temp;
	    temp = num = tempX = tempY = 0;
	    

	    for(i=1;i<main.SALESMAN;i++)
	    {
	        for(j=0;j<30;j++)
	        {
	            if(main.sales[i][j]==0)
	            {        
	                if(j==0)
	                    temp++;
	                break;
	            }
	            else
	            {
	                tempX += main.nodes[main.sales[i][j]][0];
	                tempY += main.nodes[main.sales[i][j]][1];            
	                num++;
	            
	            }
	        }
	        if(num == 0)
	        {
	            
	        }
	        else
	        {
	            if(main.centroids[i][0] == tempX / num && main.centroids[i][1] == tempY / num )
	            {
	                temp++;
	                
	            }
	            main.centroids[i][0] = tempX / num;
	                main.centroids[i][1] = tempY / num;
	        }
	        num = tempX = tempY = 0;
	    }
	    if (temp == main.SALESMAN-1)
	    {
	        //System.out.printf("temp = %d\n",temp);
	        return 0;
	    }
	    else
	    {
	        //System.out.printf("temp = %d\n",temp);
	        return 1;
	    }
	    

	}
	/**
	* A function to print the nodes out 
	*/
	public void printIt()
	{
	    int i,j;
	    
	    System.out.println("inside  Other!!!!!!!!!!!!!");
	    for(i=1;i<main.SALESMAN;i++)
	    {   
	        
	    
	        
	        System.out.printf("%d salesMan\n\n\n",i);
	        
	        System.out.printf("nodes are\n");
	        for(j=0;j<main.TOTALNODES+1;j++)
	        {
	            if(main.sales[i][j] == 0)
	                break;
	            System.out.printf("%d ",main.sales[i][j]);
	        }
	        System.out.printf("\n\n");
	    }
	}
	/**
	* Main function
	* Cluster the nodes until every group's centroids doesn't move 
	*/
	public void main(Inturns original)
	{
		int centerFlag = 1;
		
		original.initCentroids();
		original.inTurnClustering();
	    /*centerFlag = original.findCenter();
	    
	    main.iterations = 0;
	    while(centerFlag==1 && main.iterations<5)
	    {
	    	original.inTurnClustering();          
	        centerFlag = findCenter();
	        //for(i=1;i<7;i++)
	            //printf(" helo  %d %d %d\n",i,centroids[i][0],centroids[i][1]);
	        main.iterations++;
	    }*/
	    System.out.println(main.iterations+" the iteration");
	    original.printIt();
	}
	
	
	/**
	 * Initialize centroids
	 * 
	 */
	
	public void initCentroids()
	{
		for(int i=1; i<main.SALESMAN; i++ )
		{
			main.centroids[i][0] = main.nodes[main.TOTALNODES+1-1+i][0];
			main.centroids[i][1] = main.nodes[main.TOTALNODES+1-1+i][1];
		}
		
		
	}
	
	/**
	* initSales
	* initialize the salesman array and other stuff
	*/  
	public void initSales()
	{
	    int i,j;
	    
	    for(i=0;i<main.SALESMAN;i++)
	    {
	    	for(j=0;j<main.TOTALNODES+1;j++)
			{
				main.chosen[i][j] = false;
			}
	    }
	    
	    for(i=1 ; i<main.TOTALNODES+1; i++)
		{
			main.nodes[i][2] = main.k;
		}
	    for(i=0;i<main.SALESMAN;i++)
	    {   
	        main.indexs[i] = 0;
	        for(j=0;j<main.TOTALNODES+1;j++)
	            main.sales[i][j] = 0;    
	    }
	}

	/**
	* insertSales
	* insert an node to the sales array
	* insert node No.nodeNum into salesman No.salesNum
	*/ 
	public void insertSales(int nodeNum ,int salesNum)
	{

	    main.sales[salesNum][main.indexs[salesNum]] = nodeNum;

	    main.indexs[salesNum]++;
	    return;
	}
}
