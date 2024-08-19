package kafkademo.taskmanagersystem.validation;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import kafkademo.taskmanagersystem.dto.user.VerificationData;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {
    private static final int EXPIRATION_PERIOD = 1;
    private final ConcurrentHashMap<String, VerificationData> verificationMap =
            new ConcurrentHashMap<>();
    @Autowired
    private UserRepository userRepository;

    public void saveVerificationData(String token, VerificationData verificationData) {
        verificationMap.put(token, verificationData);
    }

    public boolean verifyData(String token) {
        VerificationData verificationData = verificationMap.get(token);
        if (verificationData == null) {
            return false;
        }
        verificationMap.remove(token);
        LocalDateTime createdAt = verificationData.getCreatedAt();
        LocalDateTime expiredAt = createdAt.plusHours(EXPIRATION_PERIOD);
        if (LocalDateTime.now().isAfter(expiredAt)) {
            return false;
        }
        User user = userRepository.findUserByEmail(verificationData.getEmail()).get();
        user.setChatId(Long.parseLong(verificationData.getChatId()));
        userRepository.save(user);
        return true;
    }
}
