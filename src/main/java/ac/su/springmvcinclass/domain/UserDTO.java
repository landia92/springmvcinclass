package ac.su.springmvcinclass.domain;

import lombok.Getter;

@Getter  // Setter 없어도 됨
public class UserDTO {
    // final 키워드는 View 파라미터 수신용 기본 생성자와 충돌하므로 쓰지 않는다
    private Long id;
    private String name;
    private String email;

    public UserDTO() {} //기본 생성자 삭제 시, Requset(body,param)로 부터 제대로된 수신 데이터 주입이 일어날 수 없음
    
    private UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public static UserDTO fromEntity(User user) {
        //User -> UserDTO
        //UserDTO(User user)는 private 이지만 개발자가 수동으로 데이터를 받아올 수 있다
        return new UserDTO(user);
    }

    public User toEntity() {
        //UserDTO -> User
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        return user;
    }
}