package ru.xdpxrt.vinyl.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.cons.Role;

import java.time.LocalDate;

import static ru.xdpxrt.vinyl.cons.Config.DATE_FORMAT;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64)
    private String name;
    @Column(nullable = false, length = 250, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate birthday;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}