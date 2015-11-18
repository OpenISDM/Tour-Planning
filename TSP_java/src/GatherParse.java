import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


//The variables with 1 are for AverageMax
//The variables with 2 are for wttimes


public class GatherParse {

	/**
	 * @param args
	 * @throws IOException
	 * 
	 *  args[0] = int type
	 *  // type 1 = CO
		// type 2 = BC
		// type 3 = OR
     *  
     *  args[1] = path
     *  args[2] = int total testcase
     *  args[3] = total nodes
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//String type = args[0];
		// type 1 = CO
		// type 2 = BC
		// type 3 = OR
		int type = Integer.parseInt(args[0]);
		int averageMax = 0;
		int total = Integer.parseInt(args[2]);
		int wttimes = 0;
		String output = "";
		String output2 = "";
		String nodes = args[3];
		String path = args[1];

		switch (type)
		{
			case 1:
				output = path + "\\Circle"+nodes+".txt";
				output2 = path + "\\CircleWin"+nodes+".txt";
				break;
			case 2:
				output = path +  "\\Mix"+nodes+".txt";
				output2 = path + "\\MixWin"+nodes+".txt";
				break;
			case 3:
				output = path +  "\\K-means"+nodes+".txt";
				output2 = path + "\\K-meansWin"+nodes+".txt";
				break;
		}
		
		File fo = new File(output);
		File fo2 = new File(output2);
		File fi = new File("TotalMax_results.txt");
		File fi2 = new File("WTTimes_results.txt");
		FileWriter fout = new FileWriter(fo,true);
		BufferedWriter bout = new BufferedWriter(fout);
		FileWriter fout2 = new FileWriter(fo2,true);
		BufferedWriter bout2 = new BufferedWriter(fout2);
		try {
			Scanner sn = new Scanner(fi);
			Scanner sn2 = new Scanner(fi2);
			switch (type)
			{
				case 1:
					sn.next();
					averageMax = Integer.parseInt(sn.next());
					averageMax = averageMax / total;
					
					sn2.next();
					wttimes = Integer.parseInt(sn2.next());
					break;
				case 2:
					sn.next();
					sn.next();
					sn.next();
					averageMax = Integer.parseInt(sn.next());
					averageMax = averageMax / total;
					
					sn2.next();
					sn2.next();
					sn2.next();
					wttimes = Integer.parseInt(sn2.next());
					break;
				case 3:
					sn.next();
					sn.next();
					sn.next();
					sn.next();
					sn.next();
					averageMax = Integer.parseInt(sn.next());
					averageMax = averageMax / total;
					
					sn2.next();
					sn2.next();
					sn2.next();
					sn2.next();
					sn2.next();
					wttimes = Integer.parseInt(sn2.next());
					break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bout.write(averageMax+"");
		bout.newLine();
		bout.flush();
		bout.close();
		
		bout2.write(wttimes+"");
		bout2.newLine();
		bout2.flush();
		bout2.close();
		
		
		
	}

}
