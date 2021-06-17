package fr.univ.rouen.xmas.repositories;
import fr.univ.rouen.xmas.entities.Item;
import fr.univ.rouen.xmas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByUser(User user);
}
