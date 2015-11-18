import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TestCaseGenForBiased {

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
	
	// java TestCaseGenForBiased %%i.txt "total" "percent"
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
	    
		
		
	    switch(t)
	    {
	    	case 30: percent = 5;break;
	    	case 60: percent = 3;break;
	    	case 90: percent = 2;break;
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
	    
	    if(percent == 2)
	    {
	    	for(int i=0 ; i<total ; i++)
		    {
				if(rn.nextInt()%3!=1)
				{
					int tempx = 0;
					int tempy = 0;
					int forCircle = 0;
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
				}
				else
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
		    }
	    }
	    else
	    {
		    for(int i=0 ; i<total ; i++)
		    {
				if(rn.nextInt()%percent==1)
				{
					int tempx = 0;
					int tempy = 0;
					int forCircle = 0;
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
				}
				else
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
		    }
	    }
	    out.flush();
	    out.close();
	    
		
	}

}
