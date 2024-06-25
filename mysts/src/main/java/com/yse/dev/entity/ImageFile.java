package com.yse.dev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ImageFile {
  @Id
  private String id;

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

}
