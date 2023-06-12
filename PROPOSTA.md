**[Crescendo Bolso]**

---

- **OBJETIVO**
  - Jogo educacional que ensine educação financeira para seus usuários de uma forma aplicada no dia a dia. O jogo irá incluir tópicos como gerenciamento de orçamento, investimentos, impostos e juros. O objetivo do jogo será ajudar os jogadores a desenvolver habilidades financeiras úteis na vida real.

---

- **Páginas principais da aplicação**
  - Criar conta;
  - Recuperar senha;
  - Login;
  - Aprender;
  - Revisar;
  - Ligas;
  - Loja;
  - Perfil;

---

- **FUNCIONALIDADES**

  - Criar conta;
      - Nome Completo;
      - Apelido (opcional);
      - E-mail;
      - Senha;

  - Recuperar senha (por e-mail);

  - Login
      - Aprender
          - O usuário deve responder as atividades de cada modulo para progredir no jogo;
          - Módulos são compostos por atividades;
          - O número de atividades do modulo varia de acordo com a sua complexidade do assunto;
          - Cada atividade possui um assunto específico relacionado ao modulo;
          - Cada modulo possui um tema. Os temas serão os seguintes:
              - O planejamento financeiro;
              - A organização das finanças pessoais;
              - O controle de gastos;
              - O uso consciente do cartão de crédito;
              - A escolha de investimentos adequados;
              - A compreensão dos juros e taxas financeiras;
              - A compreensão dos impostos brasileiros;
          - Atividades são compostas por 2 tipos de conteúdo: aula e prática.
          - A aula consiste em conteúdos textuais sobre o assunto abordado pela atividade;
          - A prática consiste em 5 questões aleatórias sobre o conteúdo abordado;
          - Atividades serão consideradas concluídas quando o usuário acertar 3 das 5 questões da parte prática da atividade, cada atividade concluída dá 200 XP;
              - em caso de falha o usuário pode repetir a aula quantas vezes necessário;
              - em caso de sucesso desbloqueará a próxima atividade;
          - O modulo será considerado concluído quando todas as atividades estiverem concluídas e o usuário receberá 1000 XP;
          - Será possível listar todos os módulos, mas não será possível acessar módulos que ainda não foram desbloqueados;
          - Completar todas as atividades de um modulo, libera a próximo;
          - Ao completar um modulo o usuário recebe as seguintes conquistas:
              - Concluiu modulo;
              - Concluiu modulo com todas as respostas corretas (caso se aplique);

      - Revisar
          - O usuário pode refazer os módulos já concluídos;
          - Será possível ver a aula e realizar uma prática já feita;
          - Atividades revisadas darão metade da XP que as aprendidas.

      - Ligas
          - Para cada amigo, o usuário tem a possibilidade de enviar um desafio;
              - Desafio: usuário que enviou, realiza uma "prática" da atividade de um dos módulos em 3 minutos, e o amigo que recebeu, deve fazer o mesmo;
              - Só será disponibilizado a pontuação dos jogadores após ambos terem respondido o desafio;
              - Só é possível enviar um novo desafio, quando não houver desafios pendentes entre os dois usuários;
              - Histórico de desafios concluídos;
              - Só é possível enviar um desafio de algum modulo que ambos já tenham concluído.
              - O vencedor do desafio ganhará 10 pontos de liga e 50 moedas;
              - O perdedor do desafio perderá 5 pontos de liga e 0 moedas;
              - Os pontos de liga não podem ser menores que 0;
          - O usuário possui um rank de acordo com seu desempenho nos desafios;
          - O rank é entre o usuário e todos os amigos;
          - O rank será definido através dos pontos de liga;
          - Todos os usuários iniciam com 0 pontos de liga;
          - Os pontos de liga serão adquiridos ao ganhar desafios entre amigos;
          - O usuário possui um título de acordo com o número de pontos de liga que possui;
          - Existem 3 títulos:
              - Investidor (mais alto)
              - Poupador (médio)
              - Gastador (mais baixo)
          - Os títulos são divididos por pontuações:
              - 0 até 40 pontos de liga: Gastador;
              - 41 até 80 pontos de liga: Poupador;
              - 81 até 100 pontos de liga: Investidor.
              - Exemplo: O jogador que atingir a pontuação de 41 pontos passará a fazer parte do rank Poupador;

      - Loja
          - Imagens e ícones utilizados pelo app serão salvos diretamente no banco;
          - Exibir moedas;
          - Itens podem ser adquiridos com as moedas do jogo obtidas através dos desafios entre amigos;
          - Itens adquiridos:
              - Ícones
              - Molduras

      - Perfil
          - Editar perfil;
          - Exibir moedas;
          - Exibir Estatísticas (dias seguidos realizando atividades, XP, liga);
          - Exibir Conquistas;
          - Amigos
              - Listagem de amigos;
              - Encontrar novo amigo;
              - Solicitar/Aceitar amizade;
              - Deletar amigo;

      - Sair (logout)

---

- **Funcionalidade da nova Tecnologia**

  - PWA: transformar a aplicação em um app para smartphones;

---

- **BACKLOG**

  - Missões diárias;
      - A realização das missões diárias libera conquistas, moedas para gastar na loja;
      - Uma missão diária consiste em responder corretamente 5 questões aleatórias de qualquer unidade que já tenha sido concluída pelos usuários.
  - Desafio:
      - pontuações mais altas por respostas certas em sequência;
      - pontuações mais altas por respostas certas em menor tempo;
  - Conquista: Ser o primeiro entre os amigos a chegar no rank investidor;
  - Fazer os ranks resetarem em uma semana, oferecer recompensas aos que obtiveram o título investidor;
  - Item na loja para não perder pontos de liga nos desafios;
  - Fazer uma melhor de 5, o usuário só terá um rank após jogar 5 desafios;

---

- **TECNOLOGIAS**

  - Banco de Dados:
      - PostgreSql
  - Backend:
      - Java;
      - Spring:
          - Spring-Web;
          - Spring-Data-JPA;
          - PostgreSql Driver;
          - Lombok;
          - Spring-Boot-Actuator;
          - Validation;
          - Spring-Security
  - Frontend:
      - React;
          - axios;
          - react-router-dom;
          - react-create-global-state
  - Devops:
      - Docker-compose;
