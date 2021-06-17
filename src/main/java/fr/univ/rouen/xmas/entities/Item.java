package fr.univ.rouen.xmas.entities;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="toy_id")
    private Toy toy;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // Généré automatiquement par rapport a la durée de fabrication du jouet,
    // la compétence requise pour la fabriquation de jouet, et la compétence du lutin
    @Column(name = "processTime")
    private Integer processTime;

    @Column(name = "finished")
    private Boolean finished;

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
