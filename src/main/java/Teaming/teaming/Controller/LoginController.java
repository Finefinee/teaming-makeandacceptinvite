package Teaming.teaming.Controller;

import Teaming.teaming.DTO.MemberDTO;
import Teaming.teaming.Domain.MemberDomain;
import Teaming.teaming.Jwt.JwtProvider;
import Teaming.teaming.Repository.MemberRepository;
import Teaming.teaming.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        MemberDomain member = memberRepository.findByUsername(memberDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        if (!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.createToken(memberDTO.getUsername(),member.getRole());

        ResponseCookie cookie = ResponseCookie.from("token",token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header("Set-Cookie",cookie.toString())
                .body(Map.of("M","로그인이 완료되었습니다."));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
        if(memberRepository.findByUsername(memberDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }

        MemberDomain member = new MemberDomain();
        member.setUsername(memberDTO.getUsername());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setRole("ROLE_USER");
        memberRepository.save(member);
        return ResponseEntity.ok(Map.of("M","회원가입이 완료되었습니다."));
    }
}
