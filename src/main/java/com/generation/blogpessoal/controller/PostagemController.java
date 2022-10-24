package com.generation.blogpessoal.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController //Receberá requisições que serão compostas por URL,VERBO,CORPO DE REQUISIÇÃO.
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")  // caminho que dá autorização de acesso ao frontend
public class PostagemController {
	
        @Autowired 
        private PostagemRepository postagemRepository;
        
        @Autowired
        private TemaRepository temaRepository;
        
        @GetMapping // puxar
       public ResponseEntity<List<Postagem>> getAll(){
        	return ResponseEntity.ok(postagemRepository.findAll());

        }
        @GetMapping("/{id}") //procura especifico, no caso ID
        public ResponseEntity<Postagem> getById(@PathVariable Long id){ //é uma requisição atraves do getById, que se comunica com o bancode dados e trás a resposta
        	return postagemRepository.findById(id)
        			.map(resposta->ResponseEntity.ok(resposta)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());}
        //MAP VAI MAPEAR A RESPOSTA

        @GetMapping("/titulo/{titulo}")//procura titulo especifico
        public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
        	return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
        }
        
        @PostMapping //insere novos dados
        public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
        	if(temaRepository.existsById(postagem.getTema().getId()))
        		return ResponseEntity.status(HttpStatus.CREATED)
        			.body(postagemRepository.save(postagem));
        	
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        @PutMapping //atualiza os dados
        public ResponseEntity<Postagem>put(@Valid @RequestBody Postagem postagem){
        	if (postagemRepository.existsById(postagem.getId())) {
        		if (temaRepository.existsById(postagem.getTema().getId()))
        			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
        		
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        	}
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/{id}") //deleta o dado através do ID
        public void delete(@PathVariable Long id) {
        	Optional<Postagem> postagem = postagemRepository.findById(id);
        	
        	if(postagem.isEmpty())
        		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        	
        	postagemRepository.deleteById(id);
        }
        
        }