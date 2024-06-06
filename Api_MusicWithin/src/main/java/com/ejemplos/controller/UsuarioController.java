package com.ejemplos.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.RecoverPasswordRequest;
import com.ejemplos.modelo.Roles;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.JwtUtil;
import com.ejemplos.repository.RolesRepositorio;
import com.ejemplos.services.EmailService;
import com.ejemplos.services.RolesService;
import com.ejemplos.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	private final RolesRepositorio rolesRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private EmailService emailService;
	
	@PostMapping("/generarToken")
	public ResponseEntity<?> generateToken() {
	    String token = jwtUtil.generateToken();
	    Map<String, String> response = new HashMap<>();
	    response.put("token", token);
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
	    String username = credentials.get("username");
	    String password = credentials.get("password");

	    Usuario usuario = usuarioService.findByUsername(username);

	    if (usuario != null && passwordEncoder.matches(password, usuario.getPassword_hash())) {
	        String token = jwtUtil.generateToken(username);
	        Map<String, String> response = new HashMap<>();
	        response.put("token", token);
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
	    }
	}
	
	@GetMapping("/usuario")
	public ResponseEntity<?> obtenerTodos(){
		List<Usuario> usuarios=this.usuarioService.obtenerTodos();
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/usuario/{usuarioID}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long usuarioID){
		Usuario usuarioNuevo=this.usuarioService.obtenerPorId(usuarioID);
		return ResponseEntity.ok().body(usuarioNuevo);
	}
	
	@GetMapping("/usuario/username/{username}")
	public ResponseEntity<?> obtenerIdPorUsername(@PathVariable String username){
	    Usuario usuario = usuarioService.findByUsername(username);
	    if (usuario != null) {
	        return ResponseEntity.ok().body(usuario);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/usuario/username")
	public ResponseEntity<?> obtenerIdPorUsername2(@RequestParam String username){
	    Long userID = usuarioService.getUserIdByUsername(username);
	    if (userID != null) {
	        return ResponseEntity.ok().body(userID);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<?> insertarUsuario(@RequestBody Map<String, Object> payload){
	    Usuario usuario = new Usuario();
	    usuario.setUsername((String) payload.get("username"));
	    usuario.setNombre((String) payload.get("nombre"));
	    usuario.setApellidos((String) payload.get("apellidos"));
	    usuario.setFecha_creacion(LocalDateTime.parse((String) payload.get("fecha_creacion")));
	    usuario.setEmail((String) payload.get("email"));
	    
	    String password = (String) payload.get("password_hash");

	    String passwordCifrada = passwordEncoder.encode(password);
	    usuario.setPassword_hash(passwordCifrada);
	    
	    usuario.setFoto(payload.get("foto") != null ? Base64.getDecoder().decode((String) payload.get("foto")) : null);

	    Long roleID = ((Number) payload.get("roleID")).longValue();
	    Roles role = rolesRepository.findById(roleID)
	            .orElseThrow(() -> new RuntimeException("Role not found"));

	    usuario.setRole(role);

	    Usuario usuarioNuevo = this.usuarioService.insertarUsuario(usuario);
	    return ResponseEntity.ok().body(usuarioNuevo);
	}
	
	@PutMapping("usuario/{usuarioID}")
	public ResponseEntity<?>actualizarUsuario(@PathVariable Long usuarioID, @RequestBody Usuario Usuario){
		Usuario usuarioActualizado=this.usuarioService.actualizarUsuario(usuarioID, Usuario);
		return ResponseEntity.ok().body(usuarioActualizado);
	}
	
	@DeleteMapping("usuario/{usuarioID}")
	public ResponseEntity<?>eliminarUsuario(@PathVariable Long usuarioID){
		this.usuarioService.eliminarUsuario(usuarioID);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/uploadFoto/{usuarioID}")
	public ResponseEntity<?> uploadFoto(@PathVariable Long usuarioID, @RequestParam(name ="foto", required = false) MultipartFile foto) {
	    if (foto.isEmpty()) {
	        return new ResponseEntity<>("Error: La imagen está vacía", HttpStatus.BAD_REQUEST);
	    }
	    try {
	        byte[] fotoBytes = foto.getBytes();
	        String base64Image = Base64.getEncoder().encodeToString(fotoBytes);
	        Usuario usuario = usuarioService.obtenerPorId(usuarioID);
	        if (usuario != null) {
	            usuario.setFoto(fotoBytes);
	            usuarioService.actualizarUsuario(usuarioID, usuario);
	            Map<String, String> response = new HashMap<>();
	            response.put("fotoBase64", base64Image);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	        }
	    } catch (IOException e) {
	        return new ResponseEntity<>("Error al procesar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping("/recuperarPassword")
	public ResponseEntity<?> recuperarPassword(@RequestBody Map<String, String> request) {
	    String username = request.get("username");
	    String email = request.get("email");
	    Usuario usuario = usuarioService.findByUsernameAndEmail(username, email);
	    if (usuario != null) {
	        String nuevaPassword = UUID.randomUUID().toString().substring(0, 8);
	        String encryptedPassword = passwordEncoder.encode(nuevaPassword);
	        usuario.setPassword_hash(encryptedPassword);
	        usuarioService.actualizarUsuario(usuario.getUserID(), usuario);
	        emailService.sendEmail(
	                email,
	                "Recuperación de Contraseña",
	                "Tu nueva contraseña es: " + nuevaPassword
	        );
	        return ResponseEntity.ok(Map.of("message", "Nueva contraseña enviada a tu correo electrónico"));
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Usuario no encontrado o correo electrónico incorrecto"));
	    }
	}
	
	@GetMapping("/usuario/buscar")
	public ResponseEntity<?> buscarUsuarios(@RequestParam(required = false) String keyword){
	    if (keyword == null || keyword.isEmpty()) {
	        return ResponseEntity.ok().body(usuarioService.obtenerTodos());
	    } else {
	        List<Usuario> usuarios = usuarioService.buscarUsuarios(keyword);
	        return ResponseEntity.ok().body(usuarios);
	    }
	}
	
	@GetMapping("/usuario/{username}/tipo")
    public ResponseEntity<?> obtenerTipoUsuario(@PathVariable String username) {
        String tipoUsuario = usuarioService.determinarTipoUsuario(username);
        if (!tipoUsuario.equals("UNKNOWN")) {
            return ResponseEntity.ok().body(Map.of("tipoUsuario", tipoUsuario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Tipo de usuario no encontrado"));
        }
    }
	
	@GetMapping("usuario/rol/{userID}")
	public ResponseEntity<?> obtenerRoleID(@PathVariable Long userID){
		Long roleID=this.usuarioService.encontrarRoleUsuario(userID);
		return ResponseEntity.ok().body(roleID);
	}

}
