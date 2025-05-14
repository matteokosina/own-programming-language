# Glückliche Rinne

*"Glückliche Rinne"* (a questionable German translation of a subversive twist on *"Lucky Strike"*) is a statically-typed, imperative programming language developed as part of an academic course on compiler construction. I mean, what else could it possibly be? 😄

## 📚 Documentation

Whether you're a developer or a user, the full documentation for *glückliche Rinne* — including getting started instructions — is located in the `docs/` directory.

You can view the documentation in two ways:
- 📄 **Read the Markdown files** directly in the `docs/` folder.
- 🌐 **Serve it locally in your browser** using [MkDocs](https://www.mkdocs.org/).

### Serving the Documentation Locally

If you prefer a browser-based view, follow these steps:

1. Make sure you have **Python 3** installed.
2. Install MkDocs:  
   ```bash
   pip install mkdocs
   ```
3. Serve the documentation:  
   ```bash
   mkdocs serve
   ```

This will launch a local web server, usually at http://127.0.0.1:8000, where you can browse the docs.

### Building the Static Site

To generate a static HTML version of the documentation (useful for deployment or offline viewing):

```bash
mkdocs build
```

The output will be placed in the `site/` directory.


## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.

Please make sure to update tests and documentation as appropriate.
