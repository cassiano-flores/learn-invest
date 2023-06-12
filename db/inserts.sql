-- Insert para tabela icon
INSERT INTO icon (name, price) VALUES
('Radiante', 0),
('Geekster', 50),
('Perplexo', 50),
('Dorminhoco', 50),
('Aliviado', 50),
('Beijoca', 150),
('Apaixonado', 150),
('Furioso', 150),
('Abatido', 150),
('Enjoado', 500),
('Apreensivo', 500),
('Maluquete', 500);


-- Insert para tabela achievement
INSERT INTO achievement (name, description, icon) VALUES
(
'Pequeno Poupador',
'Parabéns, você concluiu o primeiro curso de educação financeira com sucesso! Agora você sabe como economizar dinheiro e fazer um orçamento financeiro simples.',
'gastador.png'
),
(
'Investidor Iniciante',
'Incrível, você concluiu o segundo curso de educação financeira com sucesso! Agora você sabe como investir o seu dinheiro de forma inteligente e fazer o seu dinheiro trabalhar para você.',
'poupador.png'
),
(
'Planejador Financeiro',
'Parabéns, você concluiu o terceiro curso de educação financeira com sucesso! Agora você sabe como planejar a sua aposentadoria e proteger o seu patrimônio.',
'investidor.png'),
(
'Estrategista Financeiro',
'Incrível, você concluiu o quarto curso de educação financeira com sucesso! Agora você sabe como criar estratégias financeiras avançadas para alcançar seus objetivos financeiros de longo prazo.',
'investidorMestre.png'
);


-- Insert para tabela course
INSERT INTO course (name, achievement_complete_course_id) VALUES
('GASTADOR', 1),
('POUPADOR', 2),
('INVESTIDOR', 3),
('INVESTIDOR MESTRE', 4);

UPDATE course SET next_course_id = 2 WHERE id = 1;
UPDATE course SET next_course_id = 3 WHERE id = 2;
UPDATE course SET next_course_id = 4 WHERE id = 3;


-- Insert para tabela activity
INSERT INTO activity (title, lesson, course_id) VALUES
(
'Como criar um orçamento pessoal detalhado',
'Um orçamento pessoal é uma ferramenta essencial para controlar suas finanças e alcançar seus objetivos financeiros. Para criar um orçamento pessoal detalhado, comece registrando todas as suas despesas e receitas mensais. Em seguida, analise seus gastos e identifique áreas em que pode reduzir custos. Por fim, defina metas financeiras realistas e estabeleça um plano de ação para alcançá-las.',
 1
),
(
'Estratégias para economizar dinheiro no dia a dia',
'Reduzir seus gastos é uma das maneiras mais simples e eficazes de economizar dinheiro. Para economizar dinheiro no dia a dia, comece por identificar gastos desnecessários em áreas como alimentação, transporte, moradia e lazer. Em seguida, adote estratégias como cozinhar em casa, usar transporte público, trocar serviços por atividades gratuitas e pesquisar preços antes de comprar. Lembre-se de que pequenas mudanças podem fazer uma grande diferença a longo prazo.',
 1
),
(
'Formas de investir',
'É importante entender que todo investimento tem seus riscos e é importante estudar e entender bem antes de investir. Mas, investir pode ser uma ótima maneira de fazer o seu dinheiro crescer e atingir suas metas financeiras.

Investimentos em renda fixa:
Investimentos em renda fixa são investimentos que oferecem uma taxa de retorno fixa. Isso significa que você sabe exatamente quanto irá ganhar com o investimento, desde o início. Alguns exemplos são: títulos do Tesouro Direto, CDBs, LCIs, LCAs, entre outros.

Investimentos em renda variável:
Investimentos em renda variável são investimentos que oferecem uma taxa de retorno variável, ou seja, você não sabe exatamente quanto irá ganhar com o investimento. Alguns exemplos são: ações, fundos imobiliários, entre outros.

Investimentos em criptomoedas:
Investimentos em criptomoedas são investimentos em moedas virtuais como o Bitcoin, Ethereum, entre outros. As criptomoedas são conhecidas por sua volatilidade, mas também podem oferecer oportunidades de ganhos expressivos.

Investimentos em imóveis:
Investimentos em imóveis podem ser feitos comprando uma propriedade para alugar ou vender posteriormente. Também é possível investir em fundos imobiliários, que são formados por um grupo de investidores que aplicam em empreendimentos imobiliários e recebem uma parte dos rendimentos.

Lembre-se, cada tipo de investimento tem suas vantagens e desvantagens, e é importante estudar e entender bem antes de investir. Comece com pouco dinheiro e vá aumentando gradativamente conforme você ganha experiência e confiança.',
 1
),
(
'Diferenças entre gastos e investimentos',
'Muitas pessoas confundem os conceitos de gastos e investimentos, mas eles têm significados diferentes no contexto financeiro. Gastos são despesas que não geram retorno ou benefício futuro, como por exemplo, comprar roupas, comer fora, pagar contas de água e luz, etc. Investimentos são aplicações de recursos que visam obter um lucro ou uma vantagem no futuro, como por exemplo, comprar ações, fundos imobiliários, títulos públicos, etc.

A principal diferença entre gastos e investimentos é que os gastos reduzem o patrimônio líquido de uma pessoa ou empresa, enquanto os investimentos aumentam ou preservam o patrimônio líquido. Além disso, os gastos são geralmente consumidos no curto prazo, enquanto os investimentos têm uma perspectiva de longo prazo. Por isso, é importante saber diferenciar os gastos dos investimentos e planejar o orçamento de forma equilibrada, buscando reduzir os gastos desnecessários e aumentar os investimentos rentáveis.
', 2
),
(
'Como fazer um levantamento das finanças',
'Fazer um levantamento das finanças é um passo importante para quem quer ter controle sobre o seu dinheiro e planejar o seu futuro financeiro. Um levantamento das finanças consiste em registrar todas as entradas e saídas de dinheiro que ocorrem em um determinado período, como um mês ou um ano, e analisar como esse dinheiro está sendo usado. Para fazer um levantamento das finanças, você pode seguir os seguintes passos:

1. Escolha uma ferramenta para registrar as suas finanças. Pode ser um caderno, uma planilha, um aplicativo ou um software. O importante é que seja fácil de usar e que você tenha acesso frequente a ela.
2. Anote todas as suas receitas, ou seja, o dinheiro que entra na sua conta. Isso inclui o seu salário, rendimentos de investimentos, aluguéis, pensões, etc. Se possível, separe as receitas fixas das variáveis, ou seja, aquelas que são garantidas todos os meses daquelas que podem mudar de acordo com a situação.
3. Anote todas as suas despesas, ou seja, o dinheiro que sai da sua conta. Isso inclui as contas fixas, como aluguel, água, luz, telefone, internet, etc., as despesas variáveis, como alimentação, transporte, lazer, etc., e as dívidas, como cartão de crédito, empréstimos, financiamentos, etc. Se possível, separe as despesas essenciais das supérfluas, ou seja, aquelas que são necessárias para a sua sobrevivência e bem-estar daquelas que podem ser reduzidas ou eliminadas.
4. Compare as suas receitas e despesas e verifique se o seu saldo é positivo ou negativo. O saldo é a diferença entre o que você ganha e o que você gasta. Se o saldo for positivo, significa que você está gastando menos do que ganha e pode poupar ou investir o dinheiro que sobra. Se o saldo for negativo, significa que você está gastando mais do que ganha e precisa cortar gastos ou aumentar a sua renda para equilibrar as suas finanças.
5. Faça uma análise crítica das suas finanças e identifique os pontos fortes e fracos. Por exemplo, você pode verificar quais são as suas maiores fontes de receita e de despesa, quais são os seus hábitos de consumo e de poupança, quais são os seus objetivos financeiros e quais são os obstáculos para alcançá-los. A partir dessa análise, você pode traçar um plano de ação para melhorar as suas finanças e alcançar os seus sonhos.', 2
),
(
'Entendendo cartão de crédito',
'Um cartão de crédito é um meio de pagamento eletrônico que permite ao usuário comprar bens e serviços em estabelecimentos comerciais ou online, parcelar suas compras e pagar juros sobre o saldo devedor. O cartão de crédito também pode ser usado para sacar dinheiro em caixas eletrônicos ou transferir valores para outras contas.

Para ter um cartão de crédito, é preciso solicitar a uma instituição financeira, como um banco ou uma fintech, que faça uma análise de crédito e aprove o seu pedido. Você receberá um limite de crédito, que é o valor máximo que você pode gastar com o cartão. A cada mês, você receberá uma fatura com o resumo das suas compras e o valor total a pagar. Você pode escolher pagar o valor integral, o mínimo ou qualquer valor entre eles. Se você não pagar o valor integral, entrará no rotativo do cartão e pagará juros sobre o saldo restante.

Existem diversos tipos de cartão de crédito, com diferentes bandeiras, anuidades, benefícios e taxas. Alguns cartões oferecem vantagens como cashback, milhas, descontos, seguros e serviços. Outros cartões são sem anuidade ou têm anuidade reduzida ou gratuita dependendo do seu gasto mensal. Você deve escolher o cartão que melhor se adapta ao seu perfil financeiro e às suas necessidades.

Para solicitar um cartão de crédito, você pode acessar os sites das instituições financeiras ou usar comparadores online que mostram as melhores opções para você. Você precisará preencher um formulário com seus dados pessoais, profissionais e financeiros e enviar documentos como RG, CPF e comprovante de renda e residência. A instituição financeira fará uma análise de crédito e informará se o seu pedido foi aprovado ou não. Se for aprovado, você receberá o seu cartão em casa ou poderá gerar um cartão virtual para usar imediatamente.
', 2
),
(
'O que é renda fixa',
'Renda fixa é uma modalidade de investimento que oferece uma remuneração previsível e estável ao investidor. Nesse tipo de aplicação, o investidor sabe, no momento da contratação, qual será o rendimento do seu dinheiro ao final do prazo estabelecido. Isso significa que há um menor risco de perdas ou surpresas negativas em relação ao retorno esperado.

Existem diversos tipos de investimentos em renda fixa, que podem ser classificados em públicos ou privados, pré-fixados ou pós-fixados, e com diferentes prazos e liquidez. Os investimentos públicos são aqueles emitidos pelo governo federal, como os títulos do Tesouro Direto. Os investimentos privados são aqueles emitidos por instituições financeiras ou empresas, como os CDBs, LCIs, LCAs, debêntures, entre outros.

Os investimentos pré-fixados são aqueles que têm a taxa de juros definida no momento da contratação, ou seja, o investidor sabe exatamente quanto vai receber ao final do prazo. Os investimentos pós-fixados são aqueles que têm a taxa de juros atrelada a um indicador de referência, como a Selic ou o CDI, ou seja, o investidor só saberá quanto vai receber ao final do prazo de acordo com a variação desse indicador.

Os investimentos em renda fixa também podem ter diferentes prazos e liquidez. O prazo é o tempo que o investidor deve deixar o seu dinheiro aplicado para receber o rendimento acordado. A liquidez é a facilidade de resgatar o dinheiro antes do prazo. Alguns investimentos têm prazos mais curtos e maior liquidez, como os CDBs com liquidez diária. Outros têm prazos mais longos e menor liquidez, como as debêntures.

A escolha do melhor investimento em renda fixa depende do perfil e dos objetivos de cada investidor. De modo geral, essa modalidade é indicada para quem busca segurança, previsibilidade e rentabilidade acima da poupança.
', 3
),
(
'Investimentos de risco',
'Investimentos de risco são aqueles que oferecem uma alta possibilidade de perda do capital investido, mas também uma alta expectativa de retorno. Esses investimentos são indicados para investidores com perfil arrojado, que aceitam correr mais riscos em busca de maiores ganhos, e que possuem um horizonte de longo prazo.

Alguns exemplos de investimentos de risco são: ações, fundos de ações, fundos multimercado, fundos imobiliários, criptomoedas, derivativos e opções. Cada um desses investimentos possui características próprias, vantagens e desvantagens, e requer um conhecimento específico do mercado e das suas oscilações.

Antes de investir em qualquer produto de risco, é importante fazer uma análise do seu perfil de investidor, dos seus objetivos financeiros, do seu nível de tolerância ao risco e da sua capacidade de diversificação. Também é recomendável estudar bem o investimento escolhido, acompanhar o seu desempenho e estar preparado para eventuais perdas.

Investir em risco não significa apostar na sorte ou no acaso. Significa assumir uma postura mais ousada e dinâmica diante das oportunidades do mercado financeiro, mas sempre com responsabilidade e estratégia.', 3
),
(
'Entendendo imposto de renda',
'O imposto de renda é um tributo que incide sobre a renda e os proventos de contribuintes residentes no país ou no exterior. O imposto de renda é declarado anualmente à Receita Federal, que é o órgão responsável por fiscalizar e arrecadar os tributos federais. A declaração de imposto de renda é obrigatória para quem se enquadra em determinados critérios, como ter recebido rendimentos tributáveis acima de um limite estabelecido pela Receita ou possuir bens e direitos de valor superior a outro limite. A declaração de imposto de renda deve ser feita entre 15 de março e 31 de maio de cada ano, por meio do programa gerador da declaração, disponível no site da Receita Federal. Quem entrega a declaração fora do prazo está sujeito a multa pelo atraso. A declaração de imposto de renda serve para informar à Receita Federal os rendimentos, as despesas, os bens, os direitos, as dívidas e as doações do contribuinte no ano anterior. A partir dessas informações, a Receita Federal calcula se o contribuinte pagou mais ou menos imposto do que deveria ao longo do ano. Se pagou mais, tem direito à restituição do imposto; se pagou menos, deve pagar a diferença. A restituição do imposto é feita em lotes mensais, de junho a dezembro, conforme a ordem de entrega da declaração. O pagamento da diferença pode ser feito à vista ou parcelado em até oito vezes, com acréscimo de juros. A declaração de imposto de renda é um instrumento importante para garantir a justiça fiscal e o financiamento dos serviços públicos prestados pelo governo federal. Por isso, é essencial que o contribuinte declare corretamente seus dados e cumpra os prazos estabelecidos pela Receita Federal.', 3
),
(
'Imposto de renda avançado',
'O imposto de renda é um tributo que incide sobre a renda das pessoas físicas e jurídicas. O cálculo do imposto de renda depende de vários fatores, como o tipo de rendimento, a alíquota, a base de cálculo e as deduções.

O tipo de rendimento é a origem da renda, que pode ser do trabalho, de capital, de pensão, de aluguel, etc. Cada tipo de rendimento tem uma forma de tributação específica, que pode ser na fonte, exclusiva ou definitiva.

A alíquota é a porcentagem que é aplicada sobre a base de cálculo para obter o valor do imposto. A alíquota varia conforme a faixa de renda do contribuinte e o tipo de rendimento. Por exemplo, para os rendimentos do trabalho, as alíquotas vão de 7,5% a 27,5%, enquanto para os rendimentos de capital, as alíquotas são de 15% ou 22,5%.

A base de cálculo é o valor sobre o qual o imposto é calculado. A base de cálculo pode ser bruta ou líquida. A base bruta é o valor total dos rendimentos recebidos no período. A base líquida é o valor dos rendimentos menos as deduções.

As deduções são os valores que podem ser abatidos da base de cálculo para reduzir o imposto a pagar. As deduções podem ser legais ou incentivadas. As deduções legais são aquelas previstas na lei, como as despesas com saúde, educação e previdência. As deduções incentivadas são aquelas que estimulam o contribuinte a destinar parte do imposto para fins sociais, culturais ou esportivos.

Para calcular o imposto de renda, o contribuinte deve somar todos os seus rendimentos tributáveis no período, aplicar as alíquotas correspondentes e subtrair as deduções permitidas. O resultado é o valor do imposto devido ou restituído.', 4
),
(
'Imposto sobre investimentos',
'O imposto sobre investimentos é um tributo que incide sobre os rendimentos obtidos por meio de aplicações financeiras, como ações, fundos, títulos públicos, poupança, entre outras. O objetivo desse imposto é arrecadar recursos para o governo e também regular a oferta e a demanda de dinheiro na economia.

O imposto sobre investimentos varia de acordo com o tipo, o prazo e a modalidade de investimento. Em geral, quanto maior o prazo e menor o risco da aplicação, menor é a alíquota do imposto. Por exemplo, a poupança é isenta de imposto sobre investimentos, enquanto as ações estão sujeitas à alíquota de 15% sobre os lucros.

O imposto sobre investimentos é cobrado na fonte, ou seja, o valor é descontado automaticamente no momento do resgate ou do recebimento dos rendimentos. O investidor deve declarar os valores recebidos na sua declaração anual de imposto de renda e pagar eventuais diferenças ou receber restituições.

O imposto sobre investimentos é importante para o funcionamento da economia, pois estimula o investimento de longo prazo e desestimula a especulação financeira. Além disso, contribui para a arrecadação do governo e para o financiamento de políticas públicas nas áreas de saúde, educação, infraestrutura, entre outras.', 4
),
(
'Juros',
'Juros são uma forma de remunerar o dinheiro que é emprestado ou investido. Quando alguém pega um empréstimo, por exemplo, precisa devolver o valor principal mais uma taxa de juros, que é o custo do dinheiro no tempo. Já quando alguém investe em uma aplicação financeira, recebe uma rentabilidade que é composta pelo valor principal mais uma taxa de juros.

Entender como funcionam os juros é fundamental para a educação financeira, pois eles podem ser aliados ou inimigos da sua saúde financeira. Se você souber usar os juros a seu favor, pode fazer o seu dinheiro render mais e alcançar seus objetivos financeiros. Mas se você não tiver cuidado com os juros, pode se endividar e comprometer o seu orçamento.

Existem dois tipos principais de juros: simples e compostos. Os juros simples são calculados sobre o valor inicial do empréstimo ou investimento, sem considerar os juros acumulados ao longo do tempo. Já os juros compostos são calculados sobre o saldo atualizado do empréstimo ou investimento, ou seja, levam em conta os juros acumulados nos períodos anteriores. Os juros compostos são mais comuns no mercado financeiro e têm um efeito exponencial sobre o valor final.

Para calcular os juros simples, basta multiplicar o valor inicial pela taxa de juros e pelo número de períodos. Por exemplo, se você pegar um empréstimo de R$ 1.000,00 com uma taxa de juros de 10% ao mês por 6 meses, terá que pagar R$ 1.600,00 no final, sendo R$ 600,00 de juros simples (1.000 x 0,10 x 6).

Para calcular os juros compostos, é preciso usar a fórmula:

Valor final = Valor inicial x (1 + taxa de juros) elevado ao número de períodos

Por exemplo, se você investir R$ 1.000,00 com uma taxa de juros de 10% ao mês por 6 meses, terá R$ 1.771,56 no final, sendo R$ 771,56 de juros compostos. Note que o valor final é maior do que no caso dos juros simples, pois os juros são calculados sobre o saldo atualizado a cada mês.

Os juros podem ser nominais ou reais. Os juros nominais são aqueles que não levam em conta a inflação, ou seja, a variação dos preços na economia. Já os juros reais são aqueles que descontam a inflação do período. Os juros reais mostram o ganho real do seu dinheiro, pois indicam o quanto ele aumentou ou diminuiu o seu poder de compra.

Para calcular os juros reais, é preciso usar a fórmula:

Juros reais = [(1 + Juros nominais) / (1 + Inflação)] - 1

Por exemplo, se você investir R$ 1.000,00 com uma taxa de juros nominal de 10% ao ano e a inflação for de 5% ao ano, terá R$ 1.100,00 no final do ano em termos nominais. Mas em termos reais, terá R$ 1.047,62, pois os seus R$ 1.000,00 iniciais valeriam R$ 1.050,00 após a inflação. Logo, os seus juros reais seriam de 4,76% ao ano ([(1 + 0,10) / (1 + 0,05)] - 1).

Portanto, para ter uma boa educação financeira, é importante saber como os juros funcionam e como eles afetam as suas decisões financeiras. Assim, você pode planejar melhor o seu orçamento e escolher as melhores opções de crédito e investimento para o seu perfil.', 4
);


-- Insert para tabela question
INSERT INTO question (question_text, alternative_a, alternative_b, alternative_c, alternative_d, answer, activity_id) VALUES
('Qual é o primeiro passo para criar um orçamento pessoal detalhado?', 'Analisar', 'Gastar', 'Poupar', 'Investir', 'A', 1),
('O que é preciso fazer para analisar os gastos mensais?', 'Registrar', 'Ignorar', 'Reduzir', 'Aumentar', 'B', 1),
('Como registrar os gastos mensais de forma eficiente?', 'Planilha', 'Bilhete', 'Memória', 'Agenda', 'A', 1),
('Quais são as categorias mais comuns de gastos mensais?', 'Fixos', 'Variáveis', 'Extras', 'Todas as anteriores', 'D', 1),
('Qual é a recomendação geral para a taxa de poupança mensal?', '10%', '20%', '30%', '40%', 'C', 1),
('Qual é o primeiro passo para criar um orçamento pessoal?', 'Receitas', 'Despesas', 'Metas', 'Investimentos', 'A', 1),
('O que são despesas fixas?', 'Aluguel', 'Lazer', 'Educação', 'Imprevistos', 'A', 1),
('O que são despesas variáveis?', 'Transporte', 'Saúde', 'Alimentação', 'Todas as anteriores', 'D', 1),
('Qual é a melhor forma de controlar seus gastos?', 'Planilha', 'Cartão', 'Impulso', 'Sorte', 'A', 2),
('Como economizar na conta de luz?', 'Desligar', 'Acender', 'Ignorar', 'Reclamar', 'A', 2),
('O que é um fundo de emergência?', 'Reserva', 'Investimento', 'Dívida', 'Luxo', 'A', 2),
('Como evitar compras por impulso?', 'Planejar', 'Parcelar', 'Esperar', 'Desistir', 'C', 2),
('Qual é a vantagem de pagar à vista?', 'Desconto', 'Juros', 'Crédito', 'Status', 'A', 2),
('O que fazer com o dinheiro que sobra no final do mês?', 'Poupar', 'Gastar', 'Doar', 'Esconder', 'A', 2),
('Como definir metas financeiras?', 'SMART', 'VAGAS', 'CARAS', 'FÁCEIS', 'A', 2),
('Como negociar dívidas com credores?', 'Proposta', 'Fuga', 'Ameaça', 'Silêncio', 'A', 2),
('Como aproveitar as promoções sem se endividar?', 'Orçamento', 'Limite', 'Tentação', 'Ilusão', 'A', 2),
('Como aumentar sua renda?', 'Renda extra', 'Loteria', 'Herança', 'Mágica', 'A', 2),
('Qual é a forma mais segura de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Dólar', 'A', 3),
('Qual é a forma mais rentável de investir?', 'Ações', 'Poupança', 'Bitcoin', 'Dólar', 'C', 3),
('Qual é a forma mais volátil de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Dólar', 'C', 3),
('Qual é a forma mais líquida de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Dólar', 'D', 3),
('Qual é a forma mais diversificada de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Fundos', 'D', 3),
('Qual é a forma mais simples de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Fundos', 'A', 3),
('Qual é a forma mais complexa de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Derivativos', 'D', 3),
('Qual é a forma mais tributada de investir?', 'Poupança', 'Ações', 'Bitcoin', 'Renda fixa', 'B', 3),
('Qual é a forma mais isenta de impostos de investir?', 'Poupança', 'Ações', 'Bitcoin', 'LCI/LCA', 'D', 3),
('Qual é a forma mais sustentável de investir?', 'Poupança', 'Ações ESG', 'Bitcoin', 'Ouro', 'B', 3),
('O que é um gasto?', 'Despesa', 'Receita', 'Lucro', 'Ativo', 'A', 4),
('O que é um investimento?', 'Renda', 'Custo', 'Aplicação', 'Passivo', 'C', 4),
('Qual é a diferença entre gasto fixo e gasto variável?', 'Período', 'Valor', 'Necessidade', 'Origem', 'B', 4),
('Qual é a diferença entre investimento produtivo e investimento financeiro?', 'Retorno', 'Risco', 'Objetivo', 'Natureza', 'D', 4),
('O que é o retorno sobre o investimento (ROI)?', 'Ganho', 'Percentual', 'Prazo', 'Taxa', 'B', 4),
('O que é o custo de oportunidade de um investimento?', 'Benefício', 'Alternativa', 'Sacrifício', 'Comparação', 'C', 4),
('O que é a taxa interna de retorno (TIR) de um investimento?', 'Mínima', 'Máxima', 'Média', 'Efetiva', 'A', 4),
('O que é o valor presente líquido (VPL) de um investimento?', 'Desconto', 'Fluxo', 'Saldo', 'Diferença', 'D', 4),
('O que é a depreciação de um ativo?', 'Perda', 'Redução', 'Ajuste', 'Valorização', 'B', 4),
('O que é a amortização de uma dívida?', 'Pagamento', 'Parcela', 'Juros', 'Abatimento', 'D', 4),
('Qual é o primeiro passo para fazer um levantamento das finanças?', 'Planejar', 'Gastar', 'Poupar', 'Investir', 'A', 5),
('Qual é o primeiro passo para fazer um levantamento das finanças?', 'Analisar', 'Planejar', 'Executar', 'Avaliar', 'A', 5),
('O que é um fluxo de caixa?', 'Entrada', 'Saída', 'Movimento', 'Saldo', 'C', 5),
('Como calcular a receita bruta de um negócio?', 'Vendas - Custos', 'Vendas - Despesas', 'Vendas - Impostos', 'Vendas + Lucro', 'B', 5),
('O que são custos fixos e variáveis?', 'Dependem da produção', 'Não dependem da produção', 'São iguais todos os meses', 'São diferentes todos os meses', 'D', 5),
('Como calcular o ponto de equilíbrio de um negócio?', 'Receita = Custos', 'Receita = Lucro', 'Receita = Despesas', 'Receita = Impostos', 'A', 5),
('Qual é o primeiro passo para fazer um levantamento das finanças?', 'Analisar', 'Orçar', 'Investir', 'Economizar', 'A', 6),
('O que é um fluxo de caixa?', 'Uma ferramenta', 'Uma despesa', 'Uma receita', 'Uma dívida', 'A', 6),
('O que é um fluxo de caixa?', 'Entrada', 'Saída', 'Diferença', 'Movimento', 'D', 6),
('Como calcular a margem de lucro de um produto ou serviço?', '(Receita - Custo) / Receita', '(Receita - Custo) / Custo', '(Custo - Receita) / Receita', '(Custo - Receita) / Custo', 'A', 6),
('O que é um orçamento?', 'Previsão', 'Planejamento', 'Controle', 'Avaliação', 'B', 6),
('O que é receita?', 'Dinheiro', 'Entrada', 'Saída', 'Lucro', 'B', 6),
('O que é despesa?', 'Custo', 'Gasto', 'Investimento', 'Perda', 'C', 6),
('O que é saldo?', 'Diferença', 'Sobra', 'Déficit', 'Superávit', 'A', 6),
('O que significa renda fixa?', 'Investimento', 'Salário', 'Juros', 'Dividendo', 'C', 7),
('Qual é o principal risco da renda fixa?', 'Inflação', 'Volatilidade', 'Liquidez', 'Default', 'D', 7),
('Qual é a diferença entre renda fixa pré e pós-fixada?', 'Taxa de juros', 'Prazo de vencimento', 'Valor nominal', 'Indexador', 'A', 7),
('Qual é o órgão responsável por regular o mercado de renda fixa no Brasil?', 'Banco Central', 'CVM', 'ANBIMA', 'B3', 'B', 7),
('Qual é o imposto que incide sobre os rendimentos da renda fixa?', 'IRPF', 'IRPJ', 'IOF', 'IPTU', 'A', 7),
('Qual é o tipo de investimento que oferece maior rentabilidade e maior risco?', 'Ações', 'Títulos', 'Fundos', 'Poupança', 'A', 8),
('Qual é o conceito de investimento de risco?', 'Renda fixa', 'Renda variável', 'Poupança', 'Tesouro direto', 'B', 8),
('Quais são os principais tipos de investimentos de risco?', 'Ações', 'Opções', 'Futuros', 'Todos os anteriores', 'D', 8),
('Qual é o conceito de investimento de risco?', 'Rentabilidade', 'Volatilidade', 'Liquidez', 'Diversificação', 'B', 8),
('Quais são os principais tipos de investimentos de risco?', 'Ações', 'Debêntures', 'Fundos', 'Todos', 'D', 8),
('Qual a alíquota máxima do imposto de renda para pessoas físicas?', '22,5%', '25%', '27,5%', '30%', 'C', 10),
('Qual o valor mínimo de rendimentos tributáveis para ser obrigado a declarar o imposto de renda?', 'R$ 28.559,70', 'R$ 29.559,70', 'R$ 30.559,70', 'R$ 31.559,70', 'A', 9),
('Quem pode ser considerado dependente na declaração do imposto de renda?', 'Filhos até 21 anos', 'Filhos até 24 anos', 'Filhos até 27 anos', 'Filhos até 30 anos', 'B', 9),
('Qual o limite de dedução por dependente na declaração do imposto de renda?', 'R$ 2.275,08', 'R$ 2.375,08', 'R$ 2.475,08', 'R$ 2.575,08', 'A', 9),
('Qual o limite de dedução por despesas com educação na declaração do imposto de renda?', 'R$ 3.561,50', 'R$ 3.661,50', 'R$ 3.761,50', 'R$ 3.861,50', 'A', 9),
('Qual o modelo de declaração que permite deduzir despesas com saúde e educação?', 'Simplificado', 'Completo', 'Anual', 'Mensal', 'B', 9),
('Qual é o nome do documento que deve ser enviado à Receita Federal?', 'Declaração', 'Recibo', 'Comprovante', 'Extrato', 'A', 10),
('Qual é o prazo final para a entrega da declaração do imposto de renda?', '30 de abril', '31 de maio', '30 de junho', '31 de julho', 'B', 9),
('Qual é o valor da multa por atraso na entrega da declaração?', '1% ao mês', '2% ao mês', '3% ao mês', '4% ao mês', 'A', 10),
('Quais são os tipos de declaração do imposto de renda?', 'Simplificada e completa', 'Simples e complexa', 'Básica e avançada', 'Normal e especial', 'A', 10),
('O que fazer se a declaração do imposto de renda estiver errada?', 'Retificar a declaração', 'Cancelar a declaração', 'Ignorar a declaração', 'Repetir a declaração', 'A', 10),
('Qual é o imposto que incide sobre os rendimentos de aplicações financeiras?', 'IRPF', 'IRPJ', 'IOF', 'IPI', 'C', 11),
('Qual é o prazo para recolher o imposto sobre os rendimentos de aplicações financeiras?', '15 dias', '30 dias', '45 dias', '60 dias', 'B', 11),
('Qual é a alíquota do imposto sobre os rendimentos de aplicações financeiras de renda fixa?', '15%', '17,5%', '20%', '22,5%', 'D', 11),
('Qual é a alíquota do imposto sobre os rendimentos de aplicações financeiras de renda variável?', '15%', '17,5%', '20%', '22,5%', 'A', 11),
('Qual é o imposto que incide sobre as operações de câmbio?', 'IRPF', 'IRPJ', 'IOF', 'IPI', 'C', 11),
('Qual é a alíquota do imposto sobre as operações de câmbio para compra ou venda de moeda estrangeira em espécie?', '0,38%', '1,1%', '6,38%', '15%', 'B', 11),
('Qual é a alíquota do imposto sobre as operações de câmbio para compra ou venda de moeda estrangeira com cartão de crédito ou débito?', '0,38%', '1,1%', '6,38%', '15%', 'C', 11),
('Qual é o imposto que incide sobre os ganhos de capital na alienação de bens ou direitos?', 'IRPF', 'IRPJ', 'IOF', 'IPI', 'A', 11),
('Qual é a alíquota do imposto sobre os ganhos de capital na alienação de bens ou direitos para pessoas físicas?', '15%', '17,5%', '20%', '22,5%', 'A', 11),
('O que é juros simples?', 'Rendimento', 'Desconto', 'Acréscimo', 'Parcela', 'C', 12),
('O que é juros compostos?', 'Juros sobre juros', 'Juros sobre capital', 'Juros sobre saldo', 'Juros sobre parcela', 'A', 12),
('Qual é a fórmula do montante em juros simples?', 'M = C * (1 + i * n)', 'M = C * (1 + i) ^ n', 'M = C + i * n', 'M = C + i ^ n', 'A', 12),
('Qual é a fórmula do montante em juros compostos?', 'M = C * (1 + i * n)', 'M = C * (1 + i) ^ n', 'M = C + i * n', 'M = C + i ^ n', 'B', 12),
('Qual é a taxa de juros simples de um empréstimo de R$ 1000 que gerou R$ 200 de juros em 6 meses?', '2%', '3%', '4%', '5%', 'C', 12),
('Qual é a taxa de juros compostos de um empréstimo de R$ 1000 que gerou R$ 200 de juros em 6 meses?', '1,82%', '2,01%', '2,21%', '2,41%', 'A', 12),
('Qual é o prazo de um investimento de R$ 1000 que rendeu R$ 200 de juros simples a uma taxa de 5% ao mês?', '3 meses', '4 meses', '5 meses', '6 meses', 'B', 12),
('Qual é o prazo de um investimento de R$ 1000 que rendeu R$ 200 de juros compostos a uma taxa de 2% ao mês?', '8 meses', '9 meses', '10 meses', '11 meses', 'B', 12),
('Qual é o valor futuro de um investimento de R$ 1000 aplicado a uma taxa de juros simples de 3% ao mês por um ano?', 'R$ 1360', 'R$ 1395', 'R$ 1430', 'R$ 1465', 'B', 12),
('Qual é o valor futuro de um investimento de R$ 1000 aplicado a uma taxa de juros compostos de 3% ao mês por um ano?', 'R$ 1425,76', 'R$ 1459,27', 'R$ 1493,80', 'R$ 1529,38', 'D', 12);









