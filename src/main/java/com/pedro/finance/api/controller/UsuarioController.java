package com.pedro.finance.api.controller;


import com.pedro.finance.api.dto.TransacaoResponseDTO;
import com.pedro.finance.api.dto.UsuarioRequestDTO;
import com.pedro.finance.api.entity.Usuario;
import com.pedro.finance.api.dto.UsuarioResponseDTO;
import com.pedro.finance.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioResponseDTO salvar(@RequestBody @Valid UsuarioRequestDTO usuario){
        return usuarioService.salvar(usuario);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar(){
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarId(@PathVariable @Valid Long id){
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable @Valid long id, @RequestBody UsuarioRequestDTO usuario){
        return usuarioService.atualizar(id,usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Valid Long id){
        this.usuarioService.delete(id);
    }



}
