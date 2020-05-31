package rocket.launch.converters;

import org.springframework.stereotype.Component;
import rocket.launch.domain.Scientist;
import rocket.launch.dto.ScientistDto;

@Component
public class ScientistConverter {

    public Scientist convertToEntity(ScientistDto dto){
        return Scientist.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .password(dto.getPassword())
                .build();
    }

    public ScientistDto convertToDto(Scientist entity){
        return ScientistDto.builder()
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
                .password(entity.getPassword())
                .build();
    }
}
