package vn.tqkhanhsn.controller.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class UserPageResponse  extends UserPageResponseAbstract implements Serializable {
    private List<UserResponse> users;
}
