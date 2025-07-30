package vn.tqkhanhsn.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import vn.tqkhanhsn.common.Gender;
import vn.tqkhanhsn.common.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserCreationRequest implements Serializable {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private Date birthDate;
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    private Gender gender;
    private String phone;
    private UserType type;
    private List<AddressRequest> addresses;

}
