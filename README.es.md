# CircleSample on Android Projects
[![](https://img.shields.io/badge/language-EN-blue.svg)](./)

#### Author Romell Domínguez
[![](snapshot/icono.png)](https://www.romellfudi.com/)

## Basic Module

Cuando tenga listo su apllicación Android/Gradle, registrar su aplicación en circleci.com 

Cuando enlacen su projecto(en este caso Bitbucket también funciona para Github), nos permitirá enlazar nuestro projecto con una configuración *.yml este debe ser colocado en una carpeta llamada '.circleci/':

> ./circleci/config.yml
```yml
version: 2
jobs:
  build:
    working_directory: ~/code
    docker:
      - image: circleci/android:api-25-alpha
    environment:
      JVM_OPTS: -Xmx3200m
    steps:
      - checkout
      - restore_cache:
          key: jars-{{ checksum "build.gradle" }}-{{ checksum  "app/build.gradle" }}
      - run:
         name: Chmod permissions #if permission for Gradlew Dependencies fail, use this.
         command: sudo chmod +x ./gradlew
      - run:
          name: Download Dependencies
          command: ./gradlew androidDependencies
      - save_cache:
          paths:
            - ~/.gradle
          key: jars-{{ checksum "build.gradle" }}-{{ checksum  "app/build.gradle" }}
      - run:
          name: Run Tests
          command: ./gradlew lint test
      - store_artifacts:
          path: app/build/reports
          destination: reports
      - store_test_results:
          path: app/build/test-results

```

![image](snapshot/A.png#vertical)

En la configuración del bitbucket deben de estar asegurado que los permisos de escritura esten habilitados para commits inmediatos en el branch original

![image](snapshot/B.png#center)

Para hacer dinamico la prueba generamos un caso de prueba unitario erroneo:

![image](snapshot/AS.png#center)

![image](snapshot/AT.png#center)

Cuando realizamos el push el git remote, podemos visualizar que circleci comienza a ejecutar todas las pruebas 

![image](snapshot/AU.png#center)

Una vez finalizado nos indicará la falla encontrada

![image](snapshot/AW.png#center)

Ahora supongamos que ya resolvimos la incidencia, y las pruebas unitarias han sido corraboradas, realizamos push al git remote, en Circleci vemos que automáticamente las pruebas comezarón a ejecutarse

![image](snapshot/AV.png#center)

![image](snapshot/AX.png#center)

Una vez finalizado podemos apreciar en el dashboard de Circleci nos indica que ha culminado con éxito

![image](snapshot/AY.png#center)

Resumiendo el dashboard de circle ci muestra lo siguiente, donde:

* NOT RUN: Indica la prueba no ha sido ejecutada.
* SUCCESS: La prueba ha sido ejecutada satisfactoriamente
* FAILED: La prueba ha presentado incidencias
* FIXED: SI encuentra todas las pruebas fallidas anteriormente ejecutadas pasan correctamente

![image](snapshot/AZ.png#center)

## Intermediate Module

Una vez visto el potencial de la herramienta vamos a ver lo realmente útil que podemos hacer con Circleci, supongamos que en nuestro team Development queremos asegurar el branch maestro de nuestro repositorio siempre tenga una versión estable, donde todo lo desarrollado ha sido probado correctamente.

En la configuración de permisos de branch ahora quitaremos los permisos a todos los usuarios vías directas, y habilitamos únicamente los permisos vía pull request

![image](snapshot/C.png#center)

Generamos nuevamente un caso  de prueba 'serviceModule', en mi caso estoy probando una interfaz utilizando la libraría mokito 

![image](snapshot/D.png#center)

Hacemos el commit respectivo

![image](snapshot/E.png#center)

Pero al intentar enviar nuestros cambios al remote origin, el repositorio nos rechazará por no tender permisos

**Y AHORA QUÉ HAGO?**

![image](snapshot/EA.png#center)

**Simple generamos una rama a partir de la principal**
En la ventana de 'Push Commits' AndroidStudio, tendremos que pasar todos nuestros cambios a una rama auxiliar

![image](snapshot/F.png#center)

![image](snapshot/G.png#center)

Al realizar push, nos dejará libremente crearla debido a que únicamente deshabilitamos los cambios directos en el branch master.

![image](snapshot/H.png#center)

En el dasdhboard de nuestro proyecto en Circleci, veremos que hay un registro de pruebas con la etiqueta **NOT RUN**, está se agrega al detectar nuevos cambios en el repositorio

![image](snapshot/I.png#center)

Por otro lado en nuestro árbol de cambios/commits visualizaremos la nueva rama 'BranchServiceTest', creada desde nuestro AndroidStudio

![image](snapshot/J.png#center)

La persona encargada del cambio, que ha creado el nuevo branch, tiene que solicitar el merge al remote origin (crear nuevo pull request) 

![image](snapshot/L.png#center)

En el detalle debe indicar los cambios que ha relizado a alto nivel, confirmando que ha añadido sus respectivos casos de pruebas

![image](snapshot/M.png#center)

En la pantalla de pull requests se habrá añadido la solicitud para el respectivo merge

![image](snapshot/N.png#center)

Al entrar en el detalle podemos ver los cambios con sus comentarios hechos y el mensaje del desarrollador. **Debemos percatarnos de algo muy especial, tenemos un toggle button en la parte superior derecha (Approve)**. Al hacer click procederá la ejecución de los casos de pruebas en Circleci

![image](snapshot/O.png#center)

En el dashboard veremos como las pruebas se han ejecutado, una vez terminado visualizamos con éxito la ejecución de pruebas

![image](snapshot/P.png#center)

En la pantalla donde estamos del bitbucket visualizamos que hay un contador de pruebas ejecutadas, en nuestro caso como no ha detectado que los flujos de otros casos de pruebas han sido modificados, únicamente ejecutará nuestro caso de prueba 'serviceModule' de la clase ModuleTest 

![image](snapshot/Q.png#center)

En caso el administrador este conforme con  la implementación de los casos sean conforme, puede proceder al merge. **Asegurando una tasa de fiabilidad del código en todo momento** 

![image](snapshot/R.png#center)

En el apartado de los commits visualizamos que ya no existe la rama auxiliar, manteniendo nuestro cambios en el branch 'master' 

![image](snapshot/S.png#center)

Como podemos apreciar la herramienta Circleci nos permite una gran utilidad, para la gestión y control del versionamiento de los casos de pruebas automatizados, permitiendo diversificar las tareas entre los colaboradores del proyecto donde requieran pruebas

## Advance module

En el dashboard de Cicleci si entramos en uno de los resultados de las ejecuciones podemos ver:

![image](snapshot/T.png#center)

Podemos ver el registro a detalle de la prueba, step by step de todos los comandos que hemos dichos que realice:

![image](snapshot/U.png#center)

En el apartado de 'Chmod permissions' es muy usual que los principiantes en esta herramienta tengan problemas debido a dependiendo de la maquina esta necesitará permisos SUDO

```yml
jobs: ...
  build: ...
    steps: ...
      - run: ...
         command: sudo chmod +x ./gradlew
...
```

Como se puede apreciar la herramienta Circleci soporta flavors de gradle, en este proyecto estoy usando el flavor 'SonarQube' para luego conectarlo a una base de hechos de pruebas en caso lo requiera por parte de la organización  :

![image](snapshot/V.png#center)

Todos los derechos reservados de uso a la herramienta :

[![image](snapshot/circleci.png#circleci)](https://circleci.com/)

### License
```
Copyright 2018 Romell D.Z.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

**2018, July**

<style>
img[src*='#center'] { 
    width:500px;
    display: block;
    margin: auto;
}
img[src*='#vertical'] { 
    width:250px;
    display: block;
    margin: auto;
}
img[src*='#circleci'] { 
    width:100px;
    display: block;
    margin: auto;
}
</style>