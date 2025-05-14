# Language Specification

*"Glückliche Rinne"* (literally: *happy gutter*) is a statically-typed, imperative, block-structured programming language developed as part of an academic course on compiler construction. It is intentionally designed to be syntactically dissimilar from Java (the implementation language)
to improve didactic clarity and encourage student exploration in language and compiler design.

## 1. Lexical Structure

The entire language is case-sensitive. Lexemes like `drucke`, `Drucke`, and `DRUCKE` are treated as distinct.
Whitespace is used solely as a separator; it carries no syntactic significance and may be used freely to improve code readability.

### 1.1 Keywords (Reserved Words)

Certain lexemes are reserved because they carry special meaning. They **cannot** be used for other purposes. Misuse of a keyword results in a compile-time error.

`als`, `ansonsten`, `bitte`, `deklariere`, `drucke`, `falsch`, `Fehler`, `Fließzahl`, `Ganzzahl`, `gebe`, `ja`, `mit`, `nein`, `oder`, `ohje`, `ohne`, `Schnur`, `so`, `solange`, `und`, `wahr`, `Wahrheitswert`, `wenn`, `wiederhole`, `während`, `zurück`

### 1.2 Identifiers

Identifiers are the names used to represent values, variables, functions, or other entities in the program. They must begin with a letter or underscore (`[a-zA-ZäöüÄÖÜ_]`), and may contain letters, digits (`[0-9]`), or underscores thereafter.

⚠️ Remember that keywords cannot be used as identifiers and that the language is case-sensitive, so `x` and `X` are considered different identifiers.

### 1.3 Literals

The language supports the following kinds of literal values:

- Integer literals: e.g. `42`, `0`, or `-7`, within the range -2<sup>63</sup> to 2<sup>63</sup>-1.
- Decimal numbers: e.g. `3.14159` or `2.71828`
- Boolean literals: `wahr` or `falsch` (with `ja` and `nein` accepted as stylistic variants)
- String literals: sequences of characters enclosed in double quotes, e.g. `"text"`.  
  Multiline strings are not supported

### 1.4 Comments

Comments allow programmers to include notes or explanations in their code that are ignored by the compiler. They can improve readability, document intent, or just leave passive-aggressive reminders for future readers (including yourself).

#### 1.4.1 Single-line Comments

Single-line comments begin with two forward slashes `//` and continue to the end of the line. They can appear on their own line or after code:

```gr
// This is a single-line comment
```

#### 1.4.2 Multi-line (Block) Comments

For longer explanations or temporarily disabling sections of code, you can use block comments. A block comment begins with `/*` and ends with `*/`. Everything in between is ignored by the compiler—even across multiple lines. Block comments cannot be nested.

```gr
/* This is a multi-line comment.
   It ends on the first occurrence of */
```


## 2. Expressions and Operators

Expressions represent computations composed of literals, identifiers, operators, and function calls (see §4.7). When evaluated, an expression yields a value.

In an expression like `3 + 4 * 5`, the operators `+` und `*` both compete for the operand `4`. To resolve such situations consistently, the language defines two rules:

- **Precedence** defines which operators bind tighter.
- **Associativity** defines how operators of equal precedence group (left-to-right or right-to-left).

The following table lists all supported operators, ordered from highest to lowest precedence, with `a` and `b` being placeholders for arbitrary sub-expressions:

| Precedence | Operator                          | Description                              | Associativity |
| ---------- | --------------------------------- | ---------------------------------------- | ------------- |
| 1          | `a()`                             | function call                            | left-to-right |
| 1          | `+a` `-a` `!a`                    | unary plus, unary minus, and logical NOT | right-to-left |
| 2          | `a * b` `a / b` `a % b`           | Multiplication, division, and remainder  | left-to-right |
| 3          | `a + b` `a - b`                   | Addition and subtraction                 | left-to-right |
| 4          | `a < b` `a <= b` `a > b` `a >= b` | Relational comparisons                   | left-to-right |
| 5          | `a == b` `a != b`                 | Equality comparisons                     | left-to-right |
| 6          | `a && b`                          | logical AND                              | left-to-right |
| 7          | `a || b`                          | logical OR                               | left-to-right |

Use parentheses `()` to override operator precedence and clarify grouping. For example, `(1 + 2) * 3` evaluates to `9`.

The language also supports chained comparisons in mathematical style. An expression like `3 < x < 10` is interpreted as `3 < x && x < 10`, simplifying range checks and improving readability.

## 3. Types

Types describe what kind of data a value represents—numbers, text, truth values, and so on. The language provides the following built-in types:

- `Ganzzahl`: 64-bit signed integers
- `Fließzahl`: 64-bit floating-point numbers
- `Wahrheitswert`: Boolean values (`wahr`/`falsch`, or `ja`/`nein`)
- `Schnur`: Immutable sequences of characters

**⚠️ TODO**: Type conversions are not yet specified.

## 4. Statements and Control Structures

Programs consist of statements, which are executed in sequence. While expressions produce values, statements produce effects—such as assigning values to variables, printing output, or changing the control flow. Statements are separated by commas, and blocks end with a period (`.`).

There is no way to define your own block scope explicitly, as is usually done within curly braces `{}`. Blocks are created implicitly by control structures or function definitions (see §4.5 to §4.7). Only the language’s control structures determine where blocks begin and end.


### 4.1 Console Output

The `drucke` statement prints the value of an expression to standard output (typically the console). It does not append a newline or space; formatting must be handled explicitly within string literals.

Printed values appear in their plain form (e.g. numbers as digits, booleans as `wahr` or `falsch`).

```gr
drucke "Hallo, Welt",
drucke 21 * 2,
drucke wahr,
```

### 4.2 Program Termination

The `ohje` statement is used to **immediately and unconditionally terminate** program execution. It provides no error message, no traceback, and no additional explanation.

```gr
drucke "This should be output.",
ohje,
drucke "This will not be output.",
```

### 4.3 Variable Declarations

Programs need memory to perform their calculations. A variable is a named storage location that holds a value during program execution. Variables are mutable, meaning their contents can change over time. Each variable has:

- a **name** (an identifier),
- a **type** (such as `Ganzzahl` or `Schnur`),
- and a **value**, which may be assigned immediately or later.

All variables must be explicitly declared before use. A declaration introduces the variable's name, its type, and optionally its initial value. The language supports two forms of variable declaration:

The most common form combines declaration and initialization in a single step:

```gr
anteil als Fließzahl := 0,
zahl := 42,
```

This form allows for type inference—the type is determined from the expression.

To declare a variable without assigning it a value yet, use the `oder so` form:

```gr
text als Schnur oder so,
```

This expresses that the variable exists but remains undefined until explicitly assigned.

Here is the general syntax:


```ebnf
variableDeclaration ::= identifier declarationTail

declarationTail     ::= 'als' type initialization
declarationTail     ::= ':=' expression ','

initialization      ::= 'oder' 'so'
initialization      ::= ':=' expression ','
```

All variables are **block-scoped**, meaning they are only visible within the block in which they are declared. There is no implicit sharing of scope between blocks. If a variable is declared inside a loop or conditional, it is not accessible outside of that structure. Variables may be **shadowed** by new declarations of the same name in nested blocks.


### 4.4 Assignments

Once a variable has been declared, its value can be changed through an assignment. An assignment updates the contents of a variable with the result of evaluating an expression. Unlike declarations, assignments do not introduce a new variable—they operate on one that already exists.

```gr
zahl als Ganzzahl oder so,
name als Schnur := "Alice",
zahl = 42,
zahl = zahl + 1,
name = name + " und Bob",
```

Here is the general syntax:

```ebnf
assignment ::= identifier '=' expression ','
```

The left-hand side must be the name of a previously declared variable. The right-hand side must be a valid expression whose result matches the type of the variable.


### 4.5 Conditional Statements

Sometimes, a program needs to make decisions. This is where conditional statements come in. They allow a block of code to execute only if a certain condition is true. The construct used is `wenn`, optionally followed by `ansonsten` for an alternative path.

The condition is written in parentheses after `wenn`. If it evaluates to true, the associated block runs. If it doesn’t—and a `ansonsten` block is provided—that block runs instead.

For example:

```gr
wenn(x < 0)
  drucke "negativ",
ansonsten
  drucke "nicht negativ",
.
```

In this case, the program prints either "negativ" or "nicht negativ" depending on the value of `x`.

Conditional blocks can contain multiple statements, separated by commas:

```gr
wenn(x == 12)
  drucke "zwölf",
  drucke " oder ein Duzend",
.
```

Nesting is allowed, but there is no `else if` construct. If you need multiple branches, you can nest `wenn` statements or use logical operators such as `und`, `oder`, and `nicht` to build compound conditions.

Here is the general syntax:

```ebnf
ifStatement ::= 'wenn' '(' condition ')' statements [ 'ansonsten' statements ] '.'
```

We had plans to support pattern matching too—but unless someone finds a free weekend, it probably won’t happen in time. So for now, it’s just `wenn` and `ansonsten`. Oh, and there’s no ternary operator either. Honestly, that’s the least of this language’s problems.


### 4.6 Looping

Repeating things is a fundamental part of programming. This language offers two loop constructs: `während` and `wiederhole ... solange`. Both execute a block of code repeatedly, but they differ in *when* the loop condition is evaluated.

The pre-check loop (`während`) checks the condition *before* each iteration. If the condition is false at the start, the loop body may never run at all. The post-check loop (`wiederhole ... solange`), by contrast, checks the condition *after* each iteration, which guarantees the loop body runs *at least once*.

For example:

```gr
zähler := 0,
während(zähler < 5)
  drucke zähler,
  zähler := zähler + 1,
.
```

This prints the numbers 0 through 4. The loop condition is checked *before* each iteration, and the loop exits as soon as `zähler` is no longer less than 5.

Now compare with:

```gr
zähler := 0,
wiederhole
  drucke zähler,
  zähler := zähler + 1,
solange(zähler < 5),
```

This produces the same output—unless `zähler` starts at a value like 10. In that case, the `wiederhole` block still executes once before the condition is even checked. Sometimes that’s useful. Sometimes it isn’t.

There are no `break` or `continue` statements. Control flow is expected to follow the structure you write—no shortcuts, no jumping out. That was a conscious decision. Whether it holds up under pressure remains to be seen.

Here is the general syntax:

```ebnf
whileStatement ::= 'während' '(' condition ')' statements '.'
doWhileStatement ::= 'wiederhole' statements 'solange' '(' condition ')' ','
```


### 4.7 Functions

Functions allow tightly related sections of code to be extracted into named, reusable units. A function encapsulates a specific task within a clearly defined block of code that can be invoked ("called") whenever needed. This promotes modularity by organizing logic into manageable components and abstracting away implementation details behind a clean interface.

Using functions improves code readability, facilitates reuse, and reduces redundancy, which in turn enhances maintainability. Because functions isolate behavior, they can be tested independently, making debugging easier and improving overall software reliability.

Functions can be *overloaded*, meaning multiple functions may share the same name as long as their parameter types or counts differ. Each function also defines its own scope, ensuring that variables declared within it do not interfere with those outside.

Function definitions begin with the keyword `deklariere`, and function calls start with the keyword `bitte`. The syntax is defined by the following grammar:

```ebnf
functionDefinition ::= 'deklariere' identifier '(' 'mit' parameters ')' statements '.'
parameters ::= 'ohne'
parameters ::= parameter { 'und' parameter }
parameter ::= identifier 'als' type

functionCall ::= 'bitte' identifier
```

Because function calls start with `bitte`, function names are expected to begin with a verb to clearly convey the action being requested.

For example, a function to compute Fibonacci numbers can be defined and used as follows:

```gr
deklariere berechneFibonacci (mit n als Ganzzahl)
  wenn(n <= 1)
    gebe n zurück,
  .
  gebe bitte berechneFibonacci mit (n-1) + bitte berechneFibonacci mit (n-2) zurück,
.

n := 10,
bitte fibonacci mit n,
```

As you can see from the example, functions can also call themselves to solve problems that are naturally recursive, such as tree traversal or computing Fibonacci numbers.


### 4.8 Error handling

All types implicitly include a special sentinel value named `Fehler`, representing an error state. This value does not automatically alter control flow—errors must be explicitly checked and handled using conditionals. If not checked, errors may silently propagate—potentially leading to incorrect or confusing program behavior.

For example:

```gr
quotient := 1 / 0,
wenn(quotient == Fehler)
  drucke "Anyone who understands this language can also divide by zero.",
  ohje,
.
```

In this example, division by zero produces `Fehler`, but the program continues executing unless the error is explicitly checked. Assigning `Fehler` to a variable does not interrupt execution, which allows errors to go unnoticed unless the programmer takes care to detect them.

For the record:
I voiced strong opposition against this design, but the class insisted. I accepted it as as a cautionary example. If they refuse to listen, they'll just have to learn through pain 😈


## 5. Program Structure

There is no explicit entry point such as a `main` function. Execution begins with the first top-level statement and continues sequentially with the next statements. This design makes the language easy to get started with, especially for short scripts and educational use.


## 6. Examples

This program prints the sum of all even numbers from 1 to 20:

```gr
deklariere i als Ganzzahl := 1,
deklariere summe als Ganzzahl := 0,

während (i <= 20)
  wenn (i % 2 == 0)
    summe = summe + i,
  .
  i = i + 1,
.

drucke "Summe der geraden Zahlen zwischen 1 und 20: ",
drucke summe,
```
