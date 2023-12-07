package JeysonAmadoA.AuthService.Unit.Entities;

import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class UserEntityTest {


    @Test
    public void commitCreateTest(){
        UserEntity user = new UserEntity();
        user.commitCreate(1L);

        assertEquals(1L, user.getUserWhoCreatedId());
        assertNotNull(user.getCreatedAt());
    }

    @Test
    public void commitUpdateTest(){
        UserEntity user = new UserEntity();
        user.commitUpdate(1L);

        assertEquals(1L, user.getUserWhoUpdatedId());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    public void commitDeleteTest(){
        UserEntity user = new UserEntity();
        user.commitDelete(1L);

        assertEquals(1L, user.getUserWhoDeletedId());
        assertNotNull(user.getDeletedAt());
    }

    @Test
    public void getAgeTest(){
        UserEntity user = new UserEntity();
        user.setBirthDay(LocalDateTime.of(1997,11,23,0,0));

        assertThat(user.getAge()).isGreaterThanOrEqualTo(26);
    }
}
