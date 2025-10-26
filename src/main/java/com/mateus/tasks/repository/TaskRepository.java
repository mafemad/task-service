package com.mateus.tasks.repository;

import com.mateus.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.dueDate <= :deadeline and t.notified = false ")
    List<Task> findTasksDueWithinDeadline(LocalDateTime deadline);
}
