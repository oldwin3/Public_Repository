package ru.itis.orisjavaproject.Services.AdminService;


import ru.itis.orisjavaproject.Entities.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUser();

    void banAllUsersExceptAdmin();
}
