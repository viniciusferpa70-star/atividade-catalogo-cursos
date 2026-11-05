package br.edu.catalogocursos.repository;
import br.edu.catalogocursos.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CursoRepository extends JpaRepository<Curso,Long> {
 java.util.List<Curso> findByInstrutorIdOrderByTituloAsc(Long instrutorId);
}
