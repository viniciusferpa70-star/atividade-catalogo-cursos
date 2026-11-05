package br.edu.catalogocursos.api.exception;
import br.edu.catalogocursos.application.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 public record Erro(int status,String message,String path,Map<String,String> fields){}
 private ResponseEntity<Erro> erro(int s,String m,HttpServletRequest r,Map<String,String> f){return ResponseEntity.status(s).body(new Erro(s,m,r.getRequestURI(),f));}
 @ExceptionHandler(NaoEncontrado.class) public ResponseEntity<Erro> ausente(NaoEncontrado e,HttpServletRequest r){return erro(404,e.getMessage(),r,Map.of());}
 @ExceptionHandler({Conflito.class,DataIntegrityViolationException.class}) public ResponseEntity<Erro> conflito(RuntimeException e,HttpServletRequest r){return erro(409,"Conflito de integridade ou email já utilizado",r,Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Erro> validacao(MethodArgumentNotValidException e,HttpServletRequest r){
  Map<String,String> f=new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x->f.putIfAbsent(x.getField(),x.getDefaultMessage()));return erro(400,"Dados inválidos",r,f);
 }
 @ExceptionHandler({HttpMessageNotReadableException.class,IllegalArgumentException.class,org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
 public ResponseEntity<Erro> formato(Exception e,HttpServletRequest r){return erro(400,"Dados ou JSON inválidos",r,Map.of());}
}
