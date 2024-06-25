package ru.itis.orisjavaproject.Services.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Repositories.UserRepository;


import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository repository;

    @Override
    public List<User> getAllUser() {
        return repository.findAll();
    }

    @Override
    public void banAllUsersExceptAdmin() {
        repository.banAllUsersExceptAdmins();
    }
}
