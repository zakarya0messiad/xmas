package fr.univ.rouen.xmas;

import fr.univ.rouen.xmas.entities.Item;
import fr.univ.rouen.xmas.entities.Skill;
import fr.univ.rouen.xmas.entities.Toy;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.services.ItemService;
import fr.univ.rouen.xmas.services.SkillService;
import fr.univ.rouen.xmas.services.ToyServiceImp;
import fr.univ.rouen.xmas.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class XmasApplication implements CommandLineRunner {


	@Autowired
	private SkillService skillService;

	@Autowired
	private UserServiceImp userService;

	@Autowired
	private ToyServiceImp toyService;

	@Autowired
	private ItemService itemService;

	public static void main(String[] args) {
		SpringApplication.run(XmasApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("http://localhost:8080");
			}
		};
	}

	@Override
	public void run(String... args) {
		// compte Admin du père Noël
		User santa = new User();
		santa.setName("Père Noël");
		santa.setEmail("santa@claus.com");
		santa.setPassword("MerryChristmas2021");
		santa.setRole("ADMIN");
		userService.createUser(santa);

		// compte Admin de la mère Noël
		User user = new User();
		user.setName("Mère Noël");
		user.setEmail("mrs.santa@claus.com");
		user.setPassword("MerryXmas2021");
		user.setRole("ADMIN");

		userService.createUser(user);

		// Quelques compétences

		Skill skill = new Skill("menuiserie");
		skillService.createSkill(skill);

		Skill skill2 = new Skill("électricité");
		skillService.createSkill(skill2);

		Skill skill3 = new Skill("peinture");
		skillService.createSkill(skill3);

		Skill skill4 = new Skill("moulage plastique");
		skillService.createSkill(skill4);


		// compte Lutin 1
		User lutin = new User();
		lutin.setName("Lutin 1");
		lutin.setEmail("lutin@claus.com");
		lutin.setPassword("MerryXmas2021");
		lutin.setRole("USER");
		lutin.setSkill(skill2);

		userService.createUser(lutin);

		// compte Lutin 2
		User lutin2 = new User();
		lutin2.setName("Lutin 2");
		lutin2.setEmail("lutin2@claus.com");
		lutin2.setPassword("MerryXmas2021");
		lutin2.setRole("USER");
		lutin2.setSkill(skill);

		userService.createUser(lutin2);

		// Quelques Jouets

		Toy toy = new Toy();
		toy.setName("Barbie");
		toy.setSkill(skill4);
		toy.setProcessTime(20);
		toyService.createToy(toy);

		Toy toy2 = new Toy();
		toy2.setName("Planchettes Kapla");
		toy2.setSkill(skill);
		toy2.setProcessTime(90);
		toyService.createToy(toy2);

		Toy toy3 = new Toy();
		toy3.setName("Lego");
		toy3.setSkill(skill4);
		toy3.setProcessTime(40);
		toyService.createToy(toy3);

		// Quelques commandes
		Item commande = new Item();
		commande.setFinished(false);
		commande.setToy(toy);
		commande.setUser(lutin);
		itemService.createItem(commande);

		Item commande2 = new Item();
		commande2.setFinished(false);
		commande2.setToy(toy2);
		commande2.setUser(lutin2);
		itemService.createItem(commande2);


	}
}
