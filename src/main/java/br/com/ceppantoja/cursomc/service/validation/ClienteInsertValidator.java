package br.com.ceppantoja.cursomc.service.validation;

import br.com.ceppantoja.cursomc.domain.enums.TipoCliente;
import br.com.ceppantoja.cursomc.dto.ClienteNewDTO;
import br.com.ceppantoja.cursomc.resources.exception.FieldMessage;
import br.com.ceppantoja.cursomc.service.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> fieldMessages = new ArrayList<>();

        this.validaCpfOuCnpj(clienteNewDTO, fieldMessages);

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
