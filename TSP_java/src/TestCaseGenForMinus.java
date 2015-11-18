import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TestCaseGenForMinus {

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
	
	public static void main(String[] args) throws IOException {

		FileWriter fstream = new FileWriter(args[0],true);
	    BufferedWriter out = new BufferedWriter(fstream);
	    int total = Integer.parseInt(args[1]);
	    int t = Integer.parseInt(args[2]); // percent
	    int percent = 0;
		Random rn = new Random();
		out.write(args[1]);
	    out.newLine();
	    out.write("7");
	    out.newLine();
	    out.write("5");
	    out.newLine();
	    out.write("1");
	    out.newLine();
	    
		// dvalue 15 11 11 25 25 15
	    switch(t)
	    {
	    	case 20: percent = 5;break;
	    	case 33: percent = 3;break;
	    	case 66: percent = -3;break;
	    	case 80: percent = -5;break;
	    	case 90: percent = -10;break;
	    	default: break;
	    }
		
	    for(int i=0;i<2;i++)
	    {
	    	out.write("36 30");
		    out.newLine();
		    out.write("100 45");
		    out.newLine();
		    out.write("130 50");
		    out.newLine();
		    out.write("40 130");
		    out.newLine();
		    out.write("70 110");
		    out.newLine();
		    out.write("135 100");
		    out.newLine();
	    }
	    
	    if(percent>0)
	    {
		    for(int i=0 ; i<total ; i++)
		    {
				if(rn.nextInt()%percent==0)
				{
					int tempx = Math.abs(rn.nextInt()%160);
					int tempy = Math.abs(rn.nextInt()%160);
					boolean flag = false;
					
					while(!flag)
					{
						
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
					}
					out.write(tempx+" "+tempy);
					out.newLine();
				}
				else
				{
					out.write(Math.abs(rn.nextInt()%160)+" "+Math.abs(rn.nextInt()%160));
					out.newLine();
				}
		    }
	    }
	    else
	    {
	    	for(int i=0 ; i<total ; i++)
		    {
				if(rn.nextInt()%Math.abs(percent)!=1)
				{
					int tempx = Math.abs(rn.nextInt()%160);
					int tempy = Math.abs(rn.nextInt()%160);
					boolean flag = false;
					
					while(!flag)
					{
						
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
					}
					out.write(tempx+" "+tempy);
					out.newLine();

				}
				else
				{
					out.write(Math.abs(rn.nextInt()%160)+" "+Math.abs(rn.nextInt()%160));
					out.newLine();
				}
		    }
	    }
	    out.flush();
	    out.close();
	    
	}

}
