package com.schedule.workout.workoutScheduler.controller.jwtToken;

import com.schedule.workout.workoutScheduler.exceptions.UnauthorizedException;
import com.schedule.workout.workoutScheduler.services.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class JwtTokenController {
    @Autowired
    JwtTokenService tokenService;

    @RequestMapping(method = RequestMethod.POST, value = "/token")
    public ResponseEntity<?> createToken(@Valid @RequestBody JWTRequest jwtRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tokenService.createJwtToken(jwtRequest));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/valid")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tokenService.getUserFromToken(token));
        } catch (ExpiredJwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    build();
        }
    }

}