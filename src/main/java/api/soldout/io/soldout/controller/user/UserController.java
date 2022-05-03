package api.soldout.io.soldout.controller.user;

import api.soldout.io.soldout.dtos.user.UserDto;
import api.soldout.io.soldout.dtos.user.request.RequestSignInDto;
import api.soldout.io.soldout.dtos.user.request.RequestSignUpDto;
import api.soldout.io.soldout.dtos.user.response.ResponseDTO;
import api.soldout.io.soldout.dtos.user.response.data.SignUpData;
import api.soldout.io.soldout.exception.AlreadyExistEmailException;
import api.soldout.io.soldout.exception.NotValidEmailException;
import api.soldout.io.soldout.exception.NotValidPasswordException;
import api.soldout.io.soldout.service.security.SecurityService;
import api.soldout.io.soldout.service.user.UserServiceImpl;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * userService : 회원 정보에 대한 비즈니스 로직을 담당.
 * securityService : 로그인, 로그아웃을 위한 세션 관리를 담당
 */

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  private final SecurityService securityService;

  /**
   *.
   */

  @PostMapping("/signup")
  public ResponseDTO signUp(@RequestBody RequestSignUpDto request) {

    UserDto user = userService.signUp(request);

    return new ResponseDTO(true, SignUpData.from(user), "회원가입 성공", null);
  }

  /**
   *.
   */

  @GetMapping("/{email}/exists")
  public ResponseDTO checkEmail(@PathVariable String email) {

    // 이미 존재하는 이메일일 경우
    if (userService.isExistEmail(email)) {
      throw new AlreadyExistEmailException("이미 가입된 이메일입니다.");
    }

    return new ResponseDTO(true, null, "사용 가능한 이메일", null);
  }

  /**
   *.
   */

  @PostMapping("/signin")
  public ResponseDTO signIn(@RequestBody RequestSignInDto request, HttpSession session) {

    UserDto user = userService.findByEmail(request.getEmail());

    // 확인한 이메일로 가입된 회원이 없는 경우
    if (user == null) {
      throw new NotValidEmailException("이메일이 틀렸습니다.");
    }

    // 이메일은 맞지만 사용자가 입력한 비밀번호가 다를 경우 -> 다른 API로 구현??
    if (!userService.isValidPassword(request.getPassword(), user.getPassword())) {
      throw new NotValidPasswordException("비밀번호가 틀렸습니다.");
    }

    securityService.signIn(user.getEmail(), session);

    return new ResponseDTO(true, null, "로그인 성공", null);
  }

  /**
   *.
   */

  @PostMapping("/logout")
  public ResponseDTO logOut(HttpSession session) {

    securityService.logOut(session);

    return new ResponseDTO(true, null, "로그아웃 성공", null);
  }
}
