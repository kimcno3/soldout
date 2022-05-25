package api.soldout.io.soldout.service.user;

import api.soldout.io.soldout.dtos.UserDto;
import api.soldout.io.soldout.service.user.command.SignUpCommand;

/**
 *.
 */

public interface UserService {

  void signUp(SignUpCommand commandDto);

  UserDto signIn(String email, String password);

  UserDto findByEmail(String email);

  boolean isExistEmail(String email);

}
