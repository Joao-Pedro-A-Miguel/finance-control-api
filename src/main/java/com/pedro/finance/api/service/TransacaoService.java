package com.pedro.finance.api.service;


import com.pedro.finance.api.Enum.Tipo;
import com.pedro.finance.api.Exception.RegraNegocioException;
import com.pedro.finance.api.dto.CategoriaResponseDTO;
import com.pedro.finance.api.dto.TransacaoRequestDTO;
import com.pedro.finance.api.dto.TransacaoResponseDTO;
import com.pedro.finance.api.entity.*;
import com.pedro.finance.api.repository.CategoriaRepository;
import com.pedro.finance.api.repository.TransacaoRepository;
import com.pedro.finance.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public TransacaoResponseDTO salvar(TransacaoRequestDTO transacaoDTO){



        if (transacaoDTO.getTipo() == Tipo.RECEITA && transacaoDTO.getValor() < 0) {
            throw new RegraNegocioException("Receita não pode ser negativa");
        }

        if (transacaoDTO.getTipo() == Tipo.DESPESA && transacaoDTO.getValor() < 0) {
            throw new RegraNegocioException("Despesa não pode ser negativa");
        }
        if(transacaoDTO.getCategoriaId() == null){
            throw new RegraNegocioException("Categoria ID não pode ser nulo");
        }

        if(transacaoDTO.getUsuarioId() == null){
            throw new RegraNegocioException("Usuario ID não pode ser nulo");
        }

        Categoria categoria = categoriaRepository.findById(transacaoDTO.getCategoriaId())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));

        if (!categoria.getTipo().equals(transacaoDTO.getTipo())) {
            throw new RegraNegocioException("Tipo da transação não bate com a categoria");
        }
        Usuario usuario = usuarioRepository.findById(transacaoDTO.getUsuarioId())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));




        Transacao transacao = new Transacao();
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setTipo(transacaoDTO.getTipo());
        transacao.setData(transacaoDTO.getDate());
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);

        System.out.println("Tipo transacao: " + transacao.getTipo());
        System.out.println("Tipo categoria: " + categoria.getTipo());
        System.out.println("Categoria ID: " + categoria.getId());

        return mapToResponse(transacaoRepository.save(transacao));
    }

    public List<TransacaoResponseDTO> listar(){
        return transacaoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TransacaoResponseDTO buscar(Long id){
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Transação não encontrada"));

        return mapToResponse(transacao);
    }

    public TransacaoResponseDTO atualizar(Long id, TransacaoRequestDTO dto){

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Transação não encontrada"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        if (!categoria.getTipo().equals(dto.getTipo())) {
            throw new RegraNegocioException("Tipo da transação não bate com a categoria");
        }

        if (dto.getTipo() == Tipo.RECEITA && dto.getValor() < 0) {
            throw new RegraNegocioException("Receita não pode ser negativa");
        }

        if (dto.getTipo() == Tipo.DESPESA && dto.getValor() < 0) {
            throw new RegraNegocioException("Despesa não pode ser negativa");
        }

        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(dto.getValor());
        transacao.setTipo(dto.getTipo());
        transacao.setData(dto.getDate());
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);

        return mapToResponse(transacaoRepository.save(transacao));
    }

    public void deletar(Long id){
        transacaoRepository.deleteById(id);
    }

    private TransacaoResponseDTO mapToResponse(Transacao transacao){
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getData(),
                new CategoriaResponseDTO(
                        transacao.getCategoria().getId(),
                        transacao.getCategoria().getNome(),
                        transacao.getCategoria().getTipo()
                )
        );
    }

    public Map<String, Double> resumo(int mes, int ano){
        List<Transacao> transacoes = transacaoRepository.buscarPorMesAno(mes, ano);

        double totalReceitas = transacoes.stream()
                .filter(transacao -> transacao.getTipo() == Tipo.RECEITA)
                .mapToDouble(Transacao::getValor)
                .sum();

        double totalDespesas = transacoes.stream()
                .filter(transacao ->  transacao.getTipo() == Tipo.DESPESA)
                .mapToDouble(Transacao::getValor)
                .sum();

        double saldoTotal = totalReceitas - totalDespesas;

        Map<String, Double> resumoInfo = new HashMap<>();
        resumoInfo.put("totalReceitas", totalReceitas);
        resumoInfo.put("totalDespesas", totalDespesas);
        resumoInfo.put("saldo", saldoTotal);

        return resumoInfo;
    }

    public List<TransacaoResponseDTO> filtrarPorMesAno(int mes, int ano){
        return transacaoRepository.buscarPorMesAno(mes, ano)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
