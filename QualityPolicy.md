# Group Boston Quality Policy

## Branches

* There shall not be any commits made directly to the scopechange.Main branch.
* A single branch shall be made per User Story.
* No more than this one branch shall be created per User Story, except as follows:
    * A major refactoring or breaking change is being made such that it would cause disruption from uncompilable code if
      the commits were made to the User Story branch directly AND the branch creator has obtained the consent of all
      those in the group.
* A single branch shall be made per Storyless Task
* Branches shall be named according to the following format:
    * User Story branches: `US-##` where "##" is the number of the user story.
    * Storyless Task branches: `T-##-Description` where "##" is the task number.
* Each branch shall be merged into the parent branch in accordance ONLY with the Review policy (see below)
    * Exception: There is no restriction on merging branches *into* User Story and Storyless Task branches

## Reviews

* Branches being merged into the main branch shall be approved by 3 reviewers other than the Pull Request submitter.
* Reviewers shall conduct their reviews using the following formatted checklist:

```markdown
# Review comments

# Review Checklist
- [ ] Taiga updated
- [ ] Code Compiles
- [ ] Tests Pass
- [ ] Essential code is commented using docstrings
- [ ] US acceptance criteria are met
- [ ] Static analysis checks pass
```

## Releases

* A Release Tag shall be created for the state of the scopechange.Main branch after each merged pull request
* The Release Tag shall follow the naming scheme `#.#.#` Where the last number is for user stories and second number is
  for period releases
* The Release shall be created by creating and pushing a tag to the repository, **NOT** manually creating a release on the Github website (which is handled by a workflow). This can be done by running the following commands:
```shell
#            tag     annotation
git tag -am 2.0.0 "Release 2.0.0"
git push -u origin 2.0.0
```

## Swimlanes

* Swimlanes will have a DONE, DELIVERED, and DEPLOYED status
* DONE meaning that the work for the user story is completely DONE
* DELIVERED meaning that the additions have been delivered with a release
* DEPLOYED meaning that the additions are containerized and push button deployable
