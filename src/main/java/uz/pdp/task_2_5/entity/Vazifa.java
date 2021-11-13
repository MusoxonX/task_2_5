package uz.pdp.task_2_5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.task_2_5.entity.q.VazifaXolati;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vazifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String discription;

    private Date givenTimeToFinish;

    @ManyToOne
    private User manager;

    @ManyToOne
    private User xodim;

    private VazifaXolati vazifaXolati;

    private boolean finished;
}
