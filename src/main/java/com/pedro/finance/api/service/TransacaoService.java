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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
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

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Categoria categoria = categoriaRepository
                .findByIdAndUsuarioEmail(transacaoDTO.getCategoriaId(), email)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));

        if (!categoria.getTipo().equals(transacaoDTO.getTipo())) {
            throw new RegraNegocioException("Tipo da transação não bate com a categoria");

        }
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));



        Transacao transacao = new Transacao();
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setTipo(transacaoDTO.getTipo());
        transacao.setData(transacaoDTO.getData());
        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);

        log.info("Tipo transacao: {}", transacao.getTipo());
        log.info("Tipo categoria: {}", categoria.getTipo());
        log.info("Categoria ID: {}", categoria.getId());


        Transacao salva = transacaoRepository.save(transacao);
        log.info("SALVO ID: " + salva.getId());
        return mapToResponse(salva);

    }

    @Transactional
    public List<TransacaoResponseDTO> listar(){
        String email = getEmailUsuarioLogado();

        return transacaoRepository.findByUsuarioEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TransacaoResponseDTO buscar(Long id){

        String email = getEmailUsuarioLogado();

        Transacao transacao = transacaoRepository
                .findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RegraNegocioException("Transação não encontrada"));


        return mapToResponse(transacao);
    }

    @Transactional
    public TransacaoResponseDTO atualizar(Long id, TransacaoRequestDTO transacaoDTO){

        String email = getEmailUsuarioLogado();

        Transacao transacao = transacaoRepository
                .findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RegraNegocioException("Transação não encontrada"));

        Categoria categoria = categoriaRepository.findById(transacaoDTO.getCategoriaId())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));


        if (!categoria.getTipo().equals(transacaoDTO.getTipo())) {
            throw new RegraNegocioException("Tipo da transação não bate com a categoria");
        }

        if (transacaoDTO.getTipo() == Tipo.RECEITA && transacaoDTO.getValor() < 0) {
            throw new RegraNegocioException("Receita não pode ser negativa");
        }

        if (transacaoDTO.getTipo() == Tipo.DESPESA && transacaoDTO.getValor() < 0) {
            throw new RegraNegocioException("Despesa não pode ser negativa");
        }

        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setTipo(transacaoDTO.getTipo());
        transacao.setData(transacaoDTO.getData());
        transacao.setCategoria(categoria);


        return mapToResponse(transacaoRepository.save(transacao));
    }

    @Transactional
    public void deletar(Long id){

        String email = getEmailUsuarioLogado();

        Transacao transacao = transacaoRepository
                .findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RegraNegocioException("Transação não encontrada"));

        transacaoRepository.delete(transacao);
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

        String email = getEmailUsuarioLogado();

        List<Transacao> transacoes =
                transacaoRepository.buscarPorMesAnoEUsuario(email, mes, ano);

        double totalReceitas = transacoes.stream()
                .filter(t -> t.getTipo() == Tipo.RECEITA)
                .mapToDouble(Transacao::getValor)
                .sum();

        double totalDespesas = transacoes.stream()
                .filter(t -> t.getTipo() == Tipo.DESPESA)
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
        String email = getEmailUsuarioLogado();

        return transacaoRepository.buscarPorMesAnoEUsuario(email, mes, ano)
                .stream()
                .map(this::mapToResponse)
                .toList();
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
