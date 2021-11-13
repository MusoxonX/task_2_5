package uz.pdp.task_2_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    private Object object;

}
