package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.security.domain.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static br.com.cwi.crescer.api.domain.enums.LeagueTitle.GASTADOR;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class UsuarioFactory {

    public static Usuario get() {
        LocalDate maxDate = LocalDate.now();
        LocalDate randomDate;

        long randomDays = ThreadLocalRandom.current().nextLong(1, 365);
        randomDate = maxDate.minusDays(randomDays);

        List<Course> courses = new ArrayList<>();
        courses.add(CourseFactory.get());

        Usuario usuario = new Usuario();

        usuario.setId(getRandomLong());
        usuario.setName("Test Usuario");
        usuario.setNickname("Test");
        usuario.setEmail("test@cwi.com.br");
        usuario.setPassword("123");
        usuario.setCurrentIcon(IconFactory.get());
        usuario.setCreateIn(randomDate);
        usuario.setUpdateIn(randomDate);
        usuario.setLeaguePoints(Math.abs(getRandomLong()));
        usuario.setCoins(Math.abs(getRandomLong()));
        usuario.setXp(Math.abs(getRandomLong()));
        usuario.setLeagueTitle(GASTADOR);
        usuario.setActive(true);
        usuario.setAchievements(new ArrayList<>());
        usuario.setCourses(courses);
        usuario.setFinishedActivities(new ArrayList<>());

        return usuario;
    }
}
