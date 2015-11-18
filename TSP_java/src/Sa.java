import java.util.Random;


public class Sa {

	/**
	 * A input class that holds the data structure of the TSP problem
	 */
	private Data data;
	
	/**
	 * BruteForce constructor 
	 * @param data link BruteForce to the datastructure
	 * 
	 */
	public Sa(Data data)
	{
		this.data = data;
	}
	
	
	public void sa(int whichSales)
	{
		
		int total = data.indexs[whichSales];
		int init[] = new int[total];
		int next[] = new int[total];
		int best[] = new int[total];
		double bestCost = 0;
		double nowCost,laterCost;
		double temper = data.temper;
		double p = 0;
		double delta = 0;
		Random rn = new Random();
		
		data.temper = 2000;
		data.times = 50;
		for(int i=0 ; i<total ; i++)
		{
			best[i] = init[i] = data.sales[whichSales][i];
		}
		
		data.iter[whichSales] = 0;
		// compute initial cost for initial solution
		// set bestCost = inital cost
		bestCost = compute(init,whichSales);
		
		while(temper > 0.00001)
		{
			data.iter[whichSales]++;
			for(int i=0 ; i<data.times ; i++)
			{
				getNext(init,next,whichSales);
				nowCost = compute(init,whichSales);
				laterCost = compute(next,whichSales);
				delta = laterCost - nowCost;
				p = Math.abs((double)(rn.nextInt() % 100001)/100000);
				
				if(delta < 0 || Math.exp((-delta)/temper)>p)
				{
					for(int j=0;j<total;j++)
					{
						init[j] = next[j];
					}
					nowCost = nowCost+delta;
				}
				if(nowCost < bestCost)
				{
					bestCost = nowCost;
					for(int j=0;j<total;j++)
					{
						best[j] = next[j];
					}
				}
			}
			temper *= 0.9999;

		}
		for(int i=0 ; i<total ;i++)
		{
			data.path[whichSales][i] = best[i];
			//System.out.printf("%d ",best[i] );
		}
		//System.out.println();
		data.results[whichSales] = compute(best,whichSales);
	}
	
	
	/**
	 *  change the solution sequence for a bit
	 */
	
	public void getNext(int init[], int next[], int whichSales)
	{
		
		int total = data.indexs[whichSales];
		Random rn = new Random();
		int r1 = Math.abs(rn.nextInt()%total);
		int r2 = Math.abs(rn.nextInt()%total);
		
		for(int i=0 ; i<total ; i++)
			next[i] = init[i];
		
		next[r1] = init[r2];
		next[r2] = init[r1];
		
		
		
	}
	
	/**
	 * compute the current cost for the salesman No.whichSales
	 * then return the cost value
	 * @param list
	 * @param whichSales
	 * @return
	 */
	
	public double compute(int list[], int whichSales)
	{
		int total = data.indexs[whichSales];
		double result = 0;
		
		for(int i=0;i<total-1;i++)
	    {
	        result += dist(list[i],list[i+1]);
	    }
		// add the distance from the depot to the first and last node
		result += distNodeToDepot(list[0],whichSales);
		result += distNodeToDepot(list[total-1],whichSales);
		return result;
	}
	/**
	 *  compute the distance of a node to a depot
	 */
	public double distNodeToDepot(int nodeNum , int salesNum)
	{
		int xSquare , ySquare;
		
		xSquare = data.depot[salesNum][0] - data.nodes[nodeNum][0];
		ySquare = data.depot[salesNum][1] - data.nodes[nodeNum][1];
		xSquare *= xSquare;
		ySquare *= ySquare;
		
		return  Math.sqrt(xSquare + ySquare);
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
	
}
