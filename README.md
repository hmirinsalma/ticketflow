# TicketFlow

**Projet académique de microservices avec Spring Cloud - Une plateforme de réservation de billets d'événements**

Projet développé dans le cadre d'un cours d'architecture microservices, implémentant progressivement les patterns et technologies Spring Cloud.

---

## 📊 État d'avancement

| TP | Fonctionnalité | Statut | Technologies |
|---|---|---|---|
| **TP0** | Setup & Customer Service | ✅ Terminé | Spring Boot, JPA, H2 |
| **TP1** | API Gateway | ✅ Terminé | Spring Cloud Gateway |
| **TP2** | Service Discovery | ✅ Terminé | Eureka Server/Client |
| **TP3** | Configuration centralisée | ✅ Terminé | Spring Cloud Config + GitHub |
| **TP4** | Communication inter-services | ✅ Terminé | OpenFeign |
| **TP5** | Résilience | ✅ Terminé | Resilience4j Circuit Breaker |

---

## 🏗️ Architecture actuelle

```
                          ┌─────────────────┐
                          │   API Gateway   │
                          │   Port: 8080    │
                          └────────┬────────┘
                                   │
                    ┌──────────────┼──────────────┐
                    │              │              │
          ┌─────────▼────────┐ ┌──▼──────────┐ ┌▼─────────────┐
          │ Customer Service │ │Event Service│ │Booking Service│
          │   Port: 8081     │ │ Port: 8082  │ │  Port: 8083   │
          └──────────────────┘ └─────────────┘ └───────┬───────┘
                                                        │
                                                   OpenFeign
                                                  Circuit Breaker
                                                        
          ┌────────────────────────────────────────────────────┐
          │            Discovery Server (Eureka)               │
          │                 Port: 8761                         │
          └────────────────────────────────────────────────────┘
          
          ┌────────────────────────────────────────────────────┐
          │          Config Server (GitHub backend)            │
          │                 Port: 8888                         │
          └────────────────────────────────────────────────────┘
```

---

## 📦 Services implémentés

### 1. **config-server** (Port 8888)
Configuration centralisée avec backend GitHub.
- Repository externe: [`ticketflow-config`](https://github.com/hmirinsalma/ticketflow-config)
- Refresh dynamique avec `/actuator/refresh`
- Profiles par environnement

### 2. **discovery-server** (Port 8761)
Service Discovery avec Eureka Server.
- Interface web: http://localhost:8761
- Auto-registration des services
- Health checks automatiques

### 3. **customer-service** (Port 8081)
Gestion des clients (CRUD).
- API REST complète
- Validation des données
- Gestion d'erreurs personnalisée

### 4. **event-service** (Port 8082)
Catalogue d'événements.
- Gestion des événements publiés
- DataLoader avec données de test
- API REST avec filtres

### 5. **booking-service** (Port 8083)
Réservations avec appel à event-service.
- Communication via OpenFeign
- Circuit Breaker (Resilience4j)
- Calcul automatique du prix total
- Fallback en cas de panne d'event-service

### 6. **api-gateway** (Port 8080)
Point d'entrée unique avec routage intelligent.
- Load Balancing via Eureka
- Routes configurées pour tous les services
- Header X-Served-By pour traçabilité

---

## 🚀 Démarrage du projet

### Prérequis
- Java 21
- Maven 3.9+
- Git

### Variables d'environnement
```bash
CONFIG_GIT_URI=https://github.com/hmirinsalma/ticketflow-config.git
```

### Ordre de démarrage

**1. Config Server** (obligatoire en premier)
```bash
cd services/config-server
$env:CONFIG_GIT_URI="https://github.com/hmirinsalma/ticketflow-config.git"
../../mvnw.cmd spring-boot:run
```

**2. Discovery Server**
```bash
cd services/discovery-server
../../mvnw.cmd spring-boot:run
```

**3. Services métier** (dans n'importe quel ordre)
```bash
# Customer Service
cd services/customer-service
../../mvnw.cmd spring-boot:run

# Event Service
cd services/event-service
../../mvnw.cmd spring-boot:run

# Booking Service
cd services/booking-service
../../mvnw.cmd spring-boot:run
```

**4. API Gateway**
```bash
cd services/api-gateway
../../mvnw.cmd spring-boot:run
```

### Vérification

- Eureka Dashboard: http://localhost:8761
- Tous les services doivent être enregistrés et UP

---

## 🧪 Tests fonctionnels

### Via l'API Gateway (recommandé)

**1. Lister les événements**
```bash
curl http://localhost:8080/api/v1/events
```

**2. Créer un customer**
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ahmed","lastName":"Bennani","email":"ahmed@example.com","phone":"+212600000000"}'
```

**3. Créer une réservation**
```bash
curl -X POST http://localhost:8080/api/v1/bookings \
  -H "Content-Type: application/json" \
  -d '{"customerId":1,"eventId":1,"quantity":2}'
```

**4. Tester le Circuit Breaker**
- Arrêter event-service
- Essayer de créer une réservation → Erreur élégante
- Après 5+ échecs → Circuit ouvert (réponse immédiate)
- Redémarrer event-service
- Après 10 secondes → Circuit refermé

---

## 🛠️ Technologies utilisées

| Catégorie | Technologie | Version |
|---|---|---|
| **Framework** | Spring Boot | 4.0.8 |
| **Cloud** | Spring Cloud | 2025.1.2 |
| **Java** | OpenJDK | 21 |
| **Build** | Maven | 3.9+ |
| **Discovery** | Eureka | 2025.1.2 |
| **Gateway** | Spring Cloud Gateway | 2025.1.2 |
| **Config** | Spring Cloud Config | 2025.1.2 |
| **REST Client** | OpenFeign | 2025.1.2 |
| **Resilience** | Resilience4j | via Spring Cloud |
| **Database** | H2 (in-memory) | - |

---

## 📁 Structure du projet

```
ticketflow/
├── services/
│   ├── config-server/          # TP3: Configuration centralisée
│   ├── discovery-server/       # TP2: Eureka Server
│   ├── customer-service/       # TP0: Service CRUD
│   ├── event-service/          # TP4: Catalogue événements
│   ├── booking-service/        # TP4 + TP5: Réservations + Circuit Breaker
│   └── api-gateway/            # TP1: Point d'entrée
├── infra/
│   └── docker-compose.yml      # (À venir)
├── pom.xml                     # Parent POM
└── README.md                   # Ce fichier
```

---

## 🔗 Repositories liés

- **Code source**: [ticketflow](https://github.com/hmirinsalma/ticketflow)
- **Configuration**: [ticketflow-config](https://github.com/hmirinsalma/ticketflow-config)

---

## 👥 Équipe

- **Salma Hmirin** - [GitHub](https://github.com/hmirinsalma)
- Membre 2
- Membre 3

---

## 📝 Workflow Git

Le projet suit un workflow avec branches feature:

```bash
# Pour chaque TP
git checkout -b feature/step-XX-nom
# ... développement ...
git commit -m "TPX: description"
git push origin feature/step-XX-nom
# Pull Request → Review → Merge dans main
```

**Pull Requests mergées:**
- PR #1: TP2 - Service Discovery (Eureka)
- PR #2: TP3 - Config Server
- PR #3: TP4 - OpenFeign
- PR #4: TP5 - Circuit Breaker

---

## 📚 Concepts démontrés

- ✅ Architecture microservices
- ✅ Service Discovery (pattern Registry)
- ✅ API Gateway (pattern Gateway)
- ✅ Configuration externalisée
- ✅ Load Balancing côté client
- ✅ Communication synchrone (REST)
- ✅ Resilience (Circuit Breaker, Fallback)
- ✅ Observabilité (Actuator)

---

## 🎓 Projet académique

Ce projet a été réalisé dans le cadre d'un cours d'architecture microservices.

**Objectif pédagogique**: Comprendre et implémenter les patterns essentiels des microservices avec Spring Cloud.

---

## 📄 License

Projet académique - Tous droits réservés