package vn.tqkhanhsn.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import vn.tqkhanhsn.common.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest implements Serializable {
    @NotNull
    @Min(value = 1,message = "ID must be greater or equal than 0")
    private long id;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private Date birthDate;
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    private String phone;
    private Gender gender;
    private List<AddressRequest> addresses;
}
