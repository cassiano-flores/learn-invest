package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Achievement;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.domain.enums.LeagueTitle;
import br.com.cwi.crescer.api.factories.AchievementFactory;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.factories.CourseFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ActivityRepository;
import br.com.cwi.crescer.api.repository.CourseRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static br.com.cwi.crescer.api.domain.enums.LeagueTitle.INVESTIDOR_MESTRE;
import static br.com.cwi.crescer.api.domain.enums.LeagueTitle.POUPADOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ResultActivityServiceTest {

    public static final int XP_EARNED_ON_COURSE_CONCLUSION = 300;

    @InjectMocks
    private ResultActivityService tested;

    @Mock
    private SearchActivityService searchActivityService;

    @Mock
    private SearchCourseService searchCourseService;

    @Mock
    private SearchAchievementService searchAchievementService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private CourseRepository courseRepository;

    private Activity activity;
    private Usuario usuario;
    private Course course;
    private Achievement achievementCompleteCourse;

    private Long activityId;
    private Long achievementCompleteCourseId;

    @BeforeEach
    public void setUp() {
        activity = ActivityFactory.get();
        usuario = UsuarioFactory.get();
        course = activity.getCourse();
        achievementCompleteCourse = AchievementFactory.get();

        activityId = activity.getId();
        achievementCompleteCourseId = course.getAchievementCompleteCourseId();
    }

    @Test
    @DisplayName("Deve alterar atividade concluida para true")
    void deveAlterarAtividadeConcluidaParaTrue() {

        when(searchActivityService.byId(activityId)).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourseId)).thenReturn(achievementCompleteCourse);

        tested.result(activityId);

        verify(searchActivityService).byId(activityId);
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourseId);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);

        assertTrue(usuario.getFinishedActivities().contains(activity));
    }

    @Test
    @DisplayName("Deve ganhar 300 xp e achievementComplete quando curso for concluído")
    void deveGanhar300XpEAchievementCompleteQuandoCursoConcluido() {

        activity = ActivityFactory.getNoCourse();
        course = CourseFactory.getNoActivity();
        course.setActivities(Collections.singletonList(activity));
        activity.setCourse(course);
        usuario.setCourses(Collections.singletonList(course));

        activityId = activity.getId();
        achievementCompleteCourseId = course.getAchievementCompleteCourseId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourseId)).thenReturn(achievementCompleteCourse);

        final int XP_EARNED_ON_COURSE_CONCLUSION = 300;
        Long xpExpected = usuario.getXp() + XP_EARNED_ON_COURSE_CONCLUSION;

        tested.result(activityId);

        verify(searchActivityService).byId(activityId);
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourseId);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);
        verify(usuarioRepository).save(usuario);

        assertEquals(xpExpected, usuario.getXp());
        assertTrue(usuario.getAchievements().contains(achievementCompleteCourse));
    }

    @Test
    @DisplayName("Deve adicionar o próximo curso para o usuário quando concluído e não é o último")
    void deveAdicionarProximoCursoQuandoConcluidoENaoUltimo() {

        course = CourseFactory.getNoActivity();
        Course nextCourse = CourseFactory.get();
        Long nextCourseId = course.getNextCourseId();
        usuario.setCourses(new ArrayList<>(List.of(course)));
        nextCourse.setId(nextCourseId);
        course.setActivities(Collections.singletonList(activity));
        activity.setCourse(course);
        long countSizePlusOne = usuario.getCourses().size() + 1;

        achievementCompleteCourseId = course.getAchievementCompleteCourseId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourseId)).thenReturn(achievementCompleteCourse);
        when(courseRepository.count()).thenReturn(countSizePlusOne);
        when(searchCourseService.byId(nextCourseId)).thenReturn(nextCourse);

        tested.result(activityId);

        verify(searchActivityService).byId(activityId);
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourseId);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);
        verify(usuarioRepository).save(usuario);
        verify(searchCourseService).byId(nextCourseId);

        boolean foundedNextCourse = usuario.getCourses().stream()
                .anyMatch(course -> Objects.equals(course.getId(), nextCourseId));

        assertTrue(foundedNextCourse);
    }

    @Test
    @DisplayName("Não deve adicionar o próximo curso para o usuário quando concluído E SER o último")
    void naoDeveAdicionarProximoCursoQuandoConcluidoESerUltimo() {

        course = CourseFactory.getNoActivity();
        Course nextCourse = CourseFactory.get();
        Long nextCourseId = course.getNextCourseId();
        usuario.setCourses(new ArrayList<>(List.of(course)));
        usuario.addCourse(nextCourse);
        nextCourse.setId(nextCourseId);
        course.setActivities(Collections.singletonList(activity));
        activity.setCourse(course);
        long countSizePlusOne = usuario.getCourses().size() + 1;

        int expectedLength = usuario.getCourses().size();

        achievementCompleteCourseId = course.getAchievementCompleteCourseId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourseId)).thenReturn(achievementCompleteCourse);
        when(courseRepository.count()).thenReturn(countSizePlusOne);
        when(searchCourseService.byId(nextCourseId)).thenReturn(nextCourse);

        tested.result(activityId);

        verify(searchActivityService).byId(activityId);
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourseId);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);
        verify(usuarioRepository).save(usuario);
        verify(searchCourseService).byId(nextCourseId);

        assertEquals(expectedLength, usuario.getCourses().size());
    }

    @Test
    @DisplayName("Deve lançar exceção quando atividade não for encontrada")
    void deveLancarExcecaoQuandoAtividadeNaoForEncontrada() {

        when(searchActivityService.byId(activityId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Activity not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.result(activityId));

        verify(searchActivityService).byId(activityId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        when(usuarioAutenticadoService.get()).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.result(activityId));

        verify(usuarioAutenticadoService).get();
    }

    @Test
    @DisplayName("Deve lançar exceção quando conquista não for encontrada")
    void deveLancarExcecaoQuandoConquistaNaoForEncontrada() {

        when(searchActivityService.byId(activityId)).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourseId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Achievement not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.result(activityId));

        verify(searchActivityService).byId(activityId);
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourseId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando curso não for encontrado")
    void deveLancarExcecaoQuandoCursoNaoForEncontrado() {

        course = CourseFactory.getNoActivity();
        Course nextCourse = CourseFactory.get();
        Long nextCourseId = course.getNextCourseId();
        usuario.setCourses(new ArrayList<>(List.of(course)));
        nextCourse.setId(nextCourseId);
        course.setActivities(Collections.singletonList(activity));
        activity.setCourse(course);
        long countSizePlusOne = usuario.getCourses().size() + 1;

        achievementCompleteCourseId = course.getAchievementCompleteCourseId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourseId)).thenReturn(achievementCompleteCourse);
        when(courseRepository.count()).thenReturn(countSizePlusOne);
        when(searchCourseService.byId(course.getNextCourseId())).thenThrow(new ResponseStatusException(NOT_FOUND, "Course not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.result(activityId));

        verify(searchActivityService).byId(activityId);
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourseId);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);
        verify(searchCourseService).byId(nextCourseId);
    }

    @Test
    @DisplayName("Deve adicionar atividade concluída caso seja a primeira vez")
    void deveAdicionarAtividadeConcluidaCasoSejaAPrimeiraVez() {

        Activity activity = ActivityFactory.get();
        Usuario usuario = UsuarioFactory.get();
        usuario.setFinishedActivities(new ArrayList<>());

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.result(activity.getId());

        assertTrue(usuario.getFinishedActivities().contains(activity));
    }

    @Test
    @DisplayName("Não deve adicionar atividade concluída caso não seja a primeira vez")
    void naoDeveAdicionarAtividadeConcluidaCasoNaoSejaAPrimeiraVez() {

        Activity activity = ActivityFactory.get();
        Usuario usuario = UsuarioFactory.get();
        usuario.setFinishedActivities(Collections.singletonList(activity));

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.result(activity.getId());

        assertEquals(Collections.frequency(usuario.getFinishedActivities(), activity), 1);
    }

    @Test
    @DisplayName("Deve ganhar xp e achievement quando atividade concluída")
    void deveGanharXpEAchievementQuandoAtividadeConcluida() {

        Achievement achievementCompleteCourse = AchievementFactory.get();

        Course course = CourseFactory.getNoActivity();
        course.setAchievementCompleteCourseId(achievementCompleteCourse.getId());

        Activity activity = ActivityFactory.get();
        activity.setCourse(course);

        Usuario usuario = UsuarioFactory.get();
        usuario.setAchievements(new ArrayList<>());

        List<Achievement> achievementsExpected = Collections.singletonList(achievementCompleteCourse);
        Long xpExpected = usuario.getXp() + XP_EARNED_ON_COURSE_CONCLUSION;

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourse.getId())).thenReturn(achievementCompleteCourse);

        tested.result(activity.getId());

        verify(usuarioRepository).save(usuario);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);

        assertEquals(achievementsExpected, usuario.getAchievements());
        assertEquals(xpExpected, usuario.getXp());
    }

    @Test
    @DisplayName("Deve avançar de league title quando passar de nível")
    void deveAvancarLeagueTitleQuandoPassarDeNivel() {

        Achievement achievementCompleteCourse = AchievementFactory.get();

        Course course = CourseFactory.getNoActivity();
        course.setAchievementCompleteCourseId(achievementCompleteCourse.getId());

        Activity activity = ActivityFactory.get();
        activity.setCourse(course);

        Usuario usuario = UsuarioFactory.get();

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourse.getId())).thenReturn(achievementCompleteCourse);

        tested.result(activity.getId());

        verify(usuarioRepository).save(usuario);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);

        assertEquals(POUPADOR, usuario.getLeagueTitle());
    }

    @Test
    @DisplayName("Não deve adicionar o próximo curso se já estiver na lista de cursos do usuário")
    void naoDeveAdicionarProximoCursoCasoJaEsteja() {

        Achievement achievementCompleteCourse = AchievementFactory.get();

        Course course = CourseFactory.get();
        course.setAchievementCompleteCourseId(achievementCompleteCourse.getId());

        Course nextCourse = CourseFactory.get();
        nextCourse.setAchievementCompleteCourseId(AchievementFactory.get().getId());

        Activity activity = ActivityFactory.get();
        activity.setCourse(course);

        Usuario usuario = UsuarioFactory.get();
        usuario.setAchievements(Collections.singletonList(achievementCompleteCourse));
        usuario.setCourses(Collections.singletonList(nextCourse));
        usuario.setXp(0L);

        List<Achievement> achievementsExpected = usuario.getAchievements();
        List<Course> coursesExpected = usuario.getCourses();
        Long xpExpected = usuario.getXp();
        LeagueTitle leagueTitleExpected = usuario.getLeagueTitle();

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.result(activity.getId());

        verify(usuarioRepository).save(usuario);
        verify(activityRepository).save(activity);

        assertEquals(achievementsExpected, usuario.getAchievements());
        assertEquals(coursesExpected, usuario.getCourses());
        assertEquals(xpExpected, usuario.getXp());
        assertEquals(leagueTitleExpected, usuario.getLeagueTitle());
    }

    @Test
    @DisplayName("Não deve ganhar xp nem o achievement quando estiver praticando")
    void naoDeveGanharXpNemAchievementAoPraticar() {

        Activity activity = ActivityFactory.getNoCourse();
        Activity activityForCourse = ActivityFactory.getNoCourse();
        Usuario usuario = UsuarioFactory.get();
        Course course = CourseFactory.getNoActivity();
        Achievement achievementCompleteCourse = AchievementFactory.get();
        activity.setCourse(course);
        usuario.addActivity(activityForCourse);
        usuario.addAchievement(achievementCompleteCourse);
        course.setActivities(Arrays.asList(activity, activityForCourse));
        course.setAchievementCompleteCourseId(achievementCompleteCourse.getId());

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourse.getId())).thenReturn(achievementCompleteCourse);

        Long xpExpected = usuario.getXp();
        List<Achievement> achievementsExpected = usuario.getAchievements();

        tested.result(activity.getId());

        verify(searchActivityService).byId(activity.getId());
        verify(usuarioAutenticadoService).get();
        verify(searchAchievementService).byId(achievementCompleteCourse.getId());
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);
        verify(usuarioRepository).save(usuario);

        assertEquals(xpExpected, usuario.getXp());
        assertEquals(achievementsExpected, usuario.getAchievements());
    }

    @Test
    @DisplayName("Não deve mudar o league title quando atingido o último nível")
    void naoDeveMudarLeagueTitleQuandoAtingidoUltimoNivel() {

        Achievement achievementCompleteCourse = AchievementFactory.get();

        Course course = CourseFactory.getNoActivity();
        course.setAchievementCompleteCourseId(achievementCompleteCourse.getId());
        course.setNextCourseId(null);

        Activity activity = ActivityFactory.get();
        activity.setCourse(course);

        Usuario usuario = UsuarioFactory.get();
        usuario.setAchievements(new ArrayList<>());
        usuario.setLeagueTitle(INVESTIDOR_MESTRE);

        List<Achievement> achievementsExpected = Collections.singletonList(achievementCompleteCourse);
        Long xpExpected = usuario.getXp() + XP_EARNED_ON_COURSE_CONCLUSION;

        when(searchActivityService.byId(activity.getId())).thenReturn(activity);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchAchievementService.byId(achievementCompleteCourse.getId())).thenReturn(achievementCompleteCourse);

        tested.result(activity.getId());

        verify(usuarioRepository).save(usuario);
        verify(activityRepository).save(activity);
        verify(courseRepository).save(course);

        assertEquals(achievementsExpected, usuario.getAchievements());
        assertEquals(xpExpected, usuario.getXp());
        assertEquals(INVESTIDOR_MESTRE, usuario.getLeagueTitle());
    }
}
