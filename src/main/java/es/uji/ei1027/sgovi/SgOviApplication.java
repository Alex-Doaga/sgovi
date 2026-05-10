package es.uji.ei1027.sgovi;

import java.util.logging.Logger;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SgOviApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(SgOviApplication .class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(SgOviApplication.class).run(args);
	}

	// Funció principal
	public void run(String... strings) throws Exception {
		log.info("Ací va el meu codi");
		//Esto es para sacar el hash de la contraseña y meterlo en el técnico
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		System.out.println(passwordEncryptor.encryptPassword("12345678"));
	}
}

