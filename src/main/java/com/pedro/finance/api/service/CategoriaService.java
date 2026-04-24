package com.pedro.finance.api.service;

import com.pedro.finance.api.dto.CategoriaRequestDTO;
import com.pedro.finance.api.dto.CategoriaResponseDTO;
import com.pedro.finance.api.entity.Categoria;
import com.pedro.finance.api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaDTO){


        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        categoria.setTipo(categoriaDTO.getTipo());

        Categoria salva = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                salva.getId(),
                salva.getNome(),
                salva.getTipo()
        );
    }

    public List<CategoriaResponseDTO> listar(){
        return categoriaRepository.findAll()
                .stream()
                .map(c -> new CategoriaResponseDTO(
                        c.getId(),
                        c.getNome(),
                        c.getTipo()
                ))
                .toList();
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaDTO){

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoria.setNome(categoriaDTO.getNome());
        categoria.setTipo(categoriaDTO.getTipo());

        Categoria salva = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                salva.getId(),
                salva.getNome(),
                salva.getTipo()
        );
    }

    public void deletar(Long id){
        categoriaRepository.deleteById(id  );
    }


}
