package atdd.station.service;

import atdd.station.domain.UserRepository;
import atdd.station.dto.user.FindByEmailResponseView;
import atdd.station.dto.user.UserLoginResponseView;
import atdd.station.dto.user.UserSighUpRequestView;
import atdd.station.dto.user.UserSighUpResponseView;
import atdd.station.security.TokenAuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenAuthenticationService tokenAuthenticationService;

    public UserService(TokenAuthenticationService tokenAuthenticationService, UserRepository userRepository) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userRepository = userRepository;
    }

    public UserSighUpResponseView singUp(UserSighUpRequestView newUser) {
        FindByEmailResponseView user = userRepository.findByEmail(newUser.getEmail());

        if (!isExistUser(user)) {
            throw new ExistUserException();
        }

        return UserSighUpResponseView.toDtoEntity(userRepository.save(UserSighUpRequestView.toEntity(newUser)));
    }

    public UserLoginResponseView login(UserLoginRequestView newUser) {
        FindByEmailResponseView user = userRepository.findByEmail(newUser.getEmail());

        if (isExistUser(user)) {
            throw new NotExistUserException();
        }

        String jwt = tokenAuthenticationService.toJwtByEmail(user.getEmail());
        return UserLoginResponseView.toDtoEntity(jwt, tokenAuthenticationService.getTokenTypeByJwt(jwt));
    }

    public void delete(User user) {
        userRepository.deleteById(user.getId());
    }

    private boolean isExistUser(FindByEmailResponseView user) {
        return user.getId() == null;
    }

    public UserDetailResponseView findById(Long id) {
        return UserDetailResponseView.toDtoEntity(userRepository.findById(id));
    }
}
