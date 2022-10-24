package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity; //sempre importar
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp; //Hibernate é uma biblioteca

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//anotações
@Entity //Transforma aclasse em uma tabela/entidade
@Table(name = "tb_postagem") //Vamos renomear a tabela, e o nome nesse caso é tb_postagem
public class Postagem {
     //VALIDAR ID
	@Id//logica para fazer a varivel ID no Banco de dados
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Responsavel em transformar em auto increment
	//vai fazer a logica de cadastrar, apartir do número 1
	private Long id;//variavel
	
	@NotBlank(message = "O atributo título é obrigatório!") //javax.validation
	//@not null -> não pode deixar de ser informado
	//@not blank -> IGUAL O NOT MAIS não deixa preencher com espaços em branco tbm além de tornar obrigatório
    @Size(min = 2, max = 100, message = "O atributo título deve conter no mínimo 03 e no máximo 100 caracteres") // tamanho/quantidade dos caracteres do ID no caso
	private String titulo;
	
	@NotBlank(message = "Este campo é obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo Texto deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String texto;
	
	@UpdateTimestamp //atualiza a data e hora conforme o sistema da pessoa que utilizar
	private LocalDateTime data;
	
	@ManyToOne //configurando relação de muitas postagens para um tema. relação indo e voltando
	@JsonIgnoreProperties("postagem") //a Relação entre as Classes será do tipo Bidirecional, para que a busca apresente postagem somente uma vez.
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
