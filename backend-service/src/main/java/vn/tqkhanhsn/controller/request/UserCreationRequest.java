package vn.tqkhanhsn.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.tqkhanhsn.common.Gender;
import vn.tqkhanhsn.common.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserCreationRequest implements Serializable {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String username;
    private String email;
    private Gender gender;
    private String phone;
    private UserType type;
    private List<AddressRequest> addresses;

}
