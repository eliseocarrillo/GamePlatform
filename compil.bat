mkdir bin
del /f /s /q bin\*.class

javac -d bin -classpath "src" src/fr/cnam/projet/Projet.java
pause
