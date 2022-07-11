Key cloak info:  (http://localhost:8080)
1. bin/kc.bat start-dev # bring up a dev instance (take 10 minute to initialize)
   admin/keycloakP...
   myrealm/myuser myp...
   myclient/ZiYfEwmn28TPZyTKLv7YNmwN4Mtdwt7U
   
2. create Master/admin (first time only)
     - Roles -> Add Role (for BOOK_SERVICE_READER and BOOK_SERVICE_ADMIN)
	 
3. create realm (myrealm), create user in myrealm/myuser (one time only)
     - add role for myuser (Users->Role Mappings -> BOOK_SERVICE_READER)
	 - add role for myadmin (Users->Role Mappings -> BOOK_SERVICE_ADMIN)
	 
4. create client (myclient/ZiYfEwmn28TPZyTKLv7YNmwN4Mtdwt7U) (one time only)
     - access type in setting is set to confidential
	 - valid redirect in setting is set to client controller to get access token
	      after authenication server sends an authorization token.
	 - generate secret key in "Credentials" tab.
	 
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/auth/realms/myrealm/app
	 
	 
	 
	 
 get http://localhost:8080/realms/myrealm/.well-known/openid-configuration
