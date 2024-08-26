package kafkademo.taskmanagersystem.util;

public class MessageConstant {
    public static final String PROJECT_MEMBERSHIP_SUBJECT =
            "Welcome to the Project '%s'!";
    public static final String PROJECT_MEMBERSHIP = """
             You have been added to the project '%s'.
            Below are the details of the project:
            
                Project Name: %s
                Description: %s
                Start Date: %s
                End Date: %s
                Status: %s
            """;
    public static final String PROJECT_DEADLINE_SUBJECT =
            "Project '%s' Deadline Approaching!";
    public static final String PROJECT_DEADLINE =
            "This is a reminder that the project '%s' deadline is today.";
    public static final String TASK_ASSIGNING_SUBJECT = "New Task Assigned!";
    public static final String TASK_ASSIGNING = """
             You have been assigned a new task in the project '%s'.
             Please find the details below:
            
                Task Name: %s
                Description: %s
                Priority: %s
                Due Date: %s
                Status: %s
            """;
    public static final String TASK_DEADLINE_SUBJECT =
            "Task Deadline Approaching!";
    public static final String TASK_DEADLINE = """
            This is a reminder that the task
            assigned to you in the project '%s' deadline is today.
       
                Task Name: %s
                Due Date: %s
                Priority: %s
                Status: %s
            """;

    private MessageConstant() {
    }
}
