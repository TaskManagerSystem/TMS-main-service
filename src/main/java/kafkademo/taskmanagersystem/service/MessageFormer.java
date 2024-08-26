package kafkademo.taskmanagersystem.service;

import com.example.dto.NotificationData;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;

public interface MessageFormer {
    NotificationData formMessageAboutAddingProjectMember(Project project, User user);

    NotificationData formMessageAboutRemovingProjectMember(Project project, User user);

    NotificationData formMessageAboutProjectDeadline(Project project, User user);

    NotificationData formMessageAboutTaskAssigning(String projectName, Task task, User user);

    NotificationData formMessageAboutTaskDeadline(String projectName, Task task, User user);
}
