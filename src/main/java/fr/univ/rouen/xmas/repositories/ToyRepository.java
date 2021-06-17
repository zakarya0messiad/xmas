package fr.univ.rouen.xmas.repositories;
import fr.univ.rouen.xmas.entities.Toy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ToyRepository extends JpaRepository<Toy, UUID> {
}
