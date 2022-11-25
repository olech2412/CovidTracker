package org.vaadin.covid.manager;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.repository.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class UserManager{

    @Autowired
    private UsersRepository usersRepository;

    private UserManager(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    private void setRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void createNewUser(Users user) {
        log.info("User: " + VaadinSession.getCurrent().getSession().getId() + " saveUser: " + user.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode("Start-1234!"));
        user.setEnabled(true);
        user.setCreationDate(LocalDate.now());
        user.setLastLogin(LocalDateTime.now());
        usersRepository.save(user);
    }

    public void saveUser(Users user) {
        log.info("User: " + VaadinSession.getCurrent().getSession().getId() + " saveUser: " + user.getUsername());
        usersRepository.save(user);
    }


    public void deleteUser(Users user) {
        log.info("User: " + VaadinSession.getCurrent().getSession().getId() + " deleteUser: " + user.getUsername());
        usersRepository.delete(user);
    }

    public void updateUser(Users user) {
        log.info("User: " + VaadinSession.getCurrent().getSession().getId() + " updateUser: " + user.getUsername());
        usersRepository.save(user);
    }


    public List<Users> findAll() {
        log.debug("User: " + VaadinSession.getCurrent().getSession().getId() + " findAll");
        return usersRepository.findAll();
    }

    public void resetPassword(Users user) {
        log.info("User: " + VaadinSession.getCurrent().getSession().getId() + " resetPassword: " + user.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode("Start-1234!"));
        usersRepository.save(user);
        Notification resetSuccess = new Notification("Passwort erfolgreich zurückgesetzt -> Start-1234!", 3000, Notification.Position.BOTTOM_START);
        resetSuccess.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        resetSuccess.open();
    }

    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
