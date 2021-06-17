package fr.univ.rouen.xmas.services;
import fr.univ.rouen.xmas.entities.Toy;
import java.util.List;
import java.util.UUID;

public interface ToyService {
    Toy createToy(Toy toy);
    Toy updateToy(Toy toy);
    List<Toy> getAllToys();
    Toy getToyById(UUID toyId);
    void deleteToy(UUID toyId);
    Toy updateName(UUID id, Toy toy);
}
