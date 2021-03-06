package com.example.userservice.Controller;

import com.example.userservice.DTO.UserDTO;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service/")
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env,UserService userService) {
        this.env = env;
        this.userService=userService;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local" +
                ".server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();

    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){

        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO=mapper.map(user,UserDTO.class);
        userService.createUser(userDTO);

        ResponseUser responseUser=mapper.map(userDTO,ResponseUser.class);
        //201 ??????????????? ???????????? ????????????
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList=userService.getUserByAll();

        List<ResponseUser> result=new ArrayList<>();
        userList.forEach(v ->{
            result.add(new ModelMapper().map(v,ResponseUser.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
       UserDTO userDTO=userService.getUserByUserId(userId);

        List<ResponseUser> result=new ArrayList<>();
        ResponseUser returnValue=new ModelMapper().map(userDTO,
                ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
