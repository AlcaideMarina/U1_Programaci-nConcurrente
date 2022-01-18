import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatbotClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		String serverAddress = System.console().readLine("Introduce la dirección IP del servidor.\n");
		System.out.println("\n");
		System.out.println("Bienvenido a la Academia Learning\n");
		System.out.println("Este chatbot está destinado a la resolución de preguntas sobre diversos temas.\n");
		System.out.println("Le dejamos aquí una lista de dudas frecuentes. "
				+ "Para más información, le recomendamos que acuda a alguno de nuestros centros o nos llame a los"
				+ "números de teléfono de nuestra página web.\n");
		
		while(true) {
			
			String number = System.console().readLine("Introduce el índice de la pregunta.\n");
			
			// Creamos el socket
			Socket socket = new Socket(serverAddress, 9999);
			
			// Enviar número al servidor
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(number);
			
			// El servidor procesa el número
			// Recibimos la respuesta del servidor 
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String answer = input.readLine();
			
			// Imprimimos el mensaje
			System.out.println(answer);
			
			// Cerrar el socket
			socket.close();
			
			
		}

	}

}
