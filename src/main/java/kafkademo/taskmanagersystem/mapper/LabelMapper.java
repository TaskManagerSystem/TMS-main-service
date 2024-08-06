package kafkademo.taskmanagersystem.mapper;

import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.label.CreateLabelRequestDto;
import kafkademo.taskmanagersystem.dto.label.LabelDto;
import kafkademo.taskmanagersystem.entity.Label;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {

    LabelDto toDto(Label label);

    Label toEntity(CreateLabelRequestDto requestDto);
}
