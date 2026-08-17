# Rastreador de Tarefas (CLI)

Aplicação de linha de comando em Java para criar e administrar tarefas. Cada tarefa possui um identificador, uma descrição, um status e datas de criação e de atualização.

Os dados são mantidos localmente no arquivo `tasks.json`, criado automaticamente no diretório de onde o programa é executado.

## Pré-requisitos

- Java Development Kit (JDK) 21 ou superior
- Apache Maven 3.9 ou superior

Confira as instalações:

```powershell
java -version
mvn -version
```

## Compilar e executar

Na raiz do projeto, gere o JAR:

```powershell
mvn package
```

Em seguida, execute um comando com:

```powershell
java -jar target/task-tracker-cli-1.0.jar <comando>
```

Por exemplo:

```powershell
java -jar target/task-tracker-cli-1.0.jar add "Estudar Java"
```

> Execute os comandos sempre na raiz do projeto se quiser que o arquivo de dados seja o `tasks.json` deste diretório.

## Comandos

| Comando | Descrição | Exemplo |
| --- | --- | --- |
| `add "descrição"` | Cria uma tarefa com status `todo`. | `java -jar target/task-tracker-cli-1.0.jar add "Comprar leite"` |
| `update id "descrição"` | Altera a descrição da tarefa. | `java -jar target/task-tracker-cli-1.0.jar update 1 "Comprar leite e pão"` |
| `delete id` | Remove uma tarefa. | `java -jar target/task-tracker-cli-1.0.jar delete 1` |
| `list` | Exibe todas as tarefas. | `java -jar target/task-tracker-cli-1.0.jar list` |
| `list todo` | Exibe apenas tarefas pendentes. | `java -jar target/task-tracker-cli-1.0.jar list todo` |
| `list in-progress` | Exibe apenas tarefas em andamento. | `java -jar target/task-tracker-cli-1.0.jar list in-progress` |
| `list done` | Exibe apenas tarefas concluídas. | `java -jar target/task-tracker-cli-1.0.jar list done` |
| `mark-in-progress id` | Marca uma tarefa como em andamento. | `java -jar target/task-tracker-cli-1.0.jar mark-in-progress 1` |
| `mark-done id` | Marca uma tarefa como concluída. | `java -jar target/task-tracker-cli-1.0.jar mark-done 1` |

Os textos com espaços devem ficar entre aspas. Os status aceitos nos filtros são exatamente `todo`, `in-progress` e `done`.

## Exemplo de fluxo

```powershell
# Criar uma tarefa
java -jar target/task-tracker-cli-1.0.jar add "Preparar apresentação"

# Consultar as tarefas e identificar o ID
java -jar target/task-tracker-cli-1.0.jar list

# Iniciar e concluir a tarefa de ID 1
java -jar target/task-tracker-cli-1.0.jar mark-in-progress 1
java -jar target/task-tracker-cli-1.0.jar mark-done 1

# Consultar somente as tarefas concluídas
java -jar target/task-tracker-cli-1.0.jar list done
```

A listagem segue o formato:

```text
1 | Preparar apresentação | in-progress
```

## Dados e status

O arquivo `tasks.json` guarda os campos `id`, `description`, `status`, `createdAt` e `updatedAt`. Uma tarefa nova começa com status `todo`; ao alterar a descrição ou o status, a data `updatedAt` é atualizada.

## Testes

Para executar os testes automatizados:

```powershell
mvn test
```
