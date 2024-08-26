package kafkademo.taskmanagersystem.service.impl;

import com.example.dto.NotificationData;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.service.MessageFormer;
import kafkademo.taskmanagersystem.util.MessageConstant;
import org.springframework.stereotype.Service;

@Service
public class MessageFormerImpl implements MessageFormer {
    @Override
    public NotificationData formMessageAboutProjectMembership(Project project, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject(
                MessageConstant.PROJECT_MEMBERSHIP_SUBJECT.formatted(project.getName())
        );
        notificationData.setMessageText(MessageConstant.PROJECT_MEMBERSHIP.formatted(
                project.getName(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus()
        ));
        return notificationData;
    }

    @Override
    public NotificationData formMessageAboutProjectDeadline(Project project, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject(
                MessageConstant.PROJECT_DEADLINE_SUBJECT.formatted(
                        project.getName()
                )
        );
        notificationData.setMessageText(
                MessageConstant.PROJECT_DEADLINE.formatted(
                        project.getName()
                )
        );
        return notificationData;
    }

    @Override
    public NotificationData formMessageAboutTaskAssigning(String projectName,
                                                          Task task,
                                                          User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject(MessageConstant.TASK_ASSIGNING_SUBJECT);
        notificationData.setMessageText(MessageConstant.TASK_ASSIGNING.formatted(
                projectName,
                task.getName(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate(),
                task.getStatus()
        ));
        return notificationData;
    }

    @Override
    public NotificationData formMessageAboutTaskDeadline(String projectName, Task task, User user) {
        NotificationData notificationData = createAndSetUpRecipient(user);
        notificationData.setMessageSubject(MessageConstant.TASK_DEADLINE_SUBJECT);
        notificationData.setMessageText(MessageConstant.TASK_DEADLINE.formatted(
                projectName,
                task.getName(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        ));
        return notificationData;
    }

    private NotificationData createAndSetUpRecipient(User user) {
        NotificationData notificationData = new NotificationData();
        notificationData.setChatId(user.getChatId());
        notificationData.setEmail(user.getEmail());
        return notificationData;
    }
}
