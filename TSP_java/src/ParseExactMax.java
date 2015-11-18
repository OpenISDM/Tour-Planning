import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ParseExactMax {

	public static void main(String[] args) throws IOException {
		
		int ways = Integer.parseInt(args[0]);
		ways++; // for array indexing
		
		String in[] = new String[ways];
		Scanner sin[] = new Scanner[ways];
		File fin[] = new File[ways];
		FileWriter fout = new FileWriter("TotalMax_results.txt",true);
		BufferedWriter bout = new BufferedWriter(fout);
		
		in[1] = "MaxCO.txt";
		//in[2] = "ExactCC.txt";
		in[2] = "MaxBC.txt";
		in[3] = "MaxOR.txt";
		
		
		String temp;
		int addup[] = new int[ways];
		for(int i=1 ; i<ways ; i++)
		{
		
			addup[i] = 0;
			fin[i] = new File(in[i]);
			sin[i] = new Scanner(fin[i]);
			
			temp = sin[i].next();
			temp = temp.replace('+','a');
			String[] result=temp.split("a");
			
			// add all the numbers in result	
			for(int j=1;j<result.length;j++)
			{
				addup[i] = addup[i] + Integer.parseInt(result[j]);
			}
			
			bout.write(in[i]+"  "+addup[i]);
			bout.newLine();
		}
		bout.flush();
		bout.close();
		
		
		
	}
}
