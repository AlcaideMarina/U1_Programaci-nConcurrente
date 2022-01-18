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
				options.add("1 - Informaci�n sobre la academia.");
				options.add("2 - Informaci�n sobre las clases ofrecidas.");
				options.add("3 - Informaci�n sobre precios y tarifas.");
				options.add("4 - Informaci�n sobre darse de baja y devoluciones.");
				options.add("5 - Informaci�n sobre pol�tica de privacidad.");*/
				
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				int clientAnswer = 0;
				String inputString = input.readLine();
				
				if (isNumeric(inputString)) {
					clientAnswer = Integer.parseInt(inputString);
				}
				
				if (!isNumeric(inputString)) {
					out.println("El dato introducido (" + inputString + ") sea num�rico.\n"
							+ "Por favor, introduzca el �ndice de la opci�n que le interese (1 - 5).");
				} else if (clientAnswer < 1 || clientAnswer > 5) {
					out.println("Debe introducir el �ndice de la opci�n que le interese, que se encontrar� en el rango 1 - 5.");
				} else if (clientAnswer == 1) {
					out.println("Informaci�n sobre la academia:\n" 
							+ "Somos centro oficial examinador de ingl�s y contamos con la mayor tasa de aprobados.");
				} else if (clientAnswer == 2) {
					out.println("Informaci�n sobre las clases ofrecidas\n" 
							+ "En Academia Learning ofrecemos cursos anuales, semestrales e intensivos. "
							+ "Adem�s, puedes elegir la modalidad presencial u online. "
							+ "Ten en cuenta que adem�s puedes escoger cursos de  Cambridge, Trinity, Aptis y LanguageCert");
				} else if (clientAnswer == 3) {
					out.println("Informaci�n sobre precios y tarifas.\n" 
							+ "El precio var�a en funci�n del tipo de curso, de si es presencial, del t�tulo que hagas. Te recomendamos que te dirijas a alguno de nuestros centro o que llames al tel�fono de atenci�n al cliente.");
				} else if (clientAnswer == 4) {
					out.println("Informaci�n sobre darse de baja y devoluciones.\n"
							+ "Para cursos online, se pagar� todo por adelantado, sin haber posibilidad de devoluci�n del dinero. "
							+ "Para cursos presenciales, los pagos se har�n mensualmente, por lo que se podr� dar de baja en cualquier momento, sin tener que seguir pagando el resto del curso.");
				} else if (clientAnswer == 5) {
					out.println("Informaci�n sobre pol�tica de privacidad.\n"
							+ "Le informamos que estas conversaciones pueden ser grabadas con el fin de mejorar la calidad de nuestros servicios.");
				} else {
					out.println("Se ha producido un error. Por favor, int�ntelo m�s tarde.");
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
