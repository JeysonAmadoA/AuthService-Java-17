package JeysonAmadoA.AuthService.Exceptions;

public class DeleteUserException extends Exception{

    private static final String MESSAGE  = "Error al eliminar usuario.";

    public DeleteUserException(String error) {
        super(MESSAGE + " " + error);
    }

    public DeleteUserException() {
        super(MESSAGE);
    }
}
