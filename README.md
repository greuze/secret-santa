# secret-santa
Secret Santa send mailer

## Compile
Compile with:
```
mkdir bin
javac -d bin -cp lib/mail-1.4.1.jar src/es/greuze/sandbox/secretsanta/*.java
```

## Run
Run with:
```
java -cp bin:lib/mail-1.4.1.jar es/greuze/sandbox/secretsanta/SecretSanta ./participantes.txt
```
