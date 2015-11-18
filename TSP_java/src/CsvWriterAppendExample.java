import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvWriterAppendExample {

	/**
	 * @param args
	 */
public static void main(String[] args) {
		
		String output1 = "Mix"+args[0]+".csv";
		String output2 = "Circle"+args[0]+".csv";
		String output3 = "K-means"+args[0]+".csv";
		String in1 = "MaxBC.txt";
		String in2 = "MaxCO.txt";
		String in3 = "MaxOR.txt";
		
		// before we open the file check to see if it already exists
//		boolean alreadyExists1 = new File(output1).exists();
//		boolean alreadyExists2 = new File(output2).exists();
//		boolean alreadyExists3 = new File(output3).exists();
		File i1 = new File(in1);
		File i2 = new File(in2);
		File i3 = new File(in3);
		
		Scanner sc1 = null;
		Scanner sc2 = null;
		Scanner sc3 = null;
		try {
			sc1 = new Scanner(i1);
			sc2 = new Scanner(i2);
			sc3 = new Scanner(i3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String a , b , c;
		a = sc1.next();
		b = sc2.next();
		c = sc3.next();
		String MixResults [] = new String [102]; 
		String CircleResults [] = new String [102];
		String KmeansResults [] = new String [102];
		MixResults = a.split("\\+");
		CircleResults = b.split("\\+");
		KmeansResults = c.split("\\+");
		
		try {
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput1 = new CsvWriter(new FileWriter(output1, true), ',');
			CsvWriter csvOutput2 = new CsvWriter(new FileWriter(output2, true), ',');
			CsvWriter csvOutput3 = new CsvWriter(new FileWriter(output3, true), ',');
			// if the file didn't already exist then we need to write out the header line
			
				csvOutput1.write("No.");
				csvOutput1.write("Tour Length");
				csvOutput1.endRecord();
				
				csvOutput2.write("No.");
				csvOutput2.write("Tour Length");
				csvOutput2.endRecord();
				
				csvOutput3.write("No.");
				csvOutput3.write("Tour Length");
				csvOutput3.endRecord();
			
			// else assume that the file already has the correct header line
			
			// write out a few records
			for(int i=0 ; i<100 ; i++)
			{
				csvOutput1.write(""+(i+1));
				csvOutput1.write(MixResults[i+1]);
				csvOutput1.endRecord();
				
				csvOutput2.write(""+(i+1));
				csvOutput2.write(CircleResults[i+1]);
				csvOutput2.endRecord();
				
				csvOutput3.write(""+(i+1));
				csvOutput3.write(KmeansResults[i+1]);
				csvOutput3.endRecord();
			}
			
			

			
			csvOutput1.close();
			csvOutput2.close();
			csvOutput3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
