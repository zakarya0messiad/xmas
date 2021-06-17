package fr.univ.rouen.xmas.services;
import fr.univ.rouen.xmas.entities.Skill;
import fr.univ.rouen.xmas.exceptions.NotFoundException;
import fr.univ.rouen.xmas.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SkillServiceImp implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public List<Skill> getAllSkills() {
        return this.skillRepository.findAll();
    }

    @Override
    public Skill getSkillById(UUID skillId) {
        Optional<Skill> skillDb = this.skillRepository.findById(skillId);

        if(skillDb.isPresent()) {
            return skillDb.get();
        }else {
            throw new NotFoundException("Record not found with id : " + skillId);
        }
    }

    @Override
    public void deleteSkill(UUID skillId) {
        Optional<Skill> skillDb = this.skillRepository.findById(skillId);

        if(skillDb.isPresent()) {
            this.skillRepository.delete(skillDb.get());
        }else {
            throw new NotFoundException("Record not found with id : " + skillId);
        }
    }
}