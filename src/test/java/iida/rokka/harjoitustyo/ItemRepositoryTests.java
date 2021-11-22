package iida.rokka.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iida.rokka.harjoitustyo.domain.Item;
import iida.rokka.harjoitustyo.domain.ItemRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ItemRepositoryTests {

	@Autowired
	private ItemRepository repository;

	@Test
	public void findByTests() {
		List<Item> items = repository.findByName("Sohva");

		assertThat(items).hasSize(1);
		assertThat(items.get(0).getLocation()).isEqualTo("Lohja");
	}

	@Test
	public void createItem() {
		LocalDate date1 = LocalDate.now();
		Item item = new Item("Testi", "Testi Testinen", "Kerava", "Testi item testausta varten",
				"testi.testi@gmail.com", 15.00, date1, "admin");
		repository.save(item);
		assertThat(item.getId()).isNotNull();
	}

	@Test
	public void deleteItem() {
		LocalDate date1 = LocalDate.now();
		Item item2 = new Item("Testi2", "Testi Testinen", "Kerava", "Testi item testausta varten",
				"testi.testi@gmail.com", 15.00, date1, "admin");
		repository.save(item2);
		repository.delete(item2);
		assertThat(repository.findByName("Testi2")).hasSize(0);
	}

}
