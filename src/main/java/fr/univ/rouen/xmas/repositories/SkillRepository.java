package fr.univ.rouen.xmas.repositories;
import fr.univ.rouen.xmas.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
