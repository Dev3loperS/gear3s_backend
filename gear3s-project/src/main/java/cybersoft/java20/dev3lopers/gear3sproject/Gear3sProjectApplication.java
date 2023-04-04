package cybersoft.java20.dev3lopers.gear3sproject;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.crypto.SecretKey;

@SpringBootApplication
public class Gear3sProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gear3sProjectApplication.class, args);
		/*// Tạo key sinh ra từ hệ thống
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String jwtKey = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("Key : "+jwtKey);*/
	}

}
