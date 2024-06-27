package com.yse.dev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)//데이타변경 감시처리
public class ImageFile {
  @Id
  private String id;

  @Column(name="seq_No", columnDefinition = "BIGINT")
  private Long seqNo;

  @Lob
  @Column(name = "image", columnDefinition="LONGBLOB")
  private byte[] photo;

  @Column(name = "predTitle1", columnDefinition="varchar(1024)")
  private String predTitle1;

  @Column(name = "predTitle2", columnDefinition="varchar(1024)")
  private String predTitle2;

  @Column(name = "predTitle3", columnDefinition="varchar(1024)")
  private String predTitle3;

  @Column(name = "trueTitle", columnDefinition="varchar(2)")
  private String truthTitle; // {1,2,3,4} 제목1이 맞으면1, 제목2가 맞으면2, 제목3이 맞으면3, 그외 4

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;

  // 최초 생성(insert)시 날짜시간
  @PrePersist
  protected void onCreate() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }
  // 수정(update)시 날짜시간
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

}
