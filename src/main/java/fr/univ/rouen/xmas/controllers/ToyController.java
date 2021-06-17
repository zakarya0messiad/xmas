package fr.univ.rouen.xmas.controllers;

import fr.univ.rouen.xmas.dto.ToyNameDTO;
import fr.univ.rouen.xmas.dto.UserDTO;
import fr.univ.rouen.xmas.dto.UserNameDTO;
import fr.univ.rouen.xmas.entities.Toy;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.services.ToyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToyController {
        @Autowired
        private ToyService toyService;

        @Autowired
        private ModelMapper modelMapper;

        @GetMapping("/toys")
        public ResponseEntity<List<Toy>> getAllToys(){
            return ResponseEntity.ok().body(toyService.getAllToys());
        }

        @GetMapping("/toys/{id}")
        public ResponseEntity<Toy> getToyById(@PathVariable UUID id){
            return ResponseEntity.ok().body(toyService.getToyById(id));
        }

        @PostMapping("/toys")
        public ResponseEntity<Toy> createToy(@RequestBody Toy toy){
            return ResponseEntity.ok().body(this.toyService.createToy(toy));
        }

        //Permet de modifier le nom et le nom d'un jouet.
        @PutMapping(value="/toys/{id}/name")
        public Toy updateToyName(@PathVariable UUID id, @RequestBody ToyNameDTO toyNameDTO) {
            return modelMapper.map((toyService.updateName(id, modelMapper.map(toyNameDTO, Toy.class))), Toy.class);
        }

        @PutMapping("/toys/{id}")
        public ResponseEntity<Toy> updateToy(@PathVariable UUID id, @RequestBody Toy toy){
            toy.setId(id);
            return ResponseEntity.ok().body(this.toyService.updateToy(toy));
        }

        @DeleteMapping("/toys/{id}")
        public HttpStatus deleteToy(@PathVariable UUID id){
            this.toyService.deleteToy(id);
            return HttpStatus.OK;
        }
    }