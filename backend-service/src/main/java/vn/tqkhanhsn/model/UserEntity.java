package vn.tqkhanhsn.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import vn.tqkhanhsn.common.Gender;
import vn.tqkhanhsn.common.UserStatus;
import vn.tqkhanhsn.common.UserType;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="firstName", length = 255)
    private String firstName;

    @Column(name="lastName", length = 255)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name="gender")
    private Gender gender;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name="username", unique = true, nullable = false, length = 255)
    private String username;

    @Column(name="password", length = 255)
    private String password;

    @Column(name="email", length = 255)
    private String email;

    @Column(name="phone", length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name="type")
    private UserType type;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name="status")
    private UserStatus status;

    @Column(name="createdAt", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updatedAt", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;



}
