package UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.User;

@ToString
@Getter
@Setter
public class UserInfo {
    private User user;
    private static UserInfo instance;

    public static UserInfo getInstance() {
        return instance==null?instance = new UserInfo():instance;
    }
    private UserInfo() {

    }
}
