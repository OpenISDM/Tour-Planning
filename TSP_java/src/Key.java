import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * the Key handler class
 * @author linbe
 *
 */
public class Key extends KeyAdapter {
    
	/**
	 * used as a link to the Main Class, allow this class to update important informations
	 */
	private Main demo;
	public String clusteringMethod;
	
	
	/**
	 * link the Main Class, allow this class to update important informations
	 */
    public Key(Main demo) {
        this.demo = demo;
    }
    
   /**
    * handling key pressing actions
    */
    public void keyPressed(KeyEvent e) {
        
    	
    	
    		
        // orginal cluster
        if(e.getKeyCode() == KeyEvent.VK_C)
        {
        	demo.cluster.main(demo.cluster);
        	clusteringMethod = "or";
        	demo.clus = true;
        	demo.bru=false;
        	demo.beterClus=false;
        	demo.readOutPut = false;
        	demo.readIP = false;
        	demo.which=0; 
        	demo.mode = 0;
        	demo.repaint();
        }
        // mix clusteirng
        else if(e.getKeyCode() == KeyEvent.VK_M)
        {
        	clusteringMethod = "b4";
        	demo.better4.main(demo.better4);
        	demo.clus = true;
        	demo.beterClus=true;
        	demo.bru=false;
        	demo.readOutPut = false;
        	demo.readIP = false;
        	demo.which=0; 
        	demo.mode = 4;
        	demo.repaint();
        }
        /*
        else if(e.getKeyCode() == KeyEvent.VK_T)
        {
        	clusteringMethod = "tt";
        	demo.newTest.main(demo.newTest);
        	demo.clus = true;
        	demo.beterClus=false;
        	demo.bru=false;
        	demo.readOutPut = false;
        	demo.readIP = false;
        	demo.which=0; 
        	demo.mode = 5;
        	demo.repaint();
        }
        */
        // circle clustering
        else if(e.getKeyCode() == KeyEvent.VK_A)
        {
        	clusteringMethod = "co";
        	demo.circle.main(demo.circle);
        	demo.clus = true;
        	demo.beterClus=true;
        	demo.bru=false;
        	demo.readOutPut = false;
        	demo.readIP = false;
        	demo.which=0; 
        	demo.mode = 6;
        	demo.repaint();
        }
        
        
        else if(e.getKeyCode() == KeyEvent.VK_D)
        {
        	
        	demo.clus=false;
        	demo.bru=false;
        	for(int i=1 ; i<demo.data.SALESMAN ; i++)
        		demo.select[i] = false;
        	demo.readOutPut = false;
        	demo.readIP = false;
        	demo.which=0;
        	demo.mode = 0;
        	String temp[] = new String[1];
        	temp[0] = "linbe";
        	//demo.main(temp);
        	demo.repaint();
        }
        
        // depot 1 show or not
        else if(e.getKeyCode() == KeyEvent.VK_1)
        {
        	if(demo.select[1]==true)
        		demo.select[1] = false;
        	else
        		demo.select[1] = true;
        	demo.repaint();
        }
        // depot 2 show or not
        else if(e.getKeyCode() == KeyEvent.VK_2)
        {
        	if(demo.select[2]==true)
        		demo.select[2] = false;
        	else
        		demo.select[2] = true;
        	demo.repaint();
        }
        // depot 3 show or not
        else if(e.getKeyCode() == KeyEvent.VK_3)
        {
        	if(demo.select[3]==true)
        		demo.select[3] = false;
        	else
        		demo.select[3] = true;
        	demo.repaint();
        }
        // depot 4 show or not
        else if(e.getKeyCode() == KeyEvent.VK_4)
        {
        	if(demo.select[4]==true)
        		demo.select[4] = false;
        	else
        		demo.select[4] = true;
        	demo.repaint();
        }
        // depot 5 show or not
        else if(e.getKeyCode() == KeyEvent.VK_5)
        {
        	if(demo.select[5]==true)
        		demo.select[5] = false;
        	else
        		demo.select[5] = true;
        	demo.repaint();
        }
        // depot 6 show or not
        else if(e.getKeyCode() == KeyEvent.VK_6)
        {
        	if(demo.select[6]==true)
        		demo.select[6] = false;
        	else
        		demo.select[6] = true;
        	demo.repaint();
        }
        // dump out lkh input file then solve it
        // and show the results on screen
        else if(e.getKeyCode() == KeyEvent.VK_P)
        {
        	
        	try {
        		System.out.println();
				demo.data.dumper(clusteringMethod,demo.testCaseNum);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	try {
        		
				FileWriter lkhFile = new FileWriter("lkh.bat",false);
				BufferedWriter lkhBuf = new BufferedWriter(lkhFile);
				for(int i=1 ; i<demo.data.SALESMAN;i++)
				{
					System.out.println("JENNIFER   "+demo.data.SALESMAN);
					lkhBuf.write("lkh.exe parafile_"+clusteringMethod+"_"+demo.testCaseNum+"_"+i+".txt");
					lkhBuf.newLine();
				}
				lkhBuf.flush();
				lkhBuf.close();
				String cmd[] = new String[3];
	    		cmd[0] = "cmd";
	    		cmd[1] = "/C";
	    		cmd[2] = "lkh.bat";
	    		Process p = Runtime.getRuntime().exec(cmd);
	    		
	    		try {
					p.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		p.destroy();
	    		
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	demo.readOutPut = true;
        	try {
				demo.data.lkhReader(clusteringMethod, demo.testCase);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        }
        // key in file name and output the testcase
        else if(e.getKeyCode() == KeyEvent.VK_F)
        {
        	demo.data.printer();
        	Scanner sn = new Scanner(System.in);
        	System.out.println("Key in input file name: ");
        	String name = sn.next();
        	demo.testCase = name.substring(0,name.length()-4);
        	demo.testCaseNum = Integer.parseInt(demo.testCase);
        	try {
				demo.data.shooter(name);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        // read and show results of lkh 
        else if(e.getKeyCode() == KeyEvent.VK_L)
        {
        	demo.readOutPut = true;
        	try {
				demo.data.lkhReader(clusteringMethod, demo.testCase);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	demo.repaint();
        }
        
        // read and show results of lkh 
        else if(e.getKeyCode() == KeyEvent.VK_J)
        {
        	demo.clus = true;
        	demo.beterClus=true;
        	demo.bru=false;
        	
        	demo.readIP = false;
        	demo.which=0; 
        	demo.mode = 6;
        	demo.repaint();
        	// perform gp
    		// new gp class
    		GPmain gp = new GPmain();
    		// run a function of gp
			try {
				gp.gpGo(demo.testCaseNum);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        			
        	// read gp output and produce lkh file
        	try {
				demo.data.GPMaster(demo.testCaseNum);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	
        	// show lkh
			demo.readOutPut = true;
	        try {
				demo.data.lkhReader("GP", demo.testCase);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
        // show the testcase to IP , use gurobi to solve
        // then read and show the result
        else if(e.getKeyCode() == KeyEvent.VK_B)
        {
        	Ip ip = new Ip();
        	String in = demo.testCase+".txt";
        	try {
				ip.IPgogo(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	
        	demo.readIP = true;
        	System.out.println("here "+demo.testCase);
        	String ipOut = demo.testCase+"out.txt";
        	System.out.println("ipOut string ==== "+ipOut);
        	try {
				demo.data.ipReader(ipOut);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	demo.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_G)
        {
        	System.out.println("outputint gams formulation");
        	
        	Gams gams = new Gams();
        	String in = demo.testCase+".txt";
        	try {
				gams.GamsGo(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}       	
        }
        
        else if(e.getKeyCode() == KeyEvent.VK_Y)
        {
        	System.out.println("outputint gams for general formulation");
        	
        	Gams gams = new Gams();
        	String in = demo.testCase+".txt";
        	try {
				gams.GamsGoForGen(in, demo.data);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}       	
        }
        
        /*
        else if(e.getKeyCode() == KeyEvent.VK_B)
        {
        	for(int i=1;i<demo.data.SALESMAN;i++)
        	{	
        		demo.brute.bruteForce(demo.data.indexs[i], i, 0);
        	}
        	demo.which = demo.data.SALESMAN;
        	demo.bru = true;
        	demo.repaint();
        }
        */
        
        
    }
    /**
     * handling key released actions
     */
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }
}