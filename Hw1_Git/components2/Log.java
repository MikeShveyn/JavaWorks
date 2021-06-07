package components2;

import java.io.*;
import java.util.ArrayList;
import java.util.List; 

public class Log {
	
	 private BufferedWriter bw;
	 private BufferedReader br;
	 File file;
	 int count = 1;

	  public Log(File f) throws IOException {
		this.file = f;
	    FileWriter fw = new FileWriter(f);
	    FileReader fr = new FileReader(f);
	    this.bw = new BufferedWriter(fw);
	    this.br = new BufferedReader(fr);
	  }

	  public void writeEntry(String message) throws IOException {
		  synchronized(bw){
			  
		System.out.println("-------------Write WRITE---------------");
	    bw.write(count + ". ");
	    bw.write(message);
	    bw.write("\r\n");
	    count++;
	    bw.flush();
	    
		}
	  }
	  
	  public List<String> readEntry() throws IOException {
		  synchronized(br)
		  {
			  
			  System.out.println("-------------Read Read Read ---------------");
			  List<String> data = new ArrayList<String>();
			  String st;
			  //How to read???
			  while ((st = br.readLine()) != null)
			  {
				  System.out.println(st);
				  data.add(st);
			  }
				    
		
			  br.close();
			  ResetReader();
			  
			  return data;
			  
		  }
	  }

	  public void close() throws IOException {
	    bw.close();
	    br.close();
	
	  }
	  
	  private void ResetReader() throws FileNotFoundException
	  {
		  FileReader fr = new FileReader(this.file);
		  this.br = new BufferedReader(fr);
	  }

	  
	  protected void finalize() {
	    try {
	      this.close();
	    
	    }
	    catch (IOException ex) {
	    }
	  }
}
