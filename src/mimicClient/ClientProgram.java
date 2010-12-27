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
	 * @param args
	 */
	
	//TODO - whole class needs documentation!
	public static void main(String[] args) {
		
		
		
		try {
			
			new clientController(
				args[0], args[1], Integer.parseInt(args[2]) ).run();
			
		} catch (NumberFormatException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}// end main()

}//end Class
