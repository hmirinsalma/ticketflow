# 🔄 Mise à Jour Importante - Migration PostgreSQL

## ⚠️ CHANGEMENT MAJEUR

Votre collègue a migré le projet de **H2** (base en mémoire) vers **PostgreSQL** (base persistante).

**Les services ne démarreront plus sans PostgreSQL !**

---

## 🚀 Nouveau Processus de Démarrage

### Étape 1 : Démarrer PostgreSQL avec Docker

**Prérequis** : Docker Desktop doit être installé et démarré

```powershell
# Naviguer vers le dossier infra
cd infra

# Démarrer toutes les bases de données PostgreSQL
docker-compose up -d

# Vérifier que tout est démarré
docker-compose ps
```

**Résultat attendu** : 7 conteneurs PostgreSQL + PgAdmin démarrés

---

### Étape 2 : Arrêter les services Spring Boot actuels

```powershell
# Retour au dossier racine
cd ..

# Arrêter tous les services Java
.\stop-all-services.ps1
```

---

### Étape 3 : Redémarrer les services Spring Boot

```powershell
# Démarrer tous les services avec PostgreSQL
.\start-all-services.ps1
```

⏰ **Attention** : Le premier démarrage sera plus long car PostgreSQL doit créer les schémas

---

## 🗄️ Bases de Données Configurées

| Service | Port | Base | Utilisateur | Mot de passe |
|---------|------|------|-------------|--------------|
| Customer Service | 5432 | customer_db | ticketflow | ticketflow123 |
| Event Service | 5433 | event_db | ticketflow | ticketflow123 |
| Booking Service | 5434 | booking_db | ticketflow | ticketflow123 |
| Payment Service (futur) | 5435 | payment_db | ticketflow | ticketflow123 |
| Ticket Service (futur) | 5436 | ticket_db | ticketflow | ticketflow123 |
| Notification Service (futur) | 5437 | notification_db | ticketflow | ticketflow123 |

---

## 🌐 PgAdmin - Interface Web

**URL** : http://localhost:5050

**Connexion** :
- Email : `admin@ticketflow.ma`
- Mot de passe : `admin123`

### Ajouter une connexion à une base :
1. Clic droit sur "Servers" → Create → Server
2. Onglet "General" :
   - Name : `Customer DB` (ou autre nom)
3. Onglet "Connection" :
   - Host : `postgres-customer` (nom du conteneur Docker)
   - Port : `5432`
   - Database : `customer_db`
   - Username : `ticketflow`
   - Password : `ticketflow123`
4. Save

**Répéter pour chaque service** (postgres-event, postgres-booking, etc.)

---

## 🧪 Vérification

### 1. Vérifier que PostgreSQL est démarré
```powershell
docker ps
```
Vous devriez voir 7 conteneurs PostgreSQL + PgAdmin

### 2. Vérifier qu'un service Spring Boot se connecte
```powershell
# Les logs doivent montrer :
# "HikariPool-1 - Starting..."
# "Successfully acquired change log lock"
```

### 3. Tester l'API
```powershell
# Créer un client
$client = @{
    firstName = "Test"
    lastName = "PostgreSQL"
    email = "test@postgres.com"
    phone = "+212600000000"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/customers `
    -Method POST -Body $client -ContentType "application/json"

# Arrêter et redémarrer le service
# Les données PERSISTENT maintenant !
```

---

## 🛑 Arrêter Tout

### Arrêter les services Spring Boot
```powershell
.\stop-all-services.ps1
```

### Arrêter PostgreSQL
```powershell
cd infra
docker-compose down
```

### Arrêter ET supprimer les données
```powershell
cd infra
docker-compose down -v
```
⚠️ **Attention** : `-v` supprime tous les volumes (données perdues)

---

## 🔧 Commandes Docker Utiles

### Voir les logs d'une base
```powershell
docker logs ticketflow-postgres-customer
docker logs ticketflow-postgres-event
docker logs ticketflow-postgres-booking
```

### Redémarrer une base spécifique
```powershell
docker restart ticketflow-postgres-customer
```

### Accéder au shell PostgreSQL
```powershell
docker exec -it ticketflow-postgres-customer psql -U ticketflow -d customer_db
```

Commandes SQL utiles :
```sql
-- Lister les tables
\dt

-- Voir les données
SELECT * FROM customer;

-- Quitter
\q
```

---

## 📊 Avantages de PostgreSQL

✅ **Persistance** : Les données survivent au redémarrage  
✅ **Production-ready** : Base utilisée en production  
✅ **Performance** : Meilleure que H2 pour les gros volumes  
✅ **Outils** : PgAdmin, DBeaver, etc.  
✅ **Réaliste** : Proche de l'environnement de production  

---

## ❌ Problèmes Courants

### Erreur : "Connection refused" ou "Connection to localhost:5432 refused"
**Solution** : PostgreSQL n'est pas démarré
```powershell
cd infra
docker-compose up -d
```

### Erreur : "FATAL: password authentication failed"
**Solution** : Mauvais mot de passe dans application.properties
- Vérifier : `ticketflow` / `ticketflow123`

### Erreur : "Docker daemon is not running"
**Solution** : Démarrer Docker Desktop

### Erreur : "Port 5432 already allocated"
**Solution** : Un autre PostgreSQL utilise déjà le port
```powershell
# Trouver le processus
netstat -ano | findstr :5432

# Arrêter Docker Compose
cd infra
docker-compose down
```

---

## 🎯 Workflow Recommandé

1. **Démarrer Docker Desktop** (une fois au début de la journée)
2. **Démarrer PostgreSQL** : `cd infra && docker-compose up -d`
3. **Démarrer les services** : `.\start-all-services.ps1`
4. **Travailler normalement**
5. **Fin de journée** :
   - Arrêter les services : `.\stop-all-services.ps1`
   - (Optionnel) Arrêter PostgreSQL : `cd infra && docker-compose down`

💡 **Astuce** : Vous pouvez laisser PostgreSQL tourner en arrière-plan

---

## 📚 Documentation Docker Compose

Pour plus d'infos sur Docker Compose :
- https://docs.docker.com/compose/
- https://docs.docker.com/compose/reference/

---

**Projet mis à jour par votre collègue - Migration vers PostgreSQL réussie !** 🎉
