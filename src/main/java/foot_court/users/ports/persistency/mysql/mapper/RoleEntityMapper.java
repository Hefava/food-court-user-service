package foot_court.users.ports.persistency.mysql.mapper;

import foot_court.users.domain.model.Role;
import foot_court.users.ports.persistency.mysql.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    @Mapping(target = "roleID", source = "roleID")
    RoleEntity toEntity(Role role);

    Role toRole(RoleEntity roleEntity);

    List<Role> toRolesList(List<RoleEntity> roleEntities);
}
