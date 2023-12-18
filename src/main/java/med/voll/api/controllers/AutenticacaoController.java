package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.AutenticacaoUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.TokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Object> logar(@RequestBody @Valid AutenticacaoUsuario autenticacaoUsuario) {
        var authToken = new UsernamePasswordAuthenticationToken(autenticacaoUsuario.login(), autenticacaoUsuario.senha());
        var auth = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(jwtToken));
    }
}
