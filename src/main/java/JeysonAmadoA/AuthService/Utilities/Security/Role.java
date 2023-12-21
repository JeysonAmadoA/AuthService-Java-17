package JeysonAmadoA.AuthService.Utilities.Security;

import java.util.Arrays;
import java.util.List;


public enum Role {

    ADMIN(
            Arrays.asList(
                    Permission.UPDATE_USERS,
                    Permission.DELETE_USERS,
                    Permission.GET_ALL_USERS,
                    Permission.GET_ONE_USER
            )
    ),

    CUSTOMER(
            Arrays.asList(
                    Permission.UPDATE_USERS,
                    Permission.GET_ONE_USER
            )
    );

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
