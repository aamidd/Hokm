public class User {
    private static int numberOfUsers = 0;
    private final int id;
    private String username;

    public User() {
        id = numberOfUsers++;
        username = "Player" + numberOfUsers;
    }

    public User(String username) {
        id = numberOfUsers++;
        this.username = username.isEmpty() ? "Player" + numberOfUsers : username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
