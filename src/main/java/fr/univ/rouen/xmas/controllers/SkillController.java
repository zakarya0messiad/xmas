package fr.univ.rouen.xmas.controllers;

import fr.univ.rouen.xmas.entities.Skill;
import fr.univ.rouen.xmas.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills(){
        return ResponseEntity.ok().body(skillService.getAllSkills());
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable UUID id){
        return ResponseEntity.ok().body(skillService.getSkillById(id));
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill){
        return ResponseEntity.ok().body(this.skillService.createSkill(skill));
    }

    @DeleteMapping("/skills/{id}")
    public HttpStatus deleteSkill(@PathVariable UUID id){
        this.skillService.deleteSkill(id);
        return HttpStatus.OK;
    }
}
