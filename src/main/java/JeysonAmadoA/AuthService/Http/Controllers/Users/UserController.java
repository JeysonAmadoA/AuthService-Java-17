package JeysonAmadoA.AuthService.Http.Controllers.Users;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Interfaces.Services.Users.UserServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        if (user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = this.userService.updateUser(userDto, id);
            return updatedUser != null
                    ? ResponseEntity.status(HttpStatus.OK).body("Usuario actualizado con exito")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            boolean isDeleted = this.userService.deleteUser(id);
            return isDeleted
                    ? ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con exito")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
