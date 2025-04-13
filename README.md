# Athenull

**Athenull** is an open-source reimplementation of Comma.ai's backend, written in Kotlin. It aims to provide a fully self-hosted backend server that emulates the behavior of Comma’s proprietary infrastructure, allowing you to connect and interact with Comma devices on your own terms—with full control over your data and environment.

**⚠️ This project is in active development. Expect things to break. Contributions, issue reports, and feature suggestions are welcome!**

---

## 🚀 Project Goals

- Provide an open-source alternative to Comma.ai’s backend servers
- Support SQLite, MySQL, and PostgreSQL for flexible database setups
- Remain API-compatible with stock Comma firmware
- Modular and extensible design for tinkering, customizing, and integrating with other systems

---

## 🔧 Features (WIP)

- [x] Basic endpoint scaffolding
- [ ] Device registration and heartbeat tracking
- [ ] File upload support (route segments, logs, etc.)
- [ ] Drive metadata handling
- [ ] Comma Prime-style features
- [ ] Web interface for logs, drives, and stats

---

## 🛠️ Setup

1. Clone the repo  
   ```bash
   git clone https://github.com/your-username/athenull.git
   cd athenull
   ```

2. Choose and configure your database in `config.yaml`

3. Build and run:
   ```bash
   ./gradlew run
   ```

## ⚠️ Full setup guide coming soon. Right now, this is mostly for devs who are comfortable poking around and hacking on stuff.

## 🧠 Credits & Inspirations
Athenull is made possible by the incredible work of the open-source and Comma.ai communities:

- Comma.ai — Their API documentation and openpilot repo provided critical insight into how the backend communicates with devices.

- [USA-RedDragon/rtz-server](https://github.com/USA-RedDragon/rtz-server) — This project is heavily inspired by and adapts parts of rtz-server, a GoLang-based Comma backend reimplementation.

Huge thanks to both for blazing the trail and sharing their knowledge openly.

## 🧪 Disclaimer
This project is unofficial and is not affiliated with Comma.ai in any way.
Use at your own risk. While care is being taken to avoid breaking changes, there still may be times when it will happen anyway.

## 📣 Want to help?
Pull requests, issues, and feature discussions are welcome!
Hit me up via GitHub Issues or start a thread in Discussions if you want to contribute.

## 📜 License
This project is licensed under GPLv3.
