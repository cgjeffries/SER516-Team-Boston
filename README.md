# SER516-Team-Boston

## Taiga API Integration

This project integrates with the Taiga API, facilitating scrum management and metric calculations. It features both a command-line interface (CLI) for direct API interactions and a graphical user interface (GUI) for an enhanced user experience.


## Setting up the application

### 1) Clone the repository


   ```bash
   git clone https://github.com/ser516asu/SER516-Team-Boston.git
   cd SER516-Team-Boston
   ```

### 2) Compile and Run the application

Go to the project root and compile the Maven project

```bash
   mvn compile
   ```

Now, run the GUI application using following command

```bash
   mvn compile exec:java -Dexec.mainClass=ui.Main
   ```

You may also run the application using javafx

```bash
   mvn javafx:run
   ```


To run the original starter CLI Taiga API app use the following command

```bash
   mvn compile exec:java -Dexec.mainClass=Main
   ```


### Testing the application

Run application tests with the following command

```bash
   mvn test
   ```



### NOTE

In case you don't have Maven installed, please refer to following tutorial

https://phoenixnap.com/kb/install-maven-windows


