# trello-quarkus-upload-attachment

This project is a reproducer for https://github.com/quarkusio/quarkus/issues/35915.

## How to reproduce?

### Initial setup

* Create or login to a (free) [Trello account](https://trello.com/home) (check your spam at account creation...)
* Once you've been redirected from the Atlassian SSO, you should land on a welcome page: https://trello.com/create-first-team
* Create a first board, you can skip the rest of the wizard
* Create a card in the board, and then click on it to open it, look at the url, it should be of the form 
  https://trello.com/c/<cardId>/<whatever>. You want to keep the `cardId`, you'll need it.
* Go to https://trello.com/power-ups/admin/, accept the conditions, then click on new.
* Fill in the form, you can skip the `Iframe connector URL (Required for Power-Up)` field.
* Finally (!) click the `Generate a new API key` button, retrieve the `API Key` from the page and click on the 
  generate a token link at the right of the `API key` field.

### Reproducing

* Clone this project
* Setup the `trello.apiKey` and `trello.token` config properties that we just retrieved
* Run `mvn quarkus:dev -Dquarkus.args="<cardId> 500"`, observe the file was properly uploaded:

```text
2023-09-14 11:59:30,787 INFO  [eu.pie.EntryCommand] (Quarkus Main Thread) Trello answered with TrelloAttachment[id=6502d982a1ddacfcf39a3e71, name=file, url=https://trello.com/1/cards/6502d43d57a9e66c24f13c5a/attachments/6502d982a1ddacfcf39a3e71/download/file, data=null, isUpload=true, isCover=false]
```

* Run `mvn quarkus:dev -Dquarkus.args="<cardId> 20000"`, observe the upload failure.

```text
org.jboss.resteasy.reactive.ClientWebApplicationException: Received: 'Length Required, status code 411' when invoking: Rest Client method: 'eu.pierrebeitz.TrelloRestClient#uploadAttachmentOnCard'
	at org.jboss.resteasy.reactive.client.impl.RestClientRequestContext.unwrapException(RestClientRequestContext.java:185)
	at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.handleException(AbstractResteasyReactiveContext.java:324)
	at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:175)
// omitted
	at java.base/java.lang.Thread.run(Thread.java:833)
Caused by: jakarta.ws.rs.WebApplicationException: Length Required, status code 411
	at eu.pierrebeitz.EntryCommand.run(EntryCommand.java:34)
	at picocli.CommandLine.executeUserObject(CommandLine.java:2026)
	at picocli.CommandLine.access$1500(CommandLine.java:148)
	at picocli.CommandLine$RunLast.executeUserObjectOfLastSubcommandWithSameParent(CommandLine.java:2461)
	at picocli.CommandLine$RunLast.handle(CommandLine.java:2453)
	at picocli.CommandLine$RunLast.handle(CommandLine.java:2415)
```

NOTE: you can experiment with whatever size (in bytes) you want by modifying the second argument.

--------------

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/trello-quarkus-upload-attachment-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- REST Client Reactive ([guide](https://quarkus.io/guides/rest-client-reactive)): Call REST services reactively
- Picocli ([guide](https://quarkus.io/guides/picocli)): Develop command line applications with Picocli

## Provided Code

### Picocli Example

Hello and goodbye are civilization fundamentals. Let's not forget it with this example picocli application by changing the <code>command</code> and <code>parameters</code>.

[Related guide section...](https://quarkus.io/guides/picocli#command-line-application-with-multiple-commands)

Also for picocli applications the dev mode is supported. When running dev mode, the picocli application is executed and on press of the Enter key, is restarted.

As picocli applications will often require arguments to be passed on the commandline, this is also possible in dev mode via:
```shell script
./mvnw compile quarkus:dev -Dquarkus.args='Quarky'
```

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
