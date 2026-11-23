# 📍 URLs Disponibles - TicketFlow

## ⚠️ Important
**N'accédez PAS directement à `http://localhost:8080/` car il n'y a pas de page d'accueil.**  
L'API Gateway route uniquement vers les endpoints API des services.

---

## 🌐 Interfaces Web

### 1. Eureka Dashboard (Recommandé pour voir l'état)
```
http://localhost:8761
```
**Description**: Interface web complète montrant tous les services enregistrés et leur état

---

## 🚪 API Gateway (Port 8080)

**Base URL**: `http://localhost:8080`

### 📋 Customer Service (Clients)

#### Lister tous les clients
```
GET http://localhost:8080/api/customers
```

#### Obtenir un client par ID
```
GET http://localhost:8080/api/customers/1
```

#### Créer un client
```
POST http://localhost:8080/api/customers
Content-Type: application/json

{
  "firstName": "Mohamed",
  "lastName": "Alami",
  "email": "mohamed@example.com",
  "phone": "+212600111222"
}
```

#### Mettre à jour un client
```
PUT http://localhost:8080/api/customers/1
Content-Type: application/json

{
  "firstName": "Mohamed",
  "lastName": "Alami",
  "email": "mohamed.updated@example.com",
  "phone": "+212600111222"
}
```

#### Supprimer un client
```
DELETE http://localhost:8080/api/customers/1
```

---

### 🎭 Event Service (Événements)

#### Lister tous les événements
```
GET http://localhost:8080/api/v1/events
```

#### Obtenir un événement par ID
```
GET http://localhost:8080/api/v1/events/1
```

#### Lister les événements publiés uniquement
```
GET http://localhost:8080/api/v1/events/published
```

---

### 🎟️ Booking Service (Réservations)

#### Lister toutes les réservations
```
GET http://localhost:8080/api/v1/bookings
```

#### Obtenir une réservation par ID
```
GET http://localhost:8080/api/v1/bookings/1
```

#### Créer une réservation
```
POST http://localhost:8080/api/v1/bookings
Content-Type: application/json

{
  "customerId": 1,
  "eventId": 1,
  "quantity": 2
}
```

---

## 🧪 Exemples PowerShell

### Lister les événements
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/events
```

### Lister les clients
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/customers
```

### Créer un client
```powershell
$client = @{
    firstName = "Fatima"
    lastName = "Zahra"
    email = "fatima@example.com"
    phone = "+212600333444"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/customers `
    -Method POST `
    -Body $client `
    -ContentType "application/json"
```

### Créer une réservation
```powershell
$booking = @{
    customerId = 1
    eventId = 2
    quantity = 3
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings `
    -Method POST `
    -Body $booking `
    -ContentType "application/json"
```

### Obtenir les détails d'une réservation
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings/1
```

---

## 🔗 Accès Direct aux Services (Sans passer par Gateway)

⚠️ **Non recommandé en production, mais possible pour le développement**

### Customer Service
```
http://localhost:8081/api/customers
```

### Event Service
```
http://localhost:8082/api/v1/events
```

### Booking Service
```
http://localhost:8083/api/v1/bookings
```

### Config Server (Voir la configuration d'un service)
```
http://localhost:8888/customer-service/default
http://localhost:8888/event-service/default
http://localhost:8888/booking-service/default
http://localhost:8888/api-gateway/default
```

---

## 📊 Actuator Endpoints (Monitoring)

### Via API Gateway
```
http://localhost:8080/actuator
http://localhost:8080/actuator/health
http://localhost:8080/actuator/gateway/routes
```

### Services Individuels
```
http://localhost:8081/actuator/health  (Customer)
http://localhost:8082/actuator/health  (Event)
http://localhost:8083/actuator/health  (Booking)
http://localhost:8888/actuator/health  (Config)
http://localhost:8761/actuator/health  (Eureka)
```

---

## 🧪 Scénario de Test Complet

### 1. Vérifier que tout fonctionne
```powershell
# Vérifier Eureka
Invoke-RestMethod -Uri http://localhost:8761/actuator/health

# Vérifier les événements disponibles
Invoke-RestMethod -Uri http://localhost:8080/api/v1/events
```

### 2. Créer un client
```powershell
$client = @{
    firstName = "Youssef"
    lastName = "Idrissi"
    email = "youssef@example.com"
    phone = "+212600555666"
} | ConvertTo-Json

$newClient = Invoke-RestMethod -Uri http://localhost:8080/api/customers `
    -Method POST `
    -Body $client `
    -ContentType "application/json"

# Noter l'ID du client créé
$clientId = $newClient.id
Write-Host "Client créé avec ID: $clientId"
```

### 3. Créer une réservation pour ce client
```powershell
$booking = @{
    customerId = $clientId
    eventId = 1
    quantity = 2
} | ConvertTo-Json

$newBooking = Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings `
    -Method POST `
    -Body $booking `
    -ContentType "application/json"

Write-Host "Réservation créée:"
Write-Host "  ID: $($newBooking.id)"
Write-Host "  Prix total: $($newBooking.totalPrice) DH"
Write-Host "  Statut: $($newBooking.status)"
```

### 4. Vérifier les réservations
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings
```

---

## ❌ URLs qui NE FONCTIONNENT PAS

Ces URLs retournent une erreur 404:
- ❌ `http://localhost:8080/` (pas de page d'accueil)
- ❌ `http://localhost:8080/home`
- ❌ `http://localhost:8080/index`
- ❌ Toute URL non définie dans les routes

**Solution**: Utilisez les endpoints API listés ci-dessus ou allez sur Eureka (http://localhost:8761)

---

## 🔍 Comment Trouver les Routes Disponibles

### Via l'actuator de l'API Gateway
```powershell
Invoke-RestMethod -Uri http://localhost:8080/actuator/gateway/routes
```

Cela affiche toutes les routes configurées dans le Gateway.

---

## 💡 Conseils

1. **Toujours utiliser Eureka** (http://localhost:8761) pour vérifier l'état des services
2. **Passer par l'API Gateway** (port 8080) pour toutes les requêtes API
3. **Utiliser les bons préfixes**:
   - `/api/customers` pour Customer Service
   - `/api/v1/events` pour Event Service
   - `/api/v1/bookings` pour Booking Service
4. **Vérifier les logs** dans les fenêtres PowerShell en cas d'erreur

---

## 📞 Support

Si une URL ne fonctionne pas:
1. Vérifiez que le service est UP sur Eureka: http://localhost:8761
2. Consultez les logs du service dans sa fenêtre PowerShell
3. Testez l'endpoint directement sur le service (ports 8081, 8082, 8083)
4. Vérifiez la configuration dans le Config Server
