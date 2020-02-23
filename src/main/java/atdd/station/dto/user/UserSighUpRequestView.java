package atdd.station.dto.user;

import atdd.path.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSighUpRequestView {
    private String email;
    private String name;
    private String password;

    @Builder
    public UserSighUpRequestView(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static User toEntity(UserSighUpRequestView user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
