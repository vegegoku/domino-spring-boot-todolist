# domino-todolist
Todo-list using [Domino](https://github.com/GwtDomino/domino) implementing UI with three different ways:

- [Elemento](https://github.com/hal/elemento) + [Polymer components](https://github.com/manolo/gwt-polymer-elements)
- UIBinder + [GWTMaterialDesign](https://github.com/GwtMaterialDesign/gwt-material)
- Desktop JavaFx (Requires a jdk with javafx support)

## How to build :

1. clone the repository

2. run `mvn clean install` to compile with UiBinders and Material design or run `mvn clean install -Dui=polymer` to compile with elemento and polymer web components

## How to run :
- Running as web application :

  - For super dev mode 
  
    - In one terminal run `mvn gwt:codeserver -pl todolist-frontend -am`
    
    - In another terminal `cd todolist-backend-vertx`
    - execute `mvn exec:java`
    - the server port will be printed in the logs access the application on `http://localhost:[port]`

  - For compiled mode 
    - `cd todolist-backend-vertx`
    - execute `mvn exec:java -Dmode=compiled`
    - the server port will be printed in the logs access the application on `http://localhost:[port]`

> Note : The UI you see in the browser depends on what command you used to build the application.

- Running the desktop version
  - in a terminal `cd todolist-desktop-frontend`
  - execute `mvn exec:java  -Dexec.args="localhost [port]"`

- Run the Spring-Boot backend

   this todo-list sample can also be started as a spring boot application

   - in a terminal `cd todolist-backend-springboot`
   - execute `mvn clean install`
   - execute `mvn spring-boot:run`

- Run the tomcat backend

    - For super dev-mode

        - In one terminal execute `mvn gwt:codeserver -pl todolist-frontend -am`
        - In another terminal execute `mvn tomcat7:run -pl todolist-backend-tomcat -am -Denv=dev`

    - For production : deploy the war artifact from the todolist-backend-tomcat in a tomcat server.

Thats it, have fun..
  
