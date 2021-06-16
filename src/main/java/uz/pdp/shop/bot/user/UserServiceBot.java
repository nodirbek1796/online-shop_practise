package uz.pdp.shop.bot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceBot {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceBot(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(long chatId){
        Optional<UserDatabase> optionalUserDatabase = userRepository.findByChatId(chatId);

        if (optionalUserDatabase.isEmpty()){
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.setChatId(chatId);
            userRepository.save(userDatabase);
        }

    }
}
