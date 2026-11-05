package br.edu.catalogocursos.api.controller;
import br.edu.catalogocursos.application.*;
import br.edu.catalogocursos.api.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.net.URI;
@RestController @RequestMapping("/api/cursos")
public class CursoController {
 private final CursoService service;
 public CursoController(CursoService s){service=s;}
 @PostMapping public ResponseEntity<CursoResponse> cadastrar(@Valid @RequestBody CursoRequest r){
  var resposta=service.cadastrar(r);return ResponseEntity.created(URI.create("/api/cursos/"+resposta.id())).body(resposta);
 }
 @GetMapping("/{id}") public CursoResponse buscar(@PathVariable Long id){return service.buscar(id);}
 
}
