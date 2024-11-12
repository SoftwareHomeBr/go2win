package com.go2win.usuarios.services;

import com.go2win.usuarios.model.Usuario;
import com.go2win.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodos_DeveRetornarTodosOsUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("Alice");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Bob");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioService.listarTodos();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        assertEquals("Alice", usuarios.get(0).getNome());
        assertEquals("Bob", usuarios.get(1).getNome());
    }

    @Test
    void buscarPorId_DeveRetornarUsuarioQuandoExistir() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alice");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Alice", resultado.get().getNome());
    }

    @Test
    void buscarPorId_DeveRetornarVazioQuandoNaoExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void salvar_DeveSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Alice");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.salvar(usuario);

        assertNotNull(resultado);
        assertEquals("Alice", resultado.getNome());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deletar_DeveExcluirUsuario() {
        Long id = 1L;

        usuarioService.deletar(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
