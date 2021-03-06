package es.lucatic.peliculaslee.com.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import es.lucatic.peliculaslee.com.domains.Usuarios;
import es.lucatic.peliculaslee.com.interfaces.services.IUsuariosService;
import es.lucatic.peliculaslee.com.service.UsuariosService;

//ESTE CONTROLLER COMPRUEBA SI EL USUARIO Y AL CONTRASE�A SON CORRECTAS 
@Controller
public class LoginController {
	
@RequestMapping( value = "/logearse", method = RequestMethod.GET )
	
	public ModelAndView interface_Logearse() {
		System.out.println("ENTRAlogearse");
		
		/* LE REDIRECCIONAMOS A LA PAGINA Index.jsp */
		return new ModelAndView( "login", "datosaltausuario", new Usuarios() );
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	// ANOTACION DE QUE VA A RECIBIR UN PRODUCTO. SIN LA ANOTACION NO FUNCIONA
	public ModelAndView altaUsuario(@Valid Usuarios usuario, HttpServletRequest request) {
		String pagina = "../index";

		HttpServletResponse response;
		String mensaje = "";
		HttpSession session = request.getSession(true);

		IUsuariosService usuarioService = new UsuariosService();
		Usuarios aux = usuarioService.findUsuariosByUsername(usuario);
		if (aux != null) {
			if (aux.getUsername().equals(usuario.getUsername())) {
				if (aux.getPassword().equals(usuario.getPassword())) {
					// Si el usuario est� en la base de datos subimos a la nube
					// su
					// informacion
					session.setAttribute("usuario", aux);
					mensaje = "Bienvenido "+aux.getNombre();
					request.setAttribute("mensaje", mensaje);
				}
				else {
					mensaje = "El usuario o la contrase�a no son correctas.";
					request.setAttribute("mensaje", mensaje);
					pagina = "login";

				}
			}

			else {
				mensaje = "El usuario o la contrase�a no son correctas.";
				request.setAttribute("mensaje", mensaje);
				pagina = "login";

			}
		} else {
			mensaje = "El usuario no existe.";
			request.setAttribute("mensaje", mensaje);
			pagina = "login";
		}
		ModelAndView modelandviewAux = new ModelAndView(pagina);
		System.out.println("Login: "+mensaje);
		modelandviewAux.addObject(mensaje);
		modelandviewAux.addObject(request);
		return modelandviewAux;
		

	}
	@ModelAttribute("datosUsuario")  //para el tema de validaciones hay que poner esto ademas
	 //  "datosCoche" coincide con el <form:form method="post" action="login.do" commandName="datoslogin"> del .jsp
	public Usuarios populateForm() {
		// System.out.println("populateForm usuarioLogin()");
	     return new Usuarios(); // creamos el bean para que se pueda popular
	}

	public LoginController() {
		// TODO Auto-generated constructor stub
	}

}
