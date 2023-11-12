package br.com.sanittas.app.service.funcionario.dto;

import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.model.Usuario;
import br.com.sanittas.app.service.autenticacao.dto.FuncionarioTokenDto;
import br.com.sanittas.app.service.autenticacao.dto.UsuarioTokenDto;

public class FuncionarioMapper {

    public static Funcionario of(FuncionarioCriacaoDto funcionarioCriacaoDto) {
        Funcionario funcionario = new Funcionario();

        funcionario.setFuncional(funcionarioCriacaoDto.getFuncional());
        funcionario.setNome(funcionarioCriacaoDto.getNome());
        funcionario.setCpf(funcionarioCriacaoDto.getCpf());
        funcionario.setRg(funcionarioCriacaoDto.getRg());
        funcionario.setEmail(funcionarioCriacaoDto.getEmail());
        funcionario.setSenha(funcionarioCriacaoDto.getSenha());
        funcionario.setNumeroRegistroAtuacao(funcionarioCriacaoDto.getNumeroRegistroAtuacao());


        return funcionario;
    }

    public static FuncionarioTokenDto of(Funcionario funcionario, String token) {
        FuncionarioTokenDto funcionarioTokenDto = new FuncionarioTokenDto();

        funcionarioTokenDto.setUserId(funcionario.getId());
        funcionarioTokenDto.setNome(funcionario.getNome());
        funcionarioTokenDto.setEmail(funcionario.getEmail());
        funcionarioTokenDto.setToken(token);

        return funcionarioTokenDto;
    }
}

