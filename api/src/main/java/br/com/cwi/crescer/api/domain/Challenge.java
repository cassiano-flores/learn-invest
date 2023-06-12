package br.com.cwi.crescer.api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Challenge {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long usuarioSenderScore;
    private Long usuarioReceiverScore;
    private LocalDateTime challengedIn;
    private boolean isComplete;

    @OneToOne
    @JoinColumn(name = "usuario_challenge_id")
    private UsuarioChallenge usuarioChallenge;
}
