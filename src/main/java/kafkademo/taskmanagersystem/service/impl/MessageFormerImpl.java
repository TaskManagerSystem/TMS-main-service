package kafkademo.taskmanagersystem.service.impl;

import com.example.dto.NotificationData;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.service.MessageFormer;
import org.springframework.stereotype.Service;

@Service
public class MessageFormerImpl implements MessageFormer {
    @Override
    public NotificationData formMessageAboutProjectMembership(Project project, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject("Project membership");
        notificationData.setMessageText("User: %s, Project: %s".formatted(
                notificationData.getEmail(), project.getName())
        );
        return notificationData;
        //TODO: finish this method
    }

    @Override
    public NotificationData formMessageAboutProjectDeadline(Project project, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject("Project deadline!");
        notificationData.setMessageText("");
        return notificationData;
        //TODO: finish this method
    }

    @Override
    public NotificationData formMessageAboutTaskAssigning(Task task, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        //TODO: finish this method
        return notificationData;
    }

    @Override
    public NotificationData formMessageAboutTaskDeadline(Task task, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject("Task deadline!");
        notificationData.setMessageText("");
        return notificationData;
        //TODO: finish this method
    }

    private NotificationData createAndSetUpRecipient(User user) {
        NotificationData notificationData = new NotificationData();
        notificationData.setChatId(user.getChatId());
        notificationData.setEmail(user.getEmail());
        return notificationData;
    }
}
