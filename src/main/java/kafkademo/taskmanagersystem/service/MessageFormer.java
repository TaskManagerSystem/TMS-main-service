package kafkademo.taskmanagersystem.service;

import com.example.dto.NotificationData;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;

public interface MessageFormer {
    NotificationData formMessageAboutProjectMembership(Project project, User user);

    NotificationData formMessageAboutProjectDeadline(Project project, User user);

    NotificationData formMessageAboutTaskDeadline(Task task, User user);
}
