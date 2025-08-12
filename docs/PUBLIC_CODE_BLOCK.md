### Public Code Block
If Public Code Block is enabled, if Copilot generates code that closely matches licensed code in the public domain, the response will be blocked. However, there are ways of helping Copilot avoid suggesting public code.

- Refactor / Reframe your prompt
- Ask Copilot to break suggested code into different blocks in its response
- Ask Copilot to only show changed lines of code
- Ask Copilot to just show pseudocode
- Ask Copilot to show the code it suggests in another language
- Break your problem into smaller problems
<!-- - Ask Copilot to comment out the code it suggests
- Ask Copilot to prepend the code it suggests with something like `##` -->

Generally speaking, when we work with our own large, complex, unique codebases, we won't run into this much. This will mostly come into play when we are starting from scratch or asking Copilot for generic examples. Across all of Copilot, only about 1% of suggestions hit a public code block and most of those are new new files or other generic (and non-code!) use cases. The alternative to the Public Code Block is Code Referencing, where Copilot will show the public code anyway and let you know what type of license applies to the repo it is sourced from.