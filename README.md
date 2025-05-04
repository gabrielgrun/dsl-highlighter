# DSL Highlighter

This project is a simple **Domain-Specific Language (DSL) highlighter** for text-based UI automation commands like `click` and `enter into`. It parses predefined commands from plain text and outputs a colorized version using HTML `<span>` elements, helping visualize syntax structure for better readability or educational purposes.

## ✨ Features

- Highlights DSL commands such as:
    - `click "button label"`
    - `enter "value" into "field name"`
- Supports mixed casing (e.g., `CLICK`, `Click`, `click` are all valid).
- Identifies and highlights syntax errors.
- Easily extensible for additional commands.

## 💡 Example Input

click "Submit Button"
enter "John Doe" into "Name Field"


### Output

```html
<span color="command">click</span> <span color="parameter">"Submit Button"</span>
<span color="command">enter</span> <span color="parameter">"John Doe"</span> <span color="keyword">into</span> <span color="parameter">"Name Field"</span>
```

### ✅ Running Tests
This project uses JUnit 5 for unit testing. To run the tests:

#### With Maven
Make sure you have Maven installed. Then run:
```mvn test```