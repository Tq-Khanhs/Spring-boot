package vn.tqkhanhsn.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class UserPageResponseAbstract implements Serializable {
    public int pageNumber;
    public int pageSize;
    public int totalPages;
    public long totalElements;
}
