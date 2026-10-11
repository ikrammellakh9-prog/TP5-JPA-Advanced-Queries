# TP 5 : RequÃªtes AvancÃ©es JPQL & Criteria API (Ikram Mellakh)

Techniques d'interrogation dynamique avec JPA 2.2 :
- Fonctions d'agrÃ©gation (`COUNT`, `AVG`, `MAX`) et groupements `GROUP BY`
- Pagination avec `setFirstResult` et `setMaxResults`
- Requetes prÃ©compilÃ©es `@NamedQuery`
- Construction dynamique et type-safe via `CriteriaBuilder` et `CriteriaQuery`

---

## Captures d'Ã‰cran IntelliJ IDEA & Terminal CMD

### 1. EntitÃ© SalleReunion & NamedQueries PrÃ©compilÃ©es (IntelliJ IDEA)
![Named Queries](docs/images/01-intellij-named-queries.png)

### 2. Service Requetes Dynamiques Criteria API (IntelliJ IDEA)
![Criteria API](docs/images/02-intellij-criteria-api.png)

### 3. RÃ©sultat des Tests et ExÃ©cution (CMD / PowerShell)
![RÃ©sultat ExÃ©cution CMD](docs/images/03-cmd-test-result.png)

---

## Lancement
```bash
mvn clean compile exec:java -Dexec.mainClass=ma.enset.ikram.tp5.App
```