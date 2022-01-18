import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatbotClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		String serverAddress = System.console().readLine("Introduce la direcci�n IP del servidor.\n");
		System.out.println("\n");
		System.out.println("Bienvenido a la Academia Learning\n");
		System.out.println("Este chatbot est� destinado a la resoluci�n de preguntas sobre diversos temas.\n");
		System.out.println("Le dejamos aqu� una lista de dudas frecuentes. "
				+ "Para m�s informaci�n, le recomendamos que acuda a alguno de nuestros centros o nos llame a los"
				+ "n�meros de tel�fono de nuestra p�gina web.\n");
		
		while(true) {
			
			String number = System.console().readLine("Introduce el �ndice de la pregunta.\n");
			
			// Creamos el socket
			Socket socket = new Socket(serverAddress, 9999);
			
			// Enviar n�mero al servidor
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(number);
			
			// El servidor procesa el n�mero
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
