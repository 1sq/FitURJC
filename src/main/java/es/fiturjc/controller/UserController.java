package es.fiturjc.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import es.fiturjc.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.fiturjc.component.UserComponent;
import es.fiturjc.model.Category;
import es.fiturjc.model.Course;
import es.fiturjc.model.User;
import es.fiturjc.service.CourseService;
import es.fiturjc.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserComponent userComponent;
	
	@Autowired
    private CourseService courseService;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping("/profile")
	public String userProfile(Model model) {

		if(!userComponent.isLoggedUser()){
			return "redirect:/403.html";
		}
		User userLogged = userComponent.getLoggedUser(); // Check if the user is logged
		
		// 3 task in the user interface 

		model.addAttribute("userPage", userLogged);
		model.addAttribute("recommendations", userService.getRecommendedCoursesForUser(userLogged));
		model.addAttribute("schedules", scheduleRepository.findByListUsersContains(userLogged)); 
		return "user";
	}

	@RequestMapping("/")
	public String userRedirectToProfile(Principal p) {
		return "redirect:/user/profile";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String nickname,@RequestParam String name,@RequestParam String surname,@RequestParam String email,@RequestParam String password,@RequestParam String age) {
		User user = userService.createNewUser(nickname,name,surname,email,password,age);
		userComponent.setLoggedUser(user);
		return "redirect:/" ;
	}

	@RequestMapping("/newUser")
	public String newUser(Model model, User user, @RequestParam String password) {
		userService.createNewUser(user, password);
		return "redirect:/";
	}

	@PostMapping("/imageUpload")
	public String imgUpload(@RequestParam MultipartFile file) {
		User u = userComponent.getLoggedUser(); // The user itself
		userService.setImage(u, file);
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/editUser")
	public String editUserProfile(Model model) {
		if(!userComponent.isLoggedUser())
		    return "redirect:/403.html";

		User loggedUser = userComponent.getLoggedUser();
		model.addAttribute("userPage", loggedUser); // Variable that i use // Data access
		return "profile_settings";
	}

	@PostMapping(value = "/editedUser")
	public String userProfileEdit(@RequestParam Map<String, String> params) {
 		User loggedUser = userComponent.getLoggedUser();
		userService.editUser(loggedUser, params);

		return "redirect:/user/profile";
	}

}
