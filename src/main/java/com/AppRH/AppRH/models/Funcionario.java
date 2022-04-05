package com.AppRH.AppRH.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Funcionario implements Serializable {
      private static final long serialVersionUID= 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Long id;

      private String nome;
      private String data;
      private String email;

      //deletando em cascata (Cascade)
      @OneToMany(mappedBy = "funcionario", cascade = CascadeType.REMOVE)
      private List<Dependentes>dependentes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Dependentes> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependentes> dependentes) {
        this.dependentes = dependentes;
    }
}
