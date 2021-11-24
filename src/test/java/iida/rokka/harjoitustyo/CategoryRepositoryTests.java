package iida.rokka.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iida.rokka.harjoitustyo.domain.Category;
import iida.rokka.harjoitustyo.domain.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repository;

	@Test
	public void findByTests() {
		List<Category> cats = repository.findByName("Muu");

		assertThat(cats).hasSize(1);
		assertThat(cats.get(0).getName()).isEqualTo("Muu");
	}

	@Test
	public void createCat() {

		Category cat = new Category("Testikategoria");
		repository.save(cat);
		assertThat(cat.getId()).isNotNull();
	}

	@Test
	public void deleteCat() {
		Category cat = new Category("Testikategoria2");
		repository.save(cat);
		repository.delete(cat);
		assertThat(repository.findByName("Testikategoria2")).hasSize(0);
	}

}
