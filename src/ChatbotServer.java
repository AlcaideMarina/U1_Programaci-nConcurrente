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
			String description = "Este chatbot está destinado a la resolución de preguntas sobre diversos temas. Le dejamos aquí una lista de dudas frecuentes. Para más información, le recomendamos que acuda a alguno de nuestros centros o nos llame a los números de teléfono de nuestra página web. Introduzca el índice de la pregunta o escriba 'Salir' si desea salir de la aplicación";

			List<String> options = new ArrayList<>();
			options.add("1 - Información sobre la academia.");
			options.add("2 - Información sobre las clases ofrecidas.");
			options.add("3 - Información sobre precios y tarifas.");
			options.add("4 - Información sobre darse de baja y devoluciones.");
			options.add("5 - Información sobre política de privacidad.");
			options.add("Salir - Salir de la aplicación.");

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
						out.println("¡Hasta pronto!");

					} else {

						if (isNumeric(line)) {
							clientAnswer = Integer.parseInt(line);
						}

						if (!isNumeric(line)) {
							out.println("Es necesario que el dato introducido (" + line + ") sea numérico. "
									+ "Por favor, introduzca el índice de la opción que le interese (1 - 5).");

						} else if (clientAnswer < 1 || clientAnswer > 5) {
							out.println(
									"Debe introducir el índice de la opción que le interese, que se encontrará en el rango 1 - 5.");

						} else if (clientAnswer == 1) {
							out.println("INFORMACIÓN SOBRE LA ACADEMIA: "
									+ "Somos centro oficial examinador de inglés y contamos con la mayor tasa de aprobados.");

						} else if (clientAnswer == 2) {
							out.println("INFORMACIÓN SOBRE LAS CLASES OFRECIDAS: "
									+ "En Academia Learning ofrecemos cursos anuales, semestrales e intensivos. "
									+ "Además, puedes elegir la modalidad presencial u online. "
									+ "Ten en cuenta que además puedes escoger cursos de  Cambridge, Trinity, Aptis y LanguageCert");

						} else if (clientAnswer == 3) {
							out.println("INFORMACIÓN SOBRE PRECIOS Y TARIFAS: "
									+ "El precio varía en función del tipo de curso, de si es presencial, del título que hagas. "
									+ "Te recomendamos que te dirijas a alguno de nuestros centro o que llames al teléfono de atención al cliente.");

						} else if (clientAnswer == 4) {
							out.println("INFORMACIÓN SOBRE DARSE DE BAJA Y DEVOLUCIONES: "
									+ "Para cursos online, se pagará todo por adelantado, sin haber posibilidad de devolución del dinero. "
									+ "Para cursos presenciales, los pagos se harán mensualmente, por lo que se podrá dar de baja en cualquier momento, sin tener que seguir pagando el resto del curso.");
						} else if (clientAnswer == 5) {
							out.println("INFORMACIÓN SOBRE POLÍTICA DE PRIVACIDAD: "
									+ "Le informamos que estas conversaciones pueden ser grabadas con el fin de mejorar la calidad de nuestros servicios.");

						} else {
							out.println("Se ha producido un error. Por favor, inténtelo más tarde.");

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
