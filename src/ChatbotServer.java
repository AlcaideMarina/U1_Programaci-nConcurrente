import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatbotServer {

	public static void main(String[] args) throws IOException {

		// Iniciamos el servidor
		ServerSocket listener = new ServerSocket(9999);
		System.out.println("Servidor iniciado");
		
		try {
			
			while (true) {
			
				Socket socket = listener.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				/*List<String> options = new ArrayList<>();
				options.add("1 - Información sobre la academia.");
				options.add("2 - Información sobre las clases ofrecidas.");
				options.add("3 - Información sobre precios y tarifas.");
				options.add("4 - Información sobre darse de baja y devoluciones.");
				options.add("5 - Información sobre política de privacidad.");*/
				
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				int clientAnswer = 0;
				String inputString = input.readLine();
				
				if (isNumeric(inputString)) {
					clientAnswer = Integer.parseInt(inputString);
				}
				
				if (!isNumeric(inputString)) {
					out.println("El dato introducido (" + inputString + ") sea numérico.\n"
							+ "Por favor, introduzca el índice de la opción que le interese (1 - 5).");
				} else if (clientAnswer < 1 || clientAnswer > 5) {
					out.println("Debe introducir el índice de la opción que le interese, que se encontrará en el rango 1 - 5.");
				} else if (clientAnswer == 1) {
					out.println("Información sobre la academia:\n" 
							+ "Somos centro oficial examinador de inglés y contamos con la mayor tasa de aprobados.");
				} else if (clientAnswer == 2) {
					out.println("Información sobre las clases ofrecidas\n" 
							+ "En Academia Learning ofrecemos cursos anuales, semestrales e intensivos. "
							+ "Además, puedes elegir la modalidad presencial u online. "
							+ "Ten en cuenta que además puedes escoger cursos de  Cambridge, Trinity, Aptis y LanguageCert");
				} else if (clientAnswer == 3) {
					out.println("Información sobre precios y tarifas.\n" 
							+ "El precio varía en función del tipo de curso, de si es presencial, del título que hagas. Te recomendamos que te dirijas a alguno de nuestros centro o que llames al teléfono de atención al cliente.");
				} else if (clientAnswer == 4) {
					out.println("Información sobre darse de baja y devoluciones.\n"
							+ "Para cursos online, se pagará todo por adelantado, sin haber posibilidad de devolución del dinero. "
							+ "Para cursos presenciales, los pagos se harán mensualmente, por lo que se podrá dar de baja en cualquier momento, sin tener que seguir pagando el resto del curso.");
				} else if (clientAnswer == 5) {
					out.println("Información sobre política de privacidad.\n"
							+ "Le informamos que estas conversaciones pueden ser grabadas con el fin de mejorar la calidad de nuestros servicios.");
				} else {
					out.println("Se ha producido un error. Por favor, inténtelo más tarde.");
				}
				
				socket.close();
				
			}
			
		} finally {
			
			listener.close();
			
		}
	}
	
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
