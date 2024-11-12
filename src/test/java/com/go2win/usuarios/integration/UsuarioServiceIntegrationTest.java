package com.go2win.usuarios.integration;

import com.go2win.usuarios.model.Usuario;
import com.go2win.usuarios.repository.UsuarioRepository;
import com.go2win.usuarios.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Usa o profile de teste configurado
@Transactional // Garante que as operações de banco de dados sejam revertidas após o teste
class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll(); // Limpa os dados do banco antes de cada teste
    }

    @Test
    void listarTodos_DeveRetornarListaDeUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Alice");

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Bob");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        List<Usuario> usuarios = usuarioService.listarTodos();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @Test
    void buscarPorId_DeveRetornarUsuarioQuandoExistir() {
        Usuario usuario = new Usuario();
        usuario.setNome("Alice");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Optional<Usuario> resultado = usuarioService.buscarPorId(usuarioSalvo.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Alice", resultado.get().getNome());
    }

    @Test
    void buscarPorId_DeveRetornarOptionalVazioQuandoNaoExistir() {
        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void salvar_DevePersistirUsuarioNoBanco() {
        Usuario usuario = new Usuario();
        usuario.setNome("Alice");

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        assertNotNull(usuarioSalvo.getId());
        assertEquals("Alice", usuarioSalvo.getNome());

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuarioSalvo.getId());
        assertTrue(usuarioDoBanco.isPresent());
    }

    @Test
    void deletar_DeveRemoverUsuarioDoBanco() {
        Usuario usuario = new Usuario();
        usuario.setNome("Alice");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        usuarioService.deletar(usuarioSalvo.getId());

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuarioSalvo.getId());
        assertFalse(usuarioDoBanco.isPresent());
    }
}
