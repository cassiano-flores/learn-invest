package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Icon {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private Long price;

    @ManyToMany
    @JoinTable(name = "usuario_icon",
            joinColumns = @JoinColumn(name = "icon_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    @OrderBy("id")
    private List<Usuario> usuarios = new ArrayList<>();
}
