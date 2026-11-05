package br.edu.catalogocursos.domain;
import jakarta.persistence.*;
@Entity
public class Curso {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=150) private String titulo;
 @Column(nullable=false) private Integer cargaHoraria;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private NivelCurso nivel;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="instrutor_id",nullable=false) private Instrutor instrutor;
 protected Curso(){}
 public Curso(String titulo,Integer cargaHoraria,NivelCurso nivel){
  if(titulo==null||titulo.strip().length()<3||titulo.strip().length()>150) throw new IllegalArgumentException("Título inválido");
  if(cargaHoraria==null||cargaHoraria<1||cargaHoraria>400||nivel==null) throw new IllegalArgumentException("Carga horária ou nível inválido");
  this.titulo=titulo.strip();this.cargaHoraria=cargaHoraria;this.nivel=nivel;
 }
 void vincular(Instrutor instrutor){this.instrutor=java.util.Objects.requireNonNull(instrutor);}
 public Long getId(){return id;} public String getTitulo(){return titulo;} public Integer getCargaHoraria(){return cargaHoraria;}
 public NivelCurso getNivel(){return nivel;} public Instrutor getInstrutor(){return instrutor;}
}
