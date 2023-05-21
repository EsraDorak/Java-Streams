# playground

## Getting started

Die Struktur des `streams`-Branches des Projekts ist:

```
<SE-1>                  ; Haupt-Verzeichnis (Workspace bei eclipse)
  |
  +-<playground>        ; Neues Projekt für diese Übung
  |   +--<.vscode>
  |   +-- env.sh, tests.run, .gitignore, README.md
  |   |
  |   +-<src>           ; Java-Quellcode für das Projekt (source, src)
  |   |   +-<application>           ; Java-Paket (package)
  |   |       +-- PlayStreams.java        ; Interface mit Methoden für diese Übung
  |   |       +-- PlayStreamsImpl.java    ; Klasse mit Implementierungen der Methoden
  |   |       +-- PlayStreamsRunner.java  ; Klasse zur Ausführung mit main() – Funktion
  |   |       +-- Factory.java      ; Klasse zur Erzeugung von PlayStreams-Instanzen
  |   |
  |   +-<test>          ; Quellcode für JUnit5 - Tests für implementierte Methoden
  |   |   +-<application/streams>
  |   |       +-- Streams_0_always_pass_Test.java   ; JUnit Test-Klassen
  |   |       +-- ...
  |   |
  |   +-<lib>           ; Bibliotheken (libraries) für die JUnit5 Testausführung
  |   |   +-- junit-platform-console-standalone-1.9.2.jar
  |   |
  |   +--<bin, out>     ; Übersetzter Code (eclipse: bin, IntelliJ: out)
  |   |   +--<application>
  |   |       +--PlayStreams.class  ...
```

Beschaffen Sie den Branch in das existierende Projekt `playground` oder downloaden Sie das
[.zip](https://gitlab.bht-berlin.de/sgraupner/playground/-/archive/streams/playground-streams.zip).

```
git fetch origin streams      ; Beschaffen des Branches aus dem remote repository
git checkout streams          ; Wechsel in den Branch im Projektverzeichnis
```

&nbsp;

---
### 1. Sourcen des Projekts

Beim "Sourcen" des Projekts werden Umgebungsvariablen (environment variables)
gesetzt, die in einem script-File enthalten sind, das üblicherweise `.env`
oder `.env.sh` heisst.

```
cd .../playground                              ; Wechsel in das Projektverzeichnis
source .env.sh                                 ; Sourcen des Projekts, setzt CLASSPATH
echo ${CLASSPATH}
```

Anzeige der $CLASSPATH Umgebungsvariablen, die durch das Sourcen gesetzt wurde.
Windows-Systeme verwenden `";"` als Trennung zwischen den Einträgen, Mac und
Unix/Linux-Systeme `":"` (bitte beachten).

Die Umgebungsvariablen aus `.env.sh` sind für die Benutzung des Terminals
gültig. In neuen Terminals muss die Umgebung jeweils neu "sourced" werden.

```
Für Mac, Linux:
CLASSPATH=src:test:bin:lib/junit-platform-console-standalone-1.9.2.jar

Für Windows:
CLASSPATH=src;test;bin;lib/junit-platform-console-standalone-1.9.2.jar
```

&nbsp;

---
### 2. Übersetzen des Quellcodes

Das Übersetzen des Quellcodes erfolgt mit dem Java-Compiler `javac` im
Projektverzeichnis. Die Option -d gibt an, in welches Verzeichnis die
übersetzten Quellfiles geschrieben werden sollen, hier ist es: `bin`
(für binaries für *eclipse*, *VS Code*), *IntelliJ* verwendet `out`
(Verzeichnis entsprechend anpassen).

```
javac src/application/PlayStreams.java -d bin      ; übersetzt PlayStreams.java
javac src/application/PlayStreamsRunner.java -d bin
javac $(find src -name '*.java') -d bin        ; Übersetzen aller .java-Files
find bin                                       ; Anzeige der übersetzten Files
```
Anzeige der übersetzten Files in `bin` (bzw. `out`):
```
bin
bin/application
bin/application/Factory.class
bin/application/PlayStreams.class
bin/application/PlayStreamsImpl.class
bin/application/PlayStreamsRunner.class
```

&nbsp;

---
### 3. Ausführen des Programms

Die *main()*-Funktion befindet sich in der Klasse `PlayStreamsRunner.java`.
Das Ausführen des Programms erfolgt mit der `java` VM:

```
java application.PlayStreamsRunner 1 2 3
```

Ausgabe:

```
PlayStreamsRunner
 - tenRandomNumbers() -> [482]
 - tenEvenRandomNumbers() -> []
 - tenSortedEvenRandomNumbers() -> []

```


&nbsp;

---
### 4. Übersetzen der JUnit-Tests

Der Quellcode der JUnit-Tests befindet sich in einem separaten Verzeichnis
`test` im Projektverzeichnis (stets getrennt von `src`):

```
find test

test
test/application
test/application/streams
test/application/streams/Streams_0_always_pass_Test.java
test/application/streams/Streams_1_tenRandomNumbers_Tests.java
test/application/streams/Streams_2_tenEvenRandomNumbers_Tests.java
test/application/streams/Streams_3_tenSortedEvenRandomNumbers_Tests.java
test/application/streams/Streams_4_filteredNumbers_Tests.java
test/application/streams/Streams_5_filteredNames_Tests.java
test/application/streams/Streams_6_sortedNames_Tests.java
test/application/streams/Streams_7_sortedNamesByLength_Tests.java
test/application/streams/Streams_8_calculateOrderValue_Tests.java
test/application/streams/Streams_9_sortByOrderValue_Tests.java
...
```

Das Übersetzen einer JUnit-Test-Klasse erfolgt mit dem Java-Compiler `javac`.
Die übersetzten Tests werden wieder in das Verzeichnis `bin` geschrieben
(oder `out` bei *IntelliJ* ):

```
javac test/application/streams/Streams_0_always_pass_Test.java -d bin
```

Der übersetzte Test befindet sich jetzt in `bin`:

```
find bin

bin ...
bin/application/streams/Streams_0_always_pass_Test.class
...
```

Das Übersetzen aller JUnit-Tests erfolgt durch Auflistung aller
*.java Files des test-Verzeichnisses und Übergabe an den Java-Compiler
`javac`:

```
find test -name '*.java'
javac $(find test -name '*.java') -d bin

find bin
```

Die übersetzten Files befinden sich im Verzeichnis `bin` (oder `out` bei
*IntelliJ* ).


&nbsp;

---
### 5. Ausführung der JUnit-Tests

JUnit-Tests werden mit einem [Test-Runner](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-console-standalone)
ausgeführt, der angegebene Test-Klassen ausführen kann (`--select-class` oder `-c`)
oder diese selbständig im ${CLASSPATH} sucht (`--scan-classpath`).
Der Test-Runner befindet sich im `lib`-Verzeichnis.

Die Ausführung einer speziellen Test-Klasse erfolgt mit:

```
java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp "${CLASSPATH}" \
          -c application.streams.Streams_0_always_pass_Test
```

Ausgabe der Ergebnisse:

```
.
+-- JUnit Jupiter [OK]
| '-- Streams_0_always_pass_Test [OK]
|   '-- test_001_always_pass() [OK]
+-- JUnit Vintage [OK]
'-- JUnit Platform Suite [OK]

Test run finished after 117 ms
[         4 containers found      ]
[         0 containers skipped    ]
[         4 containers started    ]
[         0 containers aborted    ]
[         4 containers successful ]
[         0 containers failed     ]
[         1 tests found           ]
[         0 tests skipped         ]
[         1 tests started         ]
[         0 tests aborted         ]
[         1 tests successful      ]
[         0 tests failed          ]
```

Alternativ können die im Projektverzeichnis in der Datei
[tests.run](https://gitlab.bht-berlin.de/sgraupner/playground/-/blob/main/tests.run)
spezifizierten Tests ent-kommentiert (`#` zu Beginn der Zeile entfernen)
und ausgeführt werden mit:

```
java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp "${CLASSPATH}" \
          $(grep -v '^#' tests.run)
```

Der JUnit Test-Runner kann auch selbständig nach allen compilierten
Test-Klassen suchen:

```
java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp "${CLASSPATH}" \
          --scan-class-path
```

Ausgabe der Ergebnisse mit Ausführung aller Tests in
[tests.run](https://gitlab.bht-berlin.de/sgraupner/playground/-/blob/main/tests.run):

```
.
+-- JUnit Jupiter [OK]
| +-- Streams_0_always_pass_Test [OK]
| | '-- test_001_always_pass() [OK]
| +-- Streams_1_tenRandomNumbers_Tests [OK]
| | '-- test100_tenRandomNumbers_regular() [OK]
...
+-- JUnit Vintage [OK]
'-- JUnit Platform Suite [OK]

Test run finished after 226 ms
[        13 containers found      ]
[         0 containers skipped    ]
[        13 containers started    ]
[         0 containers aborted    ]
[        13 containers successful ]
[         0 containers failed     ]
[        30 tests found           ]
[         0 tests skipped         ]
[        30 tests started         ]
[         0 tests aborted         ]
[        30 tests successful      ]    <- 30 Tests erfolgreich
[         0 tests failed          ]
```
