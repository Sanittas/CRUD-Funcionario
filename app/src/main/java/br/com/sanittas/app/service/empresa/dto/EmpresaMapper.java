package br.com.sanittas.app.service.empresa.dto;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaTokenDto;

public class EmpresaMapper {

    public static EmpresaTokenDto of(Empresa empresa, String token) {
        EmpresaTokenDto empresaTokenDto = new EmpresaTokenDto();

        empresaTokenDto.setEnterpriseId(empresa.getId());
        empresaTokenDto.setRazaoSocial(empresa.getRazaoSocial());
        empresaTokenDto.setCnpj(empresa.getCnpj());
        empresaTokenDto.setToken(token);

        return empresaTokenDto;
    }
}
