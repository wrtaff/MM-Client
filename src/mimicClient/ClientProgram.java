package mimicClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;




/**
 * The MM-Client software for remote host.
 * So far, a single class that handles both sides of 
 * communication with the remote server (up and down) as 
 * well as local execution of remotely (server) commanded
 * methods.  
 * 
 * @author W. Taff and P. Salevski
 *
 */
public class ClientProgram {

	/**
	 * @param args
	 */
	
	//TODO - whole class needs documentation!
	//TODO - add data members like hostname and server etc...
	public static void main(String[] args) {
		
		String hostName = args[0];

		String server = "127.0.0.1";
		Integer servPort = 30000;
		
		String textReceived = "";
		
		
		
		//initialize socket connection
		try {
			Socket socket = new Socket(server,servPort);
			System.out.println("Connected to Server " +
					".. waiting for #GETNAME") ;
			
			InputStream in = socket.getInputStream();
			
			PrintStream outPrintStream = new PrintStream(socket.getOutputStream() );
			BufferedReader inBufferedReader = 
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//NEED TO SLEEP TO GET TIME FOR INITIAL TO ARRIVE
			Thread.sleep(1000);
			
			
			if (inBufferedReader.ready()) {
				textReceived = inBufferedReader.readLine();
				System.out.println(textReceived);
			}
			
			//if it says getname, then tell it alice, and status
			
			if (textReceived.compareTo("#GETNAME")==0 ){
				
				outPrintStream.println("NAME=" + hostName);
				outPrintStream.println("STATUS=HELLO");	
				
			}
			
			
			//and then start looping!  
			//keep checking inbox
			while ( textReceived.compareTo("HALT")!=0 ){
				
				outPrintStream.println("GETINBOX");
				
				Thread.sleep(5000);
				
				textReceived = inBufferedReader.readLine();
				
				System.out.println(textReceived);
				
			
				
				
			}
			
			
			
			outPrintStream.println("QUIT");
			
			Thread.sleep(1000);
			
			socket.close();
			
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception f){
			f.printStackTrace();
		}
		

		
		
	}

}//end class
