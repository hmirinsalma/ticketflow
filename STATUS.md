# ✅ TicketFlow - Projet Démarré avec Succès !

**Date**: 11 novembre 2026  
**Statut**: 🟢 TOUS LES SERVICES SONT OPÉRATIONNELS

---

## 📊 État des Services

| Service | Port | Statut | PID Terminal |
|---------|------|--------|--------------|
| ✅ Config Server | 8888 | 🟢 UP | Terminal 3 |
| ✅ Discovery Server (Eureka) | 8761 | 🟢 UP | Terminal 4 |
| ✅ Customer Service | 8081 | 🟢 UP | Terminal 5 |
| ✅ Event Service | 8082 | 🟢 UP | Terminal 6 |
| ✅ Booking Service | 8083 | 🟢 UP | Terminal 7 |
| ✅ API Gateway | 8080 | 🟢 UP | Terminal 8 |

---

## 🌐 URLs Importantes

### Interfaces Web
- **Eureka Dashboard**: http://localhost:8761  
  _Voir tous les services enregistrés_

### API Gateway (Point d'entrée unique)
- **Base URL**: http://localhost:8080

### Services Directs (optionnel, passer par Gateway)
- Config Server: http://localhost:8888
- Customer Service: http://localhost:8081
- Event Service: http://localhost:8082
- Booking Service: http://localhost:8083

---

## 🧪 Tests Effectués

### ✅ 1. Liste des événements
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/events
```
**Résultat**: 2 événements retournés
- Concert Jazz à Casablanca (150 DH)
- Match de gala à Rabat (80 DH)

### ✅ 2. Création d'un client
```powershell
$body = @{
    firstName = "Ahmed"
    lastName = "Bennani"
    email = "ahmed@example.com"
    phone = "+212600000000"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/customers `
    -Method POST -Body $body -ContentType "application/json"
```
**Résultat**: Client créé avec ID 1

### ✅ 3. Création d'une réservation
```powershell
$body = @{
    customerId = 1
    eventId = 1
    quantity = 2
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings `
    -Method POST -Body $body -ContentType "application/json"
```
**Résultat**: Réservation créée
- ID: 1
- Prix total: 300 DH (2 × 150 DH)
- Statut: PENDING_PAYMENT

---

## 🛠️ Gestion des Services

### Démarrer tous les services
```powershell
.\start-all-services.ps1
```

### Arrêter tous les services
```powershell
.\stop-all-services.ps1
```

### Vérifier les processus en cours
```powershell
Get-Process -Name "java"
```

### Voir les logs d'un service
Les logs s'affichent dans les fenêtres PowerShell de chaque service.

---

## 📝 Exemples d'API Supplémentaires

### Lister tous les clients
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/customers
```

### Obtenir un client par ID
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/customers/1
```

### Lister toutes les réservations
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings
```

### Obtenir une réservation par ID
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/bookings/1
```

### Obtenir un événement par ID
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/v1/events/1
```

---

## 🔧 Dépannage

### Si un service ne répond pas
1. Vérifiez Eureka: http://localhost:8761
2. Consultez les logs dans la fenêtre PowerShell du service
3. Redémarrez le service si nécessaire

### Si un port est déjà utilisé
```powershell
# Trouver le processus (exemple pour port 8080)
netstat -ano | findstr :8080

# Tuer le processus (remplacer <PID>)
taskkill /PID <PID> /F
```

### Redémarrer un service spécifique
1. Fermer la fenêtre PowerShell du service
2. Ouvrir une nouvelle fenêtre PowerShell
3. Naviguer vers le dossier du service
4. Lancer: `..\..\mvnw.cmd spring-boot:run`

---

## 🎯 Prochaines Étapes Possibles

1. **Explorer l'interface Eureka** pour voir les services enregistrés
2. **Tester le Circuit Breaker**: Arrêter event-service et essayer de créer une réservation
3. **Modifier la configuration** dans le repo GitHub ticketflow-config
4. **Ajouter des données**: Créer plus de clients et réservations
5. **Consulter les métriques**: Endpoints `/actuator` sur chaque service

---

## 📚 Documentation

- README principal: `README.md`
- Guide rapide: `QUICK-START.md`
- Repository de configuration: https://github.com/hmirinsalma/ticketflow-config

---

## 💡 Notes Importantes

- ⚠️ **Ordre de démarrage important**: Config Server → Discovery → Services → Gateway
- ⏰ **Attendre 30 secondes** entre chaque service pour l'enregistrement Eureka
- 🔄 **Heartbeat Eureka**: Les services envoient un heartbeat toutes les 30 secondes
- 💾 **Base de données H2**: En mémoire, les données sont perdues au redémarrage
- 🌐 **Configuration externalisée**: Toute la config est dans le repo GitHub

---

**Projet développé dans le cadre d'un cours d'architecture microservices** 🎓
