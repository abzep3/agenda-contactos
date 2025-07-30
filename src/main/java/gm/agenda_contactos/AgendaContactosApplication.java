package gm.agenda_contactos;

import gm.agenda_contactos.modelo.Contacto;
import gm.agenda_contactos.servicio.IContactoServicio;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class AgendaContactosApplication implements CommandLineRunner { //4

	@Autowired
	private IContactoServicio contactoServicio; //1

	private static final Logger logger = LoggerFactory.getLogger(AgendaContactosApplication.class); //2

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación");
		SpringApplication.run(AgendaContactosApplication.class, args);
		logger.info("Aplicación finalizada!");  //3
	}


	@Override
	public void run(String... args) throws Exception { // 5
		logger.info("*** Aplicación Agenda Contactos ***");
		agendaContactosApp();
	}

	private void agendaContactosApp(){
		var salir = false;
		var consola = new Scanner(System.in);
		while(!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner consola){
		logger.info("""
		\n*** Agenda Contactos ***
		1. Listar Contactos
		2. Buscar Contacto
		3. Agregar Contacto
		4. Modificar Contacto
		5. Eliminar Contacto
		6. Salir
		Elige una opción:\s""");
		var opcion = Integer.parseInt(consola.nextLine());
		return opcion;
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;
		switch (opcion){
			case 1 -> {
				logger.info(nl + "--- Listado de Contactos: ---" + nl);
				List<Contacto> contactos = contactoServicio.listarContactos();
				contactos.forEach(contacto -> logger.info(contacto.toString() + nl));
			}
			case 2 -> {
				logger.info(nl + "--- Buscar Contacto por Id ---" + nl);
				logger.info("Id Contacto a buscar: ");
				var idContacto= Integer.parseInt(consola.nextLine());
				Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
				if(contacto != null)
					logger.info("Contacto encontrado: " + contacto + nl);
				else
					logger.info("Contacto NO encontrado: " + contacto + nl);
			}
			case 3 -> {
				logger.info(nl + "--- Agregar Contacto ---" + nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Telefono: ");
				var telefono = consola.nextLine();
				logger.info("Email: ");
				var email = consola.nextLine();
				logger.info("Dirección: ");
				var direccion = consola.nextLine();
				var contacto = new Contacto();
				contacto.setNombre(nombre);
				contacto.setTelefono(telefono);
				contacto.setEmail(email);
				contacto.setDireccion(direccion);
				contactoServicio.guardarContacto(contacto);
				logger.info("Contacto agregado: " + contacto + nl);
			}
			case 4 -> {
				logger.info(nl + "--- Modificar Contacto ---" + nl);
				logger.info("Id Contacto: ");
				var idContacto = Integer.parseInt(consola.nextLine());
				Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
				if(contacto != null){
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Telefono: ");
					var telefono = consola.nextLine();
					logger.info("Email: ");
					var email = consola.nextLine();
					logger.info("Dirección: ");
					var direccion = consola.nextLine();
					contacto.setNombre(nombre);
					contacto.setTelefono(telefono);
					contacto.setEmail(email);
					contacto.setDireccion(direccion);
					contactoServicio.guardarContacto(contacto);
					logger.info("Contacto modificado: " + contacto + nl);
				}
				else
					logger.info("Contacto NO encontrado: " + contacto + nl);
			}
			case 5 -> {
				logger.info ("--- Eliminar Contacto ---" + nl);
				logger.info ("Id Contacto: ");
				var idContacto = Integer.parseInt(consola.nextLine());
				var contacto = contactoServicio.buscarContactoPorId(idContacto);
				if(contacto != null){
					contactoServicio.eliminarContacto(contacto);
					logger.info("Contacto Eliminado: " + contacto + nl);
				} else {
					logger.info("Contacto NO encontrado: " + contacto + nl);
				}
			}
			case 6 -> {
				logger.info("Hasta pronto!" + nl + nl);
				salir = true;
			}
			default ->  logger.info("Opción NO reconocida: " + opcion + nl);
		}
		return salir;
	}


}
