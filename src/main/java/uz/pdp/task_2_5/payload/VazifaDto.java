package uz.pdp.task_2_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VazifaDto {
    private String name;
    private String discription;
    private Date givenTimeToFinish;
    private Integer managerId;
    private Integer xodimId;
    private boolean finished;
}
