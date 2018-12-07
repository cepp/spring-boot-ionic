package br.com.ceppantoja.cursomc.service.validation;

import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.domain.enums.TipoCliente;
import br.com.ceppantoja.cursomc.dto.ClienteNewDTO;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.resources.exception.FieldMessage;
import br.com.ceppantoja.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Autowired
    private ClienteRepository repo;

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> fieldMessages = new ArrayList<>();

        this.validaCpfOuCnpj(clienteNewDTO, fieldMessages);

        Cliente dbObj = this.repo.findByEmail(clienteNewDTO.getEmail());
        if(dbObj != null) {
            fieldMessages.add(new FieldMessage("email", "E-mail já existente"));
        }

        if(!fieldMessages.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            fieldMessages.forEach(obj -> constraintValidatorContext.buildConstraintViolationWithTemplate(obj.getFieldMessage()).addPropertyNode(obj.getFieldName()).addConstraintViolation());
        }

        return fieldMessages.isEmpty();
    }

    private void validaCpfOuCnpj(ClienteNewDTO clienteNewDTO, List<FieldMessage> fieldMessages) {
        TipoCliente tipo = TipoCliente.toEnum(clienteNewDTO.getTipo());
        if(TipoCliente.PESSOAFISICA.equals(tipo) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
            fieldMessages.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(TipoCliente.PESSOAJURIDICA.equals(tipo) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
            fieldMessages.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

    }

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }
}
