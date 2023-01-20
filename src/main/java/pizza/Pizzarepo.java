package pizza;

import org.springframework.data.repository.CrudRepository;

public interface Pizzarepo extends CrudRepository<PizzaEntity, Integer> {
}
