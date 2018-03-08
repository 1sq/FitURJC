package es.fiturjc.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.fiturjc.component.UserComponent;
import es.fiturjc.model.Course;
import es.fiturjc.model.User;
import es.fiturjc.service.AdminService;
import es.fiturjc.service.CourseService;
import es.fiturjc.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserComponent userComponent;

	@DeleteMapping (value="/User/{id}")
	public ResponseEntity<?> deleteUser (@PathVariable long id) {
		if(adminService.deleteUser(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
//	@PutMapping (value="/{id}")
//	public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody User user ) {
//		adminService.editUser(id, user);
//			return new ResponseEntity<>(HttpStatus.OK);
//		}
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addCourse(@RequestBody Course course) {
		User userLogged = userService.findOne(userComponent.getLoggedUser().getId());
		if (userLogged != null) {
			courseService.createNewCourse(course, null); 
			return new ResponseEntity<>(course, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value="/{id}") 
	public ResponseEntity<?> deleteCourse(@PathVariable long id) { // En vez de pathvariable, hago request body ? 
		if(courseService.deleteCourse(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping (value="/{id}")
	public ResponseEntity<?> editCourse(@PathVariable long id , @RequestBody Course course) { 
		Course c = courseService.findCourse(id);
		if(c != null) {
			courseService.editCourse(course, id);
			return new ResponseEntity<>(course,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
}