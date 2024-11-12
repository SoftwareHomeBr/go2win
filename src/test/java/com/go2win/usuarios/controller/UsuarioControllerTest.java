package com.go2win.usuarios.controller;

import com.go2win.usuarios.model.Usuario;
import com.go2win.usuarios.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodas_DeveRetornarListaDeUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alice");

        when(usuarioService.listarTodos()).thenReturn(Arrays.asList(usuario));

        List<Usuario> usuarios = usuarioController.listarTodas();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Alice", usuarios.get(0).getNome());
    }

    @Test
    void buscarPorId_DeveRetornarUsuarioQuandoExistir() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alice");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alice", response.getBody().getNome());
    }

    @Test
    void buscarPorId_DeveRetornar404QuandoNaoExistir() {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.buscarPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void salvar_DeveRetornarUsuarioSalvo() {
        Usuario usuario = new Usuario();
        usuario.setNome("Alice");

        when(usuarioService.salvar(usuario)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.salvar(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Alice", response.getBody().getNome());
    }

    @Test
    void atualizar_DeveAtualizarUsuarioQuandoExistir() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNome("Alice");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Alice Updated");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuarioAtualizado);

        ResponseEntity<Usuario> response = usuarioController.atualizar(1L, usuarioAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alice Updated", response.getBody().getNome());
    }

    @Test
    void deletar_DeveExcluirUsuarioQuandoExistir() {
        Long id = 1L;
        when(usuarioService.buscarPorId(id)).thenReturn(Optional.of(new Usuario()));

        ResponseEntity<Void> response = usuarioController.deletar(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).deletar(id);
    }
}

