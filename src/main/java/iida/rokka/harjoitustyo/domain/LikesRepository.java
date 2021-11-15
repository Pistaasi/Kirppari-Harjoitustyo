package iida.rokka.harjoitustyo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends CrudRepository<ItemLikes, Long> {

	List<ItemLikes> findAll();
}
