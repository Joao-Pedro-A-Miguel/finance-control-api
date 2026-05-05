package com.pedro.finance.api.service;

import com.pedro.finance.api.Exception.RegraNegocioException;
import com.pedro.finance.api.dto.CategoriaRequestDTO;
import com.pedro.finance.api.dto.CategoriaResponseDTO;
import com.pedro.finance.api.entity.Categoria;
import com.pedro.finance.api.entity.Usuario;
import com.pedro.finance.api.repository.CategoriaRepository;
import com.pedro.finance.api.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaService(CategoriaRepository categoriaRepository,
                            UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaDTO){

        String email = getEmailUsuarioLogado();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        categoria.setTipo(categoriaDTO.getTipo());
        categoria.setUsuario(usuario);

        Categoria salva = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                salva.getId(),
                salva.getNome(),
                salva.getTipo()
        );
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar(){

        String email = getEmailUsuarioLogado();

        return categoriaRepository.findByUsuarioEmail(email)
                .stream()
                .map(c -> new CategoriaResponseDTO(
                        c.getId(),
                        c.getNome(),
                        c.getTipo()
                ))
                .toList();
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaDTO){

        String email = getEmailUsuarioLogado();

        Categoria categoria = categoriaRepository
                .findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));

        categoria.setNome(categoriaDTO.getNome());
        categoria.setTipo(categoriaDTO.getTipo());

        Categoria salva = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                salva.getId(),
                salva.getNome(),
                salva.getTipo()
        );
    }

    @Transactional
    public void deletar(Long id){

        String email = getEmailUsuarioLogado();

        Categoria categoria = categoriaRepository
                .findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));

        categoriaRepository.delete(categoria);
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
