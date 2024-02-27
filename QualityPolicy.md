# Group Boston Quality Policy

## Branches
* There shall not be any commits made directly to the Period or Master branches.
* A single branch shall be made per User Story.
* No more than this one branch shall be created per User Story, except as follows:
  * A major refactoring or breaking change is being made such that it would cause disruption from uncompilable code if the commits were made to the User Story branch directly AND the branch creator has obtained the consent of all those in the group.
* A single branch shall be made per Storyless Task
* Branches shall be named according to the following format:
  * User Story branches: `US-##` where "##" is the number of the user story.
  * Storyless Task branches: `T-##-Description` Where "##" is the task number.
* A single branch shall be made per Project Period and shall follow the naming convention `Period-#`
* Each branch shall be merged into the parent branch in accordance ONLY with the Review policy (see below)
  * Exception: There is no restriction on merging branches *into* User Story and Storyless Task branches

## Reviews
* Branches being merged into the Period branch shall be approved by 2 reviewers other than the Pull Request submitter.
* Reviewers shall post their reviews using the following formatted checklist:
```markdown
# Review Checklist
[ ] - Code Compiles
[ ] - Tests Pass
[ ] - Essential code is commented using docstrings
[ ] - US acceptance criteria are met
```