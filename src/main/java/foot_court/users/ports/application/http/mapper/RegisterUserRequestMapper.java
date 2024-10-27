package foot_court.users.ports.application.http.mapper;

import foot_court.users.domain.model.User;
import foot_court.users.ports.application.http.dto.RegisterUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterUserRequestMapper {
    @Mapping(target = "role", ignore = true)
    User toDomain(RegisterUserRequest request);

    RegisterUserRequest toDto(User user);
}
