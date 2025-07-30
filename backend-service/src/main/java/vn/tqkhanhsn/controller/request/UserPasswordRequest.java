package vn.tqkhanhsn.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserPasswordRequest implements Serializable {
    @NotNull
    @Min(value = 1,message = "ID must be greater or equal than 0")
    private Long id;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Password is mandatory")
    private String confirmPassword;
}
