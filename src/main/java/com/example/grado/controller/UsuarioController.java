package com.example.grado.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.grado.Exception.NotFoundException;
import com.example.grado.entity.Usuario;
import com.example.grado.entity.Usuario.RolEnum;

import com.example.grado.repository.UsuarioRepository;
import com.example.grado.repository.CalendarioRepository;
import com.example.grado.repository.FormatoRepository;
import com.example.grado.repository.ProyectoRepository;

import com.example.grado.entity.Calendario;
import com.example.grado.entity.Formato;
import com.example.grado.entity.Proyecto;
import com.example.grado.entity.Proyecto.EstadoProyecto;
import com.example.grado.entity.Proyecto.EstadoRevisionDirector;

import jakarta.servlet.http.HttpSession;

@Controller
 	
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CalendarioRepository calendarioRepository;
	
	@Autowired
	private FormatoRepository formatoRepository;
	
	@Autowired
	private ProyectoRepository proyectoRepository;
	
	// Cors redirecciones a los login respectivos roles
	@GetMapping("/loginAdministador")
	public String loginAdministrador() {
		return "administrador-login";
	}
	
	@GetMapping("/loginCoordinacion")
	public String loginCoordinacion() {
		return "coordinacion-login";
	}
	
	@GetMapping("/loginDirector")
	public String loginDirector() {
		return "director-login";
	}
	
	@GetMapping("/loginEvaluador")
	public String loginEvaluador() {
		return "evaluador-login";
	}
	
	@GetMapping("/loginEstudiante")
	public String loginEstudiante() {
		return "estudiante-login";
	}
	
	
	@GetMapping("/index")
	public String index() {
	    return "redirect:http://localhost:8124";
	}
	
	
	
	
	
	
	
	//______________________________________________________________________________________
	
	@GetMapping("/regresarDashboardAdmin")
	public String regresarDashboardAdmin() {
		return "dashboard-administrador";
	}
	
	
	//Funciones del administador
	@PostMapping("/validarAdmin")
	public ModelAndView validarCredencialesAdministrador(@RequestParam String correoElectronico, @RequestParam String contrasena) {
		Usuario usuario = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(usuario != null && usuario.getRol() == RolEnum.ADMINISTRADOR) {
			modelAndView.addObject("bienvenida", "¡Bienvenido Administrador!");
			modelAndView.setViewName("dashboard-administrador");
		}else {
			modelAndView.addObject("error", "¡Creenciales inválidas o usuario no Autorizado!");
			modelAndView.setViewName("administrador-login");
		}
		return modelAndView;
	}
	
	
	@GetMapping("/gestionRoles")
	public ModelAndView gestionRoles(@RequestParam("mensaje") String mensaje) {
		ModelAndView modelAndView = new ModelAndView();
		
		List<Usuario> usuarios;
		List<Calendario> calendarios;
		List<Formato> formatos;
		
		switch (mensaje) {
		
		case "DIRECTOR": 
			usuarios = usuarioRepository.findByRol(mensaje);
			modelAndView.addObject("directores", usuarios);
			modelAndView.setViewName("panelGestionDirectores");
			break;
			
			
		case "COORDINACION":
			usuarios = usuarioRepository.findByRol(mensaje);  
			modelAndView.addObject("coordinacion", usuarios);
			modelAndView.setViewName("panelGestionCoordinacion");
			break;
			
			
		case "ESTUDIANTE": 
			usuarios = usuarioRepository.findByRol(mensaje);
			modelAndView.addObject("estudiantes", usuarios);
			modelAndView.setViewName("panelGestionEstudiantes");
			break;
			
			
		case "EVALUADOR": 
			usuarios = usuarioRepository.findByRol(mensaje);
			modelAndView.addObject("evaluadores", usuarios);
			modelAndView.setViewName("panelGestionEvaluadores");
			break;	
			
		case "CALENDARIO":
			calendarios = calendarioRepository.findAll();
			modelAndView.addObject("calendarios", calendarios);
			modelAndView.setViewName("panelGestionCalendario");
			break;
			
		case "FORMATO":
			formatos = formatoRepository.findAll();
			modelAndView.addObject("formatos", formatos);
			modelAndView.setViewName("panelGestionFormatos");
			break;	
		}

		return modelAndView;
	}
	

	   @GetMapping("/logoutAdmin")
	    public String logoutAdmin(HttpSession session) {
	        session.invalidate();
	        return "redirect:/api/loginAdministador";
	    }
	   	
	   
	
		  @GetMapping("/newCoordinador")
		  public String coordinacionNewTemplate(Model model) {
			  model.addAttribute("usuarioCoordinador", new Usuario());
			  return "add-coordinacion";
		  }
	   
		  
		  @GetMapping("/newDirector")
		  public String directorNewTemplate(Model model) {
			  model.addAttribute("usuarioDirector", new Usuario());
			  return "add-director";
		  }
		  
		  
		  @GetMapping("/newEstudiante")
		  public String estudianteNewTemplate(Model model) {
			  model.addAttribute("usuarioEstudiante", new Usuario());
			  return "add-estudiante";
		  }
		  
		  
		  
		  @GetMapping("/newEvaluador")
		  public String evaluadorNewTemplate(Model model) {
			  model.addAttribute("usuarioEvaluador", new Usuario());
			  return "add-evaluador";
		  }
	   
		  
		  @GetMapping("/newCalendario")
		  public String calendarioNewTemplate(Model model) {
			  model.addAttribute("calendario", new Calendario());
			  return "add-calendario";
		  }
		  
		  @GetMapping("/newFormato")
		  public String formatoNewTemplate(Model model) {
			  model.addAttribute("formato", new Formato());
			  return "add-formato";
		  }
	   
	   

		  
		  
		  // Metodos para registrar los roles
		  @PostMapping("/saveCoordinacion")
		  public String coordinacionSaveProcess(@ModelAttribute("usuarioCoordinador") Usuario usuario) {
			  usuario.setRol(RolEnum.COORDINACION);
			  if(usuario.getId().isEmpty()) {
				  usuario.setId(null);
			  }
			  usuarioRepository.save(usuario);
			  return "redirect:/api/gestionRoles?mensaje=COORDINACION";
		  }
		  
	   
		  
		  
		  @PostMapping("/saveDirector")
		  public String directorSaveProcess(@ModelAttribute("usuarioDirector") Usuario usuario) {
			  usuario.setRol(RolEnum.DIRECTOR);
			  if(usuario.getId().isEmpty()) {
				  usuario.setId(null);
			  }
			  usuarioRepository.save(usuario);
			  return "redirect:/api/gestionRoles?mensaje=DIRECTOR";
		  }
		  
		  
		  
		  @PostMapping("/saveEstudiante")
		  public String estudianteSaveProcess(@ModelAttribute("usuarioEstudiante") Usuario usuario) {
			  usuario.setRol(RolEnum.ESTUDIANTE);
			  if(usuario.getId().isEmpty()) {
				  usuario.setId(null);
			  }
			  usuarioRepository.save(usuario);
			  return "redirect:/api/gestionRoles?mensaje=ESTUDIANTE";
		  }
	   
		  
		  
		  @PostMapping("/saveEvaluador")
		  public String evaluadorSaveProcess(@ModelAttribute("usuarioEvaluador") Usuario usuario) {
			  usuario.setRol(RolEnum.EVALUADOR);
			  if(usuario.getId().isEmpty()) {
				  usuario.setId(null);
			  }
			  usuarioRepository.save(usuario);
			  return "redirect:/api/gestionRoles?mensaje=EVALUADOR";
		  }
		  
		  
		  
		  @PostMapping("/saveCalendario")
		  public String calendarioSaveProcess(@ModelAttribute("calendario") Calendario calendario) {
			  if(calendario.getId().isEmpty()) {
				  calendario.setId(null);
			  }
			  calendarioRepository.save(calendario);
			  return "redirect:/api/gestionRoles?mensaje=CALENDARIO";
		  }
		  
		  
		  @PostMapping("/saveFormato")
		  public String formatoSaveProcess(@ModelAttribute("formato") Formato formato) {
			  if(formato.getId().isEmpty()) {
				  formato.setId(null);
			  }
			  formatoRepository.save(formato);
			  return "redirect:/api/gestionRoles?mensaje=FORMATO";
		  }
		  

		  
		  //Metodos para editar los roles
		  @GetMapping("/editCoordinacion/{id}")
	      public String coordinacionEditTemplate(@PathVariable("id") String id, Model model) {
			    model.addAttribute("coordinacion", usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Coordinador no encontrado")));
			    return "edit-coordinacion";
		  }
		  
		  
		  @GetMapping("/editDirector/{id}")
	      public String directorEditTemplate(@PathVariable("id") String id, Model model) {
			    model.addAttribute("director", usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Director no encontrado")));
			    return "edit-director";
		  }
		  
		  
		  @GetMapping("/editEstudiante/{id}")
	      public String estudianteEditTemplate(@PathVariable("id") String id, Model model) {
			    model.addAttribute("estudiante", usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado")));
			    return "edit-estudiante";
		  }
		  
		  
		  
		  @GetMapping("/editEvaluador/{id}")
	      public String evaluadorEditTemplate(@PathVariable("id") String id, Model model) {
			    model.addAttribute("evaluador", usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Evaluador no encontrado")));
			    return "edit-evaluador";
		  }
		  
		  
		  @GetMapping("/editCalendario/{id}")
	      public String calendarioEditTemplate(@PathVariable("id") String id, Model model) {
			    model.addAttribute("calendario", calendarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Calendario no encontrado")));
			    return "edit-calendario";
		  }
		  
		  
		  @GetMapping("/editFormato/{id}")
	      public String formatoEditTemplate(@PathVariable("id") String id, Model model) {
			    model.addAttribute("formato", formatoRepository.findById(id).orElseThrow(() -> new NotFoundException("Formato no encontrado")));
			    return "edit-formato";
		  }

	
		  
		  //Metodo para eliminar los Roles
		    @GetMapping("/deleteCoordinador/{id}")
		    public String coordinadorDeleteProcess(@PathVariable("id") String id) {
		        usuarioRepository.deleteById(id);
		        return "redirect:/api/gestionRoles?mensaje=COORDINACION";
		    }
		    
		    
		    @GetMapping("/deleteDirector/{id}")
		    public String directorDeleteProcess(@PathVariable("id") String id) {
		        usuarioRepository.deleteById(id);
		        return "redirect:/api/gestionRoles?mensaje=DIRECTOR";
		    }
		    
		    
		    @GetMapping("/deleteEstudiante/{id}")
		    public String estudiantesDeleteProcess(@PathVariable("id") String id) {
		        usuarioRepository.deleteById(id);
		        return "redirect:/api/gestionRoles?mensaje=ESTUDIANTE";
		    }
		    
		    
		    @GetMapping("/deleteEvaluador/{id}")
		    public String evaluadorDeleteProcess(@PathVariable("id") String id) {
		        usuarioRepository.deleteById(id);
		        return "redirect:/api/gestionRoles?mensaje=EVALUADOR";
		    }
		    
		    
		    @GetMapping("/deleteNoticia/{id}")
		    public String noticiaDeleteProcess(@PathVariable("id") String id) {
		        calendarioRepository.deleteById(id);
		        return "redirect:/api/gestionRoles?mensaje=CALENDARIO";
		    }
		  
		    
		    @GetMapping("/deleteFormato/{id}")
		    public String formatoDeleteProcess(@PathVariable("id") String id) {
		        formatoRepository.deleteById(id);
		        return "redirect:/api/gestionRoles?mensaje=FORMATO";
		    }
		  
		  
		  
		  
		  
		  
		                   
		  
		  
		  
		  
		  
	
	
	//______________________________________________________________________________________
	
	//Funciones del coordinador
		      
	@GetMapping("/regresarDashboardCoordinador")
		public String regresarDashboardCoordinador() {
			return "dashboard-coordinacion";
	}
			
	@PostMapping("/validarCoordinador")
	
	public ModelAndView validarCredencialesCoordinador(@RequestParam String correoElectronico, @RequestParam String contrasena, HttpSession session) {
		Usuario usuario = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(usuario != null && usuario.getRol() == RolEnum.COORDINACION) {
			session.setAttribute("nombreCoordinador", usuario.getNombre());
			modelAndView.addObject("bienvenida", "¡Bienvenido Coordinador!");
			modelAndView.setViewName("dashboard-coordinacion");
		}else {
			modelAndView.addObject("error", "¡Creenciales inválidas o usuario no Autorizado!");
			modelAndView.setViewName("coordinacion-login");
		}
		return modelAndView;
	}
	
	
	   @GetMapping("/logoutCoordinador")
	    public String logoutCoordinador(HttpSession session) {
		   session.removeAttribute("nombreCoordinador");
	        return "redirect:/api/loginCoordinacion";
	    }
	
	   
	   
	   @GetMapping("/gestionarIdesProyectos")
		public String proyectosListTemplate(Model model) {
		    model.addAttribute("proyectos", proyectoRepository.findAll());
		    return "gestion-proyecto-coordinacion";
	   }
	
	   
		  @PostMapping("/saveIdeaProyecto")
		  public String formatoSaveProcess(@ModelAttribute("proyecto") Proyecto proyecto) {
			  if(proyecto.getId().isEmpty()) {
				  proyecto.setId(null);
			  }
			  proyectoRepository.save(proyecto);
			  return "redirect:/api/gestionarIdesProyectos";
		  }
	   
	   
	   
		 @GetMapping("/deleteProyecto/{id}")
		 public String proyectoDeleteProcess(@PathVariable("id") String id) {
		     proyectoRepository.deleteById(id);
		     return "redirect:/api/gestionarIdesProyectos";
		 }
	
		 
		 
		 @GetMapping("/newIdeaProyecto")
			   public String ideaProyectoNewTemplate(Model model) {
			       List<Usuario> estudiantes = usuarioRepository.findByRol("ESTUDIANTE");
			       List<Usuario> director = usuarioRepository.findByRol("DIRECTOR");
			       List<Usuario> evaluador = usuarioRepository.findByRol("EVALUADOR");
			       
			       List<String> studentIds = new ArrayList<>();
			       for (Proyecto project : proyectoRepository.findAll()) {
			           if (project.getEstudiante() != null && project.getEstudiante().getId() != null) {
			               studentIds.add(project.getEstudiante().getId());
			           }
			          
			       }

			       estudiantes.removeIf(student -> studentIds.contains(student.getId()));
			       
			       
			       model.addAttribute("estudiantes", estudiantes);
			       model.addAttribute("directores", director);
			       model.addAttribute("evaluadores", evaluador);
			       
			       model.addAttribute("proyecto", new Proyecto());
			       return "add-ideaProyecto";
			   }
		 
	
		  //Metodos para editar los roles
		  @GetMapping("/editProyecto/{id}")
	      public String proyectoEditTemplate(@PathVariable("id") String id, Model model) {
		       List<Usuario> estudiantes = usuarioRepository.findByRol("ESTUDIANTE");
		       List<Usuario> director = usuarioRepository.findByRol("DIRECTOR");
		       List<Usuario> evaluador = usuarioRepository.findByRol("EVALUADOR");
		       
		       List<String> studentIds = new ArrayList<>();
		       for (Proyecto project : proyectoRepository.findAll()) {
		           if (project.getEstudiante() != null && project.getEstudiante().getId() != null) {
		               studentIds.add(project.getEstudiante().getId());
		           }
		          
		       }

		       estudiantes.removeIf(student -> studentIds.contains(student.getId()));
		            
		       
		       Proyecto proyecto = proyectoRepository.findById(id).orElseThrow(() -> new NotFoundException("Proyecto no encontrado"));
		     
		       if(proyecto.getEstudiante() != null) {
		    	   estudiantes.add(proyecto.getEstudiante());
		       }
		       
		       model.addAttribute("estudiantes", estudiantes);
		       model.addAttribute("directores", director);
		       model.addAttribute("evaluadores", evaluador);
		       
		      

			    model.addAttribute("proyecto", proyecto);
			    return "edit-proyecto";
		  }
		  

		  
		   @GetMapping("/visualizarEstudiantesProyectos")
			public String consultarEstudiantesProyectos(Model model) {
			   List<Proyecto> proyectos = new ArrayList<>();
			   
			   for (Proyecto project : proyectoRepository.findAll()) {
				   if(project.getEstudiante() != null) {
					   proyectos.add(project);
				   }
			   }
			   
			   model.addAttribute("proyectos", proyectos);
			    
			    return "alumnos-proyectos";
		   }
		   
		   
		   @GetMapping("/visualizarDirectoresProyectos")
			public String consultarDirectoresProyectos(Model model) {
			   List<Proyecto> proyectos = new ArrayList<>();
			   
			   for (Proyecto project : proyectoRepository.findAll()) {
				   if(project.getDirector() != null) {
					   proyectos.add(project);
				   }
			   }
			   
			   model.addAttribute("proyectos", proyectos);
			    
			    return "directores-proyectos";
		   }
		   
		   
		   
		   @GetMapping("/visualizarEvaluadorProyectos")
			public String consultarEvaluadoresProyectos(Model model) {
			   List<Proyecto> proyectos = new ArrayList<>();
			   
			   for (Proyecto project : proyectoRepository.findAll()) {
				   if(project.getEvaluador() != null) {
					   proyectos.add(project);
				   }
			   }
			   
			   model.addAttribute("proyectos", proyectos);
			    
			    return "evaluadores-proyectos";
		   }

		  
		  
		   @GetMapping("/visualizarCalendarioAcademico")
			public String calendarioAcademicoListTemplate(Model model) {
			    model.addAttribute("calendarios", calendarioRepository.findAll());
			    return "visualizar-calendario-academico";
		   }
		   
		   
		   @GetMapping("/visualizarFormatosDeGrado")
			public String formatosDeGradoListTemplate(Model model) {
			    model.addAttribute("formatos", formatoRepository.findAll());
			    return "visualizar-formatos-grado";
		   }
		   
		   
		   
	   
	   
	   
	   

	
	//______________________________________________________________________________________
	
	//Funciones del estudiante
	   
	@GetMapping("/regresarDashboardEstudiante")
	public String regresarDashboardEstudiante() {
		return "dashboard-estudiante";
	}   
	   
	@PostMapping("/validarEstudiante")
	public ModelAndView validarCredencialesEstudiante(@RequestParam String correoElectronico, @RequestParam String contrasena, HttpSession session) {
		Usuario usuario = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(usuario != null && usuario.getRol() == RolEnum.ESTUDIANTE) {
			session.setAttribute("estudianteId", usuario.getId());
			session.setAttribute("nombreEstudianteSession", usuario.getNombre());
			modelAndView.addObject("bienvenida", "¡Bienvenido Estudiante!");
			modelAndView.setViewName("dashboard-estudiante");
		}else {
			modelAndView.addObject("error", "¡Creenciales inválidas o usuario no Autorizado!");
			modelAndView.setViewName("estudiante-login");
		}
		return modelAndView;
	}
	
	
	   @GetMapping("/logoutEstudiante")
	    public String logoutEstudiante(HttpSession session) {
	        session.removeAttribute("estudianteId");
	        session.removeAttribute("nombreEstudianteSession");
	        return "redirect:/api/loginEstudiante";
	    }
	   
	   
	   
	   @GetMapping("/ideasProyectos")
		public String seleccionarIdeaEstudiante(Model model, HttpSession session) {
		   List<Proyecto> proyectos = new ArrayList<>();
		   
		   String bandera = "NoSeleccion";
		   
		   String estudianteId = (String) session.getAttribute("estudianteId");
		   
		    if (estudianteId != null) {
		        for (Proyecto project : proyectoRepository.findAll()) {
		            Usuario estudiante = project.getEstudiante();
		            if (estudiante != null && estudiante.getId().equals(estudianteId)) {
		                bandera = "Seleccion";
		                break; // Salimos del bucle una vez que encontramos un proyecto asignado al estudiante
		            }
		           
		        }
		    }

		   
		   for (Proyecto project : proyectoRepository.findAll()) {
			   if(project.getEstado() == EstadoProyecto.IDEA && project.getEstudiante() == null) {
				   proyectos.add(project);
			   }
		   }
		   
		   model.addAttribute("proyectosIdeas", proyectos);
		   model.addAttribute("bandera", bandera);
		    
		    return "ideas-proyecto";
	   }
	   
	   
	   
	   
	   
	   @PostMapping("/quitarIdea")
	   public String quitarIdea(HttpSession session) {
	       String estudianteId = (String) session.getAttribute("estudianteId");
	       if (estudianteId != null) {
	           Usuario estudiante = usuarioRepository.findById(estudianteId).orElse(null);
	           if (estudiante != null) {
	               List<Proyecto> proyectosEstudiante = proyectoRepository.findByEstudiante(estudiante);
	               if (!proyectosEstudiante.isEmpty()) {
	                   Proyecto proyecto = proyectosEstudiante.get(0);
	                   proyecto.setEstudiante(null);
	                   proyecto.setEstado(EstadoProyecto.IDEA);
	                   proyectoRepository.save(proyecto);
	               }
	           }
	       }
	       return "redirect:/api/ideasProyectos";
	   }

	   
		   
		   
		   
		  @GetMapping("/elegirIdea/{id}")
	      public String seleccionIdea(@PathVariable("id") String id, HttpSession session) {
			  String estudianteId = (String) session.getAttribute("estudianteId");
			  if(estudianteId != null) {
				  Usuario estudiante = usuarioRepository.findById(estudianteId).orElse(null);
				  Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
				  if(estudiante != null && proyecto != null) {
					  proyecto.setEstado(EstadoProyecto.ANTEPROYECTO);
					  proyecto.setEstudiante(estudiante);
					  proyectoRepository.save(proyecto);
				  }
			  }
			  
			    return "redirect:/api/ideasProyectos";
		  }
	   
		  
	   
	   
		   @GetMapping("/gestionAnteproyecto")
			public String gestionAnteproyecto(Model model, HttpSession session) {
			   List<Proyecto> proyectos = new ArrayList<>();
			   
			   String bandera = "noHay";
			   
			   String estudianteId = (String) session.getAttribute("estudianteId");
			   
			   for (Proyecto project : proyectoRepository.findAll()) {
				   if(project.getEstudiante() != null && project.getEstudiante().getId().equals(estudianteId)) {
					   proyectos.add(project);
					   bandera = "siHay";
					   break;
				   }
			   }
			   
			   model.addAttribute("anteProyecto", proyectos);
			   model.addAttribute("bandera", bandera);
			    
			    return "gestion-anteproyecto-estudiante";
		   }
	
		   
		   
		   
			  @GetMapping("/crearAnteproyecto")
			  public String nuevoAnproyecto(Model model) {
				  model.addAttribute("newAnteproyecto", new Proyecto());
				  return "add-Anteproyecto-Estudiante";
			  }
	
			  
			  
	
			  @PostMapping("/saveAnteproyectoEstudiante")
			  public String saveAnteproyectoEstudiante(@ModelAttribute("newAnteproyecto") Proyecto proyecto, HttpSession session) {
				  String estudianteId = (String) session.getAttribute("estudianteId");
				  Usuario estudiante = usuarioRepository.findById(estudianteId).orElse(null);
				  
				  proyecto.setEstudiante(estudiante);
				  proyecto.setEstado(EstadoProyecto.ANTEPROYECTO);
				  proyecto.setEstadoRevisionDirector(EstadoRevisionDirector.PENDIENTE);
				  
				  if(proyecto.getId().isEmpty()) {
					  proyecto.setId(null);
				  }
	
				  proyectoRepository.save(proyecto);
				  return "redirect:/api/gestionAnteproyecto";
			  }
			  
			  
			  
			  
			  @GetMapping("/editAnteproyectoEstudiante/{id}")
		      public String editAnteproyectoEstudiante(@PathVariable("id") String id, Model model) {
				    model.addAttribute("anteproyecto", proyectoRepository.findById(id).orElseThrow(() -> new NotFoundException("Anteproyecto no encontrado")));
				    return "edit-anteproyecto";
			  }
	
	
			    @GetMapping("/deleteAnteproyectoEstudiante/{id}")
			    public String anteproyectoDeleteProcess(@PathVariable("id") String id, HttpSession session) {
			    	
			        proyectoRepository.deleteById(id);
			        return "redirect:/api/gestionAnteproyecto";
			    }
	   
	   
	   
	   
	   

	   

		//______________________________________________________________________________________
				
				//Funciones del director
					   
				@GetMapping("/regresarDashboardDirector")
				public String regresarDashboardDirector() {
					return "dashboard-director";
				}
					   
				@PostMapping("/validarDirector")
				public ModelAndView validarCredencialesDirector(@RequestParam String correoElectronico, @RequestParam String contrasena, HttpSession session) {
					Usuario usuario = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
					
					ModelAndView modelAndView = new ModelAndView();
					
					if(usuario != null && usuario.getRol() == RolEnum.DIRECTOR) {
						session.setAttribute("directorId", usuario.getId());
						session.setAttribute("nombreDirectorSession", usuario.getNombre());
						modelAndView.addObject("bienvenida", "¡Bienvenido Director!");
						modelAndView.setViewName("dashboard-director");
					}else {
						modelAndView.addObject("error", "¡Creenciales inválidaso o usuario no Autorizado!");
						modelAndView.setViewName("director-login");
					}
					return modelAndView;
				}
				
				   @GetMapping("/logoutDirector")
				    public String logoutDirector(HttpSession session) {
					   session.removeAttribute("directorId");
					   session.removeAttribute("nombreDirectorSession");
				        return "redirect:/api/loginDirector";
				    }
				   
				   
	   
			@GetMapping("/GestionProyectosAsignados")
				public String GestionProyectosAsignados(Model model, HttpSession session) {
					List<Proyecto> proyectos = new ArrayList<>();
					   
					String bandera = "noHay";
					   
					String directorId = (String) session.getAttribute("directorId");
					   
				    for (Proyecto project : proyectoRepository.findAll()) {
						   if(project.getDirector() != null && project.getDirector().getId().equals(directorId)) {
							   proyectos.add(project);
							   bandera = "siHay";
						   }
					   }
					   
					   model.addAttribute("anteProyectoDelDirector", proyectos);
					   model.addAttribute("bandera", bandera);
					    
					    return "gestion-anteproyecto-subidos-estudiante";
				   }	
			
			
			
			  @GetMapping("/abandonarAnteproyecto/{id}")
		      public String abandonarAnteproyecto(@PathVariable("id") String id, HttpSession session) {

					  Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
					  if(proyecto != null) {
						  proyecto.setDirector(null);
						  proyectoRepository.save(proyecto);
					  }
				 
				  
				    return "redirect:/api/GestionProyectosAsignados";
			  }
	   
	   
			  
			  @GetMapping("/editarAnteproyectoPorDirector/{id}")
			  public String editarAnteproyectoPorDirector(@PathVariable("id") String id, Model model) {
			      Proyecto proyecto = proyectoRepository.findById(id)
			              .orElseThrow(() -> new NotFoundException("Anteproyecto no encontrado"));
			      model.addAttribute("anteproyectoPorDirector", proyecto);
			      return "edit-anteproyecto-por-director";
			  }

			  @PostMapping("/saveProyectoModificadoPorDirector")
			  public String saveProyectoModificadoPorDirector(@ModelAttribute("anteproyectoPorDirector") Proyecto proyectoForm) {
			      Proyecto proyectoExistente = proyectoRepository.findById(proyectoForm.getId())
			              .orElseThrow(() -> new NotFoundException("Anteproyecto no encontrado"));

			      // Actualiza solo los campos modificados
			      proyectoExistente.setTitulo(proyectoForm.getTitulo());
			      proyectoExistente.setDescripcion(proyectoForm.getDescripcion());
			      proyectoExistente.setEstadoRevisionDirector(proyectoForm.getEstadoRevisionDirector());

			      proyectoRepository.save(proyectoExistente);
			      return "redirect:/api/GestionProyectosAsignados";
			  }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	
	//______________________________________________________________________________________
	
	//Funciones del evaluador
	   
	@GetMapping("/regresarDashboardEvaluador")
	public String regresarDashboardEvaluador() {
		return "dashboard-evaluador";
	}   
	   
	@PostMapping("/validarEvaluador")
	public ModelAndView validarCredencialesEvaluador(@RequestParam String correoElectronico, @RequestParam String contrasena, HttpSession session) {
		Usuario usuario = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(usuario != null && usuario.getRol() == RolEnum.EVALUADOR) {
			session.setAttribute("evaluadorId", usuario.getId());
			session.setAttribute("nombreEvaluadorSession", usuario.getNombre());
			modelAndView.addObject("bienvenida", "¡Bienvenido Evaluador!");
			modelAndView.setViewName("dashboard-evaluador");
		}else {
			modelAndView.addObject("error", "¡Creenciales inválidas o usuario no Autorizado!");
			modelAndView.setViewName("evaluador-login");
		}
		return modelAndView;
	}
	
	
	   @GetMapping("/logoutEvaluador")
	    public String logoutEvaluador(HttpSession session) {
		   session.removeAttribute("evaluadorId");
		   session.removeAttribute("nombreEvaluadorSession");
	        return "redirect:/api/loginEvaluador";
	    }
	
	
	
	
		@GetMapping("/GestionProyectosAsignadosEvaluador")
		public String GestionProyectosAsignadosEvaluador(Model model, HttpSession session) {
			List<Proyecto> proyectos = new ArrayList<>();
			   
			String bandera = "noHay";
			   
			String evaluadorId = (String) session.getAttribute("evaluadorId");
			   
		    for (Proyecto project : proyectoRepository.findAll()) {
				   if(project.getEvaluador() != null && project.getEvaluador().getId().equals(evaluadorId)
						   && project.getEstadoRevisionDirector() == EstadoRevisionDirector.APROBADO) {
					   proyectos.add(project);
					   bandera = "siHay";
				   }
			   }
			   
			   model.addAttribute("anteProyectoDelEvaluador", proyectos);
			   model.addAttribute("banderaEva", bandera);
			    
			    return "gestion-anteproyecto-subidos-director";
		   }	
		
		
	
		  @GetMapping("/abandonarAnteproyectoDirector/{id}")
	      public String abandonarAnteproyectoDirector(@PathVariable("id") String id, HttpSession session) {

				  Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
				  if(proyecto != null) {
					  proyecto.setEvaluador(null);
					  proyectoRepository.save(proyecto);
				  }
			 
			  
			    return "redirect:/api/GestionProyectosAsignadosEvaluador";
		  }
		  
		  
		  
		  
		  
		  
		  @GetMapping("/editarAnteproyectoPorEvaluador/{id}")
		  public String editarAnteproyectoPorEvaluador(@PathVariable("id") String id, Model model) {
		      Proyecto proyecto = proyectoRepository.findById(id)
		              .orElseThrow(() -> new NotFoundException("Anteproyecto no encontrado"));
		      model.addAttribute("anteproyectoPorEvaluador", proyecto);
		      return "edit-anteproyecto-por-evaluador";
		  }
		  
		  
		  
		  
		  @PostMapping("/saveProyectoModificadoPorEvaluador")
		  public String saveProyectoModificadoPorEvaluador(@ModelAttribute("anteproyectoPorEvaluador") Proyecto proyectoForm) {
		      Proyecto proyectoExistente = proyectoRepository.findById(proyectoForm.getId())
		              .orElseThrow(() -> new NotFoundException("Anteproyecto no encontrado"));

		      // Actualiza solo los campos modificados
		      proyectoExistente.setTitulo(proyectoForm.getTitulo());
		      proyectoExistente.setDescripcion(proyectoForm.getDescripcion());
		      proyectoExistente.setEstado(proyectoForm.getEstado());

		      proyectoRepository.save(proyectoExistente);
		      return "redirect:/api/GestionProyectosAsignadosEvaluador";
		  }
	
	
	
	
}
