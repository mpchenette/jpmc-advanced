> For information on the app used in this lab, see [ABOUT_THE_APP.md](docs/ABOUT_THE_APP.md)

# Hands-on Exercises
## 1. Copilot Customization

### Challenge 1.1
#### Set-up
1. Open Copilot Chat
1. Switch to `Agent` mode for Copilot Chat
1. Ask Copilot to `Can you help me find vulnerabilities in my repository? I've been assigned with stabilizing our codebase this sprint and I need to fix any vulnerabilities I can find.`
   - Q: What do you notice about `Agent` mode's behavior? Is it making changes to your code? Why might this be a problem if I'm attempting to resolve vulnerabilities in my codebase?
1. Stop Copilot if necessary and `Undo` any suggested changes

#### Exercise
Using custom instructions, modify Copilot's behavior such that Copilot does not make any changes to your code, using that same prompt (i.e., `Can you help me find vulnerabilities in my repository? I've been assigned with stabilizing our codebase this sprint and I need to fix any vulnerabilities I can find.`).

<details>
<summary>SOLUTION</summary>

Add something similar to the following line to `.github/copilot-instructions.md`:
- `Do not make any code changes without first asking permission. You need explicit approval before modifying any code in this repository.`
</details>

#### Follow-up
Take a minute to explore the [awesome-copilot](https://github.com/github/awesome-copilot) repo. What stands out? What do you notice about the types of custom instructions (and other customization options) shared there? What ideas does awesome-copilot give you about how you can supercharge your own project's custom instructions approach?

### Challenge 1.2
#### Background
You work at XYZ Company. At XYZ, there are common standards and expectations that all documentation changes are held to. These standards include:
- All new additions to a document are sectioned off with labels highlighting who made the change
- There is a required field at the top of every document outlining when the document was last updated
- Any changes to existing document content must provide a WHAT/WHY label below the change outlining what the change was and why it was necessary

#### Set-up
1. Open Copilot Chat
1. Switch to `Agent` mode for Copilot Chat
1. Ask Copilot `Can you modify #file:ABOUT_THE_APP.md to add a more robust description about what this project is? Can you also add a new section: "Future Enhancements" outlining things that should be worked on soon?`
1. Observe the changes suggested by `Edit` mode. Do not accept them.
1. `Undo` the suggested changes

#### Exercise
Using custom instructions, modify Copilot's behavior such that the same prompt (i.e., `Can you modify #file:ABOUT_THE_APP.md to add a more robust description about what this project is? Can you also add a new section: "Future Enhancements" outlining things that should be worked on soon?`), follows the standards outlined above.

<details>
<summary>SOLUTION</summary>

Add the following lines to `.github/copilot-instructions.md`:
- `Add and maintain a "LAST UPDATED:" line to the beginning of any documentation you create or update. This line should specify the date a change is made.`
- `Any net new additions to documentation needs to be contained in a block comment (or similar separator) with a label at the top and bottom of said comment or separator outlining the current user's name and handle.`
- `Any changes to existing documentation must include a "WHAT/WHY:" label immediately below any changes outlining what change is being made and why it is needed.`
</details>

#### Follow-up
Which type of custom instruction file did you use? Why? Would you have been able to achieve the same result with the other type of custom instruction file? What would use of the other custom instruction file type have looked like?

### Challenge 1.3
#### Set-up
1. Open Copilot Chat
1. Switch to `Agent` mode for Copilot Chat
1. Ask Copilot to `Create a new API endpoint to duplicate an existing task.`
1. Observe Copilot's approach to implementing the new endpoint.
   - What does it do? What does it not do?
     - Does it write unit tests?
     - Does it write documentation?
     - What types of error messages does it include for the new endpoint?
   - If needed, stop Copilot as it's running. The testing it tries to perform can take awhile.
1. `Undo` any changes suggested by Copilot

#### Exercise
Using custom instructions, modify Copilot's behavior such that Copilot appropriately validates user input (if appropriate), provides detailed endpoint error messages and documents changes it makes when using the same prompt (i.e., `Create a new API endpoint to duplicate an existing task.`). 

<details>
<summary>SOLUTION</summary>

Add something similar to the following lines to `.github/copilot-instructions.md`:
- `When creating a new API endpoint, ensure that:`
  - `All POST endpoints validate user input`
  - `All endpoints provide custom error responses, not just generic messages`
  - `All endpoints are documented in docs/ENDPOINTS.md`
</details>

#### Follow-up
Q: Given what we learned today, which feature might be a better fit for this type of task in the future, and why?

<details>
<summary>ANSWER</summary>
<a href="https://code.visualstudio.com/docs/copilot/copilot-customization#_prompt-files-experimental">Prompt files</a> because it's highly likely that other devs will create new API endpoints in the future, and we want a reusable prompt to standardize that process.
</details>

## 2. Workspace knowledge + Copilot code review
### Challenge 2.1
#### Set-up
1. Click on the Copilot icon at the bottom right of the VS Code window
2. If not already indexed, create a local index of this repository
3. Open Copilot Chat
4. Switch to `Ask` mode for Copilot Chat
5. Close all open files and ask Copilot `Where in this repository do I query the database?`. Do not add any context.

#### Exercise
Using `Ask` mode and Copilot code review, identify and fix three vulnerabilities in this repo.
> Tip: If you're having a hard time finding vulnerabilities, utilize the `#codebase` reference to further inform Copilot of the files in this repository

<details>
<summary>SOLUTION</summary>

1. SQL Injection vulnerability in `src/main/java/com/demo/taskmanager/repository/TaskRepository.java`
  - Highlight code, right-click, `Copilot`->`Review and Comment`
1. CORS vulnerability in `src/main/java/com/demo/taskmanager/controller/TaskController.java`
  - Highlight code, right-click, `Copilot`->`Review and Comment`
1. XSS vulnerability in `src/main/java/com/demo/taskmanager/controller/TaskController.java`
  - Highlight code, right-click, `Copilot`->`Review and Comment`
</details>

#### Follow-up
1. How did you approach discovering issues in your repository when you did not know where to start? What tactics worked and which did not work? How might you approach this task is the repository were much larger?
1. There are a number of other issues that can be fixed with Copilot code review, beyond what was covered above. How many other vulnerabilities can you find and fix?

## 3. Public Code Block
### Challenge 3.1
#### Set-up
1. Open Copilot Chat
2. Switch to `Ask` mode for Copilot Chat
3. Ask Copilot `Can you show me how a quick sort algorithm works? I want to understand the logic better.`

#### Exercise
Using the strategies discussed earlier, find a way to have Copilot show you the logic to a quick sort function without having the response blocked.

<details>
<summary>SOLUTION</summary>

- `Can you show me how a quick sort algorithm works? I want to understand the logic better. Just show me pseudocode.`
</details>

### Challenge 3.2
#### Set-up
1. Navigate to `/src/main/java/com/demo/taskmanager/util/StringUtil.java`
2. Open Copilot Chat
2. Switch to `Ask` mode for Copilot Chat
3. Include `StringUtil.java` as context
4. Ask Copilot `Can you add comments to #file:StringUtil.java ? Do not edit existing comments.`

#### Exercise
Using the strategies discussed earlier, find a way to have Copilot add comments to your function without having the response blocked.

<details>
<summary>SOLUTION</summary>

### Try
- `Can you add comments to #file:StringUtil.java ? Do not edit existing comments. Please show each comment you intend to add in it's own code block. Include the line below the function in each code block.`
  - `StringUtil.java:13-43`
## If needed
- `Can you add comments to #file:StringUtil.java ? Do not edit existing comments. Please show each comment you intend to add in it's own code block. Include the line below the function in each code block. Do not show the complete function.`
  - `StringUtil.java:13-43`
</details>

## 4. Knowledge cutoff + Application upgrades
### Challenge 4.1
#### Set-up
1. Open Copilot Chat
1. Switch to `Ask` mode in Copilot Chat
1. Ask Copilot `What is your knowledge cutoff date?`
1. Ask Copilot `What is the latest version of Java?`
  - Q: What would you do if you were tasked with upgrading to Java 24?
1. Update `.github/instructions/java.instructions.md` to say Java 24
1. Switch to `Agent` mode in Copilot Chat

#### Exercise
Using what we learned today, perform an upgrade of this Application from Java 17 to Java 24.

<details>
<summary>HINT</summary>

This may require use of many tools including:
- `#fetch` (only available in VS Code)
- Ask mode
- Agent mode
- Custom instructions
</details>

<details>
<summary>SOLUTION</summary>

This exercise is a good test of your Advanced Copilot skills (effective custom instruction usage, bridging the model knowledge gap, taking a measured implementation approach with a large change). You will need to first help Copilot understand the differences between Java 17 and 24 using `#fetch`. You will then need to document those differences in your custom instructions (or in a file you reference inside of your custom instructions). You will then need to form a development/upgrade plan with `Ask` mode. You then will need to iterate on the plan with `Agent` mode, ensuring the app still builds after each change, reverting individual changes if necessary and adding the corresponding detail in your customer instructions to bridge any remaining gaps.
</details>

#### Follow-up
Q: Which feature did we discuss today that would be a better fit for application upgrades in the future?

<details><summary>Answer</summary>The Java (or .NET) Upgrade Agent</details>
