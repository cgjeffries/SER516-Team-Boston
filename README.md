# SER516-Team-Boston-Louisville

# Project Documentation and Readme

## Scrum Application with Taiga API Integration

This project integrates with the Taiga API, facilitating scrum management and metric calculations. It features a GUI (
JavaFX) application to display Taiga information and scrum management with visualizations.

## Project Features

### Burndown Chart

The Burndown Chart feature generates visual representations of work versus time for a given sprint, utilizing Story
Points or the custom attribute "Business Value" from Taiga. This presents the user with a visual "information radiator"
for the progress of user stories and tasks against the total planned work for a sprint.

### Cycle Time

The Cycle Time feature analyzes the efficiency of the workflow process, measuring the time taken for Tasks or User
Stories to move from being started ("in-progress") to completed ("done"). This metric is visualized through a Cycle Time
scatterplot graph, offering an inverse perspective to task inertia and highlighting the speed of task completion.

### Lead Time

The Lead Time feature measures the duration from when a story or task is added to the product backlog until it is
delivered in a sprint, visualized on a Cumulative Flow Diagram (CFD). This metric helps teams understand the overall
timeline from idea inception to delivery and the efficiency of the development cycle.

### Product Backlog (PB) Change

This feature tracks and displays user stories added to the Product Backlog during an active sprint. It allows teams to
monitor scope creep and understand how additions to the backlog can impact sprint commitments and overall project
timelines.

### Product Backlog (PB) Health

The PB Health metric provides a comprehensive view of the product backlog's readiness for upcoming sprints by evaluating
the ratio of total user stories against those marked as "SprintReady". A "SprintReady" story, indicating it has been
groomed and is ready for inclusion in a sprint, helps assess the backlog's balance between preparedness and required
development effort, based on story points.

### Groom Rate

Groom Rate measures the proportion of user stories groomed (modified or updated) within a specific timeframe. This
metric highlights the team's effort in refining and estimating the backlog, crucial for maintaining an actionable and
prioritized list of user stories. It emphasizes the importance of continuous backlog grooming in agile development.

### Scope Change

Scope Change focuses on identifying user stories added to the Sprint Backlog after the sprint planning phase. This
metric is vital for understanding changes in sprint scope, helping teams to adapt and re-prioritize work effectively,
ensuring sprint goals remain achievable despite any alterations to the initially planned scope.

### Task Churn

This metric tracks the frequency and extent to which tasks are modified or added during a time frame in a project. It is a crucial indicator of stability or volatility in project requirements and objectives, providing insights that help teams recognize, adapt to, and effectively manage changes in their project scope or direction. This understanding can guide adjustments to workflow or resource allocation to maintain project continuity.

### Task Defect Density

This metric quantifies the number of defects found in a task relative to its size, such as the amount of work or number of features within the task. It serves as an essential indicator of the quality of the outputs produced during task execution. By monitoring task defect density, teams can pinpoint areas requiring quality enhancement, focus on refining their development practices, and ensure that deliverables meet the expected standards of functionality and reliability.

### Task Inertia

This metric identifies and measures the resistance to change or progression that a task encounters, often influenced by factors such as task complexity, lack of necessary resources, or entrenched organizational practices. It provides valuable insights for project managers and teams to understand where and why bottlenecks occur, enabling them to devise strategies to reduce inertia, enhance efficiency, and improve the overall pace of project execution.

### Task Excess

Task Excess metric evaluates the surplus or deficit of tasks within a given sprint compared to the team's capacity and
sprint goals. Task Excess can help teams optimize their sprint planning by balancing workload and ensuring realistic 
commitments, ultimately leading to improved productivity and delivery consistency.

## Setting up the application

### Requirements and Setup

- Java 17
- Maven for dependency management and running the project
  - Maven will include JavaFX in its dependencies
- Docker
  - You will need [BuildKit](https://docs.docker.com/build/buildkit/#overview) installed. If you have Docker Desktop installed, you should already have it.

### Clone the repository

   ```bash
   git clone https://github.com/ser516asu/SER516-Team-Boston.git
   cd SER516-Team-Boston
   ```

### One Punch Run Command

At the root of the project, run:

   ```bash
   ./scripts/run_deployed.sh
   ```

### Building

At the root of the project, run:

```bash
# Build the Microservices
docker-compose build
# Build the client
mvn -pl client -am -DskipTests clean install
```

Or using the convenience script under `scripts/`:

```bash
./scripts/build.sh
```

### Running

To run go to the project root and do:

```bash
# Run in detached mode
docker-compose up -d
mvn -pl client javafx:run
```

Or using the convenience script under `scripts/`:

```bash
./scripts/start.sh
```

### Stopping

At the root of the project, run:

```bash
docker-compose down
```

### Testing the application

Run application tests with the following command

```bash
   mvn test
   ```

### Implementation and Technical Description

The project is structured to interact with the Taiga API, leveraging JavaFX for the GUI components, allowing users to
interact with the application visually. The main technical aspects include:

- API Integration: Utilizes custom API wrapper classes (UserStoryAPI, ProjectAPI, etc.) to communicate with Taiga's
  RESTful API, fetching and updating data asynchronously.
- Model Representation: Defines comprehensive model classes (UserStoryDetail, Project, etc.) that mirror the JSON
  structures returned by the Taiga API, facilitating easy data manipulation and display.
- UI Interaction: Employs JavaFX screens (MetricSelection, ProjectSelection, etc.) for navigating between different
  functionalities, such as selecting metrics, projects, and sprints.
- Utility and Helper Classes: Incorporates utility classes (UserStoryUtils, TaigaUtil, etc.) that provide methods for
  calculating metrics and performing other helper functionalities.
- Configuration and Settings Management: Manages application settings and configurations (Settings.java, AppModel.java),
  including saving and loading user preferences and project data.

## TROUBLESHOOTING

If you encounter issues, particularly if no data is coming in, it may help to 
delete the 'settings.json' file in your home directory 
(e.g. 'C:\Users\user\.boston\settings.json').

### NOTE

In case you don't have Maven installed, please refer to following tutorial:

https://phoenixnap.com/kb/install-maven-windows

### Team
**Boston**  
Oluwamayowa Esan,
Charles Jeffries,
Michael Kangas,
Soohwan Kim,
James Thayer

**Louisville**  
Ashish Thanga
Akshat Bakliwal
Jill Bharat Hansalia
Pranav Kutralingam
Harshitha Karur
