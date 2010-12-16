package mimicClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

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
				
				outPrintStream.println("NAME=ALICE");
				outPrintStream.println("STATUS=HELLO");	
				
			}
			
			
			//and then start looping!  
			
			while ( textReceived.compareTo("HALT")!=0 ){
				
				outPrintStream.println("GETINBOX");
				
				Thread.sleep(5000);
				
				textReceived = inBufferedReader.readLine();
				
				System.out.println(textReceived);
				
				//keep checking inbox
				
				
				
			}
			
			
			
			outPrintStream.println("QUIT");
			
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
