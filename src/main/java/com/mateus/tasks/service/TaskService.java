package com.mateus.tasks.service;

import com.mateus.tasks.client.NotificationClient;
import com.mateus.tasks.client.dto.NotificationRequest;
import com.mateus.tasks.entity.Task;
import com.mateus.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationClient  notificationClient;

    public TaskService(TaskRepository taskRepository, NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
        this.taskRepository = taskRepository;
    }

    public void sendNotificationForDueTasks(){
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        List<Task> tasks = taskRepository.findTasksDueWithinDeadline(deadline);

        tasks.forEach(task -> {
            NotificationRequest notificationRequest = new NotificationRequest(
                    "sua tarefa: " + task.getTitle() + "esta prestes a expirar", task.getEmail());
            notificationClient.sendNotification(notificationRequest);
            task.setNotified(true);
        });

    }


}
