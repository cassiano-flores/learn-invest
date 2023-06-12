package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.domain.enums.AnswerOptions;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Question {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String questionText;
    @Column(name = "alternative_a")
    private String alternativeA;
    @Column(name = "alternative_b")
    private String alternativeB;
    @Column(name = "alternative_c")
    private String alternativeC;
    @Column(name = "alternative_d")
    private String alternativeD;

    @Enumerated(STRING)
    private AnswerOptions answer;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
}
