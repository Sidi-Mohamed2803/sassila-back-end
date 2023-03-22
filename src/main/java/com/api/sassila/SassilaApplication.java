package com.api.sassila;

import com.api.sassila.modele.Femme;
import com.api.sassila.modele.Homme;
import com.api.sassila.modele.Individu;
import com.api.sassila.repository.IndividuRepository;
import com.api.sassila.service.IndividuServiceImplement;
import com.api.sassila.service.SectionServiceImplement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import static java.lang.System.out;

@SpringBootApplication
@Slf4j
public class SassilaApplication {
	static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(SassilaApplication.class, args);

		IndividuRepository individuRepository = ctx.getBean(IndividuRepository.class);
		IndividuServiceImplement individuServiceImplement = ctx.getBean(IndividuServiceImplement.class);
//		Homme minato = (Homme) individuServiceImplement.recupererUnIndividu("H01011970TOUMINA3");
//		Femme kushina = (Femme) individuServiceImplement.recupererUnIndividu("F01011970KONKUUZ4");
//		log.info(minato.getEnfants().size()+" Kushina's children");
//		//kushina.setEnfants(minato.getEnfants());
//		log.info(minato.getEnfants().size()+" Minato's");
		//individuRepository.save(kushina);

//		individuServiceImplement.supprimerIndividu("F13032023DDFGFFG11");
//		Homme naruto = (Homme) individuServiceImplement.recupererUnIndividu("H28032001BAMSIMOKO1");
//		//out.println(Arrays.toString(naruto.getImageUrl()));
//		File f = new File("C:\\Users\\sidik\\Downloads\\sidi_mohamed_kone.jpg");
//		try {
////			out.println(Base64.getEncoder().encodeToString(Files.readAllBytes(f.toPath())));
////			out.println(Arrays.toString(Files.readAllBytes(f.toPath())));
////			out.println(f.toPath());
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		try {
//			naruto.setImageUrl(Base64.getEncoder().encodeToString(Files.readAllBytes(f.toPath())).getBytes());
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		individuRepository.save(naruto);
		//Base64.getEncoder().encode(file.getBytes());

//		SectionServiceImplement sectionServiceImplement = ctx.getBean(SectionServiceImplement.class);
//		out.println(sectionServiceImplement.recupererSectionsParIndividu("H01011995KONNAUZ5"));
//		Homme homme = new Homme("KONÉ", "Sidi Mohamed", "", LocalDate.of(2001,3,28),"Bamako", "Sfax");
//		Femme femme = new Femme("TOURÉ", "Aminata", "", LocalDate.of(2001,4,25),"Bamako","Sfax");
//		individuServiceImplement.ajouterIndividu(homme);
//		individuServiceImplement.ajouterIndividu(femme);
//		Homme minato = new Homme("NAMIKAZE", "Minato", "", LocalDate.of(1970,1,1),"Tourbillon", "Konoha");
//		Femme kushina = new Femme("UZUMAKI", "Kushina", "", LocalDate.of(1970,1,1),"Konoha", "Konoha");
//		Homme naruto = new Homme("UZUMAKI", "Naruto", "", LocalDate.of(1995,1,1),"Konoha", "Konoha");
//		Femme hinata = new Femme("HYUGA", "Hinata", "", LocalDate.of(1995,1,1),"Konoha", "Konoha");
//		Homme boruto = new Homme("UZUMAKI", "Boruto", "", LocalDate.of(2018,1,1),"Konoha", "Konoha");
//		Femme himawari = new Femme("UZUMAKI", "Himawari", "", LocalDate.of(2021,1,1),"Konoha", "Konoha");
//
//		individuServiceImplement.ajouterIndividu(minato);
//		individuServiceImplement.ajouterIndividu(kushina);
//		individuServiceImplement.ajouterIndividu(naruto);
//		individuServiceImplement.ajouterIndividu(hinata);
//		individuServiceImplement.ajouterIndividu(boruto);
//		individuServiceImplement.ajouterIndividu(himawari);
//
//		minato.epouser(kushina);
//		minato.getEnfants().add(naruto);
//		kushina.getEnfants().add(naruto);
//		naruto.epouser(hinata);
//		naruto.getEnfants().addAll(new ArrayList<Individu>(Arrays.asList(boruto,himawari)));
//		hinata.getEnfants().addAll(new ArrayList<Individu>(Arrays.asList(boruto,himawari)));
//
//		individuRepository.save(minato);
//		individuRepository.save(kushina);
//		individuRepository.save(naruto);
//		individuRepository.save(hinata);
//		individuRepository.save(boruto);
//		individuRepository.save(himawari);
	}

//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200", "http://10.0.2.2:8000", "http://localhost:53078", "http://localhost:8000"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
//				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
//		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
//				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//		return new CorsFilter(urlBasedCorsConfigurationSource);
//	}

}
