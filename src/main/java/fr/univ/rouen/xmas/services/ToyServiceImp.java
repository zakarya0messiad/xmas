package fr.univ.rouen.xmas.services;
import fr.univ.rouen.xmas.dto.UserDTO;
import fr.univ.rouen.xmas.dto.UserNameDTO;
import fr.univ.rouen.xmas.entities.Toy;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.exceptions.NotFoundException;
import fr.univ.rouen.xmas.repositories.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
public class ToyServiceImp implements ToyService {

    @Autowired
    private ToyRepository toyRepository;

    @Override
    public Toy createToy(Toy toy) {
        return toyRepository.save(toy);
    }

    @Override
    public Toy updateToy(Toy toy) {
        Optional<Toy> toyDb = this.toyRepository.findById(toy.getId());

        if(toyDb.isPresent()) {
            Toy toyUpdate = toyDb.get();
            toyUpdate.setId(toy.getId());
            toyUpdate.setName(toy.getName());
            toyUpdate.setProcessTime(toy.getProcessTime());
            toyRepository.save(toyUpdate);
            return toyUpdate;
        }else {
            throw new NotFoundException("Record not found with id : " + toy.getId());
        }
    }

    @Override
    public List<Toy> getAllToys() {
        return this.toyRepository.findAll();
    }

    @Override
    public Toy getToyById(UUID toyId) {

        Optional<Toy> toyDb = this.toyRepository.findById(toyId);

        if(toyDb.isPresent()) {
            return toyDb.get();
        }else {
            throw new NotFoundException("Record not found with id : " + toyId);
        }
    }

    @Override
    public void deleteToy(UUID toyId) {
        Optional<Toy> toyDb = this.toyRepository.findById(toyId);

        if(toyDb.isPresent()) {
            this.toyRepository.delete(toyDb.get());
        }else {
            throw new NotFoundException("Record not found with id : " + toyId);
        }

    }

    public Toy updateName(UUID id, Toy toy) {
        Optional<Toy> optionalToy = toyRepository.findById(id);

        if (!optionalToy.isPresent()) {
            throw new NotFoundException("Aucun utilisateur trouvé avec l'identifiant " + id);
        }

        Toy t = optionalToy.get();
        t.setName(toy.getName());

        return toyRepository.save(t);
    }
}