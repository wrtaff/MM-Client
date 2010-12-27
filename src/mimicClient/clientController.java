package mimicClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class clientController {
	
	///////////////////////////////////////////
	//DATA MEMBERS 
	///////////////////////////////////////////
	
	private String hostName;
	
	private String serverAddr;
	
	private int serverPort;
	
	private String os_name;

	private Runtime localRuntime;

	private String status;

	private InetAddress localMachine;

	private Socket socket;

	private String textReceiveBuf;

	private BufferedReader inBufferedReader;

	private PrintStream outPrintStream;
	
	
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////

	/**
	 * @param serverPort 
	 * @param serverAddr 
	 * @param hostName 
	 * @throws Exception 
	 * 
	 */
	public clientController(String hostName, 
			String serverAddr, int serverPort) throws Exception {
		
		super();
		
		this.hostName = hostName;
		
		this.serverAddr = serverAddr;
		
		this.serverPort = serverPort;
		
		os_name  = System.getProperty("os.name");
		
		localRuntime = Runtime.getRuntime();
		
		status = "IDLE";
		
		localMachine = InetAddress.getLocalHost();
		
		socket = new Socket(serverAddr,serverPort);
		
	}




	public void run() throws Exception {
		
		initializeConnection();
		
		String textReceived = "";		
		
		//initialize socket connection
		try {
			

			
			
			//and then start looping and keep checking inbox
			while ( textReceived.compareTo("HALT")!=0 ){
				
				outPrintStream.println("GETINBOX");
				
				Thread.sleep(5000);
				
				textReceived = inBufferedReader.readLine();
				
				System.out.println(textReceived);
				
				
				if ( textReceived.compareTo("MOD_0")==0 ){
					
					status = "MOD_0";
					
					mod_0(outPrintStream, status);
				
					
				}
				
				if ( textReceived.compareTo("MOD_1")==0 ){
					
					//TODO RUN MOD 1
					mod_1(localRuntime, os_name);
					
					status = "MOD_1";
					outPrintStream.println("STATUS=" + status);
					
					
					
				}
				
				
				if ( textReceived.compareTo("MOD_2")==0 ){
					
					//TODO RUN MOD 2
					System.out.println("Running Mod 2");
					status = "MOD_2";
					outPrintStream.println("STATUS=" + status);

					
					
				}
				
				
			

				
			}
			
			
			//CLOSE CONNECTION
			outPrintStream.println("CLOSING...");
			
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
	
	private void initializeConnection() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Connected ... waiting for #GETNAME") ;

		outPrintStream = new PrintStream(socket.getOutputStream() );

		inBufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

		//GIVET TIME FOR INITIAL COMMAND TO ARRIVE
		Thread.sleep(1000);


		if (inBufferedReader.ready()) {
			textReceiveBuf = inBufferedReader.readLine();
			System.out.println(textReceiveBuf);
		}

		//if server says getname, then tell it hostName, and status

		if (textReceiveBuf.compareTo("#GETNAME")==0 ){
			
			outPrintStream.println("NAME=" + hostName);
			outPrintStream.println("STATUS=" + status);	
			
		}
		
		
	}// end initializeConnection()




	/**
	 * A 5 ping module.
	 * pings server 5 times then stops.  
	 * 
	 * @param r
	 * @param osName 
	 */
	private void mod_1(Runtime r, String osName) {
		// TODO Auto-generated method stub
		
		System.out.println("Running Mod 1");
		
		try {
			
			Process p;
			
			if (osName.contains("Linux")) {
			
				p = r.exec("/bin/ping -c5 127.0.0.1"); //works linux
				//Process p = r.exec("/bin/ls"); // works Linux
			
			}
			
			else { //is windows
				
				p = r.exec("ping -n 5 127.0.0.1");
				
			}
			
			InputStream in = p.getInputStream();
			BufferedInputStream buf = new BufferedInputStream(in);
			InputStreamReader inread = new InputStreamReader(buf);
			BufferedReader bufferedReader = new BufferedReader(inread);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				
				System.out.println(line);
				
			}
			
			try {
				if (p.waitFor() != 0) {
					System.err.println("exit value = " + p.exitValue());
				}
			}
			catch (InterruptedException e) {
				System.err.println(e);
			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Mod 1 Iteration Complete");

		
	}

	/**
	 * Sends a status update message to the server.  
	 * 
	 * @param outPrintStream
	 * @param status
	 */
	private void mod_0(PrintStream outPrintStream, 
												String status) {
		
		outPrintStream.println("STATUS=" + status);	
		
	}	
	

}// end class