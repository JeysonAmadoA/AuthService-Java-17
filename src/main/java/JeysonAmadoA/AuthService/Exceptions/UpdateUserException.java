package JeysonAmadoA.AuthService.Exceptions;

public class UpdateUserException extends Exception{

    private static final String MESSAGE  = "Error al actualizar usuario.";

    public UpdateUserException(String error) {
        super(MESSAGE + " " + error);
    }

    public UpdateUserException() {
        super(MESSAGE);
    }
}
