package com.mateus.tasks.service;

import com.mateus.tasks.client.NotificationClient;
import com.mateus.tasks.client.dto.NotificationRequest;
import com.mateus.tasks.entity.Task;
import com.mateus.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

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
        List<Task> tasks = taskRepository.findDueTask();

        tasks.forEach(task -> {
            NotificationRequest notificationRequest = new NotificationRequest(
                    "sua tarefa: " + task.getTitle() + "esta prestes a expirar", task.getEmail());
            notificationClient.sendNotification(notificationRequest);
            task.setNotified(true);
        });

    }


}
