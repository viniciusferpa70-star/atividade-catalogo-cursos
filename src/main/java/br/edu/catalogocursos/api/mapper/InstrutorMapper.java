package br.edu.catalogocursos.api.mapper;
import br.edu.catalogocursos.domain.*;
import br.edu.catalogocursos.api.dto.*;
import org.springframework.stereotype.Component;
@Component public class InstrutorMapper {
 public Instrutor toEntity(InstrutorRequest r){return new Instrutor(r.nome(),r.email());}
 public InstrutorResponse toResponse(Instrutor i){return new InstrutorResponse(i.getId(),i.getNome(),i.getEmail());}
}
