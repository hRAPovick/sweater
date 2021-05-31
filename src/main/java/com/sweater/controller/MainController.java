package com.sweater.controller;

import com.sweater.domain.Message;
import com.sweater.domain.User;
import com.sweater.service.MessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model
    ) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageService.findByTag(filter);
        } else {
            messages = messageService.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException, URISyntaxException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            messageService.save(message);
        }
        Iterable<Message> messages = messageService.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    private void saveFile(
            @Valid Message message,
            @RequestParam("file") MultipartFile file) throws IOException, URISyntaxException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            URL dirURL = MainController.class.getClassLoader().getResource(uploadPath);
            File uploadDir = null;
            if (dirURL != null && dirURL.getProtocol().equals("file")) {
                /* A file path: easy enough */
                uploadDir = new File(dirURL.toURI());
            } else {
                /*
                 * In case of a jar file, we can't actually find a directory.
                 * Have to assume the same jar as clazz.
                 */
                uploadDir = new File("/tmp/" + uploadPath);
            }
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadDir + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }

    @GetMapping("user-messages/{user}")
    public String userMessages (
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {
        Iterable<Message> messages = messageService.findByAuthor(user);

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));

        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", (currentUser.getId()).equals(user.getId()));

        return "userMessages";
    }

    @PostMapping("user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException, URISyntaxException {
        if (user.equals(currentUser.getId())) {
            if (!StringUtils.isBlank(text)) {
                message.setText(text);
            }
            if (!StringUtils.isBlank(tag)) {
                message.setTag(tag);
            }
            saveFile(message, file);
            messageService.save(message);
        }

        return "redirect:/user-messages/" + user;
    }

}