package com.example.sistema_venda_ingressos.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sistema_venda_ingressos.exceptions.RecursoNaoEncontradoException;
import com.example.sistema_venda_ingressos.exceptions.RegraDeNegocioException;
import com.example.sistema_venda_ingressos.models.UsuarioModel;
import com.example.sistema_venda_ingressos.models.dtos.UsuarioRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.UsuarioResponseDTO;
import com.example.sistema_venda_ingressos.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    

    private final UsuarioRepository usuarioRepository;

    public static final String MENSAGEM_ERRO = "Usuário não encontrado com o id ";

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioDTO){

        if(usuarioRepository.findByEmail(usuarioDTO.email()).isPresent()) {
            throw new RegraDeNegocioException("E-mail já cadastrado: " + usuarioDTO.email());
        }

        UsuarioModel usuario = toModel(usuarioDTO);
        usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromModel(usuario);
    }

    public UsuarioResponseDTO buscarPorId(Long id){

        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        return UsuarioResponseDTO.fromModel(usuario);
    }

    public UsuarioResponseDTO buscarPorEmail(String email){

        UsuarioModel usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o email " + email));

        return UsuarioResponseDTO.fromModel(usuario);
    }

    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable){
        
        return usuarioRepository.findAll(pageable)
            .map(UsuarioResponseDTO::fromModel);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO novoUsuario){

        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        usuario.setNome(novoUsuario.nome());
        usuario.setEmail(novoUsuario.email());
        usuario.setSenha(novoUsuario.senha());

        usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromModel(usuario);
    }

    public void deletarUsuario(Long id){

        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        usuarioRepository.delete(usuario);
    }



    private UsuarioModel toModel(UsuarioRequestDTO usuarioDTO){
        return new UsuarioModel(
            usuarioDTO.nome(),
            usuarioDTO.email(),
            usuarioDTO.senha()
        );
    }
}
