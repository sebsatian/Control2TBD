package com.example.Control2TBD.persistence.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean completed;
    private Long userId;
}
