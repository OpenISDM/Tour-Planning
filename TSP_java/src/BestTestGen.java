import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class BestTestGen {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static double distGeneral(int fromX, int fromY , int toX , int toY)
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
	
	// java BestTestGen %%i.txt "total" "percent" "w" 
	// args[0] is the testcase number . txt
	// args[1] is the total number of nodes
	// args[2] is the percentage of nodes inside circles
	// args[3] is the number w
	
	
	/*
	 * 
	 *  1dvalue = 32
		2dvalue = 15
		3dvalue = 15
		4dvalue = 18
		5dvalue = 18
		6dvalue = 25
		
			    depot[1][0] = 36; depot[1][1] = 30;
	    depot[2][0] = 100; depot[2][1] = 45;
	    depot[3][0] = 130; depot[3][1] = 50;
	    depot[4][0] = 40; depot[4][1] = 130;
	    depot[5][0] = 70; depot[5][1] = 110;
	    depot[6][0] = 135; depot[6][1] = 100;
	    
	    out.write("85 60");
		    out.newLine();
		    out.write("85 90");
		    out.newLine();
		    out.write("110 100");
		    out.newLine();
		    out.write("135 90");
		    out.newLine();
		    out.write("120 60");
		    out.newLine();
		    out.write("95 45");
		    out.newLine();
	 */
	public static void main(String[] args) throws IOException {

		FileWriter fstream = new FileWriter(args[0],false);
	    BufferedWriter out = new BufferedWriter(fstream);
	    int total = Integer.parseInt(args[1]);
	    int t = Integer.parseInt(args[2]); // percent
	    int percent = 0;
	    int randomTemp = 0;
	    //number of participant plus one 
	    int SALESMAN = 7;
	    // first index as ID
	    // second as the coordinates and diameter of each circle
	    int depot[][] = new int [SALESMAN][3];
	    double dist[][] = new double[SALESMAN][SALESMAN];
	    int dValue[] = new int[SALESMAN];
	    double min = 9999999;
		Random rn = new Random();
		
		
		out.write(args[1]);
		out.newLine();
	    out.write("7"); //number of participant plus one 
	    out.newLine();
	    out.write("5");
	    out.newLine();
	    out.write(args[3]);
	    out.newLine();
	    percent = t;
	    
	    depot[1][0] = 0; depot[1][1] = 0;
	    depot[2][0] = 0; depot[2][1] = 80;
	    depot[3][0] = 0; depot[3][1] = 160;
	    depot[4][0] = 160; depot[4][1] = 0;
	    depot[5][0] = 160; depot[5][1] = 80;
	    depot[6][0] = 160; depot[6][1] = 160;
	    
	    // compute the distances between depots
	    for(int i=1 ; i<SALESMAN; i++)
	    {
	    	for(int j=1; j<SALESMAN; j++)
	    	{
	    		if(i!=j)
	    			dist[i][j] = dist[j][i] = distGeneral(depot[i][0],depot[i][1],depot[j][0],depot[j][1]);
	    	}
	    }
	        
	    // compute d Value
	    for(int i=1;i<SALESMAN;i++)
		{
			min = 9999999;
			for(int j=1 ; j<SALESMAN ; j++)
			{
				if (i==j)
					continue;
				if(dist[i][j]<min)
				{
					min = dist[i][j];
				}
			}
			dValue[i] = (int) Math.floor(min/2);
			//System.out.println(i+"dvalue = "+ dValue[i]);
		}
	    
	    // write depot to output file, testcase
	    for(int j=0 ; j<2 ; j++)
	    {
		    for(int i=1;i<SALESMAN;i++)
		    {
		    	out.write(depot[i][0]+" "+depot[i][1]);
			    out.newLine();
		    }
	    }
	    
    	for(int i=0 ; i<total ; i++)
	    {
			boolean flagt = false;
			randomTemp = Math.abs(rn.nextInt());
			switch(percent)
			{
				case 10:
					if(randomTemp%10==3)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 20:
					if(randomTemp%5==3)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 30:
					if(randomTemp%10==3 || randomTemp%10==5 || randomTemp%10==7)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 40:
					if(randomTemp%5==3 || randomTemp%5==1)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 50:
					if(randomTemp%4==3 || randomTemp%4==1)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 60:
					if(randomTemp%5!=3 && randomTemp%5!=1)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 70:
					if(randomTemp%10!=3 && randomTemp%10!=5 && randomTemp%10!=7)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 80:
					if(randomTemp%5!=3)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				case 90:
					if(randomTemp%10!=3)
					{
						flagt = true;
						break;
					}
					else
					{
						flagt = false;
						break;
					}
				default:
					break;
			}
			
			// if the flagt is true, place the node inside the circle
    		if(flagt)
			{
				int tempx = 0;
				int tempy = 0;
				int forCircle = 0;
				
				// randomly choose one circle to put the node in
				int rand = Math.abs(rn.nextInt()%(SALESMAN - 1))+1;
				
				// set tempx to a number that falls in the range of  <= dValue and >= -dValue
				
				tempx = Math.abs(rn.nextInt()%(dValue[rand]*2 + 1)) - dValue[rand]; 
				
				// forcircle is the length left for the y coordinate to choose
				forCircle = (int)Math.sqrt( dValue[rand]*dValue[rand] - (tempx*tempx) );
				if(forCircle!=0)
					tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
				
				// to avoid the chance of getting out the boarder 
				if (tempx + depot[rand][0] > 160)
					tempx = 0-tempx;
				if (tempy + depot[rand][1] > 160)
					tempy = 0-tempy;
				if (tempx + depot[rand][0] < 0)
					tempx = 0-tempx;
				if (tempy + depot[rand][1] < 0)
					tempy = 0-tempy;
				
				
				
				out.write((depot[rand][0]+tempx)+" "+(depot[rand][1]+tempy));
				out.newLine();
			
				
				/*
				switch(Math.abs(rn.nextInt()%6)+1)
				{

					case 1: 
						tempx = Math.abs(rn.nextInt()%65) - 32; 
						forCircle = (int)Math.sqrt(1024-(tempx*tempx));
						if(forCircle!=0)
							tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
						out.write((36+tempx)+" "+(30+tempy));
						out.newLine();
						break;
					case 2: 
						tempx = Math.abs(rn.nextInt()%31) - 15;
						forCircle = (int)Math.sqrt(225-(tempx*tempx));
						if(forCircle!=0)
							tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
						out.write((100+tempx)+" "+(45+tempy));
						out.newLine();
						break;
					case 3: 
						tempx = Math.abs(rn.nextInt()%31) - 15;
						forCircle = (int)Math.sqrt(225-(tempx*tempx));
						if(forCircle!=0)
							tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
						out.write((130+tempx)+" "+(50+tempy));
						out.newLine();
						break;
					case 4: 
						tempx = Math.abs(rn.nextInt()%37) - 18;
						forCircle = (int)Math.sqrt(324-(tempx*tempx));
						if(forCircle!=0)
							tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
						out.write((40+tempx)+" "+(130+tempy));
						out.newLine();
						break;
					case 5: 
						tempx = Math.abs(rn.nextInt()%37) - 18;
						forCircle = (int)Math.sqrt(324-(tempx*tempx));
						if(forCircle!=0)
							tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
						out.write((70+tempx)+" "+(110+tempy));
						out.newLine();
						break;
					case 6: 
						tempx = Math.abs(rn.nextInt()%51) - 25;
						forCircle = (int)Math.sqrt(625-(tempx*tempx));
						if(forCircle!=0)
							tempy = (int) (Math.abs(rn.nextInt()%forCircle) - (int)forCircle*0.5);
						out.write((135+tempx)+" "+(100+tempy));
						out.newLine();
						break;
					default:
					break;
				}
				*/
			}
			else
			{
				int tempx = Math.abs(rn.nextInt()%160);
				int tempy = Math.abs(rn.nextInt()%160);
				int counter = 0;
				boolean flag = false;
				
				// While the node is placed in one of circles
				while(!flag)
				{
					for(int j=1 ; j<SALESMAN ; j++)
					{
						if(distGeneral(tempx,tempy,depot[j][0],depot[j][1]) < dValue[j])
						{
							counter++;
						}
					}
					
					
					//System.out.println(counter);
					if(counter == 0)
					{
						flag = true;
					}
					else
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
						counter = 0;
					}
					/*
					if(distGeneral(tempx,tempy,36,30)<=32)
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
					}
					else if(distGeneral(tempx,tempy,100,45)<=15)
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
					}
					else if(distGeneral(tempx,tempy,130,50)<=15)
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
					}
					else if(distGeneral(tempx,tempy,40,130)<=18)
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
					}
					else if(distGeneral(tempx,tempy,70,110)<=18)
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
					}
					else if(distGeneral(tempx,tempy,135,100)<=25)
					{
						tempx = Math.abs(rn.nextInt()%160);
						tempy = Math.abs(rn.nextInt()%160);
					}
					else
					{
						flag = true;
					}
					*/
				}
				out.write(tempx+" "+tempy);
				out.newLine();
			}
	    }
	    
	    
	    out.flush();
	    out.close();
	    
		
	}

}
