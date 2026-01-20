import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListManager {

    static final String FILE_NAME = "tasks.txt";

    // Task class
    static class Task {
        String description;
        String priority;
        LocalDate dueDate;
        boolean isCompleted;

        Task(String description, String priority, LocalDate dueDate, boolean isCompleted) {
            this.description = description;
            this.priority = priority;
            this.dueDate = dueDate;
            this.isCompleted = isCompleted;
        }

        @Override
        public String toString() {
            return (isCompleted ? "[Done] " : "[ ] ") +
                   description + " | Priority: " + priority +
                   " | Due: " + dueDate;
        }

        // Convert task to file format
        String toFileString() {
            return description + ";" + priority + ";" + dueDate + ";" + isCompleted;
        }

        // Create task from file line
        static Task fromFileString(String line) {
            String[] parts = line.split(";");
            return new Task(
                    parts[0],
                    parts[1],
                    LocalDate.parse(parts[2]),
                    Boolean.parseBoolean(parts[3])
            );
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasks();
        int choice;

        do {
            System.out.println("\n--- TO-DO LIST MANAGER ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String desc = scanner.nextLine();

                    System.out.print("Enter priority (HIGH / MEDIUM / LOW): ");
                    String priority = scanner.nextLine().toUpperCase();

                    System.out.print("Enter due date (YYYY-MM-DD): ");
                    LocalDate dueDate = LocalDate.parse(scanner.nextLine());

                    tasks.add(new Task(desc, priority, dueDate, false));
                    saveTasks(tasks);
                    System.out.println("Task added successfully.");
                    break;

                case 2:
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks available.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter task number to mark completed: ");
                    int completeIndex = scanner.nextInt() - 1;
                    if (completeIndex >= 0 && completeIndex < tasks.size()) {
                        tasks.get(completeIndex).isCompleted = true;
                        saveTasks(tasks);
                        System.out.println("Task marked as completed.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 4:
                    System.out.print("Enter task number to delete: ");
                    int deleteIndex = scanner.nextInt() - 1;
                    if (deleteIndex >= 0 && deleteIndex < tasks.size()) {
                        tasks.remove(deleteIndex);
                        saveTasks(tasks);
                        System.out.println("Task deleted successfully.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting program. Tasks saved.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        scanner.close();
    }

    // Save tasks to file
    static void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    // Load tasks from file
    static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return tasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks.");
        }
        return tasks;
    }
}
