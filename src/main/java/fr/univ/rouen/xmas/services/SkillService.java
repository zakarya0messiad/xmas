package fr.univ.rouen.xmas.services;
import fr.univ.rouen.xmas.entities.Skill;
import java.util.List;
import java.util.UUID;

public interface SkillService {
    Skill createSkill(Skill skill);
    List<Skill> getAllSkills();
    Skill getSkillById(UUID skillId);
    void deleteSkill(UUID skillId);
}
