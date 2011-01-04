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
/*      USAGE:      ./MM-Client hostname serverName serverPort  */
/*                                                              */
/*          hostname      name of host                          */
/*          serverName    IP addr of server, in dotted quad     */
/*          serverPort 	  Integer port number of remote server  */
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
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		//TODO change input params to use local host addr, instead of input host?  

		
		while (true) {
			
			try {

				new clientController(args[0], args[1], Integer
						.parseInt(args[2])).run();
				

			} catch (NumberFormatException e) {

				e.printStackTrace();

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
