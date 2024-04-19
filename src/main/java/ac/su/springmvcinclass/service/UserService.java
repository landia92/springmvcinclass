package ac.su.springmvcinclass.service;

import ac.su.springmvcinclass.domain.User;
import ac.su.springmvcinclass.domain.UserBmiDTO;
import ac.su.springmvcinclass.domain.UserDTO;
import ac.su.springmvcinclass.exception.UserNotFoundException;
import ac.su.springmvcinclass.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public static List<UserDTO> convertToUserDTOList(List<User> userList) {
        return userList.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllUsers(){
        //구체적인 조건, 처리 세부사항 등의 로직을
        //Service Layer에서 처리한다
        List<User> allUserList =  userRepository.findAll();
        return convertToUserDTOList(allUserList);
    }

    public User getUserByID(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        //1. throw case 에러 catch
        //2. dummy 객체 리턴
        //3. custom error 패이지로 이동
    }

    public UserDTO getUserDTOById(Long id){
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        //DB에서
        return UserDTO.fromEntity(existingUser);
    }

    public UserDTO createUser(User newUser) {
        User createdUser = userRepository.save(newUser); // id 값이 새로 생성 되므로, SQL create 문 수행
        return UserDTO.fromEntity(createdUser);
    }

    public UserDTO updateUser(Long id, User updatedUser) {
        // 유저 검색
        User registeredUser = getUserByID(id);
        registeredUser.setName(updatedUser.getName());
        registeredUser.setEmail(updatedUser.getEmail());
        User updatesdUser = userRepository.save(registeredUser); // id 값이 이미 존재 하므로 SQL update 문 수행
        return UserDTO.fromEntity(updatesdUser);
    }

    public UserDTO patchUser(Long id, User updatedUser) {
        // PATCH 의 경우 user 데이터 일부 필드만 수신될 가능성 고려해서 Validation
        // 모든 필드 비어있을 가능성도 있음
        // 없는 유저 대상 exception 처리도 여전히 상존
        User existingUser = getUserByID(id);
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        User patchedUser = userRepository.save(existingUser);
        return UserDTO.fromEntity(patchedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserBmiDTO> findAllUserWithBmi() {
        List<User> allUsers = userRepository.findAll();
        return convertToUserBmiDTOList(allUsers);
    }

    public static List<UserBmiDTO> convertToUserBmiDTOList(List<User> userList) {
        return userList.stream()
                .map(UserBmiDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserBmiDTO findUserBmiDTOById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserBmiDTO.fromEntity(foundUser);
    }

    public UserBmiDTO createUserBmi(User newUser) {
        User createdUser = userRepository.save(newUser); // id 값이 새로 생성 되므로, SQL create 문 수행
        return UserBmiDTO.fromEntity(createdUser);
    }

    public UserBmiDTO updateUserBmi(Long id, User updatedUser) {
        // 유저 검색
        User registeredUser = getUserByID(id);
        registeredUser.setName(updatedUser.getName());
        registeredUser.setEmail(updatedUser.getEmail());
        registeredUser.setWeight(updatedUser.getHeight());
        registeredUser.setWeight(updatedUser.getWeight());
        User updatesdUser = userRepository.save(registeredUser); // id 값이 이미 존재 하므로 SQL update 문 수행
        return UserBmiDTO.fromEntity(updatesdUser);
    }

    public UserBmiDTO patchUserBmi(Long id, User updatedUser) {
        // PATCH 의 경우 user 데이터 일부 필드만 수신될 가능성 고려해서 Validation
        // 모든 필드 비어있을 가능성도 있음
        // 없는 유저 대상 exception 처리도 여전히 상존
        User existingUser = getUserByID(id);
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getHeight() != 0) {
            existingUser.setHeight(updatedUser.getHeight());
        }
        if (updatedUser.getWeight() != 0) {
            existingUser.setWeight(updatedUser.getWeight());
        }
        User patchedUser = userRepository.save(existingUser);
        return UserBmiDTO.fromEntity(patchedUser);
    }
}
