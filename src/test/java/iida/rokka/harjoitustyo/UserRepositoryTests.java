package iida.rokka.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iida.rokka.harjoitustyo.domain.User;
import iida.rokka.harjoitustyo.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;

	@Test
	public void findByTests() {
		List<User> users = repository.findByRole("ADMIN");

		User user = repository.findByUsername("user");

		assertThat(users).hasSize(1);
		assertThat(users.get(0).getUsername()).isEqualTo("admin");
		assertThat(user.getUsername().equals("user"));
	}

	@Test
	public void createUser() {
		User user2 = new User("test1", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER",
				"testi.tester@gmail.com");

		repository.save(user2);
		assertThat(user2.getId()).isNotNull();
	}

	@Test
	public void deleteUser() {
		User user1 = new User("test2", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER",
				"testi.tester@gmail.com");

		repository.save(user1);
		repository.delete(user1);
		assertThat(repository.findByUsername("test2")).isNull();
	}

}