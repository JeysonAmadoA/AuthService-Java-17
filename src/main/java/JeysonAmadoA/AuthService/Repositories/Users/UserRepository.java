package JeysonAmadoA.AuthService.Repositories.Users;

import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

    List<UserEntity> findByEmailLike(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.name LIKE %:searchTerm% OR u.lastName LIKE %:searchTerm%")
    List<UserEntity> findByNameLikeOrLastNameLike(@Param("searchTerm") String searchTerm);

}
