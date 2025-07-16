package vn.tqkhanhsn.controller.request;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String username;
    private String email;
    private String phone;
    private String gender;
    private List<AddressRequest> addresses;
}
