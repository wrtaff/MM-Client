package mimicClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

/**
 * Controller class for the Malware Mimic client.  
 * Started by ClientProgram. 
 * 
 * @author W. Taff and P. Salevski
 *
 */
public class ClientController {
	
	
	
	///////////////////////////////////////////
	//DATA MEMBERS 
	///////////////////////////////////////////
	
	private String hostName;
	
	private String os_name;

	private Runtime localRuntime;

	private String status;

	private InetAddress localMachine;

	private Socket socket;

	private String textReceiveBuf;

	private BufferedReader inBufferedReader;

	private PrintStream outPrintStream;

	private String serverAddr;

	private int serverPort;
	
	
	
	
	
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////

	/**
	 * Constructor for ClientController
	 * @param serverPort the port of the remote server to use
	 * @param serverAddr the string dotted-quad server address
	 * @param hostName the hostname of local machine; will append
	 * @throws Exception 
	 * 
	 */
	
	public ClientController(String hostName, 
			String serverAddr, int serverPort) throws Exception {
		
		super();
		
		os_name  = System.getProperty("os.name");
		
		localRuntime = Runtime.getRuntime();
		
		status = "READY";
		
		localMachine = InetAddress.getLocalHost();
		
		socket = new Socket(serverAddr,serverPort);
		
		this.hostName = hostName + localMachine.getHostName();
		
		this.serverAddr = serverAddr;
		
		this.serverPort = serverPort;
		
		
		
	}


	

	/**
	 * Main body of the clientController.
	 * Loops until receives a halt command, checking the inbox 
	 * located on the remote server, and executing any commands. 
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {
		
		initializeConnection();
		
			//and then start looping and keep checking inbox
			while ( textReceiveBuf.compareTo("HALT")!=0 ){
				
				outPrintStream.println("GETINBOX");
				
				Thread.sleep(5000);
				
				textReceiveBuf = inBufferedReader.readLine();
				
				System.out.println(textReceiveBuf);
				
				
				if ( textReceiveBuf.compareTo("MOD_0")==0 ) mod_0();
				
				if ( textReceiveBuf.compareTo("MOD_1")==0 ) mod_1();
				
				if ( textReceiveBuf.compareTo("MOD_2")==0 ){mod_2();

				}

				
			}//end while
			
			
			//CLOSE CONNECTION
			outPrintStream.println("CLOSING...");
			
			Thread.sleep(1000);
			
			socket.close();

		
		
	}
	
	
	
	
	
	
	
	/**
	 * Initializes the connection with the remote host.
	 * Called by run(), connects with the remote host, and upon
	 * connection, sends initialization parameters to the server.
	 * 
	 * @throws Exception
	 */
	private void initializeConnection() throws Exception {
		
		System.out.println("Connected ... waiting for #GETNAME") ;

		outPrintStream = new PrintStream(socket.getOutputStream() );

		inBufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

		//GIVE TIME FOR INITIAL COMMAND TO ARRIVE
		Thread.sleep(1000);


		if (inBufferedReader.ready()) {
			textReceiveBuf = inBufferedReader.readLine();
			System.out.println(textReceiveBuf);
		}

		//if server says getname, then tell it hostName, and status

		if (textReceiveBuf.compareTo("#GETNAME")==0 ){
			
			outPrintStream.println("NAME=" + hostName);
			outPrintStream.println("STATUS=" + status);	
			//TODO - also tell it exercise name
			
		}
		
		
	}// end initializeConnection()
	
	
	/**
	 * A hping scan of 10 sequential ports from a random start port.
	 * Scans server in range of 1 to 1024.  
	 * @throws InterruptedException
	 */
	private void mod_2() throws InterruptedException {
		
		status=("MOD_2");
		outPrintStream.println("STATUS=" + status);	
		
		int randomPort = new Random().nextInt(1014) + 1;


		
		try {
			
			Process p = null;
			
			if (os_name.contains("Linux")) {
				
					p = localRuntime.exec("/usr/bin/sudo " +
							"/usr/sbin/hping3 -c 10 -s 1 -p "+ 
							randomPort + " -S " + serverAddr);
					
					randomPort++;
					
					System.out.println(randomPort);
				
			}
			
			else { //is windows
		
					
					p = localRuntime.exec("hping -c 10 -s 1 -p "
							+ randomPort +" -S "+serverAddr);
					
				
					System.out.println(randomPort);
				
			}
			
			
			BufferedReader buffRdr = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							p.getInputStream())));
			
			String line;
			
			while ((line = buffRdr.readLine()) != null) {
				
				System.out.println(line);
				
			}
			
			try {
				if (p.waitFor() != 0) {
					
					System.err.println(
							"exit value = " + p.exitValue());
				}
			}
			catch (InterruptedException e) {
				System.err.println(e);
			}

			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		System.out.println("Mod 2 Iteration Complete");
		
		
		
		
		
	}




	/**
	 * A 5 ping module.
	 * Pings server 5 times then stops.  
	 */
	private void mod_1() {
				
		status = "MOD_1";
		
		outPrintStream.println("STATUS=" + status);
	
		
		try {
			
			Process p;
			
			if (os_name.contains("Linux")) {
			
				p = localRuntime.exec("/bin/ping -c5 " + serverAddr);
			
			}
			
			else { //is windows
				
				p = localRuntime.exec("ping -n 5 " + serverAddr);
				
			}
			
			BufferedReader buffRdr = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							p.getInputStream())));
			
			String line;
			
			while ((line = buffRdr.readLine()) != null) {
				
				System.out.println(line);
				
			}
			
			try {
				if (p.waitFor() != 0) {
					
					System.err.println(
							"exit value = " + p.exitValue());
				}
			}
			catch (InterruptedException e) {
				System.err.println(e);
			}

			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		System.out.println("Mod 1 Iteration Complete");

		
	}

	
	
	
	
	
	
	
	/**
	 * Sends a status update message to the server.  
	 * Equivalent to an idle command.  
	 * 
	 */
	private void mod_0() {
		
		status=("MOD_0");
		outPrintStream.println("STATUS=" + status);	
		
	}	
	

}// end class
