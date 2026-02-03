import java.util.concurrent.Callable;

public class MenuOption {
    Callable<Void> callable;
    String description;

    public MenuOption(Callable<Void> callable, String description) {
        this.callable = callable;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
