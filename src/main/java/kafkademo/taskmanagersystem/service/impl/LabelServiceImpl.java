package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kafkademo.taskmanagersystem.dto.label.CreateLabelRequestDto;
import kafkademo.taskmanagersystem.dto.label.LabelDto;
import kafkademo.taskmanagersystem.entity.Label;
import kafkademo.taskmanagersystem.mapper.LabelMapper;
import kafkademo.taskmanagersystem.repo.LabelRepository;
import kafkademo.taskmanagersystem.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelMapper labelMapper;
    private final LabelRepository labelRepository;

    @Override
    public LabelDto create(CreateLabelRequestDto requestDto) {
        Label label = labelMapper.toEntity(requestDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public List<LabelDto> gelAllLabels() {
        return labelRepository.findAll()
                .stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelDto updateLabel(CreateLabelRequestDto updateDto, Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can not find label by id: " + id));
        label.setName(updateDto.getName());
        label.setColor(updateDto.getColor());
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public void deleteLabel(Long id) {
        labelRepository.deleteById(id);
    }
}
