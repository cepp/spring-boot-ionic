package br.com.ceppantoja.cursomc.service.validation;

import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.dto.ClienteDTO;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> fieldMessages = new ArrayList<>();

        Map<String, String> map = (Map<String, String>) this.request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer id = Integer.parseInt(map.get("id"));

        Cliente dbObj = this.repo.findByEmail(objDTO.getEmail());
        if(dbObj != null && !id.equals(dbObj.getId())) {
            fieldMessages.add(new FieldMessage("email", "E-mail já existente"));
        }

        if(!fieldMessages.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            fieldMessages.forEach(obj -> constraintValidatorContext.buildConstraintViolationWithTemplate(obj.getFieldMessage()).addPropertyNode(obj.getFieldName()).addConstraintViolation());
        }

        return fieldMessages.isEmpty();
    }

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }
}
