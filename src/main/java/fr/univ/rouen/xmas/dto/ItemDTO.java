package fr.univ.rouen.xmas.dto;

import fr.univ.rouen.xmas.entities.Toy;
import fr.univ.rouen.xmas.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ItemDTO {

    private UUID id;
    private Toy toy;
    private User user;
    private Boolean finished;
    private Integer processTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Integer getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Integer processTime) {
        this.processTime = processTime;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean isfinished) {
        finished = isfinished;
    }

    public Toy getToy() {
        return toy;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }
}
