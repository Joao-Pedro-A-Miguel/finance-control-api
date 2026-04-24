package com.pedro.finance.api.controller;

import com.pedro.finance.api.dto.TransacaoRequestDTO;
import com.pedro.finance.api.dto.TransacaoResponseDTO;
import com.pedro.finance.api.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public TransacaoResponseDTO salvar(@RequestBody TransacaoRequestDTO transacaoDTO){
        return transacaoService.salvar(transacaoDTO);
    }

    @GetMapping
    public List<TransacaoResponseDTO> listar(){
        return transacaoService.listar();
    }

    @GetMapping("/{id}")
    public TransacaoResponseDTO buscar(@PathVariable @Valid Long id){
        return transacaoService.buscar(id);
    }

    @PutMapping("/{id}")
    public TransacaoResponseDTO atualizar(@PathVariable @Valid Long id, @RequestBody @Valid TransacaoRequestDTO transacaoDTO){
        return transacaoService.atualizar(id,transacaoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        transacaoService.deletar(id);
    }

    @GetMapping("/relatorios/resumo")
    public Map<String, Double> resumo(@RequestParam int mes, @RequestParam int ano){
        return transacaoService.resumo(mes, ano);
    }

    @GetMapping("/filtro")
    public List<TransacaoResponseDTO> filtrar(
            @RequestParam int mes,
            @RequestParam int ano) {

        return transacaoService.filtrarPorMesAno(mes, ano);
    }



}
