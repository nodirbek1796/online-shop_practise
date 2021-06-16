package uz.pdp.shop.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.model.receive.user.UserReceiveModel;
import uz.pdp.shop.model.response.base.BaseRestResponse;
import uz.pdp.shop.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public BaseRestResponse addUser(
//            UserReceiveModel userReceiveModel
//    ){
//        UserDatabase userDatabase = new UserDatabase();
//        userDatabase.setName(userReceiveModel.getName());
//        userDatabase.setAge(userReceiveModel.getAge());
//        userDatabase.setEmail(userReceiveModel.getEmail());
//        userDatabase.setPhoneNumber(userReceiveModel.getPhoneNumber());
//        userDatabase.setPassword(userReceiveModel.getPassword());
//        userRepository.save(userDatabase);
//    }
}
