package foot_court.users.ports.application.http.mapper;

import foot_court.users.domain.utils.Validation;
import foot_court.users.ports.application.http.dto.ValidationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValidationResponseMapper {
    ValidationResponse toDto(Validation validation);
}