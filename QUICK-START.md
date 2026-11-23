# Guide de Démarrage Rapide - TicketFlow

## 🚀 Démarrage Automatique (Recommandé)

### Option 1: Script PowerShell
```powershell
.\start-all-services.ps1
```

Ce script va :
- ✅ Démarrer tous les services dans le bon ordre
- ✅ Ouvrir une fenêtre PowerShell pour chaque service
- ✅ Attendre entre chaque service pour la synchronisation

### Option 2: Démarrage Manuel

**1. Config Server** (obligatoire en premier)
```powershell
cd services\config-server
$env:CONFIG_GIT_URI="https://github.com/hmirinsalma/ticketflow-config.git"
..\..\mvnw.cmd spring-boot:run
```
⏳ Attendre ~30 secondes

**2. Discovery Server**
```powershell
cd services\discovery-server
..\..\mvnw.cmd spring-boot:run
```
⏳ Attendre ~30 secondes

**3. Services Métier** (ouvrir 3 terminaux)
```powershell
# Terminal 1
cd services\customer-service
..\..\mvnw.cmd spring-boot:run

# Terminal 2
cd services\event-service
..\..\mvnw.cmd spring-boot:run

# Terminal 3
cd services\booking-service
..\..\mvnw.cmd spring-boot:run
```
⏳ Attendre ~20 secondes

**4. API Gateway**
```powershell
cd services\api-gateway
..\..\mvnw.cmd spring-boot:run
```

## 🛑 Arrêt des Services

```powershell
.\stop-all-services.ps1
```

## ✅ Vérification

### 1. Eureka Dashboard
Ouvrir: http://localhost:8761

Vous devriez voir tous ces services enregistrés:
- ✅ CUSTOMER-SERVICE
- ✅ EVENT-SERVICE
- ✅ BOOKING-SERVICE
- ✅ API-GATEWAY

### 2. Test Rapide

**Lister les événements via Gateway:**
```powershell
curl http://localhost:8080/api/v1/events
```

**Créer un client:**
```powershell
curl -X POST http://localhost:8080/api/customers `
  -H "Content-Type: application/json" `
  -d '{\"firstName\":\"Ahmed\",\"lastName\":\"Bennani\",\"email\":\"ahmed@example.com\",\"phone\":\"+212600000000\"}'
```

**Créer une réservation:**
```powershell
curl -X POST http://localhost:8080/api/v1/bookings `
  -H "Content-Type: application/json" `
  -d '{\"customerId\":1,\"eventId\":1,\"quantity\":2}'
```

## 📊 Ports des Services

| Service | Port | URL |
|---------|------|-----|
| Config Server | 8888 | http://localhost:8888 |
| Discovery Server | 8761 | http://localhost:8761 |
| Customer Service | 8081 | http://localhost:8081 |
| Event Service | 8082 | http://localhost:8082 |
| Booking Service | 8083 | http://localhost:8083 |
| API Gateway | 8080 | http://localhost:8080 |

## 🔧 Dépannage

### Problème: Port déjà utilisé
```powershell
# Trouver le processus utilisant le port (ex: 8080)
netstat -ano | findstr :8080

# Tuer le processus (remplacer PID)
taskkill /PID <PID> /F
```

### Problème: Service ne démarre pas
1. Vérifier que Config Server est démarré en premier
2. Vérifier que Discovery Server est démarré en second
3. Attendre 30 secondes entre chaque service
4. Consulter les logs dans la fenêtre PowerShell du service

### Problème: Service non enregistré dans Eureka
- Attendre 30-60 secondes (heartbeat)
- Vérifier les logs du service
- Redémarrer le service si nécessaire

## 💡 Conseils

- ⏰ **Patience**: Attendez bien entre chaque service (30 sec minimum)
- 🔄 **Ordre**: Respectez l'ordre de démarrage (Config → Discovery → Services → Gateway)
- 👀 **Eureka**: Vérifiez toujours http://localhost:8761 pour confirmer l'enregistrement
- 🪟 **Fenêtres**: Chaque service a sa propre fenêtre PowerShell pour voir les logs
