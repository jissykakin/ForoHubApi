package com.foro.hubApi.domain.roles;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Table(name = "roles")
@Entity(name = "Role")
@Getter
@EqualsAndHashCode(of = "id")
public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Enumerated(EnumType.STRING)
        @Column(length = 20)
        private RoleName name;


        public RoleName getName() {
            return name;
        }
}
