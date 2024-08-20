package kafkademo.taskmanagersystem.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateLabelRequestDto {
    @NotBlank
    @Size(max = 30, message = "Label name can't be longer than 30 characters")
    private String name;
    @NotBlank
    @Size(max = 30, message = "Label color can't be longer than 30 characters")
    private String color;
}
