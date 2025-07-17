package vn.tqkhanhsn.controller.request;

import lombok.Getter;
import lombok.ToString;
import vn.tqkhanhsn.common.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String username;
    private String email;
    private String phone;
    private Gender gender;
    private List<AddressRequest> addresses;
}
