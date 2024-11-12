package com.go2win.usuarios.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.go2win.usuarios.controller.UsuarioController;
import com.go2win.usuarios.model.Usuario;
import com.go2win.usuarios.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarTodas_DeveRetornarListaDeUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alice");

        when(usuarioService.listarTodos()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Alice"));
    }

    @Test
    void buscarPorId_DeveRetornarUsuarioQuandoExistir() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alice");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Alice"));
    }

    @Test
    void buscarPorId_DeveRetornar404QuandoNaoExistir() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvar_DeveCriarNovoUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alice");

        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Alice"));
    }

    @Test
    void atualizar_DeveAtualizarUsuarioQuandoExistir() throws Exception {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNome("Alice");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Alice Updated");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuarioAtualizado);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Alice Updated"));
    }

    @Test
    void deletar_DeveExcluirUsuarioQuandoExistir() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
