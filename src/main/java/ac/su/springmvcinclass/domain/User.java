package ac.su.springmvcinclass.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    //요구사항에 따른 비즈니스 로직(BMI 계산)을 위해서 데이터 읽어와야 함
    //Repository에서는 DAO를 사용하지만
    //Service에서 Controller 및 View로 전달할 떄는 DTO를 사용
    @Column(nullable = true)
    private double height;

    @Column(nullable = true)
    private double weight;
}
