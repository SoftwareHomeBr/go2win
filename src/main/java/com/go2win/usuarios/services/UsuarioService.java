package com.go2win.usuarios.services;

import com.go2win.usuarios.model.Usuario;
import com.go2win.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario pessoa) {
        return usuarioRepository.save(pessoa);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
