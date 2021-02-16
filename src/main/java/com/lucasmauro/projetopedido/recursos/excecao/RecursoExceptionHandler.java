package com.lucasmauro.projetopedido.recursos.excecao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.lucasmauro.projetopedido.servicos.excecoes.AuthorizationException;
import com.lucasmauro.projetopedido.servicos.excecoes.DataIntegrityException;
import com.lucasmauro.projetopedido.servicos.excecoes.FileException;
import com.lucasmauro.projetopedido.servicos.excecoes.ObjectNotFoundException;

@ControllerAdvice
public class RecursoExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ErroPadrao> objetoNaoLocalizado(ObjectNotFoundException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<ErroPadrao> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validacao(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ErroValidacao erro = new ErroValidacao(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());		
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			erro.addError(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ErroPadrao> autorizacao(AuthorizationException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<ErroPadrao> file(FileException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<ErroPadrao> amazonServico(AmazonServiceException e, HttpServletRequest request){
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		ErroPadrao erro = new ErroPadrao(code.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(code).body(erro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ErroPadrao> amazonClient(AmazonClientException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ErroPadrao> amazonS3(AmazonS3Exception e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
