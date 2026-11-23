# Script de démarrage de tous les services TicketFlow
# Usage: .\start-all-services.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Démarrage de TicketFlow" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Définir la variable d'environnement pour Config Server
$env:CONFIG_GIT_URI = "https://github.com/hmirinsalma/ticketflow-config.git"

$services = @(
    @{Name="Config Server"; Path="services\config-server"; Port=8888; Wait=30},
    @{Name="Discovery Server"; Path="services\discovery-server"; Port=8761; Wait=30},
    @{Name="Customer Service"; Path="services\customer-service"; Port=8081; Wait=20},
    @{Name="Event Service"; Path="services\event-service"; Port=8082; Wait=20},
    @{Name="Booking Service"; Path="services\booking-service"; Port=8083; Wait=20},
    @{Name="API Gateway"; Path="services\api-gateway"; Port=8080; Wait=15}
)

foreach ($service in $services) {
    Write-Host "🚀 Démarrage de $($service.Name) (Port: $($service.Port))..." -ForegroundColor Green
    
    # Démarrer le service dans une nouvelle fenêtre PowerShell
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\$($service.Path)'; Write-Host 'Démarrage de $($service.Name)...' -ForegroundColor Yellow; ..\..\mvnw.cmd spring-boot:run"
    
    Write-Host "⏳ Attente de $($service.Wait) secondes avant le prochain service..." -ForegroundColor Yellow
    Start-Sleep -Seconds $service.Wait
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Tous les services sont en cours de démarrage!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "📊 URLs importantes:" -ForegroundColor Cyan
Write-Host "  - Eureka Dashboard: http://localhost:8761" -ForegroundColor White
Write-Host "  - API Gateway: http://localhost:8080" -ForegroundColor White
Write-Host "  - Config Server: http://localhost:8888" -ForegroundColor White
Write-Host ""
Write-Host "💡 Conseil: Vérifiez que tous les services sont UP sur Eureka (http://localhost:8761)" -ForegroundColor Yellow
Write-Host ""
