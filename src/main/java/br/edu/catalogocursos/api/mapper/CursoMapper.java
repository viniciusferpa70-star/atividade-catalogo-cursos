package br.edu.catalogocursos.api.mapper;
import br.edu.catalogocursos.domain.*;
import br.edu.catalogocursos.api.dto.*;
import org.springframework.stereotype.Component;
@Component public class CursoMapper {
 public Curso toEntity(CursoRequest r){return new Curso(r.titulo(),r.cargaHoraria(),r.nivel());}
 public CursoResponse toResponse(Curso c){return new CursoResponse(c.getId(),c.getTitulo(),c.getCargaHoraria(),c.getNivel(),new CursoResponse.InstrutorResumo(c.getInstrutor().getId(),c.getInstrutor().getNome()));}
 public CursoResumoResponse toResumo(Curso c){return new CursoResumoResponse(c.getId(),c.getTitulo(),c.getCargaHoraria(),c.getNivel());}
}
