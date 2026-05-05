package com.pedro.finance.api.service;

import com.pedro.finance.api.Exception.RegraNegocioException;
import com.pedro.finance.api.dto.UsuarioRequestDTO;
import com.pedro.finance.api.dto.UsuarioResponseDTO;
import com.pedro.finance.api.entity.*;
import com.pedro.finance.api.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO usuarioDTO){

        if(usuarioRepository.existsByEmail(usuarioDTO.getEmail())){
            throw new RegraNegocioException("Email já cadastrado");
        }


        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail().toLowerCase());
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail()
        );
    }

    public List<UsuarioResponseDTO> listar(){
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioResponseDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail()
                ))
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO usuarioDTO){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getSenha() != null && ! usuarioDTO.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail()
        );
    }

    @Transactional
    public void delete(Long id){
        String emailLogado = getEmailUsuarioLogado();

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(emailLogado)) {
            throw new RegraNegocioException("Você só pode deletar sua própria conta");
        }

        usuarioRepository.delete(usuario);
    }

    private String getEmailUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }
}
