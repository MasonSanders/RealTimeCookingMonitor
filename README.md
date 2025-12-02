# RealTimeCookingMonitor

Swing-based desktop app that simulates managing a grill and multiple food thermometers. It lets you pick a dish, preheat the grill, start/stop cooking sessions, and watch estimated progress/time remaining update from simulated temperature feeds.

## Project layout
- `src/cookingMonitor/` – application sources (UI, controllers, simulation models).
- `test/cookingMonitor/` – lightweight, dependency-free tests and a simple runner.
- `RealTimeCookingMonitor.jar` – built runnable jar (entrypoint `cookingMonitor.CookingMonitor`).

## Run the app
With Java available on your PATH:
```
java -jar RealTimeCookingMonitor.jar
```

## Build from source
Compile sources into `bin`:
```
javac -d bin $(find src -name '*.java')
```
Run the app from compiled classes:
```
java -cp bin cookingMonitor.CookingMonitor
```

## Tests
Tests use the minimal runner in `test/cookingMonitor/TestRunner.java`—no external libraries needed.
Compile and execute:
```
javac -d out $(find src test -name '*.java')
java -ea -cp out cookingMonitor.TestRunner
```
