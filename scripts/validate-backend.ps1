$ErrorActionPreference = 'Stop'

$baseUrl = 'http://localhost:8080'

Write-Host "Run this script from the repository root with: .\scripts\validate-backend.ps1"

Write-Host 'Checking health endpoint...'
$health = Invoke-RestMethod -Method Get -Uri "$baseUrl/health"
if ($health.status -ne 'UP') {
  throw "Health endpoint did not return UP. Response: $($health | ConvertTo-Json -Compress)"
}
Write-Host 'Health OK'

Write-Host 'Checking auth login...'
$loginBody = @{
  authorizationCode = 'demo-code'
  redirectUri = 'com.repcontrol://auth/callback'
} | ConvertTo-Json
$login = Invoke-RestMethod -Method Post -Uri "$baseUrl/api/v1/auth/login" -ContentType 'application/json' -Body $loginBody
if (-not $login.success) {
  throw "Login endpoint failed. Response: $($login | ConvertTo-Json -Compress)"
}
Write-Host 'Login OK'

Write-Host 'Checking auth refresh...'
$refreshBody = @{
  refreshToken = $login.data.refreshToken
} | ConvertTo-Json
$refresh = Invoke-RestMethod -Method Post -Uri "$baseUrl/api/v1/auth/refresh" -ContentType 'application/json' -Body $refreshBody
if (-not $refresh.success) {
  throw "Refresh endpoint failed. Response: $($refresh | ConvertTo-Json -Compress)"
}
Write-Host 'Refresh OK'

Write-Host 'Checking auth logout...'
$headers = @{
  Authorization = "Bearer $($login.data.token)"
}
$logout = Invoke-RestMethod -Method Post -Uri "$baseUrl/api/v1/auth/logout" -Headers $headers
if (-not $logout.success) {
  throw "Logout endpoint failed. Response: $($logout | ConvertTo-Json -Compress)"
}
Write-Host 'Logout OK'

Write-Host 'All validations passed.'
