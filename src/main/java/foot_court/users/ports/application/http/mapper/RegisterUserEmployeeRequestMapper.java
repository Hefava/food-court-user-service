package foot_court.users.ports.application.http.mapper;

import foot_court.users.domain.model.User;
import foot_court.users.ports.application.http.dto.RegisterUserEmployeeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterUserEmployeeRequestMapper {
    @Mapping(target = "role", ignore = true)
    User toDomain(RegisterUserEmployeeRequest request);

    RegisterUserEmployeeRequest toDto(User user);
}
