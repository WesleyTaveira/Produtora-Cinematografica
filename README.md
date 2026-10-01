<div align="center">

# Produtora Cinematográfica

**Sistema desktop para gerenciamento de uma produtora de cinema: filmes, elenco, diretores, estúdios e contratos**

[![Java](https://img.shields.io/badge/Java-18-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-5382A1?logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![NetBeans](https://img.shields.io/badge/IDE-Apache%20NetBeans-1B6AC6?logo=apachenetbeanside&logoColor=white)](https://netbeans.apache.org)
[![Ant](https://img.shields.io/badge/Build-Apache%20Ant-A81C7D?logo=apacheant&logoColor=white)](https://ant.apache.org)
[![Status](https://img.shields.io/badge/Status-Acad%C3%AAmico-informational)](#)

</div>

---

## Visão Geral

A **Produtora Cinematográfica** é uma aplicação desktop escrita em **Java** com interface gráfica **Swing**, desenvolvida como projeto acadêmico de Programação Orientada a Objetos. O sistema simula o dia a dia de uma produtora de cinema: cadastro de funcionários (atores e diretores), criação de filmes com elenco, gêneros e estúdios, e contratação de empresas terceirizadas, tudo amarrado a um **controle de orçamento por filme**.

Os dados são persistidos localmente por **serialização de objetos Java** em um único arquivo binário, sem necessidade de banco de dados externo. O acesso é feito por uma tela de login com três perfis: **Administrador**, **Diretor** e **Ator**, cada um com sua própria área.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Rodando o Projeto](#rodando-o-projeto)
  - [Pelo NetBeans (recomendado)](#pelo-netbeans-recomendado)
  - [Pela linha de comando com Ant](#pela-linha-de-comando-com-ant)
  - [Pelo JAR distribuído](#pelo-jar-distribuído)
- [Guia de Uso](#guia-de-uso)
  - [Login e perfis de acesso](#login-e-perfis-de-acesso)
  - [Menu do Administrador](#menu-do-administrador)
  - [Funcionários](#funcionários)
  - [Estúdios](#estúdios)
  - [Empresas](#empresas)
  - [Filmes](#filmes)
  - [Personagens](#personagens)
  - [Contratos](#contratos)
  - [Gêneros e Estúdios do filme](#gêneros-e-estúdios-do-filme)
  - [Área do Ator e do Diretor](#área-do-ator-e-do-diretor)
- [Regras de Negócio](#regras-de-negócio)
- [Modelos de Dados](#modelos-de-dados)
- [Persistência](#persistência)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Limitações Conhecidas](#limitações-conhecidas)
- [Solução de Problemas](#solução-de-problemas)
- [Autor](#autor)

---

## Funcionalidades

- **Login com três perfis** — Administrador, Diretor e Ator, cada um direcionado para sua própria tela
- **Cadastro de funcionários** (Ator ou Diretor), com edição de dados e validação de CPF
- **CRUD completo de filmes** — criar, editar, procurar e excluir, com exclusão em cascata das relações
- **CRUD completo de estúdios** — nome, ID e localização
- **Cadastro de empresas** terceirizadas (nome + CNPJ), com edição e consulta
- **Gestão de elenco** — personagens vinculados a atores, com idade, sexo, descrição e cachê
- **Contratos de serviço** entre filme e empresa (filmagem, edição, sonoplastia, figurinos, maquiagem)
- **Controle de orçamento** — cachês e contratos são abatidos do orçamento restante do filme e devolvidos ao serem removidos
- **Cálculo de pagamentos** — total a receber por ator (soma dos cachês) e por diretor (percentual sobre o faturamento)
- **Gêneros pré-cadastrados** (drama, comédia, ação, terror) associáveis aos filmes
- **Persistência automática** em arquivo serializado, criado na primeira execução
- **Interface gráfica** construída no editor visual do NetBeans, com tema Nimbus

---

## Arquitetura

O projeto segue uma organização em camadas simples, típica de aplicações Swing acadêmicas:

```
┌─────────────────────────────────────────────────────────────────┐
│                      APRESENTAÇÃO (telas/)                      │
│   JFrames Swing · Formulários (.form) · Tabelas · Validações    │
├─────────────────────────────────────────────────────────────────┤
│                         MODELO (classes/)                       │
│   Entidades · Regras de orçamento · Cálculo de pagamentos       │
├─────────────────────────────────────────────────────────────────┤
│                   PERSISTÊNCIA (BancoDeDados)                   │
│   Agregado raiz serializado em BancoDados/bd.ser                │
└─────────────────────────────────────────────────────────────────┘
```

**Pontos de projeto:**

- **Herança e polimorfismo** — `Funcionario` é uma classe abstrata com o método abstrato `totalReceber()`, implementado de forma diferente por `Ator` e `Diretor`
- **Agregado raiz** — `BancoDeDados` concentra todas as coleções (funcionários, filmes, estúdios, empresas, contratos, gêneros) e é gravado/lido de uma só vez
- **Grafo de objetos** — as relações (ex.: `Personagem` → `Ator` e `Filme`) são referências diretas entre objetos, preservadas pela serialização
- **Máquina de estados nas telas** — o enum `Botao` (`NOVO`, `EDITAR`, `EXCLUIR`, `PROCURAR`, `NONE`) define o que o botão **OK** faz em cada formulário CRUD

---

## Tecnologias

| Categoria | Tecnologia | Versão |
|-----------|-----------|--------|
| Linguagem | Java (source/target) | 18 |
| Interface gráfica | Java Swing + Look and Feel Nimbus | JDK |
| IDE / editor de telas | Apache NetBeans (GUI Builder) | 13+ |
| Build | Apache Ant (`build.xml` gerado pelo NetBeans) | 1.10+ |
| Persistência | Serialização Java (`ObjectOutputStream`) | JDK |
| Dependências externas | Nenhuma | — |

---

## Pré-requisitos

- **JDK 18 ou superior** — [download](https://adoptium.net)
- **Apache NetBeans** *(recomendado)* — [download](https://netbeans.apache.org/download/)
- **Apache Ant** *(somente para build pela linha de comando)* — [download](https://ant.apache.org/bindownload.cgi)
- **Git** — [download](https://git-scm.com)

Verifique suas versões:

```bash
java -version   # openjdk version "18" ou superior
ant -version    # Apache Ant(TM) version 1.10.x (opcional)
```

---

## Rodando o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/WesleyTaveira/Produtora-Cinematografica.git
cd Produtora-Cinematografica
```

> **Importante:** o arquivo de dados é gravado no caminho relativo `BancoDados\bd.ser`. A aplicação deve ser executada **a partir da raiz do projeto**, onde a pasta `BancoDados/` existe. Veja [Persistência](#persistência).

### Pelo NetBeans (recomendado)

1. `File → Open Project...` e selecione a pasta do projeto
2. Confirme que a plataforma Java é JDK 18+ (`Properties → Libraries`)
3. Pressione **F6** (*Run Project*)

A classe principal configurada é `telas.TelaLogin`.

### Pela linha de comando com Ant

```bash
# Compila e gera dist/ProdutoraCinematografica.jar
ant clean jar

# Compila e executa
ant run
```

### Pelo JAR distribuído

Um JAR já compilado está disponível em `dist/`. Execute-o **da raiz do projeto**:

```bash
java -jar dist/ProdutoraCinematografica.jar
```

---

## Guia de Uso

### Login e perfis de acesso

A primeira tela pede **CPF** e **senha**. O destino depende de quem entra:

| Perfil | Credenciais | Tela de destino |
|--------|-------------|-----------------|
| Administrador | CPF `0` · senha `admin` | Menu principal (cadastros) |
| Diretor | CPF e senha cadastrados | Menu do Diretor |
| Ator | CPF e senha cadastrados | Menu do Ator |

> Na primeira execução não há funcionários cadastrados. Entre como **administrador** e cadastre atores e diretores antes de usar os demais perfis.

---

### Menu do Administrador

O menu principal dá acesso aos quatro cadastros do sistema:

```
Produtora Cinematográfica
├── Funcionários   → TelaCadastroFuncionario
├── Filmes         → CadastroFilme
├── Estúdios       → CadastroEstudio
└── Empresas       → CadastroEmpresa
```

---

### Funcionários

Tela: `TelaCadastroFuncionario`

Escolha o tipo (**Ator** ou **Diretor**) e preencha os campos.

| Campo | Ator | Diretor | Observação |
|-------|:---:|:---:|-----------|
| Nome, CPF, Senha | ✔ | ✔ | Senha usada no login |
| Telefone, E-mail | ✔ | ✔ | |
| Data de nascimento | ✔ | ✔ | Formato `dd/MM/yyyy` |
| Sexo, Nacionalidade | ✔ | ✔ | |
| Tempo de experiência | ✔ | | Inteiro (anos) |
| Biografia | ✔ | | |
| Percentual | | ✔ | % sobre o faturamento dos filmes dirigidos |

**Validações:**
- Todos os campos são obrigatórios
- O CPF deve conter **apenas dígitos**, não pode ser `0` (reservado ao administrador) e não pode estar repetido

**Edição:** clique em uma linha da tabela para carregar os dados e use **Editar**. CPF e data de nascimento não podem ser alterados.

---

### Estúdios

Tela: `CadastroEstudio` — CRUD completo.

| Campo | Descrição |
|-------|-----------|
| Nome | Nome do estúdio |
| ID do Estúdio | Identificador único |
| Localização | Cidade/endereço |

Ações: **Novo**, **Editar**, **Procurar** e **Excluir** (pelo ID).

---

### Empresas

Tela: `CadastroEmpresa`

Empresas são os prestadores de serviço contratados pelos filmes.

| Campo | Descrição |
|-------|-----------|
| Nome | Razão social |
| CNPJ | Identificador único |

Ações: **Novo**, **Editar** e **Procurar** (pelo CNPJ). A tabela mostra a quantidade de contratos de cada empresa. O botão **Filmes** leva direto para o cadastro de filmes.

---

### Filmes

Tela: `CadastroFilme` — o centro do sistema.

| Campo | Tipo | Observação |
|-------|------|-----------|
| Nome | texto | |
| Código | texto | Identificador único do filme |
| Ano | inteiro | |
| Idioma | texto | |
| Duração | inteiro | Em minutos |
| Classificação | lista | `Livre`, `+12`, `+16`, `+18` |
| Sinopse | texto | |
| Tempo de produção | inteiro | |
| Orçamento | decimal | Base para o controle de gastos |
| Faturamento | decimal | Base para o pagamento do diretor |
| CPF do Diretor | texto | Deve ser um diretor cadastrado |

A partir da tela de filme abrem-se as telas de relacionamento:

```
CadastroFilme
├── Personagens  → AdicionaPersonagem   (exige orçamento preenchido)
├── Contratos    → RealizaContrato      (exige orçamento preenchido)
├── Estúdios     → addEstudio           (exige código preenchido)
└── Gêneros      → TelaAddGenero        (exige código preenchido)
```

**Fluxo recomendado para criar um filme:**

1. Clique em **Novo** e preencha os dados básicos, incluindo **código** e **orçamento**
2. Adicione pelo menos **um personagem**, **um estúdio** e **um gênero**
3. Opcionalmente, registre **contratos** com empresas
4. Clique em **OK** para salvar

**Validações ao salvar:**
- Todos os campos preenchidos
- Código não pode existir em outro filme
- CPF do diretor deve pertencer a um diretor cadastrado
- O filme precisa ter **ao menos um personagem, um estúdio e um gênero**

**Exclusão em cascata:** ao excluir um filme, o sistema remove seus personagens dos atores, retira o filme da lista do diretor e dos estúdios, e apaga os contratos ligados a ele (inclusive da lista da empresa).

---

### Personagens

Tela: `AdicionaPersonagem`

| Campo | Observação |
|-------|-----------|
| Nome | Único dentro do filme |
| Idade | Inteiro |
| Sexo | `Masculino`, `Feminino`, `Outro` |
| Descrição | |
| Cachê | Decimal, abatido do orçamento do filme |
| CPF do Ator | Deve ser um ator cadastrado |

Ações: **Novo**, **Editar**, **Procurar** e **Excluir**. A tela lista os atores disponíveis (nome e CPF) para facilitar a escolha.

---

### Contratos

Tela: `RealizaContrato`

| Campo | Observação |
|-------|-----------|
| Código | Único dentro do filme |
| CNPJ | Deve ser de uma empresa cadastrada |
| Serviço | `Filmagem`, `Edição`, `Sonoplastia`, `Figurinos`, `Maquiagem` |
| Valor | Decimal, abatido do orçamento do filme |
| Data de início / fim | Formato `dd/MM/yyyy` |

O contrato é registrado tanto no filme quanto na empresa. Se o valor ultrapassar o orçamento restante, a operação é recusada com **"Orçamento insuficiente"**.

---

### Gêneros e Estúdios do filme

- **`TelaAddGenero`** — adiciona ou remove gêneros do filme a partir da lista global, informando o código do gênero
- **`addEstudio`** — adiciona ou remove estúdios do filme, informando o ID do estúdio; não permite duplicar o mesmo estúdio

Gêneros disponíveis de fábrica:

| Código | Gênero |
|:---:|--------|
| 1 | drama |
| 2 | comédia |
| 3 | ação |
| 4 | terror |

---

### Área do Ator e do Diretor

**Menu do Ator** (`VisualizarAtor`)
- **Dados** → `VisuDadosAtor`: dados pessoais, experiência e biografia
- **Filmes** → `VisuPersonagensAtor`: personagens interpretados, com descrição, cachê, idade e sexo

**Menu do Diretor** (`VisualizarDiretor`)
- **Dados** → `VizuDadosDiretor`: dados pessoais e percentual
- **Filmes** → `VisuFilmesDiretor`: filmes dirigidos, com classificação, idioma, ano e faturamento

---

## Regras de Negócio

### Controle de orçamento

Cada filme mantém um **orçamento total** e um **orçamento restante**:

```
restante = orçamento − Σ cachês dos personagens − Σ valores dos contratos
```

| Operação | Efeito no restante |
|----------|--------------------|
| Adicionar personagem / contrato | Subtrai o cachê / valor (recusa se não houver saldo) |
| Remover personagem / contrato | Devolve o cachê / valor |
| Alterar cachê / valor | Devolve o antigo e subtrai o novo (recusa se ficar negativo) |
| Alterar orçamento total | Ajusta o restante pela diferença |

### Pagamentos

`Funcionario.totalReceber()` é abstrato e cada subclasse calcula do seu jeito:

| Funcionário | Cálculo |
|-------------|---------|
| **Ator** | Soma dos cachês de todos os seus personagens |
| **Diretor** | Σ (faturamento do filme × percentual ÷ 100) para cada filme dirigido |

---

## Modelos de Dados

### Diagrama de Classes

```
                    ┌─────────────────────┐
                    │  «abstract»         │
                    │  Funcionario        │
                    ├─────────────────────┤
                    │ cpf · nome · senha  │
                    │ email · telefone    │
                    │ data_nascimento     │
                    │ sexo · nacionalidade│
                    ├─────────────────────┤
                    │ totalReceber()      │
                    └──────────┬──────────┘
                     ┌─────────┴─────────┐
              ┌──────┴───────┐    ┌──────┴───────┐
              │    Ator      │    │   Diretor    │
              ├──────────────┤    ├──────────────┤
              │ tempoExper.  │    │ percentual   │
              │ biografia    │    └──────┬───────┘
              └──────┬───────┘           │ 1
                     │ 1                 │
                     │ N                 │ N
              ┌──────┴───────┐    ┌──────┴────────────────┐      ┌──────────────┐
              │  Personagem  │ N  │        Filme          │ N  N │   Estudio    │
              ├──────────────┤────┤───────────────────────┤──────┤──────────────┤
              │ nome · idade │  1 │ idFilme · nome · ano  │      │ idestudio    │
              │ sexo · cache │    │ idioma · duracao      │      │ nome         │
              │ descricao    │    │ classificacao·sinopse │      │ localizacao  │
              └──────────────┘    │ orcamento · restante  │      └──────────────┘
                                  │ faturamento           │
                                  └──┬─────────────────┬──┘
                                     │ 1               │ N
                                     │ N               │ N
                              ┌──────┴───────┐  ┌──────┴───────┐
                              │   Contrato   │  │    Genero    │
                              ├──────────────┤  ├──────────────┤
                              │ idContrato   │  │ idgenero     │
                              │ servico      │  │ nomeGenero   │
                              │ valor        │  └──────────────┘
                              │ dataInicio   │
                              │ dataTermino  │
                              └──────┬───────┘
                                     │ N
                                     │ 1
                              ┌──────┴───────┐
                              │   Empresa    │
                              ├──────────────┤
                              │ cnpj · nome  │
                              └──────────────┘
```

**Relacionamentos:**
- `Diretor` → `Filme`: **um para muitos**
- `Filme` → `Personagem` ← `Ator`: um ator interpreta vários personagens; cada personagem pertence a um filme
- `Filme` ↔ `Estudio` e `Filme` ↔ `Genero`: **muitos para muitos**
- `Filme` → `Contrato` ← `Empresa`: o contrato liga um filme a uma empresa prestadora

---

## Persistência

Toda a base de dados vive em um único objeto `BancoDeDados`, serializado em:

```
BancoDados\bd.ser
```

- **Leitura:** cada tela, ao abrir, carrega o arquivo com `BancoDeDados.readBancoDeDados()`
- **Primeira execução:** se o arquivo não existe, um banco vazio é criado (com os 4 gêneros padrão) e gravado imediatamente
- **Gravação:** após cada operação confirmada (OK/Salvar/Editar), o banco inteiro é regravado com `BancoDeDados.writeBancoDeDados(bd)`
- **Reiniciar os dados:** feche a aplicação e apague `BancoDados/bd.ser`; ele será recriado vazio na próxima execução

> O arquivo `bd.ser` é binário e depende da estrutura das classes. Se você alterar campos de uma classe serializável, o arquivo antigo pode deixar de ser lido; nesse caso, apague-o.

---

## Estrutura do Projeto

```
Produtora-Cinematografica/
├── src/
│   ├── classes/                    # Modelo de domínio + persistência
│   │   ├── BancoDeDados.java       # Agregado raiz, leitura/gravação do bd.ser
│   │   ├── Funcionario.java        # Classe abstrata base (totalReceber)
│   │   ├── Ator.java               # Funcionário com personagens e cachês
│   │   ├── Diretor.java            # Funcionário com percentual sobre filmes
│   │   ├── Filme.java              # Entidade central + controle de orçamento
│   │   ├── Personagem.java         # Papel de um ator em um filme
│   │   ├── Contrato.java           # Serviço contratado entre filme e empresa
│   │   ├── Empresa.java            # Prestadora de serviço (CNPJ)
│   │   ├── Estudio.java            # Local de produção
│   │   └── Genero.java             # Gênero cinematográfico
│   │
│   ├── enums/
│   │   └── Botao.java              # Estado das telas CRUD (NOVO, EDITAR, ...)
│   │
│   ├── telas/                      # Interface Swing (.java + .form do NetBeans)
│   │   ├── TelaLogin               # Ponto de entrada (main class)
│   │   ├── Principal               # Menu do administrador
│   │   ├── TelaCadastroFuncionario # Cadastro/edição de atores e diretores
│   │   ├── CadastroFilme           # CRUD de filmes
│   │   ├── AdicionaPersonagem      # Elenco do filme
│   │   ├── RealizaContrato         # Contratos do filme
│   │   ├── TelaAddGenero           # Gêneros do filme
│   │   ├── addEstudio              # Estúdios do filme
│   │   ├── CadastroEstudio         # CRUD de estúdios
│   │   ├── CadastroEmpresa         # Cadastro de empresas
│   │   ├── VisualizarAtor          # Menu do ator
│   │   ├── VisuDadosAtor           # Dados do ator
│   │   ├── VisuPersonagensAtor     # Personagens do ator
│   │   ├── VisualizarDiretor       # Menu do diretor
│   │   ├── VizuDadosDiretor        # Dados do diretor
│   │   ├── VisuFilmesDiretor       # Filmes do diretor
│   │   └── IniciarPrograma.java    # Stub não utilizado
│   │
│   └── imagens/                    # Ícones e imagens da interface
│
├── BancoDados/                     # Pasta do arquivo de dados (bd.ser)
├── build/                          # Classes compiladas (gerado)
├── dist/                           # JAR distribuível (gerado)
├── nbproject/                      # Configuração do projeto NetBeans
├── test/                           # Pasta de testes (vazia)
├── build.xml                       # Script Ant
└── manifest.mf                     # Manifesto do JAR
```

> Os arquivos `.form` são gerados pelo **GUI Builder do NetBeans**. Para alterar o layout de uma tela, edite-a pelo editor visual — o código dentro dos blocos `// <editor-fold desc="Generated Code">` é regerado automaticamente e não deve ser editado à mão.

---

## Limitações Conhecidas

Por ser um projeto acadêmico, alguns pontos ficaram simplificados:

| Ponto | Situação atual |
|-------|----------------|
| Senhas | Armazenadas em texto puro dentro do `bd.ser` |
| Administrador | Credenciais fixas no código (`0` / `admin`) |
| Validação de CPF | Verifica apenas se são dígitos e se é único, sem dígitos verificadores |
| Validação de CNPJ | `validaFormatoCnpj()` sempre retorna `true` |
| Login inválido | Credenciais erradas não exibem mensagem de erro |
| Exclusões | Funcionários e empresas não podem ser excluídos pela interface |
| Caminho do banco | Fixo e relativo (`BancoDados\bd.ser`), com separador do Windows |
| Testes | Não há testes automatizados |

**Ideias de evolução:** hash de senhas, validação real de CPF/CNPJ, caminho do banco via `File.separator` ou configuração, migração para um banco SQL (ex.: SQLite/H2 com JDBC) e testes unitários com JUnit para as regras de orçamento e pagamento.

---

## Solução de Problemas

### Os dados não são salvos (console mostra `erro`)

A pasta `BancoDados/` não foi encontrada no diretório de execução. Execute a aplicação **a partir da raiz do projeto** ou crie a pasta `BancoDados` ao lado de onde você roda o `java -jar`.

---

### No Linux/macOS aparece um arquivo chamado `BancoDados\bd.ser` na raiz

O caminho usa `\`, que fora do Windows vira parte do nome do arquivo. A aplicação funciona, mas grava nesse arquivo com nome estranho. Para corrigir, troque em `BancoDeDados.java`:

```java
private static final String filePath = "BancoDados" + java.io.File.separator + "bd.ser";
```

---

### Erro ao abrir depois de alterar alguma classe (`InvalidClassException`)

O `bd.ser` foi gravado com uma versão anterior das classes. Apague `BancoDados/bd.ser` para recriar a base.

---

### Não consigo entrar como ator ou diretor

Verifique se o funcionário foi cadastrado pelo administrador e se CPF e senha estão corretos. O sistema não mostra aviso em caso de credenciais erradas — a tela simplesmente permanece aberta.

---

### "Diretor inexistente" ao salvar o filme

O CPF informado no campo do diretor precisa pertencer a um funcionário cadastrado como **Diretor** (não como Ator).

---

### "O filme deve ter pelo menos um personagem, estudio e genero"

Antes de clicar em **OK**, use os botões **Personagens**, **Estúdios** e **Gêneros** para adicionar ao menos um item de cada.

---

### Erro de versão (`UnsupportedClassVersionError`)

O projeto é compilado para Java 18. Instale um JDK 18 ou superior e confirme com `java -version`.

---

<div align="center">
  Desenvolvido com Java + Swing + NetBeans
</div>

## Autor

**Wesley Taveira** — [@WesleyTaveira](https://github.com/WesleyTaveira)
