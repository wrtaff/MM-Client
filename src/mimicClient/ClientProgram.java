/****************************************************************/
/*                                                              */
/*      Program:  Malware Mimic Client					        */
/*                                                              */
/*      Handles client side communications.  Controls session   */
/*      with remote server.  Executes commands from server on   */
/* 		local machine.                                          */
/*                                                         		*/
/*      FILE:       ClientProgram.java			                */
/*                                                              */
/*      USAGE: ./MM-Client hostname exerciseId srvrName srvrPort*/
/*                                                              */
/*          hostname      name of host                          */
/*          serverName    IP addr of server, in dotted quad     */
/*          serverPort 	  Integer port number of remote server  */
/*                                                              */
/*			AUTHORS: W. Taff and P. Salevski                    */
/*                                                              */
/*			DATE: 22 January 2011			                    */
/*                                                              */
/*                                                              */
/****************************************************************/

package mimicClient;


/**
 * The MM-Client software for remote host.
 * Handles both sides of  communication with the remote server
 * (up and down) as well as local execution of remotely (server)
 *  commanded methods.    
 * 
 * @author W. Taff and P. Salevski
 *
 */
public class ClientProgram {
	
	
	
	
	/**
	 * Top level main() for program.  
	 * Loops, starting clientController with each iteration.  If
	 * clientController dies, handles that exception, and restarts.
	 * So far, is self perpetuating - i.e., will loop until killed 
	 * externally.   
	 * 
	 * @param args hostName, exercise Id, server IP.addr, server port
	 */
	public static void main(String[] args) {
			

		
		while (true) {
			
			try {

				new ClientController(args[0], args[1], args[2], Integer
						.parseInt(args[3])).run();

			} catch (NumberFormatException e) {

				e.printStackTrace();

				System.out.println("Check your parameters!\n" +
						"Expect hostId exerciseID serverIP.addr " +
						"serverIP.port" );

				System.exit(2);
				
			} 
			
			catch (ArrayIndexOutOfBoundsException e) {
		
				e.printStackTrace();
				
				System.out.println("Check your parameters!\n" +
						"Expect hostId exerciseID serverIP.addr " +
						"serverIP.port" );
				
				System.exit(2);
		
			}
			
			catch (NullPointerException f) {
				
				f.printStackTrace();
				
			}
			
			catch (Exception e) {

				e.printStackTrace();

			}
			
			finally {
				
				try {
					
					Thread.sleep(10000);
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}//end while
		
	}// end main()

}//end Class