# 🧙‍♂️ Hogwarts Archive System

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)
![Course](https://img.shields.io/badge/Course-INFO1113-purple?style=flat-square)

**A magical spellbook management system for the Hogwarts library**

[English](#english) | [中文](#中文)

</div>

---

## English

### 📖 Overview

The Hogwarts Archive System is a command-line application for managing spellbooks and student rentals at Hogwarts School of Witchcraft and Wizardry. This project demonstrates object-oriented programming principles including encapsulation, inheritance, and the use of design patterns.

### ✨ Features

- **Spellbook Management**: Add, list, and search spellbooks by type, inventor, or serial number
- **Student Accounts**: Create student accounts and track rental activities
- **Rental System**: Rent and return spellbooks with complete history tracking
- **CSV Import/Export**: Load spellbook collections from CSV files and save system state
- **Search & Filter**: Find spellbooks by type, inventor, or availability status

### 🚀 Quick Start

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/hogwarts-archive.git
cd hogwarts-archive

# Compile
javac src/*.java -d out

# Run
java -cp out HogwartsArchive
```

### 💻 Commands

| Command | Description |
|---------|-------------|
| `EXIT` | End the archive process |
| `COMMANDS` | Display help information |
| `LIST ALL [LONG]` | List all spellbooks |
| `LIST AVAILABLE [LONG]` | List available spellbooks |
| `LIST TYPES` | List all spellbook types |
| `LIST INVENTORS` | List all inventors |
| `TYPE <type>` | Show spellbooks of a specific type |
| `INVENTOR <inventor>` | Show spellbooks by an inventor |
| `SPELLBOOK <serial> [LONG]` | Show spellbook details |
| `ADD STUDENT <name>` | Add a new student |
| `ADD COLLECTION <file>` | Import spellbooks from CSV |
| `RENT <student> <serial>` | Rent a spellbook |
| `RELINQUISH <student> <serial>` | Return a spellbook |
| `COMMON <student1> <student2> ...` | Find common rental history |

### 📁 Project Structure

```
hogwarts-archive/
├── src/
│   ├── HogwartsArchive.java   # Main application & CLI
│   ├── Archive.java           # Business logic layer
│   ├── SpellBook.java         # Spellbook entity
│   └── Student.java           # Student entity
├── data/
│   └── spellbooks.csv         # Sample spellbook data
├── DESIGN_REPORT.md           # System design documentation
└── README.md
```

### 🏗️ Architecture

The system follows a clean three-tier architecture:

1. **Presentation Layer** (`HogwartsArchive.java`): Handles user input/output and command parsing
2. **Business Logic Layer** (`Archive.java`): Manages operations and coordinates between entities
3. **Data Layer** (`SpellBook.java`, `Student.java`): Domain entities with encapsulated state

For detailed design decisions, see [DESIGN_REPORT.md](DESIGN_REPORT.md).

---

## 中文

### 📖 项目概述

霍格沃茨档案管理系统是一个命令行应用程序，用于管理霍格沃茨魔法学校的咒语书和学生借阅记录。本项目展示了面向对象编程原则，包括封装、继承和设计模式的使用。

### ✨ 功能特性

- **咒语书管理**：添加、列出和搜索咒语书（按类型、发明者或序列号）
- **学生账户**：创建学生账户并跟踪借阅活动
- **借阅系统**：借阅和归还咒语书，完整记录借阅历史
- **CSV导入/导出**：从CSV文件加载咒语书集合并保存系统状态
- **搜索与筛选**：按类型、发明者或可用状态查找咒语书

### 🚀 快速开始

```bash
# 克隆仓库
git clone https://github.com/YOUR_USERNAME/hogwarts-archive.git
cd hogwarts-archive

# 编译
javac src/*.java -d out

# 运行
java -cp out HogwartsArchive
```

### 💻 命令列表

| 命令 | 描述 |
|------|------|
| `EXIT` | 退出程序 |
| `COMMANDS` | 显示帮助信息 |
| `LIST ALL [LONG]` | 列出所有咒语书 |
| `LIST AVAILABLE [LONG]` | 列出可借阅的咒语书 |
| `LIST TYPES` | 列出所有咒语书类型 |
| `LIST INVENTORS` | 列出所有发明者 |
| `TYPE <类型>` | 显示特定类型的咒语书 |
| `INVENTOR <发明者>` | 显示某发明者的咒语书 |
| `SPELLBOOK <序列号> [LONG]` | 显示咒语书详情 |
| `ADD STUDENT <姓名>` | 添加新学生 |
| `ADD COLLECTION <文件>` | 从CSV导入咒语书 |
| `RENT <学生号> <序列号>` | 借阅咒语书 |
| `RELINQUISH <学生号> <序列号>` | 归还咒语书 |
| `COMMON <学生1> <学生2> ...` | 查找共同借阅历史 |

### 🏗️ 系统架构

系统采用清晰的三层架构：

1. **表示层** (`HogwartsArchive.java`)：处理用户输入输出和命令解析
2. **业务逻辑层** (`Archive.java`)：管理操作并协调各实体之间的交互
3. **数据层** (`SpellBook.java`, `Student.java`)：封装状态的领域实体

详细设计决策请参阅 [DESIGN_REPORT.md](DESIGN_REPORT.md)。

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📬 Contact

If you have any questions or suggestions, please open an issue.
