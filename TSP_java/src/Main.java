import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This is the Main class
 * @author William
 * @version 1.0
 */
public class Main extends JFrame{	
	
	/**
	 *  data the datastructure set used in the static main function
	 */
    private static Data dat;
    /**
     *  a cluster object used to do clustering
     */
    public Cluster cluster;
    /**
     *  a better cluster
     */
    public BetterCluster better;
    /**
     *  another object used to do another form of clustering
     */
    public Other other;
    /**
     *  inturns clustering class with centroids
     */
    public Inturns inturns;
    /**
     *  inturns clustering class with centroids
     */
    public NewInTurns newInTurns;
    public New newTest;
    /**
     * indicating it's in clutering mode
     */
    public boolean clus = false;
    /**
     *  indicating it's in bruteforce mode
     */
    public boolean bru = false;
    /**
     *  indicating it's in addNode mode
     */
    public boolean addNode = false;
    /**
     *  indicating it's in addSales mode
     */
    public boolean addSales = false;
    /**
     *  indicating the amount of k
     */
    public int kAmount = 1;
    
    int xBoarderForResults = 1030;
	int xForButtons = 1400;
	int xOffSet = 20;
	int yOffSet = 40;
	
    public boolean beterClus = false;
    public boolean readOutPut = false;
    public boolean readIP = false;
    /**
     * an object used for bruteforce solution
     */
    /**
     *  SA
     */
    /**
     * public datastructure set
     */
    public Data data;
    /**
     *  determining which salesman's path are to be solved
     */
    public int which;
    /**
     *  determine selecting mode
     */
    public boolean select[];
    /**
     *  determine which is selected
     */
    public int swhich;
    /**
     *  int mode : 1 for original 2 for in turns;
     */
    public int mode;

    public BetterCluster4 better4;
    public Circle circle;
    
    public String testCase;
    public int testCaseNum;
    
    /**
     * Main's constructor : set's the setting of the Frame
     * @param data
     */
    public Main(Data data)
    {
    	
    	super("mTSP clutering solver");
        setSize((160*data.zoom+2)*2,160*data.zoom+120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.white);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        //this.setUndecorated(true);
        // Initialize member.
        // Register listener for key event.
        Key handler = new Key(this);
        addKeyListener(handler);
        Mouse mouse = new Mouse(this);
        addMouseListener(mouse);
		addMouseMotionListener(mouse);
        select = new boolean [data.SALESMAN];
        
    	/*Container content = getContentPane();
    	content.add(jtest);
    	pack();*/
        setVisible(true);
        inturns = new Inturns(data);
        cluster = new Cluster(data);
        other = new Other(data);
    	better = new BetterCluster(data);
    	circle = new Circle(data);
    	
    	better4 = new BetterCluster4(data);
    	newInTurns = new NewInTurns(data);
    	newTest = new New(data);
    	this.data = data;
    	which = 0;
    	swhich = 0;
    	mode = 0;
    }
    
    public static int depotTransX(int x)
    {
    	return (x*dat.zoom)-8;
    }
    
    public static int depotTransY(int y)
    {
    	return 3;
    } 
    /**
     * the paint method, updates the mTSP cluster and route
     */
    public void paint(Graphics g) {
        
    	int x , y;
    	
    	
    	
		if(!clus)
    	{
    		//g.setColor(Color.red);
	    	g.clearRect(0, 0, getWidth(), getHeight());
	    	//g.drawLine(1, 160*dat.zoom+1, 160*dat.zoom+1 , 160*dat.zoom+1);
	    	//g.drawLine(1, 160*dat.zoom+1, 1 ,1);
	        //g.setColor(Color.red);
	    	
	    	// draw boarder
	    	g.setColor(Color.black);
	    	g.drawLine(20, 40, 20, 840);
	    	g.drawLine(20, 40, 820, 40);
	    	g.drawLine(20, 840, 820, 840);
	    	g.drawLine(820, 40, 820, 840);
	    	
	    	// draw buttons
	    	
	    	// K-means
	    	g.setColor(Color.CYAN);
	    	g.fillRect(xForButtons,80,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("K-means",Font.BOLD,4*dat.zoom));
			g.drawString("K-means", xForButtons+5, 110);
	    	
			// Circle Original
			g.setColor(Color.CYAN);
	    	g.fillRect(xForButtons,150,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("Circle",Font.BOLD,4*dat.zoom));
			g.drawString("Circle", xForButtons+5, 180);
	    	
			// Add nodes
			g.setColor(Color.CYAN);
			if (addNode)
				g.setColor(Color.RED);
	    	g.fillRect(xForButtons,220,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("AddNode",Font.BOLD,4*dat.zoom));
			g.drawString("AddNode", xForButtons+5, 250);
			
			// K amount
			g.setColor(Color.CYAN);
	    	g.fillRect(xForButtons+110,220,50,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("K="+kAmount,Font.BOLD,4*dat.zoom));
			g.drawString("K="+kAmount, xForButtons+115, 250);
			
			
			// Add participants
			g.setColor(Color.CYAN);
			if (addSales)
				g.setColor(Color.RED);
	    	g.fillRect(1350,290,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("AddSales",Font.BOLD,4*dat.zoom));
			g.drawString("AddSales", 1355, 320);
			
			
			// draw salesmen
	        for(int i=1 ; i<dat.SALESMAN ; i++)
	        {	
		        x = (dat.depot[i][0]*dat.zoom)-8;
		        y = (160*dat.zoom-(dat.depot[i][1]*dat.zoom))-8;
		        g.fillOval(x+xOffSet, y+yOffSet, 16, 16);
	        }
	        
	        
	        // draw nodes
	        g.setColor(Color.black);
	        for(int i=0 ; i<dat.TOTALNODES+1 ; i++)
	        {
	        	x = (dat.nodes[i][0]*dat.zoom)-5;
	            y = (160*dat.zoom-(dat.nodes[i][1]*dat.zoom))-5;
	            g.fillOval(x+xOffSet, y+yOffSet, 10, 10);
	            
	            String ind = ""+i;
	    		g.setFont(new Font(ind,Font.PLAIN,12));
	    		g.drawString(ind,x+xOffSet,y+yOffSet-10);
	        }
	        
	        
	        g.setColor(new Color(180 , 10 , 180));
	        for(int i=dat.TOTALNODES + 1;i<dat.TOTALNODES + dat.SALESMAN ;i++)
	        {
	        	x = (dat.nodes[i][0]*dat.zoom)-7;
	            y = (160*dat.zoom-(dat.nodes[i][1]*dat.zoom))-7;
	            g.drawRect(x+xOffSet, y+yOffSet, 14, 14);
	        }
	        
	        for(int i=1 ; i<dat.SALESMAN ; i++)
	        {
	        	x = (dat.centroids[i][0]*dat.zoom)-3;
	        	y = (160*dat.zoom-(dat.centroids[i][1]*dat.zoom))-3;
	        	g.fillRect(x+xOffSet, y+yOffSet, 6, 6);
	        }
	        
	        
    	}
		
		
    	// if the GUI is in any kind of clustering mode
    	else if(clus)
    	{
    		g.clearRect(0, 0, getWidth(), getHeight());
    		//g.drawLine(1, 160*dat.zoom+1, 160*dat.zoom+1 , 160*dat.zoom+1);
    		
    		// draw boarder
	    	g.setColor(Color.black);
	    	g.drawLine(20, 40, 20, 840);
	    	g.drawLine(20, 40, 820, 40);
	    	g.drawLine(20, 840, 820, 840);
	    	g.drawLine(820, 40, 820, 840);
	    	
    		// draw buttons
	    	
	    	// K-means
	    	g.setColor(Color.CYAN);
	    	g.fillRect(xForButtons,80,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("K-means",Font.BOLD,4*dat.zoom));
			g.drawString("K-means", xForButtons+5, 110);
	    	
			// Circle Original
			g.setColor(Color.CYAN);
	    	g.fillRect(xForButtons,150,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("Circle",Font.BOLD,4*dat.zoom));
			g.drawString("Circle", xForButtons+5, 180);
	    	
			// Add nodes
			g.setColor(Color.CYAN);
			if (addNode)
				g.setColor(Color.RED);
	    	g.fillRect(xForButtons,220,90,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("AddNode",Font.BOLD,4*dat.zoom));
			g.drawString("AddNode", xForButtons+5, 250);
			
			// K amount
			g.setColor(Color.CYAN);
	    	g.fillRect(xForButtons+110,220,50,50);
	    	g.setColor(Color.black);	
			g.setFont(new Font("K="+kAmount,Font.BOLD,4*dat.zoom));
			g.drawString("K="+kAmount, xForButtons+115, 250);
			
			
 	        // draw the bigger ovals for salesmen
    		for(int i=1 ; i<dat.SALESMAN ; i++)
	        {	
    			switch(i)
				{
					case 1: g.setColor(Color.blue);break;
					case 2: g.setColor(Color.magenta);break;
					case 3: g.setColor(Color.pink);break;
					case 4: g.setColor(Color.black);break;
					case 5: g.setColor(Color.green);break;
					case 6: g.setColor(Color.red); break;
					default: break;
				}
    			x = (dat.depot[i][0]*dat.zoom)-9;
		        y = (160*dat.zoom-(dat.depot[i][1]*dat.zoom))-9;
		        g.fillOval(x+xOffSet, y+yOffSet, 18, 18);
	        }
	        
    		
    		
	        // draw centroids
	        for(int i=1;i<dat.SALESMAN;i++)
	        {
	        	switch(i)
				{
					case 1: g.setColor(Color.blue);break;
					case 2: g.setColor(Color.magenta);break;
					case 3: g.setColor(Color.pink);break;
					case 4: g.setColor(Color.black);break;
					case 5: g.setColor(Color.green);break;
					case 6: g.setColor(Color.red); break;
					default: break;
				}
	        	x = (dat.centroids[i][0]*dat.zoom)-7;
	            y = (160*dat.zoom-(dat.centroids[i][1]*dat.zoom))-7;
	            g.drawRect(x+xOffSet, y+yOffSet, 14, 14);
	        }
	        
	        // color the nodes assigned to each salesman
    		for(int i=1 ; i<dat.SALESMAN ; i++)
    		{
    			if(select[i]==true)
    				continue;
    			switch(i)
				{
					case 1: g.setColor(Color.blue);break;
					case 2: g.setColor(Color.magenta);break;
					case 3: g.setColor(Color.pink);break;
					case 4: g.setColor(Color.black);break;
					case 5: g.setColor(Color.green);break;
					case 6: g.setColor(Color.red); break;
					default: break;
				}
    			for(int j=0;j<dat.indexs[i];j++)
    			{
    				if(dat.sales[i][0]==0)
    				{
    					break;
    				}
    				else
    				{
    					x = (dat.nodes[dat.sales[i][j]][0]*dat.zoom)-5;
	    	            y = (160*dat.zoom-(dat.nodes[dat.sales[i][j]][1]*dat.zoom))-5;
	    	            g.fillOval(x+xOffSet, y+yOffSet, 10, 10);
	    	           
	    	            String ind = ""+dat.sales[i][j];
	    	    		g.setFont(new Font(ind,Font.PLAIN,12));
	    	    		g.drawString(ind,x+xOffSet,y+yOffSet-10);
    				}
    			}
    		}
    		
    		// draw and specifies the movement of the centroid
    		// pretending the initial centroid to be the depot
    		double maxMove = 0;
    		for(int i=1 ; i<dat.SALESMAN ; i++)
    		{
    			int tx1,tx2,ty1,ty2;
    			
    			switch(i)
				{
					case 1: g.setColor(Color.blue);break;
					case 2: g.setColor(Color.magenta);break;
					case 3: g.setColor(Color.pink);break;
					case 4: g.setColor(Color.black);break;
					case 5: g.setColor(Color.green);break;
					case 6: g.setColor(Color.red); break;
					default: break;
				}
    			tx1 = dat.depot[i][0]*dat.zoom;
    			ty1 = 160*dat.zoom - dat.depot[i][1]*dat.zoom;
    			tx2 = dat.centroids[i][0]*dat.zoom;
    			ty2 = 160*dat.zoom - dat.centroids[i][1]*dat.zoom;
    			ty1+=yOffSet;
    			ty2+=yOffSet;
    			tx1+=xOffSet;
    			tx2+=xOffSet;
    			g.drawLine(tx1, ty1, tx2, ty2);
    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
    			g.drawLine(tx1+1, ty1-1, tx2, ty2);
    			g.drawLine(tx1-1, ty1+1, tx2, ty2);
    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
    			g.drawLine(tx1, ty1, tx2+1, ty2-1);
    			g.drawLine(tx1, ty1, tx2-1, ty2+1);
    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
    			
    			String centMove = "";
    			
    			
    			double distMove = (int)dat.distGeneral(dat.depot[i][0], dat.depot[i][1], dat.centroids[i][0], dat.centroids[i][1]);
    			
    			centMove = ""+(int)distMove;
    			g.setFont(new Font(centMove,Font.BOLD,4*dat.zoom));
    			g.drawString(centMove, (dat.depot[i][0]-3)*dat.zoom, (160*dat.zoom-(dat.depot[i][1]+3)*dat.zoom));
    			if(distMove > maxMove)
    			{
    				maxMove = distMove;
    			}
    		}
    		
    		String maxMovement = "maxCentMove "+(int)maxMove;
    		g.setFont(new Font(maxMovement,Font.BOLD,27));
    		g.setColor(Color.red);
    		g.drawString(maxMovement,xBoarderForResults,220);
    		String iter = "iterations "+(int)dat.iterations;
    		g.setFont(new Font(maxMovement,Font.BOLD,27));
    		g.setColor(Color.red);
    		g.drawString(iter,xBoarderForResults+100,300);
    		
    		// draw circle for circle clustering methods
    		if(beterClus)
    		{
    			// circle and mix
	    		if(mode < 7)
	    		{
	    			for(int i=1 ; i<dat.SALESMAN ; i++)
			        {	
		    			switch(i)
						{
							case 1: g.setColor(Color.blue);break;
							case 2: g.setColor(Color.magenta);break;
							case 3: g.setColor(Color.PINK);break;
							case 4: g.setColor(Color.black);break;
							case 5: g.setColor(Color.green);break;
							case 6: g.setColor(Color.red); break;
							default: break;
						}
		    			x = (dat.depot[i][0]*dat.zoom)-(dat.dValue[i])*dat.zoom;
				        y = (160*dat.zoom-(dat.depot[i][1]*dat.zoom))-(dat.dValue[i])*dat.zoom;
				        g.drawOval(x+xOffSet, y+yOffSet, 2*dat.dValue[i]*dat.zoom, 2*dat.dValue[i]*dat.zoom);
			        }
	    		}
	    		else if(mode == 7)
	    		{
	    			for(int i=1 ; i<dat.SALESMAN ; i++)
			        {	
		    			switch(i)
						{
							case 1: g.setColor(Color.blue);break;
							case 2: g.setColor(Color.magenta);break;
							case 3: g.setColor(Color.PINK);break;
							case 4: g.setColor(Color.black);break;
							case 5: g.setColor(Color.green);break;
							case 6: g.setColor(Color.red); break;
							default: break;
						}
		    			x = (dat.centroids[i][0]*dat.zoom)-(dat.dValue[i])*dat.zoom;
				        y = (160*dat.zoom-(dat.centroids[i][1]*dat.zoom))-(dat.dValue[i])*dat.zoom;
				        g.drawOval(x+xOffSet, y+yOffSet, 2*dat.dValue[i]*dat.zoom, 2*dat.dValue[i]*dat.zoom);
			        }
	    		}
	    		else
	    		{
	    			for(int i=1 ; i<dat.SALESMAN ; i++)
			        {	
		    			switch(i)
						{
							case 1: g.setColor(Color.blue);break;
							case 2: g.setColor(Color.magenta);break;
							case 3: g.setColor(Color.PINK);break;
							case 4: g.setColor(Color.black);break;
							case 5: g.setColor(Color.green);break;
							case 6: g.setColor(Color.red); break;
							default: break;
						}
		    			x = (((dat.centroids[i][0]+dat.depot[i][0])/2)*dat.zoom)-(dat.dValue[i])*dat.zoom;
				        y = (160*dat.zoom-(((dat.centroids[i][1]+dat.depot[i][1])/2)*dat.zoom))-(dat.dValue[i])*dat.zoom;
				        g.drawOval(x+xOffSet, y+yOffSet, 2*dat.dValue[i]*dat.zoom, 2*dat.dValue[i]*dat.zoom);
			        }
	    		}
    		}// end of drawing circles
    		
    		
    		// the LKH tour case
    		if(readOutPut)
    		{
    			int tx1 , tx2 , ty1 , ty2;
    			
    			
    			
    			for(int i=1 ; i<dat.SALESMAN ; i++)
    			{
    				if(select[i]==true)
        				continue;
    				switch(i)
        			{
        				case 1: g.setColor(Color.blue);break;
        				case 2: g.setColor(Color.magenta);break;
        				case 3: g.setColor(Color.pink);break;
        				case 4: g.setColor(Color.black);break;
        				case 5: g.setColor(Color.green);break;
        				case 6: g.setColor(Color.red); break;
        				default: break;
        			}
    				
    				for(int j=1 ; j<=dat.probIndex[i] ; j++)
    				{
    					
    					if(j+1<=dat.probIndex[i])
    					{
	    					// node 1
	    					tx1 = (dat.lkhNodes[i][dat.path[i][j]][0]*dat.zoom);
	    	    			ty1 = (160*dat.zoom-(dat.lkhNodes[i][dat.path[i][j]][1]*dat.zoom));
	    	    			// node 2
	    	    			tx2 = (dat.lkhNodes[i][dat.path[i][j+1]][0]*dat.zoom);
	    	    			ty2 = (160*dat.zoom-(dat.lkhNodes[i][dat.path[i][j+1]][1]*dat.zoom));
	    	    			
	    	    			ty1 += yOffSet;
	    	    			ty2 += yOffSet;
	    	    			tx1 += xOffSet;
	    	    			tx2 += xOffSet;
	    	    			
	    	    			g.drawLine(tx1, ty1, tx2, ty2);
	    	    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
	    	    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
	    	    			g.drawLine(tx1+1, ty1-1, tx2, ty2);
	    	    			g.drawLine(tx1-1, ty1+1, tx2, ty2);
	    	    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1, ty1, tx2+1, ty2-1);
	    	    			g.drawLine(tx1, ty1, tx2-1, ty2+1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
    					}
    					else
    					{
    						// node 1
	    					tx1 = (dat.lkhNodes[i][dat.path[i][j]][0]*dat.zoom);
	    	    			ty1 = (160*dat.zoom-(dat.lkhNodes[i][dat.path[i][j]][1]*dat.zoom));
	    	    			// node 2
	    	    			tx2 = (dat.lkhNodes[i][dat.path[i][1]][0]*dat.zoom);
	    	    			ty2 = (160*dat.zoom-(dat.lkhNodes[i][dat.path[i][1]][1]*dat.zoom));
	    	    			
	    	    			ty1 += yOffSet;
	    	    			ty2 += yOffSet;
	    	    			tx1 += xOffSet;
	    	    			tx2 += xOffSet;
	    	    			
	    	    			g.drawLine(tx1, ty1, tx2, ty2);
	    	    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
	    	    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
	    	    			g.drawLine(tx1+1, ty1-1, tx2, ty2);
	    	    			g.drawLine(tx1-1, ty1+1, tx2, ty2);
	    	    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1, ty1, tx2+1, ty2-1);
	    	    			g.drawLine(tx1, ty1, tx2-1, ty2+1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
    					}
    					
    					
    				}
    				
    				// show the results on top of each centroids
    				String str;
	    			str = dat.results[i] + "";
	    			g.setFont(new Font(str,Font.BOLD,4*dat.zoom));
	    			g.drawString(str, (dat.centroids[i][0]-3)*dat.zoom, (160*dat.zoom-(dat.centroids[i][1]+3)*dat.zoom));
    				
    			}
    			
    			double total = 0;
        		double min = 99999999.99999;
        		double max = 0;
        		int maxt = 0, mint = 0;
    			for(int k=1;k<dat.SALESMAN;k++)
    			{
    				total+=dat.results[k];
    				if(dat.results[k]>=max)
    				{
    					max = dat.results[k];
    					maxt = k;
    				}
    				if(dat.results[k]<=min)
    				{
    					min = dat.results[k];
    					mint = k;
    				}
    			}
    			
    			g.setColor(Color.red);
    			String str = null;
    			String s1,s2,s3,s4 = null;
    			str = "Total ="+(int)total;
    			s1 = "Max = "+(int)max;
    			s2 = "Min = "+(int)min;
    			s3 = "Delta ="+(int)(max-min);
    			
    			g.setFont(new Font(str,Font.BOLD,27));
    			if(mode==0)
    			{
    				s4 = "K-means Clustering";
    			}
    			else if(mode == 1)
    			{
    				s4 = "Better with assignment";
    			}
    			else if(mode == 2)
    			{
    				s4 = "Take Turn with Centroids";
    			}
    			else if(mode == 3)
    			{
    				s4 = "Original with assignment";
    			}
    			else if(mode == 4)
    			{
    				s4 = "Mix clustering";
    			}
    			else if(mode == 5)
    			{
    				s4 = "Take Turns";
    			}
    			else if(mode == 6)
    			{
    				s4 = "Circle clustering";
    			}
    			else if(mode == 7)
    			{
    				s4 = "Circle With Centroid";
    			}
    			else if(mode == 8)
    			{
    				s4 = "Circle With Cent and Dep";
    			}
    			g.drawString(str,xBoarderForResults,100);
    			g.drawString(s1,xBoarderForResults,130);
    			g.drawString(s2,xBoarderForResults,160);
    			g.drawString(s3,xBoarderForResults,190);
    			g.drawString(s4, xBoarderForResults, 70);
    			
    			String index = "";
    			g.setFont(new Font(index,Font.BOLD,23));
    			for(int i=1 ; i<dat.SALESMAN ; i++)
    			{
    				switch(i)
    				{
	    				case 1: g.setColor(Color.blue);break;
	    				case 2: g.setColor(Color.magenta);break;
	    				case 3: g.setColor(Color.pink);break;
	    				case 4: g.setColor(Color.black);break;
	    				case 5: g.setColor(Color.green);break;
	    				case 6: g.setColor(Color.red); break;
	    				default: break;
	    			}
    				
    				g.fillOval(xBoarderForResults+20, 210+30*i, 14, 14);
    				index = ""+(int)i;
    				g.drawString(index, xBoarderForResults, 225+30*i);
    				index = ""+(int)dat.results[i]; 
    				g.drawString(index, xBoarderForResults+40, 225+30*i);
    				
    				
    			}
    			
    		}// end of reading LKH output
    		
    		// else if reading IP output
    		if(readIP)
    		{
    			// paint reading IP
    			int tx1 , tx2 , ty1 , ty2;
    			int n1, n2;
    			for(int i=1 ; i<dat.SALESMAN ; i++)
    			{
    				switch(i)
    				{
	    				case 1: g.setColor(Color.blue);break;
	    				case 2: g.setColor(Color.magenta);break;
	    				case 3: g.setColor(Color.pink);break;
	    				case 4: g.setColor(Color.black);break;
	    				case 5: g.setColor(Color.green);break;
	    				case 6: g.setColor(Color.red); break;
	    				default: break;
	    			}
    				
    				System.out.println("Salesman No."+i+" SALESMAN == "+dat.SALESMAN);
    				
    				for(int j=0; j<dat.ipEdgeNum[i] ; j++)
    				{	
    					
    					n1 = dat.ipPath[i][j][0];
    					n2 = dat.ipPath[i][j][1];
    					System.out.println(n1+" "+n2);
    					// node 1
    					// if it's depot
    					// in ip path sales starts from 0
    					if(n1<dat.SALESMAN-1)
    					{
    						n1++;
    						tx1 = (dat.depot[n1][0]*dat.zoom);
    		    			ty1 = (160*dat.zoom-(dat.depot[n1][1]*dat.zoom));
    					}
    					else
    					{
    						n1 = n1 - dat.SALESMAN+2;
    	    				tx1 = (dat.nodes[n1][0]*dat.zoom);
    		    			ty1 = (160*dat.zoom-(dat.nodes[n1][1]*dat.zoom));
    					}
    					
    					// node 2
    					if(n2<dat.SALESMAN-1)
    					{
    						n2++;
    						tx2 = (dat.depot[n2][0]*dat.zoom);
    		    			ty2 = (160*dat.zoom-(dat.depot[n2][1]*dat.zoom));
    					}
    					else
    					{
    						n2 = n2 - dat.SALESMAN+2;
    						tx2 = (dat.nodes[n2][0]*dat.zoom);
    		    			ty2 = (160*dat.zoom-(dat.nodes[n2][1]*dat.zoom));
    					}
    					
		    			
		    			
		    			ty1 += yOffSet;
		    			ty2 += yOffSet;
		    			tx1 += xOffSet;
		    			tx2 += xOffSet;
		    			
		    			g.drawLine(tx1, ty1, tx2, ty2);
		    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
		    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
		    			g.drawLine(tx1+1, ty1-1, tx2, ty2);
		    			g.drawLine(tx1-1, ty1+1, tx2, ty2);
		    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
		    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
		    			g.drawLine(tx1, ty1, tx2+1, ty2-1);
		    			g.drawLine(tx1, ty1, tx2-1, ty2+1);
		    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
		    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
		    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
		    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
    				}
    				// show the results on top of each centroids
    				String str;
	    			str = dat.results[i] + "";
	    			g.setFont(new Font(str,Font.BOLD,4*dat.zoom));
	    			g.drawString(str, (dat.centroids[i][0]-3)*dat.zoom, (160*dat.zoom-(dat.centroids[i][1]+3)*dat.zoom));
    			}
    			
    			double total = 0;
        		double min = 99999999.99999;
        		double max = 0;
        		int maxt = 0, mint = 0;
    			for(int k=1;k<dat.SALESMAN;k++)
    			{
    				total+=dat.results[k];
    				if(dat.results[k]>=max)
    				{
    					max = dat.results[k];
    					maxt = k;
    				}
    				if(dat.results[k]<=min)
    				{
    					min = dat.results[k];
    					mint = k;
    				}
    			}
    			g.setColor(Color.red);
    			String str = null;
    			String s1,s2,s3 = null;
    			str = "Total ="+(int)total;
    			s1 = "Max = "+(int)max;
    			s2 = "Min = "+(int)min;
    			s3 = "Delta ="+(int)(max-min);
    			
    			g.setFont(new Font(str,Font.BOLD,27));
    			
    			g.drawString(str,xBoarderForResults,100);
    			g.drawString(s1,xBoarderForResults,130);
    			g.drawString(s2,xBoarderForResults,160);
    			g.drawString(s3,xBoarderForResults,190);
    			
    		}
    		
    		
    		
    		// the brute force and SA painting
    		else if (bru && which == dat.SALESMAN)
    		{
    			int tx1 , tx2 , ty1 , ty2;
        		
        		for(int j=1 ; j<dat.SALESMAN ; j++)
        		{
        			switch(j)
        			{
        				case 1: g.setColor(Color.blue);break;
        				case 2: g.setColor(Color.magenta);break;
        				case 3: g.setColor(Color.pink);break;
        				case 4: g.setColor(Color.black);break;
        				case 5: g.setColor(Color.green);break;
        				case 6: g.setColor(Color.red); break;
        				default: break;
        			}
        			
        			
        			for(int i = 0; i < dat.indexs[j] ; i++)
	        		{
	        			if(i+1 < dat.indexs[j])
	        			{
	    	    			tx1 = (dat.nodes[dat.path[j][i]][0]*dat.zoom);
	    	    			ty1 = (160*dat.zoom-(dat.nodes[dat.path[j][i]][1]*dat.zoom));
	    	    			tx2 = (dat.nodes[dat.path[j][i+1]][0]*dat.zoom);
	    	    			ty2 = (160*dat.zoom-(dat.nodes[dat.path[j][i+1]][1]*dat.zoom));
	    	    			g.drawLine(tx1, ty1, tx2, ty2);
	    	    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
	    	    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
	    	    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
	        			}
	        			else
	        			{
	        				tx1 = (dat.nodes[dat.path[j][i]][0]*dat.zoom);
	    	    			ty1 = (160*dat.zoom-(dat.nodes[dat.path[j][i]][1]*dat.zoom));
	    	    			tx2 = (dat.depot[j][0]*dat.zoom);
	    	    			ty2 = (160*dat.zoom-(dat.depot[j][1]*dat.zoom));
	    	    			g.drawLine(tx1, ty1, tx2, ty2);
	    	    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
	    	    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
	    	    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
	    	    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
	    	    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
	        			}
	        		}
	        		tx1 = (dat.nodes[dat.path[j][0]][0]*dat.zoom);
	    			ty1 = (160*dat.zoom-(dat.nodes[dat.path[j][0]][1]*dat.zoom));
	    			tx2 = (dat.depot[j][0]*dat.zoom);
	    			ty2 = (160*dat.zoom-(dat.depot[j][1]*dat.zoom));
	    			g.drawLine(tx1, ty1, tx2, ty2);
	    			g.drawLine(tx1+1, ty1+1, tx2, ty2);
	    			g.drawLine(tx1-1, ty1-1, tx2, ty2);
	    			g.drawLine(tx1, ty1, tx2+1, ty2+1);
	    			g.drawLine(tx1, ty1, tx2-1, ty2-1);
	    			g.drawLine(tx1+1, ty1+1, tx2+1, ty2+1);
	    			g.drawLine(tx1-1, ty1-1, tx2-1, ty2-1);
	    			g.drawLine(tx1-1, ty1-1, tx2+1, ty2+1);
	    			g.drawLine(tx1+1, ty1+1, tx2-1, ty2-1);
	    			String str;
	    			str = dat.results[j] + "";
	    			g.setFont(new Font(str,Font.BOLD,4*dat.zoom));
	    			g.drawString(str, (dat.centroids[j][0]-3)*dat.zoom, (160*dat.zoom-(dat.centroids[j][1]+3)*dat.zoom));
	    			
        		}
        		
        		double total = 0;
        		double min = 99999999.99999;
        		double max = 0;
        		int maxt = 0, mint = 0;
    			for(int k=1;k<dat.SALESMAN;k++)
    			{
    				total+=dat.results[k];
    				if(dat.results[k]>=max)
    				{
    					max = dat.results[k];
    					maxt = k;
    				}
    				if(dat.results[k]<=min)
    				{
    					min = dat.results[k];
    					mint = k;
    				}
    			}
    			
    			g.setColor(Color.red);
    			String str,s1,s2,s3,s4;
    			str = "Total ="+(int)total;
    			s1 = "Max = "+(int)max;
    			s2 = "Min = "+(int)min;
    			s3 = "Delta ="+(int)(max-min);
    			
    			g.setFont(new Font(str,Font.BOLD,27));
    			if(mode==1)
    			{
    				s4 = "Original Cluster";
    				g.drawString(s4, 20, 790);
    			}
    			else if(mode == 2)
    			{
    				s4 = "In Turns";
    				g.drawString(s4, 20, 790);
    			}
    			g.drawString(str,20,815);
    			g.drawString(s1,20,840);
    			g.drawString(s2,20,865);
    			g.drawString(s3,378,817);
    			
    		}
    	}
    }
    
    public static Data parser(String[] args) throws IOException
    {
    	
		/**
		 *   Input Format,
		 *   total nodes + 1
		 * 	 num of salesmen +1
		 *	 frame size * zoom
		 *	 k value
		 *	 temper
		 *	 times
		 *	 centroids // num of salesmen of them
		 *	 depots    // num of salesmen of them
		 *	 nodes // starting from node No.1 to total number of nodes
		 */

    	File file = new File(args[0]);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Data data = new Data(sc.nextInt(),sc.nextInt());
		data.zoom = sc.nextInt();
		data.k = sc.nextInt();
		
		// centroids
		for(int i=1 ; i<data.SALESMAN ; i++)
		{
			data.centroids[i][0] = sc.nextInt();
			data.centroids[i][1] = sc.nextInt();
		}
		
		// depots
		for(int i=1 ; i<data.SALESMAN ; i++)
		{
			data.nodes[data.TOTALNODES+i][0] = data.depot[i][0] = sc.nextInt();
			data.nodes[data.TOTALNODES+i][1] = data.depot[i][1] = sc.nextInt();
		}
		
		// nodes
		for(int i=1 ; i<data.TOTALNODES+1; i++)
		{
			data.nodes[i][0] = sc.nextInt();
			data.nodes[i][1] = sc.nextInt();
			data.nodes[i][2] = data.k;
			
			
		}
		// distance matrix
		for(int i=1 ; i<data.TOTALNODES+data.SALESMAN ; i++)
		{
			for(int j=1 ; j<data.TOTALNODES+data.SALESMAN ; j++)
			{
					data.distMatrix[i][j] = data.distGeneral(data.nodes[i][0],data.nodes[i][1] , data.nodes[j][0], data.nodes[j][1]);
			}
		}
		
		// computing cost matrix
		// short for cost matrix go 
		data.costco();
		// determining how many node copies there are
		data.copies = (data.TOTALNODES)*data.k;
		return data;
    }
    
	/**
	 * main function, parseing the Data
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		
		/**
		 *   Input Format,
		 *   total nodes + 1
		 * 	 num of salesmen +1
		 *	 frame size * zoom
		 *	 k value
		 *	 centroids // num of salesmen of them
		 *	 depots    // num of salesmen of them
		 *	 nodes // starting from node No.1 to total number of nodes
		 *   
		 *   
		 * 
		 */
		
		if (args[0].equals("linbe"))
		{
			int total , salesmanNum;
			System.out.println("Enter total number of nodes and number of sales");
			//Scanner scanIn = new Scanner(System.in);
			//total = scanIn.nextInt();
			//salesmanNum = scanIn.nextInt();
			total = 200; salesmanNum = 10;
			dat = new Data(total,salesmanNum);
			dat.zoom = 5;
			System.out.println("newing main");
			Main main = new Main(dat);
			System.out.println("DONE!!! newing main");
			dat.Nodebound = total;
			dat.TOTALNODES = 0;
			dat.SALESMAN = 1;
			main.testCaseNum = 0;
			main.testCase = "0";
		}
		else
		{
			dat = parser(args);
			Main main = new Main(dat);
			main.testCase = args[0];
			dat.Nodebound = dat.TOTALNODES;
			main.testCase = main.testCase.replace(".txt","");
			main.testCaseNum = Integer.parseInt(main.testCase);
			//dat.gx = true;
		}
		//main.better.main(main.better);
	}
}
