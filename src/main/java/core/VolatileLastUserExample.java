package core;

public class VolatileLastUserExample {

    private volatile String lastValidUsername;

    public boolean authenticate(final String username, final String password) {

        // In this illustrative example we assume a fixed password
        // and perform some fake authentication for testing.
        final boolean valid = "password".equals(password);
        if (valid) {
            lastValidUsername = username;
            System.out.println(username);
        }
        return valid;
    }

    public final String getLastValidUsername() {
        return lastValidUsername;
    }
}