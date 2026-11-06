# Catálogo de Cursos

**Autor: Vinicius Portela Nunes de Oliveira**

Atividade prática das aulas 00 a 14. Projeto independente gerado pelo Spring Initializr: Java 21, Spring Boot 4.0.7, Maven Wrapper, PostgreSQL e Liquibase. Não reutiliza classes, pacotes, banco ou histórico Git do petshop.

## Executar
1. Instale Java 21 e PostgreSQL.
2. Crie um banco exclusivo `catalogocursos_dev` (e `catalogocursos_test` para testes), com usuário que tenha permissão para criar tabelas.
3. Copie `.env.example` para `.env` e configure DB_URL, DB_USERNAME e DB_PASSWORD. Não publique esse arquivo.
4. Execute `.\mvnw.cmd spring-boot:run`. Porta padrão 8080; se estiver ocupada, use `-Dspring-boot.run.arguments=--server.port=8081`.
5. Importe `postman/CatalogoCursos.postman_collection.json`, ajuste baseUrl se necessário e execute em ordem.

## Testar
Configure DB_URL para um banco exclusivo de testes, DB_USERNAME e DB_PASSWORD, então execute:
```powershell
.\mvnw.cmd clean test
```
Os testes usam PostgreSQL real e transações com rollback. Nunca aponte testes para banco de produção. Resultado validado em 05/11/2026: **8 testes, 0 falhas, 0 erros, BUILD SUCCESS**, no banco catalogocursos_test.

## Modelo
Instrutor possui coleção inicializada, LAZY e sem setter público. Curso é o proprietário da relação por conter instrutor_id. adicionarCurso rejeita nulo, mantém ambos os lados e evita repetição da mesma instância. A troca implícita de instrutor é rejeitada. Não há CascadeType.ALL: o serviço persiste o curso explicitamente.

Liquibase cria instrutor antes de curso, impõe NOT NULL, unique de email e FK, com rollback em ordem reversa. Hibernate usa somente validate. Migrações estão em src/main/resources/db/changelog. Não aplique rollback em banco com dados que deseja preservar.

## API
| Método | Rota | Sucesso |
|---|---|---|
| POST | /api/instrutores | 201 + Location |
| GET | /api/instrutores/{id} | 200 |
| POST | /api/cursos | 201 + Location |
| GET | /api/cursos/{id} | 200 |
| GET | /api/instrutores/{id}/cursos | 200, ordenado por título |

DTOs interrompem o ciclo JSON: a resposta do curso contém apenas id e nome do instrutor; a listagem contém resumos dos cursos. Escritas são transacionais e consultas readOnly. Erros centralizados: 400 validação, 404 inexistente, 409 email repetido.

## Entrega
- Repositório: https://github.com/viniciusferpa70-star/atividade-catalogo-cursos
- Hash final: execute `git rev-parse HEAD` após clonar, ou veja o commit mais recente no GitHub.
- Banco validado: catalogocursos_test (banco de aplicação sugerido: catalogocursos_dev).
- Testes: 8 executados, sem falhas ou erros.
- **Qual entidade é proprietária?** Curso.
- **Em qual tabela está a FK?** Na tabela curso, coluna instrutor_id, referenciando instrutor.id.
- **Por que não retornar entidades?** Para evitar recursão da associação bidirecional, carregamento lazy indevido e acoplamento do contrato HTTP ao modelo persistido.

JWT, frontend e Docker não fazem parte desta entrega.
