package ru.itis.orisjavaproject.Services.Groups;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.Group;
import ru.itis.orisjavaproject.Repositories.GroupRepository;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.UserService.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;


    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(UUID id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(UUID id) {
        groupRepository.deleteById(id);
    }

    @Transactional
    public void createGroupAndUpdateUser(Group group, UserDetailsImpl userDetails) {
        createGroup(group);
        userService.updateUserGroup(userDetails.getUser().getId(), group);
    }

}