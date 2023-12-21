package JeysonAmadoA.AuthService.Feature.Controllers.Users;

import JeysonAmadoA.AuthService.Dto.Users.UpdatePasswordDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;
import JeysonAmadoA.AuthService.Http.Controllers.Users.UserController;
import JeysonAmadoA.AuthService.Services.Users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void getUsersByIdTest(){

        UserDto userResult = new UserDto();
        userResult.setName("Jeyson");
        userResult.setLastName("Amado");
        userResult.setEmail("jeyson@example.com");
        userResult.setBirthDay(LocalDate.now());

        when(userService.getUserById(anyLong())).thenReturn(userResult);

        ResponseEntity<?> response = userController.getUsersById(1L);

        verify(userService, times(1)).getUserById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResult, response.getBody());
    }

    @Test
    public void getUsersByIdNotFoundTest(){

        when(userService.getUserById(anyLong())).thenReturn(null);

        ResponseEntity<?> response = userController.getUsersById(1L);

        verify(userService, times(1)).getUserById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody());
    }

    @Test
    public void getAllUsers(){

        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();

        List<UserDto> users = List.of(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        verify(userService, times(1)).getAllUsers();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void updateUserTest() throws UpdateUserException {

        Long userToUpdate = 1L;
        UserDto userResult = new UserDto();

        when(userService.updateUser(any(UserDto.class), anyLong())).thenReturn(userResult);

        ResponseEntity<String> response = userController.updateUser(userToUpdate, userResult);

        verify(userService, times(1)).updateUser(userResult, userToUpdate);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario actualizado con exito", response.getBody());
    }

    @Test
    public void updateUserNotFoundTest() throws UpdateUserException {

        Long userToUpdate = 1L;
        UserDto userResult = new UserDto();

        when(userService.updateUser(any(UserDto.class), anyLong())).thenReturn(null);

        ResponseEntity<String> response = userController.updateUser(userToUpdate, userResult);

        verify(userService, times(1)).updateUser(userResult, userToUpdate);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateUserFailTest() throws UpdateUserException {

        Long userToUpdate = 1L;
        UserDto userResult = new UserDto();

        when(userService.updateUser(any(UserDto.class), anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<String> response = userController.updateUser(userToUpdate, userResult);

        verify(userService, times(1)).updateUser(userResult, userToUpdate);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void changePasswordTest() throws UpdateUserException {

        UserDto userResult = new UserDto();
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();

        when(userService.updatePassword(any(UpdatePasswordDto.class), anyLong())).thenReturn(userResult);

        ResponseEntity<String> response = userController.changePassword(1L, updatePasswordDto);

        verify(userService, times(1)).updatePassword(updatePasswordDto, 1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Contraseña actualizada con exito", response.getBody());
    }

    @Test
    public void changePasswordNotFoundTest() throws UpdateUserException {

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();

        when(userService.updatePassword(any(UpdatePasswordDto.class), anyLong())).thenReturn(null);

        ResponseEntity<String> response = userController.changePassword(1L, updatePasswordDto);

        verify(userService, times(1)).updatePassword(updatePasswordDto, 1L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void changePasswordFailTest() throws UpdateUserException {

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();

        when(userService.updatePassword(any(UpdatePasswordDto.class), anyLong()))
                .thenThrow(new UpdateUserException("No coincide con la contraseña actual"));

        ResponseEntity<String> response = userController.changePassword(1L, updatePasswordDto);

        verify(userService, times(1)).updatePassword(updatePasswordDto, 1L);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error al actualizar usuario. No coincide con la contraseña actual", response.getBody());
    }

    @Test
    public void deleteUserTest() throws DeleteUserException {

        Long userToUpdate = 1L;

        when(userService.deleteUser(anyLong())).thenReturn(true);

        ResponseEntity<String> response = userController.deleteUser(userToUpdate);

        verify(userService, times(1)).deleteUser(userToUpdate);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario eliminado con exito", response.getBody());
    }

    @Test
    public void deleteUserNotFoundTest() throws DeleteUserException {

        Long userToUpdate = 1L;

        when(userService.deleteUser(anyLong())).thenReturn(false);

        ResponseEntity<String> response = userController.deleteUser(userToUpdate);

        verify(userService, times(1)).deleteUser(userToUpdate);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteUserFailTest() throws DeleteUserException {

        Long userToUpdate = 1L;

        when(userService.deleteUser(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<String> response = userController.deleteUser(userToUpdate);

        verify(userService, times(1)).deleteUser(userToUpdate);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
