package br.edu.catalogocursos.application;
import br.edu.catalogocursos.domain.*;
import br.edu.catalogocursos.repository.*;
import br.edu.catalogocursos.api.dto.*;
import br.edu.catalogocursos.api.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional(readOnly=true)
public class InstrutorService {
 private final InstrutorRepository repository; private final CursoRepository cursos; private final InstrutorMapper mapper; private final CursoMapper cursoMapper;
 public InstrutorService(InstrutorRepository r,CursoRepository c,InstrutorMapper m,CursoMapper cm){repository=r;cursos=c;mapper=m;cursoMapper=cm;}
 @Transactional public InstrutorResponse cadastrar(InstrutorRequest request){
  var i=mapper.toEntity(request); if(repository.existsByEmail(i.getEmail())) throw new Conflito("Email já utilizado");
  return mapper.toResponse(repository.save(i));
 }
 public Instrutor buscarEntidade(Long id){return repository.findById(id).orElseThrow(()->new NaoEncontrado("Instrutor não encontrado"));}
 public InstrutorResponse buscar(Long id){return mapper.toResponse(buscarEntidade(id));}
 public List<CursoResumoResponse> listarCursos(Long id){buscarEntidade(id);return cursos.findByInstrutorIdOrderByTituloAsc(id).stream().map(cursoMapper::toResumo).toList();}
}
