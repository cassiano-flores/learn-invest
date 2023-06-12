package br.com.cwi.crescer.api.security.domain;

import br.com.cwi.crescer.api.domain.*;
import br.com.cwi.crescer.api.domain.enums.LeagueTitle;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "current_icon_id")
    private Icon currentIcon;

    private LocalDate createIn;
    private LocalDate updateIn;
    private Long leaguePoints;
    private Long coins;
    private Long xp;
    private boolean active;
    private String token;

    @Enumerated(STRING)
    private LeagueTitle leagueTitle;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissions = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    private List<Achievement> achievements = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    private List<Icon> icons = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "friend")
    private List<Friendship> friends = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    private List<Activity> finishedActivities = new ArrayList<>();


    public void addPermission(Permissao permission) {
        this.permissions.add(permission);
        permission.setUsuario(this);
    }

    public void addIcon(Icon icon) {
        this.icons.add(icon);
        icon.getUsuarios().add(this);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getUsuarios().add(this);
    }

    public void addActivity(Activity activity) {
        this.finishedActivities.add(activity);
        activity.getUsuarios().add(this);
    }

    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
        achievement.getUsuarios().add(this);
    }

    public void addFriendship(Friendship friendship) {
        this.friends.add(friendship);
    }

    public void removeFriendship(Friendship friendship) {
        this.friends.remove(friendship);
    }

    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        for (Course course : getCourses()) {
            activities.addAll(course.getActivities());
        }
        return activities;
    }
}
