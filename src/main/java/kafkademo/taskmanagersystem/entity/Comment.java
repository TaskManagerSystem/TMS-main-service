package kafkademo.taskmanagersystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @ManyToOne(fetch = FetchType.LAZY)
    private Long taskId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Long userId;
    private String text;
    @CreationTimestamp
    private LocalDateTime timestamp;
}
