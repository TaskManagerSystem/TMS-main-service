package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kafkademo.taskmanagersystem.dto.label.CreateLabelRequestDto;
import kafkademo.taskmanagersystem.dto.label.LabelDto;
import kafkademo.taskmanagersystem.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/labels")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create a new label",
            description = "Create a new label")
    public LabelDto create(@Valid @RequestBody CreateLabelRequestDto requestDto) {
        return labelService.create(requestDto);
    }

    @Operation(summary = "Get all label",
            description = "Get a list of all label")
    @GetMapping
    public List<LabelDto> getAllLabels() {
        return labelService.gelAllLabels();
    }

    @Operation(summary = "Update label by id",
            description = "Update label by specific id")
    @PutMapping("/{id}")
    public LabelDto updateLabelById(@Valid @RequestBody CreateLabelRequestDto updateDto,
                                    @PathVariable Long id) {
        return labelService.updateLabel(updateDto, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete label by id",
            description = "Delete label by specific id")
    public void deleteLabelById(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
