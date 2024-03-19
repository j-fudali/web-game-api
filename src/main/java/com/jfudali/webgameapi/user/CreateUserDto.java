package com.jfudali.webgameapi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @NotBlank(message = "Username cannot be empty")
    @Length(max = 150, message = "Username must be maximum 150" +
            " characters length")
    private String username;
    @NotBlank(message = "E-mail cannot be empty")
    @Length(max = 150, message = "E-mail must be maximum 150" +
            " characters length")
    @Email(message = "Field must be a valid e-mail")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must includes at least 1 small and " +
            "capital letter, 1 digit, 1 special character and must includes " +
            "from 8 to 20 characters")
    private String password;
}
