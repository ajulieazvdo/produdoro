package dev.wakandaacademy.produdoro.usuario.application.service;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {
    @InjectMocks
    private UsuarioApplicationService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void deveMudarStatusParaPausaCurta(){
        Usuario usuario = DataHelper.createUsuario();

        when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        usuarioService.mudaStatusParaPausaCurta(usuario.getEmail(), usuario.getIdUsuario());

        verify(usuarioRepository, times(1)).salva(usuario);
    }
    @Test
    void deveNaoMudarStatusParaPausaCurta_QuandoIdUsuarioForDifenrente(){
        Usuario usuario = DataHelper.createUsuario();
        UUID idUsuarioDiferente = UUID.fromString("6b320646-acd0-4f3f-ab65-895c1df31f69");

        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        APIException e = assertThrows(APIException.class,
                () -> usuarioService.mudaStatusParaPausaCurta(usuario.getEmail(), idUsuarioDiferente));

        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusException());
    }
}