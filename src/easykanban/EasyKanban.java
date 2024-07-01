/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package easykanban;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

class Task {
    private String taskName;
    private int taskNumber;
    private String taskDescription;
    private String developerDetails;
    private int taskDuration;
    private String taskID;
    private String taskStatus;

    // Arrays to store data
    public static List<String> developers = new ArrayList<>();
    public static List<String> taskNames = new ArrayList<>();
    public static List<String> taskIDs = new ArrayList<>();
    public static List<Integer> taskDurations = new ArrayList<>();
    public static List<String> taskStatuses = new ArrayList<>();

    public Task(String taskName, int taskNumber, String taskDescription, String developerDetails, int taskDuration, String taskStatus) {
        this.taskName = taskName;
        this.taskNumber = taskNumber;
        this.taskDescription = taskDescription;
        this.developerDetails = developerDetails;
        this.taskDuration = taskDuration;
        this.taskStatus = taskStatus;
        this.taskID = createTaskID();

        // Add to arrays
        developers.add(developerDetails);
        taskNames.add(taskName);
        taskIDs.add(taskID);
        taskDurations.add(taskDuration);
        taskStatuses.add(taskStatus);
    }

    public boolean checkTaskDescription() {
        return taskDescription.length() <= 50;
    }

    public String createTaskID() {
        String taskNamePart = taskName.length() >= 2 ? taskName.substring(0, 2) : taskName;
        String developerPart = developerDetails.length() >= 3 ? developerDetails.substring(developerDetails.length() - 3) : developerDetails;
        return (taskNamePart + ":" + taskNumber + ":" + developerPart).toUpperCase();
    }

    public String printTaskDetails() {
        return "Task Status: " + taskStatus + "\n" +
                "Developer Details: " + developerDetails + "\n" +
                "Task Number: " + taskNumber + "\n" +
                "Task Name: " + taskName + "\n" +
                "Task Description: " + taskDescription + "\n" +
                "Task ID: " + taskID + "\n" +
                "Duration: " + taskDuration + " hours";
    }

    public static int returnTotalHours() {
        int totalHours = 0;
        for (int duration : taskDurations) {
            totalHours += duration;
        }
        return totalHours;
    }

    // Getter for taskDescription
    public String getTaskDescription() {
        return taskDescription;
    }
}

public class EasyKanban {
    private static List<Task> tasks = new ArrayList<>();
    private static int taskCounter = 0;

    public static void main(String[] args) {
        if (authenticateUser()) {
            JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");
            boolean quit = false;
            while (!quit) {
                String option = JOptionPane.showInputDialog("Choose an option:\n1) Add tasks\n2) Show report\n3) Quit");
                switch (option) {
                    case "1":
                        addTasks();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null, "Coming Soon");
                        break;
                    case "3":
                        quit = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                }
            }
        }
    }

    private static boolean authenticateUser() {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        // In a real application, add proper authentication checks here
        return username.equals("admin") && password.equals("password");
    }

    private static void addTasks() {
        int numberOfTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks to add:"));
        for (int i = 0; i < numberOfTasks; i++) {
            String taskName = JOptionPane.showInputDialog("Enter task name:");
            String taskDescription = JOptionPane.showInputDialog("Enter task description:");
            String developerDetails = JOptionPane.showInputDialog("Enter developer details (first and last name):");
            int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter task duration in hours:"));
            String[] statuses = {"To Do", "Doing", "Done"};
            String taskStatus = (String) JOptionPane.showInputDialog(null, "Select task status:", "Task Status", JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);

            Task task = new Task(taskName, taskCounter++, taskDescription, developerDetails, taskDuration, taskStatus);

            if (task.checkTaskDescription()) {
                tasks.add(task);
                JOptionPane.showMessageDialog(null, "Task successfully captured");
                JOptionPane.showMessageDialog(null, task.printTaskDetails());
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
                taskCounter--; // Revert the counter as the task was not added
            }
        }
        int totalHours = Task.returnTotalHours();
        JOptionPane.showMessageDialog(null, "Total hours for all tasks: " + totalHours);
    }

    // Additional methods as per requirements:

    public static void displayTasksDone() {
        for (int i = 0; i < tasks.size(); i++) {
            if (Task.taskStatuses.get(i).equals("Done")) {
                System.out.println("Developer: " + Task.developers.get(i) +
                        ", Task Name: " + Task.taskNames.get(i) +
                        ", Task Duration: " + Task.taskDurations.get(i));
            }
        }
    }

    public static void displayLongestTask() {
        int maxDuration = Integer.MIN_VALUE;
        int maxIndex = -1;

        for (int i = 0; i < tasks.size(); i++) {
            if (Task.taskDurations.get(i) > maxDuration) {
                maxDuration = Task.taskDurations.get(i);
                maxIndex = i;
            }
        }

        if (maxIndex != -1) {
            System.out.println("Developer: " + Task.developers.get(maxIndex) +
                    ", Task Duration: " + Task.taskDurations.get(maxIndex));
        }
    }

    public static void searchTaskByName(String name) {
        for (int i = 0; i < tasks.size(); i++) {
            if (Task.taskNames.get(i).equalsIgnoreCase(name)) {
                System.out.println("Task Name: " + Task.taskNames.get(i) +
                        ", Developer: " + Task.developers.get(i) +
                        ", Task Status: " + Task.taskStatuses.get(i));
                return; // Exit after finding the first match
            }
        }
        System.out.println("Task not found.");
    }

    public static void searchTasksByDeveloper(String developer) {
        for (int i = 0; i < tasks.size(); i++) {
            if (Task.developers.get(i).equalsIgnoreCase(developer)) {
                System.out.println("Task Name: " + Task.taskNames.get(i) +
                        ", Task Status: " + Task.taskStatuses.get(i));
            }
        }
    }

    public static void deleteTaskByName(String name) {
        for (int i = 0; i < tasks.size(); i++) {
            if (Task.taskNames.get(i).equalsIgnoreCase(name)) {
                Task.taskNames.remove(i);
                Task.developers.remove(i);
                Task.taskIDs.remove(i);
                Task.taskDurations.remove(i);
                Task.taskStatuses.remove(i);
                System.out.println("Task \"" + name + "\" successfully deleted.");
                return; // Exit after deleting the task
            }
        }
        System.out.println("Task not found.");
    }

    public static void displayFullReport() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Task Name: " + Task.taskNames.get(i) +
                    ", Task ID: " + Task.taskIDs.get(i) +
                    ", Developer: " + Task.developers.get(i) +
                    ", Task Description: " + tasks.get(i).getTaskDescription() +
                    ", Task Status: " + Task.taskStatuses.get(i) +
                    ", Duration: " + Task.taskDurations.get(i));
        }
    }
}
