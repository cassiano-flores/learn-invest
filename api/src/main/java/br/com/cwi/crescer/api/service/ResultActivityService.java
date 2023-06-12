package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Achievement;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.domain.enums.LeagueTitle;
import br.com.cwi.crescer.api.repository.ActivityRepository;
import br.com.cwi.crescer.api.repository.CourseRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultActivityService {

    @Autowired
    private SearchActivityService searchActivityService;

    @Autowired
    private SearchCourseService searchCourseService;

    @Autowired
    private SearchAchievementService searchAchievementService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourseRepository courseRepository;

    public static final int XP_EARNED_ON_COURSE_CONCLUSION = 300;

    @Transactional
    public void result(Long activityId) {

        Activity activity = searchActivityService.byId(activityId);
        Usuario usuario = usuarioAutenticadoService.get();
        Course course = activity.getCourse();
        Achievement achievementCompleteCourse = searchAchievementService.byId(course.getAchievementCompleteCourseId());

        if (!usuario.getFinishedActivities().contains(activity)) {
            usuario.addActivity(activity);
        }

        activityRepository.save(activity);

        boolean courseComplete = true;
        List<Activity> finishedActivities = usuario.getFinishedActivities();
        for (Activity courseActivity : course.getActivities()) {
            if (!finishedActivities.contains(courseActivity)) {
                courseComplete = false;
                break;
            }
        }
        courseRepository.save(course);

        if (courseComplete) {
            if (!usuario.getAchievements().contains(achievementCompleteCourse)) {
                usuario.setXp(usuario.getXp() + XP_EARNED_ON_COURSE_CONCLUSION);
                usuario.addAchievement(achievementCompleteCourse);

                LeagueTitle currentTitle = usuario.getLeagueTitle();

                if (currentTitle.getOrder() < LeagueTitle.INVESTIDOR_MESTRE.getOrder()) {
                    LeagueTitle nextTitle = LeagueTitle.values()[currentTitle.getOrder()];
                    usuario.setLeagueTitle(nextTitle);
                }
            }

            if (usuario.getCourses().size() < courseRepository.count()) {
                Course nextCourse = searchCourseService.byId(course.getNextCourseId());
                if (!usuario.getCourses().contains(nextCourse)) {
                    usuario.addCourse(nextCourse);
                }
            }
        }
        usuarioRepository.save(usuario);
    }
}
