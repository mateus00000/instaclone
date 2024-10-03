package com.instagram.clone.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 30)
    private String username;  // Campo adicionado para o nome de usuário

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String fullName;

    @Column(length = 500)
    private String bio;

    @Column(length = 255)
    private String profilePicture; // Caminho do arquivo da foto de perfil

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_followers",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "follower_id")}
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
    private Set<User> following = new HashSet<>();
}
