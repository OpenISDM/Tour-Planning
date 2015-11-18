import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TestCaseGenRandom {

	/**
	 * @param args
	 * @throws IOException 
	 * java TestCaseGenRandom filename.txt totalnodes sales kamount
	 */
	public static void main(String[] args) throws IOException {

		FileWriter fstream = new FileWriter(args[0],true);
	    BufferedWriter out = new BufferedWriter(fstream);
	    int total = Integer.parseInt(args[1]);
	    int sales = Integer.parseInt(args[2]);
	    
		Random rn = new Random();
	    out.write(args[1]);
	    out.newLine();
	    out.write(""+(sales+1));
	    out.newLine();
	    out.write("5");
	    out.newLine();
	    out.write(args[3]);
	    out.newLine();
	    
	    int x[] = new int[sales];
	    int y[] = new int[sales];
	    
	    
	    for(int i=0;i<sales;i++)
	    {
	    	x[i] = Math.abs(rn.nextInt()%160);
	    	y[i] = Math.abs(rn.nextInt()%160);
	    }
	    
	    for(int i=0; i<2 ; i++)
	    {
	    	for(int j=0 ; j<sales ; j++)
	    	{
	    		out.write(x[j]+" "+y[j]);
	    		out.newLine();
	    	}
	    }
	    for(int i=0 ; i<total ; i++)
	    {
	    	out.write(Math.abs(rn.nextInt()%160)+" "+Math.abs(rn.nextInt()%160));
		    out.newLine();
	    }
	    out.flush();
	    out.close();
	}

}
