
#  OptiTask Core

A lightweight Java execution engine designed for asynchronous task scheduling, priority-based execution, and automated failure recovery.

## Key Features
- **Dynamic Priority Queue:** Executes `HIGH` priority tasks before `MEDIUM` and `LOW` jobs using `PriorityQueue` & `Comparable`.
- **Failure Recovery:** Automatically re-queues failed tasks up to a defined retry limit (`Max Retries`).
- **Unique Job Tracking:** Assigns shortened `UUID` identifiers to each task for tracking.
- **Zero External Dependencies:** Built with pure Java (`java.util.*`), making it fast and ultra-lightweight.

## How to Run
```bash
# Compile and run in terminal
javac Main.java
java Main
