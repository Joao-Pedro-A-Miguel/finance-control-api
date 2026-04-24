package com.pedro.finance.api.controller;

import com.pedro.finance.api.dto.CategoriaRequestDTO;
import com.pedro.finance.api.dto.CategoriaResponseDTO;
import com.pedro.finance.api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public CategoriaResponseDTO salvar(@RequestBody @Valid CategoriaRequestDTO categoria){
        return categoriaService.salvar(categoria);

    }

    @GetMapping
    public List<CategoriaResponseDTO> listar(){
        return categoriaService.listar();
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@PathVariable @Valid Long id, @RequestBody @Valid CategoriaRequestDTO categoriaDTO){
        return categoriaService.atualizar(id,categoriaDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        categoriaService.deletar(id);
    }
}
