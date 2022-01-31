import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server class
class Server {
	
	public static void main(String[] args) {

		ServerSocket server = null;

		try {

			server = new ServerSocket(9999);
			server.setReuseAddress(true);

			while (true) {

				Socket client = server.accept();
				System.out.println("New client connected" + client.getInetAddress().getHostAddress());

				ClientHandler clientHandler = new ClientHandler(client);

				new Thread(clientHandler).start();
			}
			
		} catch (IOException e) {
			System.out.println("Error en el main (catch) - Server: " + e.toString());
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					System.out.println("Error en el main (finally) - Server: " + e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			PrintWriter out = null;
			BufferedReader in = null;

			String intro = "Bienvenido a la Academia Learning.";
			String description = "Este chatbot est� destinado a la resoluci�n de preguntas sobre diversos temas. Le dejamos aqu� una lista de dudas frecuentes. Para m�s informaci�n, le recomendamos que acuda a alguno de nuestros centros o nos llame a los n�meros de tel�fono de nuestra p�gina web. Introduzca el �ndice de la pregunta o escriba 'Salir' si desea salir de la aplicaci�n";

			List<String> options = new ArrayList<>();
			options.add("1 - Informaci�n sobre la academia.");
			options.add("2 - Informaci�n sobre las clases ofrecidas.");
			options.add("3 - Informaci�n sobre precios y tarifas.");
			options.add("4 - Informaci�n sobre darse de baja y devoluciones.");
			options.add("5 - Informaci�n sobre pol�tica de privacidad.");
			options.add("Salir - Salir de la aplicaci�n.");

			try {

				// get the outputstream of client
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				// get the inputstream of client
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String line;
				int clientAnswer = 0;
				while ((line = in.readLine()) != null) {

					if (line.equals("Connected")) {

						out.println(intro);
						out.println(description);
						out.println(options.get(0));
						out.println(options.get(1));
						out.println(options.get(2));
						out.println(options.get(3));
						out.println(options.get(4));
						out.println(options.get(5));

					} else if (line.equals("Salir")) {
						out.println("�Hasta pronto!");

					} else {

						if (isNumeric(line)) {
							clientAnswer = Integer.parseInt(line);
						}

						if (!isNumeric(line)) {
							out.println("Es necesario que el dato introducido (" + line + ") sea num�rico. "
									+ "Por favor, introduzca el �ndice de la opci�n que le interese (1 - 5).");

						} else if (clientAnswer < 1 || clientAnswer > 5) {
							out.println(
									"Debe introducir el �ndice de la opci�n que le interese, que se encontrar� en el rango 1 - 5.");

						} else if (clientAnswer == 1) {
							out.println("INFORMACI�N SOBRE LA ACADEMIA: "
									+ "Somos centro oficial examinador de ingl�s y contamos con la mayor tasa de aprobados.");

						} else if (clientAnswer == 2) {
							out.println("INFORMACI�N SOBRE LAS CLASES OFRECIDAS: "
									+ "En Academia Learning ofrecemos cursos anuales, semestrales e intensivos. "
									+ "Adem�s, puedes elegir la modalidad presencial u online. "
									+ "Ten en cuenta que adem�s puedes escoger cursos de  Cambridge, Trinity, Aptis y LanguageCert");

						} else if (clientAnswer == 3) {
							out.println("INFORMACI�N SOBRE PRECIOS Y TARIFAS: "
									+ "El precio var�a en funci�n del tipo de curso, de si es presencial, del t�tulo que hagas. "
									+ "Te recomendamos que te dirijas a alguno de nuestros centro o que llames al tel�fono de atenci�n al cliente.");

						} else if (clientAnswer == 4) {
							out.println("INFORMACI�N SOBRE DARSE DE BAJA Y DEVOLUCIONES: "
									+ "Para cursos online, se pagar� todo por adelantado, sin haber posibilidad de devoluci�n del dinero. "
									+ "Para cursos presenciales, los pagos se har�n mensualmente, por lo que se podr� dar de baja en cualquier momento, sin tener que seguir pagando el resto del curso.");
						} else if (clientAnswer == 5) {
							out.println("INFORMACI�N SOBRE POL�TICA DE PRIVACIDAD: "
									+ "Le informamos que estas conversaciones pueden ser grabadas con el fin de mejorar la calidad de nuestros servicios.");

						} else {
							out.println("Se ha producido un error. Por favor, int�ntelo m�s tarde.");

						}
					}
				}
			} catch (IOException e) {
				System.out.println("Error en run - ClientHandler: " + e.toString());
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				} catch (IOException e) {
					System.out.println("Error en run (finally) - ClientHandler: " + e.toString());
					e.printStackTrace();
				}
			}
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
