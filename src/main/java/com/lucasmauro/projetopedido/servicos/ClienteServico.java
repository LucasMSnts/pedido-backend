package com.lucasmauro.projetopedido.servicos;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lucasmauro.projetopedido.dominio.Cidade;
import com.lucasmauro.projetopedido.dominio.Cliente;
import com.lucasmauro.projetopedido.dominio.Endereco;
import com.lucasmauro.projetopedido.dominio.enums.Perfil;
import com.lucasmauro.projetopedido.dominio.enums.TipoCliente;
import com.lucasmauro.projetopedido.dto.ClienteDTO;
import com.lucasmauro.projetopedido.dto.ClienteNovoDTO;
import com.lucasmauro.projetopedido.repositorios.ClienteRepositorio;
import com.lucasmauro.projetopedido.repositorios.EnderecoRepositorio;
import com.lucasmauro.projetopedido.security.UsuarioSS;
import com.lucasmauro.projetopedido.servicos.excecoes.AuthorizationException;
import com.lucasmauro.projetopedido.servicos.excecoes.DataIntegrityException;
import com.lucasmauro.projetopedido.servicos.excecoes.ObjectNotFoundException;

@Service
public class ClienteServico {
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClienteRepositorio repo;
	
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	
	@Autowired
	private S3Servico s3Servico;
	
	@Autowired
	private ImagemServico imagemServico;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Cliente find(Integer id) {
		
		UsuarioSS usuario = UsuarioServico.authenticated();
		if (usuario==null || !usuario.hasRole(Perfil.ADMIN) && !id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}	
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepositorio.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj)	{
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linhasPorPaginas, String orderBy, String direcao) { // direcao = ordem crescente ou descrecente 
		PageRequest pageRequest = PageRequest.of(page, linhasPorPaginas, Direction.valueOf(direcao), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNovoDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), 
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UsuarioSS usuario = UsuarioServico.authenticated();
		if(usuario == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImagem = imagemServico.getJpgImageFromFile(multipartFile);
		jpgImagem = imagemServico.cropSquare(jpgImagem);
		jpgImagem = imagemServico.resize(jpgImagem, size);
		
		String fileName = prefix + usuario.getId() + ".jpg";
		
		return s3Servico.uploadFile(imagemServico.getInputStream(jpgImagem, "jpg"), fileName, "image");
	}
}
