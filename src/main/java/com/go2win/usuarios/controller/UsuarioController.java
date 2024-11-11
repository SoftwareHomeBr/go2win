package com.go2win.usuarios.controller;

import com.go2win.usuarios.model.Usuario;
import com.go2win.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuario API",  description = "Gerência de Usuarios") // Documenta a classe como um recurso no Swagger
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar todas os usuarios
    @GetMapping
    @Operation(summary = "Listar todos os usuarios", description = "Retorna uma lista com todas as pessoas cadastradas.")
    public List<Usuario> listarTodas() {
        return usuarioService.listarTodos();
    }

    // Buscar uma pessoa por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID.")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Inserir uma nova pessoa
    @PostMapping
    @Operation(summary = "Inserir um novo usuário", description = "Cria uma nova pessoa ou usuário.")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario pessoa) {
        Usuario novaPessoa = usuarioService.salvar(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    // Atualizar uma pessoa existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de uma pessoa existente.")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario pessoa) {
        return usuarioService.buscarPorId(id)
                .map(p -> {
                    p.setNome(pessoa.getNome());
                    p.setEmail(pessoa.getEmail());
                    p.setDataNascimento(pessoa.getDataNascimento());
                    Usuario pessoaAtualizada = usuarioService.salvar(p);
                    return ResponseEntity.ok(pessoaAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar uma pessoa por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pessoa", description = "Deleta uma pessoa pelo ID.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (usuarioService.buscarPorId(id).isPresent()) {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
