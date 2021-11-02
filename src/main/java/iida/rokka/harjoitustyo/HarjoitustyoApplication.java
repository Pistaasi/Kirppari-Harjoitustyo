package iida.rokka.harjoitustyo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import iida.rokka.harjoitustyo.domain.Category;
import iida.rokka.harjoitustyo.domain.CategoryRepository;
import iida.rokka.harjoitustyo.domain.Item;
import iida.rokka.harjoitustyo.domain.ItemRepository;
import iida.rokka.harjoitustyo.domain.User;
import iida.rokka.harjoitustyo.domain.UserRepository;

@SpringBootApplication
public class HarjoitustyoApplication {

	private static final Logger log = LoggerFactory.getLogger(HarjoitustyoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HarjoitustyoApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(ItemRepository repository, CategoryRepository catrepository,
			UserRepository urepository) {
		return (args) -> {

			log.info("save some categories");
			catrepository.save(new Category("Huonekalut"));
			catrepository.save(new Category("Lelut ja pelit"));
			catrepository.save(new Category("Urheilu"));
			catrepository.save(new Category("Vaatteet"));
			catrepository.save(new Category("Elektroniikka ja kodinkoneet"));
			catrepository.save(new Category("Muu"));

			log.info("save some items");
			repository.save(new Item("Maastopyörä", "Matti Meikäläinen", "Helsinki",
					"Uudehko miltei käyttämättömäksi jäänyt maastopyörä, punainen, renkaat vaihdettu 2kk sitten.",
					"matti.meikalainen@gmail.com", 150.00));
			repository.save(new Item("Sohva", "Liisa Puro", "Lohja",
					"Nahkainen sohva, musta, käyttöä ollut jonkin verran (2v), muuten hyvässä kunnossa.",
					"liisa.puro@gmail.com", 90.00));
			repository.save(new Item("Erilaisia nukkeja", "Salla Isojärvi", "Tampere",
					"Laatikollinen käytettyjä lasten vanhoja nukkeja. Esim. Barbie, Monster High ja Littlest Petshop, joiltakin puuttuu raajoja, hiuksia, vaatteita jne.",
					"salla.isojarvi@hotmail.fi", 20.00));

			// Create users: admin/admin user/user
			User user1 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN",
					"iida.rokka@hotmail.fi");
			urepository.save(user1);

			log.info("fetch all items");
			for (Item item : repository.findAll()) {
				log.info(item.toString());
			}
		};
	}

}
