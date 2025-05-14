# Getting Started

Welcome to **glückliche Rinne**! This guide will help you set up the project on your local machine so you can start exploring, running, or contributing to the codebase.

## Prerequisites

Before you begin, make sure the following tools are installed on your system:

- Java 17 or later
- Maven 3.8+

Optional (for building and viewing the documentation in your browser):

- Python 3
- MkDocs (install with `pip install mkdocs`)

## Clone the Repository

Clone the project from GitHub and navigate into the project directory:

```bash
git clone https://github.com/marco-haupt/glueckliche-rinne.git
cd glueckliche-rinne
```

## Build the Project

Once you've got it on your system, you can build the project using Maven:

```bash
mvn clean install
```

This will compile the source code, run any tests, and package the application into a JAR file in the `target/` directory.
