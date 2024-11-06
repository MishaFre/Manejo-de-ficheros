package ProgramaEligeTuCesta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProgramaEligeTuCesta extends Empleado {
	
	/* como esta clase hereda de Empleados, importamos el constructor */
	public ProgramaEligeTuCesta(String id, String nombre, String apellido1, String apellido2, String edad, String sexo, String telefono, String salario, String dni) {
		super(id, nombre, apellido1, apellido2, edad, sexo, telefono, salario, dni);
	} // constructor
		
	public static void main(String args []) {
		
		/* creamos un menu que recoja pida y recoja en un String el archivo al que queremos acceder */
		String ficheroUsuario = seleccionFichero();
		
		/*  creamos los direcotorios donde guardaremos los archivos */
		crearDirectorios();
		
		/* La primera parte del ejercicio consistirá en escribir los datos de un fichero .txt a un .dat */
		int objetosEscritos = crearPuntoDat(ficheroUsuario);
		
		/* La segunda parte del ejercicio consistira en convertir el archivo .dat en los XML que nos piden */
		crearXML(objetosEscritos);
	} // main 
	
	public static String seleccionFichero() {
		
		/* menu que nos pide por pantalla el nombre del archivo al que queremos acceder */
		Scanner sc = new Scanner(System.in);
		String ficheroUsuario;
		boolean salirMenu = false;
		do {
			System.out.print("Indique el nombre del fichero que desea manipular (incluida la extensión del mismo): ");
			ficheroUsuario = sc.nextLine();
			if(ficheroUsuario.trim().isEmpty()) {
				System.out.println("Nombre de documento no válido.");
				ficheroUsuario = null;
			} else {
			salirMenu = true;
			}
		} while(salirMenu == false);
		sc.close();
		return ficheroUsuario;
	} // seleccionFichero 
	
	public static void crearDirectorios() {
		
		/* metodo que crea las carpetas donde almacenaremos el archivo .dat y los XML */
		File directorioPuntoDat = new File("Empleados");
		directorioPuntoDat.mkdir();
		
		File directorioXML = new File("Subvenciones");
		directorioXML.mkdir();
		
		System.out.println(".> Creando directorios de almacenamiento...");
			
	} // crearDirectorios
	
	public static int crearPuntoDat(String ficheroUsuario) {
		
		/* creamos las variables que utilizaremos para trabajar nuestro .txt */
		String lineaLeida; // leerá cada línea del .txt
		int objetosEscritos = 0; // lleva la cuenta de la linea leida
		String[] camposLeidos = new String[9]; // almacena los 9 campos escritos del .txt que hemos leido
		
		try {
			
			/* abrimos el flujo de datos para leer el archivo .txt */
			System.out.println(".> Creando flujo de datos para lectura...");
			File ficheroLeido = new File("Incorporaciones/" + ficheroUsuario);
			BufferedReader lectorTxt = new BufferedReader(new FileReader(ficheroLeido));
			System.out.println(".> Fichero .txt leido...");
			
			/* abrimos el flujo de datos para escribir en el fichero .dat */
			System.out.println(".> Creando archivo .dat...");
			File empleadosDat = new File("Empleados/empleadosNavidad.dat");
			System.out.println(".> Creando flujo de datos para la escritura...");
			FileOutputStream ficheroSalida = new FileOutputStream(empleadosDat);
			ObjectOutputStream objetoSalida = new ObjectOutputStream(ficheroSalida);
			System.out.println(".> Comenzando la escritura de objetos...");
			
			while((lineaLeida = lectorTxt.readLine()) != null) {
				
				/* separamos los campos leidos por el caracter '#' */
				camposLeidos = lineaLeida.split("#");
				
				/* metemos cada campo en en el constructor para crear el objeto empleado */
				Empleado empleado = new Empleado (camposLeidos[0], camposLeidos[1], camposLeidos[2], camposLeidos[3], camposLeidos[4], camposLeidos[5], camposLeidos[6], camposLeidos[7], camposLeidos[8]);
				
				/* escribimos los objetos en el fichero .dat */
				objetoSalida.writeObject(empleado);
				
				/* con cada objeto que escribimos, aumentamos el contador para llevar la cuenta de cuantos objetos escribimos */
				objetosEscritos++;
				
			} // while 
			
			System.out.println(".> Escritura de objetos terminada...");
			
			/* cerramos el flujo de datos de lectura y escritura */
			System.out.println(".> Cerrando flujo de datos para lectura del archivo...");
			lectorTxt.close();
			System.out.println(".> Cerrando flujo de datos para escritura de objetos...");
			ficheroSalida.close();
			objetoSalida.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Archivo no encontrado.");
		} catch (IOException e) {
			System.out.println("ERROR: Se encontró un error a la hora de leer el archivo.");
		} // bloques try - catch
		
		return objetosEscritos;
		
	} // crearPuntoDat
	
	public static void crearXML(int objetosEscritos) {
		
		try {
			
			/* abrimos el flujo de datos para la creacion del archivo XML */
			File binarioLeido = new File("Empleados/empleadosNavidad.dat");
			FileInputStream entradaBinario = new FileInputStream(binarioLeido);
			ObjectInputStream lecturaObjeto = new ObjectInputStream(entradaBinario);
			System.out.println(".> FLujo de datos para la lectura de objetos completo...");
			
			/* creamos un ArrayList que almacene nuestros objetos Empleado
			 * y otras dos listas mas que guarden los empleados menores de 25 y mayores de 55 */
			ArrayList<Empleado> listaObjetos = new ArrayList<>();
			ArrayList<Empleado> menores25 = new ArrayList<>();
			ArrayList<Empleado> mayores55 = new ArrayList<>();
			System.out.println(".> Creando listas de empleados...");
			
			/* volcamos todos nuestros objetos empleado en el ArrayList
			 * para ello primero instanciamos un objeto empleado */
			Empleado empleado;
			for(int i = 0; i < objetosEscritos; i++) {
				empleado = (Empleado) lecturaObjeto.readObject();
				listaObjetos.add(empleado);
			} // for 
			
			/* recorremos nuestra lista principal para enviar los empleados pertinentes a su correspondiente lista */
			for(int j = 0; j < objetosEscritos; j++) {
				
				/* primero convertimos el campo edad de String a int */
				int edadMirada = Integer.parseInt(listaObjetos.get(j).getEdadEmpleado());
				
				/* definimos las condiciones para enviar cada empleado a su lista correspondiente */
				if(edadMirada < 25) {
					menores25.add(listaObjetos.get(j));
				} else if (edadMirada > 55) {
					mayores55.add(listaObjetos.get(j));
				} // if - else if
				
				/* volvemos a convertir la edad en una variable tipo String */
				String edadString = String.valueOf(edadMirada);
			} // for 
			
			System.out.println(".> Añadiendo cada empleado a su lista correspondiente...");
			
			/* comenzamos a escribir nuestro XML */
			System.out.println(".> Comenzando escritura de XMLs...");
			
			/* instanciamos el DocumentBuilderFactory */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			System.out.println(".> Instanciando DocumentBuilderFactory...");
			
			/* construimos el Parser */
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println(".> Construyendo Parser...");
			
			/* utilizamos la interfaz DOMImplementatio */
			DOMImplementation implementation = builder.getDOMImplementation();
			System.out.println(".> Implementando interfaz DOM...");
			
			System.out.println(".> Creando XML virtual para empleados menores de 25 años...");
			
			/* creamos el documento XML vacio */
			Document documento25 = implementation.createDocument(null, "Contrataciones25.xml", null);
			
			/* asignamos una version al XML */
			documento25.setXmlVersion("1.0");
			
			/* recorremos nuestra lista de empleados menores de 25 */
			for(int k = 0; k < menores25.size(); k++) {
				Empleado empleado25 = menores25.get(k);
				
				/* creamos el nodo raiz Empleados */
				Element raiz = documento25.createElement("empleado");
				
				/* agregamos el nodo a la raiz del XML */
				documento25.getDocumentElement().appendChild(raiz);
				
				/* agregamos los elementos a cada elemento empleado */
				crearElemento("DNI", empleado25.getDniEmpleado(), raiz, documento25);
				crearElemento("Nombre", empleado25.getNombreEmpleado(), raiz, documento25);
				crearElemento("Apellidos", (empleado25.getApellido1Empleado() + " " + empleado25.getApellido2Empleado()), raiz, documento25);
				crearElemento("Edad", empleado25.getEdadEmpleado(), raiz, documento25);
				crearElemento("Telefono", empleado25.getTelefonoEmpleado(), raiz, documento25);
				crearElemento("Sexo", empleado25.getSexoEmpleado(), raiz, documento25);
				double salarioFinal = (Double.parseDouble(empleado25.getSalarioEmpleado().replace(",", ".")) * 14);
				String salarioString = String.valueOf(salarioFinal);
				crearElemento("Sueldo", salarioString, raiz, documento25);
			} // for 
			
			System.out.println(".> XML virtual de empleados menores de 25 años creado...");
			
			/* generamos el fichero XML fisico a partir del documento creado */
			
			System.out.println(".> Generando XML físico de empleados menores de 25 años...");
			
			DOMSource source25 = new DOMSource (documento25);
			StreamResult resultado25 = new StreamResult (new java.io.File("Subvenciones/Contrataciones25.xml"));
			Transformer transformer25 = TransformerFactory.newInstance().newTransformer();
			transformer25.transform(source25, resultado25);
			
			System.out.println(".> XML físico de empleados menores de 25 años creado con éxito...");
			
			/* repetimos el proceso para crear el XML de empleados mayores de 55 años */
			/* creamos el documento XML vacio */
			
			System.out.println(".> Generando XML virtual para empleados mayores de 55 años...");
			
			Document documento55 = implementation.createDocument(null, "Contrataciones55.xml", null);
			
			/* asignamos una version al XML */
			documento55.setXmlVersion("1.0");
			
			/* recorremos nuestra lista de empleados menores de 25 */
			for(int k = 0; k < mayores55.size(); k++) {
				Empleado empleado55 = mayores55.get(k);
				
				/* creamos el nodo raiz Empleados */
				Element raiz = documento55.createElement("empleado");
				
				/* agregamos el nodo a la raiz del XML */
				documento55.getDocumentElement().appendChild(raiz);
				
				/* agregamos los elementos a cada elemento empleado */
				crearElemento("DNI", empleado55.getDniEmpleado(), raiz, documento55);
				crearElemento("Nombre", empleado55.getNombreEmpleado(), raiz, documento55);
				crearElemento("Apellidos", (empleado55.getApellido1Empleado() + " " + empleado55.getApellido2Empleado()), raiz, documento55);
				crearElemento("Edad", empleado55.getEdadEmpleado(), raiz, documento55);
				crearElemento("Telefono", empleado55.getTelefonoEmpleado(), raiz, documento55);
				crearElemento("Sexo", empleado55.getSexoEmpleado(), raiz, documento55);
				double salarioFinal = (Double.parseDouble(empleado55.getSalarioEmpleado().replace(",", ".")) * 14);
				String salarioString = String.valueOf(salarioFinal);
				crearElemento("Sueldo", salarioString, raiz, documento55);
			} // for 
			
			System.out.println(".> XML virtual de empleados mayores de 55 años creado...");
			
			/* generamos el fichero XML fisico a partir del documento creado */
			
			System.out.println(".> Generando XML físico de empleados mayores de 55 años...");
			
			DOMSource source55 = new DOMSource (documento55);
			StreamResult resultado55 = new StreamResult (new java.io.File("Subvenciones/Contrataciones55.xml"));
			Transformer transformer55 = TransformerFactory.newInstance().newTransformer();
			transformer55.transform(source55, resultado55);
			
			System.out.println(".> XML físico de empleados mayores de 55 años creado con éxito...");
			
			System.out.println(".> Fichero XML generados con éxito...");
			
			System.out.println(".> Cerrando los flujos de datos...");
			
			entradaBinario.close();
			lecturaObjeto.close();
			
			System.out.println(".> Programa terminado.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No se ha podido realizar la lectura del objeto.");
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR: No se pudo crear la factoría.");
		} catch (TransformerConfigurationException e) {
			System.out.println("ERROR: No se pudo transformar el archivo.");
		} catch (TransformerFactoryConfigurationError e) {
			System.out.println("ERROR: No se pudo transformar el archivo.");
		} catch (TransformerException e) {
			System.out.println("ERROR: Hubo un fallo con la transfomacion del archivo");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar el archivo buscado.");
		} catch (IOException e) {
			System.out.println("ERROR: No se pudo establecer el flujo de lectura y escritura.");
		} // bloque try - catch
		
	} // crear XML
		
	private static void crearElemento(String datoEmpleado, String valorDato, Element raiz, Document documento) {
		Element elemen = documento.createElement(datoEmpleado);
		Text text = documento.createTextNode(valorDato);
		raiz.appendChild(elemen);
		elemen.appendChild(text);
	} // crearElemento
	
} // clase ProgramaEligeTuCesta