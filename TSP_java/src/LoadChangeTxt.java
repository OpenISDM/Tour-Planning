import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadChangeTxt {

    public LoadChangeTxt() {
    }
	public static String change;
   
    public String getFileText(String path){
		int counter = 0;
        StringBuffer strBuf = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(path),"utf8" ));
            while(br.ready()) {
				counter++;
                String brStr = br.readLine();
                if(brStr.length() > 0){
                    int c = brStr.charAt(0);
                    if(c == 65279){
                        brStr = brStr.substring(1, brStr.length());
                    }
					if(counter==4)
						brStr = change;
					brStr=brStr+"\n";
                    strBuf.append(brStr);
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return strBuf.toString();
    }

  
    public int writeFileText(String path,String txt){
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            br.write(txt);
            br.close();
        }catch(IOException e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    /**
     * 
     * @param path        
     * @param oldWord    
     * @param word       
     * @return            
     */
    public String changeTxtWord(String path, String oldWord,String word){
        String txt = getFileText(path);            
        //txt = txt.replaceFirst(oldWord, word);    
        writeFileText(path,txt);                
        return txt;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LoadChangeTxt lct = new LoadChangeTxt(); 
		change = args[0];
		for(int i=1 ; i<101 ; i++)
		{
			lct.changeTxtWord(i+".txt", "1", "2");    
		}
    }

}