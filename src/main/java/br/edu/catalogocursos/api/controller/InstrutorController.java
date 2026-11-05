package br.edu.catalogocursos.api.controller;
import br.edu.catalogocursos.application.*;
import br.edu.catalogocursos.api.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.net.URI;
@RestController @RequestMapping("/api/instrutores")
public class InstrutorController {
 private final InstrutorService service;
 public InstrutorController(InstrutorService s){service=s;}
 @PostMapping public ResponseEntity<InstrutorResponse> cadastrar(@Valid @RequestBody InstrutorRequest r){
  var resposta=service.cadastrar(r);return ResponseEntity.created(URI.create("/api/instrutores/"+resposta.id())).body(resposta);
 }
 @GetMapping("/{id}") public InstrutorResponse buscar(@PathVariable Long id){return service.buscar(id);}
 @GetMapping("/{id}/cursos") public java.util.List<CursoResumoResponse> cursos(@PathVariable Long id){return service.listarCursos(id);}
}
