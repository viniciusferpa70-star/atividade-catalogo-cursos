package br.edu.catalogocursos.application;
import br.edu.catalogocursos.repository.*;
import br.edu.catalogocursos.api.dto.*;
import br.edu.catalogocursos.api.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional(readOnly=true)
public class CursoService {
 private final CursoRepository repository;private final InstrutorService instrutores;private final CursoMapper mapper;
 public CursoService(CursoRepository r,InstrutorService i,CursoMapper m){repository=r;instrutores=i;mapper=m;}
 @Transactional public CursoResponse cadastrar(CursoRequest r){
  var instrutor=instrutores.buscarEntidade(r.instrutorId());var curso=mapper.toEntity(r);
  instrutor.adicionarCurso(curso);return mapper.toResponse(repository.save(curso));
 }
 public CursoResponse buscar(Long id){return mapper.toResponse(repository.findById(id).orElseThrow(()->new NaoEncontrado("Curso não encontrado")));}
}
