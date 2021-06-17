package fr.univ.rouen.xmas.entities;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "toy")
public class Toy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "toy_id")
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "processTime")
    private Integer processTime;

    @CreationTimestamp
    private Date createdAt;

    @CreationTimestamp
    private Date updatedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skill skill;

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Integer processTime) {
        this.processTime = processTime;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
