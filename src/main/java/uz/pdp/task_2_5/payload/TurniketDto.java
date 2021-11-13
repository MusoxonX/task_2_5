package uz.pdp.task_2_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurniketDto {
    private Integer username;
    private Date comingDate;
    private Date goingDate;
    private double oylik;
}
