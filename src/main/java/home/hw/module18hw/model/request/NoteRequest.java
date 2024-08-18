package home.hw.module18hw.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteRequest {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    @NotNull(message = "Title not found")
    private String title;
    private String content;
}
