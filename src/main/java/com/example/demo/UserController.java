package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

//    private List<User> users = new ArrayList<>();
//
//    @PostConstruct
//    public void init() {
//        users.add(new User(1, "Violet", "violet@gmail.com", 25));
//        users.add(new User(2, "Emma", "emma@gmail.com", 25));
//        users.add(new User(3, "Harry", "harry@gmail.com", 25));
//        users.add(new User(4, "Ronald", "ron@gmail.com", 25));
//    }

    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/api/users")
//    public List<User> getAllUsers() {
//        return users;
//    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @GetMapping("/api/users/{id}")
//    public User getUser(@PathVariable Integer id) {
//        return users.stream()
//                .filter(user -> user.getId() == id)
//                .findFirst()
//                .orElse(null);
//    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping("/api/users")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        int newId = users.size()+1;
//        user.setId(newId);
//        users.add(user);
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }


//    @PutMapping("/api/users/{id}")
//    public ResponseEntity<User> updateUser(@RequestBody User updatedUser, @PathVariable Integer id) {
//        for (User user : users) {
//            if (user.getId().equals(id)) {
//                // Update user details
//                user.setName(updatedUser.getName());
//                user.setEmail(updatedUser.getEmail());
//                user.setAge(updatedUser.getAge());
//                return ResponseEntity.ok(user);
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser, @PathVariable Integer id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            updatedUser.setId(existingUser.get().getId());
            User savedUser = userRepository.save(updatedUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @DeleteMapping("/api/users/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
//        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
//            User user = iterator.next();
//            if (user.getId().equals(id)) {
//                iterator.remove();
//                return ResponseEntity.ok().build();
//            }
//        }
//        return null;
//    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
