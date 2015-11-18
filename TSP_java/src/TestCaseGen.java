import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TestCaseGen {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		FileWriter fstream = new FileWriter(args[0],false);
	    BufferedWriter out = new BufferedWriter(fstream);
		Random rn = new Random();
	    out.write("160");
	    out.newLine();
	    out.write("7");
	    out.newLine();
	    out.write("5");
	    out.newLine();
	    out.write("1");
	    out.newLine();
	    
	    for(int i=0;i<2;i++)
	    {
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
	    }
	    
	    /*
	    for(int i=1 ; i<=160 ; i++)
	    {
	    	//out.write(Math.abs(rn.nextInt()%160)+" "+Math.abs(rn.nextInt()%160));
		    out.write(i+" "+(160-i));
	    	out.newLine();
	    }*/
	    
	    for(int i=1 ; i<=32 ; i++)
	    {
	    	for(int j=1 ; j<=5 ; j++)
	    	{
	    		out.write(i*2+" "+j*2);
	    		out.newLine();
	    	}
	    }
	    out.flush();
	    out.close();
	    
		
	}

}
