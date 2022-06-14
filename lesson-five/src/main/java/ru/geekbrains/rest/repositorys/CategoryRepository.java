package ru.geekbrains.rest.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entites.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
