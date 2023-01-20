package pizza;

import org.springframework.data.repository.CrudRepository;

public interface Uzenetrepo extends CrudRepository<UzenetekEntity, Integer> {
    public UzenetekEntity findByTartalom(String tartalom);
}