package foot_court.users.ports.persistency.mysql.mapper;

import foot_court.users.domain.model.Role;
import foot_court.users.ports.persistency.mysql.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    RoleEntity toEntity(Role role);

    Role toRole(RoleEntity roleEntity);

    List<Role> toRolesList(List<RoleEntity> roleEntities);
}

