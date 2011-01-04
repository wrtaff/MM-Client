package mimicClient;



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
	 * Top level main() for program.  
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		
		//TODO - how can I eject from this loop?  
		//
		while (true) {
			
			try {

				new clientController(args[0], args[1], Integer
						.parseInt(args[2])).run();

			} catch (NumberFormatException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} 
			
			catch (NullPointerException f) {
				
				f.printStackTrace();
				
			}
			
			
			
			catch (Exception e) {

				// TODO Auto-generated catch block
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
