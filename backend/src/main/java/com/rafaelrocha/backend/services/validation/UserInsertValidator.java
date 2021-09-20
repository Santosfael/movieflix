package com.rafaelrocha.backend.services.validation;

import com.rafaelrocha.backend.dto.UserInsertDTO;
import com.rafaelrocha.backend.entities.User;
import com.rafaelrocha.backend.repositories.UserRepository;
import com.rafaelrocha.backend.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserInsertDTO userInsertDTO, ConstraintValidatorContext context) {
        List<FieldMessage> listErrors = new ArrayList<>();
        User user = userRepository.findByEmail(userInsertDTO.getEmail());

        if(user != null) {
            listErrors.add(new FieldMessage("email", "E-mail já existe"));
        }

        for(FieldMessage e : listErrors) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return listErrors.isEmpty();
    }
}
