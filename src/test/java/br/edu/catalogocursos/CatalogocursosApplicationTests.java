package br.edu.catalogocursos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import br.edu.catalogocursos.domain.*;
import br.edu.catalogocursos.repository.*;
import br.edu.catalogocursos.application.*;
import br.edu.catalogocursos.api.dto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CatalogocursosApplicationTests {
 @Autowired MockMvc mvc;
 @Autowired InstrutorRepository instrutores;
 @Autowired CursoRepository cursos;
 @Autowired CursoService service;
 @Test void associacaoBidirecionalSemDuplicar(){
  var i=new Instrutor("Marina Costa","marina@example.com");var c=new Curso("Java",40,NivelCurso.INICIANTE);
  i.adicionarCurso(c);i.adicionarCurso(c);
  assertSame(i,c.getInstrutor());assertEquals(1,i.getCursos().size());
  assertThrows(NullPointerException.class,()->i.adicionarCurso(null));
  assertThrows(UnsupportedOperationException.class,()->i.getCursos().clear());
 }
 @Test void instrutorInexistente(){
  assertThrows(NaoEncontrado.class,()->service.cadastrar(new CursoRequest("Java",40,NivelCurso.INICIANTE,Long.MAX_VALUE)));
 }
 @Test void instrutorInvalido400() throws Exception {
  mvc.perform(post("/api/instrutores").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
 }
 @Test void listagemFiltradaOrdenada() throws Exception {
  var i=instrutores.save(new Instrutor("Marina Costa","marina@example.com"));
  var outro=instrutores.save(new Instrutor("Outro Instrutor","outro@example.com"));
  for(String titulo:java.util.List.of("Zoologia","Algoritmos")){
   var c=new Curso(titulo,40,NivelCurso.INICIANTE);i.adicionarCurso(c);cursos.save(c);
  }
  var c=new Curso("Nao deve aparecer",20,NivelCurso.AVANCADO);outro.adicionarCurso(c);cursos.saveAndFlush(c);
  mvc.perform(get("/api/instrutores/"+i.getId()+"/cursos")).andExpect(status().isOk())
   .andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].titulo").value("Algoritmos"))
   .andExpect(jsonPath("$[1].titulo").value("Zoologia")).andExpect(jsonPath("$[0].instrutor").doesNotExist());
 }
 @Test void cadastroLocationEConflito() throws Exception {
  String json="{\"nome\":\"Marina Costa\",\"email\":\"marina@example.com\"}";
  mvc.perform(post("/api/instrutores").contentType(MediaType.APPLICATION_JSON).content(json))
   .andExpect(status().isCreated()).andExpect(header().exists("Location")).andExpect(jsonPath("$.cursos").doesNotExist());
  mvc.perform(post("/api/instrutores").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isConflict());
 }
 @Test void cursoDtoSemRecursao() throws Exception {
  var i=instrutores.save(new Instrutor("Marina Costa","marina@example.com"));
  String body="{\"titulo\":\"Java\",\"cargaHoraria\":40,\"nivel\":\"INICIANTE\",\"instrutorId\":"+i.getId()+"}";
  var location=mvc.perform(post("/api/cursos").contentType(MediaType.APPLICATION_JSON).content(body))
   .andExpect(status().isCreated()).andExpect(jsonPath("$.instrutor.nome").value("Marina Costa"))
   .andExpect(jsonPath("$.instrutor.cursos").doesNotExist()).andReturn().getResponse().getHeader("Location");
  mvc.perform(get(location)).andExpect(status().isOk()).andExpect(jsonPath("$.titulo").value("Java"));
  mvc.perform(get("/api/cursos/"+Long.MAX_VALUE)).andExpect(status().isNotFound());
 }
 @Test void fkEObrigatoriedade(){
  assertThrows(org.springframework.dao.DataIntegrityViolationException.class,()->cursos.saveAndFlush(new Curso("Java",40,NivelCurso.INICIANTE)));
 }

	@Test
	void contextLoads() {
	}

}
