package com.web.blog.controller.account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.UserDao;
import com.web.blog.jwt.JwtService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtService jwtService;

    // @GetMapping("/account/login")
    // @ApiOperation(value = "로그인")
    // public Object login(@RequestParam(required = true) final String email,
    // @RequestParam(required = true) final String password) {

    // Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

    // ResponseEntity response = null;

    // if (userOpt.isPresent()) {
    // final BasicResponse result = new BasicResponse();
    // result.status = true;
    // result.data = "success";
    // response = new ResponseEntity<>(result, HttpStatus.OK);
    // } else {
    // response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    // }

    // return response;
    // }

    @PostMapping("/account/kakaologin")
    @ApiOperation(value = "카카오 로그인")
    public Object viewInfo(@RequestBody User request) throws SQLException, IOException {
        String token = null;
        System.out.println(request.getUid());
        try {
            Optional<User> userOpt = userDao.findUserByUid(request.getUid());
            if (userOpt.isPresent()) {
                User tokenuser = new User();
                tokenuser.setUid(userOpt.get().getUid());
                tokenuser.setName(userOpt.get().getName());
                token = jwtService.createLoginToken(tokenuser);
                System.out.println(token);
                return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
            } else {
                System.out.println("2번째");
                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")

    public Object signup(@Valid @RequestBody User request) {
        String token = null;
        System.out.println("logger - signup method");
        // 이메일, 닉네임 중복처리 필수

        System.out.println(request.getEmail());
        User user = userDao.getUserByEmail(request.getEmail());

        if (user != null) {
            System.out.println("logger - 해당 이메일이 이미 있음 ");

        } else {
            // 회원가입
            System.out.println("logger - 회원가입 진행");

            userDao.save(request);
            User tokenuser = new User();
            tokenuser.setUid(request.getUid());
            tokenuser.setName(request.getName());
            token = jwtService.createLoginToken(tokenuser);
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);

        }
        // 회원가입단을 생성해 보세요.

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/authuser")
    @ApiOperation(value = "토큰으로 유저정보 가져오기")
    public Object authUser(HttpServletRequest request) throws SQLException, IOException {
        System.out.println("logger - 토큰으로 유저정보 가져오기");
        String token = request.getHeader("jwtToken");
        System.out.println(token);
        User tokenuser = jwtService.getUser(token);

        Optional<User> userinfo = userDao.findUserByUid(tokenuser.getUid());
        try {
            if (userinfo.isPresent()) {
                return new ResponseEntity<>(userinfo.get(), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateuser")
    @ApiOperation(value = "회원정보 수정하기")
    public void updateUser(@RequestBody User updateReq, HttpServletRequest request) throws SQLException, IOException {
        System.out.println("logger - 회원정보 수정하기");
        // String token = request.getHeader("jwtToken");
        // User tokenuser = jwtService.getUser(token);
        try {
            userDao.save(updateReq);
            // return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account/takepic")
    public ResponseEntity<?> takePictoJoin() {
        ResponseEntity<?> response = null;
        String[] command = new String[8];

        command[0] = "python";
        command[1] = "C:\\Users\\multicampus\\Desktop\\project3\\s03p31b107\\face_classifier\\face_classifier.py";
        command[2] = "0";
        command[3] = "-d";
        command[4] = "-S";
        command[5] = "0.1";
        command[6] = "-c";
        command[7] = "a";

        try {
            ByteArrayOutputStream out = execPython(command);
            String extact_result = out.toString();
            for (int i = 0; i < extact_result.length(); i++) {
                char c = extact_result.charAt(i);
                if (c == '\n' || c == '\r') {
                    break;
                }
            }

            command = new String[2];
            command[0] = "python";
            command[1] = "C:\\Users\\multicampus\\Desktop\\project3\\s03p31b107\\face_classifier\\only_train.py";
            try {
                out = execPython(command);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response = new ResponseEntity<>(null, HttpStatus.OK);

        return response;

    }

    @GetMapping("/kiosk/recog")
    public ResponseEntity<?> recog() {
        ResponseEntity<?> response = null;
        BasicResponse result = new BasicResponse();
        String[] command = new String[8];
        StringBuffer res = new StringBuffer();

        command[0] = "python";
        command[1] = "C:\\Users\\multicampus\\Desktop\\project3\\s03p31b107\\face_classifier\\take_pic.py";
        command[2] = "0";
        command[3] = "-d";
        command[4] = "-S";
        command[5] = "0.1";
        command[6] = "-c";
        command[7] = "a";

        try {
            ByteArrayOutputStream out = execPython(command);
            String extact_result = out.toString();
            for (int i = 0; i < extact_result.length(); i++) {
                char c = extact_result.charAt(i);
                if (c == '\n' || c == '\r') {
                    break;
                } else if (c != ' ') {
                    // res.append(c);
                }
            }

            command = new String[2];
            command[0] = "python";
            // command[1] =
            // "C:\\Users\\multicampus\\Desktop\\project3\\s03p31b107\\face_classifier\\face_recognition_mlp.py";
            command[1] = "C:\\Users\\multicampus\\Desktop\\project3\\s03p31b107\\face_classifier\\face_recognition_knn.py";
            // res = new StringBuffer();
            try {
                out = execPython(command);
                extact_result = out.toString();

                for (int i = 0; i < extact_result.length(); i++) {
                    char c = extact_result.charAt(i);
                    if (c == '\n' || c == '\r') {
                        break;
                    } else if (c != ' ') {
                        res.append(c);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (res.toString().equals("INCORRECT")) {
            result.data = "찾을 수 없는 유저입니다.";
            result.object = "Unknown";
        } else {
            result.data = "가입된 유저입니다.";
            result.object = res.toString().split(":")[1];
        }
        System.out.println(result.data);
        response = new ResponseEntity<>(result, HttpStatus.OK);

        return response;

    }

    public static ByteArrayOutputStream execPython(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int[] ev = { 0, 1, 2 };
        executor.setExitValues(ev);
        int result = executor.execute(commandLine);
        return outputStream;
    }
}