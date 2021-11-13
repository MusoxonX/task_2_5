package uz.pdp.task_2_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull
    @Size(min = 3,max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3,max = 50)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

}
