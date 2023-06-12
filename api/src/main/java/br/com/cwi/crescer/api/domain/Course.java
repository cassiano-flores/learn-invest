package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private Long nextCourseId;
    private Long achievementCompleteCourseId;

    @OneToMany(mappedBy = "course")
    @OrderBy("id")
    private List<Activity> activities = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "usuario_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    @OrderBy("id")
    private List<Usuario> usuarios = new ArrayList<>();
}
