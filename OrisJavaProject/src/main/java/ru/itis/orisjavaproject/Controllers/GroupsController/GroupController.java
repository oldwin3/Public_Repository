package ru.itis.orisjavaproject.Controllers.GroupsController;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.orisjavaproject.Entities.Group;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.Groups.GroupService;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.Services.UserService.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final ImageService imageService;


    @GetMapping
    public String getAllGroups(Model model) {
        log.info("Getting all groups");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        List<Group> groups = groupService.getAllGroups();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.getUserById(userDetails.getUser().getId()).get();
        if (user.getGroup() != null) {
            model.addAttribute("flag", 1);
        } else {
            model.addAttribute("flag", 0);
        }
        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        return "groups/list";
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable UUID id, Model model) {
        log.info("Getting all groups");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Group group = groupService.getGroupById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.getUserById(userDetails.getUser().getId()).get();
        if (group.getGroupPicture() != null) {
            String profilePictureBase64 = Base64.encodeBase64String(group.getGroupPicture());
            model.addAttribute("groupPictureBase64", profilePictureBase64);
        }
        if (user.getId().equals(group.getCreator())) {
            model.addAttribute("flag", 2);
        } else if (user.getGroup() != null) {
            if (user.getGroup().getId().equals(group.getId())) {
                model.addAttribute("flag", 1);
            } else {
                model.addAttribute("flag", 3);
            }
        } else {
            model.addAttribute("flag", 0);
        }
        model.addAttribute("group", group);
        return "groups/group";
    }

    @PostMapping("/{id}")
    public String postGroupById(@PathVariable UUID id) {
        log.info("Getting all groups");
        Group group = groupService.getGroupById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.getUserById(userDetails.getUser().getId()).get();

        if (user.getId().equals(group.getCreator())) {
            userService.removeUsersFromGroup(group);
            groupService.deleteGroup(group.getId());
        } else if (user.getGroup() != null) {
            if (user.getGroup().getId().equals(group.getId())) {
                userService.updateUserGroup(userDetails.getUser().getId(), null);
            }
        } else {
            userService.updateUserGroup(userDetails.getUser().getId(), groupService.getGroupById(id));
        }
        return "redirect:/groups";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        log.info("Showing create group form");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "groups/creata";
    }

    @PostMapping("/new")
    @SneakyThrows
    public String createGroup(@RequestParam String groupName, @RequestParam(value = "groupPicture", required = false)
    MultipartFile groupPicture) {
        log.info("Creating new group with name: {}", groupName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Group group = new Group();
        group.setCreator(userDetails.getUser().getId());
        group.setName(groupName);
        if (groupPicture != null && !groupPicture.isEmpty()) {
            group.setGroupPicture(groupPicture.getBytes());
        }
        groupService.createGroupAndUpdateUser(group, userDetails);
        return "redirect:/groups";
    }

    @GetMapping("/upd")
    public String showUpdateForm(Model model) {
        log.info("Showing update group form");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "groups/creata";
    }


    @PostMapping("/upd")
    @SneakyThrows
    public String updateGroup(@RequestParam String groupName, @RequestParam(value = "groupPicture", required = false)
    MultipartFile groupPicture) {
        log.info("Updating group with name: {}", groupName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Group group = userDetails.getUser().getGroup();
        group.setName(groupName);
        if (groupPicture != null && !groupPicture.isEmpty()) {
            group.setGroupPicture(groupPicture.getBytes());
        }
        groupService.updateGroup(group);
        return "redirect:/groups";
    }


}