package br.edu.catalogocursos.domain;
import jakarta.persistence.*;
import java.util.*;
@Entity
public class Instrutor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String nome;
 @Column(nullable=false,unique=true,length=160) private String email;
 @OneToMany(mappedBy="instrutor",fetch=FetchType.LAZY) private List<Curso> cursos=new ArrayList<>();
 protected Instrutor() {}
 public Instrutor(String nome,String email) {
  if(nome==null||nome.strip().length()<3||nome.strip().length()>120) throw new IllegalArgumentException("Nome inválido");
  if(email==null||email.length()>160||!email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) throw new IllegalArgumentException("Email inválido");
  this.nome=nome.strip(); this.email=email.strip().toLowerCase(Locale.ROOT);
 }
 public void adicionarCurso(Curso curso) {
  Objects.requireNonNull(curso,"Curso obrigatório");
  if(curso.getInstrutor()!=null&&curso.getInstrutor()!=this) throw new IllegalArgumentException("Curso já possui outro instrutor");
  curso.vincular(this); if(!cursos.contains(curso)) cursos.add(curso);
 }
 public Long getId(){return id;} public String getNome(){return nome;} public String getEmail(){return email;}
 public List<Curso> getCursos(){return Collections.unmodifiableList(cursos);}
}
