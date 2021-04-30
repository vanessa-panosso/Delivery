# Delivery

 - Java 11
 - Usando banco H2 - http://www.h2database.com/html/download.html
 - Gradle versão 5
 - Para subir a aplicação executar `gradle run` 

# API
 - A API para cadastro rota Post /route
  - Payload: 
    {
        "originName": "D",
        "destination": "E",
        "distance": 50
    }
 - A API para conulta da melhor rota Post /find-route
  - Payload: 
        {
            "originName":"A",
            "destinationName":"D",
            "kmPerLiter":"10",
            "gasLiterPrice":"2.50"
        }
  - Retorno:
    {
        "routes": "A, B",
        "cost": 6.25
    }