import java.io.*;
import java.net.*;

class ChatbotClient {

	public static void main(String[] args) {

		try (Socket socket = new Socket("localhost", 9999)) {

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String line = null;

			// Introducción
			out.println("Connected");
			System.out.println(in.readLine() + "\n");
			System.out.println(in.readLine() + "\n");
			// Opciones
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println("\n");

			while (!"Salir".equalsIgnoreCase(line)) {

				line = System.console().readLine();

				out.println(line);	
				out.flush();

				System.out.println(in.readLine());
				System.out.println("\n");

			}

		} catch (IOException e) {
			System.out.println("Error en el main - Client: " + e.toString());
			e.printStackTrace();
		}
	}
}
