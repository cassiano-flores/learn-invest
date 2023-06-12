package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class UsuarioChallenge {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_sender_id")
    private Usuario usuarioSender;

    @ManyToOne
    @JoinColumn(name = "usuario_receiver_id")
    private Usuario usuarioReceiver;

    @OneToOne(mappedBy = "usuarioChallenge")
    private Challenge challenge;
}
