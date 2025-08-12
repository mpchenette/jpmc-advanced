> For information on the app used in this lab, see [ABOUT_THE_APP.md](docs/ABOUT_THE_APP.md)

# Hands-on Exercises
## 1. Copilot Customization
### Challenge #1
#### Set-up
1. Navigate to ``
2. Open Copilot Chat
3. Switch to `Agent` mode for Copilot Chat
``` md
Controllers must accept Spring Data Pageable and return Page<DTO>. 
Never return entities from controllers; use TaskDto + MapStruct mapper.
Always support sort via `sort` query parameter.
```

#### Exercise

#### Follow-up
1. Given what we learned earlier, which feature might be a better fit for this type of information in the future?

<details>

ANSWER: Prompt files
</details>


### Challenge #2
use Mermaid?

### Challenge #3
specifying how we want MD files to be structured

## 2. Workspace Knowledge + Copilot Code Review
### Challenge #1
#### Set-up
1. Click on the Copilot icon at the bottom right of the VS Code window
2. If not already indexed, create a local index of this repository
3. Open Copilot Chat
4. Switch to `Ask` mode for Copilot Chat
5. Close all open files and ask Copilot `Where in the repo would I find ___`. Do not add any context.

#### Exercise
Using `Ask` mode and Copilot code review, identify and fix three vulnerabilities in this repo.
> Tip: If you're having a hard time finding vulnerabilities, utilize the `#codebase` reference to further inform Copilot of the files in this repository

<details>
<summary>SOLUTION</summary>

1. Vulnerability in `abc.java`
  - Highlight code, right-click, `Copilot`->`Review and Comment`
1. Vulnerability in `abc.java`
  - Highlight code, right-click, `Copilot`->`Review and Comment`
1. Vulnerability in `abc.java`
  - Highlight code, right-click, `Copilot`->`Review and Comment`
</details>


## 3. Public Code Block
### Challenge #1
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

### Challenge #2
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
### Challenge #1
#### Set-up
1. Open Copilot Chat
1. Switch to `Ask` mode in Copilot Chat
1. Ask Copilot `What is your knowledge cutoff date?`
1. Ask Copilot `What is the latest version of Java?`
  - Q: What would you do if you were tasked with upgrading to Java 25?
1. Update `.github/instructions/java.instructions.md` to say Java 25
1. Switch to `Agent` mode in Copilot Chat

#### Exercise
Using what we learned today, perform an upgrade of this Application from Java 17 to Jave 25.

<details>
<summary>HINT</summary>

This will require use of many tools including:
- `#fetch`
- Ask mode
- Agent mode
- Custom instructions
</details>

<details>
<summary>SOLUTION</summary>

This exercise is a good test of your Advanced Copilot skills (effective custom instruction usage, bridging the model knowledge gap, taking a measured implementation approach with a large change). You will need to first help Copilot understand the differences between Java 17 and 25 using `#fetch`. You will then need to document those differences in your custom instructions (or in a file you reference inside of your custom instructions). You will then need to form a development/upgrade plan with `Ask` mode. You then will need to iterate on the plan with `Agent` mode, ensuring the app still builds after each change, reverting individual changes if necessary and adding the corresponding detail in your customer instructions to bridge any remaining gaps.
</details>

#### Follow-up
Q: Which feature did we discuss today that would be a better fit for application upgrades in the future?

<details><summary>Answer</summary>The Java (or .NET) Upgrade Agent</details>
