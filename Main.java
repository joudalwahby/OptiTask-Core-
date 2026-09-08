import java.util.PriorityQueue;
import java.util.Random;
import java.util.UUID;

// 1. Enum
enum TaskPriority {
    HIGH,
    MEDIUM,
    LOW
}

// 2. Task Class
class Task implements Comparable<Task> {
    private String id;
    private String name;
    private TaskPriority priority;
    private int maxRetries;
    private int currentRetries;

    public Task(String name, TaskPriority priority, int maxRetries) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.priority = priority;
        this.maxRetries = maxRetries;
        this.currentRetries = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public TaskPriority getPriority() { return priority; }
    public int getMaxRetries() { return maxRetries; }
    public int getCurrentRetries() { return currentRetries; }

    public void incrementRetries() { this.currentRetries++; }

    @Override
    public int compareTo(Task other) {
        return this.priority.compareTo(other.priority);
    }

    @Override
    public String toString() {
        return "[" + priority + "] " + name + " (ID: " + id + ")";
    }
}

// 3. TaskScheduler Class
class TaskScheduler {
    private PriorityQueue<Task> taskQueue;
    private Random random;

    public TaskScheduler() {
        this.taskQueue = new PriorityQueue<>();
        this.random = new Random();
    }

    public void addTask(Task task) {
        taskQueue.add(task);
        System.out.println("[ADDED] Task: " + task);
    }

    public void processNextTask() {
        if (taskQueue.isEmpty()) {
            System.out.println("[INFO] No tasks in queue.");
            return;
        }

        Task currentTask = taskQueue.poll();
        System.out.println("\n[PROCESSING] Task: " + currentTask);

        boolean isSuccess = random.nextBoolean(); 

        if (isSuccess) {
            System.out.println("[SUCCESS] Completed: " + currentTask.getName());
        } else {
            System.out.println("[FAILED] Task failed: " + currentTask.getName());
            handleFailure(currentTask);
        }
    }

    private void handleFailure(Task task) {
        if (task.getCurrentRetries() < task.getMaxRetries()) {
            task.incrementRetries();
            System.out.println("[RETRYING] Re-queueing... (Attempt " + task.getCurrentRetries() + " of " + task.getMaxRetries() + ")");
            taskQueue.add(task);
        } else {
            System.out.println("[DROPPED] Task [" + task.getName() + "] dropped after max retries.");
        }
    }

    public boolean hasTasks() {
        return !taskQueue.isEmpty();
    }
}

// 4. Main Class
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler();

        System.out.println("=== Task Scheduler Engine Initialized ===\n");

        scheduler.addTask(new Task("Send Password Reset Email", TaskPriority.HIGH, 2));
        scheduler.addTask(new Task("Generate Monthly Report", TaskPriority.LOW, 1));
        scheduler.addTask(new Task("Process Payment Transaction", TaskPriority.HIGH, 3));
        scheduler.addTask(new Task("Compress Uploaded Image", TaskPriority.MEDIUM, 2));

        System.out.println("\n==================================================");
        System.out.println("Starting Priority Task Execution...");
        System.out.println("==================================================\n");

        while (scheduler.hasTasks()) {
            scheduler.processNextTask();
            Thread.sleep(1000);
        }

        System.out.println("\n*** All tasks completed successfully! ***");
    }
}