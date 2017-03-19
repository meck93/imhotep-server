[Git Rules](#git-rules)
[Issue Rules](#issue-rules)

# SoPra RESTful Service Template

## Spring Boot

* Documentation: http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/
* Guides: http://spring.io/guides
  * Building a RESTful Web Service: http://spring.io/guides/gs/rest-service/
  * Building REST services with Spring: http://spring.io/guides/tutorials/bookmarks/


## Setup this Template with Eclipse

Note: Feel free to develop in the IDE of your choice (e.g., [IntelliJ IDEA](https://www.jetbrains.com/idea/)).

1. Install Gradle Eclipse plugin: http://marketplace.eclipse.org/content/gradle-integration-eclipse-44
2. File -> Import... -> Gradle Project
3. Browse to `sopra-fs17-template-server` and `Build Model`

To run right click the `build.gradle` and choose `Run As` -> `gradle run`


## Building with Gradle

* Gradle installation: http://gradle.org/installation
  * Mac OS X with [Homebrew](http://brew.sh/): ``brew install gradle``

### Run

```bash
gradle bootRun
```

### Test

```bash
gradle test
```



***





# Git Rules
**First view GitHub presentation on OLAT _Git_GitHub.pdf_.**

_[Basic Git commands (Atlassian Documentation)](https://confluence.atlassian.com/bitbucketserver/basic-git-commands-776639767.html)_

_[Git Cheat Sheet](https://www.git-tower.com/blog/git-cheat-sheet/)_

The following section describes the workflow of one coding session. If you code over multiple days (you will :)) or start coding in the morning, having a break and continue in the evening, it is not one single session.

#### Before Working (starting your coding session)
- check slack and issues before working on your task (there might have been some changes or the work might have been already done)
- never start working on a task without an issue ([Issue Rules](#issue-rules))

#### Start working on a new issue
- go to local repository
- switch to development branch
```bash
git checkout development
```
- pull development branch
```bash
git pull
```
- switch to your branch
```bash
git checkout [your_name]
```
- merge your branch with the development branch
```bash
git merge development
```

#### Continue working on an issue
- be sure you are working on the newest version (maybe pull and merge newest changes of development branch to your branch as described in the section above)

#### Committing your Work
**Commit if your work can't be described in one single line**
- stage all files you want to have in your commit...

... see which files were added, changed and deleted ...
```bash
git status
```
... stage either chosen files ...
```bash
git add [file_name]
```
... or stage all changed files ...
```bash
git add -A
```

- commit your staged files with the following scheme
```bash
git commit -m "#[issue_number]: [work description]"
```
e.g.

```bash
git commit -m "#5: added Git and Issue Rules and Workflow to ReadMe"
```

#### After Work (end of your coding session)
- push your work and commits of **YOUR** branch (be sure you are in your branch) to the remote repository

... for the first time ...
```bash
git push origin [branch name]
```
... later on ...
```bash
git push
```

- if your work is tested and seems to work fine, merge your branch into the development branch (or better make a pull request if you are not sure)




# Issue Rules
Issues are not only for **defining task responsibilities** but also for **tracking the workflow** and make it understandable for not envolved team members.
Derive Issues from the task list.

**DO NOT START WORKING BEFORE THERE IS AN ISSUE FOR YOUR IMPLEMENTATION TASK!**

#### Creating an Issue
- give it a selfdescribing name
- relate your description to a user story or/and task from the task list
- assign responsible team member (there is no issue without an assignee)
- add a matching label (or multiple)
- add issue to the corresponding project board (either TODO or In Progress)
- add milestone

#### Working on an Issue
- relate your commits on your branch to this issue (see [Git Rules](#git-rules))

#### Passing an Issue
- assign new responsible team member
- leave a comment what has to be done next (and what is already done)

#### Closing an Issue
- be sure all work is done (including comments on this issue)
- close issue
- move issue to "Done" on the project board
- merge your branch to the development branch (see [Git Rules](#git-rules))
