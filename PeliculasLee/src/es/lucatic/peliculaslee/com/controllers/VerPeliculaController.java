package es.lucatic.peliculaslee.com.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.lucatic.peliculaslee.com.domains.Categorias;
import es.lucatic.peliculaslee.com.domains.CategoriasPorPelicula;
import es.lucatic.peliculaslee.com.domains.Comentarios;
import es.lucatic.peliculaslee.com.domains.Peliculas;
import es.lucatic.peliculaslee.com.domains.Usuarios;
import es.lucatic.peliculaslee.com.domains.Valoraciones;
import es.lucatic.peliculaslee.com.interfaces.services.ICategoriasPorPeliculaService;
import es.lucatic.peliculaslee.com.interfaces.services.ICategoriasService;
import es.lucatic.peliculaslee.com.interfaces.services.IComentariosService;
import es.lucatic.peliculaslee.com.interfaces.services.IPeliculasService;
import es.lucatic.peliculaslee.com.interfaces.services.IUsuariosService;
import es.lucatic.peliculaslee.com.interfaces.services.IValoracionesService;
import es.lucatic.peliculaslee.com.service.CategoriasPorPeliculaService;
import es.lucatic.peliculaslee.com.service.CategoriasService;
import es.lucatic.peliculaslee.com.service.ComentariosService;
import es.lucatic.peliculaslee.com.service.PeliculasService;
import es.lucatic.peliculaslee.com.service.UsuariosService;
import es.lucatic.peliculaslee.com.service.ValoracionesService;

@Controller
public class VerPeliculaController {

	public VerPeliculaController() {
		
	
	}


	// TODO Auto-generated constructor stub
			@RequestMapping( value = "/verPelicula",method = RequestMethod.POST )
			
			public ModelAndView filtrarByValoracion(HttpServletRequest request) {
				String mensaje="";
				String pagina="pelicula";
				
				HttpSession session = request.getSession(true);
				Peliculas pelicula=new Peliculas();
				IPeliculasService peliculasService= new PeliculasService();
			
				double valoracion=0;
				IValoracionesService valoracionesService= new ValoracionesService();
				List<Valoraciones> valoraciones= new ArrayList<Valoraciones>();
				IComentariosService comentariosService= new ComentariosService();
				List<Comentarios> comentarios= new ArrayList<Comentarios>();
				Usuarios usuario= new Usuarios();
				IUsuariosService usuarioService= new UsuariosService();
				Categorias categoria= new Categorias();
				CategoriasPorPelicula categoriaPorPelicula= new CategoriasPorPelicula();
				ICategoriasService categoriasService=new CategoriasService();
				ICategoriasPorPeliculaService categoriasPorPeliculaService=new CategoriasPorPeliculaService();
				List<CategoriasPorPelicula> lista= new ArrayList<CategoriasPorPelicula>();
				String categorias="";
				int numValoraciones=0;
				double valoracionAVG=0;
				
				
				pelicula.setIdPelicula(Integer.parseInt(request.getParameter("idPelicula")));
				pelicula=peliculasService.findPeliculaByIdPelicula(pelicula);
				session.setAttribute("peliculaActual", pelicula);
				
				lista=categoriasPorPeliculaService.findCategoriasPorPeliculaByIdPelicula(pelicula);
				for(int i =0;i<lista.size();i++){
					categoria.setIdCategoria(lista.get(i).getIdCategoria());
					categoria=categoriasService.findCategoriaByIdCategoria(categoria);
					categorias+=categoria.getDescripcion();
					if(i<(lista.size()-1))
						categorias+=",";
				}
				session.setAttribute("categoriasPeliculaActual", categorias);
				numValoraciones=valoracionesService.countValoracionesByIdPelicula(pelicula);
				session.setAttribute("numValoracionesPeliculaActual", numValoraciones);
				valoracionAVG=valoracionesService.avgValoracionesByPelicula(pelicula);
				session.setAttribute("valoracionAVGPeliculaActual", valoracionAVG);
				valoracion=valoracionesService.avgValoracionesByPelicula(pelicula);
				session.setAttribute("valoracionPeliculaActual", valoracion);
				comentarios=comentariosService.findComentariosByIdPelicula(pelicula);
				String[] usuariosComentario=new String[comentarios.size()];
				session.setAttribute("comentariosPeliculaActual", comentarios);
				for(int i=0;i<comentarios.size();i++){
					
					usuariosComentario[i]=comentarios.get(i).getUsername();
				}
				
				session.setAttribute("usuariosComentarios", usuariosComentario);
				
				
				return new ModelAndView(pagina);
				
				
				


			}
}
