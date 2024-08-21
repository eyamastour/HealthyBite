package com.esprit.pidev.entities.UserRole;


import com.esprit.pidev.entities.CommandeLivraison.AdresseLivraison;
import com.esprit.pidev.entities.ConseilRecette.Objectif;
import com.esprit.pidev.entities.ConseilRecette.TypeActivite;
import com.esprit.pidev.entities.CommandeLivraison.Commande;
import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.Post;

import com.esprit.pidev.entities.ProduitRepas.ObjectifType;
import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean enabled;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    private String phone;
    @NotBlank
    private String code;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    public User(Set<Role> roles) {
        this.roles = roles;
    }
    private Long maxCalories;

    @Enumerated(EnumType.STRING)
    private TypeActivite activite;

    @Enumerated(EnumType.STRING)
    private ObjectifType objectif;

    private Long poids;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private GenderType Gender;

    private int age;
    private Long ObjectifPoids;
    private Long taille;


    public User(String username, String email, String phone, String encode) {
    }
    ///********************fin des attributs


    private String verificationToken;

    ///********************fin des attributs
    public boolean isActive() {
        return enabled;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AdresseLivraison> addresses;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Repas> repas;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Reclamation> reclamations;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Produit> produits;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Commande> commande;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "user_liked_posts",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    private Set<Post> likedPosts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_liked_comments",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private Set<Comment> likedComments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Objectif> objectifs = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(){

    }

    public User(Long id) {
        this.id = id;


    }



    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
