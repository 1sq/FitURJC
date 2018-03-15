package es.fiturjc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fiturjc.model.Course;
import es.fiturjc.model.Schedule;
import es.fiturjc.repository.ScheduleRepository;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	public Schedule findById (long id) {
		return scheduleRepository.findOne(id);
	}
	
	public boolean deleteSchedule(long id) {
		Schedule s = scheduleRepository.findOne(id);
		if (s != null) {
			scheduleRepository.delete(id);
			return true;
		}
		return false;
	}
	
	public List<Schedule> getAllSchedule() {
		return scheduleRepository.findAll();
	}
	
}