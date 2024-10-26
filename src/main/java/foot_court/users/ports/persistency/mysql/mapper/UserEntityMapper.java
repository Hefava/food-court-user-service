package foot_court.users.ports.persistency.mysql.mapper;

import foot_court.users.domain.model.User;
import foot_court.users.ports.persistency.mysql.entity.RoleEntity;
import foot_court.users.ports.persistency.mysql.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleEntityMapper.class})
public interface UserEntityMapper {

    @Mapping(target = "role", source = "role")
    User toUser(UserEntity userEntity);

    @Mapping(target = "role", source = "role")
    UserEntity toEntity(User user);

    List<User> toUsersList(List<UserEntity> userEntities);

    default Long map(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return roleEntity.getRoleID();
    }

    default RoleEntity map(Long roleID) {
        if (roleID == null) {
            return null;
        }
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleID(roleID);
        return roleEntity;
    }
}