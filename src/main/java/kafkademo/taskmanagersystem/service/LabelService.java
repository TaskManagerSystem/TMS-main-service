package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.label.CreateLabelRequestDto;
import kafkademo.taskmanagersystem.dto.label.LabelDto;

public interface LabelService {

    LabelDto create(CreateLabelRequestDto requestDto);

    List<LabelDto> gelAllLabels();

    LabelDto updateLabel(CreateLabelRequestDto updateDto, Long id);

    void deleteLabel(Long id);
}
