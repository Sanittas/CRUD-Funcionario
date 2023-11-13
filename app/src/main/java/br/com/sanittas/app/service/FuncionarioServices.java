package br.com.sanittas.app.service;

import br.com.sanittas.app.api.configuration.security.jwt.GerenciadorTokenJwt;
import br.com.sanittas.app.exception.ValidacaoException;
import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.FuncionarioRepository;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaLoginDto;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaTokenDto;
import br.com.sanittas.app.service.empresa.dto.EmpresaMapper;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioMapper;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionario;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionarioAtualizacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FuncionarioServices {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    public List<ListaFuncionario> listaFuncionarios() {
        var funcionarios = repository.findAll();
        List<ListaFuncionario> listaFunc = new ArrayList<>();
        for (Funcionario funcionario : funcionarios) {
            criarDtoFuncionarios(funcionario, listaFunc);
        }
        return listaFunc;
    }

    private static void criarDtoFuncionarios(Funcionario funcionario,  List<ListaFuncionario> listaFuncionarios) {
        var funcionarioDto = new ListaFuncionario(
                funcionario.getId(),
                funcionario.getFuncional(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getRg(),
                funcionario.getEmail(),
                funcionario.getSenha(),
                funcionario.getNumeroRegistroAtuacao(),
                funcionario.getIdEmpresa()
        );
        listaFuncionarios.add(funcionarioDto);
    }

    public ListaFuncionarioAtualizacao atualizar(Integer id, Funcionario dados) {
        var funcionario = repository.findById(id);
        if (funcionario.isPresent()) {
            funcionario.get().setFuncional(dados.getFuncional());
            funcionario.get().setNome(dados.getNome());
            funcionario.get().setCpf(dados.getCpf());
            funcionario.get().setRg(dados.getRg());
            funcionario.get().setEmail(dados.getEmail());
            funcionario.get().setSenha(dados.getSenha());
            funcionario.get().setNumeroRegistroAtuacao(dados.getNumeroRegistroAtuacao());

            ListaFuncionarioAtualizacao FuncionarioDto = new ListaFuncionarioAtualizacao(
                    funcionario.get().getId(),
                    funcionario.get().getFuncional(),
                    funcionario.get().getNome(),
                    funcionario.get().getCpf(),
                    funcionario.get().getRg(),
                    funcionario.get().getEmail(),
                    funcionario.get().getSenha(),
                    funcionario.get().getNumeroRegistroAtuacao()
            );
            repository.save(funcionario.get());
            return FuncionarioDto;
        }
        return null;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ValidacaoException("Usuário não existe!");
        }
        repository.deleteById(id);
    }

    public Funcionario buscar(Integer id) {
        var funcionario = repository.findById(id);
        if (funcionario.isEmpty()) {
            throw new ValidacaoException("FUncionário não existe!");
        }
        return funcionario.get();
    }

    public EmpresaTokenDto autenticar(EmpresaLoginDto empresaLoginDto) {
        log.info("Autenticando empresa com CNPJ: {}", empresaLoginDto.cnpj());
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                empresaLoginDto.cnpj(), empresaLoginDto.senha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Empresa empresaAutenticada =
                empresaRepository.findByCnpj(empresaLoginDto.cnpj())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "CNPJ não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwtToken = gerenciadorTokenJwt.gerarToken(authentication);

        log.info("Autenticação bem-sucedida para empresa com CNPJ: {}", empresaLoginDto.cnpj());

        return EmpresaMapper.of(empresaAutenticada, jwtToken);
    }

    public void cadastrar(FuncionarioCriacaoDto funcionarioCriacaoDto) {
        final Funcionario novoFuncionario = FuncionarioMapper.of(funcionarioCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoFuncionario.getSenha());
        novoFuncionario.setSenha(senhaCriptografada);

        repository.save(novoFuncionario);
    }

}
