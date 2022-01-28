import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {
	
	// driver code
	public static void main(String[] args)
	{
		// establish a connection by providing host and port
		// number
		try (Socket socket = new Socket("localhost", 1234)) {
			
			// writing to server
			PrintWriter out = new PrintWriter(
				socket.getOutputStream(), true);

			// reading from server
			BufferedReader in
				= new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			String line = null;
			
			//Introducción
			out.println("Connected");
			System.out.println(in.readLine() + "\n");
			System.out.println(in.readLine() + "\n");
			//Opciones
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println("\n");

			while (!"Salir".equalsIgnoreCase(line)) {
				
				// reading from user
				line = System.console().readLine();

				// sending the user input to server
				out.println(line);
				out.flush();

				// displaying server reply
				System.out.println(in.readLine());
				System.out.println("\n");

			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
