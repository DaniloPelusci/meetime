
# Integração com HubSpot - API de Contatos

Este projeto foi desenvolvido com base em um template que já possuo, preparado para ocasiões como esta. Ele já vem com Swagger integrado para facilitar a navegação, testes e documentação dos endpoints da API.

---

## Pré-requisitos

Além do cadastro no HubSpot, também é necessário se cadastrar no [Ngrok](https://ngrok.com/) para expor seu ambiente local e permitir que o HubSpot envie webhooks para sua aplicação. O Ngrok cria um túnel HTTP seguro para seu localhost, útil especialmente na etapa de testes com Webhook.

### Instalação e configuração do Ngrok
1. Acesse [https://ngrok.com/download](https://ngrok.com/download) e baixe a versão para seu sistema operacional.
2. Extraia o arquivo em uma pasta de fácil acesso.
3. Acesse a pasta extraída via terminal ou prompt de comando.
4. Autentique seu Ngrok com seu token pessoal (disponível no painel da conta Ngrok):

   ```bash
   ngrok config add-authtoken SEU_TOKEN_AQUI
   ```

5. Para rodar sua aplicação local na porta 8080 e expor para a internet, execute:

   ```bash
   ngrok http 8080
   ```

6. Copie a URL gerada (ex: `https://abc123.ngrok.io`) e use ela ao registrar o webhook no HubSpot.

Talvez seja necessário alterar o arquivo de configuração do Ngrok (`ngrok.yml` ou `ngrok.txt`) para que ele funcione corretamente. 
Um exemplo de configuração mínima seria:

```yaml
version: 2
authtoken: SEU_TOKEN_DO_NGROK
tunnels:
  oauth:
    proto: http
    addr: 8080
```

Certifique-se de salvar esse arquivo na pasta do Ngrok (ou no caminho padrão do sistema) antes de iniciar o túnel.

---

## Etapa 1: Configurar o App no HubSpot

1. Acesse o [painel de apps](https://developers.hubspot.com/apps)
2. Entre no app que você criou
3. Vá até a aba "Auth" (Autenticação)
4. No campo de Scopes (escopos), adicione:

```
crm.objects.contacts.read crm.objects.contacts.write
```

5. Salve e vá até a aba "Credentials"
6. Copie os valores:
   - clientId
   - clientSecret

Essas credenciais serão necessárias nas próximas etapas.

---

## Etapa 2: Gerar a URL de Autorização

### Endpoint:

```
GET /oauth/authorize-url
```

### Parâmetros obrigatórios:

- clientId
- redirectUri
- scope

> Use os escopos já configurados:  
> crm.objects.contacts.read crm.objects.contacts.write

### Retorno:

Será gerada uma URL de autorização do HubSpot. Ao acessá-la:

1. Você será redirecionado para o site do HubSpot
2. Aceite os termos de acesso
3. O HubSpot redirecionará para a redirectUri informada, contendo um code na URL

---

## Etapa 3: Trocar o Code por Access Token

### Endpoint:

```
POST /oauth/callback
```

### Parâmetros obrigatórios:

- clientId
- clientSecret
- redirectUri
- scope
- code

### Retorno esperado:

```json
{
  "access_token": "SEU_TOKEN",
  "refresh_token": "...",
  "expires_in": 21600
}
```

---

## Etapa 4: Criar um Contato no HubSpot

### Endpoint:

```
POST /hubspot/contatos
```

### Cabeçalho:

```
Authorization: Bearer {access_token}
```

### Corpo da requisição:

```json
{
  "email": "teste@example.com",
  "firstname": "Fulano",
  "lastname": "da Silva",
  "phone": "61999999999"
}
```

---

## Etapa 5: Receber Webhook de Criação de Contato

1. No painel do app no HubSpot, vá até a aba "Webhooks"
2. Cadastre a URL do seu endpoint:

```
POST /webhook/hubspot/contact
```

3. Escolha o evento:

- Object Type: Contact
- Event Type: Creation (contact.creation)

4. Salve e ative.

---

## Teste de Resiliência: Retry com Fallback

Endpoint de teste para simular erro 429:

```
GET /hubspot/teste-retry
```

Comportamento:

- 3 tentativas automáticas
- 2 segundos entre tentativas
- fallback em caso de falha persistente

---

## Documentação via Swagger

Acesse a documentação dos endpoints em:

```
http://localhost:8080/swagger-ui.html
```

---

## Tecnologias utilizadas

- Java 17+
- Spring Boot 3
- Spring Web
- Springdoc OpenAPI (Swagger)
- Resilience4j (Retry)
- HubSpot OAuth 2.0 API

---

## Autor

Desenvolvido por Danilo Pelusci para fins de integração com a API do HubSpot como parte de um case técnico.
