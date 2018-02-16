package FitURJC.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import FitURJC.User.User;
import FitURJC.User.UserComponent;
import FitURJC.User.UserRepository;
import FitURJC.course.Course;
import FitURJC.course.CourseRepository;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value = "/")
	public String getIndex(Model model) {
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		return "index";
	}

//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login(Model model, HttpServletRequest request) {
//		if ((userComponent.isLoggedUser())) {
//			// Comprobar si hay un usuario logueado y añadirlo
//			long userLogged_id = userComponent.getLoggedUser().getId();
//			User userLogged = userRepo.findOne(userLogged_id);
//
//			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
//				model.addAttribute("logged", true);
//			}
//			// Comprobar si es admin
//			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
//			return "redirect:/user";
//		} else {
//			return "login";
//		}
//
//	}
//
//	@RequestMapping("/loginerror")
//	public String loginerror(Model model) {
//		model.addAttribute("loginerror", true);
//		return "login";
//	}


//
//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//		return new EmbeddedServletContainerCustomizer() {
//			@Override
//			public void customize(ConfigurableEmbeddedServletContainer container) {
//
//				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//				ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
//				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//				container.addErrorPages(error401Page, error404Page, error403Page, error500Page);
//			}
//		};
//	}
}
